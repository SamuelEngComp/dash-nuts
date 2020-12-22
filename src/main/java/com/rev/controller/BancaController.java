package com.rev.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
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
import com.rev.modelo.BancaExaminadora;
import com.rev.modelo.Origem;
import com.rev.modelo.TipoDeBanca;
import com.rev.repository.BancaRepository;
import com.rev.repository.filtro.BancaFiltro;
import com.rev.service.BancaService;
import com.rev.utils.PeriodoRelatorio;

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

	@RequestMapping(value = { "/nova",
			"/editar/{\\d+}" }, method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public ModelAndView cadastrarAtividade(@Valid BancaExaminadora bancaExaminadora, BindingResult bindingResult,
			Model model, RedirectAttributes redirect) {

		if (bindingResult.hasErrors()) {
			return novaBanca(bancaExaminadora);
		} else {
			bancaExaminadora.setValorTotal(bancaService.somandoValores(bancaExaminadora));
//			bancaExaminadora.setData(bancaService.ajustandoData(bancaExaminadora));
			bancaService.salvarBanca(bancaExaminadora);
			redirect.addFlashAttribute("mensagem", bancaExaminadora.getTipoDeBanca().getDescricao().toUpperCase() + " - BANCA SALVA COM SUCESSO !!! ");
			return new ModelAndView("redirect:/banca/nova");
		}

	}

	@GetMapping
	public ModelAndView buscarTodasBancas() {

		ModelAndView mv = new ModelAndView("pesquisar-banca");
		mv.addObject("bancas", bancaService.buscarTodas());
		mv.addObject("periodoRelatorio", new PeriodoRelatorio());
		return mv;

	}

	@RequestMapping(value = "/filtros", method = RequestMethod.GET)
	public ModelAndView filtrarBanca(BancaFiltro bancaFiltro, BindingResult bindingResult,
			@PageableDefault(size = 10) Pageable paginacao, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("filtros/filtro-banca");

		PageWrapper<BancaExaminadora> paginaWrapper = new PageWrapper<BancaExaminadora>(
				bancaRepository.filtrar(bancaFiltro, paginacao), httpServletRequest);

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

	// METODO EDITAR ESTA FUNCIONANDO PARA JOGAR O OBJETO NOS CAMPOS, MAS NAO
	// CONSEGUE SALVAR AINDA
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
	@GetMapping(value = "/dados/totalPorTipo", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> bancasPorTipoAnoAtual() {
		return bancaRepository.totalBancasPorTipoAnoAtual();
	}
	
	
	
	
	/**
	 * painel 2015 - gráficos de pizza - BANCAS EXAMINADORAS
	 * @return
	 */
	@GetMapping(value = "/dados/categoria2015bancas", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> categoriaBancas() {
		LocalDate data2015 = LocalDate.parse("2015-01-01");		
		return bancaRepository.origemDasBancas(data2015.getYear());
		
	}
	
	@GetMapping(value = "/dados/tiposdebancas2015porano", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> tiposDeBancasPorCategoria(){
		LocalDate data2015 = LocalDate.parse("2015-01-01");
		return bancaRepository.tiposDasBancas(data2015.getYear());
	}

	@GetMapping(value = "/dados/valoresbancas2015porano", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<BigDecimal> valoresPorTipoDeBanca(){
		LocalDate data2015 = LocalDate.parse("2015-01-01");
		return bancaRepository.valorEconomizadoPorTipoDeBanca(data2015.getYear());
	}
	
	
	/**
	 * painel 2016
	 */
	@GetMapping(value = "/dados/categoria2016bancas", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> categoria2016Bancas() {
		LocalDate data2015 = LocalDate.parse("2016-01-01");		
		return bancaRepository.origemDasBancas(data2015.getYear());
		
	}
	
	@GetMapping(value = "/dados/tiposdebancas2016porano", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> tiposDeBancas2016PorCategoria(){
		LocalDate data2015 = LocalDate.parse("2016-01-01");
		return bancaRepository.tiposDasBancas(data2015.getYear());
	}

	@GetMapping(value = "/dados/valoresbancas2016porano", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<BigDecimal> valoresPorTipoDe2016Banca(){
		LocalDate data2015 = LocalDate.parse("2016-01-01");
		return bancaRepository.valorEconomizadoPorTipoDeBanca(data2015.getYear());
	}


	/**
	 * painel 2017
	 * @return
	 */
	@GetMapping(value = "/dados/categoria2017bancas", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> categoria2017Bancas() {
		LocalDate data2015 = LocalDate.parse("2017-01-01");		
		return bancaRepository.origemDasBancas(data2015.getYear());
		
	}
	
	@GetMapping(value = "/dados/tiposdebancas2017porano", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> tiposDeBancas2017PorCategoria(){
		LocalDate data2015 = LocalDate.parse("2017-01-01");
		return bancaRepository.tiposDasBancas(data2015.getYear());
	}

	@GetMapping(value = "/dados/valoresbancas2017porano", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<BigDecimal> valoresPorTipoDe2017Banca(){
		LocalDate data2015 = LocalDate.parse("2017-01-01");
		return bancaRepository.valorEconomizadoPorTipoDeBanca(data2015.getYear());
	}

	/**
	 * painel 2018
	 * @return
	 */
	@GetMapping(value = "/dados/categoria2018bancas", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> categoria2018Bancas() {
		LocalDate data2015 = LocalDate.parse("2018-01-01");		
		return bancaRepository.origemDasBancas(data2015.getYear());
		
	}
	
	@GetMapping(value = "/dados/tiposdebancas2018porano", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> tiposDeBancas2018PorCategoria(){
		LocalDate data2015 = LocalDate.parse("2018-01-01");
		return bancaRepository.tiposDasBancas(data2015.getYear());
	}

	@GetMapping(value = "/dados/valoresbancas2018porano", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<BigDecimal> valoresPorTipoDe2018Banca(){
		LocalDate data2015 = LocalDate.parse("2018-01-01");
		return bancaRepository.valorEconomizadoPorTipoDeBanca(data2015.getYear());
	}
	
	
	/**
	 * painel 2019
	 * @return
	 */
	@GetMapping(value = "/dados/categoria2019bancas", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> categoria2019Bancas() {
		LocalDate data2015 = LocalDate.parse("2019-01-01");		
		return bancaRepository.origemDasBancas(data2015.getYear());
		
	}
	
	@GetMapping(value = "/dados/tiposdebancas2019porano", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> tiposDeBancas2019PorCategoria(){
		LocalDate data2015 = LocalDate.parse("2019-01-01");
		return bancaRepository.tiposDasBancas(data2015.getYear());
	}

	@GetMapping(value = "/dados/valoresbancas2019porano", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<BigDecimal> valoresPorTipoDe2019Banca(){
		LocalDate data2015 = LocalDate.parse("2019-01-01");
		return bancaRepository.valorEconomizadoPorTipoDeBanca(data2015.getYear());
	}
	
	
	
	/**
	 * painel 2020
	 * @return
	 */
	@GetMapping(value = "/dados/categoria2020bancas", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> categoria2020Bancas() {
		LocalDate data2015 = LocalDate.parse("2020-01-01");		
		return bancaRepository.origemDasBancas(data2015.getYear());
		
	}
	
	@GetMapping(value = "/dados/tiposdebancas2020porano", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> tiposDeBancas2020PorCategoria(){
		LocalDate data2015 = LocalDate.parse("2020-01-01");
		return bancaRepository.tiposDasBancas(data2015.getYear());
	}

	@GetMapping(value = "/dados/valoresbancas2020porano", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<BigDecimal> valoresPorTipoDe2020Banca(){
		LocalDate data2015 = LocalDate.parse("2020-01-01");
		return bancaRepository.valorEconomizadoPorTipoDeBanca(data2015.getYear());
	}
	
	/**
	 * painel 2021
	 * @return
	 */
	@GetMapping(value = "/dados/categoria2021bancas", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> categoria2021Bancas() {
		LocalDate data2015 = LocalDate.parse("2021-01-01");		
		return bancaRepository.origemDasBancas(data2015.getYear());
		
	}
	
	@GetMapping(value = "/dados/tiposdebancas2021porano", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Long> tiposDeBancas2021PorCategoria(){
		LocalDate data2015 = LocalDate.parse("2021-01-01");
		return bancaRepository.tiposDasBancas(data2015.getYear());
	}

	@GetMapping(value = "/dados/valoresbancas2021porano", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<BigDecimal> valoresPorTipoDe2021Banca(){
		LocalDate data2015 = LocalDate.parse("2021-01-01");
		return bancaRepository.valorEconomizadoPorTipoDeBanca(data2015.getYear());
	}

}







