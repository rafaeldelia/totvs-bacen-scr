package br.com.totvs.plugins.bacen;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.totvs.exceptions.ConfigException;
import br.com.totvs.exceptions.InfraException;
import br.com.totvs.exceptions.LayoutException;
import br.com.totvs.plugins.PluginConfig;
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

	private static String userSisbacen = "";
	
	private static String passwordSisbacen = "";

	public HashMap<String, Object> execute(Map<String, Object> hashIn) throws InfraException, LayoutException, ConfigException {

		HashMap<String, Object> hashOut = new HashMap<String, Object>();

		try {
			
			LOGGER.info(">> BacenScr.execute()");
			
			BacenUtil.validarParametrosEntrada(hashIn);

			HashMap<String, Object> configuracoesPlugin = PluginConfig.getDetails("bacen-scr");

			this.setTrustStore(configuracoesPlugin);

			this.validarAutenticacaoExecucao(hashIn);
			
			ResumoDoCliente retornoResumo = this.consumirWebServiceBacen(hashIn, configuracoesPlugin);
			
			BacenUtil.popularXMLEnvioRetorno(hashIn, hashOut, retornoResumo);

			this.tratarNegocio(hashOut, retornoResumo);
			
			LOGGER.info("<< BacenScr.execute()");
			
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
	 * @throws LayoutException 
	 */
	private void setTrustStore(HashMap<String, Object> configuracoesPlugin) throws ConfigException, FileNotFoundException, LayoutException {
				
		String trustStore = (String) configuracoesPlugin.get(Constants.BACENSCR_TRUSTSTORE);
		
		if (trustStore == null || trustStore.isEmpty()) {
			throw new ConfigException("Nao encontrou variavel '" + Constants.BACENSCR_TRUSTSTORE + " no aquivo JSON'");
		}
		
		trustStore = "" + "/acessos/sisbacenscr" + "/resources/" + trustStore;
		
		LOGGER.info("trustStore -->>" + trustStore);

		File fileTesteTrustStore = new File(trustStore);
		if (!fileTesteTrustStore.exists()) {
			throw new ConfigException("O arquivo Truststore nao existe -->>" + trustStore);
		}

		String trustStoreType = (String) configuracoesPlugin.get(Constants.BACENSCR_TRUSTSTORETYPE);
		
		if (trustStoreType == null || trustStoreType.isEmpty()) {
			throw new ConfigException("Nao encontrou variavel '" + Constants.BACENSCR_TRUSTSTORETYPE + " no aquivo JSON'");
		}
		
		LOGGER.info("trustStoreType -->>" + trustStoreType);

		String trustStorePassword = (String) configuracoesPlugin.get(Constants.BACENSCR_TRUSTSTOREPASSWORD);
		
		if (trustStorePassword == null || trustStorePassword.isEmpty()) {
			throw new ConfigException("Nao encontrou variavel '" + Constants.BACENSCR_TRUSTSTOREPASSWORD + "no aquivo JSON'");
		}
		
		LOGGER.info("trustStorePassword -->>" + trustStorePassword);

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

	private ResumoDoCliente consumirWebServiceBacen(Map<String, Object> hashIn, HashMap<String, Object> configuracoesPlugin)
			throws MalformedURLException, ConfigException, BCServicoException_Exception {
		LOGGER.info("-->> consumirWebServiceBacen");
		URL wsdlURL = new URL(BacenUtil.consultarURLBacen(configuracoesPlugin));
		Scr2WebService ss = new Scr2WebService(wsdlURL, Constants.SERVICE_NAME);
		IScr2WebService port = ss.getScr2WebPort();
		String cpfCnpj = (String) hashIn.get("CPFCNPJ");
		String tipoCliente = (String) hashIn.get("TIPOCLIENTE");
		String dataBase = (String) hashIn.get("DATABASE");
		String autorizacao = (String) hashIn.get("AUTORIZACAO");
		LOGGER.info("<<-- consumirWebServiceBacen");
		return port.getResumoDoCliente(cpfCnpj, tipoCliente, dataBase, autorizacao);
	}

	/**
	 * Montar hashOut
	 * @author rsdelia
	 * @param hashOut
	 * @param resumoDoCliente
	 * @throws LayoutException
	 */
	private void tratarNegocio(HashMap<String, Object> hashOut, ResumoDoCliente resumoDoCliente) throws LayoutException {

		hashOut.put("DATABASECONSULTADA", resumoDoCliente.getDataBaseConsultada());
		LOGGER.info("DATABASECONSULTADA-->>" + resumoDoCliente.getDataBaseConsultada());
		LOGGER.info("TIPODOCLIENTE-->>" + resumoDoCliente.getTipoDoCliente());
		LOGGER.info("CODIGODOCLIENTE-->>" + resumoDoCliente.getCodigoDoCliente());
		LOGGER.info("CNPJDAIFSOLICITANTE-->>" + resumoDoCliente.getCnpjDaIFSolicitante());
		LOGGER.info("COOBRIGACAOASSUMIDA-->>" + resumoDoCliente.getCoobrigacaoAssumida().doubleValue());
		LOGGER.info("COOBRIGACAORECEBIDA-->>" + resumoDoCliente.getCoobrigacaoRecebida().doubleValue());
		LOGGER.info("DATAINICIORELACIONAMENTO-->>" + resumoDoCliente.getDataInicioRelacionamento());
		LOGGER.info("PERCENTUALDOCUMENTOSPROCESSADOS-->>" + resumoDoCliente.getPercentualDocumentosProcessados());
		LOGGER.info("PERCENTUALVOLUMEPROCESSADO-->>" + resumoDoCliente.getPercentualVolumeProcessado());
		LOGGER.info("QUANTIDADEDEINSTITUICOES-->>" + resumoDoCliente.getQuantidadeDeInstituicoes());
		LOGGER.info("QUANTIDADEDEOPERACOES-->>" + resumoDoCliente.getQuantidadeDeOperacoes());
		LOGGER.info("QUANTIDADEOPERACOESDISCORDANCIA-->>" + resumoDoCliente.getQuantidadeOperacoesDiscordancia());
		LOGGER.info("QUANTIDADEOPERACOESSUBJUDICE-->>" + resumoDoCliente.getQuantidadeOperacoesSubJudice());
		LOGGER.info("RESPONSABILIDADETOTALDISCORDANCIA-->>" + resumoDoCliente.getResponsabilidadeTotalDiscordancia().doubleValue());
		LOGGER.info("RESPONSABILIDADETOTALSUBJUDICE-->>" + resumoDoCliente.getResponsabilidadeTotalSubJudice().doubleValue());
		LOGGER.info("RISCOINDIRETOVENDOR-->>" + resumoDoCliente.getRiscoIndiretoVendor().doubleValue());

		List<BcMsgRetorno> listaMensagensBcMsgRetorno = resumoDoCliente.getListaDeMensagensDeValidacao();

		Boolean erroBacen = Boolean.FALSE;
		
		if (listaMensagensBcMsgRetorno.size() > 0) {
			LOGGER.info("Lista de Mensagens de Validacao (" + resumoDoCliente.getListaDeMensagensDeValidacao().size() + "):");
			erroBacen = Boolean.TRUE;
		} else {
			LOGGER.info("Nao ha Mensagens de Validacao");
		}

		String mensagensBacen = "";

		for (BcMsgRetorno mensagem : listaMensagensBcMsgRetorno) {
			mensagensBacen = mensagensBacen + mensagem.getCodigo() + "-" + mensagem.getMensagem() + "/";
			LOGGER.info("Codigo-->>" + mensagem.getCodigo());
			LOGGER.info("Mensagem-->" + mensagem.getMensagem());
		}

		// Resumo das operacoes
		List<ResumoDaOperacao> listaDeResumoDasOperacoes = resumoDoCliente.getListaDeResumoDasOperacoes();

		for (ResumoDaOperacao resumoOperacao : listaDeResumoDasOperacoes) {
			LOGGER.info("Modalidade-->>" + resumoOperacao.getModalidade());
			LOGGER.info("Varivacao Cambial-->>" + resumoOperacao.getVariacaoCambial());

			for (ResumoDoVencimento resumoDoVencimento : resumoOperacao.getListaDeVencimentos()) {
				LOGGER.info("Codigo Vencimento-->>" + resumoDoVencimento.getCodigoVencimento());
				LOGGER.info("Valor Vencimento-->> " + resumoDoVencimento.getValorVencimento());
			}
		}

		// Se houve mensagem de erro
		if (erroBacen) {
			throw new LayoutException(mensagensBacen);
		}
	}
}