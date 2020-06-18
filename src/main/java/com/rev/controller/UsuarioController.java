package com.rev.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rev.controller.page.PageWrapper;
import com.rev.mail.Mailer;
import com.rev.modelo.Atividade;
import com.rev.modelo.Grupo;
import com.rev.modelo.Usuario;
import com.rev.repository.GrupoRepository;
import com.rev.repository.UsuarioRepository;
import com.rev.repository.filtro.AtividadeFiltro;
import com.rev.repository.filtro.UsuarioFiltro;
import com.rev.service.StatusUsuario;
import com.rev.service.UsuarioService;
import com.rev.service.exception.EmailUsuarioJaCadastradoException;
import com.rev.service.exception.SenhaObrigatoriaUsuarioException;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private GrupoRepository grupoRepository;

	
	@Autowired
	private Mailer enviandoEmail;
	
	
	@RequestMapping("/novo")
	public ModelAndView novoUsuario(Usuario usuario) {
		ModelAndView mv = new ModelAndView("cadastro-usuario");
		mv.addObject("grupos", grupoRepository.findAll());

		return mv;
	}

	@RequestMapping(value = {"/novo", "/editar/{\\d+}"}, method = RequestMethod.POST)
	public ModelAndView cadastrarUsuario(@Valid Usuario usuario, BindingResult bindingResult, Model model,
			RedirectAttributes redirect) {
		if (bindingResult.hasErrors()) {
			return novoUsuario(usuario);
		}

		try {
			usuarioService.salvarUsuario(usuario);
		} catch (EmailUsuarioJaCadastradoException e) {
			bindingResult.rejectValue("email", e.getMessage(), e.getMessage());
			return novoUsuario(usuario);
		} catch (SenhaObrigatoriaUsuarioException e) {
			bindingResult.rejectValue("senha", e.getMessage(), e.getMessage());
			return novoUsuario(usuario);
		}

		redirect.addFlashAttribute("mensagem", "Usuário Salvo Com Sucesso !!! ");
		return new ModelAndView("redirect:/usuario/novo");

	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView removerUsuario(@PathVariable ("codigo") Long codigo, RedirectAttributes redirect) {
		usuarioService.removerUsuario(codigo);
		redirect.addFlashAttribute("mensagem", "Usuário Removido Com Sucesso !!! ");
		return new ModelAndView("redirect:/usuario");
	}
	
	@GetMapping("/editar/{codigo}")
	public ModelAndView editarUsuario(@PathVariable ("codigo") Long codigo) {
		
		Usuario usuario = usuarioRepository.buscarPorGrupos(codigo);
		ModelAndView mv = novoUsuario(usuario);
		mv.addObject(usuario);
		return mv;
	}
	
	
	@GetMapping
	public ModelAndView pesquisarUsuario(Usuario usuario) {
		ModelAndView mv = new ModelAndView("pesquisar-usuario");
		mv.addObject("grupos", grupoRepository.findAll());
		mv.addObject("usuarios", usuarioRepository.findAll());
		return mv;
	}
	
	
	

	/*
	 * ESSE AQUI ESTA FUNCIONANDO, ENTÃO DEPOIS BASTA DESCOMENTAR
	 * 
	 * @RequestMapping(value = "/filtros", method = RequestMethod.GET) public
	 * ModelAndView filtrarUsuario(UsuarioFiltro usuarioFiltro) { ModelAndView mv =
	 * new ModelAndView("filtros/filtro-usuario");
	 * 
	 * mv.addObject("usuarios", usuarioRepository.filtrar(usuarioFiltro));
	 * 
	 * mv.addObject("grupos", grupoRepository.findAll());
	 * 
	 * return mv; }
	 */

	
	  @PutMapping("/filtros/status")
	  @ResponseStatus(HttpStatus.OK) 
	  public void atualizarStatus(@RequestParam("codigos[]") Long[] codigos, @RequestParam("status") StatusUsuario statusUsuario) {
		  usuarioService.alterarStatus(codigos, statusUsuario);
	  }
	 

	@RequestMapping(value = "/filtros", method = RequestMethod.GET) 
	public ModelAndView filtrarUsuario(UsuarioFiltro usuarioFiltro, BindingResult
	  bindingResult, @PageableDefault(size = 5) Pageable paginacao, HttpServletRequest httpServletRequest) { 
		ModelAndView mv = new ModelAndView("filtros/filtro-usuario");
	  
		mv.addObject("grupos", grupoRepository.findAll());
		
		PageWrapper<Usuario> paginaWrapper = new PageWrapper<Usuario>(usuarioRepository.filtrar(usuarioFiltro, paginacao), httpServletRequest);
	  
	   
		mv.addObject("paginas",paginaWrapper);
		return mv; 
	 }

	/***************************************************************************************
	 *************************************************************************************** 
	 * MÉTODO PARA REALIZAR O REGISTRO... ATIVO = TRUE E GRUPO = VISITANTE
	 * *************************************************************************************
	 *************************************************************************************** 
	 **/
	@RequestMapping(value = "/registrar", method = RequestMethod.POST)
	public ModelAndView cadastrarUsuarioVisitante(@Valid Usuario usuario, BindingResult bindingResult, Model model,
			RedirectAttributes redirect) {
		if (bindingResult.hasErrors()) {
			return registro(usuario);
		}

		try {

			List<Grupo> grupos = new ArrayList<Grupo>();
			grupos.add(0, new Grupo((long) 3, "Visitante")); // estrategia para setar o visitante

			usuario.setAtivo(true);
			usuario.setGrupos(grupos);

			usuarioService.salvarUsuario(usuario);
		} catch (EmailUsuarioJaCadastradoException e) {
			bindingResult.rejectValue("email", e.getMessage(), e.getMessage());
			return registro(usuario);
		} catch (SenhaObrigatoriaUsuarioException e) {
			bindingResult.rejectValue("senha", e.getMessage(), e.getMessage());
			return registro(usuario);
		}

		redirect.addFlashAttribute("mensagem", "Usuário Cadastrado Com Sucesso !!! ");
		
		enviandoEmail.enviar(usuario);
		
		return new ModelAndView("redirect:/usuario/registrar");

	}

	@RequestMapping("/registrar")
	public ModelAndView registro(Usuario usuario) {
		ModelAndView mv = new ModelAndView("registrar");
		return mv;
	}

}
