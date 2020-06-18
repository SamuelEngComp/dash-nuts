package com.rev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.rev.modelo.Usuario;
import com.rev.repository.UsuarioRepository;
import com.rev.service.exception.EmailUsuarioJaCadastradoException;
import com.rev.service.exception.SenhaObrigatoriaUsuarioException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	public void salvarUsuario(Usuario usuario) {
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new EmailUsuarioJaCadastradoException("E-mail já cadastrado");
		}

		if (usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaObrigatoriaUsuarioException("Senha é obrigatória para novo usuário");
		}

		if (usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(this.bcrypt.encode(usuario.getSenha()));
		}else if(StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(usuarioExistente.get().getSenha());
		}
		usuario.setConfirmarSenha(usuario.getSenha());
		
		
		if(!usuario.isNovo() && usuario.getAtivo() == null) {
			usuario.setAtivo(usuarioExistente.get().getAtivo());
		}

		usuarioRepository.save(usuario);
	}

	
	@Transactional
	public void alterarStatus(Long[] codigos, StatusUsuario statusUsuario) {
		
		List<Usuario> usuariosDoBanco = usuarioRepository.findByCodigoIn(codigos);
		
		statusUsuario.executar(codigos, usuarioRepository);
		
	}
	
	
	public void removerUsuario(Long codigo) {
		usuarioRepository.deleteById(codigo);
	}

}
