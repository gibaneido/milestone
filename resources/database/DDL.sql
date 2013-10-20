DROP TABLE IF EXISTS `nivel_etapa_sub_etapa`;
DROP TABLE IF EXISTS `projeto_etapa`;
DROP TABLE IF EXISTS `apontamento`;
DROP TABLE IF EXISTS `sub_etapa`;
DROP TABLE IF EXISTS `etapa`;
DROP TABLE IF EXISTS `item`;
DROP TABLE IF EXISTS `item_comercial`;
DROP TABLE IF EXISTS `fornecedor`;
DROP TABLE IF EXISTS `unidade`;
DROP TABLE IF EXISTS `operacao`;
DROP TABLE IF EXISTS `produto`;
DROP TABLE IF EXISTS `projeto`;
DROP TABLE IF EXISTS `cliente`;
DROP TABLE IF EXISTS `usuario_perfil`;
DROP TABLE IF EXISTS `perfil`;
DROP TABLE IF EXISTS `usuario`;
DROP TABLE IF EXISTS `empreendimento`;

CREATE  TABLE IF NOT EXISTS `empreendimento` (
  `id_empreendimento` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id_empreendimento`) 
)ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

CREATE  TABLE IF NOT EXISTS `usuario` (
  `id_usuario` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL ,
  `email` VARCHAR(100) NOT NULL ,
  `senha` VARCHAR(255) NOT NULL ,
  `dt_criacao` DATETIME NOT NULL ,
  `dt_atualizacao` DATETIME NOT NULL ,
  `id_empreendimento` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`id_usuario`) ,
  INDEX `fk_usuario_empreendimento1` (`id_empreendimento` ASC) ,
  CONSTRAINT `fk_usuario_empreendimento1`
    FOREIGN KEY (`id_empreendimento` )
    REFERENCES `empreendimento` (`id_empreendimento` )
)ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

CREATE  TABLE IF NOT EXISTS `perfil` (
  `id_perfil` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(45) NOT NULL ,
  `dt_criacao` DATETIME NULL ,
  `dt_atualizacao` DATETIME NULL ,
  PRIMARY KEY (`id_perfil`)
)ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

CREATE  TABLE IF NOT EXISTS `usuario_perfil` (
  `id_usuario` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `id_perfil` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`id_usuario`, `id_perfil`) ,
  INDEX `fk_usuario_has_perfil_perfil1` (`id_perfil` ASC) ,
  INDEX `fk_usuario_has_perfil_usuario1` (`id_usuario` ASC) ,
  CONSTRAINT `fk_usuario_has_perfil_usuario1`
    FOREIGN KEY (`id_usuario` )
    REFERENCES `usuario` (`id_usuario` ),
  CONSTRAINT `fk_usuario_has_perfil_perfil1`
    FOREIGN KEY (`id_perfil` )
    REFERENCES `perfil` (`id_perfil` )
)ENGINE=MyISAM  DEFAULT CHARSET=latin1;

CREATE  TABLE IF NOT EXISTS `cliente` (
  `id_cliente` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `razao_social` VARCHAR(100) NOT NULL ,
  `cnpj` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id_cliente`) 
)ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

CREATE  TABLE IF NOT EXISTS `projeto` (
  `id_projeto` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `codigo` VARCHAR(10) NOT NULL ,
  `descricao` VARCHAR(45) NOT NULL ,
  `dt_criacao` DATETIME NOT NULL ,
  `dt_inicio` DATETIME NULL ,
  `dt_fim` DATETIME NULL ,
  `dt_atualizacao` DATETIME NOT NULL ,
  `id_usuario` BIGINT(20) NOT NULL ,
  `id_cliente` BIGINT(20) NOT NULL ,
  `id_cliente_final` BIGINT(20) NULL ,
  PRIMARY KEY (`id_projeto`) ,
  UNIQUE INDEX `nome_UNIQUE` (`descricao` ASC) ,
  INDEX `fk_projeto_usuario1` (`id_usuario` ASC) ,
  INDEX `fk_projeto_cliente1` (`id_cliente` ASC) ,
  INDEX `fk_projeto_clientefinal1` (`id_cliente_final` ASC) ,
  CONSTRAINT `fk_projeto_usuario1`
    FOREIGN KEY (`id_usuario` )
    REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `fk_projeto_cliente1`
    FOREIGN KEY (`id_cliente` )
    REFERENCES `cliente` (`id_cliente` ),
  CONSTRAINT `fk_projeto_clientefinal1`
    FOREIGN KEY (`id_cliente_final` )
    REFERENCES `cliente` (`id_cliente` )
)ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS `produto` (
  `id_produto` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(45) NOT NULL ,
  `numero` VARCHAR(45) NULL ,
  `dt_criacao` DATETIME NOT NULL ,
  `dt_atualizacao` DATETIME NOT NULL ,
  `id_projeto` BIGINT(20) NOT NULL ,
  `id_usuario` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`id_produto`) ,
  INDEX `fk_Produto_Projeto` (`id_projeto` ASC) ,
  INDEX `fk_produto_usuario1` (`id_usuario` ASC) ,
  CONSTRAINT `fk_Produto_Projeto`
    FOREIGN KEY (`id_projeto` )
    REFERENCES `projeto` (`id_projeto` ),
  CONSTRAINT `fk_produto_usuario1`
    FOREIGN KEY (`id_usuario` )
    REFERENCES `usuario` (`id_usuario` )
)ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

CREATE  TABLE IF NOT EXISTS `operacao` (
  `id_operacao` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `op` VARCHAR(45) NOT NULL ,
  `descricao` VARCHAR(45) NOT NULL ,
  `dt_criacao` DATETIME NOT NULL ,
  `dt_atualizacao` DATETIME NOT NULL ,
  `numero_desenho` VARCHAR(45) NULL ,
  `qtd_esquerda` BIGINT(4) DEFAULT 0 NULL,
  `qtd_direita` BIGINT(4) DEFAULT 0 NULL,
  `id_produto` BIGINT(20) NOT NULL ,
  `id_usuario` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`id_operacao`) ,
  INDEX `fk_operacao_produto1` (`id_produto` ASC) ,
  INDEX `fk_operacao_usuario1` (`id_usuario` ASC) ,
  CONSTRAINT `fk_operacao_produto1`
    FOREIGN KEY (`id_produto` )
    REFERENCES `produto` (`id_produto` ),
  CONSTRAINT `fk_operacao_usuario1`
    FOREIGN KEY (`id_usuario` )
    REFERENCES `usuario` (`id_usuario` )
)ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

CREATE  TABLE IF NOT EXISTS `unidade` (
  `id_unidade` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `numero` VARCHAR(45) NOT NULL ,
  `descricao` VARCHAR(45) NULL ,
  `dt_criacao` DATETIME NOT NULL ,
  `dt_atualizacao` DATETIME NOT NULL ,
  `qtd_esquerda` BIGINT(4) DEFAULT 0 NULL,
  `qtd_direita` BIGINT(4) DEFAULT 0 NULL,
  `id_operacao` BIGINT(20) NOT NULL ,
  `id_usuario` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`id_unidade`) ,
  INDEX `fk_unidade_operacao1` (`id_operacao` ASC) ,
  INDEX `fk_unidade_usuario1` (`id_usuario` ASC) ,
  CONSTRAINT `fk_unidade_operacao1`
    FOREIGN KEY (`id_operacao` )
    REFERENCES `operacao` (`id_operacao` ),
  CONSTRAINT `fk_unidade_usuario1`
    FOREIGN KEY (`id_usuario` )
    REFERENCES `usuario` (`id_usuario` )
)ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

CREATE  TABLE IF NOT EXISTS `fornecedor` (
  `id_fornecedor` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `razao_social` VARCHAR(100) NOT NULL ,
  `cnpj` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id_fornecedor`) 
)ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

CREATE  TABLE IF NOT EXISTS `item_comercial` (
  `id_item_comercial` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(45) NOT NULL ,
  `dt_criacao` DATETIME NOT NULL ,
  `dt_atualizacao` DATETIME NOT NULL ,
  `codigo_norma` VARCHAR(45) NULL ,
  `qtd_direita` BIGINT(20) NULL ,
  `qtd_esquerda` BIGINT(20) NULL ,
  `id_produto` BIGINT(20) NULL ,
  `id_unidade` BIGINT(20) NULL ,
  `id_usuario` BIGINT(20) NOT NULL ,
  `id_fornecedor` BIGINT(20) NULL ,
  PRIMARY KEY (`id_item_comercial`) ,
  INDEX `fk_equipamento_produto1` (`id_produto` ASC) ,
  INDEX `fk_equipamento_unidade1` (`id_unidade` ASC) ,
  INDEX `fk_item_comercial_usuario1` (`id_usuario` ASC) ,
  INDEX `fk_item_comercial_fornecedor1` (`id_fornecedor` ASC) ,
  CONSTRAINT `fk_equipamento_produto1`
    FOREIGN KEY (`id_produto` )
    REFERENCES `produto` (`id_produto` ),
  CONSTRAINT `fk_equipamento_unidade1`
    FOREIGN KEY (`id_unidade` )
    REFERENCES `unidade` (`id_unidade` ),
  CONSTRAINT `fk_item_comercial_usuario1`
    FOREIGN KEY (`id_usuario` )
    REFERENCES `usuario` (`id_usuario` ),
  CONSTRAINT `fk_item_comercial_fornecedor1`
    FOREIGN KEY (`id_fornecedor` )
    REFERENCES `fornecedor` (`id_fornecedor` )
)ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

CREATE  TABLE IF NOT EXISTS `item` (
  `id_item` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `posicao` VARCHAR(45) NOT NULL ,
  `descricao` VARCHAR(45) NOT NULL ,
  `dt_criacao` DATETIME NOT NULL ,
  `dt_atualizacao` DATETIME NOT NULL ,
  `material` VARCHAR(45) NULL ,
  `qtd_direita` BIGINT(20) NULL ,
  `qtd_esquerda` BIGINT(20) NULL ,
  `codigo_norma` VARCHAR(45) NULL ,
  `altura` VARCHAR(45) NULL ,
  `largura_diametro` VARCHAR(45) NOT NULL ,
  `comprimento` VARCHAR(45) NOT NULL ,
  `espessura_alma` VARCHAR(45) NULL ,
  `conjunto_soldado` CHAR NOT NULL DEFAULT 'N' ,
  `observacao` VARCHAR(255) NULL ,
  `id_unidade` BIGINT(20) NULL ,
  `id_usuario` BIGINT(20) NOT NULL ,
  `id_fornecedor` BIGINT(20) NULL ,
  `id_item_pai` BIGINT(20) NULL ,
  PRIMARY KEY (`id_item`) ,
  INDEX `fk_item_unidade1` (`id_unidade` ASC) ,
  INDEX `fk_item_usuario1` (`id_usuario` ASC) ,
  INDEX `fk_item_fornecedor1` (`id_fornecedor` ASC) ,
  INDEX `fk_item_item1` (`id_item_pai` ASC) ,
  CONSTRAINT `fk_item_unidade1`
    FOREIGN KEY (`id_unidade` )
    REFERENCES `unidade` (`id_unidade` ),
  CONSTRAINT `fk_item_usuario1`
    FOREIGN KEY (`id_usuario` )
    REFERENCES `usuario` (`id_usuario` ),
  CONSTRAINT `fk_item_fornecedor1`
    FOREIGN KEY (`id_fornecedor` )
    REFERENCES `fornecedor` (`id_fornecedor` ),
  CONSTRAINT `fk_item_item1`
    FOREIGN KEY (`id_item_pai` )
    REFERENCES `item` (`id_item` )
)ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

CREATE  TABLE IF NOT EXISTS `etapa` (
  `id_etapa` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id_etapa`) 
)ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

CREATE  TABLE IF NOT EXISTS `sub_etapa` (
  `id_sub_etapa` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(45) NULL ,
  `peso_etapa` FLOAT NULL ,
  `peso_projeto` FLOAT NULL ,
  `id_etapa` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`id_sub_etapa`) ,
  INDEX `fk_sub_etapa_etapa1` (`id_etapa` ASC) ,
  CONSTRAINT `fk_sub_etapa_etapa1`
    FOREIGN KEY (`id_etapa` )
    REFERENCES `etapa` (`id_etapa` )
)ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

CREATE  TABLE IF NOT EXISTS `milestone`.`apontamento` (
  `id_apontamento` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `id_projeto` BIGINT(20) NOT NULL ,
  `id_sub_etapa` BIGINT(20) NOT NULL ,
  `id_item` BIGINT(20) NULL ,
  `id_item_comercial` BIGINT(20) NULL ,
  `id_produto` BIGINT(20) NULL ,
  `id_operacao` BIGINT(20) NULL ,
  `id_unidade` BIGINT(20) NULL ,
  `id_nivel_etapa_sub_etapa` BIGINT(20) NOT NULL ,
  `cronograma_inicio` DATETIME NULL ,
  `cronograma_fim` DATETIME NULL ,
  `previsto_inicio` DATETIME NULL ,
  `previsto_fim` DATETIME NULL ,
  `realizado` DATETIME NULL ,
  `porcentagem` BIGINT(20) NULL ,
  `na` CHAR(1) NOT NULL DEFAULT '0' ,
  `dt_criacao` DATETIME NOT NULL ,
  INDEX `fk_apontamento_projeto1` (`id_projeto` ASC) ,
  INDEX `fk_apontamento_sub_etapa1` (`id_sub_etapa` ASC) ,
  INDEX `fk_apontamento_item1` (`id_item` ASC) ,
  INDEX `fk_apontamento_item_comercial1` (`id_item_comercial` ASC) ,
  INDEX `fk_apontamento_produto1` (`id_produto` ASC) ,
  INDEX `fk_apontamento_item_operacao1` (`id_operacao` ASC) ,
  INDEX `fk_apontamento_item_unidade1` (`id_unidade` ASC) ,
  INDEX `fk_apontamento_nivel_etapa_sub_etapa1` (`id_nivel_etapa_sub_etapa` ASC) ,
  PRIMARY KEY (`id_apontamento`) ,
  CONSTRAINT `fk_apontamento_projeto1`
    FOREIGN KEY (`id_projeto` )
    REFERENCES `milestone`.`projeto` (`id_projeto` ),
  CONSTRAINT `fk_apontamento_sub_etapa1`
    FOREIGN KEY (`id_sub_etapa` )
    REFERENCES `milestone`.`sub_etapa` (`id_sub_etapa` ),
  CONSTRAINT `fk_apontamento_item1`
    FOREIGN KEY (`id_item` )
    REFERENCES `milestone`.`item` (`id_item` ),
  CONSTRAINT `fk_apontamento_item_comercial1`
    FOREIGN KEY (`id_item_comercial` )
    REFERENCES `milestone`.`item_comercial` (`id_item_comercial` ),
  CONSTRAINT `fk_apontamento_produto1`
    FOREIGN KEY (`id_produto` )
    REFERENCES `milestone`.`produto` (`id_produto` ),
  CONSTRAINT `fk_apontamento_operacao1`
    FOREIGN KEY (`id_operacao` )
    REFERENCES `milestone`.`operacao` (`id_operacao` ),
  CONSTRAINT `fk_apontamento_unidade1`
    FOREIGN KEY (`id_unidade` )
    REFERENCES `milestone`.`unidade` (`id_unidade` ),
  CONSTRAINT `fk_apontamento_nivel_etapa_sub_etapa1`
    FOREIGN KEY (`id_nivel_etapa_sub_etapa` )
    REFERENCES `milestone`.`nivel_etapa_sub_etapa` (`id_nivel_etapa_sub_etapa` )
)ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

CREATE  TABLE IF NOT EXISTS `milestone`.`projeto_etapa` (
  `id_projeto` BIGINT(20) NOT NULL ,
  `id_etapa` BIGINT(20) NOT NULL ,
  INDEX `fk_projeto_etapa_projeto1` (`id_projeto` ASC) ,
  INDEX `fk_projeto_etapa_etapa1` (`id_etapa` ASC) ,
  PRIMARY KEY (`id_projeto`,`id_etapa`) ,
  CONSTRAINT `fk_projeto_etapa_projeto1`
    FOREIGN KEY (`id_projeto` )
    REFERENCES `milestone`.`projeto` (`id_projeto` ),
  CONSTRAINT `fk_projeto_etapa_etapa1`
    FOREIGN KEY (`id_etapa` )
    REFERENCES `milestone`.`etapa` (`id_etapa` )
)ENGINE=MyISAM  DEFAULT CHARSET=latin1;

CREATE  TABLE IF NOT EXISTS `milestone`.`nivel_etapa_sub_etapa` (
  `id_nivel_etapa_sub_etapa` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `id_etapa` BIGINT(20) NOT NULL ,
  `id_sub_etapa` BIGINT(20) NOT NULL ,
  `nivel` CHAR(5) NOT NULL ,
  INDEX `fk_nivel_etapa_sub_etapa_etapa1` (`id_etapa` ASC) ,
  INDEX `fk_nivel_etapa_sub_etapa_sub_etapa1` (`id_sub_etapa` ASC) ,
  PRIMARY KEY (`id_nivel_etapa_sub_etapa`) ,
  CONSTRAINT `fk_nivel_etapa_sub_etapa_etapa1`
    FOREIGN KEY (`id_etapa` )
    REFERENCES `milestone`.`etapa` (`id_etapa` ),
  CONSTRAINT `fk_nivel_etapa_sub_etapa_sub_etapa1`
    FOREIGN KEY (`id_sub_etapa` )
    REFERENCES `milestone`.`sub_etapa` (`id_sub_etapa` )
)ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

CREATE  TABLE IF NOT EXISTS `milestone`.`perfil_empreendimento` (
  `id_perfil` BIGINT(20) NOT NULL ,
  `id_empreendimento` BIGINT(20) NOT NULL ,
  INDEX `fk_perfil_empreendimento_perfil1` (`id_perfil` ASC) ,
  INDEX `fk_perfil_empreendimento_empreendimento1` (`id_empreendimento` ASC) ,
  PRIMARY KEY (`id_perfil`,`id_empreendimento`) ,
  CONSTRAINT `fk_perfil_empreendimento_perfil1`
    FOREIGN KEY (`id_perfil` )
    REFERENCES `milestone`.`perfil` (`id_perfil` ),
  CONSTRAINT `fk_perfil_empreendimento_empreendimento1`
    FOREIGN KEY (`id_empreendimento` )
    REFERENCES `milestone`.`empreendimento` (`id_empreendimento` )
)ENGINE=MyISAM  DEFAULT CHARSET=latin1;