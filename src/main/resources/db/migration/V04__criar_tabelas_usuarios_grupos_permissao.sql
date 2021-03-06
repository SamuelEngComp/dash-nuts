CREATE TABLE usuario(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	primeiro_nome VARCHAR(50) NOT NULL,
	ultimo_nome VARCHAR(50) NOT NULL,
	email VARCHAR(200) NOT NULL,
	senha VARCHAR(120) NOT NULL,
	ativo Boolean default true
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE grupo(
	codigo BIGINT(20) PRIMARY KEY,
	nome VARCHAR(50) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE permissao(
	codigo BIGINT(20) PRIMARY KEY,
	nome VARCHAR(50) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE usuario_grupo(
	codigo_usuario BIGINT(20) NOT NULL,
	codigo_grupo BIGINT(20) NOT NULL,
	PRIMARY KEY(codigo_usuario, codigo_grupo),
	FOREIGN KEY(codigo_usuario) REFERENCES usuario(codigo),
	FOREIGN KEY(codigo_grupo) REFERENCES grupo(codigo)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE grupo_permissao(
	codigo_grupo BIGINT(20) NOT NULL,
	codigo_permissao BIGINT(20) NOT NULL,
	PRIMARY KEY(codigo_grupo, codigo_permissao),
	FOREIGN KEY(codigo_grupo) REFERENCES grupo(codigo),
	FOREIGN KEY(codigo_permissao) REFERENCES permissao(codigo)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
