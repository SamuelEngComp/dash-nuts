package com.rev.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rev.controller.page.PageWrapper;
import com.rev.mail.Mailer;
import com.rev.modelo.Atividade;
import com.rev.modelo.FormaConexao;
import com.rev.modelo.TipoAtividade;
import com.rev.repository.AtividadeRepository;
import com.rev.repository.filtro.AtividadeFiltro;
import com.rev.service.AtividadeService;
import com.rev.service.exception.ImpossivelExcluirEditarEntidadeException;

@RestController
@RequestMapping("/atividade")
public class AtividadeController {

	@Autowired
	private AtividadeService atividadeService;

	@Autowired
	private AtividadeRepository atividadeRepository;

	//
	@RequestMapping("/nova")
	public ModelAndView novaAtividade(Atividade atividade) {
		ModelAndView mv = new ModelAndView("cadastrar-atividade");
		mv.addObject("tipoDeAtividade", TipoAtividade.values());
		mv.addObject("formaDeConexao", FormaConexao.values());
		return mv;
	}

	// esse metodo pode receber duas requisições de forma diferente, uma para salvar
	// e outra para editar
	@RequestMapping(value = { "/nova", "/editar/{\\d+}" }, method = RequestMethod.POST)
	public ModelAndView cadastrarAtividade(@Valid Atividade atividade, BindingResult bindingResult, Model model,
			RedirectAttributes redirect) {
		if (bindingResult.hasErrors()) {
			return novaAtividade(atividade);
		} else {
			atividadeService.salvarAtividade(atividade);
			redirect.addFlashAttribute("mensagem", "Atividade Salva Com Sucesso !!! ");
			return new ModelAndView("redirect:/atividade/nova");
		}
	}

	// PESQUISA FUNCIONANDO PERFEITAMENTE
	@GetMapping
	public ModelAndView pesquisarAtividade() {
		ModelAndView mv = new ModelAndView("/pesquisar-atividade");
		mv.addObject("atividades", atividadeService.buscarAtividades());
		return mv;
	}

	// FILTRO DE ATIVIDADES FUNCIONANDO PERFEITAMENTE
	@RequestMapping(value = "/filtros", method = RequestMethod.GET)
	public ModelAndView filtrarAtividade(AtividadeFiltro atividadeFiltro, BindingResult bindingResult,
			@PageableDefault(size = 10) Pageable paginacao, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("filtros/filtro-atividade");
		PageWrapper<Atividade> paginaWrapper = new PageWrapper<Atividade>(
				atividadeRepository.filtrar(atividadeFiltro, paginacao), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	// REMOCAO DE ATIVIDADE
	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView deletarAtividade(@PathVariable("codigo") Long codigo, RedirectAttributes redirect)
			throws ImpossivelExcluirEditarEntidadeException {
		atividadeService.deletarAtividade(codigo);
		redirect.addFlashAttribute("mensagem", "Atividade Removida Com Sucesso !!! ");
		return new ModelAndView("redirect:/atividade");
	}

	@GetMapping("/editar/{codigo}")
	public ModelAndView editarAtividade(@PathVariable("codigo") Long codigo)
			throws ImpossivelExcluirEditarEntidadeException {

		Atividade atividadeEdiacao = atividadeRepository.getOne(codigo);

		Boolean existe = atividadeRepository.existsById(codigo);
		if (!existe) {
			throw new ImpossivelExcluirEditarEntidadeException("Entidade não existe");
		}

		ModelAndView mv = novaAtividade(atividadeEdiacao);
		mv.addObject(atividadeEdiacao);
		return mv;
	}
	
	
	/**
	 * grafico de linha do dashboard
	 * @return
	 */
	@GetMapping(value =  "/dados", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> atividadesPorMes(){
		return atividadeRepository.atividadesPorMes();
	}
	
	
	/**
	 * grafico de barras verticais do dashboard
	 * @return
	 */
	@GetMapping(value =  "/dados/participantes", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> participantesLocalPorMes(){
		return atividadeRepository.participantesLocalPorMes();
	}
	
	
	/**
	 * grafico de rosquinha do dashboard
	 * @return
	 */
	@GetMapping(value =  "/dados/formasconexao", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> formasConexao(){
		return atividadeRepository.formasConexaoUtilizadas();
	}
	
	
	/**
	 * valor obtido, mas ainda nao utilizado
	 * @return
	 */
	@GetMapping(value =  "/dados/pontosConectados", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> pontosConectados(){
		return atividadeRepository.pontosConectados();
	}

}










