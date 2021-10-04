package br.com.totvs.plugins.bacen;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.totvs.exceptions.ConfigException;
import br.com.totvs.exceptions.InfraException;
import br.com.totvs.exceptions.LayoutException;
import br.com.totvs.plugins.PluginConfig;
import br.com.totvs.plugins.PluginInterface;
import br.com.totvs.plugins.bacen.certificate.TrustAllX509TrustManager;
import br.com.totvs.plugins.bacen.exceptions.ResourceErroAbstract;
import br.com.totvs.plugins.bacen.utils.BacenUtil;
import br.com.totvs.plugins.bacen.utils.Util;
import br.gov.bcb.scr2.operacional.webservice.BCServicoException_Exception;
import br.gov.bcb.scr2.operacional.webservice.BcMsgRetorno;

/**
 * Classe responsável pela execução do Plugin Bacen SCR
 * 
 */
public class BacenScr extends ResourceErroAbstract implements PluginInterface {

	final Logger LOGGER = Logger.getLogger("BacenScr");

	private static String userSisbacen = "";

	private static String passwordSisbacen = "";

	/**
	 * Método execute(hashIn) principal do Plugin
	 * Responsavel em validar os dados de entrada que estão configurados no "in" do arquivo bacen-scr.json
	 * Autenticar com o usuario e senha para acessar o Bacen
	 * Consumir o WS do Bacen SCR de acordo com o parâmetro do intellector
	 * Recupera as informações que estão configuradas no "out" do bacen-scr.json
	 * Loga os dados de entrada e saída da operação
	 * Lança exceções específicas em casos de validações do Bacen SCR
	 * 
	 */
	public HashMap<String, Object> execute(Map<String, Object> hashIn) throws InfraException, LayoutException, ConfigException {
		HashMap<String, Object> hashOut = new HashMap<String, Object>();
		try {
			LOGGER.debug(">> BacenScr.execute()");

			BacenUtil.validarParametrosEntrada(hashIn);

			this.validarAutenticacaoExecucao(hashIn);

			BacenUtil.popularXMLEnvio(hashIn, hashOut);

			Response retornoResumo = this.consumirWebServiceBacen(hashIn);

			if (retornoResumo == null) {

				throw new LayoutException("Web Service Bacen SCR retornou objeto null.");

			} else {

				BacenUtil.popularXMLEnvioRetorno(hashOut, retornoResumo);

				this.preencherHashOut(hashOut, retornoResumo);
			}

			LOGGER.debug("<< BacenScr.execute()");
		} catch (LayoutException | IOException | KeyManagementException | NoSuchAlgorithmException | JAXBException | ConfigException
				| BCServicoException_Exception e) {
			LOGGER.error("Erro crítico no execute:  [" + e + "]");
			tratarException(e, hashOut);
		}
		return hashOut;
	}

	/**
	 * Validar a autenticacao Coloque a senha de acesso ao sisbacen aqui Se nao tiver esse codigo ocorre o erro "The server sent HTTP status
	 * code 401: Unauthorized"
	 * 
	 * @author rsdelia
	 * @throws ConfigException
	 */
	private void validarAutenticacaoExecucao(Map<String, Object> hashIn) throws ConfigException {

		LOGGER.debug("->>validarAutenticacaoExecucao");

		userSisbacen = (String) hashIn.get("USUARIOSISBACEN");

		passwordSisbacen = (String) hashIn.get("SENHASISBACEN");

		try {
			Authenticator.setDefault(new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					String user = userSisbacen;
					String password = passwordSisbacen;
					LOGGER.debug("Configurou o usuario/senha para autorizacao no site.");
					return new PasswordAuthentication(user, password.toCharArray());
				}
			});
		} catch (RuntimeException e1) {
			throw new ConfigException("Erro no Authenticator.setDefault(): Erro ao autenticar o usuario e senha no SisbacenSCR.", e1);
		}
		LOGGER.debug("<<--validarAutenticacaoExecucao");
	}

	/**
	 * Método responsável em consumir o WS do BACEN SCR
	 * 
	 * @author rsdelia
	 * @param hashIn
	 * @return Response
	 * @throws UnmarshalException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws JAXBException
	 * @throws ConfigException
	 * @throws BCServicoException_Exception 
	 */
	private Response consumirWebServiceBacen(Map<String, Object> hashIn)
			throws UnmarshalException, IOException, NoSuchAlgorithmException, KeyManagementException, JAXBException, ConfigException, BCServicoException_Exception {

		LOGGER.debug("-->> consumirWebServiceBacen");
		
		Response response = null;

		HashMap<String, Object> configuracoesPlugin = PluginConfig.getDetails("bacen-scr");

		String jsonOut = (String) configuracoesPlugin.get("properties");

		ObjectMapper mapper = new ObjectMapper();

		Map<String, String> pluginProperties = mapper.readValue(jsonOut, new TypeReference<Map<String, String>>() {
		});

		HttpURLConnection connection = null;

		SSLContext sc = SSLContext.getInstance("TLS");

		sc.init(null, new TrustManager[] { new TrustAllX509TrustManager() }, new java.security.SecureRandom());

		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		HttpsURLConnection.setDefaultHostnameVerifier(((HostnameVerifier) (String string, SSLSession ssls) -> true));

		String url = BacenUtil.consultarURLBacen(pluginProperties) + BacenUtil.popularParametroIn(hashIn);

		connection = (HttpURLConnection) new URL(url).openConnection();

		connection.setRequestMethod("GET");

		String userSisbacen = (String) hashIn.get("USUARIOSISBACEN");

		String passwordSisbacen = (String) hashIn.get("SENHASISBACEN");

		connection.setRequestProperty("Authorization", "Basic " + BacenUtil.getToken(userSisbacen, passwordSisbacen));

		connection.setDoOutput(true);

		connection.setRequestProperty("Content-Type", "application/xml");

		int responseCode = connection.getResponseCode();

		InputStream inputStream;

		LOGGER.debug("responseCode -> [" + responseCode + "]");
		
		BacenUtil.validarAuthResponse(responseCode);
		
		if (200 <= responseCode && responseCode <= 299) {

			inputStream = connection.getInputStream();

		} else {

			inputStream = connection.getErrorStream();

		}

		String xmlResposta = BacenUtil.tratarXMLRespostaBacen(inputStream);

		LOGGER.debug("xmlResposta -> [" + xmlResposta + "]");
		
		JAXBContext context = JAXBContext.newInstance(Response.class);

		Unmarshaller unmarshaller = context.createUnmarshaller();

		LOGGER.debug("<<-- consumirWebServiceBacen");

		try {
			response = (Response) unmarshaller.unmarshal(new StreamSource(new StringReader(xmlResposta.replaceAll("&", "e"))));			
		} catch (RuntimeException e) {
			LOGGER.error("RuntimeException -> [" + e.getMessage() + "]" );
			throw new BCServicoException_Exception("Erro crítico ao consumir o Bacen SCR", e);
		}
		
		return response;
	}

	/**
	 * Montar hashOut de saída do Plugin Estes valores representam fielmente a estrutura do "out" do arquivo bacen-scr.json
	 * 
	 * Os códigos de vencimentos a serem considerados são os descritos no "Anexo 1: Código de Vencimento - CodVenc" do leiaute do documento
	 * 3040.
	 * 
	 * @author rsdelia
	 * @param hashOut
	 * @param resumoDoCliente
	 * @throws LayoutException
	 */
	private void preencherHashOut(HashMap<String, Object> hashOut, Response resumoDoCliente) throws LayoutException {

		LOGGER.debug("tratarNegocio-->>");

		LOGGER.debug("resumoDoCliente [" + resumoDoCliente.toString() + "]");

		hashOut.put("DATA_BASE_CONSULTADA", resumoDoCliente.getDataBaseConsultada());
		hashOut.put("CNPJ_IF_SOLICITANTE", resumoDoCliente.getCnpjDaIFSolicitante());
		hashOut.put("CODIGO_CLIENTE", resumoDoCliente.getCodigoDoCliente());
		hashOut.put("COOBRIGACAO_ASSUMIDA", resumoDoCliente.getCoobrigacaoAssumida());
		hashOut.put("COOBRIGACAO_RECEBIDA", resumoDoCliente.getCoobrigacaoRecebida());
		hashOut.put("DATA_INICIO_RELACIONAMENTO", resumoDoCliente.getDataInicioRelacionamento());
		hashOut.put("PERCENTUAL_DOCUMENTOS_PROCESSADOS", resumoDoCliente.getPercentualDocumentosProcessados());
		hashOut.put("PERCENTUAL_VOLUME_PROCESSADO", resumoDoCliente.getPercentualVolumeProcessado());
		hashOut.put("QUANTIDADE_INSTITUICOES", resumoDoCliente.getQuantidadeDeInstituicoes());
		hashOut.put("QUANTIDADE_OPERACOES", resumoDoCliente.getQuantidadeDeOperacoes());
		hashOut.put("QUANTIDADE_OPERACOES_DISCORDANCIA", resumoDoCliente.getQuantidadeOperacoesDiscordancia());
		hashOut.put("QUANTIDADE_OPERACOES_SUB_JUDICE", resumoDoCliente.getQuantidadeOperacoesSubJudice());
		hashOut.put("RESPONSABILIDADE_TOTAL_DISCORDANCIA", resumoDoCliente.getResponsabilidadeTotalDiscordancia());
		hashOut.put("RESPONSABILIDADE_TOTAL_SUB_JUDICE", resumoDoCliente.getResponsabilidadeTotalSubJudice());
		hashOut.put("RISCO_INDIRETO_VENDOR", resumoDoCliente.getRiscoIndiretoVendor());
		hashOut.put("TIPO_DO_CLIENTE", resumoDoCliente.getTipoDoCliente());
		hashOut.put("EXISTE_VALORES_DE_VENCIMENTOS", Boolean.FALSE);	
		
		this.tratarMensagemRetornoValidacao(resumoDoCliente);
		
		BacenUtil.popularHashSaida(hashOut, resumoDoCliente);		
		
		LOGGER.debug("<<--tratarNegocio");
	}

	/**
	 * Caso a lista esteja preenchida é porque houve alguma validação no WS do Bacen SCR e vamos exibir a mensagem concatenada com o código
	 * no intellector
	 * 
	 * @author rsdelia
	 * @param resumoDoCliente
	 * @throws LayoutException
	 */
	private void tratarMensagemRetornoValidacao(Response resumoDoCliente) throws LayoutException {
		List<BcMsgRetorno> listaMensagensBcMsgRetorno = resumoDoCliente.getListaDeMensagensDeValidacao();

		if (!Util.isNullOrEmpty(listaMensagensBcMsgRetorno)) {
			LOGGER.error("[" + listaMensagensBcMsgRetorno.toString() + "]");
			throw new LayoutException("Código: [" + listaMensagensBcMsgRetorno.get(0).getCodigo() + "] - Mensagem Validação: ["
					+ listaMensagensBcMsgRetorno.get(0).getMensagem() + "]");
		}
	}
}