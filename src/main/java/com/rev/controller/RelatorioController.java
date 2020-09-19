package com.rev.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rev.modelo.Atividade;
import com.rev.repository.AtividadeRepository;
import com.rev.utils.AtividadeExportExcel;
import com.rev.utils.PeriodoRelatorio;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@RestController
@RequestMapping(value = "/relatorios")
public class RelatorioController {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private AtividadeRepository atividadeRepository; 

	@RequestMapping(value = "/atividades", method = RequestMethod.GET)
	public ModelAndView paginaRelatorioAtividade() {
		ModelAndView mv = new ModelAndView("relatorio/relatoriosAtividades");
		mv.addObject("periodoRelatorio", new PeriodoRelatorio());
		return mv;
	}

	@RequestMapping(value = "/bancas", method = RequestMethod.GET)
	public ModelAndView paginaRelatorioBanca() {
		ModelAndView mv = new ModelAndView("relatorio/relatoriosBancas");
		mv.addObject("periodoRelatorio", new PeriodoRelatorio());
		return mv;
	}

	@RequestMapping(value = "/atividade", method = RequestMethod.POST)
	public void imprimirAtividades(PeriodoRelatorio periodoRelatorio, HttpServletResponse response)
			throws JRException, SQLException, IOException {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		Date dataInicio = Date.from(LocalDateTime.of(periodoRelatorio.getDataInicio(), LocalTime.of(0, 0, 0))
				.atZone(ZoneId.systemDefault()).toInstant());
		Date dataFim = Date.from(LocalDateTime.of(periodoRelatorio.getDataFim(), LocalTime.of(23, 59, 59))
				.atZone(ZoneId.systemDefault()).toInstant());
		parametros.put("Data_inicio", dataInicio);
		parametros.put("Data_fim", dataFim);

		parametros = parametros == null ? parametros = new HashMap<>() : parametros;

		// Pega o arquivo .jasper localizado em resources
		InputStream jasperStream = this.getClass().getResourceAsStream("/relatorios/relatorio_atividade.jasper");

		// Cria o objeto JaperReport com o Stream do arquivo jasper
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

		// Passa para o JasperPrint o relatório, os parâmetros e a fonte dos dados, no
		// caso uma conexão ao banco de dados
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource.getConnection());

		// Configura a respota para o tipo PDF
		response.setContentType("application/pdf"); // Define que o arquivo pode ser visualizado no navegador e também
													// nome final do arquivo
		// para fazer download do relatório troque 'inline' por 'attachment'
		response.setHeader("Content-Disposition", "inline; filename=relatorio_atividade.pdf");

		// Faz a exportação do relatório para o HttpServletResponse final
		OutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	}

	@RequestMapping(value = "/banca", method = RequestMethod.POST)
	public void imprimirBancas(PeriodoRelatorio periodoRelatorio, HttpServletResponse response)
			throws JRException, SQLException, IOException {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("format", "pdf");
		Date dataInicio = Date.from(LocalDateTime.of(periodoRelatorio.getDataInicio(), LocalTime.of(0, 0, 0))
				.atZone(ZoneId.systemDefault()).toInstant());
		Date dataFim = Date.from(LocalDateTime.of(periodoRelatorio.getDataFim(), LocalTime.of(23, 59, 59))
				.atZone(ZoneId.systemDefault()).toInstant());
		parametros.put("data_inicial", dataInicio);
		parametros.put("data_final", dataFim);

		parametros = parametros == null ? parametros = new HashMap<>() : parametros;

		// Pega o arquivo .jasper localizado em resources
		InputStream jasperStream = this.getClass().getResourceAsStream("/relatorios/relatorio_banca.jasper");

		// Cria o objeto JaperReport com o Stream do arquivo jasper
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

		// Passa para o JasperPrint o relatório, os parâmetros e a fonte dos dados, no
		// caso uma conexão ao banco de dados
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource.getConnection());

		// Configura a respota para o tipo PDF
		response.setContentType("application/pdf"); // Define que o arquivo pode ser visualizado no navegador e também
													// nome final do arquivo
		// para fazer download do relatório troque 'inline' por 'attachment'
		response.setHeader("Content-Disposition", "inline; filename=relatorio_banca.pdf");

		// Faz a exportação do relatório para o HttpServletResponse final
		OutputStream outStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
	}
	
	
	@RequestMapping(value = "/atividade/export", method = RequestMethod.GET)
	public void exportarExcel(PeriodoRelatorio periodo, HttpServletResponse response) throws IOException {
		
		DateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
		String dataAtual = dataFormatada.format(new Date());
		String fileName = "ATIVIDADE_NUTS_"+dataAtual+".xlsx";
		
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachement; filename="+fileName;
		
		
		
		response.setHeader(headerKey, headerValue);
		
		List<Atividade> atividades = atividadeRepository.atividadesPorDatas(periodo.getDataInicio(), periodo.getDataFim());
		
		AtividadeExportExcel atividadeExcelExport = new AtividadeExportExcel(atividades);
		atividadeExcelExport.exportExcel(response);
		
	}
	

}










