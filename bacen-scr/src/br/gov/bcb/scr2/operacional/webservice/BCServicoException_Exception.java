
package br.gov.bcb.scr2.operacional.webservice;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.6.16
 * 2015-03-16T15:48:43.979-03:00
 * Generated source version: 2.6.16
 */

@WebFault(name = "BCServicoException", targetNamespace = "http://webservice.operacional.scr2.bcb.gov.br/")
public class BCServicoException_Exception extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private br.gov.bcb.scr2.operacional.webservice.BCServicoException bcServicoException;

    public BCServicoException_Exception() {
        super();
    }
    
    public BCServicoException_Exception(String message) {
        super(message);
    }
    
    public BCServicoException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public BCServicoException_Exception(String message, br.gov.bcb.scr2.operacional.webservice.BCServicoException bcServicoException) {
        super(message);
        this.bcServicoException = bcServicoException;
    }

    public BCServicoException_Exception(String message, br.gov.bcb.scr2.operacional.webservice.BCServicoException bcServicoException, Throwable cause) {
        super(message, cause);
        this.bcServicoException = bcServicoException;
    }

    public br.gov.bcb.scr2.operacional.webservice.BCServicoException getFaultInfo() {
        return this.bcServicoException;
    }
}
