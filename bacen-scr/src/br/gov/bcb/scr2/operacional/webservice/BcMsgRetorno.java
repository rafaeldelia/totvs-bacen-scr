
package br.gov.bcb.scr2.operacional.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java de bcMsgRetorno complex type.
 * 
 * <p>
 * O seguinte fragmento do esquema especifica o conteudo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="bcMsgRetorno">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mensagem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bcMsgRetorno", propOrder = { "codigo", "mensagem" })
public class BcMsgRetorno {

	protected String codigo;
	protected String mensagem;

	/**
	 * Obtem o valor da propriedade codigo.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Define o valor da propriedade codigo.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCodigo(String value) {
		this.codigo = value;
	}

	/**
	 * Obtem o valor da propriedade mensagem.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMensagem() {
		return mensagem;
	}

	/**
	 * Define o valor da propriedade mensagem.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMensagem(String value) {
		this.mensagem = value;
	}

	@Override
	public String toString() {
		return "BcMsgRetorno [codigo=" + codigo + ", mensagem=" + mensagem + "]";
	}

}
