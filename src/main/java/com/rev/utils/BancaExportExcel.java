package com.rev.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.rev.modelo.Atividade;
import com.rev.modelo.BancaExaminadora;

public class BancaExportExcel {
	
	//planilha
		private XSSFWorkbook workbook;
		
		//aba da planilha
		private XSSFSheet sheet;
		
		//lista de atividades para ser inseridas na planilha
		private List<BancaExaminadora> bancasExaminadoras;
		
		
		public BancaExportExcel(List<BancaExaminadora> bancasExaminadoras) {
			this.bancasExaminadoras = bancasExaminadoras;
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet("BANCAS_NUTS");
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
			cell.setCellValue("TIPO DE BANCA");
			cell.setCellStyle(style);
			
			cell = row.createCell(2);
			cell.setCellValue("ORIGEM");
			cell.setCellStyle(style);
			
			cell = row.createCell(3);
			cell.setCellValue("INSTITUIÇÃO PARTICIPANTE");
			cell.setCellStyle(style);
			
			cell = row.createCell(4);
			cell.setCellValue("PAÍS");
			cell.setCellStyle(style);
			
			cell = row.createCell(5);
			cell.setCellValue("ESTADO");
			cell.setCellStyle(style);
			
			cell = row.createCell(6);
			cell.setCellValue("CIDADE");
			cell.setCellStyle(style);
			
			cell = row.createCell(7);
			cell.setCellValue("LOCALIZAÇÃO DO ESTUDANTE");
			cell.setCellStyle(style);
			
			cell = row.createCell(8);
			cell.setCellValue("PONTOS EXTERNOS");
			cell.setCellStyle(style);
			
			cell = row.createCell(9);
			cell.setCellValue("VALOR TOTAL");
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
			
			for(BancaExaminadora banca: bancasExaminadoras) {
				Row row = sheet.createRow(rowCount++);
				
				
				
				Cell cell = row.createCell(0);
				cell.setCellValue(banca.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				sheet.autoSizeColumn(0);
				cell.setCellStyle(style);
				
				cell = row.createCell(1);
				cell.setCellValue(banca.getTipoDeBanca().getDescricao().toUpperCase());
				sheet.autoSizeColumn(1);
				cell.setCellStyle(style);
				
				cell = row.createCell(2);
				cell.setCellValue(banca.getOrigem().getDescricao().toUpperCase());
				sheet.autoSizeColumn(2);
				cell.setCellStyle(style);
				
				cell = row.createCell(3);
				cell.setCellValue(banca.getInstituicaoParticipante());
				sheet.autoSizeColumn(3);
				cell.setCellStyle(style);
				
				cell = row.createCell(4);
				cell.setCellValue(banca.getPais());
				sheet.autoSizeColumn(4);
				cell.setCellStyle(style);
				
				cell = row.createCell(5);
				cell.setCellValue(banca.getEstado());
				sheet.autoSizeColumn(5);
				cell.setCellStyle(style);
				
				cell = row.createCell(6);
				cell.setCellValue(banca.getCidade());
				sheet.autoSizeColumn(6);
				cell.setCellStyle(style);
				
				cell = row.createCell(7);
				cell.setCellValue(banca.getLocalizacaoEstudante());
				sheet.autoSizeColumn(7);
				cell.setCellStyle(style);
				
				cell = row.createCell(8);
				cell.setCellValue(banca.getNumeroPontosExternos());
				sheet.autoSizeColumn(8);
				cell.setCellStyle(style);
				
				cell = row.createCell(9);
				cell.setCellValue(banca.getValorTotal().doubleValue());
				sheet.autoSizeColumn(9);
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
