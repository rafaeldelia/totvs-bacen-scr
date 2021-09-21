package br.com.totvs.plugins.bacen;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import br.com.totvs.exceptions.ConfigException;
import br.com.totvs.exceptions.InfraException;
import br.com.totvs.exceptions.LayoutException;
import br.com.totvs.plugins.PluginInterface;
import br.com.totvs.plugins.bacen.constants.Constants;
import br.com.totvs.plugins.bacen.exceptions.ResourceErroAbstract;
import br.com.totvs.plugins.bacen.utils.BacenUtil;
import br.gov.bcb.scr2.operacional.webservice.BCServicoException_Exception;
import br.gov.bcb.scr2.operacional.webservice.BcMsgRetorno;
import br.gov.bcb.scr2.operacional.webservice.IScr2WebService;
import br.gov.bcb.scr2.operacional.webservice.ResumoDaOperacao;
import br.gov.bcb.scr2.operacional.webservice.ResumoDoCliente;
import br.gov.bcb.scr2.operacional.webservice.ResumoDoVencimento;
import br.gov.bcb.scr2.operacional.webservice.Scr2WebService;

/**
 * Classe de acesso ao web service Bacen SCR
 * 
 */
public class BacenScr extends ResourceErroAbstract implements PluginInterface {

	final Logger LOGGER = Logger.getLogger("BacenScr");

	private static String erroMsg = "";
	
	private static String userSisbacen = "";
	
	private static String passwordSisbacen = "";

	public HashMap<String, Object> execute(Map<String, Object> hashIn) throws InfraException, LayoutException, ConfigException {

		HashMap<String, Object> hashOut = new HashMap<String, Object>();

		Properties pArquivo = new Properties();

		try {
			LOGGER.debug(">> BacenScr.execute()");
			
			BacenUtil.validarParametrosEntrada(hashIn);

			this.inicializarPropriedades(pArquivo);

			this.validarAutenticacaoExecucao(hashIn);
			
			ResumoDoCliente retornoResumo = this.consumirWebServiceBacen(hashIn, pArquivo);
			
			BacenUtil.popularXMLEnvioRetorno(hashIn, hashOut, retornoResumo);

			this.tratarNegocio(hashOut, retornoResumo);
			
			LOGGER.debug("<< BacenScr.execute()");		
		} catch (LayoutException | BCServicoException_Exception | MalformedURLException | FileNotFoundException e) {
			tratarException(e);
		}
		return hashOut;
	}

	/**
	 * Inicializar propriedades
	 * @author rsdelia
	 * @param pArquivo
	 * @throws ConfigException
	 * @throws FileNotFoundException
	 */
	private void inicializarPropriedades(Properties pArquivo) throws ConfigException, FileNotFoundException {
		// essa variavel deve conter o PATH para todo o processo do acesso
		String intelllectorDataDir = System.getProperty(Constants.INTELLECTOR_DATADIR);

		if (intelllectorDataDir == null || intelllectorDataDir.isEmpty()) {
			erroMsg = "Nao encontrou variavel de ambiente '" + Constants.INTELLECTOR_DATADIR + "'";
			LOGGER.error(erroMsg);
			// O throw vai para a tela.
			throw new ConfigException(erroMsg);
		}

		LOGGER.debug(Constants.INTELLECTOR_DATADIR + " -->>" + intelllectorDataDir);

		// arquivo de properties (bacenscr.properties)
		String accessProperties = intelllectorDataDir + "/acessos/sisbacenscr" + "/resources/sisbacenscr.properties";

		LOGGER.debug("accessProperties -->>" + accessProperties);

		// inputstream para o arquivo de propriedades
		InputStream iProp = new BufferedInputStream(new FileInputStream(accessProperties));

		/**
		 * Abrir e tratar o arquivo de properties... Obs.: aqui teriamos a opcao de chamar metodo para a montagem dess string; seria trocar
		 * seis por meia duzia; se algum dsv organizado supor isso, que o implemente (tantan)
		 */
		// carrega o arquivo de propriedades
		try {
			pArquivo.load(iProp);
		} catch (IOException e) {
			erroMsg = "Nao foi possivel abrir properties: [" + accessProperties + "]";
			LOGGER.error(erroMsg);
			LOGGER.error(e);
			throw new ConfigException(erroMsg);
		} finally {
			try {
				iProp.close();
			} catch (IOException e) {
				erroMsg = "Nao foi possivel fechar o arquivo de propriedade: [" + accessProperties + "]";
				LOGGER.error(erroMsg);
				LOGGER.error(e);
				throw new ConfigException(erroMsg);
			}
		}

		// Propriedades da TrustStore
		String trustStore = pArquivo.getProperty(Constants.BACENSCR_TRUSTSTORE);
		if (trustStore == null || trustStore.isEmpty()) {
			erroMsg = "Nao encontrou variavel '" + Constants.BACENSCR_TRUSTSTORE + " no aquivo de propriedades'";
			LOGGER.error(erroMsg);
			throw new ConfigException(erroMsg);
		}
		trustStore = intelllectorDataDir + "/acessos/sisbacenscr" + "/resources/" + trustStore;
		LOGGER.debug("trustStore -->>" + trustStore);

		// Testar se existe a Trustsore
		File fileTesteTrustStore = new File(trustStore);
		if (!fileTesteTrustStore.exists()) {
			erroMsg = "O arquivo Truststore nao existe -->>" + trustStore;
			LOGGER.error(erroMsg);
			throw new ConfigException(erroMsg);
		}

		String trustStoreType = pArquivo.getProperty(Constants.BACENSCR_TRUSTSTORETYPE);
		if (trustStoreType == null || trustStoreType.isEmpty()) {
			erroMsg = "Nao encontrou variavel '" + Constants.BACENSCR_TRUSTSTORETYPE + " no aquivo de propriedades'";
			LOGGER.error(erroMsg);
			throw new ConfigException(erroMsg);
		}
		LOGGER.debug("trustStoreType -->>" + trustStoreType);

		String trustStorePassword = pArquivo.getProperty(Constants.BACENSCR_TRUSTSTOREPASSWORD);
		if (trustStorePassword == null || trustStorePassword.isEmpty()) {
			erroMsg = "Nao encontrou variavel '" + Constants.BACENSCR_TRUSTSTOREPASSWORD + "no aquivo de propriedades'";
			LOGGER.error(erroMsg);
			throw new ConfigException(erroMsg);
		}
		LOGGER.debug("trustStorePassword -->>" + trustStorePassword);

		System.clearProperty("javax.net.ssl.keyStore");
		System.clearProperty("javax.net.ssl.keyStorePassword");
		System.clearProperty("javax.net.ssl.trustStore");

		System.setProperty("javax.net.ssl.trustStoreType", trustStoreType);
		System.setProperty("javax.net.ssl.trustStore", trustStore);
		System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
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

	private ResumoDoCliente consumirWebServiceBacen(Map<String, Object> hashIn, Properties pArquivo)
			throws MalformedURLException, ConfigException, BCServicoException_Exception {
		LOGGER.debug("-->> consumirWebServiceBacen");
		URL wsdlURL = new URL(BacenUtil.consultarURLBacen(pArquivo));
		Scr2WebService ss = new Scr2WebService(wsdlURL, Constants.SERVICE_NAME);
		IScr2WebService port = ss.getScr2WebPort();
		String cpfCnpj = (String) hashIn.get("CPFCNPJ");
		String tipoCliente = (String) hashIn.get("TIPOCLIENTE");
		String dataBase = (String) hashIn.get("DATABASE");
		String autorizacao = (String) hashIn.get("AUTORIZACAO");
		LOGGER.debug("<<-- consumirWebServiceBacen");
		return port.getResumoDoCliente(cpfCnpj, tipoCliente, dataBase, autorizacao);
	}

	/**
	 * @param hashOut
	 * @param resumoDoCliente
	 * @throws LayoutException
	 */
	private void tratarNegocio(HashMap<String, Object> hashOut, ResumoDoCliente resumoDoCliente) throws LayoutException {
		// Montar hashOut
		hashOut.put("DATABASECONSULTADA", resumoDoCliente.getDataBaseConsultada());
		LOGGER.debug("DATABASECONSULTADA-->>" + resumoDoCliente.getDataBaseConsultada());

		hashOut.put("TIPODOCLIENTE", resumoDoCliente.getTipoDoCliente());
		LOGGER.debug("TIPODOCLIENTE-->>" + resumoDoCliente.getTipoDoCliente());

		hashOut.put("CODIGODOCLIENTE", resumoDoCliente.getCodigoDoCliente());
		LOGGER.debug("CODIGODOCLIENTE-->>" + resumoDoCliente.getCodigoDoCliente());

		hashOut.put("CNPJDAIFSOLICITANTE", resumoDoCliente.getCnpjDaIFSolicitante());
		LOGGER.debug("CNPJDAIFSOLICITANTE-->>" + resumoDoCliente.getCnpjDaIFSolicitante());

		hashOut.put("COOBRIGACAOASSUMIDA", resumoDoCliente.getCoobrigacaoAssumida().doubleValue());
		LOGGER.debug("COOBRIGACAOASSUMIDA-->>" + resumoDoCliente.getCoobrigacaoAssumida().doubleValue());

		hashOut.put("COOBRIGACAORECEBIDA", resumoDoCliente.getCoobrigacaoRecebida().doubleValue());
		LOGGER.debug("COOBRIGACAORECEBIDA-->>" + resumoDoCliente.getCoobrigacaoRecebida().doubleValue());

		String DataInicio = resumoDoCliente.getDataInicioRelacionamento();

		hashOut.put("DATAINICIORELACIONAMENTO", DataInicio);
		LOGGER.debug("DATAINICIORELACIONAMENTO-->>" + resumoDoCliente.getDataInicioRelacionamento());

		hashOut.put("PERCENTUALDOCUMENTOSPROCESSADOS", resumoDoCliente.getPercentualDocumentosProcessados());
		LOGGER.debug("PERCENTUALDOCUMENTOSPROCESSADOS-->>" + resumoDoCliente.getPercentualDocumentosProcessados());

		hashOut.put("PERCENTUALVOLUMEPROCESSADO", resumoDoCliente.getPercentualVolumeProcessado());
		LOGGER.debug("PERCENTUALVOLUMEPROCESSADO-->>" + resumoDoCliente.getPercentualVolumeProcessado());

		hashOut.put("QUANTIDADEDEINSTITUICOES", resumoDoCliente.getQuantidadeDeInstituicoes());
		LOGGER.debug("QUANTIDADEDEINSTITUICOES-->>" + resumoDoCliente.getQuantidadeDeInstituicoes());

		hashOut.put("QUANTIDADEDEOPERACOES", resumoDoCliente.getQuantidadeDeOperacoes());
		LOGGER.debug("QUANTIDADEDEOPERACOES-->>" + resumoDoCliente.getQuantidadeDeOperacoes());

		hashOut.put("QUANTIDADEOPERACOESDISCORDANCIA", resumoDoCliente.getQuantidadeOperacoesDiscordancia());
		LOGGER.debug("QUANTIDADEOPERACOESDISCORDANCIA-->>" + resumoDoCliente.getQuantidadeOperacoesDiscordancia());

		hashOut.put("QUANTIDADEOPERACOESSUBJUDICE", resumoDoCliente.getQuantidadeOperacoesSubJudice());
		LOGGER.debug("QUANTIDADEOPERACOESSUBJUDICE-->>" + resumoDoCliente.getQuantidadeOperacoesSubJudice());

		hashOut.put("RESPONSABILIDADETOTALDISCORDANCIA", resumoDoCliente.getResponsabilidadeTotalDiscordancia().doubleValue());
		LOGGER.debug("RESPONSABILIDADETOTALDISCORDANCIA-->>" + resumoDoCliente.getResponsabilidadeTotalDiscordancia().doubleValue());

		hashOut.put("RESPONSABILIDADETOTALSUBJUDICE", resumoDoCliente.getResponsabilidadeTotalSubJudice().doubleValue());
		LOGGER.debug("RESPONSABILIDADETOTALSUBJUDICE-->>" + resumoDoCliente.getResponsabilidadeTotalSubJudice().doubleValue());

		hashOut.put("RISCOINDIRETOVENDOR", resumoDoCliente.getRiscoIndiretoVendor().doubleValue());
		LOGGER.debug("RISCOINDIRETOVENDOR-->>" + resumoDoCliente.getRiscoIndiretoVendor().doubleValue());

		List<BcMsgRetorno> listaMensagensBcMsgRetorno = resumoDoCliente.getListaDeMensagensDeValidacao();

		Boolean erroBacen = Boolean.FALSE;
		
		if (listaMensagensBcMsgRetorno.size() > 0) {
			LOGGER.debug("Lista de Mensagens de Validacao (" + resumoDoCliente.getListaDeMensagensDeValidacao().size() + "):");
			erroBacen = Boolean.TRUE;
		} else {
			LOGGER.debug("Nao ha Mensagens de Validacao");
		}

		String mensagensBacen = "";

		for (BcMsgRetorno mensagem : listaMensagensBcMsgRetorno) {
			mensagensBacen = mensagensBacen + mensagem.getCodigo() + "-" + mensagem.getMensagem() + "/";
			LOGGER.debug("Codigo-->>" + mensagem.getCodigo());
			LOGGER.debug("Mensagem-->" + mensagem.getMensagem());
		}

		// Resumo das operacoes
		List<ResumoDaOperacao> listaDeResumoDasOperacoes = resumoDoCliente.getListaDeResumoDasOperacoes();

		for (ResumoDaOperacao resumoOperacao : listaDeResumoDasOperacoes) {
			LOGGER.debug("Modalidade-->>" + resumoOperacao.getModalidade());
			LOGGER.debug("Varivacao Cambial-->>" + resumoOperacao.getVariacaoCambial());

			for (ResumoDoVencimento resumoDoVencimento : resumoOperacao.getListaDeVencimentos()) {
				LOGGER.debug("Codigo Vencimento-->>" + resumoDoVencimento.getCodigoVencimento());
				LOGGER.debug("Valor Vencimento-->> " + resumoDoVencimento.getValorVencimento());
			}
		}

		// Se houve mensagem de erro
		if (erroBacen) {
			throw new LayoutException(mensagensBacen);
		}
	}
}