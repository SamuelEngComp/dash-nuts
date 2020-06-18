package com.rev.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rev.modelo.Atividade;
import com.rev.modelo.FormaConexao;
import com.rev.modelo.TipoAtividade;
import com.rev.modelo.TipoDeBanca;
import com.rev.repository.AtividadeRepository;
import com.rev.repository.BancaRepository;
import com.rev.repository.filtro.AtividadeFiltro;

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
		
		//LocalDate data2020 = LocalDate.parse("2020-01-01");
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
		
		/**
		 * lembretes: 
		 * 1 - grafico 01 - os dados são obtidos pela requisicao ajax... 
		 * 2 - grafico 02 - os dados são obtidos pela requisicao ajax...
		 * 3 - grafico 03 - os dados são obtidos pela requisicao ajax...
		 * 4 - grafico 04 - os dados são obtidos pela requisicao ajax...
		 */
		
		
		/**
		 * #######################################################################
		 * ######################## Painel de dados de 2020 ######################
		 * #######################################################################
		 *
		 *	QUANDO FINALIZAR O ANO DE 2020 BASTA CHAMAR OS METODOS E ADICIONAR 
		 *	UM PAINEL DE CARDS NO HOME
		 *
		 */
		
		
		
		
		/**
		 * #######################################################################
		 * ######################## Painel de dados de 2015 ######################
		 * #######################################################################
		 *
		 */
		mv.addObject("painel2015TotalDeAtividades", atividade.painelTotalAtividadesPorAno(data2015.getYear()));
		mv.addObject("painel2015TotalDeParticipantes", atividade.painelTotalParticipantesPorAno(data2015.getYear()));
		mv.addObject("painel2015TotalDeHoras", atividade.painelTotalHorasAtividadePorAno(data2015.getYear()));
			
		mv.addObject("painel2015Totalsessoes", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, data2015.getYear()));
		mv.addObject("painel2015Totalebserh", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.EBSERH, data2015.getYear()));
		mv.addObject("painel2015Totalgravacao", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, data2015.getYear()));
		mv.addObject("painel2015Totalsig", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, data2015.getYear()));
		mv.addObject("painel2015Totalbancas", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, data2015.getYear()));
		mv.addObject("painel2015Totalreunioes", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.REUNIAO_ADMINISTRATIVA, data2015.getYear()));
		mv.addObject("painel2015Totalcirurgias", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.CIRURGIA, data2015.getYear()));
			
		mv.addObject("painel2015TotalWebconferencias", atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.WEBCONFERENCIA, data2015.getYear()));
		mv.addObject("painel2015TotalVideoconferencias", atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.VIDEOCONFERENCIA, data2015.getYear()));
			
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
				
		mv.addObject("painel2016Totalsessoes", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, data2016.getYear()));
		mv.addObject("painel2016Totalebserh", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.EBSERH, data2016.getYear()));
		mv.addObject("painel2016Totalgravacao", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, data2016.getYear()));
		mv.addObject("painel2016Totalsig", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, data2016.getYear()));
		mv.addObject("painel2016Totalbancas", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, data2016.getYear()));
		mv.addObject("painel2016Totalreunioes", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.REUNIAO_ADMINISTRATIVA, data2016.getYear()));
		mv.addObject("painel2016Totalcirurgias", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.CIRURGIA, data2016.getYear()));
				
		mv.addObject("painel2016TotalWebconferencias", atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.WEBCONFERENCIA, data2016.getYear()));
		mv.addObject("painel2016TotalVideoconferencias", atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.VIDEOCONFERENCIA, data2016.getYear()));
				
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
				
		mv.addObject("painel2017Totalsessoes", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, data2017.getYear()));
		mv.addObject("painel2017Totalebserh", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.EBSERH, data2017.getYear()));
		mv.addObject("painel2017Totalgravacao", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, data2017.getYear()));
		mv.addObject("painel2017Totalsig", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, data2017.getYear()));
		mv.addObject("painel2017Totalbancas", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, data2017.getYear()));
		mv.addObject("painel2017Totalreunioes", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.REUNIAO_ADMINISTRATIVA, data2017.getYear()));
		mv.addObject("painel2017Totalcirurgias", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.CIRURGIA, data2017.getYear()));
				
		mv.addObject("painel2017TotalWebconferencias", atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.WEBCONFERENCIA, data2017.getYear()));
		mv.addObject("painel2017TotalVideoconferencias", atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.VIDEOCONFERENCIA, data2017.getYear()));
				
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
				
		mv.addObject("painel2018Totalsessoes", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, data2018.getYear()));
		mv.addObject("painel2018Totalebserh", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.EBSERH, data2018.getYear()));
		mv.addObject("painel2018Totalgravacao", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, data2018.getYear()));
		mv.addObject("painel2018Totalsig", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, data2018.getYear()));
		mv.addObject("painel2018Totalbancas", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, data2018.getYear()));
		mv.addObject("painel2018Totalreunioes", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.REUNIAO_ADMINISTRATIVA, data2018.getYear()));
		mv.addObject("painel2018Totalcirurgias", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.CIRURGIA, data2018.getYear()));
				
		mv.addObject("painel2018TotalWebconferencias", atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.WEBCONFERENCIA, data2018.getYear()));
		mv.addObject("painel2018TotalVideoconferencias", atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.VIDEOCONFERENCIA, data2018.getYear()));
				
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
				
		mv.addObject("painel2019Totalsessoes", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, data2019.getYear()));
		mv.addObject("painel2019Totalebserh", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.EBSERH, data2019.getYear()));
		mv.addObject("painel2019Totalgravacao", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, data2019.getYear()));
		mv.addObject("painel2019Totalsig", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, data2019.getYear()));
		mv.addObject("painel2019Totalbancas", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, data2019.getYear()));
		mv.addObject("painel2019Totalreunioes", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.REUNIAO_ADMINISTRATIVA, data2019.getYear()));
		mv.addObject("painel2019Totalcirurgias", atividade.painelTotalTipoDeAtividadePorAno(TipoAtividade.CIRURGIA, data2019.getYear()));
				
		mv.addObject("painel2019TotalWebconferencias", atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.WEBCONFERENCIA, data2019.getYear()));
		mv.addObject("painel2019TotalVideoconferencias", atividade.painelTotalFormaDeConexaoPorAno(FormaConexao.VIDEOCONFERENCIA, data2019.getYear()));
				
		mv.addObject("painel2019TotalEconomiaComBancas", banca.painelEconomiaComBancaPorAno(data2019.getYear()));
		
		
		return mv;
	}

	
	
	/**Dois metodos criados para retornar o download da documentacao e manual do sistema
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/download/sistema")
	public HttpEntity<byte[]> downloadDocumentacaoSistema() throws IOException{
		byte[] arquivo = Files.readAllBytes(Paths.get("src/main/resources/download/apresentacao.pdf"));
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=\"apresentacao.pdf\"");
		
		HttpEntity<byte[]> entity = new HttpEntity<byte[]>(arquivo, headers);
		return entity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/download/manual")
	public HttpEntity<byte[]> downloadManualSistema() throws IOException{
		byte[] arquivo = Files.readAllBytes(Paths.get("src/main/resources/download/apresentacao.pdf"));
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=\"apresentacao.pdf\"");
		
		HttpEntity<byte[]> entity = new HttpEntity<byte[]>(arquivo, headers);
		return entity;
	}
	
	

}
