package com.rev.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rev.modelo.Usuario;
import com.rev.repository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Optional<Usuario> usuariosOptional = usuarioRepository.porEmailEAtivo(email);
		Usuario usuario = usuariosOptional
				.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou Senha incorretos"));

		return new UsuarioSistema(usuario, getPermissoes(usuario));
	}

	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authority = new HashSet<SimpleGrantedAuthority>();

		List<String> permissoes = usuarioRepository.permissoes(usuario);

		permissoes.forEach(p -> authority.add(new SimpleGrantedAuthority(p.toUpperCase())));

		return authority;
	}

}
