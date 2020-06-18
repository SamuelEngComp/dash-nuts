package com.rev.utils;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class PeriodoRelatorio {

	@NotNull(message = "A data inicial é obrigatória")
	private LocalDate dataInicio;
	
	@NotNull(message = "A data final é obrigatória")
	private LocalDate dataFim;
	
	public LocalDate getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}
	public LocalDate getDataFim() {
		return dataFim;
	}
	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}
}
