
package br.gov.bcb.scr2.operacional.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java de resumoDaOperacao complex type.
 * 
 * <p>
 * O seguinte fragmento do esquema especifica o conteudo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="resumoDaOperacao">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="listaDeVencimentos" type="{http://webservice.operacional.scr2.bcb.gov.br/}resumoDoVencimento" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="modalidade" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="variacaoCambial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resumoDaOperacao", propOrder = { "listaDeVencimentos", "modalidade", "variacaoCambial" })
public class ResumoDaOperacao {

	@XmlElement(nillable = true)
	protected List<ResumoDoVencimento> listaDeVencimentos;
	protected String modalidade;
	protected String variacaoCambial;

	/**
	 * Gets the value of the listaDeVencimentos property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list
	 * will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the listaDeVencimentos property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getListaDeVencimentos().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link ResumoDoVencimento }
	 * 
	 * 
	 */
	public List<ResumoDoVencimento> getListaDeVencimentos() {
		if (listaDeVencimentos == null) {
			listaDeVencimentos = new ArrayList<ResumoDoVencimento>();
		}
		return this.listaDeVencimentos;
	}

	/**
	 * Obtem o valor da propriedade modalidade.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getModalidade() {
		return modalidade;
	}

	/**
	 * Define o valor da propriedade modalidade.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setModalidade(String value) {
		this.modalidade = value;
	}

	/**
	 * Obtem o valor da propriedade variacaoCambial.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getVariacaoCambial() {
		return variacaoCambial;
	}

	/**
	 * Define o valor da propriedade variacaoCambial.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setVariacaoCambial(String value) {
		this.variacaoCambial = value;
	}

	@Override
	public String toString() {
		return "ResumoDaOperacao [listaDeVencimentos=" + listaDeVencimentos + ", modalidade=" + modalidade + ", variacaoCambial="
				+ variacaoCambial + "]";
	}

}
