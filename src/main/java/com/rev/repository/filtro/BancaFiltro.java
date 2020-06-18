package com.rev.repository.filtro;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.rev.modelo.Origem;
import com.rev.modelo.TipoDeBanca;

public class BancaFiltro {
	
	private LocalDate dataDe;

	private LocalDate dataAte;
	
	private TipoDeBanca tipoDeBanca;
	
	private Origem origem;
	
	private String instituicaoParticipante;
	
	private String estado;
	
	private String cidade;
	
	private String pais;
	
	private String localizacaoEstudante;
	
	private String passagens;
	
	private Integer numeroDePassagens;
	
	private Integer numeroPontosExternos;
	
	private BigDecimal valorIda;
	
	private BigDecimal valorVolta;
	
	private BigDecimal diarias;
	
	private BigDecimal valorTotalDe;
	
	private BigDecimal valorTotalAte;

	public LocalDate getDataDe() {
		return dataDe;
	}

	public void setDataDe(LocalDate dataDe) {
		this.dataDe = dataDe;
	}

	public LocalDate getDataAte() {
		return dataAte;
	}

	public void setDataAte(LocalDate dataAte) {
		this.dataAte = dataAte;
	}

	public TipoDeBanca getTipoDeBanca() {
		return tipoDeBanca;
	}

	public void setTipoDeBanca(TipoDeBanca tipoDeBanca) {
		this.tipoDeBanca = tipoDeBanca;
	}

	public Origem getOrigem() {
		return origem;
	}

	public void setOrigem(Origem origem) {
		this.origem = origem;
	}

	public String getInstituicaoParticipante() {
		return instituicaoParticipante;
	}

	public void setInstituicaoParticipante(String instituicaoParticipante) {
		this.instituicaoParticipante = instituicaoParticipante;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getLocalizacaoEstudante() {
		return localizacaoEstudante;
	}

	public void setLocalizacaoEstudante(String localizacaoEstudante) {
		this.localizacaoEstudante = localizacaoEstudante;
	}

	public String getPassagens() {
		return passagens;
	}

	public void setPassagens(String passagens) {
		this.passagens = passagens;
	}

	public Integer getNumeroDePassagens() {
		return numeroDePassagens;
	}

	public void setNumeroDePassagens(Integer numeroDePassagens) {
		this.numeroDePassagens = numeroDePassagens;
	}

	public Integer getNumeroPontosExternos() {
		return numeroPontosExternos;
	}

	public void setNumeroPontosExternos(Integer numeroPontosExternos) {
		this.numeroPontosExternos = numeroPontosExternos;
	}

	public BigDecimal getValorIda() {
		return valorIda;
	}

	public void setValorIda(BigDecimal valorIda) {
		this.valorIda = valorIda;
	}

	public BigDecimal getValorVolta() {
		return valorVolta;
	}

	public void setValorVolta(BigDecimal valorVolta) {
		this.valorVolta = valorVolta;
	}

	public BigDecimal getDiarias() {
		return diarias;
	}

	public void setDiarias(BigDecimal diarias) {
		this.diarias = diarias;
	}

	public BigDecimal getValorTotalDe() {
		return valorTotalDe;
	}

	public void setValorTotalDe(BigDecimal valorTotalDe) {
		this.valorTotalDe = valorTotalDe;
	}

	public BigDecimal getValorTotalAte() {
		return valorTotalAte;
	}

	public void setValorTotalAte(BigDecimal valorTotalAte) {
		this.valorTotalAte = valorTotalAte;
	}

	

}
