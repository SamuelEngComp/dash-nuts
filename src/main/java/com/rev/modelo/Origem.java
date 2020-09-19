package com.rev.modelo;

public enum Origem {

	NACIONAL("Nacional"), INTERNACIONAL("Internacional");

	private String descricao;

	Origem(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
