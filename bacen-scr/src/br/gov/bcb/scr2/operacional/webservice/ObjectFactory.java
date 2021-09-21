
package br.gov.bcb.scr2.operacional.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.gov.bcb.scr2.operacional.webservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetResumoDoClienteResponse_QNAME = new QName("http://webservice.operacional.scr2.bcb.gov.br/", "getResumoDoClienteResponse");
    private final static QName _GetResumoDoClienteBACENResponse_QNAME = new QName("http://webservice.operacional.scr2.bcb.gov.br/", "getResumoDoClienteBACENResponse");
    private final static QName _GetResumoDoCliente_QNAME = new QName("http://webservice.operacional.scr2.bcb.gov.br/", "getResumoDoCliente");
    private final static QName _ResumoDoCliente_QNAME = new QName("http://webservice.operacional.scr2.bcb.gov.br/", "resumoDoCliente");
    private final static QName _BCServicoException_QNAME = new QName("http://webservice.operacional.scr2.bcb.gov.br/", "BCServicoException");
    private final static QName _GetResumoDoClienteBACEN_QNAME = new QName("http://webservice.operacional.scr2.bcb.gov.br/", "getResumoDoClienteBACEN");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.gov.bcb.scr2.operacional.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetResumoDoClienteResponse }
     * 
     */
    public GetResumoDoClienteResponse createGetResumoDoClienteResponse() {
        return new GetResumoDoClienteResponse();
    }

    /**
     * Create an instance of {@link GetResumoDoClienteBACEN }
     * 
     */
    public GetResumoDoClienteBACEN createGetResumoDoClienteBACEN() {
        return new GetResumoDoClienteBACEN();
    }

    /**
     * Create an instance of {@link BCServicoException }
     * 
     */
    public BCServicoException createBCServicoException() {
        return new BCServicoException();
    }

    /**
     * Create an instance of {@link ResumoDoCliente }
     * 
     */
    public ResumoDoCliente createResumoDoCliente() {
        return new ResumoDoCliente();
    }

    /**
     * Create an instance of {@link GetResumoDoCliente }
     * 
     */
    public GetResumoDoCliente createGetResumoDoCliente() {
        return new GetResumoDoCliente();
    }

    /**
     * Create an instance of {@link GetResumoDoClienteBACENResponse }
     * 
     */
    public GetResumoDoClienteBACENResponse createGetResumoDoClienteBACENResponse() {
        return new GetResumoDoClienteBACENResponse();
    }

    /**
     * Create an instance of {@link ResumoDoVencimento }
     * 
     */
    public ResumoDoVencimento createResumoDoVencimento() {
        return new ResumoDoVencimento();
    }

    /**
     * Create an instance of {@link BcMsgRetorno }
     * 
     */
    public BcMsgRetorno createBcMsgRetorno() {
        return new BcMsgRetorno();
    }

    /**
     * Create an instance of {@link ResumoDaOperacao }
     * 
     */
    public ResumoDaOperacao createResumoDaOperacao() {
        return new ResumoDaOperacao();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResumoDoClienteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.operacional.scr2.bcb.gov.br/", name = "getResumoDoClienteResponse")
    public JAXBElement<GetResumoDoClienteResponse> createGetResumoDoClienteResponse(GetResumoDoClienteResponse value) {
        return new JAXBElement<GetResumoDoClienteResponse>(_GetResumoDoClienteResponse_QNAME, GetResumoDoClienteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResumoDoClienteBACENResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.operacional.scr2.bcb.gov.br/", name = "getResumoDoClienteBACENResponse")
    public JAXBElement<GetResumoDoClienteBACENResponse> createGetResumoDoClienteBACENResponse(GetResumoDoClienteBACENResponse value) {
        return new JAXBElement<GetResumoDoClienteBACENResponse>(_GetResumoDoClienteBACENResponse_QNAME, GetResumoDoClienteBACENResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResumoDoCliente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.operacional.scr2.bcb.gov.br/", name = "getResumoDoCliente")
    public JAXBElement<GetResumoDoCliente> createGetResumoDoCliente(GetResumoDoCliente value) {
        return new JAXBElement<GetResumoDoCliente>(_GetResumoDoCliente_QNAME, GetResumoDoCliente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResumoDoCliente }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.operacional.scr2.bcb.gov.br/", name = "resumoDoCliente")
    public JAXBElement<ResumoDoCliente> createResumoDoCliente(ResumoDoCliente value) {
        return new JAXBElement<ResumoDoCliente>(_ResumoDoCliente_QNAME, ResumoDoCliente.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BCServicoException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.operacional.scr2.bcb.gov.br/", name = "BCServicoException")
    public JAXBElement<BCServicoException> createBCServicoException(BCServicoException value) {
        return new JAXBElement<BCServicoException>(_BCServicoException_QNAME, BCServicoException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResumoDoClienteBACEN }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.operacional.scr2.bcb.gov.br/", name = "getResumoDoClienteBACEN")
    public JAXBElement<GetResumoDoClienteBACEN> createGetResumoDoClienteBACEN(GetResumoDoClienteBACEN value) {
        return new JAXBElement<GetResumoDoClienteBACEN>(_GetResumoDoClienteBACEN_QNAME, GetResumoDoClienteBACEN.class, null, value);
    }

}
