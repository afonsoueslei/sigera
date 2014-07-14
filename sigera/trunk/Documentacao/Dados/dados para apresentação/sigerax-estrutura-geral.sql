-- phpMyAdmin SQL Dump
-- version 4.0.8
-- http://www.phpmyadmin.net
--
-- Máquina: localhost
-- Data de Criação: 13-Nov-2013 às 20:45
-- Versão do servidor: 5.1.53-log
-- versão do PHP: 5.3.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de Dados: `sigerax`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `anexos`
--

CREATE TABLE IF NOT EXISTS `anexos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Req_Segunda_Chamada_Requerimento_id` int(10) unsigned NOT NULL,
  `caminho` text COLLATE utf8_unicode_ci,
  `nome` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Anexos_FKIndex1` (`Req_Segunda_Chamada_Requerimento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `configuracao`
--

CREATE TABLE IF NOT EXISTS `configuracao` (
  `id` int(10) unsigned NOT NULL,
  `data_final_ajustes_matricula` date DEFAULT NULL,
  `data_inicial_ajustes_matricula` date DEFAULT NULL,
  `data_inicial_semestre` date DEFAULT NULL,
  `data_final_semestre` date DEFAULT NULL,
  `ano_corrente` int(11) DEFAULT NULL,
  `semestre_corrente` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `curso`
--

CREATE TABLE IF NOT EXISTS `curso` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `prefixo` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `cod_matriz_curricular` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nome` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `unidade_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Curso_FKIndex1` (`unidade_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=11 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `disciplina`
--

CREATE TABLE IF NOT EXISTS `disciplina` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `carga_horaria_pratica` int(10) unsigned DEFAULT NULL,
  `carga_horaria_teorica` int(10) unsigned DEFAULT NULL,
  `ementa` text COLLATE utf8_unicode_ci,
  `nome` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  `curso_id` int(10) unsigned NOT NULL,
  `bibliografia_basica` text COLLATE utf8_unicode_ci,
  `bibliografia_complementar` text COLLATE utf8_unicode_ci,
  `objetivo_geral` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `curso_id` (`curso_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1000001 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `justificativa`
--

CREATE TABLE IF NOT EXISTS `justificativa` (
  `Requerimento_id` int(10) unsigned NOT NULL,
  `justificativa` text COLLATE utf8_unicode_ci,
  KEY `Justificativa_FKIndex1` (`Requerimento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `parecer`
--

CREATE TABLE IF NOT EXISTS `parecer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `requerimento_id` int(10) unsigned NOT NULL,
  `usuario_id` int(10) unsigned DEFAULT NULL,
  `data_parecer` datetime DEFAULT NULL,
  `conteudo` text COLLATE utf8_unicode_ci,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `requerimento_id` (`requerimento_id`),
  KEY `usuario_id` (`usuario_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `perfil`
--

CREATE TABLE IF NOT EXISTS `perfil` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=8 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `professor`
--

CREATE TABLE IF NOT EXISTS `professor` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `usuario_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `usuario_id` (`usuario_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1000009 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `reqementa_disciplina`
--

CREATE TABLE IF NOT EXISTS `reqementa_disciplina` (
  `Disciplina_id` int(10) unsigned NOT NULL,
  `Req_Ementa_Requerimento_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Disciplina_id`,`Req_Ementa_Requerimento_id`),
  KEY `Disciplina_has_Req_Ementa_FKIndex1` (`Disciplina_id`),
  KEY `Disciplina_has_Req_Ementa_FKIndex2` (`Req_Ementa_Requerimento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `requerimento`
--

CREATE TABLE IF NOT EXISTS `requerimento` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `usuario_id` int(10) unsigned DEFAULT NULL,
  `data_abertura` datetime DEFAULT NULL,
  `status_req` int(11) NOT NULL,
  `data_fechamento` datetime DEFAULT NULL,
  `justificativa` text COLLATE utf8_unicode_ci,
  `tipo` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `usuario_id` (`usuario_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1000236 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `req_acrescimodisciplina_turma`
--

CREATE TABLE IF NOT EXISTS `req_acrescimodisciplina_turma` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Req_Acrescimo_Disciplina_Requerimento_id` int(10) unsigned NOT NULL,
  `Turma_id` int(10) unsigned NOT NULL,
  `status_disciplina` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Req_Acrescimo_Disciplina_Requerimento_id` (`Req_Acrescimo_Disciplina_Requerimento_id`,`Turma_id`),
  KEY `Req_Acrescimo_Disciplina_has_Turma_FKIndex1` (`Req_Acrescimo_Disciplina_Requerimento_id`),
  KEY `Req_AcrescimoDisciplina_Turma_FKIndex2` (`Turma_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1000051 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `req_acrescimo_disciplina`
--

CREATE TABLE IF NOT EXISTS `req_acrescimo_disciplina` (
  `Requerimento_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Requerimento_id`),
  KEY `Req_Acrescimo_Disciplina_FKIndex1` (`Requerimento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `req_assinatura`
--

CREATE TABLE IF NOT EXISTS `req_assinatura` (
  `Requerimento_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Requerimento_id`),
  KEY `Req_Assinatura_FKIndex1` (`Requerimento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `req_cancelamentodisciplina_turma`
--

CREATE TABLE IF NOT EXISTS `req_cancelamentodisciplina_turma` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Req_Cancelamento_Disciplina_Requerimento_id` int(10) unsigned NOT NULL,
  `Turma_id` int(10) unsigned NOT NULL,
  `status_disciplina` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Req_Cancelamento_Disciplina_Requerimento_id` (`Req_Cancelamento_Disciplina_Requerimento_id`,`Turma_id`),
  KEY `Req_Cancelamento_Disciplina_has_Turma_FKIndex1` (`Req_Cancelamento_Disciplina_Requerimento_id`),
  KEY `Req_Cancelamento_Disciplina_has_Turma_FKIndex2` (`Turma_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=18 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `req_cancelamento_disciplina`
--

CREATE TABLE IF NOT EXISTS `req_cancelamento_disciplina` (
  `Requerimento_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Requerimento_id`),
  KEY `Req_Cancelamento_Disciplina_FKIndex1` (`Requerimento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `req_declaracao_matricula`
--

CREATE TABLE IF NOT EXISTS `req_declaracao_matricula` (
  `Requerimento_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Requerimento_id`),
  KEY `Req_Declaracao_Matricula_FKIndex1` (`Requerimento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `req_ementa`
--

CREATE TABLE IF NOT EXISTS `req_ementa` (
  `Requerimento_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Requerimento_id`),
  KEY `Req_Ementa_FKIndex1` (`Requerimento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `req_extratoacademico`
--

CREATE TABLE IF NOT EXISTS `req_extratoacademico` (
  `Requerimento_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Requerimento_id`),
  KEY `Req_ExtratoAcademico_FKIndex1` (`Requerimento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `req_segunda_chamada`
--

CREATE TABLE IF NOT EXISTS `req_segunda_chamada` (
  `Requerimento_id` int(10) unsigned NOT NULL,
  `Turma_id` int(10) unsigned NOT NULL,
  `data_prova` date DEFAULT NULL,
  PRIMARY KEY (`Requerimento_id`),
  KEY `Req_Segunda_Chamada_FKIndex1` (`Requerimento_id`),
  KEY `Req_Segunda_Chamada_FKIndex2` (`Turma_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `SEQUENCE`
--

CREATE TABLE IF NOT EXISTS `SEQUENCE` (
  `SEQ_NAME` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `turma`
--

CREATE TABLE IF NOT EXISTS `turma` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ano` int(11) DEFAULT NULL,
  `nome` varchar(5) COLLATE utf8_unicode_ci DEFAULT NULL,
  `semestre` int(11) DEFAULT NULL,
  `disciplina_id` int(10) unsigned DEFAULT NULL,
  `professor_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `professor_id` (`professor_id`),
  KEY `disciplina_id` (`disciplina_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1000001 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `unidade`
--

CREATE TABLE IF NOT EXISTS `unidade` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=8 ;

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(10) unsigned NOT NULL,
  `telefone_celular` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `telefone_residencial` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `telefone_comercial` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario_perfil`
--

CREATE TABLE IF NOT EXISTS `usuario_perfil` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Perfil_id` int(10) unsigned NOT NULL,
  `Usuario_id` int(10) unsigned NOT NULL,
  `Curso_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uc_perfil_usuario` (`Perfil_id`,`Usuario_id`),
  KEY `Perfil_has_Usuario_FKIndex1` (`Perfil_id`),
  KEY `Perfil_has_Usuario_FKIndex2` (`Usuario_id`),
  KEY `Usuario_Perfil_FKIndex3` (`Curso_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=116 ;

--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `anexos`
--
ALTER TABLE `anexos`
  ADD CONSTRAINT `anexos_ibfk_1` FOREIGN KEY (`Req_Segunda_Chamada_Requerimento_id`) REFERENCES `req_segunda_chamada` (`Requerimento_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `curso`
--
ALTER TABLE `curso`
  ADD CONSTRAINT `curso_ibfk_1` FOREIGN KEY (`unidade_id`) REFERENCES `unidade` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `disciplina`
--
ALTER TABLE `disciplina`
  ADD CONSTRAINT `disciplina_ibfk_1` FOREIGN KEY (`curso_id`) REFERENCES `curso` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `justificativa`
--
ALTER TABLE `justificativa`
  ADD CONSTRAINT `justificativa_ibfk_1` FOREIGN KEY (`Requerimento_id`) REFERENCES `requerimento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `parecer`
--
ALTER TABLE `parecer`
  ADD CONSTRAINT `parecer_ibfk_1` FOREIGN KEY (`requerimento_id`) REFERENCES `requerimento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `parecer_ibfk_2` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `professor`
--
ALTER TABLE `professor`
  ADD CONSTRAINT `professor_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `reqementa_disciplina`
--
ALTER TABLE `reqementa_disciplina`
  ADD CONSTRAINT `reqementa_disciplina_ibfk_1` FOREIGN KEY (`Disciplina_id`) REFERENCES `disciplina` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `reqementa_disciplina_ibfk_2` FOREIGN KEY (`Req_Ementa_Requerimento_id`) REFERENCES `req_ementa` (`Requerimento_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `requerimento`
--
ALTER TABLE `requerimento`
  ADD CONSTRAINT `requerimento_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `req_acrescimodisciplina_turma`
--
ALTER TABLE `req_acrescimodisciplina_turma`
  ADD CONSTRAINT `req_acrescimodisciplina_turma_ibfk_1` FOREIGN KEY (`Req_Acrescimo_Disciplina_Requerimento_id`) REFERENCES `requerimento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `req_acrescimodisciplina_turma_ibfk_2` FOREIGN KEY (`Turma_id`) REFERENCES `turma` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `req_acrescimo_disciplina`
--
ALTER TABLE `req_acrescimo_disciplina`
  ADD CONSTRAINT `req_acrescimo_disciplina_ibfk_1` FOREIGN KEY (`Requerimento_id`) REFERENCES `requerimento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `req_assinatura`
--
ALTER TABLE `req_assinatura`
  ADD CONSTRAINT `req_assinatura_ibfk_1` FOREIGN KEY (`Requerimento_id`) REFERENCES `requerimento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `req_cancelamentodisciplina_turma`
--
ALTER TABLE `req_cancelamentodisciplina_turma`
  ADD CONSTRAINT `req_cancelamentodisciplina_turma_ibfk_1` FOREIGN KEY (`Req_Cancelamento_Disciplina_Requerimento_id`) REFERENCES `requerimento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `req_cancelamentodisciplina_turma_ibfk_2` FOREIGN KEY (`Turma_id`) REFERENCES `turma` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `req_cancelamento_disciplina`
--
ALTER TABLE `req_cancelamento_disciplina`
  ADD CONSTRAINT `req_cancelamento_disciplina_ibfk_1` FOREIGN KEY (`Requerimento_id`) REFERENCES `requerimento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `req_declaracao_matricula`
--
ALTER TABLE `req_declaracao_matricula`
  ADD CONSTRAINT `req_declaracao_matricula_ibfk_1` FOREIGN KEY (`Requerimento_id`) REFERENCES `requerimento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `req_ementa`
--
ALTER TABLE `req_ementa`
  ADD CONSTRAINT `req_ementa_ibfk_1` FOREIGN KEY (`Requerimento_id`) REFERENCES `requerimento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `req_extratoacademico`
--
ALTER TABLE `req_extratoacademico`
  ADD CONSTRAINT `req_extratoacademico_ibfk_1` FOREIGN KEY (`Requerimento_id`) REFERENCES `requerimento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `req_segunda_chamada`
--
ALTER TABLE `req_segunda_chamada`
  ADD CONSTRAINT `req_segunda_chamada_ibfk_1` FOREIGN KEY (`Requerimento_id`) REFERENCES `requerimento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `req_segunda_chamada_ibfk_2` FOREIGN KEY (`Turma_id`) REFERENCES `turma` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `turma`
--
ALTER TABLE `turma`
  ADD CONSTRAINT `turma_ibfk_1` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `turma_ibfk_2` FOREIGN KEY (`disciplina_id`) REFERENCES `disciplina` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `usuario_perfil`
--
ALTER TABLE `usuario_perfil`
  ADD CONSTRAINT `usuario_perfil_ibfk_1` FOREIGN KEY (`Perfil_id`) REFERENCES `perfil` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `usuario_perfil_ibfk_2` FOREIGN KEY (`Usuario_id`) REFERENCES `usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `usuario_perfil_ibfk_3` FOREIGN KEY (`Curso_id`) REFERENCES `curso` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
