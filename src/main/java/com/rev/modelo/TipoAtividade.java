package com.rev.modelo;

public enum TipoAtividade {

	SESSAO_CLINICA("Sessão Clínica"), 
	GRAVACAO_DE_VIDEO("Gravação de Vídeo"),
	REUNIAO_ADMINISTRATIVA("Reunião Administrativa"), 
	EBSERH("EBSERH"), 
	CIRURGIA("Transmissão de Cirurgia"),
	BANCA("Banca Examinadora"), 
	SIG("SIG"), 
	CURSO("Curso"), 
	AULA("Aula"), 
	SIMPOSIO("Simpósio"), 
	OFICINA("Oficina"),
	TREINAMENTO("Treinamento"),
	WEBINARIO("Webinário"),
	CONGRESSO("Congresso"),
	WEB_PALESTRAS("Web Palestras");

	private String descricao;

	TipoAtividade(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
