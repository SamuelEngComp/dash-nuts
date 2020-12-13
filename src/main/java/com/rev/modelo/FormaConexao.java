package com.rev.modelo;

public enum FormaConexao {

	VIDEOCONFERENCIA("Videoconferência"), WEBCONFERENCIA("Webconferência"), VC_WEB("Videoconferência+Webconferência"),
	STREAMING("Streaming"), S_C("Sem Conexão"), WEBCONFERENCIA_STREAMING("Webconferência/Streaming");

	private String descricao;

	FormaConexao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
