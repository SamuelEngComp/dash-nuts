package com.rev.repository.helper.usuarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.rev.modelo.Atividade;
import com.rev.modelo.Grupo;
import com.rev.modelo.Usuario;
import com.rev.modelo.UsuarioGrupo;
import com.rev.repository.filtro.UsuarioFiltro;
import com.rev.utils.PaginacaoUtils;

public class UsuarioRepositoryImpl implements UsuarioRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtils paginacaoUtils;

	@Override
	public Optional<Usuario> porEmailEAtivo(String email) {

		return manager.createQuery("from Usuario where lower(email) = lower(:email) and ativo = true", Usuario.class)
				.setParameter("email", email).getResultList().stream().findFirst();
	}

	@Override
	public List<String> permissoes(Usuario usuario) {
		return manager
				.createQuery("select distinct p.nome from Usuario u inner join u.grupos g "
						+ "inner join g.permissoes p where u = :usuario", String.class)
				.setParameter("usuario", usuario).getResultList();
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Transactional
	 * 
	 * @Override public List<Usuario> filtrar(UsuarioFiltro filtro) {
	 * 
	 * @SuppressWarnings("deprecation") Criteria criteria =
	 * manager.unwrap(Session.class).createCriteria(Usuario.class);
	 * 
	 * criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	 * adicionarFiltro(filtro, criteria); return criteria.list(); // new
	 * PageImpl<Usuario>(criteria.list(), paginacao, total(filtro)); }
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public Page<Usuario> filtrar(UsuarioFiltro filtro, Pageable paginacao) {

		@SuppressWarnings("deprecation")
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);

//		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		paginacaoUtils.preparar(criteria, paginacao);
		adicionarFiltro(filtro, criteria);

		List<Usuario> filtrados = criteria.list();

		// inicializando os grupos
		filtrados.forEach(u -> Hibernate.initialize(u.getGrupos()));

		return new PageImpl<>(filtrados, paginacao, total(filtro));
//				new PageImpl<Usuario>(criteria.list(), paginacao, total(filtro));
	}

	private long total(UsuarioFiltro filtro) {
		Session session = manager.unwrap(Session.class);
		session.setDefaultReadOnly(true);
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Usuario.class);

		adicionarFiltro(filtro, criteria);

		criteria.setProjection(Projections.rowCount());

		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(UsuarioFiltro filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getPrimeiroNome())) {
				criteria.add(Restrictions.ilike("primeiroNome", filtro.getPrimeiroNome(), MatchMode.ANYWHERE));
			}

			if (!StringUtils.isEmpty(filtro.getUltimoNome())) {
				criteria.add(Restrictions.ilike("ultimoNome", filtro.getUltimoNome(), MatchMode.ANYWHERE));
			}

			if (!StringUtils.isEmpty(filtro.getEmail())) {
				criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.START));
			}

//			criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
			if (filtro.getGrupos() != null && !filtro.getGrupos().isEmpty()) {

				List<Criterion> subqueries = new ArrayList<>();

				for (Long codigoGrupo : filtro.getGrupos().stream().mapToLong(Grupo::getCodigo).toArray()) {

					// consulta separada
					DetachedCriteria dc = DetachedCriteria.forClass(UsuarioGrupo.class);
					dc.add(Restrictions.eq("codigo.grupo.codigo", codigoGrupo));
					dc.setProjection(Projections.property("codigo.usuario"));

					subqueries.add(Subqueries.propertyIn("codigo", dc));
				}

				Criterion[] criterions = new Criterion[subqueries.size()];
				criteria.add(Restrictions.and(subqueries.toArray(criterions)));
			}

		}

	}

	@Override
	public Usuario buscarPorGrupos(Long codigo) {
		@SuppressWarnings("deprecation")
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("codigo", codigo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Usuario) criteria.uniqueResult();
	}

}
