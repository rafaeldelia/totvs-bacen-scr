package br.com.totvs.plugins.bacen.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.HashMap;
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
				throw new LayoutException("Erro GRAVE: nao foi encontrado a chave 'CPFCNPJ' nos campos de entrada");
			} else if (Util.isNullOrEmpty(tipoCliente)) {
				throw new LayoutException("Erro GRAVE: nao foi encontrado a chave 'TIPOCLIENTE' nos campos de entrada");
			} else if (Util.isNullOrEmpty(dataBase)) {
				throw new LayoutException("Erro GRAVE: nao foi encontrado a chave 'DATABASE' nos campos de entrada");
			} else if (Util.isNullOrEmpty(userSisbacen)) {
				throw new LayoutException("Erro GRAVE: nao foi encontrado a chave 'USUARIOSISBACEN' nos campos de entrada");
			} else if (Util.isNullOrEmpty(autorizacao)) {
				throw new LayoutException("Erro GRAVE: nao foi encontrado a chave 'AUTORIZACAO' nos campos de entrada");
			} else if (Util.isNullOrEmpty(passwordSisbacen)) {
				throw new LayoutException("Erro GRAVE: nao foi encontrado a chave 'SENHASISBACEN' nos campos de entrada");
			} else if (Util.isNullOrEmpty(cnpjIF)) {
				throw new LayoutException("Erro GRAVE: nao foi encontrado a chave 'CNPJIF' nos campos de entrada");
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
}
