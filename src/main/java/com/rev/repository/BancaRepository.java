package com.rev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rev.modelo.BancaExaminadora;
import com.rev.repository.helper.banca.BancaRepositoryQueries;

@Repository
public interface BancaRepository extends JpaRepository<BancaExaminadora, Long>, BancaRepositoryQueries {

}
