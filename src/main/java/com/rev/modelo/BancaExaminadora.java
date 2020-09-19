package com.rev.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class BancaExaminadora implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotNull(message = "Data é obrigatório")
	@Column(name = "data")
	private LocalDate data;

	@NotNull(message = "O tipo de banca é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_de_banca")
	private TipoDeBanca tipoDeBanca;

	@NotNull(message = "A campo Categoria é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "origem")
	private Origem origem;

	@NotBlank(message = "O campo Instituições não pode ser vazia")
	@Size(min = 2, max = 99, message = "Minino de 2 digitos e maximo de 99")
	@Column(name = "instituicao_participante")
	private String instituicaoParticipante;

	@NotBlank(message = "O campo Estado não deve ser vazio")
	@Size(min = 2, max = 99, message = "Minino de 2 digitos e maximo de 99")
	@Column(name = "estado")
	private String estado;

	@NotBlank(message = "O campo Cidade não deve ser vazio")
	@Size(min = 2, max = 99, message = "Minino de 2 digitos e maximo de 99")
	@Column(name = "cidade")
	private String cidade;

	@NotBlank(message = "O campo Pais não deve ser vazio")
	@Size(min = 2, max = 99, message = "Minino de 2 digitos e maximo de 99")
	@Column(name = "pais")
	private String pais;

	@NotBlank(message = "O campo Localização do Estudante não deve ser vazio")
	@Size(min = 2, max = 99, message = "Minino de 2 digitos e maximo de 99")
	@Column(name = "localizacao_estudante")
	private String localizacaoEstudante;

	@Column(name = "passagens")
	private String passagens;

	@Min(value = 1, message = "O número de passagens deve ser maior/igual a 1")
	@Max(value = 99, message = "O número de passagens deve ser menor que 99")
	@NotNull(message = "O campo N° de Passagens é obrigatório")
	@Column(name = "numero_de_passagens")
	private Integer numeroDePassagens;

	@Min(value = 0)
	@Max(value = 7, message = "O número de Pontos externos deve ser menor que 7")
	@NotNull(message = "O número de Pontos Externos é um campo obrigatório")
	@Column(name = "numero_pontos_externos")
	private Integer numeroPontosExternos;

	@NotNull(message = "Valor de Ida é um campo obrigatório")
	@DecimalMin(value = "0.10")
	@DecimalMax(value = "9999999.99", message = "O valor da passagem deve ser menor que R$ 9.999.999,99")
	@Column(name = "valor_ida")
	private BigDecimal valorIda;

	@Column(name = "valor_ida_02")
	private BigDecimal valorIda02 = BigDecimal.ZERO;
	@Column(name = "valor_ida_03")
	private BigDecimal valorIda03 = BigDecimal.ZERO;
	@Column(name = "valor_ida_04")
	private BigDecimal valorIda04 = BigDecimal.ZERO;
	@Column(name = "valor_ida_05")
	private BigDecimal valorIda05 = BigDecimal.ZERO;
	@Column(name = "valor_ida_06")
	private BigDecimal valorIda06 = BigDecimal.ZERO;

	@NotNull(message = "Valor de Volta é um campo obrigatório")
	@DecimalMin(value = "0.10")
	@DecimalMax(value = "9999999.99", message = "O valor da passagem deve ser menor que R$ 9.999.999,99")
	@Column(name = "valor_volta")
	private BigDecimal valorVolta;

	@Column(name = "valor_volta_02")
	private BigDecimal valorVolta02 = BigDecimal.ZERO;
	@Column(name = "valor_volta_03")
	private BigDecimal valorVolta03 = BigDecimal.ZERO;
	@Column(name = "valor_volta_04")
	private BigDecimal valorVolta04 = BigDecimal.ZERO;
	@Column(name = "valor_volta_05")
	private BigDecimal valorVolta05 = BigDecimal.ZERO;
	@Column(name = "valor_volta_06")
	private BigDecimal valorVolta06 = BigDecimal.ZERO;

	@NotNull(message = "Diarias é um campo obrigatório")
	@DecimalMin(value = "0.10")
	@DecimalMax(value = "9999999.99", message = "O valor das Diarias deve ser menor que R$ 9.999.999,99")
	@Column(name = "diarias")
	private BigDecimal diarias;

	@Column(name = "diarias_02")
	private BigDecimal diarias02 = BigDecimal.ZERO;
	@Column(name = "diarias_03")
	private BigDecimal diarias03 = BigDecimal.ZERO;
	@Column(name = "diarias_04")
	private BigDecimal diarias04 = BigDecimal.ZERO;
	@Column(name = "diarias_05")
	private BigDecimal diarias05 = BigDecimal.ZERO;
	@Column(name = "diarias_06")
	private BigDecimal diarias06 = BigDecimal.ZERO;

	@Column(name = "valor_total")
	private BigDecimal valorTotal;

	@PrePersist
	@PreUpdate
	private void prePersistUpdate() { // antes de salvar no banco de dados eu quero que tudo fique com letras
										// maiusculas
		instituicaoParticipante = instituicaoParticipante.toUpperCase();
		estado = estado.toUpperCase();
		cidade = cidade.toUpperCase();
		pais = pais.toUpperCase();
		localizacaoEstudante = localizacaoEstudante.toUpperCase();
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
		BancaExaminadora other = (BancaExaminadora) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

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

	public BigDecimal getValorIda() {
		return valorIda;
	}

	public Integer getNumeroPontosExternos() {
		return numeroPontosExternos;
	}

	public void setNumeroPontosExternos(Integer numeroPontosExternos) {
		this.numeroPontosExternos = numeroPontosExternos;
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

	public BigDecimal getValorTotal() {
		// this.valorTotal = this.valorVolta.add(this.valorIda).add(this.diarias);
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		// valorTotal = this.valorIda.add(this.valorVolta).add(this.diarias);
		this.valorTotal = valorTotal;
	}

	public void setDiarias(BigDecimal diarias) {
		this.diarias = diarias;
	}

	public BigDecimal getValorIda02() {
		return valorIda02;
	}

	public void setValorIda02(BigDecimal valorIda02) {
		this.valorIda02 = valorIda02;
	}

	public BigDecimal getValorIda03() {
		return valorIda03;
	}

	public void setValorIda03(BigDecimal valorIda03) {
		this.valorIda03 = valorIda03;
	}

	public BigDecimal getValorIda04() {
		return valorIda04;
	}

	public void setValorIda04(BigDecimal valorIda04) {
		this.valorIda04 = valorIda04;
	}

	public BigDecimal getValorIda05() {
		return valorIda05;
	}

	public void setValorIda05(BigDecimal valorIda05) {
		this.valorIda05 = valorIda05;
	}

	public BigDecimal getValorIda06() {
		return valorIda06;
	}

	public void setValorIda06(BigDecimal valorIda06) {
		this.valorIda06 = valorIda06;
	}

	public BigDecimal getValorVolta02() {
		return valorVolta02;
	}

	public void setValorVolta02(BigDecimal valorVolta02) {
		this.valorVolta02 = valorVolta02;
	}

	public BigDecimal getValorVolta03() {
		return valorVolta03;
	}

	public void setValorVolta03(BigDecimal valorVolta03) {
		this.valorVolta03 = valorVolta03;
	}

	public BigDecimal getValorVolta04() {
		return valorVolta04;
	}

	public void setValorVolta04(BigDecimal valorVolta04) {
		this.valorVolta04 = valorVolta04;
	}

	public BigDecimal getValorVolta05() {
		return valorVolta05;
	}

	public void setValorVolta05(BigDecimal valorVolta05) {
		this.valorVolta05 = valorVolta05;
	}

	public BigDecimal getValorVolta06() {
		return valorVolta06;
	}

	public void setValorVolta06(BigDecimal valorVolta06) {
		this.valorVolta06 = valorVolta06;
	}

	public BigDecimal getDiarias02() {
		return diarias02;
	}

	public void setDiarias02(BigDecimal diarias02) {
		this.diarias02 = diarias02;
	}

	public BigDecimal getDiarias03() {
		return diarias03;
	}

	public void setDiarias03(BigDecimal diarias03) {
		this.diarias03 = diarias03;
	}

	public BigDecimal getDiarias04() {
		return diarias04;
	}

	public void setDiarias04(BigDecimal diarias04) {
		this.diarias04 = diarias04;
	}

	public BigDecimal getDiarias05() {
		return diarias05;
	}

	public void setDiarias05(BigDecimal diarias05) {
		this.diarias05 = diarias05;
	}

	public BigDecimal getDiarias06() {
		return diarias06;
	}

	public void setDiarias06(BigDecimal diarias06) {
		this.diarias06 = diarias06;
	}

	public boolean isNova() {
		return codigo == null;
	}

}
