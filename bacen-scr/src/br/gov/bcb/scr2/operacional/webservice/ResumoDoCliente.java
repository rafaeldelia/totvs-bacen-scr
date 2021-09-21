
package br.gov.bcb.scr2.operacional.webservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java de resumoDoCliente complex type.
 * 
 * <p>O seguinte fragmento do esquema especifica o conteudo esperado contido dentro desta classe.
 * 
 * <pre>
 * &lt;complexType name="resumoDoCliente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cnpjDaIFSolicitante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codigoDoCliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="coobrigacaoAssumida" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="coobrigacaoRecebida" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="dataBaseConsultada" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataInicioRelacionamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="listaDeMensagensDeValidacao" type="{http://webservice.operacional.scr2.bcb.gov.br/}bcMsgRetorno" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="listaDeResumoDasOperacoes" type="{http://webservice.operacional.scr2.bcb.gov.br/}resumoDaOperacao" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="percentualDocumentosProcessados" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="percentualVolumeProcessado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="quantidadeDeInstituicoes" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="quantidadeDeOperacoes" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="quantidadeOperacoesDiscordancia" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="quantidadeOperacoesSubJudice" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="responsabilidadeTotalDiscordancia" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="responsabilidadeTotalSubJudice" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="riscoIndiretoVendor" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="tipoDoCliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resumoDoCliente", propOrder = {
    "cnpjDaIFSolicitante",
    "codigoDoCliente",
    "coobrigacaoAssumida",
    "coobrigacaoRecebida",
    "dataBaseConsultada",
    "dataInicioRelacionamento",
    "listaDeMensagensDeValidacao",
    "listaDeResumoDasOperacoes",
    "percentualDocumentosProcessados",
    "percentualVolumeProcessado",
    "quantidadeDeInstituicoes",
    "quantidadeDeOperacoes",
    "quantidadeOperacoesDiscordancia",
    "quantidadeOperacoesSubJudice",
    "responsabilidadeTotalDiscordancia",
    "responsabilidadeTotalSubJudice",
    "riscoIndiretoVendor",
    "tipoDoCliente"
})
public class ResumoDoCliente {

    protected String cnpjDaIFSolicitante;
    protected String codigoDoCliente;
    protected BigDecimal coobrigacaoAssumida;
    protected BigDecimal coobrigacaoRecebida;
    protected String dataBaseConsultada;
    protected String dataInicioRelacionamento;
    @XmlElement(nillable = true)
    protected List<BcMsgRetorno> listaDeMensagensDeValidacao;
    @XmlElement(nillable = true)
    protected List<ResumoDaOperacao> listaDeResumoDasOperacoes;
    protected String percentualDocumentosProcessados;
    protected String percentualVolumeProcessado;
    protected int quantidadeDeInstituicoes;
    protected int quantidadeDeOperacoes;
    protected int quantidadeOperacoesDiscordancia;
    protected int quantidadeOperacoesSubJudice;
    protected BigDecimal responsabilidadeTotalDiscordancia;
    protected BigDecimal responsabilidadeTotalSubJudice;
    protected BigDecimal riscoIndiretoVendor;
    protected String tipoDoCliente;

    /**
     * Obtem o valor da propriedade cnpjDaIFSolicitante.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCnpjDaIFSolicitante() {
        return cnpjDaIFSolicitante;
    }

    /**
     * Define o valor da propriedade cnpjDaIFSolicitante.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCnpjDaIFSolicitante(String value) {
        this.cnpjDaIFSolicitante = value;
    }

    /**
     * Obtem o valor da propriedade codigoDoCliente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoDoCliente() {
        return codigoDoCliente;
    }

    /**
     * Define o valor da propriedade codigoDoCliente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoDoCliente(String value) {
        this.codigoDoCliente = value;
    }

    /**
     * Obtem o valor da propriedade coobrigacaoAssumida.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCoobrigacaoAssumida() {
        return coobrigacaoAssumida;
    }

    /**
     * Define o valor da propriedade coobrigacaoAssumida.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCoobrigacaoAssumida(BigDecimal value) {
        this.coobrigacaoAssumida = value;
    }

    /**
     * Obtem o valor da propriedade coobrigacaoRecebida.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCoobrigacaoRecebida() {
        return coobrigacaoRecebida;
    }

    /**
     * Define o valor da propriedade coobrigacaoRecebida.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCoobrigacaoRecebida(BigDecimal value) {
        this.coobrigacaoRecebida = value;
    }

    /**
     * Obtem o valor da propriedade dataBaseConsultada.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataBaseConsultada() {
        return dataBaseConsultada;
    }

    /**
     * Define o valor da propriedade dataBaseConsultada.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataBaseConsultada(String value) {
        this.dataBaseConsultada = value;
    }

    /**
     * Obtem o valor da propriedade dataInicioRelacionamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataInicioRelacionamento() {
        return dataInicioRelacionamento;
    }

    /**
     * Define o valor da propriedade dataInicioRelacionamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataInicioRelacionamento(String value) {
        this.dataInicioRelacionamento = value;
    }

    /**
     * Gets the value of the listaDeMensagensDeValidacao property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaDeMensagensDeValidacao property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaDeMensagensDeValidacao().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BcMsgRetorno }
     * 
     * 
     */
    public List<BcMsgRetorno> getListaDeMensagensDeValidacao() {
        if (listaDeMensagensDeValidacao == null) {
            listaDeMensagensDeValidacao = new ArrayList<BcMsgRetorno>();
        }
        return this.listaDeMensagensDeValidacao;
    }

    /**
     * Gets the value of the listaDeResumoDasOperacoes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaDeResumoDasOperacoes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaDeResumoDasOperacoes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResumoDaOperacao }
     * 
     * 
     */
    public List<ResumoDaOperacao> getListaDeResumoDasOperacoes() {
        if (listaDeResumoDasOperacoes == null) {
            listaDeResumoDasOperacoes = new ArrayList<ResumoDaOperacao>();
        }
        return this.listaDeResumoDasOperacoes;
    }

    /**
     * Obtem o valor da propriedade percentualDocumentosProcessados.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPercentualDocumentosProcessados() {
        return percentualDocumentosProcessados;
    }

    /**
     * Define o valor da propriedade percentualDocumentosProcessados.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPercentualDocumentosProcessados(String value) {
        this.percentualDocumentosProcessados = value;
    }

    /**
     * Obtem o valor da propriedade percentualVolumeProcessado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPercentualVolumeProcessado() {
        return percentualVolumeProcessado;
    }

    /**
     * Define o valor da propriedade percentualVolumeProcessado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPercentualVolumeProcessado(String value) {
        this.percentualVolumeProcessado = value;
    }

    /**
     * Obtem o valor da propriedade quantidadeDeInstituicoes.
     * 
     */
    public int getQuantidadeDeInstituicoes() {
        return quantidadeDeInstituicoes;
    }

    /**
     * Define o valor da propriedade quantidadeDeInstituicoes.
     * 
     */
    public void setQuantidadeDeInstituicoes(int value) {
        this.quantidadeDeInstituicoes = value;
    }

    /**
     * Obtem o valor da propriedade quantidadeDeOperacoes.
     * 
     */
    public int getQuantidadeDeOperacoes() {
        return quantidadeDeOperacoes;
    }

    /**
     * Define o valor da propriedade quantidadeDeOperacoes.
     * 
     */
    public void setQuantidadeDeOperacoes(int value) {
        this.quantidadeDeOperacoes = value;
    }

    /**
     * Obtem o valor da propriedade quantidadeOperacoesDiscordancia.
     * 
     */
    public int getQuantidadeOperacoesDiscordancia() {
        return quantidadeOperacoesDiscordancia;
    }

    /**
     * Define o valor da propriedade quantidadeOperacoesDiscordancia.
     * 
     */
    public void setQuantidadeOperacoesDiscordancia(int value) {
        this.quantidadeOperacoesDiscordancia = value;
    }

    /**
     * Obtem o valor da propriedade quantidadeOperacoesSubJudice.
     * 
     */
    public int getQuantidadeOperacoesSubJudice() {
        return quantidadeOperacoesSubJudice;
    }

    /**
     * Define o valor da propriedade quantidadeOperacoesSubJudice.
     * 
     */
    public void setQuantidadeOperacoesSubJudice(int value) {
        this.quantidadeOperacoesSubJudice = value;
    }

    /**
     * Obtem o valor da propriedade responsabilidadeTotalDiscordancia.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getResponsabilidadeTotalDiscordancia() {
        return responsabilidadeTotalDiscordancia;
    }

    /**
     * Define o valor da propriedade responsabilidadeTotalDiscordancia.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setResponsabilidadeTotalDiscordancia(BigDecimal value) {
        this.responsabilidadeTotalDiscordancia = value;
    }

    /**
     * Obtem o valor da propriedade responsabilidadeTotalSubJudice.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getResponsabilidadeTotalSubJudice() {
        return responsabilidadeTotalSubJudice;
    }

    /**
     * Define o valor da propriedade responsabilidadeTotalSubJudice.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setResponsabilidadeTotalSubJudice(BigDecimal value) {
        this.responsabilidadeTotalSubJudice = value;
    }

    /**
     * Obtem o valor da propriedade riscoIndiretoVendor.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRiscoIndiretoVendor() {
        return riscoIndiretoVendor;
    }

    /**
     * Define o valor da propriedade riscoIndiretoVendor.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRiscoIndiretoVendor(BigDecimal value) {
        this.riscoIndiretoVendor = value;
    }

    /**
     * Obtem o valor da propriedade tipoDoCliente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDoCliente() {
        return tipoDoCliente;
    }

    /**
     * Define o valor da propriedade tipoDoCliente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDoCliente(String value) {
        this.tipoDoCliente = value;
    }

}
