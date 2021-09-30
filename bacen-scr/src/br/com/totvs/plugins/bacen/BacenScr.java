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
import br.gov.bcb.scr2.operacional.webservice.BcMsgRetorno;
import br.gov.bcb.scr2.operacional.webservice.ResumoDaOperacao;
import br.gov.bcb.scr2.operacional.webservice.ResumoDoVencimento;

/**
 * Classe de acesso ao web service Bacen SCR
 * 
 */
public class BacenScr extends ResourceErroAbstract implements PluginInterface {

	final Logger LOGGER = Logger.getLogger("BacenScr");

	private static String userSisbacen = "";

	private static String passwordSisbacen = "";

	public HashMap<String, Object> execute(Map<String, Object> hashIn) throws InfraException, LayoutException, ConfigException {
		HashMap<String, Object> hashOut = new HashMap<String, Object>();
		try {
			LOGGER.info(">> BacenScr.execute()");

			BacenUtil.validarParametrosEntrada(hashIn);

			HashMap<String, Object> configuracoesPlugin = PluginConfig.getDetails("bacen-scr");

			String jsonOut = (String) configuracoesPlugin.get("properties");

			ObjectMapper mapper = new ObjectMapper();

			Map<String, String> pluginProperties = mapper.readValue(jsonOut, new TypeReference<Map<String, String>>() {
			});

			this.validarAutenticacaoExecucao(hashIn);

			Response retornoResumo = this.consumirWebServiceBacen(hashIn, pluginProperties);

			if (retornoResumo == null) {

				LOGGER.info(" retornoResumo null ");

				throw new LayoutException("ERRO VAMOS VER 2");

			} else {

				LOGGER.info(" retornoResumo preenchido ");

				BacenUtil.popularXMLEnvioRetorno(hashIn, hashOut, retornoResumo);

				this.tratarNegocio(hashOut, retornoResumo);
			}

			LOGGER.info("<< BacenScr.execute()");

		} catch (LayoutException | IOException | KeyManagementException | NoSuchAlgorithmException | JAXBException | ConfigException e) {

			LOGGER.error(" ERRO GERAL:  [" + e + "]");

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

		LOGGER.info("->>validarAutenticacaoExecucao");

		userSisbacen = (String) hashIn.get("USUARIOSISBACEN");

		passwordSisbacen = (String) hashIn.get("SENHASISBACEN");

		try {
			Authenticator.setDefault(new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					String user = userSisbacen;
					String password = passwordSisbacen;
					LOGGER.info("Configurou o usuario/senha para autorizacao no site.");
					return new PasswordAuthentication(user, password.toCharArray());
				}
			});
		} catch (RuntimeException e1) {
			throw new ConfigException("Erro no Authenticator.setDefault(): Erro ao autenticar o usuario e senha no SisbacenSCR.", e1);
		}
		LOGGER.info("<<--validarAutenticacaoExecucao");
	}

	public Response consumirWebServiceBacen(Map<String, Object> hashIn, Map<String, String> configuracoesPlugin)
			throws UnmarshalException, IOException, NoSuchAlgorithmException, KeyManagementException, JAXBException, ConfigException {

		LOGGER.info("-->> consumirWebServiceBacen");

		HttpURLConnection connection = null;

		SSLContext sc = SSLContext.getInstance("TLS");

		sc.init(null, new TrustManager[] { new TrustAllX509TrustManager() }, new java.security.SecureRandom());

		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		HttpsURLConnection.setDefaultHostnameVerifier(((HostnameVerifier) (String string, SSLSession ssls) -> true));

		String url = BacenUtil.consultarURLBacen(configuracoesPlugin) + BacenUtil.popularParametroIn(hashIn);

		LOGGER.info("url [" + url + "]");

		connection = (HttpURLConnection) new URL(url).openConnection();

		connection.setRequestMethod("GET");

		String userSisbacen = (String) hashIn.get("USUARIOSISBACEN");

		String passwordSisbacen = (String) hashIn.get("SENHASISBACEN");

		connection.setRequestProperty("Authorization", "Basic " + BacenUtil.getToken(userSisbacen, passwordSisbacen));

		connection.setDoOutput(true);

		connection.setRequestProperty("Content-Type", "application/xml");

		int responseCode = connection.getResponseCode();
		InputStream inputStream;
		if (200 <= responseCode && responseCode <= 299) {
			inputStream = connection.getInputStream();
		} else {
			inputStream = connection.getErrorStream();
		}
		String xmlResposta = BacenUtil.tratarXMLRespostaBacen(inputStream);
		JAXBContext context = JAXBContext.newInstance(Response.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		LOGGER.info("<<-- consumirWebServiceBacen");
		return (Response) unmarshaller.unmarshal(new StreamSource(new StringReader(xmlResposta.replaceAll("&", "e"))));
	}

	/**
	 * Montar hashOut
	 * 
	 * @author rsdelia
	 * @param hashOut
	 * @param resumoDoCliente
	 * @throws LayoutException
	 */
	private void tratarNegocio(HashMap<String, Object> hashOut, Response resumoDoCliente) throws LayoutException {

		LOGGER.info("tratarNegocio-->>");

		LOGGER.info("resumoDoCliente [" + resumoDoCliente.toString() + "]");

		hashOut.put("DATA_BASE_SISBACEN", resumoDoCliente.getDataBaseConsultada());

		hashOut.put("VLR_VENCIDO", 7777.44);

		List<BcMsgRetorno> listaMensagensBcMsgRetorno = resumoDoCliente.getListaDeMensagensDeValidacao();

		// retorna a exceção com o código concatenado com a mensagem de erro de validação
		if (!Util.isNullOrEmpty(listaMensagensBcMsgRetorno)) {
			LOGGER.info("Lista de Mensagens de Validacao [" + listaMensagensBcMsgRetorno.size() + "]");
			throw new LayoutException(
					listaMensagensBcMsgRetorno.get(0).getCodigo() + "-" + listaMensagensBcMsgRetorno.get(0).getMensagem());
		}

		// Resumo das operacoes
		List<ResumoDaOperacao> listaDeResumoDasOperacoes = resumoDoCliente.getListaDeResumoDasOperacoes();

		if (!Util.isNullOrEmpty(listaDeResumoDasOperacoes)) {

			for (ResumoDaOperacao resumoOperacao : listaDeResumoDasOperacoes) {

				LOGGER.info("resumoOperacao [" + resumoOperacao.toString() + "]");

				List<ResumoDoVencimento> listaDeVencimentos = resumoOperacao.getListaDeVencimentos();

				if (!Util.isNullOrEmpty(listaDeVencimentos)) {
					for (ResumoDoVencimento resumoDoVencimento : resumoOperacao.getListaDeVencimentos()) {
						LOGGER.info("resumoDoVencimento [" + resumoDoVencimento.toString() + "]");
					}

				}
			}

		}
		LOGGER.info("<<--tratarNegocio_BRASIL");
	}
}