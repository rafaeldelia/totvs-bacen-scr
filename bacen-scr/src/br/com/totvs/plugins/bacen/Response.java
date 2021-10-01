package br.com.totvs.plugins.bacen;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.gov.bcb.scr2.operacional.webservice.BcMsgRetorno;
import br.gov.bcb.scr2.operacional.webservice.ResumoDaOperacao;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "return")
public class Response {

	@XmlElement(nillable = true)
	protected String cnpjDaIFSolicitante;
	@XmlElement(nillable = true)
	protected String codigoDoCliente;
	@XmlElement(nillable = true)
	protected BigDecimal coobrigacaoAssumida;
	@XmlElement(nillable = true)
	protected BigDecimal coobrigacaoRecebida;
	@XmlElement(nillable = true)
	protected String dataBaseConsultada;
	@XmlElement(nillable = true)
	protected String dataInicioRelacionamento;
	@XmlElement(nillable = true)
	protected List<BcMsgRetorno> listaDeMensagensDeValidacao;
	@XmlElement(nillable = true)
	protected List<ResumoDaOperacao> listaDeResumoDasOperacoes;
	@XmlElement(nillable = true)
	protected String percentualDocumentosProcessados;
	@XmlElement(nillable = true)
	protected String percentualVolumeProcessado;
	@XmlElement(nillable = true)
	protected int quantidadeDeInstituicoes;
	@XmlElement(nillable = true)
	protected int quantidadeDeOperacoes;
	@XmlElement(nillable = true)
	protected int quantidadeOperacoesDiscordancia;
	@XmlElement(nillable = true)
	protected int quantidadeOperacoesSubJudice;
	@XmlElement(nillable = true)
	protected BigDecimal responsabilidadeTotalDiscordancia;
	@XmlElement(nillable = true)
	protected BigDecimal responsabilidadeTotalSubJudice;
	@XmlElement(nillable = true)
	protected BigDecimal riscoIndiretoVendor;
	@XmlElement(nillable = true)
	protected String tipoDoCliente;

	public String getCnpjDaIFSolicitante() {
		return cnpjDaIFSolicitante;
	}

	public void setCnpjDaIFSolicitante(String cnpjDaIFSolicitante) {
		this.cnpjDaIFSolicitante = cnpjDaIFSolicitante;
	}

	public String getCodigoDoCliente() {
		return codigoDoCliente;
	}

	public void setCodigoDoCliente(String codigoDoCliente) {
		this.codigoDoCliente = codigoDoCliente;
	}

	public BigDecimal getCoobrigacaoAssumida() {
		return coobrigacaoAssumida;
	}

	public void setCoobrigacaoAssumida(BigDecimal coobrigacaoAssumida) {
		this.coobrigacaoAssumida = coobrigacaoAssumida;
	}

	public BigDecimal getCoobrigacaoRecebida() {
		return coobrigacaoRecebida;
	}

	public void setCoobrigacaoRecebida(BigDecimal coobrigacaoRecebida) {
		this.coobrigacaoRecebida = coobrigacaoRecebida;
	}

	public String getDataBaseConsultada() {
		return dataBaseConsultada;
	}

	public void setDataBaseConsultada(String dataBaseConsultada) {
		this.dataBaseConsultada = dataBaseConsultada;
	}

	public String getDataInicioRelacionamento() {
		return dataInicioRelacionamento;
	}

	public void setDataInicioRelacionamento(String dataInicioRelacionamento) {
		this.dataInicioRelacionamento = dataInicioRelacionamento;
	}

	public List<BcMsgRetorno> getListaDeMensagensDeValidacao() {
		return listaDeMensagensDeValidacao;
	}

	public void setListaDeMensagensDeValidacao(List<BcMsgRetorno> listaDeMensagensDeValidacao) {
		this.listaDeMensagensDeValidacao = listaDeMensagensDeValidacao;
	}

	public List<ResumoDaOperacao> getListaDeResumoDasOperacoes() {
		if (listaDeResumoDasOperacoes == null) {
			listaDeResumoDasOperacoes = new ArrayList<ResumoDaOperacao>();
		}
		return listaDeResumoDasOperacoes;
	}

	public void setListaDeResumoDasOperacoes(List<ResumoDaOperacao> listaDeResumoDasOperacoes) {
		this.listaDeResumoDasOperacoes = listaDeResumoDasOperacoes;
	}

	public String getPercentualDocumentosProcessados() {
		return percentualDocumentosProcessados;
	}

	public void setPercentualDocumentosProcessados(String percentualDocumentosProcessados) {
		this.percentualDocumentosProcessados = percentualDocumentosProcessados;
	}

	public String getPercentualVolumeProcessado() {
		return percentualVolumeProcessado;
	}

	public void setPercentualVolumeProcessado(String percentualVolumeProcessado) {
		this.percentualVolumeProcessado = percentualVolumeProcessado;
	}

	public int getQuantidadeDeInstituicoes() {
		return quantidadeDeInstituicoes;
	}

	public void setQuantidadeDeInstituicoes(int quantidadeDeInstituicoes) {
		this.quantidadeDeInstituicoes = quantidadeDeInstituicoes;
	}

	public int getQuantidadeDeOperacoes() {
		return quantidadeDeOperacoes;
	}

	public void setQuantidadeDeOperacoes(int quantidadeDeOperacoes) {
		this.quantidadeDeOperacoes = quantidadeDeOperacoes;
	}

	public int getQuantidadeOperacoesDiscordancia() {
		return quantidadeOperacoesDiscordancia;
	}

	public void setQuantidadeOperacoesDiscordancia(int quantidadeOperacoesDiscordancia) {
		this.quantidadeOperacoesDiscordancia = quantidadeOperacoesDiscordancia;
	}

	public int getQuantidadeOperacoesSubJudice() {
		return quantidadeOperacoesSubJudice;
	}

	public void setQuantidadeOperacoesSubJudice(int quantidadeOperacoesSubJudice) {
		this.quantidadeOperacoesSubJudice = quantidadeOperacoesSubJudice;
	}

	public BigDecimal getResponsabilidadeTotalDiscordancia() {
		return responsabilidadeTotalDiscordancia;
	}

	public void setResponsabilidadeTotalDiscordancia(BigDecimal responsabilidadeTotalDiscordancia) {
		this.responsabilidadeTotalDiscordancia = responsabilidadeTotalDiscordancia;
	}

	public BigDecimal getResponsabilidadeTotalSubJudice() {
		return responsabilidadeTotalSubJudice;
	}

	public void setResponsabilidadeTotalSubJudice(BigDecimal responsabilidadeTotalSubJudice) {
		this.responsabilidadeTotalSubJudice = responsabilidadeTotalSubJudice;
	}

	public BigDecimal getRiscoIndiretoVendor() {
		return riscoIndiretoVendor;
	}

	public void setRiscoIndiretoVendor(BigDecimal riscoIndiretoVendor) {
		this.riscoIndiretoVendor = riscoIndiretoVendor;
	}

	public String getTipoDoCliente() {
		return tipoDoCliente;
	}

	public void setTipoDoCliente(String tipoDoCliente) {
		this.tipoDoCliente = tipoDoCliente;
	}

	@Override
	public String toString() {
		return "Response [cnpjDaIFSolicitante=" + cnpjDaIFSolicitante + ", codigoDoCliente=" + codigoDoCliente + ", coobrigacaoAssumida="
				+ coobrigacaoAssumida + ", coobrigacaoRecebida=" + coobrigacaoRecebida + ", dataBaseConsultada=" + dataBaseConsultada
				+ ", dataInicioRelacionamento=" + dataInicioRelacionamento + ", listaDeMensagensDeValidacao=" + listaDeMensagensDeValidacao
				+ ", listaDeResumoDasOperacoes=" + listaDeResumoDasOperacoes + ", percentualDocumentosProcessados="
				+ percentualDocumentosProcessados + ", percentualVolumeProcessado=" + percentualVolumeProcessado
				+ ", quantidadeDeInstituicoes=" + quantidadeDeInstituicoes + ", quantidadeDeOperacoes=" + quantidadeDeOperacoes
				+ ", quantidadeOperacoesDiscordancia=" + quantidadeOperacoesDiscordancia + ", quantidadeOperacoesSubJudice="
				+ quantidadeOperacoesSubJudice + ", responsabilidadeTotalDiscordancia=" + responsabilidadeTotalDiscordancia
				+ ", responsabilidadeTotalSubJudice=" + responsabilidadeTotalSubJudice + ", riscoIndiretoVendor=" + riscoIndiretoVendor
				+ ", tipoDoCliente=" + tipoDoCliente + "]";
	}

}