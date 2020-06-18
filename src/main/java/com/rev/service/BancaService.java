package com.rev.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rev.modelo.BancaExaminadora;
import com.rev.repository.BancaRepository;

@Service
public class BancaService {

	@Autowired
	private BancaRepository bancaRepository;

	public void salvarBanca(BancaExaminadora bancaExaminadora) {
		bancaRepository.save(bancaExaminadora);
	}
	
	
	public List<BancaExaminadora> buscarTodas(){
		return bancaRepository.findAll();
	}

	
	/*
	 * public LocalDate ajustandoData(BancaExaminadora banca) {
	 * 
	 * DateTimeFormatter dataFormatar = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	 * LocalDate dataFormatadaDate = banca.getData(); String data =
	 * dataFormatadaDate.format(dataFormatar); //esse retorno eh uma string
	 * 
	 * return data; // esse retorno eh um localdate com o formato yyyy-MM-dd }
	 */
	
	
	
	/***
	 *@author Samuel Farias
	 *@return soma dos valores das passagens e diarias
	 *
	 */
	public BigDecimal somandoValores(BancaExaminadora banca) {
		
		BigDecimal valorTotalDiarias = BigDecimal.ZERO;
		BigDecimal valorTotalIda = BigDecimal.ZERO;
		BigDecimal valorTotalVolta = BigDecimal.ZERO;
		
		BigDecimal totalEconomizado = BigDecimal.ZERO;
		
		switch (banca.getNumeroPontosExternos()) {
		case 1:
			valorTotalDiarias = banca.getDiarias();
			valorTotalIda = banca.getValorIda();
			valorTotalVolta = banca.getValorVolta();
			totalEconomizado = valorTotalDiarias.add(valorTotalIda).add(valorTotalVolta);
			break;
			
		case 2:
			valorTotalDiarias = banca.getDiarias().add(banca.getDiarias02());
			valorTotalIda = banca.getValorIda().add(banca.getValorIda02());
			valorTotalVolta = banca.getValorVolta().add(banca.getValorVolta02());
			totalEconomizado = valorTotalDiarias.add(valorTotalIda).add(valorTotalVolta);
			break;
			
		case 3:
			valorTotalDiarias = banca.getDiarias().add(banca.getDiarias02().add(banca.getDiarias03()));
			valorTotalIda = banca.getValorIda().add(banca.getValorIda02().add(banca.getValorIda03()));
			valorTotalVolta = banca.getValorVolta().add(banca.getValorVolta02().add(banca.getValorVolta03()));
			totalEconomizado = valorTotalDiarias.add(valorTotalIda).add(valorTotalVolta);
			break;
			
		case 4:
			valorTotalDiarias = banca.getDiarias().add(banca.getDiarias02().add(banca.getDiarias03().
					add(banca.getDiarias04())));
			valorTotalIda = banca.getValorIda().add(banca.getValorIda02().add(banca.getValorIda03().
					add(banca.getValorIda04())));
			valorTotalVolta = banca.getValorVolta().add(banca.getValorVolta02().add(banca.getValorVolta03().
					add(banca.getValorVolta04())));
			totalEconomizado = valorTotalDiarias.add(valorTotalIda).add(valorTotalVolta);
			break;
			
		case 5:
			valorTotalDiarias = banca.getDiarias().add(banca.getDiarias02().add(banca.getDiarias03().
					add(banca.getDiarias04().add(banca.getDiarias05()))));
			valorTotalIda = banca.getValorIda().add(banca.getValorIda02().add(banca.getValorIda03().
					add(banca.getValorIda04().add(banca.getValorIda05()))));
			valorTotalVolta = banca.getValorVolta().add(banca.getValorVolta02().add(banca.getValorVolta03().
					add(banca.getValorVolta04().add(banca.getValorVolta05()))));
			totalEconomizado = valorTotalDiarias.add(valorTotalIda).add(valorTotalVolta);
			break;
			
		case 6:
			valorTotalDiarias = banca.getDiarias().add(banca.getDiarias02().add(banca.getDiarias03().
					add(banca.getDiarias04().add(banca.getDiarias05().add(banca.getDiarias06())))));
			valorTotalIda = banca.getValorIda().add(banca.getValorIda02().add(banca.getValorIda03().
					add(banca.getValorIda04().add(banca.getValorIda05().add(banca.getValorIda06())))));
			valorTotalVolta = banca.getValorVolta().add(banca.getValorVolta02().add(banca.getValorVolta03().
					add(banca.getValorVolta04().add(banca.getValorVolta05().add(banca.getValorVolta06())))));
			totalEconomizado = valorTotalDiarias.add(valorTotalIda).add(valorTotalVolta);
		default:
			break;
		}
		
		
		return totalEconomizado;
	}


	public void deletarBanca(Long codigo) {
		bancaRepository.deleteById(codigo);
	}

}
