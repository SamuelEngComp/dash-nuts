package com.rev.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rev.modelo.FormaConexao;
import com.rev.modelo.Origem;
import com.rev.modelo.TipoAtividade;
import com.rev.modelo.TipoDeBanca;
import com.rev.repository.AtividadeRepository;
import com.rev.repository.BancaRepository;

@RestController
public class Home {

	@Autowired
	private AtividadeRepository atividade;

	@Autowired
	private BancaRepository banca;

	@RequestMapping("/") // quando não estiver logado
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("index");

		mv.addObject("totalParticipantes", atividade.totalParticipantes());
		mv.addObject("economia", banca.economiaComBancas());
		mv.addObject("web", atividade.totalWebconferencias());
		mv.addObject("video", atividade.totalVideoConferencia());
		mv.addObject("ano", Year.now().getValue());
		mv.addObject("todasAtividades", atividade.totalAtividade());
		mv.addObject("totalGeralBancasExaminadoras", banca.totalBancasExamiadoras());
		mv.addObject("totalGeralAtividadesEbserh", atividade.totalGeralAtividadesPorTipoEbserh());
		mv.addObject("totalGeralPontosConectados", atividade.totalGeralPontosConectados());

		return mv;
	}

	@RequestMapping("/home") // quando estiver logado
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("home");

		LocalDate data2020 = LocalDate.parse("2020-01-01");
		LocalDate data2019 = LocalDate.parse("2019-01-01");
		LocalDate data2018 = LocalDate.parse("2018-01-01");
		LocalDate data2017 = LocalDate.parse("2017-01-01");
		LocalDate data2016 = LocalDate.parse("2016-01-01");
		LocalDate data2015 = LocalDate.parse("2015-01-01");

		/**
		 * CARDS DO DASHBOARD DO ANO ATUAL
		 */
		mv.addObject("totalAtividade", atividade.totalAtividadePorAno());
		mv.addObject("sessao", atividade.totalAtividadePorTipo(TipoAtividade.SESSAO_CLINICA));
		mv.addObject("gravacao", atividade.totalAtividadePorTipo(TipoAtividade.GRAVACAO_DE_VIDEO));
		mv.addObject("totalEbserh", atividade.totalAtividadePorTipo(TipoAtividade.EBSERH));

		mv.addObject("economiaBancasAtual", banca.economiaComBancasAnoAtual());
		mv.addObject("mestrado", banca.bancasPorTipoAnoAtual(TipoDeBanca.BANCA_MESTRADO));
		mv.addObject("doutorado", banca.bancasPorTipoAnoAtual(TipoDeBanca.BANCA_DOUTORADO));
		mv.addObject("totalParticipantes", atividade.totalParticipantesPorAno());
		
		mv.addObject("nacional", banca.origemBancas(Origem.NACIONAL, Year.now().getValue()));
		mv.addObject("internacional", banca.origemBancas(Origem.INTERNACIONAL, Year.now().getValue()));

		/**
		 * lembretes: 1 - grafico 01 - os dados são obtidos pela requisicao ajax... 2 -
		 * grafico 02 - os dados são obtidos pela requisicao ajax... 3 - grafico 03 - os
		 * dados são obtidos pela requisicao ajax... 4 - grafico 04 - os dados são
		 * obtidos pela requisicao ajax...
		 */

		/**
		 * #######################################################################
		 * ######################## Painel de dados de 2020 ######################
		 * #######################################################################
		 *
		 * QUANDO FINALIZAR O ANO DE 2020 BASTA CHAMAR OS METODOS E ADICIONAR UM PAINEL
		 * DE CARDS NO HOME
		 *
		 */

		mv.addObject("painel2020TotalDeAtividades", atividade.painelTotalAtividadesPorAno(data2020.getYear()));
		mv.addObject("painel2020TotalDeParticipantes", atividade.painelTotalParticipantesPorAno(data2020.getYear()));
		mv.addObject("painel2020TotalDeHoras", atividade.painelTotalHorasAtividadePorAno(data2020.getYear()));
		
		
		mv.addObject("painel2020TotalQualiMestrado",banca.qtdPorTiposDeBancas(TipoDeBanca.QUALIFICACAO_MESTRADO, data2020.getYear()));
		mv.addObject("painel2020TotalMestrado", banca.qtdPorTiposDeBancas(TipoDeBanca.BANCA_MESTRADO, data2020.getYear()));
		mv.addObject("painel2020TotalQualiDoutorado", banca.qtdPorTiposDeBancas(TipoDeBanca.QUALIFICACAO_DOUTORADO, data2020.getYear()));
		mv.addObject("painel2020TotalDoutorado", banca.qtdPorTiposDeBancas(TipoDeBanca.BANCA_DOUTORADO, data2020.getYear()));
		
		mv.addObject("painel2020Totalreunioes", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.REUNIAO_ADMINISTRATIVA, data2020.getYear()));
		mv.addObject("painel2020TotalPontosConectados", atividade.painelTotalPontosConectadosPorAno(data2020.getYear()));
		mv.addObject("painel2020TotalWebnario", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.WEBINARIO, data2020.getYear()));
		mv.addObject("painel2020TotalCurso", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.CURSO, data2020.getYear()));
		

		mv.addObject("painel2020Totalsessoes",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, data2020.getYear()));
		mv.addObject("painel2020Totalebserh",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.EBSERH, data2020.getYear()));
		mv.addObject("painel2020Totalgravacao",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, data2020.getYear()));
		mv.addObject("painel2020Totalsig",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, data2020.getYear()));
		mv.addObject("painel2020Totalbancas",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, data2020.getYear()));
		
		mv.addObject("painel2020Totalcirurgias",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.CIRURGIA, data2020.getYear()));

		mv.addObject("painel2020TotalWebconferencias",
				atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.WEBCONFERENCIA, data2020.getYear()));
		mv.addObject("painel2020TotalVideoconferencias",
				atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.VIDEOCONFERENCIA, data2020.getYear()));

		mv.addObject("painel2020TotalEconomiaComBancas", banca.painelEconomiaComBancaPorAno(data2020.getYear()));

		/**
		 * #######################################################################
		 * ######################## Painel de dados de 2015 ######################
		 * #######################################################################
		 *
		 */
		mv.addObject("painel2015TotalDeAtividades", atividade.painelTotalAtividadesPorAno(data2015.getYear()));
		mv.addObject("painel2015TotalDeParticipantes", atividade.painelTotalParticipantesPorAno(data2015.getYear()));
		mv.addObject("painel2015TotalDeHoras", atividade.painelTotalHorasAtividadePorAno(data2015.getYear()));
		
		
		mv.addObject("painel2015TotalQualiMestrado",banca.qtdPorTiposDeBancas(TipoDeBanca.QUALIFICACAO_MESTRADO, data2015.getYear()));
		mv.addObject("painel2015TotalMestrado", banca.qtdPorTiposDeBancas(TipoDeBanca.BANCA_MESTRADO, data2015.getYear()));
		mv.addObject("painel2015TotalQualiDoutorado", banca.qtdPorTiposDeBancas(TipoDeBanca.QUALIFICACAO_DOUTORADO, data2015.getYear()));
		mv.addObject("painel2015TotalDoutorado", banca.qtdPorTiposDeBancas(TipoDeBanca.BANCA_DOUTORADO, data2015.getYear()));
		
		mv.addObject("painel2015Totalreunioes", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.REUNIAO_ADMINISTRATIVA, data2015.getYear()));
		mv.addObject("painel2015TotalPontosConectados", atividade.painelTotalPontosConectadosPorAno(data2015.getYear()));
		
		mv.addObject("painel2015TotalWebnario", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.WEBINARIO, data2015.getYear()));
		mv.addObject("painel2015TotalCurso", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.CURSO, data2015.getYear()));

		mv.addObject("painel2015Totalsessoes",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, data2015.getYear()));
		mv.addObject("painel2015Totalebserh",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.EBSERH, data2015.getYear()));
		mv.addObject("painel2015Totalgravacao",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, data2015.getYear()));
		mv.addObject("painel2015Totalsig",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, data2015.getYear()));
		mv.addObject("painel2015Totalbancas",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, data2015.getYear()));
		
		mv.addObject("painel2015TotalTreinamentos",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.TREINAMENTO, data2015.getYear()));
		
		mv.addObject("painel2015Totalcirurgias",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.CIRURGIA, data2015.getYear()));

		
		mv.addObject("painel2015TotalWebconferencias",
				atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.WEBCONFERENCIA, data2015.getYear()));
		mv.addObject("painel2015TotalVideoconferencias",
				atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.VIDEOCONFERENCIA, data2015.getYear()));

		mv.addObject("painel2015TotalEconomiaComBancas", banca.painelEconomiaComBancaPorAno(data2015.getYear()));

		/**
		 * #######################################################################
		 * ######################## Painel de dados de 2016 ######################
		 * #######################################################################
		 *
		 */
		mv.addObject("painel2016TotalDeAtividades", atividade.painelTotalAtividadesPorAno(data2016.getYear()));
		mv.addObject("painel2016TotalDeParticipantes", atividade.painelTotalParticipantesPorAno(data2016.getYear()));
		mv.addObject("painel2016TotalDeHoras", atividade.painelTotalHorasAtividadePorAno(data2016.getYear()));
		
		mv.addObject("painel2016TotalQualiMestrado",banca.qtdPorTiposDeBancas(TipoDeBanca.QUALIFICACAO_MESTRADO, data2016.getYear()));
		mv.addObject("painel2016TotalMestrado", banca.qtdPorTiposDeBancas(TipoDeBanca.BANCA_MESTRADO, data2016.getYear()));
		mv.addObject("painel2016TotalQualiDoutorado", banca.qtdPorTiposDeBancas(TipoDeBanca.QUALIFICACAO_DOUTORADO, data2016.getYear()));
		mv.addObject("painel2016TotalDoutorado", banca.qtdPorTiposDeBancas(TipoDeBanca.BANCA_DOUTORADO, data2016.getYear()));
		
		mv.addObject("painel2016Totalreunioes", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.REUNIAO_ADMINISTRATIVA, data2016.getYear()));
		mv.addObject("painel2016TotalPontosConectados", atividade.painelTotalPontosConectadosPorAno(data2016.getYear()));
		mv.addObject("painel2016TotalWebnario", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.WEBINARIO, data2016.getYear()));
		mv.addObject("painel2016TotalCurso", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.CURSO, data2016.getYear()));

		mv.addObject("painel2016Totalsessoes",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, data2016.getYear()));
		mv.addObject("painel2016Totalebserh",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.EBSERH, data2016.getYear()));
		mv.addObject("painel2016Totalgravacao",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, data2016.getYear()));
		mv.addObject("painel2016Totalsig",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, data2016.getYear()));
		mv.addObject("painel2016Totalbancas",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, data2016.getYear()));
		mv.addObject("painel2016Totalcirurgias",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.CIRURGIA, data2016.getYear()));

		mv.addObject("painel2016TotalWebconferencias",
				atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.WEBCONFERENCIA, data2016.getYear()));
		mv.addObject("painel2016TotalVideoconferencias",
				atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.VIDEOCONFERENCIA, data2016.getYear()));

		mv.addObject("painel2016TotalEconomiaComBancas", banca.painelEconomiaComBancaPorAno(data2016.getYear()));

		/**
		 * #######################################################################
		 * ######################## Painel de dados de 2017 ######################
		 * #######################################################################
		 *
		 */
		mv.addObject("painel2017TotalDeAtividades", atividade.painelTotalAtividadesPorAno(data2017.getYear()));
		mv.addObject("painel2017TotalDeParticipantes", atividade.painelTotalParticipantesPorAno(data2017.getYear()));
		mv.addObject("painel2017TotalDeHoras", atividade.painelTotalHorasAtividadePorAno(data2017.getYear()));
		
		mv.addObject("painel2017TotalQualiMestrado",banca.qtdPorTiposDeBancas(TipoDeBanca.QUALIFICACAO_MESTRADO, data2017.getYear()));
		mv.addObject("painel2017TotalMestrado", banca.qtdPorTiposDeBancas(TipoDeBanca.BANCA_MESTRADO, data2017.getYear()));
		mv.addObject("painel2017TotalQualiDoutorado", banca.qtdPorTiposDeBancas(TipoDeBanca.QUALIFICACAO_DOUTORADO, data2017.getYear()));
		mv.addObject("painel2017TotalDoutorado", banca.qtdPorTiposDeBancas(TipoDeBanca.BANCA_DOUTORADO, data2017.getYear()));
		
		mv.addObject("painel2017Totalreunioes", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.REUNIAO_ADMINISTRATIVA, data2017.getYear()));
		mv.addObject("painel2017TotalPontosConectados", atividade.painelTotalPontosConectadosPorAno(data2017.getYear()));
		mv.addObject("painel2017TotalWebnario", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.WEBINARIO, data2017.getYear()));
		mv.addObject("painel2017TotalCurso", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.CURSO, data2017.getYear()));

		mv.addObject("painel2017Totalsessoes",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, data2017.getYear()));
		mv.addObject("painel2017Totalebserh",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.EBSERH, data2017.getYear()));
		mv.addObject("painel2017Totalgravacao",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, data2017.getYear()));
		mv.addObject("painel2017Totalsig",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, data2017.getYear()));
		mv.addObject("painel2017Totalbancas",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, data2017.getYear()));
		
		mv.addObject("painel2017Totalcirurgias",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.CIRURGIA, data2017.getYear()));

		mv.addObject("painel2017TotalWebconferencias",
				atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.WEBCONFERENCIA, data2017.getYear()));
		mv.addObject("painel2017TotalVideoconferencias",
				atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.VIDEOCONFERENCIA, data2017.getYear()));

		mv.addObject("painel2017TotalEconomiaComBancas", banca.painelEconomiaComBancaPorAno(data2017.getYear()));

		/**
		 * #######################################################################
		 * ######################## Painel de dados de 2018 ######################
		 * #######################################################################
		 *
		 */
		mv.addObject("painel2018TotalDeAtividades", atividade.painelTotalAtividadesPorAno(data2018.getYear()));
		mv.addObject("painel2018TotalDeParticipantes", atividade.painelTotalParticipantesPorAno(data2018.getYear()));
		mv.addObject("painel2018TotalDeHoras", atividade.painelTotalHorasAtividadePorAno(data2018.getYear()));
		
		
		mv.addObject("painel2018TotalQualiMestrado",banca.qtdPorTiposDeBancas(TipoDeBanca.QUALIFICACAO_MESTRADO, data2018.getYear()));
		mv.addObject("painel2018TotalMestrado", banca.qtdPorTiposDeBancas(TipoDeBanca.BANCA_MESTRADO, data2018.getYear()));
		mv.addObject("painel2018TotalQualiDoutorado", banca.qtdPorTiposDeBancas(TipoDeBanca.QUALIFICACAO_DOUTORADO, data2018.getYear()));
		mv.addObject("painel2018TotalDoutorado", banca.qtdPorTiposDeBancas(TipoDeBanca.BANCA_DOUTORADO, data2018.getYear()));
		
		mv.addObject("painel2018Totalreunioes", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.REUNIAO_ADMINISTRATIVA, data2018.getYear()));
		mv.addObject("painel2018TotalPontosConectados", atividade.painelTotalPontosConectadosPorAno(data2018.getYear()));
		mv.addObject("painel2018TotalWebnario", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.WEBINARIO, data2018.getYear()));
		mv.addObject("painel2018TotalCurso", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.CURSO, data2018.getYear()));
		

		mv.addObject("painel2018Totalsessoes",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, data2018.getYear()));
		mv.addObject("painel2018Totalebserh",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.EBSERH, data2018.getYear()));
		mv.addObject("painel2018Totalgravacao",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, data2018.getYear()));
		mv.addObject("painel2018Totalsig",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, data2018.getYear()));
		mv.addObject("painel2018Totalbancas",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, data2018.getYear()));
		
		mv.addObject("painel2018Totalcirurgias",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.CIRURGIA, data2018.getYear()));

		mv.addObject("painel2018TotalWebconferencias",
				atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.WEBCONFERENCIA, data2018.getYear()));
		mv.addObject("painel2018TotalVideoconferencias",
				atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.VIDEOCONFERENCIA, data2018.getYear()));

		mv.addObject("painel2018TotalEconomiaComBancas", banca.painelEconomiaComBancaPorAno(data2018.getYear()));

		/**
		 * #######################################################################
		 * ######################## Painel de dados de 2019 ######################
		 * #######################################################################
		 *
		 */
		mv.addObject("painel2019TotalDeAtividades", atividade.painelTotalAtividadesPorAno(data2019.getYear()));
		mv.addObject("painel2019TotalDeParticipantes", atividade.painelTotalParticipantesPorAno(data2019.getYear()));
		mv.addObject("painel2019TotalDeHoras", atividade.painelTotalHorasAtividadePorAno(data2019.getYear()));
		
		
		mv.addObject("painel2019TotalQualiMestrado",banca.qtdPorTiposDeBancas(TipoDeBanca.QUALIFICACAO_MESTRADO, data2019.getYear()));
		mv.addObject("painel2019TotalMestrado", banca.qtdPorTiposDeBancas(TipoDeBanca.BANCA_MESTRADO, data2019.getYear()));
		mv.addObject("painel2019TotalQualiDoutorado", banca.qtdPorTiposDeBancas(TipoDeBanca.QUALIFICACAO_DOUTORADO, data2019.getYear()));
		mv.addObject("painel2019TotalDoutorado", banca.qtdPorTiposDeBancas(TipoDeBanca.BANCA_DOUTORADO, data2019.getYear()));
		
		mv.addObject("painel2019Totalreunioes", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.REUNIAO_ADMINISTRATIVA, data2019.getYear()));
		mv.addObject("painel2019TotalPontosConectados", atividade.painelTotalPontosConectadosPorAno(data2019.getYear()));
		mv.addObject("painel2019TotalWebnario", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.WEBINARIO, data2019.getYear()));
		mv.addObject("painel2019TotalCurso", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.CURSO, data2019.getYear()));
		

		mv.addObject("painel2019Totalsessoes",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, data2019.getYear()));
		mv.addObject("painel2019Totalebserh",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.EBSERH, data2019.getYear()));
		mv.addObject("painel2019Totalgravacao",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, data2019.getYear()));
		mv.addObject("painel2019Totalsig",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, data2019.getYear()));
		mv.addObject("painel2019Totalbancas",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, data2019.getYear()));
		
		mv.addObject("painel2019Totalcirurgias",
				atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.CIRURGIA, data2019.getYear()));

		mv.addObject("painel2019TotalWebconferencias",
				atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.WEBCONFERENCIA, data2019.getYear()));
		mv.addObject("painel2019TotalVideoconferencias",
				atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.VIDEOCONFERENCIA, data2019.getYear()));

		mv.addObject("painel2019TotalEconomiaComBancas", banca.painelEconomiaComBancaPorAno(data2019.getYear()));

		return mv;
	}

	/**
	 * Dois metodos criados para retornar o download da documentacao e manual do
	 * sistema
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/download/sistema")
	public HttpEntity<byte[]> downloadDocumentacaoSistema() throws IOException {

		byte[] arquivo = Files.readAllBytes(Paths.get("src/main/resources/download/ProjetoDashBoard.pdf"));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=\"ProjetoDashBoard.pdf\"");

		HttpEntity<byte[]> entity = new HttpEntity<byte[]>(arquivo, headers);
		return entity;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/download/manual")
	public HttpEntity<byte[]> downloadManualSistema() throws IOException {
		byte[] arquivo = Files.readAllBytes(Paths.get("src/main/resources/download/main.pdf"));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=\"main.pdf\"");

		HttpEntity<byte[]> entity = new HttpEntity<byte[]>(arquivo, headers);
		return entity;
	}

}
