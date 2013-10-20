insert into empreendimento (descricao) values ('TI');
insert into empreendimento (descricao) values ('Gerência de Projeto');
insert into empreendimento (descricao) values ('Compras');
insert into empreendimento (descricao) values ('Engenharia de Processo');
insert into empreendimento (descricao) values ('Engenharia Mecânica');
insert into empreendimento (descricao) values ('Engenharia de Automação');
insert into empreendimento (descricao) values ('PCP');



insert into perfil (descricao, dt_criacao, dt_atualizacao) values ('Administrador', current_date(), current_date());
insert into perfil (descricao, dt_criacao, dt_atualizacao) values ('Diretor', current_date(), current_date());
insert into perfil (descricao, dt_criacao, dt_atualizacao) values ('Líder', current_date(), current_date());
insert into perfil (descricao, dt_criacao, dt_atualizacao) values ('Básico', current_date(), current_date());
insert into perfil (descricao, dt_criacao, dt_atualizacao) values ('Cliente', current_date(), current_date());

insert into usuario (nome,email,senha,dt_criacao,dt_atualizacao,id_empreendimento) values ('Dennis','sepaseila@hotmail.com','827ccb0eea8a706c4c34a16891f84e7b',current_date(), current_date(),1);
insert into usuario_perfil (id_usuario,id_perfil) values (1,1);

insert into usuario (nome,email,senha,dt_criacao,dt_atualizacao,id_empreendimento) values ('Marco','marco@ea.ind.br','827ccb0eea8a706c4c34a16891f84e7b',current_date(), current_date(),2);
insert into usuario_perfil (id_usuario,id_perfil) values (2,3);

insert into etapa (descricao) values ('Engenharia de Processo');
insert into etapa (descricao) values ('Engenharia de Automação');
insert into etapa (descricao) values ('Engenharia Mecânica');
insert into etapa (descricao) values ('Compras');
insert into etapa (descricao) values ('Montagem Geral');
insert into etapa (descricao) values ('Instalação in Site');
insert into etapa (descricao) values ('Fabricação');
insert into etapa (descricao) values ('Controle');
insert into etapa (descricao) values ('Documentação');

insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Layout',1,1);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Robcad',1,4);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Off Line',1,1);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('FügeFolge',1,0.5);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Plano de Pontos',1,1);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Plano de Fixação',1,0.5);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Ciclograma',1,1);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Pré-Acesso de Pinças',1,1);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('MTM',1,1);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Projeto Elétrico',2,1.5);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Projeto Infraestrutura',2,1.5);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Projeto Fluídos',2,1.5);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Off-Line (PLC)',2,1);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Projeto 3D',3,6);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Detalhamento',3,5);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Plano A',3,2);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Liberação Desenhos',3,2);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('PDF / IGS',3,1.5);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Materiais Comerciais',4,4);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Matéria-Prima',4,6);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Transporte / Embalagem',5,1);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Montagem Mecânica',5,8);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Instalação Fluídos',5,2.5);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Instalação Elétrica',5,2.5);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Medição 3D',5,2.5);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Try-Out',5,3);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Montagem Interna',6,5.5);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Instalação Site',6,10.5);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Start-Up',6,2.25);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Retífica',7,0.5);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Caldeiraria',7,7.5);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Usinagem',7,7.5);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Pintura/Tratamento',7,1.75);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Preparação Desenhos',8,0.5);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Cadastro & RC',8,0.5);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('As Built',9,0.5);
insert into sub_etapa (descricao,id_etapa,peso_etapa) values ('Documentação',9,0.5);

insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (1,1,'PD');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (1,2,'PD');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (1,3,'PD');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (2,10,'PD');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (2,11,'PD');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (2,13,'PD');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (4,19,'PD');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (5,21,'PD');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (6,27,'PD');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (6,28,'PD');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (6,29,'PD');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (9,36,'PD');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (9,37,'PD');


insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (1,4,'OP');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (1,5,'OP');	
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (1,6,'OP');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (1,7,'OP');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (1,8,'OP');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (1,9,'OP');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (3,14,'OP');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (3,15,'OP');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (3,16,'OP');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (3,17,'OP');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (3,18,'OP');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (2,12,'OP');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (2,10,'OP');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (5,21,'OP');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (6,8,'OP');

insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (3,14,'UN');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (3,15,'UN');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (3,17,'UN');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (3,18,'UN');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (5,22,'UN');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (5,23,'UN');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (5,24,'UN');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (5,25,'UN');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (5,26,'UN');

insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (3,14,'PO');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (3,15,'PO');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (4,20,'PO');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (7,30,'PO');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (7,31,'PO');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (7,32,'PO');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (7,33,'PO');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (8,34,'PO');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (8,35,'PO');

insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (4,20,'SP');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (8,34,'SP');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (8,35,'SP');

insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (3,14,'IC');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (4,19,'IC');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (8,34,'IC');
insert into nivel_etapa_sub_etapa (id_etapa, id_sub_etapa, nivel)
values (8,35,'IC');