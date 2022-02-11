package br.com.totvs.plugins.bacen.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import br.com.totvs.exceptions.ConfigException;
import br.com.totvs.exceptions.LayoutException;
import br.com.totvs.plugins.bacen.Response;
import br.com.totvs.plugins.bacen.constants.Constants;
import br.gov.bcb.scr2.operacional.webservice.ResumoDaOperacao;
import br.gov.bcb.scr2.operacional.webservice.ResumoDoVencimento;

/**
 * Classe utilitária do Bacen SCR
 * 
 * @author rsdelia
 *
 */
public final class BacenUtil {

	private final static Logger LOGGER = Logger.getLogger("BacenUtil");

	public BacenUtil() {
		super();
	}

	/**
	 * Valido se as informações de entrada foram enviadas Todas as informações abaixo são obrigatórias para este processo
	 * 
	 * @author rsdelia
	 * @param hashIn
	 * @throws LayoutException
	 */
	public static void validarParametrosEntrada(Map<String, Object> hashIn) throws LayoutException {
		LOGGER.debug("-->>validarParametrosEntrada");
		if (hashIn != null) {
			String cpfCnpj = (String) hashIn.get("CPFCNPJ");
			String tipoCliente = (String) hashIn.get("TIPOCLIENTE");
			String dataBase = (String) hashIn.get("DATABASE");
			String autorizacao = (String) hashIn.get("AUTORIZACAO");
			String userSisbacen = (String) hashIn.get("USUARIOSISBACEN");
			String passwordSisbacen = (String) hashIn.get("SENHASISBACEN");
			String cnpjIF = (String) hashIn.get("CNPJIF");
			LOGGER.debug("cpfCnpj [" + cpfCnpj + "]");
			LOGGER.debug("tipoCliente [" + tipoCliente + "]");
			LOGGER.debug("dataBase [" + dataBase + "]");
			LOGGER.debug("autorizacao [" + autorizacao + "]");
			LOGGER.debug("userSisbacen [" + userSisbacen + "]");
			LOGGER.debug("passwordSisbacen [" + passwordSisbacen + "]");
			LOGGER.debug("cnpjIf [" + cnpjIF + "]");

			if (Util.isNullOrEmpty(cpfCnpj)) {
				throw new LayoutException("CPF/CNPJ não informado: campo obrigatório.");
			} else if (Util.isNullOrEmpty(tipoCliente)) {
				throw new LayoutException("Tipo do Cliente não informado: campo obrigatório.");
			} else if (Util.isNullOrEmpty(dataBase)) {
				throw new LayoutException("Data Base não informada: campo obrigatório.");
			} else if (Util.isNullOrEmpty(userSisbacen)) {
				throw new LayoutException("Usuário não informado: campo obrigatório.");
			} else if (Util.isNullOrEmpty(passwordSisbacen)) {
				throw new LayoutException("Senha não informada: campo obrigatório.");
			} else if (Util.isNullOrEmpty(autorizacao)) {
				throw new LayoutException("Autorização não informada: campo obrigatório.");
			} else if (Util.isNullOrEmpty(cnpjIF)) {
				throw new LayoutException("CNPJ da Instituição Financeira não informado: campo obrigatório.");
			}
			LOGGER.debug("<<--validarParametrosEntrada");
		} else {
			LOGGER.debug("<<--validarParametrosEntrada");
			throw new LayoutException("Erro GRAVE: Nenhum campo de entrada foi preenchido.");
		}
	}

	/**
	 * @author rsdelia
	 * @param pArquivo
	 * @return
	 * @throws ConfigException
	 * @see Localizacao do WSDL que pode ser homologacao ou producao
	 */
	public static String consultarURLBacen(Map<String, String> configuracoesPlugin) throws ConfigException {
		LOGGER.debug("-->>consultarURLBacen");
		String surlWsdl = (String) configuracoesPlugin.get(Constants.BACENSCR_WSDL);
		LOGGER.debug("-->>consultarURLBacen");
		if (Util.isNullOrEmpty(surlWsdl)) {
			throw new ConfigException("Nao encontrou variavel '" + Constants.BACENSCR_WSDL + " no aquivo bacen-scr.json'");
		}
		LOGGER.debug("URL Wsdl Bacen [" + surlWsdl + "]");
		LOGGER.debug("<<--consultarURLBacen");
		return surlWsdl;
	}

	/**
	 * popular LPT__PLUGIN_ENVIO
	 * 
	 * @author rsdelia
	 * @param hashIn
	 * @param hashOut
	 *            Transformo os dados de entrada da consulta em XML para armazenar nas tabelas de auditoria
	 */
	public static void popularXMLEnvio(Map<String, Object> hashIn, HashMap<String, Object> hashOut) {
		LOGGER.debug("-->> popularXMLEnvio");
		XStream xStream = new XStream(new DomDriver());
		String cpfCnpj = (String) hashIn.get("CPFCNPJ");
		String tipoCliente = (String) hashIn.get("TIPOCLIENTE");
		String dataBase = (String) hashIn.get("DATABASE");
		String userSisbacen = (String) hashIn.get("USUARIOSISBACEN");
		String passwordSisbacen = (String) hashIn.get("SENHASISBACEN");
		String autorizacao = (String) hashIn.get("AUTORIZACAO");
		String cnpjIf = (String) hashIn.get("CNPJIF");
		HashMap<String, Object> hashRequisicao = new HashMap<String, Object>();
		hashRequisicao.put("codigoDoCliente", cpfCnpj);
		hashRequisicao.put("dataBaseConsultada", dataBase);
		hashRequisicao.put("tipoDoCliente", tipoCliente);
		hashRequisicao.put("autorizacao", autorizacao);
		hashRequisicao.put("userSisbacen", userSisbacen);
		hashRequisicao.put("passwordSisbacen", passwordSisbacen);
		hashRequisicao.put("cnpjIf", cnpjIf);
		xStream.alias("map", java.util.Map.class);
		String xmlChamadaSisbacen = xStream.toXML(hashRequisicao);
		LOGGER.debug(xmlChamadaSisbacen);
		hashOut.put("LPT__PLUGIN_ENVIO", xmlChamadaSisbacen);
		LOGGER.debug("<<--popularXMLEnvio");
	}

	/**
	 * popular LPT__PLUGIN_RETORNO
	 * 
	 * @author rsdelia
	 * @param hashOut
	 * @param resumoDoCliente
	 *            Transformo os dados de retorno da consulta ao web service
	 */
	public static void popularXMLEnvioRetorno(HashMap<String, Object> hashOut, Response resumoDoCliente) {
		LOGGER.debug("-->> popularXMLEnvioRetorno");
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("map", java.util.Map.class);
		String xmlRetornoSisbacen = xStream.toXML(resumoDoCliente);
		LOGGER.debug(xmlRetornoSisbacen);
		hashOut.put("LPT__PLUGIN_RETORNO", xmlRetornoSisbacen);
		LOGGER.debug("<<--popularXMLEnvioRetorno");
	}

	/**
	 * 
	 * @param userSisbacen
	 * @param passwordSisbacen
	 * @return
	 */
	public static String concatenarUsuarioSenha(String userSisbacen, String passwordSisbacen) {
		StringBuilder sb = new StringBuilder();
		sb.append(userSisbacen);
		sb.append(":");
		sb.append(passwordSisbacen);
		return sb.toString();
	}

	/**
	 * @return
	 */
	public static String getToken(String userSisbacen, String passwordSisbacen) {
		return Base64.getEncoder().encodeToString(BacenUtil.concatenarUsuarioSenha(userSisbacen, passwordSisbacen).getBytes());
	}

	/**
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static String tratarXMLRespostaBacen(InputStream inputStream) throws IOException {
		String xmlResposta = Constants.EMPTY;
		if (null != inputStream) {
			xmlResposta = new BufferedReader(new InputStreamReader(inputStream)).lines().parallel().collect(Collectors.joining("\n"));
		}
		Pattern p2 = Pattern.compile("<return>(.*?)</return>");
		Matcher m2 = p2.matcher(xmlResposta);
		while (m2.find()) {
			xmlResposta = "<return>" + m2.group(1) + "</return>";
		}
		return xmlResposta;
	}

	/**
	 * @author rsdelia
	 * @param hashIn
	 * @return String
	 */
	public static String popularParametroIn(Map<String, Object> hashIn) {
		String cpfCnpj = (String) hashIn.get("CPFCNPJ");
		String tipoCliente = (String) hashIn.get("TIPOCLIENTE");
		String dataBase = (String) hashIn.get("DATABASE");
		String autorizacao = (String) hashIn.get("AUTORIZACAO");
		String cnpjIF = (String) hashIn.get("CNPJIF");
		return "?codCliente=" + cpfCnpj + "&tpCliente=" + tipoCliente + "&dataBase=" + dataBase + "&autorizacao=" + autorizacao + "&cnpjIF="
				+ cnpjIF;
	}

	/**
	 * @param responseCode
	 * @throws ConfigException
	 */
	public static void validarAuthResponse(int responseCode) throws ConfigException {
		if (responseCode == 401) {
			throw new ConfigException(Constants.ERROR_CODE_401);
		}
	}

	/**
	 * Retorna TRUE se existir valor a vencer maior que ZERO e FALSE se não existir
	 * 
	 * @author rsdelia
	 * @param hashOut
	 * @return boolean
	 */
	public static boolean isExisteValorAVencer(HashMap<String, Object> hashOut) {
		Double creditoAVencerAte30Dias = (Double) hashOut.get("VLR_A_VENCER_ATE_30_DIAS");
		Double creditoAVencerDe31A60Dias = (Double) hashOut.get("VLR_A_VENCER_DE_31_A_60_DIAS");
		Double creditoAVencerDe61A90Dias = (Double) hashOut.get("VLR_A_VENCER_DE_61_A_90_DIAS");
		Double creditoAVencerDe91A180Dias = (Double) hashOut.get("VLR_A_VENCER_DE_91_A_180_DIAS");
		Double creditoAVencerDe181A360Dias = (Double) hashOut.get("VLR_A_VENCER_DE_181_A_360_DIAS");
		Double creditoAVencerDe361A720Dias = (Double) hashOut.get("VLR_A_VENCER_DE_361_A_720_DIAS");
		Double creditoAVencerDe721A1080Dias = (Double) hashOut.get("VLR_A_VENCER_DE_721_A_1080_DIAS");
		Double creditoAVencerDe1081A1440Dias = (Double) hashOut.get("VLR_A_VENCER_DE_1081_A_1440_DIAS");
		Double creditoAVencerDe1441A1800Dias = (Double) hashOut.get("VLR_A_VENCER_DE_1441_A_1800_DIAS");
		Double creditoAVencerDe1801A5400Dias = (Double) hashOut.get("VLR_A_VENCER_DE_1801_A_5400_DIAS");
		Double creditoAVencerAcima5400Dias = (Double) hashOut.get("VLR_A_VENCER_ACIMA_5400_DIAS");
		Double creditoAVencerPrazoIndeterminado = (Double) hashOut.get("VLR_A_VENCER_COM_PRAZO_INDETERMINADO");

		return (creditoAVencerAte30Dias.compareTo(Constants.ZERO_DOUBLE) > 0
				|| creditoAVencerDe31A60Dias.compareTo(Constants.ZERO_DOUBLE) > 0
				|| creditoAVencerDe61A90Dias.compareTo(Constants.ZERO_DOUBLE) > 0
				|| creditoAVencerDe91A180Dias.compareTo(Constants.ZERO_DOUBLE) > 0
				|| creditoAVencerDe181A360Dias.compareTo(Constants.ZERO_DOUBLE) > 0
				|| creditoAVencerDe361A720Dias.compareTo(Constants.ZERO_DOUBLE) > 0
				|| creditoAVencerDe721A1080Dias.compareTo(Constants.ZERO_DOUBLE) > 0
				|| creditoAVencerDe1081A1440Dias.compareTo(Constants.ZERO_DOUBLE) > 0
				|| creditoAVencerDe1441A1800Dias.compareTo(Constants.ZERO_DOUBLE) > 0
				|| creditoAVencerDe1801A5400Dias.compareTo(Constants.ZERO_DOUBLE) > 0
				|| creditoAVencerAcima5400Dias.compareTo(Constants.ZERO_DOUBLE) > 0
				|| creditoAVencerPrazoIndeterminado.compareTo(Constants.ZERO_DOUBLE) > 0);
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
	 */
	public static void popularHashSaida(HashMap<String, Object> hashOut, Response resumoDoCliente) {

		// limites de credito
		BigDecimal limiteCreditoVencimentoAte360Dias = BigDecimal.ZERO;
		BigDecimal limiteCreditoVencimentoAcima360Dias = BigDecimal.ZERO;

		// credito a liberar
		BigDecimal creditoLiberarAte360Dias = BigDecimal.ZERO;
		BigDecimal creditoLiberarAcima360Dias = BigDecimal.ZERO;

		// creditos a vencer
		BigDecimal creditoAVencerAte30Dias = BigDecimal.ZERO;
		BigDecimal creditoAVencerDe31A60Dias = BigDecimal.ZERO;
		BigDecimal creditoAVencerDe61A90Dias = BigDecimal.ZERO;
		BigDecimal creditoAVencerDe91A180Dias = BigDecimal.ZERO;
		BigDecimal creditoAVencerDe181A360Dias = BigDecimal.ZERO;
		BigDecimal creditoAVencerDe361A720Dias = BigDecimal.ZERO;
		BigDecimal creditoAVencerDe721A1080Dias = BigDecimal.ZERO;
		BigDecimal creditoAVencerDe1081A1440Dias = BigDecimal.ZERO;
		BigDecimal creditoAVencerDe1441A1800Dias = BigDecimal.ZERO;
		BigDecimal creditoAVencerDe1801A5400Dias = BigDecimal.ZERO;
		BigDecimal creditoAVencerAcima5400Dias = BigDecimal.ZERO;
		BigDecimal creditoAVencerPrazoIndeterminado = BigDecimal.ZERO;

		// creditos vencidos
		BigDecimal creditoVencidosDe1A14Dias = BigDecimal.ZERO;
		BigDecimal creditoVencidosDe15A30Dias = BigDecimal.ZERO;
		BigDecimal creditoVencidosDe31A60Dias = BigDecimal.ZERO;
		BigDecimal creditoVencidosDe61A90Dias = BigDecimal.ZERO;
		BigDecimal creditoVencidosDe91A120Dias = BigDecimal.ZERO;
		BigDecimal creditoVencidosDe121A150Dias = BigDecimal.ZERO;
		BigDecimal creditoVencidosDe151A180Dias = BigDecimal.ZERO;
		BigDecimal creditoVencidosDe181A240Dias = BigDecimal.ZERO;
		BigDecimal creditoVencidosDe241A300Dias = BigDecimal.ZERO;
		BigDecimal creditoVencidosDe301A360Dias = BigDecimal.ZERO;
		BigDecimal creditoVencidosDe361A540Dias = BigDecimal.ZERO;
		BigDecimal creditoVencidosAcima540Dias = BigDecimal.ZERO;

		// prejuizo
		BigDecimal prejuizoAte12Meses = BigDecimal.ZERO;
		BigDecimal prejuizoMaisQue12Ate48Meses = BigDecimal.ZERO;
		BigDecimal prejuizoMaisQue48Meses = BigDecimal.ZERO;

		// operacoes com vencimentos
		List<ResumoDaOperacao> operacoes = resumoDoCliente.getListaDeResumoDasOperacoes();

		if (!Util.isNullOrEmpty(operacoes)) {
			for (ResumoDaOperacao operacao : operacoes) {
				List<ResumoDoVencimento> vencimentos = operacao.getListaDeVencimentos();
				if (!Util.isNullOrEmpty(vencimentos)) {
					hashOut.put("EXISTE_VALORES_DE_VENCIMENTOS", Boolean.TRUE);
					for (ResumoDoVencimento vencimento : vencimentos) {
						// limite de credito
						if ("v20".equals(vencimento.getCodigoVencimento())) {
							limiteCreditoVencimentoAte360Dias = limiteCreditoVencimentoAte360Dias.add(vencimento.getValorVencimento());
						} else if ("v40".equals(vencimento.getCodigoVencimento())) {
							limiteCreditoVencimentoAcima360Dias = limiteCreditoVencimentoAcima360Dias.add(vencimento.getValorVencimento());
						} else if ("v60".equals(vencimento.getCodigoVencimento())) { // creditos a liberar
							creditoLiberarAte360Dias = creditoLiberarAte360Dias.add(vencimento.getValorVencimento());
						} else if ("v80".equals(vencimento.getCodigoVencimento())) {
							creditoLiberarAcima360Dias = creditoLiberarAcima360Dias.add(vencimento.getValorVencimento());
						} else if ("v110".equals(vencimento.getCodigoVencimento())) { // creditos a vencer
							creditoAVencerAte30Dias = creditoAVencerAte30Dias.add(vencimento.getValorVencimento());
						} else if ("v120".equals(vencimento.getCodigoVencimento())) {
							creditoAVencerDe31A60Dias = creditoAVencerDe31A60Dias.add(vencimento.getValorVencimento());
						} else if ("v130".equals(vencimento.getCodigoVencimento())) {
							creditoAVencerDe61A90Dias = creditoAVencerDe61A90Dias.add(vencimento.getValorVencimento());
						} else if ("v140".equals(vencimento.getCodigoVencimento())) {
							creditoAVencerDe91A180Dias = creditoAVencerDe91A180Dias.add(vencimento.getValorVencimento());
						} else if ("v150".equals(vencimento.getCodigoVencimento())) {
							creditoAVencerDe181A360Dias = creditoAVencerDe181A360Dias.add(vencimento.getValorVencimento());
						} else if ("v160".equals(vencimento.getCodigoVencimento())) {
							creditoAVencerDe361A720Dias = creditoAVencerDe361A720Dias.add(vencimento.getValorVencimento());
						} else if ("v165".equals(vencimento.getCodigoVencimento())) {
							creditoAVencerDe721A1080Dias = creditoAVencerDe721A1080Dias.add(vencimento.getValorVencimento());
						} else if ("v170".equals(vencimento.getCodigoVencimento())) {
							creditoAVencerDe1081A1440Dias = creditoAVencerDe1081A1440Dias.add(vencimento.getValorVencimento());
						} else if ("v175".equals(vencimento.getCodigoVencimento())) {
							creditoAVencerDe1441A1800Dias = creditoAVencerDe1441A1800Dias.add(vencimento.getValorVencimento());
						} else if ("v180".equals(vencimento.getCodigoVencimento())) {
							creditoAVencerDe1801A5400Dias = creditoAVencerDe1801A5400Dias.add(vencimento.getValorVencimento());
						} else if ("v190".equals(vencimento.getCodigoVencimento())) {
							creditoAVencerAcima5400Dias = creditoAVencerAcima5400Dias.add(vencimento.getValorVencimento());
						} else if ("v199".equals(vencimento.getCodigoVencimento())) {
							creditoAVencerPrazoIndeterminado = creditoAVencerPrazoIndeterminado.add(vencimento.getValorVencimento());
						} else if ("v205".equals(vencimento.getCodigoVencimento())) { // creditos vencidos
							creditoVencidosDe1A14Dias = creditoVencidosDe1A14Dias.add(vencimento.getValorVencimento());
						} else if ("v210".equals(vencimento.getCodigoVencimento())) {
							creditoVencidosDe15A30Dias = creditoVencidosDe15A30Dias.add(vencimento.getValorVencimento());
						} else if ("v220".equals(vencimento.getCodigoVencimento())) {
							creditoVencidosDe31A60Dias = creditoVencidosDe31A60Dias.add(vencimento.getValorVencimento());
						} else if ("v230".equals(vencimento.getCodigoVencimento())) {
							creditoVencidosDe61A90Dias = creditoVencidosDe61A90Dias.add(vencimento.getValorVencimento());
						} else if ("v240".equals(vencimento.getCodigoVencimento())) {
							creditoVencidosDe91A120Dias = creditoVencidosDe91A120Dias.add(vencimento.getValorVencimento());
						} else if ("v245".equals(vencimento.getCodigoVencimento())) {
							creditoVencidosDe121A150Dias = creditoVencidosDe121A150Dias.add(vencimento.getValorVencimento());
						} else if ("v250".equals(vencimento.getCodigoVencimento())) {
							creditoVencidosDe151A180Dias = creditoVencidosDe151A180Dias.add(vencimento.getValorVencimento());
						} else if ("v255".equals(vencimento.getCodigoVencimento())) {
							creditoVencidosDe181A240Dias = creditoVencidosDe181A240Dias.add(vencimento.getValorVencimento());
						} else if ("v260".equals(vencimento.getCodigoVencimento())) {
							creditoVencidosDe241A300Dias = creditoVencidosDe241A300Dias.add(vencimento.getValorVencimento());
						} else if ("v270".equals(vencimento.getCodigoVencimento())) {
							creditoVencidosDe301A360Dias = creditoVencidosDe301A360Dias.add(vencimento.getValorVencimento());
						} else if ("v280".equals(vencimento.getCodigoVencimento())) {
							creditoVencidosDe361A540Dias = creditoVencidosDe361A540Dias.add(vencimento.getValorVencimento());
						} else if ("v290".equals(vencimento.getCodigoVencimento())) {
							creditoVencidosAcima540Dias = creditoVencidosAcima540Dias.add(vencimento.getValorVencimento());
						} else if ("v310".equals(vencimento.getCodigoVencimento())) { // prejuizo
							prejuizoAte12Meses = prejuizoAte12Meses.add(vencimento.getValorVencimento());
						} else if ("v320".equals(vencimento.getCodigoVencimento())) {
							prejuizoMaisQue12Ate48Meses = prejuizoMaisQue12Ate48Meses.add(vencimento.getValorVencimento());
						} else if ("v330".equals(vencimento.getCodigoVencimento())) {
							prejuizoMaisQue48Meses = prejuizoMaisQue48Meses.add(vencimento.getValorVencimento());
						}
					}
				}
			}

			// limites de credito
			hashOut.put("VALOR_LIMITE_CREDITO_ATE_360_DIAS",
					limiteCreditoVencimentoAte360Dias != null ? limiteCreditoVencimentoAte360Dias.doubleValue() : null);
			hashOut.put("VALOR_LIMITE_CREDITO_ACIMA_360_DIAS",
					limiteCreditoVencimentoAcima360Dias != null ? limiteCreditoVencimentoAcima360Dias.doubleValue() : null);

			// creditos a liberar
			hashOut.put("VALOR_CREDITO_A_LIBERAR_ATE_360_DIAS",
					creditoLiberarAte360Dias != null ? creditoLiberarAte360Dias.doubleValue() : null);
			hashOut.put("VALOR_CREDITO_A_LIBERAR_ACIMA_360_DIAS",
					creditoLiberarAcima360Dias != null ? creditoLiberarAcima360Dias.doubleValue() : null);

			// escopo do projeto
			// soma os vencidos até 90 dias
			BigDecimal vencidoAte90Dias = creditoVencidosDe1A14Dias.add(creditoVencidosDe15A30Dias).add(creditoVencidosDe31A60Dias)
					.add(creditoVencidosDe61A90Dias);
			hashOut.put("VALOR_VENCIDO_ATE_90_DIAS", vencidoAte90Dias != null ? vencidoAte90Dias.doubleValue() : null);

			// soma os vencidos acima de 90 dias
			BigDecimal valorAcima90Dias = creditoVencidosDe91A120Dias.add(creditoVencidosDe121A150Dias).add(creditoVencidosDe151A180Dias)
					.add(creditoVencidosDe181A240Dias).add(creditoVencidosDe241A300Dias).add(creditoVencidosDe301A360Dias)
					.add(creditoVencidosDe361A540Dias).add(creditoVencidosAcima540Dias);
			hashOut.put("VALOR_VENCIDO_ACIMA_90_DIAS", valorAcima90Dias != null ? valorAcima90Dias.doubleValue() : null);
			// soma os prejuizos
			BigDecimal valorEmPrejuizo = prejuizoAte12Meses.add(prejuizoMaisQue12Ate48Meses).add(prejuizoMaisQue48Meses);
			hashOut.put("VALOR_EM_PREJUIZO", valorEmPrejuizo != null ? valorEmPrejuizo.doubleValue() : null);

			// creditos a vencer
			hashOut.put("VLR_A_VENCER_ATE_30_DIAS", creditoAVencerAte30Dias != null ? creditoAVencerAte30Dias.doubleValue() : null);
			hashOut.put("VLR_A_VENCER_DE_31_A_60_DIAS", creditoAVencerDe31A60Dias != null ? creditoAVencerDe31A60Dias.doubleValue() : null);
			hashOut.put("VLR_A_VENCER_DE_61_A_90_DIAS", creditoAVencerDe61A90Dias != null ? creditoAVencerDe61A90Dias.doubleValue() : null);
			hashOut.put("VLR_A_VENCER_DE_91_A_180_DIAS",
					creditoAVencerDe91A180Dias != null ? creditoAVencerDe91A180Dias.doubleValue() : null);
			hashOut.put("VLR_A_VENCER_DE_181_A_360_DIAS",
					creditoAVencerDe181A360Dias != null ? creditoAVencerDe181A360Dias.doubleValue() : null);
			hashOut.put("VLR_A_VENCER_DE_361_A_720_DIAS",
					creditoAVencerDe361A720Dias != null ? creditoAVencerDe361A720Dias.doubleValue() : null);
			hashOut.put("VLR_A_VENCER_DE_721_A_1080_DIAS",
					creditoAVencerDe721A1080Dias != null ? creditoAVencerDe721A1080Dias.doubleValue() : null);
			hashOut.put("VLR_A_VENCER_DE_1081_A_1440_DIAS",
					creditoAVencerDe1081A1440Dias != null ? creditoAVencerDe1081A1440Dias.doubleValue() : null);
			hashOut.put("VLR_A_VENCER_DE_1441_A_1800_DIAS",
					creditoAVencerDe1441A1800Dias != null ? creditoAVencerDe1441A1800Dias.doubleValue() : null);
			hashOut.put("VLR_A_VENCER_DE_1801_A_5400_DIAS",
					creditoAVencerDe1801A5400Dias != null ? creditoAVencerDe1801A5400Dias.doubleValue() : null);
			hashOut.put("VLR_A_VENCER_ACIMA_5400_DIAS",
					creditoAVencerAcima5400Dias != null ? creditoAVencerAcima5400Dias.doubleValue() : null);
			hashOut.put("VLR_A_VENCER_COM_PRAZO_INDETERMINADO",
					creditoAVencerPrazoIndeterminado != null ? creditoAVencerPrazoIndeterminado.doubleValue() : null);

			// verifica se existe pelo menos um valor a vencer para retornar true ou false para a política
			hashOut.put("EXISTE_VALOR_A_VENCER", BacenUtil.isExisteValorAVencer(hashOut));

			// creditos vencidos
			hashOut.put("VLR_VENCIDO_1_a_14_DIAS", creditoVencidosDe1A14Dias != null ? creditoVencidosDe1A14Dias.doubleValue() : null);
			hashOut.put("VLR_VENCIDO_15_a_30_DIAS", creditoVencidosDe15A30Dias != null ? creditoVencidosDe15A30Dias.doubleValue() : null);
			hashOut.put("VLR_VENCIDO_31_a_60_DIAS", creditoVencidosDe31A60Dias != null ? creditoVencidosDe31A60Dias.doubleValue() : null);
			hashOut.put("VLR_VENCIDO_61_a_90_DIAS", creditoVencidosDe61A90Dias != null ? creditoVencidosDe61A90Dias.doubleValue() : null);
			hashOut.put("VLR_VENCIDO_91_a_120_DIAS",
					creditoVencidosDe91A120Dias != null ? creditoVencidosDe91A120Dias.doubleValue() : null);
			hashOut.put("VLR_VENCIDO_121_a_150_DIAS",
					creditoVencidosDe121A150Dias != null ? creditoVencidosDe121A150Dias.doubleValue() : null);
			hashOut.put("VLR_VENCIDO_151_a_180_DIAS",
					creditoVencidosDe151A180Dias != null ? creditoVencidosDe151A180Dias.doubleValue() : null);
			hashOut.put("VLR_VENCIDO_181_a_240_DIAS",
					creditoVencidosDe181A240Dias != null ? creditoVencidosDe181A240Dias.doubleValue() : null);
			hashOut.put("VLR_VENCIDO_241_a_300_DIAS",
					creditoVencidosDe241A300Dias != null ? creditoVencidosDe241A300Dias.doubleValue() : null);
			hashOut.put("VLR_VENCIDO_301_a_360_DIAS",
					creditoVencidosDe301A360Dias != null ? creditoVencidosDe301A360Dias.doubleValue() : null);
			hashOut.put("VLR_VENCIDO_361_a_540_DIAS",
					creditoVencidosDe361A540Dias != null ? creditoVencidosDe361A540Dias.doubleValue() : null);
			hashOut.put("VLR_VENCIDO_ACIMA_540_DIAS",
					creditoVencidosAcima540Dias != null ? creditoVencidosAcima540Dias.doubleValue() : null);

			// prejuizo ou perdas
			hashOut.put("VLR_PREJUIZO_ATE_12_MESES", prejuizoAte12Meses != null ? prejuizoAte12Meses.doubleValue() : null);
			hashOut.put("VLR_PREJUIZO_ACIMA_12_ATE_48_MESES",
					prejuizoMaisQue12Ate48Meses != null ? prejuizoMaisQue12Ate48Meses.doubleValue() : null);
			hashOut.put("VLR_PREJUIZO_ACIMA_48_MESES", prejuizoMaisQue48Meses != null ? prejuizoMaisQue48Meses.doubleValue() : null);
		}
	}
}