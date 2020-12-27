package com.rev.controller;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.Year;
import java.util.HashMap;
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
import com.rev.modelo.FormaConexao;
import com.rev.modelo.TipoAtividade;
import com.rev.repository.AtividadeRepository;
import com.rev.repository.filtro.AtividadeFiltro;
import com.rev.service.AtividadeService;
import com.rev.service.exception.ImpossivelExcluirEditarEntidadeException;
import com.rev.utils.PeriodoRelatorio;

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
			if(atividade.getPontosConectados() == null || atividade.getPontosConectados() == 0) {
				atividade.setPontosConectados(0);
			}
			
			if(atividade.getQtdParticipantes() == null || atividade.getQtdParticipantes() == 0) {
				atividade.setQtdParticipantes(0);
			}
			
			atividadeService.salvarAtividade(atividade);
			
			redirect.addFlashAttribute("mensagem", atividade.getDescriminacao() + " - SALVA COM SUCESSO !!! ");
			return new ModelAndView("redirect:/atividade/nova");
		}
	}

	// PESQUISA FUNCIONANDO PERFEITAMENTE
	@GetMapping
	public ModelAndView pesquisarAtividade() {
		ModelAndView mv = new ModelAndView("pesquisar-atividade");
		mv.addObject("atividades", atividadeService.buscarAtividades());
		mv.addObject("periodoRelatorio", new PeriodoRelatorio());
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
	 * 
	 * @return
	 */
	@GetMapping(value = "/dados", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> atividadesPorMes() {
		return atividadeRepository.atividadesPorMes();
	}
	
	
	/**
	 * grafico de linha do dashboard - comparativo anual
	 * 
	 * @return o valor de atividades por ano de cada mês
	 */
	@GetMapping(value = "/dados/linhas", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody HashMap<String, List<Long>> atividadesPorMesLinhas() {
		return atividadeRepository.atividadesPorMesLinhas();
	}

	/**
	 * grafico de barras verticais do dashboard
	 * 
	 * @return
	 */
	@GetMapping(value = "/dados/participantes", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> participantesLocalPorMes() {
		return atividadeRepository.participantesLocalPorMes();
	}

	
	
	
	@GetMapping(value = "/dados/atividades2015", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> atividades2015PorMes() {
		LocalDate data2015 = LocalDate.parse("2015-01-01");
		return atividadeRepository.atividadesPorMes(data2015.getYear());
	}
	
	@GetMapping(value = "/dados/atividades2016", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> atividades2016PorMes() {
		LocalDate data2015 = LocalDate.parse("2016-01-01");
		return atividadeRepository.atividadesPorMes(data2015.getYear());
	}
	
	@GetMapping(value = "/dados/atividades2017", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> atividades2017PorMes() {
		LocalDate data2015 = LocalDate.parse("2017-01-01");
		return atividadeRepository.atividadesPorMes(data2015.getYear());
	}
	
	@GetMapping(value = "/dados/atividades2018", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> atividades2018PorMes() {
		LocalDate data2015 = LocalDate.parse("2018-01-01");
		return atividadeRepository.atividadesPorMes(data2015.getYear());
	}
	
	@GetMapping(value = "/dados/atividades2019", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> atividades2019PorMes() {
		LocalDate data2015 = LocalDate.parse("2019-01-01");
		return atividadeRepository.atividadesPorMes(data2015.getYear());
	}
	
	@GetMapping(value = "/dados/atividades2020", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> atividades2020PorMes() {
		LocalDate data2015 = LocalDate.parse("2020-01-01");
		return atividadeRepository.atividadesPorMes(data2015.getYear());
	}
	
	@GetMapping(value = "/dados/atividades2021", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> atividades2021PorMes() {
		LocalDate data2015 = LocalDate.parse("2021-01-01");
		return atividadeRepository.atividadesPorMes(data2015.getYear());
	}
	
	
	
	
	
	
	
	/**
	 * grafico de rosquinha do dashboard
	 * 
	 * @return
	 */
	@GetMapping(value = "/dados/formasconexao", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> formasConexao() {
		return atividadeRepository.formasConexaoUtilizadas();
	}

	/**
	 * valor obtido, mas ainda nao utilizado
	 * 
	 * @return
	 */
	
	@GetMapping(value = "/dados/Publico2015", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> publicoLocal2015(){
		LocalDate data2015 = LocalDate.parse("2015-01-01");
		
		return atividadeRepository.participantesLocalPorMes(data2015.getYear());
	}
	
	@GetMapping(value = "/dados/Publico2016", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> publicoLocal2016(){
		LocalDate data2015 = LocalDate.parse("2016-01-01");
		
		return atividadeRepository.participantesLocalPorMes(data2015.getYear());
	}
	
	@GetMapping(value = "/dados/Publico2017", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> publicoLocal2017(){
		LocalDate data2015 = LocalDate.parse("2017-01-01");
		
		return atividadeRepository.participantesLocalPorMes(data2015.getYear());
	}
	
	@GetMapping(value = "/dados/Publico2018", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> publicoLocal2018(){
		LocalDate data2015 = LocalDate.parse("2018-01-01");
		
		return atividadeRepository.participantesLocalPorMes(data2015.getYear());
	}
	
	@GetMapping(value = "/dados/Publico2019", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> publicoLocal2019(){
		LocalDate data2015 = LocalDate.parse("2019-01-01");
		
		return atividadeRepository.participantesLocalPorMes(data2015.getYear());
	}
	
	@GetMapping(value = "/dados/Publico2020", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> publicoLocal2020(){
		LocalDate data2015 = LocalDate.parse("2020-01-01");
		
		return atividadeRepository.participantesLocalPorMes(data2015.getYear());
	}
	
	@GetMapping(value = "/dados/Publico2021", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> publicoLocal2021(){
		LocalDate data2015 = LocalDate.parse("2021-01-01");
		
		return atividadeRepository.participantesLocalPorMes(data2015.getYear());
	}
	
	
	
	@GetMapping(value = "/dados/pontosConectados2015", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> pontosConectados2015() {
		LocalDate data2015 = LocalDate.parse("2015-01-01");
		return atividadeRepository.pontosConectados(data2015.getYear());
	}
	
	@GetMapping(value = "/dados/pontosConectados2016", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> pontosConectados2016() {
		LocalDate data2016 = LocalDate.parse("2016-01-01");
		return atividadeRepository.pontosConectados(data2016.getYear());
	}
	
	@GetMapping(value = "/dados/pontosConectados2017", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> pontosConectados2017() {
		LocalDate data2017 = LocalDate.parse("2017-01-01");
		return atividadeRepository.pontosConectados(data2017.getYear());
	}
	
	@GetMapping(value = "/dados/pontosConectados2018", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> pontosConectados2018() {
		LocalDate data2018 = LocalDate.parse("2018-01-01");
		return atividadeRepository.pontosConectados(data2018.getYear());
	}
	
	@GetMapping(value = "/dados/pontosConectados2019", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> pontosConectados2019() {
		LocalDate data2019 = LocalDate.parse("2019-01-01");
		return atividadeRepository.pontosConectados(data2019.getYear());
	}
	
	@GetMapping(value = "/dados/pontosConectados2020", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> pontosConectados2020() {
		LocalDate data2020 = LocalDate.parse("2020-01-01");
		return atividadeRepository.pontosConectados(data2020.getYear());
	}
	
	@GetMapping(value = "/dados/pontosConectados2021", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> pontosConectados2021() {
		LocalDate data2020 = LocalDate.parse("2021-01-01");
		return atividadeRepository.pontosConectados(data2020.getYear());
	}
	
	@GetMapping(value = "/dados/pontosConectados", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> pontosConectados() {
		return atividadeRepository.pontosConectados();
	}
	

	/**
	 * Comparativo anual entre atividades, participantes, pontos conectados, web e
	 * video
	 */
	@GetMapping(value = "/dados/comparativoAnual", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> comparativoAnual() {
		return atividadeRepository.comparativoAnual();
	}

	/**
	 * Comparativo anual entre sessão clinica, banca, gravação, ebserh e sig
	 */
	@GetMapping(value = "/dados/comparativoFinal", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody HashMap<String, List<Long>> comparativoFinal() {
		return atividadeRepository.comparativoFinal();
	}

	
	/**
	 * COMPARATIVO ANUAL DE PUBLICO LOCAL
	 */
	@GetMapping(value = "/dados/comparativopublicolocal", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody HashMap<String, Long> comparativoPublicoLocalAnual() {
		return atividadeRepository.comparativoPublicoLocalAnual();
	}
	
	
	
	/**
	 * COMPARATIVO ANUAL DE PONTOS CONECTADOS
	 */
	@GetMapping(value = "/dados/comparativopontosconectadosanual", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody HashMap<String, Long> comparativoPontosConectadosAnual() {
		return atividadeRepository.comparativoPontosConectadosAnual();
	}
}












