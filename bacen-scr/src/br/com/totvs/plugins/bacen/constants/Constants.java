package br.com.totvs.plugins.bacen.constants;

import javax.xml.namespace.QName;

public final class Constants {

	private Constants() {
		throw new IllegalAccessError("ERROR: [Constantes]");
	}

	public static String BACENSCR_TRUSTSTORE = "bacenscr.truststore";
	public static String BACENSCR_TRUSTSTORETYPE = "bacenscr.truststoretype";
	public static String BACENSCR_TRUSTSTOREPASSWORD = "bacenscr.truststorepassword";

	public static String INTELLECTOR_DATADIR = "intellector.datadir";

	public static String BACENSCR_WSDL = "bacenscr.wsdl";

	public static QName SERVICE_NAME = new QName("http://webservice.operacional.scr2.bcb.gov.br/", "Scr2WebService");

	public static String EMPTY = "";
	
	public static int ZERO = 0;
}