package com.rev.repository.helper.usuarios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rev.modelo.Usuario;
import com.rev.repository.filtro.UsuarioFiltro;

public interface UsuarioRepositoryQueries {
	
	
	public Optional<Usuario> porEmailEAtivo(String email);
	
	public List<String> permissoes(Usuario usuario);
	
	
//	public List<Usuario> filtrar(UsuarioFiltro filtro);
	
	public Page<Usuario> filtrar(UsuarioFiltro filtro, Pageable paginacao);
	
	
	public Usuario buscarPorGrupos(Long codigo);
	
	
}
