package br.com.totvs.plugins.bacen.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import br.com.totvs.exceptions.ConfigException;
import br.com.totvs.exceptions.LayoutException;
import br.com.totvs.plugins.bacen.constants.Constants;
import br.gov.bcb.scr2.operacional.webservice.ResumoDoCliente;

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
			LOGGER.debug("cpfCnpj [" + cpfCnpj + "]");
			LOGGER.debug("tipoCliente [" + tipoCliente + "]");
			LOGGER.debug("dataBase [" + dataBase + "]");
			LOGGER.debug("autorizacao [" + autorizacao + "]");
			LOGGER.debug("User [" + userSisbacen + "]");
			LOGGER.debug("Password [" + passwordSisbacen + "]");

			if (Util.isNullOrEmpty(cpfCnpj)) {
				throw new LayoutException("Erro GRAVE: nao foi encontrado a chave 'CPFCNPJ' nos campos de entrada");
			} else if (Util.isNullOrEmpty(tipoCliente)) {
				throw new LayoutException("Erro GRAVE: nao foi encontrado a chave 'TIPOCLIENTE' nos campos de entrada");
			} else if (Util.isNullOrEmpty(dataBase)) {
				throw new LayoutException("Erro GRAVE: nao foi encontrado a chave 'DATABASE' nos campos de entrada");
			} else if (Util.isNullOrEmpty(autorizacao)) {
				throw new LayoutException("Erro GRAVE: nao foi encontrado a chave 'AUTORIZACAO' nos campos de entrada");
			} else if (Util.isNullOrEmpty(userSisbacen)) {
				throw new LayoutException("Erro GRAVE: nao foi encontrado a chave 'USUARIOSISBACEN' nos campos de entrada");
			} else if (Util.isNullOrEmpty(passwordSisbacen)) {
				throw new LayoutException("Erro GRAVE: nao foi encontrado a chave 'SENHASISBACEN' nos campos de entrada");
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
	public static String consultarURLBacen(Properties pArquivo) throws ConfigException {
		LOGGER.debug("-->>consultarURLBacen");
		String surlWsdl = pArquivo.getProperty(Constants.BACENSCR_WSDL);
		if (Util.isNullOrEmpty(surlWsdl)) {
			throw new ConfigException("Nao encontrou variavel '" + Constants.BACENSCR_WSDL + " no aquivo de propriedades'");
		}
		LOGGER.debug("URL Wsdl Bacen [" + surlWsdl + "]");
		LOGGER.debug("<<--consultarURLBacen");
		return surlWsdl;
	}

	/**
	 * popular LPT__PLUGIN_ENVIO popular LPT__PLUGIN_RETORNO
	 * 
	 * @author rsdelia
	 * @param hashIn
	 * @param hashOut
	 * @param resumoDoCliente
	 *            Transformo os dados de entrada da consulta em XML para armazenar nas tabelas de auditoria Transformo os dados de retorno
	 *            da consulta ao web service
	 */
	public static void popularXMLEnvioRetorno(Map<String, Object> hashIn, HashMap<String, Object> hashOut,
			ResumoDoCliente resumoDoCliente) {
		LOGGER.debug("-->> popularXMLEnvioRetorno");
		XStream xStream = new XStream(new DomDriver());

		String cpfCnpj = (String) hashIn.get("CPFCNPJ");
		String tipoCliente = (String) hashIn.get("TIPOCLIENTE");
		String dataBase = (String) hashIn.get("DATABASE");
		String autorizacao = (String) hashIn.get("AUTORIZACAO");

		HashMap<String, Object> hashRequisicao = new HashMap<String, Object>();
		hashRequisicao.put("codigoDoCliente", cpfCnpj);
		hashRequisicao.put("dataBaseConsultada", dataBase);
		hashRequisicao.put("tipoDoCliente", tipoCliente);
		hashRequisicao.put("autorizacao", autorizacao);
		xStream.alias("map", java.util.Map.class);
		String xmlChamadaSisbacen = xStream.toXML(hashRequisicao);
		LOGGER.debug(xmlChamadaSisbacen);
		hashOut.put("LPT__PLUGIN_ENVIO", xmlChamadaSisbacen);

		if (resumoDoCliente != null) {
			xStream.alias("map", java.util.Map.class);
			String xmlRetornoSisbacen = xStream.toXML(resumoDoCliente);
			LOGGER.debug(xmlRetornoSisbacen);
			hashOut.put("LPT__PLUGIN_RETORNO", xmlRetornoSisbacen);
		}
		LOGGER.debug("<<--popularXMLEnvioRetorno");
	}
}
