package com.rev.repository.helper.atividade;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.util.StringUtils;

import com.rev.modelo.Atividade;
import com.rev.modelo.FormaConexao;
import com.rev.modelo.TipoAtividade;
import com.rev.repository.filtro.AtividadeFiltro;

public class AtividadeRepositoryImpl implements AtividadeRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Page<Atividade> filtrar(AtividadeFiltro filtro, Pageable paginacao) {

		Session session = manager.unwrap(Session.class);
		session.setDefaultReadOnly(true);
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Atividade.class);

		int paginaAtual = paginacao.getPageNumber();
		int totalRegistroPorPagina = paginacao.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistroPorPagina;

		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistroPorPagina);

		adicionarFiltro(filtro, criteria);

		return new PageImpl<Atividade>(criteria.list(), paginacao, total(filtro));
	}

	private long total(AtividadeFiltro filtro) {
		Session session = manager.unwrap(Session.class);
		session.setDefaultReadOnly(true);
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Atividade.class);

		adicionarFiltro(filtro, criteria);

		criteria.setProjection(Projections.rowCount());

		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(AtividadeFiltro filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getDescriminacao())) {
				criteria.add(Restrictions.ilike("descriminacao", filtro.getDescriminacao(), MatchMode.ANYWHERE));
			}

			if (filtro.getTipo() != null) {
				criteria.add(Restrictions.eq("tipo", filtro.getTipo()));
			}

			if (filtro.getFormaConexao() != null) {
				criteria.add(Restrictions.eq("formaConexao", filtro.getFormaConexao()));
			}

			if (filtro.getQtdParticipantes() != null) {
				criteria.add(Restrictions.le("qtdParticipantes", filtro.getQtdParticipantes()));
			}

			if (!StringUtils.isEmpty(filtro.getTema())) {
				criteria.add(Restrictions.ilike("tema", filtro.getTema(), MatchMode.ANYWHERE));
			}

			if (filtro.getDuracao() != null) {
				criteria.add(Restrictions.le("duracao", filtro.getDuracao()));
			}

			if (filtro.getDataDe() != null) {
				criteria.add(Restrictions.ge("data", filtro.getDataDe()));
			}

			if (filtro.getDataAte() != null) {
				criteria.add(Restrictions.le("data", filtro.getDataAte()));
			}

		}
	}

	/**
	 * CAPTURANDO TOTAL GERAL
	 */
	@Override
	public Long totalAtividade() {
		Optional<Long> optional = Optional
				.ofNullable(manager.createQuery("select count(codigo) from Atividade", Long.class).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalParticipantes() {
		Optional<Long> optional = Optional.ofNullable(
				manager.createQuery("select sum(a.qtdParticipantes) from Atividade a", Long.class).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalWebconferencias() {
		Optional<Long> optional = Optional
				.ofNullable(manager
						.createQuery("select count(a.formaConexao) "
								+ "from Atividade a where a.formaConexao = :formaConexao", Long.class)
						.setParameter("formaConexao", FormaConexao.WEBCONFERENCIA).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalVideoConferencia() {
		Optional<Long> optional = Optional
				.ofNullable(manager
						.createQuery("select count(a.formaConexao) "
								+ "from Atividade a where a.formaConexao = :formaConexao", Long.class)
						.setParameter("formaConexao", FormaConexao.VIDEOCONFERENCIA).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	public Long totalGeralAtividadesPorTipoEbserh() {
		Optional<Long> optional = Optional.ofNullable(
				manager.createQuery("select count(a.tipo) " + "from Atividade a where a.tipo = :tipo", Long.class)
						.setParameter("tipo", TipoAtividade.EBSERH).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalGeralAtividadesPorTipoSessaoClinica() {
		Optional<Long> optional = Optional.ofNullable(
				manager.createQuery("select count(a.tipo) " + "from Atividade a where a.tipo = :tipo", Long.class)
						.setParameter("tipo", TipoAtividade.SESSAO_CLINICA).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalGeralAtividadesPorTipoReuniaoAdministrativa() {
		Optional<Long> optional = Optional.ofNullable(
				manager.createQuery("select count(a.tipo) " + "from Atividade a where a.tipo = :tipo", Long.class)
						.setParameter("tipo", TipoAtividade.REUNIAO_ADMINISTRATIVA).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalGeralAtividadesPorTipoTransmissaoCirurgia() {
		Optional<Long> optional = Optional.ofNullable(
				manager.createQuery("select count(a.tipo) " + "from Atividade a where a.tipo = :tipo", Long.class)
						.setParameter("tipo", TipoAtividade.CIRURGIA).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalGeralAtividadesPorTipoBancaExaminadora() {
		Optional<Long> optional = Optional.ofNullable(
				manager.createQuery("select count(a.tipo) " + "from Atividade a where a.tipo = :tipo", Long.class)
						.setParameter("tipo", TipoAtividade.BANCA).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalGeralAtividadesPorTipoSIG() {
		Optional<Long> optional = Optional.ofNullable(
				manager.createQuery("select count(a.tipo) " + "from Atividade a where a.tipo = :tipo", Long.class)
						.setParameter("tipo", TipoAtividade.SIG).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalGeralPontosConectados() {
		Optional<Long> optional = Optional.ofNullable(manager
				.createQuery("select sum(a.pontosConectados) " + "from Atividade a", Long.class).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	/**
	 * ##########################################################################
	 * ############# CAPTURANDO OS DADOS DO ANO ATUAL ##########################
	 * ##########################################################################
	 */
	@Override
	public Long totalAtividadePorAno() {
		Optional<Long> optional = Optional.ofNullable(
				manager.createQuery("select count(codigo) from Atividade where year(data) = :ano", Long.class)
						.setParameter("ano", Year.now().getValue()).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalParticipantesPorAno() {
		Optional<Long> optional = Optional.ofNullable(manager
				.createQuery("select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano", Long.class)
				.setParameter("ano", Year.now().getValue()).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalHorasPorAno() {
		Optional<Long> optional = Optional.ofNullable(
				manager.createQuery("select sum(a.duracao)/60 from Atividade a where year(data) = :ano", Long.class)
						.setParameter("ano", Year.now().getValue()).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalPontosConectadosPorAno() {
		Optional<Long> optional = Optional.ofNullable(manager
				.createQuery("select sum(a.pontosConectados) from Atividade a where year(data) = :ano", Long.class)
				.setParameter("ano", Year.now().getValue()).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalAtividadePorTipo(TipoAtividade tipo) {
		Optional<Long> optional = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where a.tipo = :tipo and year(data) = :ano",
						Long.class)
				.setParameter("tipo", tipo).setParameter("ano", Year.now().getValue()).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalAtividadePorFormaConexao(FormaConexao formaDeConexao) {
		Optional<Long> optional = Optional.ofNullable(manager.createQuery(
				"select count(codigo) from Atividade a where a.formaConexao = :formaConexao and year(data) = :ano",
				Long.class).setParameter("formaConexao", formaDeConexao).setParameter("ano", Year.now().getValue())
				.getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	/**
	 * ##########################################################################
	 * ############# CAPTURANDO OS DADOS DO ANO ATUAL POR MÊS ###################
	 * ##########################################################################
	 */

	@Override
	public List<Long> atividadesPorMes() {

		Optional<Long> janeiro = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 1).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> fevereiro = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 2).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> marco = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 3).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> abril = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 4).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> maio = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 5).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> junho = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 6).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> julho = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 7).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> agosto = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 8).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> setembro = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 9).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> outubro = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 10).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> novembro = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 11).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> dezembro = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 12).setParameter("ano", Year.now().getValue()).getSingleResult());

		List<Long> valores = new ArrayList<Long>();
		valores.add(0, janeiro.orElse(Long.valueOf(0)));
		valores.add(1, fevereiro.orElse(Long.valueOf(0)));
		valores.add(2, marco.orElse(Long.valueOf(0)));
		valores.add(3, abril.orElse(Long.valueOf(0)));
		valores.add(4, maio.orElse(Long.valueOf(0)));
		valores.add(5, junho.orElse(Long.valueOf(0)));
		valores.add(6, julho.orElse(Long.valueOf(0)));
		valores.add(7, agosto.orElse(Long.valueOf(0)));
		valores.add(8, setembro.orElse(Long.valueOf(0)));
		valores.add(9, outubro.orElse(Long.valueOf(0)));
		valores.add(10, novembro.orElse(Long.valueOf(0)));
		valores.add(11, dezembro.orElse(Long.valueOf(0)));
		return valores;
	}

	@Override
	public List<Long> formasConexaoUtilizadas() {

		List<Long> formas = new ArrayList<Long>();

		Optional<Long> webconferencias = Optional.ofNullable(manager.createQuery(
				"select count(a.formaConexao) from Atividade a where a.formaConexao = :formaConexao and year(data) = :ano",
				Long.class).setParameter("ano", Year.now().getValue())
				.setParameter("formaConexao", FormaConexao.WEBCONFERENCIA).getSingleResult());

		Optional<Long> videoconferencias = Optional.ofNullable(manager.createQuery(
				"select count(a.formaConexao) from Atividade a where a.formaConexao = :formaConexao and year(data) = :ano",
				Long.class).setParameter("ano", Year.now().getValue())
				.setParameter("formaConexao", FormaConexao.VIDEOCONFERENCIA).getSingleResult());

		Optional<Long> gravacoes = Optional.ofNullable(manager
				.createQuery("select count(a.tipo) from Atividade a where a.tipo = :tipo and year(data) = :ano",
						Long.class)
				.setParameter("ano", Year.now().getValue()).setParameter("tipo", TipoAtividade.GRAVACAO_DE_VIDEO)
				.getSingleResult());

		formas.add(0, webconferencias.orElse(Long.valueOf(0)));
		formas.add(1, videoconferencias.orElse(Long.valueOf(0)));
		formas.add(2, gravacoes.orElse(Long.valueOf(0)));

		return formas;
	}

	@Override
	public List<Long> participantesLocalPorMes() {

		List<Long> valores = new ArrayList<Long>();

		Optional<Long> janeiro = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 1).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> fevereiro = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 2).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> marco = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 3).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> abril = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 4).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> maio = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 5).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> junho = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 6).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> julho = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 7).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> agosto = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 8).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> setembro = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 9).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> outubro = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 10).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> novembro = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 11).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> dezembro = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 12).setParameter("ano", Year.now().getValue()).getSingleResult());

		valores.add(0, janeiro.orElse(Long.valueOf(0)));
		valores.add(1, fevereiro.orElse(Long.valueOf(0)));
		valores.add(2, marco.orElse(Long.valueOf(0)));
		valores.add(3, abril.orElse(Long.valueOf(0)));
		valores.add(4, maio.orElse(Long.valueOf(0)));
		valores.add(5, junho.orElse(Long.valueOf(0)));

		valores.add(6, julho.orElse(Long.valueOf(0)));
		valores.add(7, agosto.orElse(Long.valueOf(0)));
		valores.add(8, setembro.orElse(Long.valueOf(0)));
		valores.add(9, outubro.orElse(Long.valueOf(0)));
		valores.add(10, novembro.orElse(Long.valueOf(0)));
		valores.add(11, dezembro.orElse(Long.valueOf(0)));

		return valores;

	}

	@Override
	public List<Long> pontosConectados() {
		List<Long> valores = new ArrayList<Long>();

		Optional<Long> janeiro = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 1).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> fevereiro = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 2).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> marco = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 3).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> abril = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 4).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> maio = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 5).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> junho = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 6).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> julho = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 7).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> agosto = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 8).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> setembro = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 9).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> outubro = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 10).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> novembro = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 11).setParameter("ano", Year.now().getValue()).getSingleResult());

		Optional<Long> dezembro = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 12).setParameter("ano", Year.now().getValue()).getSingleResult());

		valores.add(0, janeiro.orElse(Long.valueOf(0)));
		valores.add(1, fevereiro.orElse(Long.valueOf(0)));
		valores.add(2, marco.orElse(Long.valueOf(0)));
		valores.add(3, abril.orElse(Long.valueOf(0)));
		valores.add(4, maio.orElse(Long.valueOf(0)));
		valores.add(5, junho.orElse(Long.valueOf(0)));

		valores.add(6, julho.orElse(Long.valueOf(0)));
		valores.add(7, agosto.orElse(Long.valueOf(0)));
		valores.add(8, setembro.orElse(Long.valueOf(0)));
		valores.add(9, outubro.orElse(Long.valueOf(0)));
		valores.add(10, novembro.orElse(Long.valueOf(0)));
		valores.add(11, dezembro.orElse(Long.valueOf(0)));

		return valores;
	}

	/***
	 * ####################################################################################################
	 * ##### BUSCANDO QUANTIDADE DE ATIVIDADE POR TIPO DE ATIVIDADE SEPARADAMENTE
	 * #####
	 * ####################################################################################################
	 ***/

	@Override
	public Long quantidadeAtividadePorTipo(TipoAtividade tipo, int ano) {
		Query consulta = manager
				.createQuery("select count(codigo) from Atividade a where a.tipo = :tipo and year(data) = :ano")
				.setParameter("tipo", tipo).setParameter("ano", ano);

		Long numerosSomados = (Long) consulta.getSingleResult();
		return numerosSomados;
	}

	@Override
	public Long somaAtividadePorFormaDeConexao(FormaConexao formaDeConexao) {
		Query consulta = manager
				.createQuery("select count(codigo) from Atividade a where a.formaConexao = :formaConexao")
				.setParameter("formaConexao", formaDeConexao);

		Long numerosSomados = (Long) consulta.getSingleResult();
		return numerosSomados;
	}

	/***
	 * ####################################################################################################
	 * ##### CONSULTAS 2019, 2018, 2017, 2016 e 2015 #####
	 * ####################################################################################################
	 ***/

	@Override
	public Long painelTotalAtividadesPorAno(int ano) {
		Optional<Long> optional = Optional.ofNullable(
				manager.createQuery("select count(codigo) from Atividade a where year(data) = :ano", Long.class)
						.setParameter("ano", ano).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long painelTotalParticipantesPorAno(int ano) {
		Optional<Long> optional = Optional.ofNullable(manager
				.createQuery("select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano", Long.class)
				.setParameter("ano", ano).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long painelTotalHorasAtividadePorAno(int ano) {
		Optional<Long> optional = Optional.ofNullable(
				manager.createQuery("select sum(a.duracao)/60 from Atividade a where year(data) = :ano", Long.class)
						.setParameter("ano", ano).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long painelTotalTipoDeAtividadePorAno(TipoAtividade tipo, int ano) {
		Optional<Long> optional = Optional.ofNullable(
				manager.createQuery("select count(codigo) from Atividade a where a.tipo = :tipo and year(data) = :ano",
						Long.class).setParameter("ano", ano).setParameter("tipo", tipo).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long painelTotalFormaDeConexaoPorAno(FormaConexao formaDeConexao, int ano) {
		Optional<Long> optional = Optional.ofNullable(manager.createQuery(
				"select count(codigo) from Atividade a where a.formaConexao = :formaConexao and year(data) = :ano",
				Long.class).setParameter("ano", ano).setParameter("formaConexao", formaDeConexao).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long painelTotalPontosConectadosPorAno(int ano) {
		Optional<Long> optional = Optional.ofNullable(manager
				.createQuery("select sum(a.pontosConectados) from Atividade a where year(data) = :ano", Long.class)
				.setParameter("ano", ano).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public List<Long> comparativoAnual() {

		List<Long> valores = new ArrayList<Long>();

		valores.add(0, painelTotalAtividadesPorAno(2015));
		valores.add(1, painelTotalAtividadesPorAno(2016));
		valores.add(2, painelTotalAtividadesPorAno(2017));
		valores.add(3, painelTotalAtividadesPorAno(2018));
		valores.add(4, painelTotalAtividadesPorAno(2019));
		valores.add(5, painelTotalAtividadesPorAno(2020));
		valores.add(6, painelTotalAtividadesPorAno(2021));
		valores.add(7, painelTotalAtividadesPorAno(Year.now().getValue()));

		return valores;
	}

	@Override
	public HashMap<String, List<Long>> comparativoFinal() {

		List<Long> ano2016 = new ArrayList<Long>();
		List<Long> ano2017 = new ArrayList<Long>();
		List<Long> ano2018 = new ArrayList<Long>();
		List<Long> ano2019 = new ArrayList<Long>();
		List<Long> ano2020 = new ArrayList<Long>();
		List<Long> ano2021 = new ArrayList<Long>();
		List<Long> anoAtual = new ArrayList<Long>();

		ano2016.add(0, painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, 2016));
		ano2016.add(1, painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, 2016));
		ano2016.add(2, painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, 2016));
		ano2016.add(3, painelTotalTipoDeAtividadePorAno(TipoAtividade.EBSERH, 2016));
		ano2016.add(4, painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, 2016));

		ano2017.add(0, painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, 2017));
		ano2017.add(1, painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, 2017));
		ano2017.add(2, painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, 2017));
		ano2017.add(3, painelTotalTipoDeAtividadePorAno(TipoAtividade.EBSERH, 2017));
		ano2017.add(4, painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, 2017));

		ano2018.add(0, painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, 2018));
		ano2018.add(1, painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, 2018));
		ano2018.add(2, painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, 2018));
		ano2018.add(3, painelTotalTipoDeAtividadePorAno(TipoAtividade.EBSERH, 2018));
		ano2018.add(4, painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, 2018));

		ano2019.add(0, painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, 2019));
		ano2019.add(1, painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, 2019));
		ano2019.add(2, painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, 2019));
		ano2019.add(3, painelTotalTipoDeAtividadePorAno(TipoAtividade.EBSERH, 2019));
		ano2019.add(4, painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, 2019));

		ano2020.add(0, painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, 2020));
		ano2020.add(1, painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, 2020));
		ano2020.add(2, painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, 2020));
		ano2020.add(3, painelTotalTipoDeAtividadePorAno(TipoAtividade.EBSERH, 2020));
		ano2020.add(4, painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, 2020));

		anoAtual.add(0, painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, Year.now().getValue()));
		anoAtual.add(1, painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, Year.now().getValue()));
		anoAtual.add(2, painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, Year.now().getValue()));
		anoAtual.add(3, painelTotalTipoDeAtividadePorAno(TipoAtividade.EBSERH, Year.now().getValue()));
		anoAtual.add(4, painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, Year.now().getValue()));

		ano2021.add(0, painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, 2021));
		ano2021.add(1, painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, 2021));
		ano2021.add(2, painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, 2021));
		ano2021.add(3, painelTotalTipoDeAtividadePorAno(TipoAtividade.EBSERH, 2021));
		ano2021.add(4, painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, 2021));

		HashMap<String, List<Long>> valores = new HashMap<String, List<Long>>();

		valores.put("2016", ano2016);
		valores.put("2017", ano2017);
		valores.put("2018", ano2018);
		valores.put("2019", ano2019);
		valores.put("2020", ano2020);
		valores.put("2021", ano2021);
		valores.put("Atual", anoAtual);

		return valores;
	}

	@Override
	public List<Long> pontosConectados(int ano) {
		List<Long> valores = new ArrayList<Long>();

		Optional<Long> janeiro = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 1).setParameter("ano", ano).getSingleResult());

		Optional<Long> fevereiro = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 2).setParameter("ano", ano).getSingleResult());

		Optional<Long> marco = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 3).setParameter("ano", ano).getSingleResult());

		Optional<Long> abril = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 4).setParameter("ano", ano).getSingleResult());

		Optional<Long> maio = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 5).setParameter("ano", ano).getSingleResult());

		Optional<Long> junho = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 6).setParameter("ano", ano).getSingleResult());

		Optional<Long> julho = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 7).setParameter("ano", ano).getSingleResult());

		Optional<Long> agosto = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 8).setParameter("ano", ano).getSingleResult());

		Optional<Long> setembro = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 9).setParameter("ano", ano).getSingleResult());

		Optional<Long> outubro = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 10).setParameter("ano", ano).getSingleResult());

		Optional<Long> novembro = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 11).setParameter("ano", ano).getSingleResult());

		Optional<Long> dezembro = Optional.ofNullable(manager.createQuery(
				"select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 12).setParameter("ano", ano).getSingleResult());

		valores.add(0, janeiro.orElse(Long.valueOf(0)));
		valores.add(1, fevereiro.orElse(Long.valueOf(0)));
		valores.add(2, marco.orElse(Long.valueOf(0)));
		valores.add(3, abril.orElse(Long.valueOf(0)));
		valores.add(4, maio.orElse(Long.valueOf(0)));
		valores.add(5, junho.orElse(Long.valueOf(0)));

		valores.add(6, julho.orElse(Long.valueOf(0)));
		valores.add(7, agosto.orElse(Long.valueOf(0)));
		valores.add(8, setembro.orElse(Long.valueOf(0)));
		valores.add(9, outubro.orElse(Long.valueOf(0)));
		valores.add(10, novembro.orElse(Long.valueOf(0)));
		valores.add(11, dezembro.orElse(Long.valueOf(0)));

		return valores;
	}

	@Override
	public List<Long> participantesLocalPorMes(int ano) {
		List<Long> valores = new ArrayList<Long>();

		Optional<Long> janeiro = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 1).setParameter("ano", ano).getSingleResult());

		Optional<Long> fevereiro = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 2).setParameter("ano", ano).getSingleResult());

		Optional<Long> marco = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 3).setParameter("ano", ano).getSingleResult());

		Optional<Long> abril = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 4).setParameter("ano", ano).getSingleResult());

		Optional<Long> maio = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 5).setParameter("ano", ano).getSingleResult());

		Optional<Long> junho = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 6).setParameter("ano", ano).getSingleResult());

		Optional<Long> julho = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 7).setParameter("ano", ano).getSingleResult());

		Optional<Long> agosto = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 8).setParameter("ano", ano).getSingleResult());

		Optional<Long> setembro = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 9).setParameter("ano", ano).getSingleResult());

		Optional<Long> outubro = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 10).setParameter("ano", ano).getSingleResult());

		Optional<Long> novembro = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 11).setParameter("ano", ano).getSingleResult());

		Optional<Long> dezembro = Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class).setParameter("mes", 12).setParameter("ano", ano).getSingleResult());

		valores.add(0, janeiro.orElse(Long.valueOf(0)));
		valores.add(1, fevereiro.orElse(Long.valueOf(0)));
		valores.add(2, marco.orElse(Long.valueOf(0)));
		valores.add(3, abril.orElse(Long.valueOf(0)));
		valores.add(4, maio.orElse(Long.valueOf(0)));
		valores.add(5, junho.orElse(Long.valueOf(0)));

		valores.add(6, julho.orElse(Long.valueOf(0)));
		valores.add(7, agosto.orElse(Long.valueOf(0)));
		valores.add(8, setembro.orElse(Long.valueOf(0)));
		valores.add(9, outubro.orElse(Long.valueOf(0)));
		valores.add(10, novembro.orElse(Long.valueOf(0)));
		valores.add(11, dezembro.orElse(Long.valueOf(0)));

		return valores;
	}

	@Override
	public List<Long> atividadesPorMes(int ano) {
		Optional<Long> janeiro = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 1).setParameter("ano", ano).getSingleResult());

		Optional<Long> fevereiro = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 2).setParameter("ano", ano).getSingleResult());

		Optional<Long> marco = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 3).setParameter("ano", ano).getSingleResult());

		Optional<Long> abril = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 4).setParameter("ano", ano).getSingleResult());

		Optional<Long> maio = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 5).setParameter("ano", ano).getSingleResult());

		Optional<Long> junho = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 6).setParameter("ano", ano).getSingleResult());

		Optional<Long> julho = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 7).setParameter("ano", ano).getSingleResult());

		Optional<Long> agosto = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 8).setParameter("ano", ano).getSingleResult());

		Optional<Long> setembro = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 9).setParameter("ano", ano).getSingleResult());

		Optional<Long> outubro = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 10).setParameter("ano", ano).getSingleResult());

		Optional<Long> novembro = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 11).setParameter("ano", ano).getSingleResult());

		Optional<Long> dezembro = Optional.ofNullable(manager
				.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
						Long.class)
				.setParameter("mes", 12).setParameter("ano", ano).getSingleResult());

		List<Long> valores = new ArrayList<Long>();
		valores.add(0, janeiro.orElse(Long.valueOf(0)));
		valores.add(1, fevereiro.orElse(Long.valueOf(0)));
		valores.add(2, marco.orElse(Long.valueOf(0)));
		valores.add(3, abril.orElse(Long.valueOf(0)));
		valores.add(4, maio.orElse(Long.valueOf(0)));
		valores.add(5, junho.orElse(Long.valueOf(0)));
		valores.add(6, julho.orElse(Long.valueOf(0)));
		valores.add(7, agosto.orElse(Long.valueOf(0)));
		valores.add(8, setembro.orElse(Long.valueOf(0)));
		valores.add(9, outubro.orElse(Long.valueOf(0)));
		valores.add(10, novembro.orElse(Long.valueOf(0)));
		valores.add(11, dezembro.orElse(Long.valueOf(0)));
		return valores;
	}

	
	/**
	 * qual o ano que teve maior numero de atividades
	 */
	@Override
	public Long numeroMaximoDeAtividades() {
		
		//pega o ano que tem o maior numero de atividades
		List<Long> maximos = new ArrayList<Long>();
		for(int i=2015, j=0; i<2030; i++, j++) {
			maximos.add(j, painelTotalAtividadesPorAno(i));
		}
		
		return (long) Collections.max(maximos);
	}

	@Override
	public double porcentagemMaximoAtividades() {
		
		//realiza a logica da porcentagem de acordo com o maximo valor
		Long atividadesAnoAtual = painelTotalAtividadesPorAno(Year.now().getValue());
		Long maxAtividades = numeroMaximoDeAtividades();
		Double porcentagemMaxAtividades = (atividadesAnoAtual.doubleValue()/maxAtividades.doubleValue()) * 100;
		
		//caso o numero de atividades continue em crescimento, tudo fica em 100%
		if(porcentagemMaxAtividades >= 100) { 
			return (long) 100; 
		}
		
		//arrendondando a porcentagem
		return Math.round(porcentagemMaxAtividades);
	}

	
	//retorna o numero maximo de bancas examinadoras que teve no ano. 
	//essa função testa todos os anos ate 2030, iniciando de 2015
	@Override
	public Long numeroMaximoDeBancasExaminadoras() {
		
		List<Long> maximos = new ArrayList<Long>();
		for(int i=2015, j=0; i<2030; i++, j++) {
			maximos.add(j, painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, i));
		}
		
		return (long) Collections.max(maximos);
	}

	
	
	@Override
	public double porcentagemMaximaBancasExaminadoras() {
		Long bancasExaminadorasAnoAtual = painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, Year.now().getValue());
		Long maxBancasExaminadoras = numeroMaximoDeBancasExaminadoras();
		Double porcentagemMaxBancaExaminadora = (bancasExaminadorasAnoAtual.doubleValue()/maxBancasExaminadoras.doubleValue()) * 100;
		
		if(porcentagemMaxBancaExaminadora >= 100) {
			return (long) 100;
		}
		return Math.round(porcentagemMaxBancaExaminadora);
	}

	@Override
	public Long numeroMaximoDeGravacoes() {
		List<Long> maximos = new ArrayList<Long>();
		for(int i=2015, j=0; i<2030; i++, j++) {
			maximos.add(j, painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, i));
		}
		
		return (long) Collections.max(maximos);
	}

	@Override
	public double porcentagemMaximoGravacoes() {
		Long gravacoesAnoAtual = painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, Year.now().getValue());
		Long maxGravacoes = numeroMaximoDeGravacoes();
		Double porcentagemMaxGravacoes = (gravacoesAnoAtual.doubleValue()/maxGravacoes.doubleValue()) * 100;
		
		if(porcentagemMaxGravacoes >= 100) {
			return (long) 100;
		}
		return Math.round(porcentagemMaxGravacoes);
	}

	
	
	
	@Override
	public Long numeroMaximoDeParticipantesLocal() {
		List<Long> maximos = new ArrayList<Long>();
		for(int i=2015, j=0; i<2030; i++, j++) {
			maximos.add(j, painelTotalParticipantesPorAno(i));
		}
		
		return (long) Collections.max(maximos);
	}

	@Override
	public double porcentagemMaximaParticipantesLocal() {
		Long participantesAnoAtual = painelTotalParticipantesPorAno(Year.now().getValue());
		Long maxParticipantes = numeroMaximoDeParticipantesLocal();
		Double porcentagemMaxParticipantes = (participantesAnoAtual.doubleValue()/maxParticipantes.doubleValue()) * 100;
		
		if(porcentagemMaxParticipantes >= 100) {
			return (long) 100;
		}
		return Math.round(porcentagemMaxParticipantes);
	}

	
	
	@Override
	public Long numeroMaximoDeSessoesClinicas() {
		List<Long> maximos = new ArrayList<Long>();
		for(int i=2015, j=0; i<2030; i++, j++) {
			maximos.add(j, painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, i));
		}
		
		return (long) Collections.max(maximos);
	}

	@Override
	public double porcentagemMaximaSessoesClinicas() {
		Long sessoesClinicasAnoAtual = painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, Year.now().getValue());
		Long maxSessoes = numeroMaximoDeSessoesClinicas();
		Double porcentagemMaxSessoes = (sessoesClinicasAnoAtual.doubleValue()/maxSessoes.doubleValue()) * 100;
		
		if(porcentagemMaxSessoes >= 100) {
			return (long) 100;
		}
		return Math.round(porcentagemMaxSessoes);
	}

	@Override
	public Long numeroMaximoDeWebconferencias() {
		List<Long> maximos = new ArrayList<Long>();
		for(int i=2015, j=0; i<2030; i++, j++) {
			maximos.add(j, painelTotalFormaDeConexaoPorAno(FormaConexao.WEBCONFERENCIA, i));
		}
		
		return (long) Collections.max(maximos);
	}

	@Override
	public double porcentagemMaximaWebconferencias() {
		Long webconferenciaAnoAtual = painelTotalFormaDeConexaoPorAno(FormaConexao.WEBCONFERENCIA, Year.now().getValue());
		Long maxWebs = numeroMaximoDeWebconferencias();
		Double porcentagemMaxWebs = (webconferenciaAnoAtual.doubleValue()/maxWebs.doubleValue()) * 100;
		
		if(porcentagemMaxWebs >= 100) {
			return (long) 100;
		}
		return Math.round(porcentagemMaxWebs);
	}

	@Override
	public Long numeroMaximoDeSig() {
		List<Long> maximos = new ArrayList<Long>();
		for(int i=2015, j=0; i<2030; i++, j++) {
			maximos.add(j, painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, i));
		}
		
		return (long) Collections.max(maximos);
	}

	@Override
	public double porcentagemMaximaSig() {
		Long sigAnoAtual = painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, Year.now().getValue());
		Long maxSig = numeroMaximoDeSig();
		Double porcentagemMaxSig = (sigAnoAtual.doubleValue()/maxSig.doubleValue()) * 100;
		
		if(porcentagemMaxSig >= 100) {
			return (long) 100;
		}
		return Math.round(porcentagemMaxSig);
	}

	@Override
	public Long numeroMaximoHorasDeAtividade() {
		List<Long> maximos = new ArrayList<Long>();
		for(int i=2015, j=0; i<2030; i++, j++) {
			maximos.add(j, painelTotalHorasAtividadePorAno(i));
		}
		
		return (long) Collections.max(maximos);
	}

	@Override
	public double porcentagemMaximaHorasDeAtividade() {
		Long horasAnoAtual = painelTotalHorasAtividadePorAno(Year.now().getValue());
		Long maxHoras = numeroMaximoHorasDeAtividade();
		Double porcentagemMaxHoras = (horasAnoAtual.doubleValue()/maxHoras.doubleValue()) * 100;
		
		if(porcentagemMaxHoras >= 100) {
			return (long) 100;
		}
		return Math.round(porcentagemMaxHoras);
	}

	@Override
	public Long numeroMaximoDePontosConectados() {
		List<Long> maximos = new ArrayList<Long>();
		for(int i=2015, j=0; i<2030; i++, j++) {
			maximos.add(j, painelTotalPontosConectadosPorAno(i));
		}
		
		return (long) Collections.max(maximos);
	}

	@Override
	public double porcentagemMaximaDePontosConectados() {
		Long pontosConectadosAnoAtual = painelTotalPontosConectadosPorAno(Year.now().getValue());
		Long maxPontosConectados = numeroMaximoDePontosConectados();
		Double porcentagemMaxPontosConectados = (pontosConectadosAnoAtual.doubleValue()/maxPontosConectados.doubleValue()) * 100;
		
		if(porcentagemMaxPontosConectados >= 100) {
			return (long) 100;
		}
		return Math.round(porcentagemMaxPontosConectados);
	}

	

}
