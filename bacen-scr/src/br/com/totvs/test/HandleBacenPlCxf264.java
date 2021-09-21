package br.com.totvs.test;

import java.math.BigDecimal;
import java.util.HashMap;

import br.com.totvs.exceptions.ConfigException;
import br.com.totvs.exceptions.InfraException;
import br.com.totvs.exceptions.LayoutException;
import br.com.totvs.plugins.bacen.BacenScr;

public class HandleBacenPlCxf264 {

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		BigDecimal decimalB = new BigDecimal("3.00");
		Double doubleB;

		doubleB = decimalB.doubleValue();
		System.out.println(decimalB);
		System.out.println(doubleB);

		// .doubleValue()

		if (true) {
			return;
		}

		BacenScr bacenScr = new BacenScr();

		HashMap<String, Object> hashIn = new HashMap<String, Object>();

		// Vari�veis de entrada do acesso

		hashIn.put("CPFCNPJ", "13323274");
		hashIn.put("TIPOCLIENTE", "2");
		hashIn.put("DATABASE", "2011-03");
		hashIn.put("USUARIOSISBACEN", "053180001.SWSCR0001");
		hashIn.put("SENHASISBACEN", "bmg456");
		// hashIn.put("USUARIOSISBACEN", "");
		// hashIn.put("SENHASISBACEN", "");

		// XStream xStream = new XStream(new DomDriver());
		// xStream.alias("map", java.util.Map.class);
		// String xmlChamadaSisbacen = xStream.toXML(hashIn);
		//
		// System.out.println(xmlChamadaSisbacen);
		// //
		// if (true){
		// return;
		// }

		// String DataInicio = "2002-01-03";

		// System.out.println(DataInicio.substring(0,4) + DataInicio.substring(5,7) + DataInicio.substring(8,10));

		// if (1==1){
		// return;
		// }

		// try {

		for (int i = 0; i < 1; i++) {

			try {
				bacenScr.execute(hashIn);
			} catch (InfraException | LayoutException | ConfigException e) {
				// TODO Auto-generated catch block
				System.out.println("Erro cuspido no programa principal");
				e.printStackTrace();
			}

			// Thread.sleep(20000); // 1000 milliseconds is one second.
		}
		// } catch(InterruptedException ex) {
		// Thread.currentThread().interrupt();
		// }

	}

}
