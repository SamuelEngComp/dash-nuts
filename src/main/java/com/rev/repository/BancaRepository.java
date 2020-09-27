package com.rev.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rev.modelo.BancaExaminadora;
import com.rev.repository.helper.banca.BancaRepositoryQueries;

@Repository
public interface BancaRepository extends JpaRepository<BancaExaminadora, Long>, BancaRepositoryQueries {

	
	
	@Query("SELECT a FROM BancaExaminadora a WHERE a.data >= :dataInicial AND a.data <= :dataFinal ORDER BY a.data ASC")
	public List<BancaExaminadora> bancasPorDatas(@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate dataFinal);
}
