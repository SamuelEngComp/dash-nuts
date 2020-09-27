package com.rev.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import com.rev.modelo.Atividade;

public class AtividadeExportExcel {
	
	//planilha
	private XSSFWorkbook workbook;
	
	//aba da planilha
	private XSSFSheet sheet;
	
	//lista de atividades para ser inseridas na planilha
	private List<Atividade> atividades;
	
	
	public AtividadeExportExcel(List<Atividade> atividades) {
		this.atividades = atividades;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("ATIVIDADES_NUTS");
	}

	private void writeHeaderRow() {
		Row row = sheet.createRow(0);
		
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		
		font.setBold(true);
		font.setFontHeight(12);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);
		style.setBorderTop(BorderStyle.MEDIUM);
		
		Cell cell = row.createCell(0);
		cell.setCellValue("DATA");
		cell.setCellStyle(style);
		
		cell = row.createCell(1);
		cell.setCellValue("DESCRIMINAÇÃO");
		cell.setCellStyle(style);
		
		cell = row.createCell(2);
		cell.setCellValue("TIPO DE ATIVIDADE");
		cell.setCellStyle(style);
		
		cell = row.createCell(3);
		cell.setCellValue("FORMA DE CONEXÃO");
		cell.setCellStyle(style);
		
		cell = row.createCell(4);
		cell.setCellValue("PÚBLICO LOCAL");
		cell.setCellStyle(style);
		
		cell = row.createCell(5);
		cell.setCellValue("PONTOS CONECTADOS");
		cell.setCellStyle(style);
		
		cell = row.createCell(6);
		cell.setCellValue("TEMA");
		cell.setCellStyle(style);
		
		cell = row.createCell(7);
		cell.setCellValue("DURAÇÃO");
		cell.setCellStyle(style);
	}
	
	private void writeDataRows() {
		
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(11);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);
		style.setBorderTop(BorderStyle.MEDIUM);
		
		
		int rowCount = 1;
		
		for(Atividade atividade: atividades) {
			Row row = sheet.createRow(rowCount++);
			
			
			
			Cell cell = row.createCell(0);
			cell.setCellValue(atividade.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			sheet.autoSizeColumn(0);
			cell.setCellStyle(style);
			
			cell = row.createCell(1);
			cell.setCellValue(atividade.getDescriminacao());
			sheet.autoSizeColumn(1);
			cell.setCellStyle(style);
			
			cell = row.createCell(2);
			cell.setCellValue(atividade.getTipo().getDescricao().toUpperCase());
			sheet.autoSizeColumn(2);
			cell.setCellStyle(style);
			
			cell = row.createCell(3);
			cell.setCellValue(atividade.getFormaConexao().getDescricao().toUpperCase());
			sheet.autoSizeColumn(3);
			cell.setCellStyle(style);
			
			cell = row.createCell(4);
			cell.setCellValue(atividade.getQtdParticipantes());
			sheet.autoSizeColumn(4);
			cell.setCellStyle(style);
			
			cell = row.createCell(5);
			cell.setCellValue(atividade.getPontosConectados());
			sheet.autoSizeColumn(5);
			cell.setCellStyle(style);
			
			cell = row.createCell(6);
			cell.setCellValue(atividade.getTema());
			sheet.autoSizeColumn(6);
			cell.setCellStyle(style);
			
			cell = row.createCell(7);
			cell.setCellValue(atividade.getDuracao());
			sheet.autoSizeColumn(7);
			cell.setCellStyle(style);
		}
		
		
		
	}
	
	
	public void exportExcel(HttpServletResponse response) throws IOException {
		writeHeaderRow();
		writeDataRows();
		
		ServletOutputStream outputStream =  response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}

}
