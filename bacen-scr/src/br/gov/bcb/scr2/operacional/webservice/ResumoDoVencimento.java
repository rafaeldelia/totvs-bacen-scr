
package br.gov.bcb.scr2.operacional.webservice;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java de resumoDoVencimento complex type.
 * 
 * <p>
 * O seguinte fragmento do esquema especifica o conteudo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="resumoDoVencimento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigoVencimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valorVencimento" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resumoDoVencimento", propOrder = { "codigoVencimento", "valorVencimento" })
public class ResumoDoVencimento {

	protected String codigoVencimento;
	protected BigDecimal valorVencimento;

	/**
	 * Obtem o valor da propriedade codigoVencimento.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCodigoVencimento() {
		return codigoVencimento;
	}

	/**
	 * Define o valor da propriedade codigoVencimento.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCodigoVencimento(String value) {
		this.codigoVencimento = value;
	}

	/**
	 * Obtem o valor da propriedade valorVencimento.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getValorVencimento() {
		return valorVencimento;
	}

	/**
	 * Define o valor da propriedade valorVencimento.
	 * 
	 * @param value
	 *            allowed object is {@link BigDecimal }
	 * 
	 */
	public void setValorVencimento(BigDecimal value) {
		this.valorVencimento = value;
	}

	@Override
	public String toString() {
		return "ResumoDoVencimento [codigoVencimento=" + codigoVencimento + ", valorVencimento=" + valorVencimento + "]";
	}

}
