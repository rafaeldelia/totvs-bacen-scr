package br.com.totvs.plugins.bacen.constants;

public final class Constants {

	private Constants() {
		throw new IllegalAccessError("ERROR: [Constants]");
	}

	public static String BACENSCR_WSDL = "bacenscr.wsdl";
	public static String EMPTY = "";
	public static int ZERO = 0;
	public static Double ZERO_DOUBLE = 0D;
	public static String ERROR_CODE_401 = "HTTP 401 Unauthorized indica que a solicitação não foi aplicada porque não possui credenciais de autenticação válidas para o recurso de destino.";
	public static String NAO_EXISTEM_CODIGOS_VENCIMENTOS = "Não existem códigos de vencimentos a serem considerados.";
}