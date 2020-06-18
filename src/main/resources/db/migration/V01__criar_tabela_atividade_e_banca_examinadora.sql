CREATE TABLE atividade(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	data DATE NOT NULL,
	descriminacao VARCHAR(255) NOT NULL,
	tipo VARCHAR(255) NOT NULL,
	forma_conexao VARCHAR(255) NOT NULL,
	qtd_participantes INT NOT NULL,
	tema VARCHAR(255) NOT NULL,
	duracao INT NOT NULL
	
)ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE banca_examinadora(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	data DATE NOT NULL,
	tipo_de_banca VARCHAR(255) NOT NULL,
	origem VARCHAR(255) NOT NULL,
	instituicao_participante TEXT NOT NULL,
	estado TEXT NOT NULL,
	cidade TEXT NOT NULL,
	pais TEXT NOT NULL,
	localizacao_estudante VARCHAR(255) NOT NULL,
	passagens VARCHAR(255) NOT NULL,
	
	numero_de_passagens INT NOT NULL,
	numero_pontos_externos INT NOT NULL,
	valor_ida DECIMAL(10,2) NOT NULL,
	valor_ida_02 DECIMAL(10,2),
	valor_ida_03 DECIMAL(10,2),
	valor_ida_04 DECIMAL(10,2),
	valor_ida_05 DECIMAL(10,2),
	valor_ida_06 DECIMAL(10,2),
	
	valor_volta DECIMAL(10,2) NOT NULL,
	valor_volta_02 DECIMAL(10,2),
	valor_volta_03 DECIMAL(10,2),
	valor_volta_04 DECIMAL(10,2),
	valor_volta_05 DECIMAL(10,2),
	valor_volta_06 DECIMAL(10,2),
	
	diarias DECIMAL(10,2) NOT NULL,
	diarias_02 DECIMAL(10,2),
	diarias_03 DECIMAL(10,2),
	diarias_04 DECIMAL(10,2),
	diarias_05 DECIMAL(10,2),
	diarias_06 DECIMAL(10,2)
	
)ENGINE=InnoDB DEFAULT CHARSET=utf8;













