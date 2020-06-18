package com.rev.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rev.controller.page.PageWrapper;
import com.rev.modelo.Atividade;
import com.rev.modelo.BancaExaminadora;
import com.rev.modelo.Origem;
import com.rev.modelo.TipoDeBanca;
import com.rev.repository.BancaRepository;
import com.rev.repository.filtro.AtividadeFiltro;
import com.rev.repository.filtro.BancaFiltro;
import com.rev.service.BancaService;

@RestController
@RequestMapping("/banca")
public class BancaController {

	@Autowired
	private BancaService bancaService;
	
	@Autowired
	private BancaRepository bancaRepository;
	
	@RequestMapping("/nova")
	public ModelAndView novaBanca(BancaExaminadora bancaExaminadora) {
		ModelAndView mv = new ModelAndView("cadastrar-banca");
		mv.addObject("tiposDeBancas", TipoDeBanca.values());
		mv.addObject("origens", Origem.values());
		return mv;
	}
	
	@RequestMapping(value = {"/nova","/editar/{\\d+}"}, method = RequestMethod.POST)
	public ModelAndView cadastrarAtividade(@Valid BancaExaminadora bancaExaminadora, BindingResult bindingResult, Model model, 
			RedirectAttributes redirect) {
		
		if (bindingResult.hasErrors()) {
		    return novaBanca(bancaExaminadora);
		}else {
			bancaExaminadora.setValorTotal(bancaService.somandoValores(bancaExaminadora));
//			bancaExaminadora.setData(bancaService.ajustandoData(bancaExaminadora));
			bancaService.salvarBanca(bancaExaminadora);
			redirect.addFlashAttribute("mensagem", "Banca Salva Com Sucesso !!! ");
			return new ModelAndView("redirect:/banca/nova");
		}
		
		
	}
	

	@GetMapping
	public ModelAndView buscarTodasBancas() {
		
		ModelAndView mv = new ModelAndView("pesquisar-banca");
		mv.addObject("bancas", bancaService.buscarTodas());
		return mv;
		
	}
	
	
	@RequestMapping(value = "/filtros", method = RequestMethod.GET)
	public ModelAndView filtrarBanca(BancaFiltro bancaFiltro, BindingResult bindingResult, 
			@PageableDefault(size = 10) Pageable paginacao, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("filtros/filtro-banca");
		
		PageWrapper<BancaExaminadora> paginaWrapper = new PageWrapper<BancaExaminadora>(bancaRepository.filtrar(bancaFiltro, paginacao), 
				httpServletRequest);
		
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	
	
	// REMOCAO DE ATIVIDADE
	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView deletarBanca(@PathVariable("codigo") Long codigo, RedirectAttributes redirect) {
		bancaService.deletarBanca(codigo);
		redirect.addFlashAttribute("mensagem", "Banca Removida Com Sucesso !!! ");
		return new ModelAndView("redirect:/banca");
	}

	//METODO EDITAR ESTA FUNCIONANDO PARA JOGAR O OBJETO NOS CAMPOS, MAS NAO CONSEGUE SALVAR AINDA
	@GetMapping("/editar/{codigo}")
	public ModelAndView editarBanca(@PathVariable("codigo") Long codigo) {
		BancaExaminadora bancaEdicao = bancaRepository.getOne(codigo);
		ModelAndView mv = novaBanca(bancaEdicao);
		mv.addObject(bancaEdicao);
		return mv;
	}
	
	
	
	/**
	 * grafico polar do dashboard
	 */
	@GetMapping(value =  "/dados/totalPorTipo", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> bancasPorTipoAnoAtual(){
		return bancaRepository.totalBancasPorTipoAnoAtual();
	}
	
	
	
}








