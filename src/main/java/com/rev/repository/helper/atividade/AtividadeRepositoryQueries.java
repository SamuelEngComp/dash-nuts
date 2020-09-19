package com.rev.repository.helper.atividade;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rev.modelo.Atividade;
import com.rev.modelo.FormaConexao;
import com.rev.modelo.TipoAtividade;
import com.rev.repository.filtro.AtividadeFiltro;

public interface AtividadeRepositoryQueries {

	public Page<Atividade> filtrar(AtividadeFiltro filtro, Pageable paginacao);

	public Long quantidadeAtividadePorTipo(TipoAtividade tipo, int ano);

	public Long somaAtividadePorFormaDeConexao(FormaConexao formaDeConexao);

	// inicio total geral
	public Long totalAtividade(); // total de atividades - desde 2015 ate os anos seguintes

	public Long totalParticipantes(); // total de participantes localmente - desde 2015 ate os anos seguintes

	public Long totalWebconferencias(); // total de webconferencias - desde 2015 ate os anos seguintes

	public Long totalVideoConferencia(); // total de videoconferencias - desde 2015 ate os anos seguintes

	public Long totalGeralAtividadesPorTipoEbserh(); // total de atividades EBSERH - desde 2015 ate os anos seguintes

	public Long totalGeralAtividadesPorTipoSessaoClinica();

	public Long totalGeralAtividadesPorTipoReuniaoAdministrativa();

	public Long totalGeralAtividadesPorTipoTransmissaoCirurgia();

	public Long totalGeralAtividadesPorTipoBancaExaminadora();

	public Long totalGeralAtividadesPorTipoSIG();

	public Long totalGeralPontosConectados();
	// fim total geral

	public List<Long> atividadesPorMes();
	public List<Long> atividadesPorMes(int ano);

	public List<Long> participantesLocalPorMes();
	public List<Long> participantesLocalPorMes(int ano);

	public List<Long> formasConexaoUtilizadas();

	public List<Long> pontosConectados();
	
	/**
	 * CRIEI ESSE MÉTODO COM O INTUITO DE CAPTAR O TOTAL DE PONTOS CONECTADOS POR MES DE CADA ANO
	 * @param ano
	 * @return
	 */
	public List<Long> pontosConectados(int ano);

	public List<Long> comparativoAnual();

	public HashMap<String, List<Long>> comparativoFinal();
//	public List<Long> comparativoFinal();

	/**
	 * @return retorna o numero de pontos conectados por ano (ATUAL)
	 */
	public Long totalPontosConectadosPorAno(); // ok

	/**
	 * @return numero total de participantes local por ano
	 */
	public Long totalParticipantesPorAno(); // ok

	/**
	 * @return numero total de atividade por ano
	 */
	public Long totalAtividadePorAno(); // ok

	/**
	 * @return numero total de horas por ano, pois esse valor eh dividido por 60
	 */
	public Long totalHorasPorAno(); // OK

	/**
	 * @param tipo
	 * @return numero total de atividade filtrada pelo tipo de atividade
	 */
	public Long totalAtividadePorTipo(TipoAtividade tipo); // ok

	/**
	 * @param formaDeConexao
	 * @return numero total de atividade filtrado pela forma de conexao
	 */
	public Long totalAtividadePorFormaConexao(FormaConexao formaDeConexao);// ok

	/**
	 * Esses metodos são necessarios para formar um painel com os dados antigos
	 * 
	 * @param ano
	 * @return
	 */
	public Long painelTotalAtividadesPorAno(int ano);

	public Long painelTotalParticipantesPorAno(int ano);

	public Long painelTotalHorasAtividadePorAno(int ano);

	public Long painelTotalTipoDeAtividadePorAno(TipoAtividade tipo, int ano);

	public Long painelTotalFormaDeConexaoPorAno(FormaConexao formaDeConexao, int ano);

	public Long painelTotalPontosConectadosPorAno(int ano);
	
	
	
	

}
