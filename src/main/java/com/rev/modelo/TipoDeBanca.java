package com.rev.modelo;

public enum TipoDeBanca {

	BANCA_TCC("Banca de Trabalho de Conclusão de Curso"),
	BANCA_TCR("Banca de Conclusão de Residência"),
	QUALIFICACAO_MESTRADO("Banca de Qualificação de Mestrado"),
	QUALIFICACAO_DOUTORADO("Banca de Qualificação de Doutorado"),
	BANCA_MESTRADO("Banca de Mestrado"),
	BANCA_DOUTORADO("Banca de Doutorado");
	
	private String descricao;
	
	TipoDeBanca(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
