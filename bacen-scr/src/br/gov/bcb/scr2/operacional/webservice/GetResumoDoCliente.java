
package br.gov.bcb.scr2.operacional.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de getResumoDoCliente complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteudo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="getResumoDoCliente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codCliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tpCliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataBase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="autorizacao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getResumoDoCliente", propOrder = {
    "codCliente",
    "tpCliente",
    "dataBase",
    "autorizacao"
})
public class GetResumoDoCliente {

    protected String codCliente;
    protected String tpCliente;
    protected String dataBase;
    protected String autorizacao;

    /**
     * Obtem o valor da propriedade codCliente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCliente() {
        return codCliente;
    }

    /**
     * Define o valor da propriedade codCliente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCliente(String value) {
        this.codCliente = value;
    }

    /**
     * Obtem o valor da propriedade tpCliente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTpCliente() {
        return tpCliente;
    }

    /**
     * Define o valor da propriedade tpCliente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTpCliente(String value) {
        this.tpCliente = value;
    }

    /**
     * Obtem o valor da propriedade dataBase.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataBase() {
        return dataBase;
    }

    /**
     * Define o valor da propriedade dataBase.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataBase(String value) {
        this.dataBase = value;
    }

    /**
     * Obtem o valor da propriedade autorizacao.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutorizacao() {
        return autorizacao;
    }

    /**
     * Define o valor da propriedade autorizacao.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutorizacao(String value) {
        this.autorizacao = value;
    }

}
