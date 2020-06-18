package com.rev.repository.helper.banca;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rev.modelo.BancaExaminadora;
import com.rev.modelo.TipoDeBanca;
import com.rev.repository.filtro.BancaFiltro;

public interface BancaRepositoryQueries {
	
	public Page<BancaExaminadora> filtrar(BancaFiltro filtro, Pageable paginacao);
	
	
	
	public Long bancasPeloTipo(TipoDeBanca tipoDeBanca);
	public BigDecimal economiaComBancas();
	
	public Long totalBancasExamiadoras();
	
	
	public BigDecimal painelEconomiaComBancaPorAno(int ano);
	
	public List<Long> totalBancasPorTipoAnoAtual();
	public BigDecimal economiaComBancasAnoAtual();
	public Long bancasPorTipoAnoAtual(TipoDeBanca tipoDeBanca);

}
