package com.rev.utils;

import org.hibernate.Criteria;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PaginacaoUtils {

	
	  public void preparar(Criteria criteria, Pageable pageable) { 
		  int paginaAtual = pageable.getPageNumber(); 
		  int totalRegistrosPorPagina = pageable.getPageSize(); 
		  int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;
	  
		  criteria.setFirstResult(primeiroRegistro);
		  criteria.setMaxResults(totalRegistrosPorPagina);
	  
	   }
	 
}
