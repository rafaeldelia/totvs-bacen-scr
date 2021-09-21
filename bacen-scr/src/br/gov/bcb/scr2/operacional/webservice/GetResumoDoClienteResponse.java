
package br.gov.bcb.scr2.operacional.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de getResumoDoClienteResponse complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteudo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="getResumoDoClienteResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://webservice.operacional.scr2.bcb.gov.br/}resumoDoCliente" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getResumoDoClienteResponse", propOrder = {
    "_return"
})
public class GetResumoDoClienteResponse {

    @XmlElement(name = "return")
    protected ResumoDoCliente _return;

    /**
     * Obtem o valor da propriedade return.
     * 
     * @return
     *     possible object is
     *     {@link ResumoDoCliente }
     *     
     */
    public ResumoDoCliente getReturn() {
        return _return;
    }

    /**
     * Define o valor da propriedade return.
     * 
     * @param value
     *     allowed object is
     *     {@link ResumoDoCliente }
     *     
     */
    public void setReturn(ResumoDoCliente value) {
        this._return = value;
    }

}
