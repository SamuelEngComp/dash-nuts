package com.rev.repository.helper.banca;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.rev.modelo.BancaExaminadora;
import com.rev.modelo.TipoAtividade;
import com.rev.modelo.TipoDeBanca;
import com.rev.repository.filtro.BancaFiltro;

public class BancaRepositoryImpl implements BancaRepositoryQueries{

	
	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public Page<BancaExaminadora> filtrar(BancaFiltro filtro, Pageable paginacao) {
		Session session = manager.unwrap(Session.class);
		session.setDefaultReadOnly(true);
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(BancaExaminadora.class);

		int paginaAtual = paginacao.getPageNumber();
		int totalRegistroPorPagina = paginacao.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistroPorPagina;

		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistroPorPagina);

		adicionarFiltro(filtro, criteria);
		return new PageImpl<BancaExaminadora>(criteria.list(), paginacao, total(filtro));
	}
	
	private long total(BancaFiltro filtro) {
		Session session = manager.unwrap(Session.class);
		session.setDefaultReadOnly(true);
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(BancaExaminadora.class);

		adicionarFiltro(filtro, criteria);

		criteria.setProjection(Projections.rowCount());

		return (Long) criteria.uniqueResult();
	}
	
	private void adicionarFiltro(BancaFiltro filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getLocalizacaoEstudante())) {
				criteria.add(Restrictions.ilike("localizacaoEstudante", filtro.getLocalizacaoEstudante(), MatchMode.ANYWHERE));
			}

			if (filtro.getTipoDeBanca() != null) {
				criteria.add(Restrictions.eq("tipoDeBanca", filtro.getTipoDeBanca()));
			}

			if (filtro.getNumeroPontosExternos() != null) {
				criteria.add(Restrictions.eq("numeroPontosExternos", filtro.getNumeroPontosExternos()));
			}

			if (filtro.getValorTotalDe() != null) {
				criteria.add(Restrictions.ge("valorTotal", filtro.getValorTotalDe()));
			}

			if (filtro.getValorTotalAte() != null) {
				criteria.add(Restrictions.le("valorTotal", filtro.getValorTotalAte()));
			}

			if (filtro.getOrigem() != null) {
				criteria.add(Restrictions.eq("origem", filtro.getOrigem()));
			}

			if (filtro.getDataDe() != null) {
				criteria.add(Restrictions.ge("data", filtro.getDataDe()));
			}

			if (filtro.getDataAte() != null) {
				criteria.add(Restrictions.le("data", filtro.getDataAte()));
			}

		}
	}

	@Override
	public Long bancasPeloTipo(TipoDeBanca tipoDeBanca) {
		Optional<Long> optional = Optional.ofNullable(manager.createQuery(
				"select count(codigo) from BancaExaminadora a where a.tipoDeBanca = :tipoDeBanca"
				+ "and Yaer", Long.class)
					.setParameter("tipoDeBanca", tipoDeBanca).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public BigDecimal economiaComBancas() {
		Optional<BigDecimal> optional = Optional.ofNullable(manager.createQuery(
				"select sum(a.valorTotal) from BancaExaminadora a", BigDecimal.class).getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}

	@Override
	public BigDecimal painelEconomiaComBancaPorAno(int ano) {
		Optional<BigDecimal> optional = Optional.ofNullable(manager.createQuery(
				"select sum(a.valorTotal) from BancaExaminadora a where year(data) = :ano", 
				BigDecimal.class).setParameter("ano", ano).getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}

	/**
	 * total de bancas examinadoras geral
	 */
	@Override
	public Long totalBancasExamiadoras() {
		Optional<Long> optional = Optional.ofNullable(manager.createQuery(
				"select count(codigo) from BancaExaminadora", Long.class).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public List<Long> totalBancasPorTipoAnoAtual() {
		
		List<Long> bancas = new ArrayList<Long>();
		
		Optional<Long> tcc = Optional.ofNullable(
				manager.createQuery("select count(codigo) from BancaExaminadora a where year(data) = :ano and a.tipoDeBanca = :tipoDeBanca",
				Long.class)
					.setParameter("tipoDeBanca", TipoDeBanca.BANCA_TCC)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> tcr = Optional.ofNullable(
				manager.createQuery("select count(codigo) from BancaExaminadora a where year(data) = :ano and a.tipoDeBanca = :tipoDeBanca",
				Long.class)
					.setParameter("tipoDeBanca", TipoDeBanca.BANCA_TCR)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> mestrado = Optional.ofNullable(
				manager.createQuery("select count(codigo) from BancaExaminadora a where year(data) = :ano and a.tipoDeBanca = :tipoDeBanca",
				Long.class)
					.setParameter("tipoDeBanca", TipoDeBanca.BANCA_MESTRADO)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> doutorado = Optional.ofNullable(
				manager.createQuery("select count(codigo) from BancaExaminadora a where year(data) = :ano and a.tipoDeBanca = :tipoDeBanca",
				Long.class)
					.setParameter("tipoDeBanca", TipoDeBanca.BANCA_DOUTORADO)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		bancas.add(0, tcc.orElse(Long.valueOf(0)));
		bancas.add(1, tcr.orElse(Long.valueOf(0)));
		bancas.add(2, mestrado.orElse(Long.valueOf(0)));
		bancas.add(3, doutorado.orElse(Long.valueOf(0)));
		
		return bancas;
	}

	@Override
	public BigDecimal economiaComBancasAnoAtual() {
		Optional<BigDecimal> optional = Optional.ofNullable(manager.createQuery(
				"select sum(a.valorTotal) from BancaExaminadora a where year(data) = :ano", BigDecimal.class)
				.setParameter("ano", Year.now().getValue()).getSingleResult());
		
		return optional.orElse(BigDecimal.ZERO);
	}

	@Override
	public Long bancasPorTipoAnoAtual(TipoDeBanca tipoDeBanca) {
		Optional<Long> banca = Optional.ofNullable(
				manager.createQuery("select count(codigo) from BancaExaminadora a where year(data) = :ano and a.tipoDeBanca = :tipoDeBanca",
				Long.class)
					.setParameter("tipoDeBanca", tipoDeBanca)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		return banca.orElse(Long.valueOf(0));
	}
	

}
