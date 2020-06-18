package com.rev.repository.helper.atividade;

import java.time.LocalDate;
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
		Optional<Long> optional	= Optional.ofNullable(manager.createQuery("select count(codigo) from Atividade", 
			Long.class).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}
	
	@Override
	public Long totalParticipantes() {
		Optional<Long> optional	= Optional.ofNullable(manager.createQuery("select sum(a.qtdParticipantes) from Atividade a", 
				Long.class).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalWebconferencias() {
		Optional<Long> optional	= Optional.ofNullable(manager.createQuery("select count(a.formaConexao) "
				+ "from Atividade a where a.formaConexao = :formaConexao", 
				Long.class).setParameter("formaConexao", FormaConexao.WEBCONFERENCIA).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalVideoConferencia() {
		Optional<Long> optional	= Optional.ofNullable(manager.createQuery("select count(a.formaConexao) "
				+ "from Atividade a where a.formaConexao = :formaConexao", 
				Long.class).setParameter("formaConexao", FormaConexao.VIDEOCONFERENCIA).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}
	
	
	public Long totalGeralAtividadesPorTipoEbserh() {
		Optional<Long> optional	= Optional.ofNullable(manager.createQuery("select count(a.tipo) "
				+ "from Atividade a where a.tipo = :tipo", 
				Long.class).setParameter("tipo", TipoAtividade.EBSERH).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}
	
	
	@Override
	public Long totalGeralAtividadesPorTipoSessaoClinica() {
		Optional<Long> optional	= Optional.ofNullable(manager.createQuery("select count(a.tipo) "
				+ "from Atividade a where a.tipo = :tipo", 
				Long.class).setParameter("tipo", TipoAtividade.SESSAO_CLINICA).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalGeralAtividadesPorTipoReuniaoAdministrativa() {
		Optional<Long> optional	= Optional.ofNullable(manager.createQuery("select count(a.tipo) "
				+ "from Atividade a where a.tipo = :tipo", 
				Long.class).setParameter("tipo", TipoAtividade.REUNIAO_ADMINISTRATIVA).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalGeralAtividadesPorTipoTransmissaoCirurgia() {
		Optional<Long> optional	= Optional.ofNullable(manager.createQuery("select count(a.tipo) "
				+ "from Atividade a where a.tipo = :tipo", 
				Long.class).setParameter("tipo", TipoAtividade.CIRURGIA).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalGeralAtividadesPorTipoBancaExaminadora() {
		Optional<Long> optional	= Optional.ofNullable(manager.createQuery("select count(a.tipo) "
				+ "from Atividade a where a.tipo = :tipo", 
				Long.class).setParameter("tipo", TipoAtividade.BANCA).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalGeralAtividadesPorTipoSIG() {
		Optional<Long> optional	= Optional.ofNullable(manager.createQuery("select count(a.tipo) "
				+ "from Atividade a where a.tipo = :tipo", 
				Long.class).setParameter("tipo", TipoAtividade.SIG).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long totalGeralPontosConectados() {
		Optional<Long> optional	= Optional.ofNullable(manager.createQuery("select sum(a.pontosConectados) "
				+ "from Atividade a", 
				Long.class).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}
	
	
	
	
	
	
	/**
	 * ##########################################################################
	 * ############# CAPTURANDO OS DADOS DO ANO ATUAL  ##########################
	 * ##########################################################################
	 */
	@Override
	public Long totalAtividadePorAno() { 
		Optional<Long> optional	= Optional.ofNullable(manager.createQuery("select count(codigo) from Atividade where year(data) = :ano", 
			Long.class).setParameter("ano", Year.now().getValue()).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}
	
	
	@Override
	public Long totalParticipantesPorAno() {
		Optional<Long> optional = Optional.ofNullable(manager.createQuery("select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano",
				Long.class).setParameter("ano", Year.now().getValue()).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}
	
	@Override
	public Long totalHorasPorAno() {
		Optional<Long> optional = Optional.ofNullable(manager.createQuery("select sum(a.duracao)/60 from Atividade a where year(data) = :ano",
				Long.class).setParameter("ano", Year.now().getValue()).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}
	
	@Override
	public Long totalPontosConectadosPorAno() {
		Optional<Long> optional = Optional.ofNullable(manager.createQuery("select sum(a.pontosConectados) from Atividade a where year(data) = :ano",
				Long.class).setParameter("ano", Year.now().getValue()).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}
	
	@Override
	public Long totalAtividadePorTipo(TipoAtividade tipo) {
		Optional<Long> optional	= Optional.ofNullable(manager.createQuery(
				"select count(codigo) from Atividade a where a.tipo = :tipo and year(data) = :ano", 
				Long.class).setParameter("tipo", tipo)
				.setParameter("ano", Year.now().getValue()).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}
	
	@Override
	public Long totalAtividadePorFormaConexao(FormaConexao formaDeConexao) {
		Optional<Long> optional	= Optional.ofNullable(manager.createQuery(
				"select count(codigo) from Atividade a where a.formaConexao = :formaConexao and year(data) = :ano", 
				Long.class).setParameter("formaConexao", formaDeConexao)
				.setParameter("ano", Year.now().getValue()).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}
	
	/**
	 * ##########################################################################
	 * ############# CAPTURANDO OS DADOS DO ANO ATUAL POR MÊS ###################
	 * ##########################################################################
	 */
	
	
	@Override
	public List<Long> atividadesPorMes() {
		
		Optional<Long> janeiro = Optional.ofNullable(
				manager.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 1)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());		
		
		Optional<Long> fevereiro = Optional.ofNullable(
				manager.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 2)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> marco = Optional.ofNullable(
				manager.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 3)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> abril = Optional.ofNullable(
				manager.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 4)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> maio = Optional.ofNullable(
				manager.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 5)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> junho = Optional.ofNullable(
				manager.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 6)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> julho = Optional.ofNullable(
				manager.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 7)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> agosto = Optional.ofNullable(
				manager.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 8)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> setembro = Optional.ofNullable(
				manager.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 9)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> outubro = Optional.ofNullable(
				manager.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 10)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> novembro = Optional.ofNullable(
				manager.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 11)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> dezembro = Optional.ofNullable(
				manager.createQuery("select count(codigo) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 12)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
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
				Long.class)
					.setParameter("ano", Year.now().getValue())
					.setParameter("formaConexao", FormaConexao.WEBCONFERENCIA)
					.getSingleResult());
		
		Optional<Long> videoconferencias = Optional.ofNullable(manager.createQuery(
				"select count(a.formaConexao) from Atividade a where a.formaConexao = :formaConexao and year(data) = :ano", 
				Long.class)
					.setParameter("ano", Year.now().getValue())
					.setParameter("formaConexao", FormaConexao.VIDEOCONFERENCIA)
					.getSingleResult());
		
		Optional<Long> gravacoes = Optional.ofNullable(manager.createQuery(
				"select count(a.tipo) from Atividade a where a.tipo = :tipo and year(data) = :ano", 
				Long.class)
					.setParameter("ano", Year.now().getValue())
					.setParameter("tipo", TipoAtividade.GRAVACAO_DE_VIDEO)
					.getSingleResult());
		
		formas.add(0, webconferencias.orElse(Long.valueOf(0)));
		formas.add(1, videoconferencias.orElse(Long.valueOf(0)));
		formas.add(2, gravacoes.orElse(Long.valueOf(0)));
		
		return formas;
	}
	
	
	@Override
	public List<Long> participantesLocalPorMes() {
		
		List<Long> valores = new ArrayList<Long>();
		
		Optional<Long> janeiro = Optional.ofNullable(
				manager.createQuery("select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 1)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> fevereiro = Optional.ofNullable(
				manager.createQuery("select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 2)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> marco = Optional.ofNullable(
				manager.createQuery("select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 3)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> abril = Optional.ofNullable(
				manager.createQuery("select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 4)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> maio = Optional.ofNullable(
				manager.createQuery("select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 5)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> junho = Optional.ofNullable(
				manager.createQuery("select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 6)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> julho = Optional.ofNullable(
				manager.createQuery("select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 7)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> agosto = Optional.ofNullable(
				manager.createQuery("select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 8)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> setembro = Optional.ofNullable(
				manager.createQuery("select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 9)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> outubro = Optional.ofNullable(
				manager.createQuery("select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 10)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> novembro = Optional.ofNullable(
				manager.createQuery("select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 11)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> dezembro = Optional.ofNullable(
				manager.createQuery("select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 12)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		
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
		
		Optional<Long> janeiro = Optional.ofNullable(
				manager.createQuery("select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 1)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> fevereiro = Optional.ofNullable(
				manager.createQuery("select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 2)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> marco = Optional.ofNullable(
				manager.createQuery("select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 3)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> abril = Optional.ofNullable(
				manager.createQuery("select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 4)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> maio = Optional.ofNullable(
				manager.createQuery("select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 5)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> junho = Optional.ofNullable(
				manager.createQuery("select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 6)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> julho = Optional.ofNullable(
				manager.createQuery("select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 7)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> agosto = Optional.ofNullable(
				manager.createQuery("select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 8)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> setembro = Optional.ofNullable(
				manager.createQuery("select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 9)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> outubro = Optional.ofNullable(
				manager.createQuery("select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 10)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> novembro = Optional.ofNullable(
				manager.createQuery("select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 11)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		Optional<Long> dezembro = Optional.ofNullable(
				manager.createQuery("select sum(a.pontosConectados) from Atividade a where year(data) = :ano and month(data) = :mes",
				Long.class)
					.setParameter("mes", 12)
					.setParameter("ano", Year.now().getValue())
					.getSingleResult());
		
		
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
		Query consulta = manager.createQuery("select count(codigo) from Atividade a where a.tipo = :tipo and year(data) = :ano")
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
		Optional<Long> optional	= Optional.ofNullable(manager.createQuery(
				"select count(codigo) from Atividade a where year(data) = :ano", 
				Long.class).setParameter("ano", ano).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long painelTotalParticipantesPorAno(int ano) {
		Optional<Long> optional	= Optional.ofNullable(manager.createQuery(
				"select sum(a.qtdParticipantes) from Atividade a where year(data) = :ano", 
				Long.class).setParameter("ano", ano).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long painelTotalHorasAtividadePorAno(int ano) {
		Optional<Long> optional	= Optional.ofNullable(manager.createQuery(
				"select sum(a.duracao)/60 from Atividade a where year(data) = :ano", 
				Long.class).setParameter("ano", ano).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long painelTotalTipoDeAtividadePorAno(TipoAtividade tipo, int ano) {
		Optional<Long> optional	= Optional.ofNullable(manager.createQuery(
				"select count(codigo) from Atividade a where a.tipo = :tipo and year(data) = :ano", 
				Long.class).setParameter("ano", ano).setParameter("tipo", tipo).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long painelTotalFormaDeConexaoPorAno(FormaConexao formaDeConexao, int ano) {
		Optional<Long> optional	= Optional.ofNullable(manager.createQuery(
				"select count(codigo) from Atividade a where a.formaConexao = :formaConexao and year(data) = :ano", 
				Long.class).setParameter("ano", ano).setParameter("formaConexao", formaDeConexao).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	@Override
	public Long painelTotalPontosConectadosPorAno(int ano) {
		Optional<Long> optional	= Optional.ofNullable(manager.createQuery(
				"select count(a.pontosConectados) from Atividade a where year(data) = :ano", 
				Long.class).setParameter("ano", ano).getSingleResult());
		return optional.orElse(Long.valueOf(0));
	}

	

	

				
}
