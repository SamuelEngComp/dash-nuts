
INSERT INTO permissao VALUES(7,'ROLE_FILTRO_ATIVIDADE');
INSERT INTO permissao VALUES(8,'ROLE_FILTRO_BANCA');

INSERT INTO grupo_permissao(codigo_grupo, codigo_permissao) VALUES (1,7);
INSERT INTO grupo_permissao(codigo_grupo, codigo_permissao) VALUES (1,8);

INSERT INTO grupo_permissao(codigo_grupo, codigo_permissao) VALUES (2,7);
INSERT INTO grupo_permissao(codigo_grupo, codigo_permissao) VALUES (2,8);
