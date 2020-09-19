package com.rev.repository.filtro;

import java.time.LocalDate;

import com.rev.modelo.FormaConexao;
import com.rev.modelo.TipoAtividade;

public class AtividadeFiltro {

	private LocalDate dataDe;

	private LocalDate dataAte;

	private String descriminacao;

	private TipoAtividade tipo;

	private FormaConexao formaConexao;

	private Integer qtdParticipantes;

	private String tema;

	private Integer duracao;

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

	public String getDescriminacao() {
		return descriminacao;
	}

	public void setDescriminacao(String descriminacao) {
		this.descriminacao = descriminacao;
	}

	public TipoAtividade getTipo() {
		return tipo;
	}

	public void setTipo(TipoAtividade tipo) {
		this.tipo = tipo;
	}

	public FormaConexao getFormaConexao() {
		return formaConexao;
	}

	public void setFormaConexao(FormaConexao formaConexao) {
		this.formaConexao = formaConexao;
	}

	public Integer getQtdParticipantes() {
		return qtdParticipantes;
	}

	public void setQtdParticipantes(Integer qtdParticipantes) {
		this.qtdParticipantes = qtdParticipantes;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public Integer getDuracao() {
		return duracao;
	}

	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}

}
