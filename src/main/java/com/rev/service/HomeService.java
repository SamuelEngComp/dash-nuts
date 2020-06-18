package com.rev.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rev.modelo.FormaConexao;
import com.rev.modelo.TipoAtividade;
import com.rev.repository.AtividadeRepository;
import com.rev.repository.BancaRepository;

@Service
public class HomeService {

	@Autowired
	private AtividadeRepository atividadeRepository;
	
	@Autowired
	private BancaRepository bancaRepository;
	
	
	public Long[] painelDashBoard2015() {
		
		LocalDate data2015 = LocalDate.parse("2015-01-01");
		
		atividadeRepository.painelTotalAtividadesPorAno(data2015.getYear());
		atividadeRepository.painelTotalParticipantesPorAno(data2015.getYear());
		atividadeRepository.painelTotalHorasAtividadePorAno(data2015.getYear());
		
		atividadeRepository.painelTotalTipoDeAtividadePorAno(TipoAtividade.SESSAO_CLINICA, data2015.getYear());
		atividadeRepository.painelTotalTipoDeAtividadePorAno(TipoAtividade.EBSERH, data2015.getYear());
		atividadeRepository.painelTotalTipoDeAtividadePorAno(TipoAtividade.GRAVACAO_DE_VIDEO, data2015.getYear());
		atividadeRepository.painelTotalTipoDeAtividadePorAno(TipoAtividade.REUNIAO_ADMINISTRATIVA, data2015.getYear());
		atividadeRepository.painelTotalTipoDeAtividadePorAno(TipoAtividade.SIG, data2015.getYear());
		atividadeRepository.painelTotalTipoDeAtividadePorAno(TipoAtividade.BANCA, data2015.getYear());
		atividadeRepository.painelTotalTipoDeAtividadePorAno(TipoAtividade.CIRURGIA, data2015.getYear());
		
		atividadeRepository.painelTotalFormaDeConexaoPorAno(FormaConexao.VIDEOCONFERENCIA, data2015.getYear());
		atividadeRepository.painelTotalFormaDeConexaoPorAno(FormaConexao.WEBCONFERENCIA, data2015.getYear());
		atividadeRepository.painelTotalFormaDeConexaoPorAno(FormaConexao.STREAMING, data2015.getYear());
		atividadeRepository.painelTotalFormaDeConexaoPorAno(FormaConexao.S_C, data2015.getYear());
		atividadeRepository.painelTotalFormaDeConexaoPorAno(FormaConexao.VC_WEB, data2015.getYear());
		
		return null;
	}
}
