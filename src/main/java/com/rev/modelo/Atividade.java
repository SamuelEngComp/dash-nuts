package com.rev.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Atividade implements Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long codigo;

	@NotNull(message = "O campo Data é obrigatório")
	@Column(name = "data")
	private LocalDate data;

	@NotNull
	@NotBlank(message = "O campo Descriminação é obrigatório")
	@Column(name = "descriminacao")
	private String descriminacao;

	@NotNull(message = "O campo Tipo de Atividade é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private TipoAtividade tipo;

	@NotNull(message = "O campo Forma de Conexão é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "forma_conexao")
	private FormaConexao formaConexao;

	@Min(value = 0)
	@Max(value = 999)
//	@NotNull(message = "Quantidade de participantes deve ser no minimo de 0 e no máximo 999")
	@Column(name = "qtd_participantes")
	private Integer qtdParticipantes = Integer.valueOf(0);

	@NotBlank(message = "O campo Tema é obrigatório")
	@NotNull
	@Column(name = "tema")
	private String tema;

	@Min(value = 1)
	@Max(value = 1440)
	@NotNull(message = "Duração deve ser no minimo de 1 e maximo de 1440 minutos")
	@Column(name = "duracao")
	private Integer duracao;

	@Min(value = 0)
	@Max(value = 1000)
//	@NotNull(message = "Pontos Conectados deve ser no minimo 0 e no maximo 1000")
	@Column(name = "pontos_conectados")
	private Integer pontosConectados = Integer.valueOf(0);

	public boolean isNova() {
		return codigo == null;
	}

	/*
	 * @PrePersist @PreUpdate private void prePersistUpdate() { //antes de salvar no
	 * banco de dados eu quero que tudo fique com letras maiusculas descriminacao =
	 * descriminacao.toUpperCase(); tema = tema.toUpperCase();
	 * 
	 * }
	 */

	/*
	 * @PostLoad private void postLoad() { //DateTimeFormatter dataFormatar =
	 * DateTimeFormatter.ofPattern("dd/MM/yyyy"); data =
	 * this.data.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)); }
	 */

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
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

	public String getDescriminacao() {
		return descriminacao;
	}

	public void setDescriminacao(String descriminacao) {
		this.descriminacao = descriminacao;
	}

	public Integer getPontosConectados() {
		return pontosConectados;
	}

	public void setPontosConectados(Integer pontosConectados) {
		this.pontosConectados = pontosConectados;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Atividade other = (Atividade) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
