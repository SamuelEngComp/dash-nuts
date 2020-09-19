package com.rev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rev.modelo.Atividade;
import com.rev.modelo.TipoAtividade;
import com.rev.repository.AtividadeRepository;

@Service
public class AtividadeService {

	@Autowired
	private AtividadeRepository atividadeRepository;

	public void salvarAtividade(Atividade atividade) {

		if (atividade.getTipo().getDescricao() == "Gravação de Vídeo"
				&& atividade.getFormaConexao().getDescricao() == "Sem Conexão") {
			atividade.setPontosConectados(0);
			atividadeRepository.save(atividade);
		} 
		else{
			atividadeRepository.save(atividade);
		}

	}

	public List<Atividade> buscarAtividades() {
		return atividadeRepository.findAll();
	}

	public void deletarAtividade(Long codigo) {
		atividadeRepository.deleteById(codigo);
	}

	public void removerAtividade(Atividade atividade) {
		atividadeRepository.delete(atividade);
	}

	/*
	 * public List<Atividade> buscarBancas(){ return
	 * atividadeRepository.findByTipoDeBanca(); }
	 * 
	 * public List<Atividade> buscarBancasCertas(){ return
	 * atividadeRepository.findByTipoDeBancas(); }
	 */

}
