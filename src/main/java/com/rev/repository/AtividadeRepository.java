package com.rev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rev.modelo.Atividade;
import com.rev.modelo.TipoAtividade;
import com.rev.repository.helper.atividade.AtividadeRepositoryQueries;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long>, AtividadeRepositoryQueries{
	
	@Query("SELECT count(codigo) FROM Atividade a where a.tipo = :tipo")
	public Long contarSessaoClinica(@Param("tipo") TipoAtividade tipo);
	
	@Query("SELECT a FROM Atividade a where a.data = :ano")
	public List<Atividade> buscarPorAno(@Param("ano") String ano);
		
}
