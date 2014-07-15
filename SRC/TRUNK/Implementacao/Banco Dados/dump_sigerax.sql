-- phpMyAdmin SQL Dump
-- version 4.0.8
-- http://www.phpmyadmin.net
--
-- Máquina: localhost
-- Data de Criação: 11-Nov-2013 às 11:18
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
CREATE DATABASE IF NOT EXISTS `sigerax` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `sigerax`;

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=12 ;

--
-- Extraindo dados da tabela `anexos`
--

INSERT INTO `anexos` (`id`, `Req_Segunda_Chamada_Requerimento_id`, `caminho`, `nome`) VALUES
(9, 1000145, 'C:/arquivos_sigera/anexos/20131111085538_turma.png', 'turma.png'),
(10, 1000152, 'C:/arquivos_sigera/anexos/20131111090828_SegundaChamada2.png', 'SegundaChamada2.png'),
(11, 1000183, 'C:/arquivos_sigera/anexos/20131111091720_curso.png', 'curso.png');

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

--
-- Extraindo dados da tabela `configuracao`
--

INSERT INTO `configuracao` (`id`, `data_final_ajustes_matricula`, `data_inicial_ajustes_matricula`, `data_inicial_semestre`, `data_final_semestre`, `ano_corrente`, `semestre_corrente`) VALUES
(1, '2014-07-31', '2012-01-01', '2012-01-01', '2012-07-31', 2012, 1);

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

--
-- Extraindo dados da tabela `curso`
--

INSERT INTO `curso` (`id`, `prefixo`, `cod_matriz_curricular`, `nome`, `unidade_id`) VALUES
(1, 'cc', '18P2IB', 'Ciência da Computação', 7),
(2, 'es', '105P1NB', 'Engenharia de Software', 7),
(4, 'si', '109P1NB', 'Sistemas de Informação', 7),
(5, 'msc', '', 'Mestrado em Ciências da Computação', 7),
(6, 'dsc', '', 'Doutorado em Ciência da Computação', 7),
(7, 'espbd', NULL, 'Especialização em Banco de Dados', 7),
(8, 'espinfedu', '', 'Especialização em Informática Aplicada à Educação', 7),
(9, 'espredes', NULL, 'Especialização em Redes de Computadores e Segurança de Sistemas', 7),
(10, 'espweb', '', 'Especialização em Desenvolvimento de Aplicações Web com Interfaces Ricas', 7);

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

--
-- Extraindo dados da tabela `disciplina`
--

INSERT INTO `disciplina` (`id`, `carga_horaria_pratica`, `carga_horaria_teorica`, `ementa`, `nome`, `curso_id`, `bibliografia_basica`, `bibliografia_complementar`, `objetivo_geral`) VALUES
(1, 0, 64, 'Medidas de complexidade, análise assintótica de limites de complexidade, técnicas de prova de cotas inferiores. Exemplos de análise de algoritmos iterativos e recursivos. Técnicas de projeto de algoritmos eficientes. Programação dinâmica. Algoritmos probabilísticos.', 'ANÁLISE E PROJETO DE ALGORITMOS', 1, 'LEISERSON, Charles, E. RIVEST, Ronald L. CORMEN, Thomas H. Algoritmos - Teoria e Prática, Campus, 2001.', 'BAASE, Sara. GELDER, Allen Van .Computer Algorithms: Introduction to Design and Analysis. (3rd Edition) - SZWARCFITER, Jayme Luiz & MARKENZON, Lilian. “Estrutura de Dados e seus Algoritmos”. LTC Editora. 1994. 2a Edição - MAMBER, Udi. “Introduction to Algorithms”. Addison Wesley Publishing Company. 1989.', '\r'),
(2, 16, 48, 'Entendimento dos mecanismos representação de dados; introdução à Programação em linguagem de máquina e linguagem de montagem; compreensão de instruções, Conjunto de instruções e modos de endereçamento; Compreensão dos mecanismos de controle de fluxo, pilha e subrotina; Apresentação aos mecanismos de interrupção; Comparação entre os diversos métodos de transferência de dados e dispositivos de E/S; Estudo do suporte de hardware para o desenvolvimento de software.', 'ARQUITETURA DE COMPUTADORES', 1, 'HENNESSY, J.L. and PATTERSON, D.A. Computer Architecture: A Quantitative Approach, 2nd. Edition, 1996, Morgan Kaufmann. - PATTERSON, D.A and HENNESSY, J.L. Computer Organization and Design-The hardware software interface , 3nd. Edition, 2005, Morgan Kaufmann. - TANENBAUM, A.. Structured Computer Organization, 4th . Edition, 1999, Prentice-Hall.', 'CARPINELLI, J. Computer Systems Organization & Architecture, Addison-Wesley, 2001. - STALLINGS, W. Computer Organization and Architecture: Designing for Performance, 4th Edition, 1996, Prentice-Hall.', '\r'),
(3, 16, 48, 'Conceitos fundamentais para o projeto, utilização e implementação de banco de dados. O Modelo Relacional: conceitos, restrições de integridade, introdução a álgebra relacional, cálculo relacional, SQL, normalização e dependências funcionais. Projeto de banco de dados: modelagem de dados usando o Modelo E/R; mapeamento ER-relacional; uso de normalização no projeto de BD.', 'BANCO DE DADOS', 1, 'SILBERSCHATZ, A., KORTH, H.F. and SUDARSHAN, S., Sistema de Banco de Dados, Ed. Campus, Rio de Janeiro, 2006.', 'HEUSER, C.A., Projeto de Banco de Dados, 5a edição, Ed. Sagra Luzzatto, Porto Alegre, 2004. - ELMASRI, R. e NAVATHE, S.B., Sistemas de Banco de Dados, 4a Edição, Pearson/Addison Wesley, São Paulo, 2005. - TEOREY, T., LIGHTSTONE, S. and NADEAU, T., Projeto e Modelagem de Bancos de Dados, Ed. Campus, Rio de Janeiro, 2007. - SETZER, V. W. e SILVA, F. S. C., Bancos de Dados, Ed. Edgard Blucher, São Paulo, 2005. - KORTH, H.F. and SILBERSCHATZ, A., Database System Concepts, Ed. MCGRAW-HILL Profissional, 2005. - ELMASRI, R. and NAVATHE, S.B., Fundamentals of Database Systems, 5th ed., Addison Wesley Higher Education, 2006. - KHOSHAFIAN, S., Banco de Dados Orientado a Objetos, IBPI Press, 1994. - DATE, C. J., Introdução a Sistemas de Banco de Dados, tradução da 8o edição americana, Ed. Campus, Rio de Janeiro, 2004. - DATE, C. J., Introduction to Database Systems, Eighth Edition. Addison-Wesley Publishing, 2003. - WELLING, L. and THOMSON, L., Tutorial MySQL, Ed. Ciência Moderna, 2004.', '\r'),
(4, 32, 32, 'A estrutura de um compilador; Análises léxica e sintática e semântica. Organização da tabela de símbolos. Gerenciamento de erros; Síntese de programas-objeto.', 'COMPILADORES', 1, '', '', '\r'),
(5, 32, 32, 'Dispositivos gráficos de entrada e saída. Processadores de exibição gráfica. Teoria de cor. Transformações geométricas bidimensionais e tridimensionais. Transformações entre sistemas de coordenadas bidimensionais. Transformações de projeção paralela e perceptiva. Câmera virtual. Transformações entre sistemas de coordenadas tridimensionais. Rasterização bidimensional. Recorte e seleção bidimensional. Visualização tridimensional: iluminação, eliminação de linhas superfícies escondidas, modelos de tonalização (“shading”). Aplicação de texturas. O problema do serrilhado (aliasing). Percepção visual humana, amostragem, realce, filtragem, restauração de imagem, segmentação de imagem, compressão e comunicação de imagens. Noção de visão computacional e reconhecimento de padrões.', 'COMPUTAÇÃO GRÁFICA', 1, '', '', '\r'),
(6, 0, 32, 'Estudo e análise de situações atuais envolvendo o uso de computadores e como estes afetam a sociedade. Situações específicas: conceito de ética e critérios para tomada de decisões éticas, acesso não autorizado, propriedade intelectual, erros e ameaças à segurança, saúde ocupacional, privacidade e uso de dados pessoais, regulamentação da profissão, inclusão digital, entre outros. Códigos de ética profissional. Estudos de casos', 'COMPUTADOR E SOCIEDADE', 1, 'Masiero, P Ética em Computação, Editora da USP, 1999', 'Masiero, P Ética em Computação, Editora da USP, 1999', '\r'),
(7, 32, 32, 'O papel do empreendedor no processo de criação de novas empresas. Etapas do Processo de Criação de Empresas: a pesquisa de oportunidades, estudo de tendências de mercado. O projeto de criação e início de atividades da nova empresa. Problemas de gestão de micro e pequenas empresas nascentes. Entidades de apoio a pequena e média empresa. Estruturas de cooperação entre empresas', 'EMPREENDEDORISMO', 1, 'DEGEN, Ronald. O Empreendedor - Fundamentos da Iniciativa Empresarial. Ed. McGraw-Hill, São Paulo, 1989', 'RESNIK, Paul. A Bíblia da Pequena Empresa. Makron Books Editora, São Paulo, 1988.', '\r'),
(8, 48, 16, 'Definição de requisitos, quando são produzidos, quais as relações com outros artefatos, desafios e propostas correntes. Os processos relacionados a requisitos: eliciação, análise, especificação (registro) e avaliação (validação) de requisitos. Classificação de requisitos (requisitos de sistema e de software; requisitos funcionais e não-funcionais). Qualidade de requisitos (objetividade, clareza, viabilidade técnica, verificabilidade). Normas e padrões pertinentes. Considerações práticas: aplicação de métodos e técnicas; a natureza iterativa dos processos de requisitos; gerência de mudanças; medição e rastreabilidade de requisitos.', 'ENGENHARIA DE REQUISITOS', 1, 'SOMMERVILLE, Ian. Software Engineering. 8th edition. Addison-Wesley, 2006', 'IEEE. Std 830 – Recommended Practice for Software Requirements Specifications, 1998. - IEEE. Std 1362 – Guide for Information Technology – System Definition – Concept of Operations (ConOps) Document, 1998. - IEEE. Guide to the Software Engineering Body of Knowledge. Cap. 2 – Software Requirements, 2004.', '\r'),
(9, 48, 16, 'Visão geral da engenharia de software, suas subáreas, objetivos, desafios e propostas correntes. Dificuldades essenciais e acidentais da Engenharia de Software. Caracterização de software (produto). Processos do Ciclo de Vida do software. Gerência de projetos de software. Garantia da Qualidade de software. Gerência de configuração de software. Paradigmas de Desenvolvimento e Manutenção de software. Teste de software. Ferramentas de apoio à Engenharia de Software (CASE). Considerações práticas: métodos e técnicas para Engenharia de Software.', 'ENGENHARIA DE SOFTWARE', 1, 'SOMMERVILLE, Ian. Software Engineering. 8th edition. Addison-Wesley, 2006', 'ABNT/ISO/IEC. Norma 12207 - Tecnologia de informação – Processos de ciclo de vida de software, 1998. - IEEE. Guide to the Software Engineering Body of Knowledge, 2004.', '\r'),
(10, 16, 48, 'Tipos abstratos de Dados. Listas: tipos de listas, operações, implementação. Pilhas e filas: tipos, estruturas, aplicações, implementação. Matriz. Árvores: tipos, aplicações, operações e implementação', 'ESTRUTURAS DE DADOS I', 1, 'MORAES, Celso Roberto. Estruturas de Dados e Algoritmos – Uma abordagem didática. Editora Berkeley.2001', 'GOODRICH M. T. et al., Data Structures and Algorithms in Java, John Wiley & Sons, 1998.  - LEISERSON, Charles, E. RIVEST, Ronald L. CORMEN, Thomas H. Algoritmos - Teoria e Prática, Campus, 2001. - Ziviani N., Projeto de Algoritmos com Implementações em Pascal e C, Livraria Pioneira Editora, São Paulo, 1993. - TENNENBAUM, A.M. e AUGENSTEIN,M.J., Data Structures Using C, Prentice Hall Inc., xxxx - Knuth D. E., The Art of Computer Programming, vol. 1 a 3, Addison-Wesley, 1997 - AHO A V, HOPCROFT J. E., and ULLMAN, J. D., Data Structures and Algorithms, Addison-Wesley, 1987,', '\r'),
(11, 16, 48, 'Conceitos Básicos de Armazenamento e Recuperação. Organização e Acesso em Memória Auxiliar. Métodos de Ordenação. Busca. Implementação de Arquivos. Espalhamento (hashing). Casamento de padrão. Compressão.', 'ESTRUTURAS DE DADOS II', 1, 'GOODRICH M. T. et al., Data Structures and Algorithms in Java, John Wiley & Sons, 1998.', 'MORAES, Celso Roberto. Estruturas de Dados e Algoritmos – Uma abordagem didática. Editora Berkeley.2001. - LEISERSON, Charles, E. RIVEST, Ronald L. CORMEN, Thomas H. Algoritmos - Teoria e Prática, Campus, 2001. - VELLOSO, P.; SANTOS, C.; AZEVEDO, P.; FURTADO, A., Estrutura de Dados, Campus, 1986 - SZWARCFITER, J.L., Estruturas de Dados e Seus Algoritmos, LTC, 1994 - KNUTH, D. The Art of Computer Programming, VOLS I, II. III, 2nd ed. 1997 - TANNENBAUM, A.M., e outros Data Structures Using C, Prentice-Hall, 1990 - SATIR, G., BROWN, D. Técnicas de Programação em C++, Infobook, 1997', '\r'),
(12, 16, 48, 'Introdução. Resolução de problemas. Conhecimento e raciocínio. Aprendizagem. Processamento de Linguagem Natural. Aplicações.', 'INTELIGÊNCIA ARTIFICIAL', 1, 'RUSSELL, Stuart e NORVIG, Peter, Inteligência Artificial . Editora Campus – 2004', 'BRATKO, Ivan, Prolog Programming for Artificial Intelligence (International Computer Science Series), Addison-Wesley, 2000. - Luger, George F. Inteligência Artificial - Estruturas e estratégias para a solução de problemas complexos - 4.ed.- 2004', '\r'),
(13, 0, 64, 'Conceituação de computador, informática e software. Histórico da computação. Noções básicas de arquitetura e organização de computadores; unidade central de processamento; memória; conjunto de instruções; execução de instruções; dispositivos de entrada/saída; comunicação com periféricos (fluxo de dados); Armazenamento (disco rígido, CD-ROM, DVD e outros) e representação de dados; sistemas de arquivos; sistemas de numeração; aritmética binária, hexadecimal e decimal; representação de números em ponto fixo e ponto flutuante; representação de caracteres, conceitos de álgebra booleana. Software básico; sistemas operacionais e sistemas gerenciadores de bancos de dados. Conceitos de sistemas; administração da informação; categorização dos sistemas quanto à função, serviços; formas de comercialização, instalação e uso. Noções de software: evolução de linguagens de programação, compiladores e interpretadores, desenvolvimento (análise e projeto), ética, segurança, emprego (e as consequências decorrentes), riscos e tecnologias emergentes. Aplicativos: processadores de texto, planilhas de cálculo, editores gráficos e de apresentações. Comunicação de dados. Multimídia e realidade virtual. Redes de computadores; Internet (organização e serviços); correio eletrônico; navegadores (browsers); busca na Internet; servidores WWW, serviços Web, páginas HTML, aplicações em n-camadas.', 'INTRODUÇÃO À COMPUTAÇÃO', 1, 'MARÇULA, M., FILHO, P. A. B. Informática , Conceitos e Aplicações. 1a edição. Editora Érica, 2005. - BROOKSHEAR, J. G., Ciência da Computação: Uma Visão Abrangente. 7a edição. Bookman, 2005.', 'FEDELI, R. D., POLLONI, E. G. F., PERES, F. E. Introdução à Ciência da Computação . Thomsom Learning, 2003. - VELLOSO, F. de C. Informática Conceitos Básicos. 6a edição. Editora Campus, 2003 - MEYER, M. et al. Nosso Futuro e o Computador . 3a edição. Bookman, 2000. - BOGHI, C., SHITSUKA, R. Sistemas de Informação: Um Enfoque Dinâmico . São Paulo. Editora Érica, 2002. - ELMASRI, R. NAVATHE, S. B. Sistemas de Banco de Dados . LTC, 2002. - LAUDON, K e LAUDON, J. Management Information Systems. 6a edição. Prentice Hall,1999. - LOUKIDES, M. ORAM, A. Programando com Ferramentas GNU”. Conectiva OReilly, 2000. - MASIERO, P C. Ética em Computação . Editora Edusp, 2000. - OLIVEIRA, D. P. R. Sistemas de Informações Gerenciais: Estratégicas, Táticas, Operacionais . São Paulo: Atlas, 2004. - PAIXÃO, R. R., HONDA, R. Processadores Intel . Editora Érica, 1999. - PFAFFENBERGER, B. Computers in Your Future 2003 . Prentice Hall, 2003. - POLLONI, E. G. Sistemas de Informação - Estudo de Viabilidade . Editora Futura, 2001. - TANENBAUM, A. S. Sistemas Operacionais Modernos . São Paulo: Prentice-Hall, 2003. - VAUGHAN, T. Multimídia na Prática . Makron Books, 1994. - WHITE, R. Como Funciona o Computador . 4a edição. Editora Quark, 1993.', '\r'),
(14, 16, 48, 'Estudo dos conceitos de linguagens de programação e dos paradigmas de programação: procedural, orientado a objetos, funcional e lógico. Comparação entre linguagens de programação quanto às estruturas de dados, estruturas de controle, ambiente de execução, verificação de tipos, expressões, construção de subprogramas. Levantamento das características desejáveis em uma linguagem de programação, sintaxe e semântica. Reflexão sobre critérios de seleção de linguagens de programação de acordo com as especificidades das aplicações específicas.', 'LINGUAGENS DE PROGRAMAÇÃO', 1, 'SEBESTA R. W. - Concepts of programming languages - 8a ed. Addison-Wesley, 2007.', 'GHEZZI, C. & JAZAYERI, M. – Programming Languages Concepts. 2a. ed. John Wiley & Sons, New York, 1997.', '\r'),
(15, 0, 64, 'Gramáticas, Linguagens regulares, livres de contexto e sensíveis ao contexto. Tipos de reconhecedores. Operações com linguagens. Propriedades de linguagens. Autômatos de estados finitos. Autômatos de pilha. Máquina de Turing.', 'LINGUAGENS FORMAIS E AUTÔMATOS', 1, 'HOPCROFT, J.E., ULLMAN, J Introdução à teoria de Autômatos, Linguagens e Computação. Ed. Campus. 2002 - LEWIS, H.R., PAPADIMITRIOU, C.H. Elementos de Teoria da Computação. 2 ed. Porto Alegre : Bookman Cia. Editora, 2000.', 'SUDKAMP, Thomas A., Languages and Machines. Addison Wesley Publishing Company. 1997. Second Edition. - WOOD, D. Theory of Computation, EUA : John Wiley & Sons, 1987.. - SIPSER, M. Introduction to the Theory of Computation. EUA : PWS Pub. Co., 1997. - MORET, B.M. The Theory of Computation. EUA : Addison-Wesley Pub. Co., 1997. - MENEZES, Linguagens Formais e Autômatos, Série UFRGS 03, Editora Sagra.', '\r'),
(16, 0, 64, 'Lógica Proposicional. Proposições e conectivos. Operações Lógicas sobre proposições. Construção de tabelas-verdade. Tautologias, contradições e contingências. Implicação Lógica. Equivalência Lógica. Álgebra das proposições. Métodos para determinação da validade de fórmulas da Lógica Proposicional. Demonstração condicional e demonstração indireta. Lógica de Predicados.', 'LÓGICA MATEMÁTICA', 1, 'SOUZA, João Nunes de. Lógica para Ciência da Computação. Editora Campus 2002. - Alencar Filho, Edgard de, Iniciação à Lógica Matemática. Ed. Nobel 2002.', 'Mendelson, E. Introduction to Mathematical Logic. Lewis Publishers, Inc. 1997. - Enderton, H. A Mathematical Introduction to Logic. Academic Press 2000 - DAGHLIAN, Jacob, Lógica e Álgebra de Boole. –4a edição, Atlas, S. A . São Paulo, 1995.', '\r'),
(17, 0, 64, 'Conjuntos, Relações, funções, ordens parciais e totais, Indução matemática, recursão, Teoria de números, criptografia.', 'MATEMÁTICA DISCRETA', 1, 'GERSTING, Judith L., Fundamentos Matemáticos para a Ciência da Computação. 3a. edição, Editora LTC. - SCHEINERMAN, E. . MATEMATICA DISCRETA, THOMSON PIONEIRA, 2003.', 'ROSEN, K. Discrete Mathematics and its Applications. McGraw-Hill Science/Engineering/Math; 5th edition., 2002. - TREMBLEY and Manohar. Discrete Mathematical Structures with Applications to Computer Science. McGraw-Hill. - Ross and Wright. Discrete Mathematics. Prentice-Hall. - GRIMALDI. Discrete and Combinatorial Mathematics—An Applied Introduction. Addison-Wesley. - ALBERTSON and Hutchinson. Discrete Mathematics with Applications. John Wiley & Sons. - Kenneth, R. Exploring Discrete Mathematics With Maple, McGraw-Hill Science/Engineering/Math; 4th edition', '\r'),
(18, 32, 32, 'Visão geral dos sistemas multimídia. Autoria: plataformas para multimídia e ferramentas de desenvolvimento. Imagens: representação digital, dispositivos gráficos e processamento. Representação de figuras e animação. Vídeo: interfaces e processamento. Métodos de compactação de vídeo. Som: propriedades físicas, representação digital, processamento e síntese. Métodos de compactação de som. Ferramentas para geração de Hipertextos. Padrões HTML, XML, SMIL e VRML. Sistemas operacionais multimídia. Sistemas de comunicação multimídia. Sincronização. Aplicações multimídia.', 'MULTIMÍDIA', 1, '01.STEINMETZ, R. e NAHRSTEDT. Multimedia: Computing, Communications & Applications. Prentice Hall,1995.', 'FILHO, Paula; PADUA, Wilson .Multimidia Conceitos e Aplicações. LTC, 2000. - PINTO, Marcos José. Flash 4. São Paulo, 1999', '\r'),
(19, 0, 64, 'Modelos Lineares de Otimização. Programação Linear. Algoritmo Simplex. Dualidade. Análise de Sensibilidade. Modelos de Redes. Programação Inteira. Programação Dinâmica.', 'PESQUISA OPERACIONAL', 1, 'HILLIER, Frederick S. and LIEBERMAN, Gerald J. Introduction to Operations - Research. McGraw-Hill Science/Engineering/Math; 7th edition (March 22, 2002)', 'GOLDBARG, Marco Cesar e LUNA, Henrique Pacca L. Otimização Combinatória e Programação Linear: Modelos e Algoritmos. Editora Campus. Seg. Edição 2005. - B. S. Bazaraa. Linear programming and network Flows. J. Wiley, 1990. - MAHMUT PARLAR. Interactive Operations Research With Maple: Methods and Models. Birkhauser (August 2000). - WINSTON,Wayne L Operations Research Applications and Algorithms. Duxbury Press3 edition (January 13, 1997). - SILVA, Ermes et al., Pesquisa Operacional - Programação linear Simulação, Atlas, 1998 - BRONSON, R. Pesquisa Operacional, McGrawHill, 1985 - PRADO,D. Programação Linear, Ed. DG, 1999', '\r'),
(20, 32, 32, 'Lógica de programação; constantes; tipos de dados primitivos; variáveis; atribuição; expressões aritméticas e lógicas; estruturas de decisão; estruturas de controle; estruturas de dados homogêneas e heterogêneas: vetores (arrays) e matrizes; funções; recursão. Desenvolvimento de algoritmos. Transcrição de algoritmos para uma linguagem de programação. Domínio de uma linguagem de programação: sintaxe e semântica; estilo de codificação; ambiente de desenvolvimento. Desenvolvimento de pequenos programas.', 'PROGRAMAÇÃO DE COMPUTADORES I', 1, 'Cormen T. H. et al., Introduction to Algorithms, 2nd edition, MIT Press, 2001. - Manber, Udi., Introduction to Algorithms: A Creative Approach, Pearson Education, 1989. - Cormen T. H. et al., Algoritmos: Teoria e Prática, Tradução da 2a Edição Ed. Campus, 2002 - Forbellone, A.L.V. e Eberspacher, H.F., Lógica de Programação - A construção de algoritmos e estruturas de dados, 3a ed., Prentice Hall, São Paulo, 2005. - Manzano, José Augusto N. G. e Oliveira, J. F., Algoritmos – Lógica para Desenvolvimento de Programação de Computadores, Editora Érica, São Paulo, 2000. - Salvetti, D.D. e Barbosa, L.M., Algoritmos, Makron Books, São Paulo, 1998. - Saliba, W.L.C., Técnicas de Programação - Uma abordagem estruturada, Makron Books, São Paulo, 1993. - Farrer, H. e outros, Programação Estruturada de Computadores - Algoritmos Estruturados, 3a ed., LTC, RJ, 1989. - Tremblay, J.-P Bunt, R.B., Ciência dos Computadores - Uma Abordagem .e Algorítmica, McGraw-Hill do Brasil, São Paulo, 1983.', 'Cormen T. H. et al., Introduction to Algorithms, 2nd edition, MIT Press, 2001. Manber, Udi., Introduction to Algorithms: A Creative Approach, Pearson Education, 1989. - Cormen T. H. et al., Algoritmos: Teoria e Prática, Tradução da 2a Edição Ed. Campus, 2002 - Forbellone, A.L.V. e Eberspacher, H.F., Lógica de Programação - A construção de algoritmos e estruturas de dados, 3a ed., Prentice Hall, São Paulo, 2005. - Manzano, José Augusto N. G. e Oliveira, J. F., Algoritmos – Lógica para Desenvolvimento de Programação de Computadores, Editora Érica, São Paulo, 2000. - Salvetti, D.D. e Barbosa, L.M., Algoritmos, Makron Books, São Paulo, 1998. - Saliba, W.L.C., Técnicas de Programação - Uma abordagem estruturada, Makron Books, São Paulo, 1993. - Farrer, H. e outros, Programação Estruturada de Computadores - Algoritmos Estruturados, 3a ed., LTC, RJ, 1989. - Tremblay, J.-P Bunt, R.B., Ciência dos Computadores - Uma Abordagem. e Algorítmica, McGraw-Hill do Brasil, São Paulo, 1983', '\r'),
(21, 32, 32, 'Aprofundamento das técnicas de programação de computadores, especialmente quanto à implementação de programas em uma linguagem procedural e de alto nível. A implementação de programas deve ser feita utilizando uma ferramenta/ambiente de desenvolvimento que permita a edição, compilação, depuração dos códigos, empacotamento e distribuição. Interpretação de enunciados de problemas e a elaboração de soluções na forma de programas implementados em uma linguagem de alto nível. Elaboração de programas modularizados, criando funções e procedimentos, funções recursivas, e utilizando bibliotecas. Domínio de uma linguagem de programação procedural quanto à sintaxe, semântica, estilo, convenções, ferramenta/ambiente de desenvolvimento. Implementação de programas utilizando, de forma aprofundada, estruturas de dados homogêneas e heterogêneas, ponteiros e referências de memória, e manipulação de arquivos (streams).', 'PROGRAMAÇÃO DE COMPUTADORES II', 1, 'Cormen T. H. et al., Introduction to Algorithms, 2nd edition, MIT Press, 2001.', 'Cormen T. H. et al., Introduction to Algorithms, 2nd edition, MIT Press, 2001.', '\r'),
(22, 32, 32, 'Estudo do modelo de programação orientada a objetos, abordando abstração, encapsulamento, classes, métodos, objetos, herança, polimorfismo. Construção de aplicações orientadas a objeto envolvendo interfaces gráficas, manipulação de eventos, tratamento de exceções, uso de streams e tratamento de concorrência com threads. A implementação das aplicações deve ser feita utilizando ferramentas de desenvolvimento com testes de unidade, depuração e controle de versão. Introdução ao projeto orientado a objetos com noções de UML, padrões de projeto (design patterns) e arquitetura de software.', 'PROGRAMAÇÃO ORIENTADA A OBJETOS', 1, 'DEITEL, H. M. and Deitel, P J. Java Como Programar. 6a . edição. Pearson, 2006.', 'SANTOS, R., Introdução a Programação Orientada a Objetos com Java, Campus, 2003. - GAMMA E. et al. Design Patterns, Addison-Wesley, 1995. - TIMOTHY, B., An Introduction to Object-Oriented Programming. Addison Wesley, 1996', '\r'),
(23, 32, 32, 'Princípios de projeto de software. O contexto do design no Ciclo de Vida do Software. Processos de design: arquitetura e detalhamento. Arquitetura de software (definição, principais estruturas). Padrões macro-arquiteturais (estilos de arquitetura). Padrões micro-arquiteturais (padrões de projeto ou design patterns). Aspectos notáveis de design (concorrência, controle de eventos, distribuição, tratamento de exceções, tolerância a falhas, interface e persistência). Modelagem e notações para projeto estático (estrutura) e dinâmico (comportamento) de software. Métodos de projeto. Métricas e avaliação da qualidade de design de software. Considerações práticas: desenvolvimento e documentação de software design. Normas e padrões pertinentes.', 'PROJETO DE SOFTWARE', 1, '', '', '\r'),
(24, 16, 48, 'Estudo e compreensão dos princípios fundamentais de modelos de referência de redes de computadores. Estudo e compreensão dos princípios e protocolos da Camada de Aplicação, com ênfase no desenvolvimento de aplicações em rede e no estudo das principais aplicações da Internet. Estudo e compreensão dos fundamentos da Camada de Transporte, compreendendo protocolos de transferência confiável de dados, modelos de serviço com e sem conexão, controle de fluxo, e controle de congestionamento, além dos protocolos de transporte utilizados na Internet. Estudo e compreensão dos conceitos da Camada de Redes, seus modelos de arquitetura (datagramas e circuitos virtuais), protocolos de nível 3 da Internet, arquitetura de roteadores, protocolos de roteamento, broadcast e multicast, e configuração de redes.', 'REDES DE COMPUTADORES I', 1, 'KUROSE, J.F.; ROSS, K. – Redes de Computadores e a Internet, 3a. Edição. Pearson Education, 2005 - PETERSON, L.L.; DAVIE, B.S. – Redes de Computadores: Uma Abordagem de Sistemas, 3a. Edição. Campus-Elsevier, 2004. - TANENBAUM, A. S., Computer Networks, 4th Edition, Prentice Hall, 2006.', 'KUROSE, J.F.; ROSS, K. – Redes de Computadores e a Internet, 3a. Edição. Pearson Education, 2005 - PETERSON, L.L.; DAVIE, B.S. – Redes de Computadores: Uma Abordagem de Sistemas, 3a. Edição. Campus-Elsevier, 2004. - TANENBAUM, A. S., Computer Networks, 4th Edition, Prentice Hall, 2006.', '\r'),
(25, 16, 48, 'Compreender os conceitos e tecnologias da Camada de Enlace, incluindo protocolos ponto-a-ponto e redes de meio compartilhado, protocolos de acesso ao meio, redes locais, dispositivos de interconexão de redes (switches e hubs), e redes de alta velocidade. Estudar os conceitos da Camada Física e os princípios básicos de telecomunicações e transmissão de dados. Compreender os conceitos e tecnologias de Redes Sem Fio, incluindo redes de área local (WLAN), pessoal (WPAN) e de longa distância (WWAN), abordando também os princípios básicos de redes de telefonia celular e da convergência entre redes de telefonia móvel e redes sem fio de computadores. Estudar e praticar o uso dos conceitos fundamentais e protocolos de Gerenciamento de Redes, com ênfase no gerenciamento de redes baseadas na Internet. Entender os princípios e aplicações dos protocolos de Segurança em redes de computadores.', 'REDES DE COMPUTADORES II', 1, 'KUROSE, J.F.; ROSS, K. – Redes de Computadores e a Internet, 3a. Edição. Pearson Education, 2005 - PETERSON, L.L.; DAVIE, B.S. – Redes de Computadores: Uma Abordagem de Sistemas, 3a. Edição. Campus-Elsevier, 2004. - TANENBAUM, A. S., Computer Networks, 4th Edition, Prentice Hall, 2006.', 'KUROSE, J.F.; ROSS, K. – Redes de Computadores e a Internet, 3a. Edição. Pearson Education, 2005 - PETERSON, L.L.; DAVIE, B.S. – Redes de Computadores: Uma Abordagem de Sistemas, 3a. Edição. Campus-Elsevier, 2004. - TANENBAUM, A. S., Computer Networks, 4th Edition, Prentice Hall, 2006.', '\r'),
(26, 32, 0, 'Visão geral de segurança e auditoria de sistemas de informação (riscos, planos de contingência e outros). Autenticação, autorização, integridade e confidencialidade. Criptografia. Chave pública. Certificado digital. Assinatura digital. Protocolos. Prática (estudantes deverão ser expostos a código, bibliotecas e uso destes).', 'SEGURANÇA E AUDITORIA DE SISTEMAS', 1, 'ISO/IEC. Norma 17799 - Tecnologia da Informação – Código de Prática para Gestão da Segurança de Informações, 2000.', 'Foundations of Security: What Every Programmer Needs to Know, Neil Daswani et al., Apress, 2007. - SCHMIDT, Paulo; SANTOS, Jose Luiz dos; ARIMA, Carlos Hideo. Fundamentos de Auditoria de Sistemas Rio de Janeiro, Atlas , 2006.', '\r'),
(27, 16, 48, 'Introduzir os conceitos fundamentais de sistemas distribuídos, a caracterização de sistemas de computação distribuída, aplicações distribuídas (características e aspectos de projeto), objetivos básicos de sistemas distribuídos (transparência, abertura, escalabilidade etc). Estudar e dominar os princípios e aplicações dos principais modelos de sistemas distribuídos: sistemas cliente/servidor e sistemas multi-camadas; sistemas peer-to-peer. Compreender a teoria e prática de objetos distribuídos: interface x implementação; objetos remotos; chamadas de métodos remotos (RMI). Estudar e compreender algumas das principais tecnologias e padrões de middleware de processamento distribuído aberto, incluindo a caracterização de sistemas ODP; o uso de middleware como suporte para o desenvolvimento de aplicações em ambientes distribuídos abertos; exemplos de plataformas de middleware e seu uso. Estudar os princípios e uso dos principais serviç - Os de sistemas distribuídos: serviços de nomes; compartilhamento de documentos / recursos distribuídos (ex.: WWW e sistemas de trabalho cooperativo); segurança. Estudar os fundamentos de tolerância a falhas em sistemas distribuídos: comunicação confiável; replicação e manutenção de consistência entre réplicas; controle de concorrência e transações distribuídas; comunicação de grupo. Compreender as noções básicas de sistemas de multimídia distribuída: características da comunicação de dados multimídia, qualidade de serviço, gerenciamento de recursos, adaptação de fluxos de mídia. Estudar alguns tópicos avançados em Sistemas Distribuídos não contemplados na ementa.', 'SISTEMAS DISTRIBUÍDOS', 1, 'COULOURIS, G. F.; DOLLIMORE, J.; KINDBERG, T. - Sistemas Distribuídos: Conceitos e Projeto, 4a. Edição. Pearson Education, 2007.', 'TANENBAUM, A.S.; STEEN, M. – Sistemas Distribuídos: Princípios e Paradigmas, 2a. Edição. Pearson Education, 2007.', '\r'),
(28, 16, 48, 'Sistemas de gerenciamento de Banco de Dados (SGBD): arquitetura e aspectos operacionais; Organização de Dados e Estruturas de Armazenamento, algebra Relacional, Processamento de consultas; Controle de concorrência, Recuperação de falhas, Gerência de Implementação de SGBD. transações; Segurança. Estudos de Projeto e Implementação de SGBD.', 'SISTEMAS GERENCIADORES DE BANCO DE DADOS', 1, 'ELMASRI, NAVATHE. Sistemas de Banco de Dados - Fundamentos e Aplicações. Editora: 4a Edição. Addison-Wesley, 2005', 'CONNOLLY, BEGG, Database Systems, 3rd. Edition, Addison Wesley, 2002 - ELMASRI, Navathe, Fundamentals of Database Systems, 5/E. Editora: Addison Wesley Higher Education, 2006 - KORTH, SILBERSCHATZ, SUDARSHAN. Database System Concepts McGraw-Hill Science/Engineering/Math; 5 edition (May 17, 2005) - KORTH, SILBERSCHATZ, Sistemas de Banco de Dados, 5a. Edição, Campus, 2006 - DATE, C, J.Introdução a Sistemas de Banco de Dados. 8o Edição, Campus, 2005.', '\r'),
(29, 16, 48, 'Estudo das funções, tipos e estruturas de Sistemas Operacionais; Gerenciamento de processos e threads; Comunicação e Sincronização entre Processos; Programacao Concorrente; Gerenciamento de memória. Estudo de caso dos conceitos abordados.', 'SISTEMAS OPERACIONAIS I', 1, 'TANNENBAUM, Andrew S., Sistemas Operacionais Modernos, 2nd Edition, Prentice-Hall, 2003', 'SHAY, W., Sistemas Operacionais, Makron Books, 1996 - DAVIS, W. Sistemas Operacionais,: uma visão sistemática, Campus, 1991', '\r'),
(30, 16, 48, 'Gerenciamento de dispositivos; Sistemas de Arquivos; Segurança em Sistemas Operacionais; Sistemas operacionais distribuídos; Estudo de caso dos conceitos abordados.', 'SISTEMAS OPERACIONAIS II', 1, 'TANNENBAUM, Andrew S., Sistemas Operacionais Modernos, 2nd Edition, Prentice-Hall, 2003 - COULOURIS, Georges, Sistemas Distribuidos conceitos e Projetos, Bookman, 3a Edition, 2007 - SILBERSCHATZ, Avi & GALVIN, Peter. Sistemas Operacionais Conceitos. Prentice-Hall. 2005 - KIFER, Michael & SMOLKA, Scott: OSP: An environment for Operating Systems Projects, Addison-Wesley, 1991. - TANENBAUM, Andrew S., Distributed Operating Systems. Prentice-Hall International Editions, 1995', 'STALLINGS, William. Operating Systems. Prentice-Hall. 1995 - TANEMBAUM, Andrew S. & WOODHULL, Albert. Operating Systems Design and Implementation. Prentice-Hall 1997. - VAHALIA, Uresh, Unix Internals, Prentice Hall 1996.', '\r'),
(31, 0, 64, 'Noções de computabilidade efetiva. Modelos de computação. Problemas indecidíveis. Classes P, NP, NP-Completa e NP-Difícil. Algoritmos de Aproximação.', 'TEORIA DA COMPUTAÇÃO', 1, '', '', '\r'),
(32, 0, 64, 'Noções básicas de grafos. Representação de grafos. Distâncias. Coloração. Matching. Conjuntos independentes de vértices. Planaridade. Problemas do caminho mínimo. Problemas Eulerianos e Hamiltonianos. Fluxo em redes.', 'TEORIA DOS GRAFOS', 1, 'SZWARCFITER, J. L., Grafos e Algoritmos Computacionais, Editora Campus,1984.', 'YELENN, J, Gross, J. Graph Theory and Its Applications. CRC Press, 1998 - WEST, D. Introduction to Graph Theory, Prentice Hall, 2000 - GIBBONS, Alan - Algorithmic Graph Theory,Cambridge University Press, 1994.', '\r'),
(33, 0, 64, 'Tópico variável em computação segundo tendências atuais na área.', 'TÓPICOS I', 1, '--', '--', '\r'),
(34, 0, 64, 'Tópico variável em computação segundo tendências atuais na área.', 'TÓPICOS II', 1, '--', '--', '\r'),
(35, 32, 32, 'Conceitos básicos. Representações de grafos. Grafos dirigidos. Pesquisa em grafos. Árvores geradoras mínimas. Caminhos mínimos.', 'ALGORITMOS EM GRAFOS', 2, 'Bundle of Algorithms in Java, Parts 1-5: Fundamentals, Data Structures, Sorting, Searching, and Graph Algorithms, Robert Sedgewick, Addison-Wesley, 3rd edition, 2003', 'Dictionary of Algorithms and Data Structures. URL: http://www.nist.gov/dads/.', 'O estudante deve ser capaz de selecionar, implementar e fazer uso das estruturas de dados e algoritmos abordados.\r'),
(36, 32, 32, 'Princípios de análise de algoritmos. Estruturas de dados elementares. Tipos abstratos de dados. Recursão e árvores.', 'ALGORITMOS: FUNDAMENTOS E ESTRUTURAS DE DADOS', 2, 'Bundle of Algorithms in Java, Parts 1-5: Fundamentals, Data Structures, Sorting, Searching, and Graph Algorithms, Robert Sedgewick, Addison-Wesley, 3rd edition, 2003', 'Dictionary of Algorithms and Data Structures. URL: http://www.nist.gov/dads/.', 'O estudante deve ser capaz de selecionar, implementar e fazer uso das estruturas de dados abordadas.\r'),
(37, 32, 32, 'Métodos elementares e avançados de ordenação. Métodos elementares de pesquisa. Árvores de pesquisa. Hashing. Pesquisa externa.', 'ALGORITMOS: ORDENAÇÃO E BUSCA', 2, 'Bundle of Algorithms in Java, Parts 1-5: Fundamentals, Data Structures, Sorting, Searching, and Graph Algorithms, Robert Sedgewick, Addison-Wesley, 3rd edition, 2003; Introdução à Estruturas de Dados, Waldemar Celes e José Lucas Rangel, Ed. Campus, 2004; Projeto de Algorithmos, Nivio Ziviane, Ed. Cengage, 2010.', 'Algorithmos e Estruturas de Dados, Nicklaus Wirth, Ed. LTC, 1989; Estruturas de Dados com Algorithmos em C, Marcos Laureano, Ed. Brasport, 2000; Dictionary of Algorithms and Data Structures. URL: ', 'O estudante deve ser capaz de selecionar, implementar e fazer uso das estruturas de dados abordadas.\r'),
(38, 0, 64, 'Visão geral dos computadores modernos. Evolução da arquitetura dos computadores. Sistemas de numeração e aritmética binária. Memória e representação da dados e instruções. Processador, ciclo de instrução, formatos, endereçamento, e programação em linguagem de montagem. Dispositivos de entrada e saída. Sistemas de interconexão (barramentos). Interfaceamento e técnicas de entrada e saída. Hierarquia de memória. Paralelismo ao nível de instrução. Arquiteturas paralelas.', 'ARQUITETURA DE COMPUTADORES', 2, 'Computer Organization and Design: The Hardware/Software Interface, David A. Patterson and John L. - Hennessy, Morgan Kaufmann, 3rd edition, 2007', 'Arquitetura de Computadores Pessoais, Raul Fernando Weber, Sagra Luzzatto, 2a. edição, 2001.', 'Fornecer uma visão geral da organização de computadores.\r'),
(39, 32, 32, 'Definição de arquitetura de software. Importância e impacto em um software. Estilos arquiteturais (pipe-and-filter, camadas, transações, publish-subscribe, baseado em eventos, cliente-servidor, MVC e outros). Relação custo/benefício entre atributos e opções arquiteturais. Questões de hardware em projeto de software. Rastreabilidade de requisitos e arquitetura de software. Arquiteturas específicas de um domínio e linhas de produto. Notações arquiteturais (visões, representações, diagramas de componentes e outros). Reutilização.', 'ARQUITETURA DE SOFTWARE', 2, 'sential Software Architecture, Ian Gordon, Springer, 2006 (; Systems Architecture: Working With Stakeholders Using Viewpoints and Perspectives, Nick Rozanski and Eóin Woods, 2nd edition, Addison-Wesley, 2011 (). - Lea; eure: for Agile Software Development, James O. Coplien et al., Wiley, 2010 .', 'A Software Architecture Primer, John Reekie and Rohan McAdam, Angophora Press, 2006 ().  - Software Architecture in Practice, Len Bass et al., Addison-Wesley, 2003 (). - Patterns of Enterprise Application Architecture, Martin Fowler, Addison-Wesley, 2002 (). - Design and Use of Software Architecture: Adopting and Evolving a Product-Line Approach, Jan Bosch, Addison-Wesley, 2000; Architecting Enterprise Solutions: Patterns for High-Capability Internet-based Systems, Paul Dyson and Andrew Longshaw, Wiley, 2004; Pattern-Oriented Software Architecture Volume 1: A System of Patterns, Frank Buschmann et al., Wiley, 1996; Pattern-Oriented Software Architecture Volume 5: On Patterns and Pattern Languages, Frank Buschmann et al., Wiley, 2007; Handbook of Software Architecture, Grady Booch, (disponível na web); SOA in Practice: The Art of Distributed System Design, Nicolai M. Josuttis, O’Reilly, 2007; ', 'Desenvolver clara percepção de arquitetura de software, sua importância e implicações no sucesso ou não de um empreendimento de software. O estudante ainda deve adquirir habilidade para identificar modelos arquiteturais apropriados para problemas e habilidade para definir uma arquitetura de software para um dado cenário.\r'),
(40, 32, 32, 'Conceitos básicos. Componentes de sistemas de bancos de dados (database systems). Modelagem conceitual (ER e EER). Modelo relacional. Prática de modelagem de dados. Noções de álgebra e cálculo relacional. Mapeamento de esquema conceitual para esquema relacional. Linguagem SQL (extensiva apresentação e prática). Restrições de integridade. Dependências funcionais e formas normais. Transações. Visão geral de mineração de dados e Data Warehousing.', 'BANCO DE DADOS', 2, 'Beginning Database Design: From Novice to Professional, Clare Churcher, Apress, 2007', 'Fundamentals of Database Systems, Ramez Elmasri e Shamant B Navathe, Addison-Wesley, 5th - edition, 2007. - Database Systems: An Application-Oriented Approach, Introductory Version, Michael Kifer et al., - Addison-Wesley, 2nd edition, 2004. - Head First SQL: Your Brain on SQL - A Leaner’s Guide, Lynn Beighley, O’Reilly, 2007.', 'O estudante deverá ser capaz de modelar e implementar bases de dados a partir de uma especificação de requisitos. A implementação inclui a criação de bases de dados e a execução de consultas SQL em um SGBD real. Adicionalmente, o estudante deverá adquirir uma visão sólida de atomicidade, consistência, isolamento e durabilidade (conhecidas por ACID) para uma transação.\r'),
(41, 48, 16, 'Fundamentos de construção de software (minimizar complexidade, antecipar mudanças, construção para a verificação e padrões em construção de software). Gerência de construção (modelos de processos de construção, planejamento de construção, medidas de construção). Projeto detalhado e construção de software. Linguagens empregadas na construção de software. Codificação. Testes de unidade e de integração. Reutilização de software. Qualidade de código. Integração.', 'CONSTRUÇÃO DE SOFTWARE', 2, 'Code Complete: Um guia prático para a construção de software, Steve McConnell, Microsoft Press, 2nd Edition, 2004', 'Head First Software Development, Dan Pilone and Russ Miles, O’Reilly, 2008 - IEEE, Guide to the Software Engineering Body of Knowledge, 2004 Version', 'Dominar “boas” práticas de construção de software.\r'),
(42, 16, 48, 'Liveness. Safety. Semáforos. Locks. Threads. Deadlocks. Implementações de algoritmos concorrentes.', 'DESENVOLVIMENTO DE SOFTWARE CONCORRENTE', 2, 'Concurrent Programming in Java: Design Principles and Patterns, Douglas Lea, Addison-Wesley, 3rd edition, 2006; Pattern-Oriented Software Architecture Volume 2: Patterns for Concurrent and Networked Objects, Douglas Schmidt et al., Wiley, 2000; Pattern-Oriented Software Architecture Volume 3: Patterns for Resource Management, Michael Kircher, Wiley, 2004; ', 'Java Concurrency in Practice, Brian Goetz et al., Addison-Wesley, 2006 ', 'Expor o estudante a conceitos, desafios e ferramentas disponíveis para o desenvolvimento de software concorrente e à prática de tal atividade. \r'),
(43, 48, 16, 'Noções de hardware de dispositivos móveis (celulares, PDAs e sensores). Ambientes, tecnologias e ferramentas para desenvolvimento de software para dispositivos móveis. Prática de desenvolvimento de uma aplicação para dispositivos móveis.', 'DESENVOLVIMENTO DE SOFTWARE PARA DISPOSITIVOS MÓVEIS', 2, 'Mobile Phone Programming and its Application to Wireless Networking, Frank H. P. Fitzek e Frank Reichert, Springer, 2007 (; g J2ME: From Novice to Professional, Sing Li and Jonathan Knudsen, APress, 3rd edition, 2005 (). Kic; ith MIDP and MSA: Creating Great Mobile Applications, Jonathan Knudsen, Prentice-Hall, 200.', 'crosoft® Mobile Development Handbook, Andy Wigley et al., Microsoft Press, 2007 ', 'Ambientar o estudante com recursos, restrições, ambientes, ferramentas e tecnologias pertinentes a dispositivos móveis, bem como o desenvolvimento de software para tais dispositivos.\r'),
(44, 16, 48, 'Tecnologias, técnicas, ferramentas e abordagens para o desenvolvimento de aplicações web.', 'DESENVOLVIMENTO DE SOFTWARE PARA WEB', 2, 'b Application Architecture: Principles, Protocols and Practice, Leon Shklar e Richard Rosen, Wiley, 2003 (;ng Web Programming with HTML, XHTML, and CSS, Jon Duckett, Wrox, 2nd edition, 2008 (). - Pro;Apache Tomcat 6, Viveck Chopra et al., Wrox, 2007.', 'ring in Action, Craig Walls and Ryan Breidenbach, Manning, 2007 (;g Spring 2 Enterprise Applications, Interface21 et al., Apress, 2007 (). - Mur; Servlets and JSP, Joel Murcah, Mike Murach & Associates, 2008 (). - Beginning; nd Tomcat Web Development: From Novice to Professional, Giulio Zambon and Michael Sekler, Apress, 2007 .', 'Exercitar o conhecimento da Engenharia de Software em aplicação que faz uso da web e, em consequência, exige o domínio de tecnologias pertinentes.\r'),
(45, 16, 48, 'Definição de persistência. Persistência empregando arquivos binários, documentos XML, objetos serializáveis, SGBDs. Tecnologias para persistência de informações. Persistência de objetos usando base relacional.', 'DESENVOLVIMENTO DE SOFTWARE PARA PERSISTÊNCIA', 2, 'Persistence in the Enterprise: A Guide to Persistence Technologies, Geoffrey Hambrick et al., IBM Press, 2008 (; g EJB 3 Application Development: From Novice to Professional, Raghu R. Kodali et al., Apress, 2006 (). - Pro; Persistence API, Mike Keith and Merrick Schincariol, Apress, 2006 ', 'Spring Persistence - A Running Start, Paul Fisher e Solomon Duskis, APress, 2009', 'Solidificar conceitos e habilidade de programação envolvendo persistência de informações.\r'),
(46, 64, 0, 'Definição de sistema, software e Engenharia de Software. Contexto social e de negócio da Engenharia de Software. Áreas do conhecimento da Engenharia de Software (requisitos, projeto de software e demais). Métodos de desenvolvimento de software. Ferramentas.', 'ENGENHARIA DE SOFTWARE', 2, 'Software Engineering: The Development Process, Richard H. Thayer and Mark J. Christensen, Wiley-IEEE Computer Society Press, 3rd edition, 2005 (; nia de Software, Ian Sommerville, 9a. edição, Pearson, 2011.  - Softw; rngineering: A Practitioner’s Approach, Roger S. Pressman, 7th international edition, McGraw-Hill, 2009', 'IEEE Computer Society Real-World Software problems: A Self-Study Guide for Today’s Software Professional, Wiley-IEEE Computer Society Press, 2006 (; the Software Engineering Body of Knowledge, IEEE Computer Society, 2004 (). - Eng; dSoftware- Teoria e Prática, James F. Peters and Witold Pedrycz, Ed. Campus, 2001.', 'Adquirir uma visão consistente e em profundidade da Engenharia de Software, o que inclui a compreensão das várias áreas do conhecimento pertinentes e as relações entre elas.\r'),
(47, 64, 0, 'Noções de ética. Código de ética para engenheiros de software. Visão geral de normas e padrões internacionais, leis e resoluções locais pertinentes à Engenharia de Software. Nomenclatura empregada pela área conforme a norma IEEE Std 12207-2008. Resolução de conflitos. Como se preparar para e se portar em reuniões. Aspectos higiênicos. Aspectos de apresentação pertinentes a trajes. Aspectos de conduta. Atitudes empreendedoras. Instrumentos do empreendedor (plano de negócios e outros). Técnicas de identificação de oportunidades e procedimentos para abertura de um negócio.', 'ÉTICA, NORMAS E POSTURA PROFISSIONAL', 2, 'Software Engineering Code of Ethics and Professional Practice, Version 5.2. Disponível em ABNT NBR ISO/IEC 12207:2009; O Segredo de Luísa, Fernando Dolabela, Cultura Editora Associados, 2002.', 'Padrões internacionais pertinentes à Engenharia de Software ().  - Tecnologia da Informação — Legislação Brasileira, Ministério da Ciência e Tecnologia, Secretaria de Política de Informática, 6a. Edição, 2008.', 'Elucidar ética, a importância para a sociedade e auxiliar o estudante a desenvolver um nível de consciência da sua própria postura. Este autoconhecimento deve ser suficiente para ativar agentes motivadores de mudanças. A disciplina deve estimular a habilidade empreendedora e fornecer noções de desafios de um negócio. Adicionalmente, a disciplina deve apresentar normas relevantes para o engenheiro de software com o propósito de esclarecer a função e o contexto onde podem ser empregadas (a aplicação será assunto de outras disciplinas no curso). Estabelecer a nomenclatura empregada pela área, conforme a norma internacional IEEE Std 12207-2008 é um dos principais objetivos da disciplina. À semelhança das normas, espera-se que o estudante adquira um nível de consciência acerca do que elas tratam.\r'),
(48, 32, 32, 'Conceituação e esclarecimento acerca de experimento controlado, estudos de caso e surveys. Processo de desenvolvimento de um projeto de pesquisa (inclui atividades, formulação de questões, construção de teoria e análise qualitativa/quantitativa de dados). Investigação de experimentos científicos em Engenharia de Software. Prática acompanhada de pequeno experimento em Engenharia de Software.', 'EXPERIMENTAÇÃO EM ENGENHARIA DE SOFTWARE', 2, 'Basics of Software Engineering Experimentation, Natalia Juristo e Ana M. Moreno, Springer, 2001', 'Experimentation in Software Engineering: An Introduction, Claes Wohlin et al., Kluwer Academic Publishers, 2000', 'Desenvolver a noção de experimento em Engenharia de Software e adquirir percepção de como pesquisas são conduzidas na área. Ambientar o estudante com as ferramentas necessárias (inclui processos e estatística) para o planejamento, condução e apresentação de relatórios de investigações empíricas em Engenharia de Software.\r'),
(49, 32, 32, 'Conceitos e terminologia. Processos de gerência de configuração. Identificação de itens de configuração. Atributos a serem registrados para cada item de configuração. Armazenamento. Controle de mudanças. Relatórios de status. Controle de versões e linhas base ou de referência (baselines). Gerência de configuração segundo o MPS.BR. Papéis em gerência de configuração. Normas (IEEE 828). Princípios de gerência de configuração e relação com atividades de desenvolvimento de software. Gerência de configuração segundo desenvolvimento ágil, técnica de builds frequentes e desenvolvimento iterativo. Gerência de configuração para diferentes tipos de produtos (compostos, multiplataforma, múltiplas variantes, críticos, pequenos, médios e grandes). Gerência de configuração para desenvolvimento de software distribuído geograficamente, múltiplos interessados e desenvolvimento paralelo. Melhoria de gerência de configuração. Considerações práticas acerca de gerência de configuração de software. Ferramentas.', 'GERÊNCIA DE CONFIGURAÇÃO DE SOFTWARE', 2, 'Configuration Management Principles and Practice, The Agile Software Development Series, Anne Mette Jonassen Hass, Pearson Education, 2003 (; Configuration Management Patterns: Effective Teamwork, Practical Integration, Stephen P. Berczuk et al., Addison-Wesley, 2003 (). - The; er: Microsoft’s Software Configuration Management Best Practices, Vincent Maraya, Addison-Wesley, 200.', 'Software Configuration Management FAQ', 'Desenvolver habilidade na elaboração, implementação e prática de planos de gerência de configuração de software.\r');
INSERT INTO `disciplina` (`id`, `carga_horaria_pratica`, `carga_horaria_teorica`, `ementa`, `nome`, `curso_id`, `bibliografia_basica`, `bibliografia_complementar`, `objetivo_geral`) VALUES
(50, 32, 32, 'Conceitos, terminologia e contexto de gerência de projetos. Ciclo de vida de produto e projeto. Interessados (stakeholders). Organização de empresas (funcionais, matriciais e baseadas em projetos). Estratégias para seleção de projetos. Processos de gerência de projetos. Gerência de escopo. Gerência de tempo (definição de atividades, sequenciamento de atividades, estimativa de recursos, estimativa de duração, desenvolvimento de cronograma e controle de cronograma). Gerência de custos (estimativas, orçamento e controle). Gerência de qualidade. Gerência de recursos humanos. Gerência de comunicação. Gerência de riscos. Gerência de aquisições. Gerência de integração (desenvolver carta de projeto, desenvolver escopo preliminar, desenvolver plano de gerência de projeto, dirigir e gerenciar a execução de projetos, monitorar e controlar atividades de projeto, controle de mudanças e fechamento do projeto). Estabelecer relações com o MPS. - BR. Gerência de aquisições deve ser observada da perspectiva do Guia de Aquisições de Software e Serviços Correlatos (MPS.BR). ', 'GERÊNCIA DE PROJETO DE SOFTWARE', 2, 'oject Management: A Systems Approach to Planning, Scheduling, and Controlling, Harold Kerzner, 10th edition, Wiley, 2009 (; t!: Your Guide to Modern, Pragmatic Project Management, Johanna Rothman, Pragmatic Bookshelf, 2007 ().  - Ag; Management with Scrum, Ken Schwaber, Microsoft Press, 2004.', 'Guide to the Project Management Body of Knowledge, 4th edition, 2008, ISBN 978-1933890517 (; Course: A Crash Course in Real-World Project Management, Rita Mulcahy, RMC Publications, 2006 ().  - Ag; ative Development: A Manager’s Guide, Craig Larman, Addison-Wesley, 2003 ().  - Agile Es; Planning, Mike Cohn, Prentice-Hall, 2005 (). - Agile Project M; Creating Innovative Products, Jim Highsmith, Addison-Wesley, 2009 (). - Software Estimation: ; g the Black Art, Steve McConnell, Microsoft Press, 2006 (). - The Mythical Man-Month: Ess; ware Engineering, Frederick P. Brooks, 2nd edition, Addison-Wesley, 1995', 'Desenvolver no estudante uma clara percepção das diferenças entre trabalhar em um projeto e gerenciar um projeto. Particularmente em relação à gerência, o estudante deve ser capaz de planejar, iniciar, planejar, executar, monitorar e concluir a gerência de pequenos projetos de software.\r'),
(51, 32, 32, 'Rever, exempliﬁcar o emprego da Engenharia de Software em abrangência e profundidade. Integrar todo o conhecimento das disciplinas do curso de tal forma a permitir a compreensão, a relação entre elas, a importância, os produtos e atividades pertinentes a cada uma delas.', 'INTEGRAÇÃO I', 2, '', '', 'Fornecer e elucidar uma visão coesa e integrada da Engenharia de Software de tal forma que o estudante desenvolva uma sólida percepção de como fazer uso do conhecimento desta área em toda a sua extensão.\r'),
(52, 16, 48, 'Definição de integração de aplicações. Desafios de integração. Abordagens de integração (transferência de arquivos, bases de dados compartilhadas, chamada de procedimento remoto e troca de mensagens). Padrões para integração de aplicações. ', 'INTEGRAÇÃO DE APLICAÇÕES', 2, 'terprise Integration Patterns: Designing, Building, and Deploying Messaging Solutions, Gregor Hohpe and Bobby Woolf, Addison-Wesley, 2003 (;g Patterns: Foundations for Enterprise, Internet and Realtime Distributed Object Middleware, Markus Voelter et al., Wiley, 2004 (). - Pat;ted Software Architecture Volume 2: Patterns for Concurrent and Networked Objects, Douglas Schmidt et al., Wiley, 200.', 'Pattern-Oriented Software Architecture Volume 4: Pattern Language for Distributed Computing, Frank Buschmann et al., Wiley, 2007', 'Fornecer uma clara noção de integração de aplicações e das abordagens sugeridas para tratar os desafios bem como habilitar o estudante a desenvolver software para integrar aplicações.\r'),
(53, 32, 32, 'Princípios de projeto de interfaces homem-computador. Modos de uso e navegação. Projeto visual (cores, ícones, fontes e outros). Tempo de resposta e retro-alimentação. Elementos de interação (menus, formulários, manipulação direta e outros). Localização e internacionalização. Métodos de projeto de interação. Modelos conceituais e metáforas. Voz, linguagem natural, sons, páginas web. Dispositivos de interação. Heurísticas de avaliação de interfaces. Abordagens para testes realizados com apoio de usuários. Técnicas de testes para páginas web. Visão geral de ferramentas de desenvolvimento de interfaces homem-computador.', 'INTERAÇÃO HOMEM-COMPUTADOR', 2, 'Designing Interfaces: Patterns for Effective Interaction Design, Jenifer Tidwell, O’Reilly, 2005 ', '', 'Desenvolver a percepção da importância de um projeto de interação adequado e a compreensão necessária acerca de processo, projeto e avaliação de usabilidade de software.\r'),
(54, 32, 32, 'O conteúdo deve ser abordado mais em abrangência do que em profundidade. É imprescindível estabelecer uma clara relação entre as várias partes da ementa e apresentar a área como um todo coeso. Não contempla atividades que exercitam o conhecimento contido na ementa (emprego prático). É desejável que o docente apresente artefatos de projetos reais que ilustrem a teoria. Por artefatos reais entenda planos (por exemplo, plano de gerência de configuração), documentos (por exemplo, definição da arquitetura de software), código e outros oriundos de desenvolvimentos reais (sempre que possível). ', 'INTRODUÇÃO À ENGENHARIA DE SOFTWARE', 2, 'Engenharia de Software, Ian Sommerville, 9a. edição, Pearson, 2011; Software Engineering: A Practitioner’s Approach, Roger S. Pressman, 7th international edition, McGraw-Hill, 2009; ', 'Engenharia de Software, Kechi Hirama, Ed. Campus, 2011; The Algorithm Design Manual, Steven S. Skiena, 2nd edition, Spri - nger, 2008; The Art of Programming, N. Knuth, Ed. McGraw, 2002.', 'Despertar o interesse e adquirir visão abrangente acerca da Engenharia de Software e da Programação. \r'),
(55, 32, 32, 'Lógica de programação. Constantes. Tipos de dados primitivos. Variáveis. Atribuição. Expressões aritméticas e lógicas. Estruturas de decisão. Estruturas de controle. Estruturas de dados homogêneas e heterogêneas. Vetores (arrays) e matrizes. Funções. Recursão. Desenvolvimento de algoritmos. Transcrição de algoritmos para uma linguagem de programação. Domínio de uma linguagem de programação: sintaxe e semântica. Estilos de codificação. Ambiente de desenvolvimento. Desenvolvimento de pequenos programas.', 'INTRODUÇÃO À PROGRAMAÇÃO', 2, 'Java™ Como Programar, H. M. Deitel e P. M. Deitel, 6a. edição, Pearson Prentice Hall, 2005; The Algorithm Design Manual, Steven S. Skiena, 2nd edition, Springer, 2008 ().; Introduction to Programming in Java: An Interdiciplinary Approach, Robert Sedgewick and Kevin Wayne, Addison-Wesley, 2007. ', 'Head First Java, Kathy Sierra and Bert Bates, O’Reilly Media, 2005;. Head First Programmin - g, Vern Ceder, O’Reilly, 2008; Dictionary of Algorithms and Data Structures. URL: ', 'Habilitar o estudante a definir algoritmos para pequenos problemas e implementá-los em uma linguagem de programação; Engenharia de Software- Teoria e Prática, James F. Peters and Witold Pedrycz, Ed. Campus, 2001. \r'),
(56, 64, 0, 'Estudar, investigar, analisar e discutir projetos de software existentes e “vencedores”.', 'LEITURA DE SOFTWARE', 2, 'Code Reading: The Open Source Perspective, Diomidis Spinellis, Addison-Wesley, 2003', '', 'Familiarizar o estudante com o que é “bom” por meio do estudo de projetos de desenvolvimento de software (ou seja, com o correspondente código, documentação, dados, ferramentas, processos utilizados e outros elementos empregados no desenvolvimento). A ambientação deve auxiliar a reconhecer o que é “bom” e, adicionalmente, a exercitar o que possivelmente mais fará em sua vida profissional: ler código (talvez mais que escrever).\r'),
(57, 32, 32, 'Visão geral de linguagens de programação e de paradigmas de programação. Tradução de linguagens. Máquinas virtuais. Tipos. Estruturas de decisão e controle. Funções. Programação orientada a objetos.', 'LINGUAGENS DE PROGRAMAÇÃO', 2, 'Concepts of Programming Languages, Robert W. Sebesta, Addison-Wesley, 9th edition, 2009 (;n oncetps in Programming Languages, Franklyn A. Turbak and David K. Gifford, MIT Press, 2008. - Essent; of Programming Languages, Daniel P. Friedman and Mitchell Wand, The MIT Press, 3rd edition, 2000.', 'The Definitive ANTLR Reference: Building Domain-Specific Languages, Terence Parr, Pragmatic Bookshelf, 2007.', 'A principal interface de um computador empregada por quem desenvolve software é a linguagem de programação. O estudante deve reconhecer a relação entre linguagem e problema (nem toda linguagem se aplica a todo e qualquer problema). O estudante deve compreender as construções de linguagens de programação, os estilos empregados e mecanismos de tradução e execução adotados por linguagens de programação de tal forma que o habilite a “aprender” novas linguagens de programação.\r'),
(58, 32, 32, 'Lógica proposicional. Proposições e conectivos. Operações lógicas sobre proposições. Construção de tabelas-verdade. Tautologias, contradições e contingências. Implicação lógica. Equivalência lógica. Álgebra das proposições. Métodos para determinação da validade de fórmulas da lógica proposicional. Demonstração condicional e demonstração indireta. Lógica de predicados.', 'LÓGICA', 2, 'Iniciação à Lógica Matemática, Edgard de Alencar Filho, Editora Nobel, 2002; Lógica para Ciência da Computação, João Souza, Editora Campus, 2002. Introduction to Mathematical Logic, E. Mendelson, Academic Press, 2000.', 'Lógica e Álgebra de Boole, Jacob Daghlian, 4a. edição, Atlas, 1995.', 'Desenvolver o raciocínio analítico e a habilidade de elaborar sentenças logicamente precisas.\r'),
(59, 32, 32, 'Conceitos e terminologia. Categorias (tipos) de manutenção. Questões técnicas e gerenciais de manutenção. Estimativa de custo de manutenção. Métricas/medidas para manutenção. Processos e atividades de manutenção. Compreensão de programas. Reengenharia. Engenharia reversa. Norma IEEE Std 14764-2006. Refatoração. Transformação de programas.', 'MANUTENÇÃO DE SOFTWARE', 2, 'rking Effectively with Legacy Code, Michael Feathers, Prentice-Hall, 2004 (); Maintenance Management: Evaluation and Continuous Improvement, Alain April e Alain Abran, Wiley, 2008 ().  - R;ng Patterns, Joshua Kerievsky, Addison-Wesley, 200.', 'Refactoring: Improving the Design of Existing Code, Martin Fowler et al, Addison-Wesley, 1999', 'Oferecer uma abrangente visão de manutenção (evolução) de software e de questões correlatas, bem como estas questões são relacionadas a outras do ciclo de vida de um software.\r'),
(60, 0, 64, 'Técnicas de demonstração. Conjuntos. Combinatoria. Relações, relações de equivalência. Ordens parciais e totais. Funções. Indução matemática. Estruturas algébricas (princípios de números naturais, inteiros e racionais).', 'MATEMÁTICA DISCRETA', 2, 'Fundamentos Matemáticos para a Ciência da Computação, Judith L. Gersting, 5a. edição, Editora LTC, 2004', 'Matemática Discreta, E. Scheinerman, Thomson Pioneira, 2003. - Discrete Mathematics and its Applications, Kenneth Rose, McGraw-Hill, 6th edition, 2006 (aqui). - Student’s Solutions Guide to accompany Discrete Mathematics and Its Applications, Kenneth H. - Rosen, McGraw-Hill, 6th edition, 2006', '\r'),
(61, 64, 0, 'Técnicas de demonstração. Conjuntos. Combinatória. Relações, relações de equivalência. Ordens parciais e totais. Funções. Indução matemática. Estruturas algébricas (princípios de números naturais, inteiros e racionais).', 'MATEMÁTICA DISCRETA', 2, 'Fundamentos Matemáticos para a Ciência da Computação, Judith L. Gersting, 5a. edição, Editora LTC, 2004; Matemática Discreta, E. Scheinerman, Thomson Pioneira, 2003; Discrete Mathematics and its Applications, Kenneth Rose, McGraw-Hill, 6th edition, 2006.', 'Student’s Solutions Guide to accompany Discrete Mathematics and Its Applications, Kenneth H. Rosen, McGraw-Hill, 6th edition, 2006', 'Desenvolvimento do raciocínio abstrato e domínio de técnicas úteis à modelagem e construção de programas.\r'),
(62, 48, 16, 'Paradigma orientado a objetos (abstração, encapsulamento, classes, métodos, objetos, herança, polimorfismo, delegação e outros). Modelagem orientada a objetos usando UML. Noções de princípios de projeto orientado a objetos. Implementação de modelos. Método de desenvolvimento de software orientado a objetos. Visão detalhada de método ágil de desenvolvimento de software. Desenvolvimento de pequenas aplicações modeladas e implementadas de forma orientada a objetos seguindo um método ágil e o emprego de orientação a objetos.', 'MÉTODO DE DESENVOLVIMENTO DE SOFTWARE', 2, 'Head First Software Development, Dan Pilone and Russ Miles, O’Reilly, 2008', 'Head First Object-Oriented Analysis & Design, Brett D. McLaughlin et al., O’Reilly, 2006. - Object-Oriented Analysis and Design with Applications, Grady Booch et al., 3rd edition, Addison-Wesley, 2007. - OOP Demystified, James Keogh and Mario Giannini, McGraw-Hill, 2004. - Object Thinking, David West, Microsoft Press, 2004. - Object-Oriented Thought Process, Matt Weisfeld, Sams, 2nd edition, 2003. - UML Distilled: A Brief Guide to the Standard Object Modeling Language, Martin Fowler, 3rd edition, Addison-Wesley, 2003. - UML Demystified, Paul Kimmel, McGraw-Hill, 2005.', 'Fornecer visão horizontal e ampla de processos técnicos de desenvolvimento de software orientado a objetos de forma prática, com ênfase na construção de software. Esta visão deve ser suficiente para servir de base para disciplinas posteriores acerca de atividades de desenvolvimento de software.\r'),
(63, 32, 32, 'Métodos heurísticos, formais e de construção de protótipos. Ferramentas para auxiliar na produção de requisitos, projeto, construção, testes e manutenção. Ferramentas de gerência de configuração, gerência de projeto, processo de software, qualidade e outras.', 'MÉTODOS E FERRAMENTAS DE SOFTWARE', 2, 'Software Engineering Methods and Technologies, Alfonso Fuggetta e Laura Stardini, publicado em - Software Engineering Volume 2: The Supporting Process, R. H. Thayer e M. Dorfman editors, 3rd - edition, 2005. Disponível em http://alfonsofuggetta.org.', 'Guide to the Software Engineering Body of Knowledge, IEEE Computer Society, 2004. Disponível em http://swebok.org.', 'Habilitar o estudante a reconhecer métodos e ferramentas relevantes para a produção de software.\r'),
(66, 32, 32, 'Conceitos e terminologia. Infraestrutura de processos (pessoas, ferramentas, treinamentos e outros). Modelagem e especificação de processos de software. Medição e análise de processos de software. Melhoria de processos de software (individual e equipe). Análise e controle de qualidade (prevenção de defeitos, revisão de processos, métricas de qualidade, análise de causa e outros). Níveis de definição de processos. Modelos de ciclo de vida (ágil, processos “pesados”, cascata, espiral, modelo V e outros). Modelos de processos e padrões (IEEE, ISO e outros). Modelo, definição, medida, análise e melhoria tanto de processo de software individual quanto de equipe. Personalização de processo. Requisitos para processos de software (ISO/IEEE 12207). Detalhada apresentação do MSP.BR (guias). Implementação do MPS.BR. Noções de governância de TI.', 'PROCESSO DE SOFTWARE', 2, 'Software Engineering: The Supporting Processes, Richard H. Thayer and Merlin Dorfman, Wiley-IEEE - Computer Society Press, 3rd edition, 2005. - Software Engineering: The Development process, Richard H. Thayer and Mark J. Christensen, Wiley- - IEEE Computer Society Press, 3rd edition, 2005.', 'Agile Software Development, Alistair Cockburn, Addison-Wesley, 2001. - Agile Software Development with SCRUM, Ken Schwaber and Mike Beedle, Prentice-Hall, 2001. - Guia de Aquisição de Software e Serviços Correlatos, Softex, 2007. - Guia Geral do MPS.BR, Softex, 2007. - Guias de Implementação do MPS.BR, Softex, 2007.', 'Desenvolver sólida percepção da importância, impacto, constituição, definição e melhoria de processos.\r'),
(67, 32, 32, 'Definição de projeto. Questões fundamentais (persistência dos dados, exceções e outras). Contexto de projeto em vários modelos de desenvolvimento de software (ciclos de vida). Princípios de projeto (encapsulamento de informações, coesão e acoplamento). Interação entre projeto e requisitos. Atributos qualitativos em um projeto (confiabilidade, usabilidade, manutenibilidade, testabilidade, desempenho, segurança, tolerância a falhas e outros). Compromissos (custo-benefício). Relação entre arquitetura de software e projeto de software. Projeto orientado a objetos. Projeto funcional. Noção de projeto baseado em estrutura de dados e projeto orientado a aspectos. Projeto orientado por responsabilidade. Projeto por contratos. Métodos de projeto de software. Padrões de projeto. Reutilização. Projeto de componentes. Projeto de interfaces entre componentes e sistemas. Notações de projeto. Ferramentas de suporte a projeto (análise estática, ava - liação dinâmica e outras). Medidas de atributos de projeto (acoplamento, coesão e outras). Métricas de projeto (principais métricas, interpretação).', 'PROJETO DETALHADO DE SOFTWARE', 2, 'Software Design, David Budgen, 2nd ediciton, Addison-Wesley, 2003', 'Head First Design Patterns, Elisabeth Freeman et al., O’Reilly Media, 2004. - Release It!: Design and Deploy Production-Ready Software, Michael Nygard, Pragmatic Bookshelf, 2007. - Pattern Oriented Software Architecture Volume 1: A System of Patterns, Frank Buschmann et al., Wiley, 1996. - Pattern Languages of Program Design 5, Dragos Manolescu et al., Addison-Wesley, 2006. - Domain-Driven Design: Tackling Complexity in the Heart of Software, Eric Evans, Addison-Wesley, 2003.', 'Desenvolver sólida percepção acerca de implicações de decisões de projeto e de estratégias para obtê-las, bem como habilidade na confecção de modelos de projeto para problemas reais.\r'),
(68, 32, 32, 'Definições e terminologia de qualidade de software. Custos e impactos de baixa qualidade. Custo de um modelo de qualidade. Terminologia para características de qualidade de software (ISO 9126-1). Papel de pessoas, processos, métodos, ferramentas e tecnologias em qualidade. Padrões de qualidade (ISO 9001, ISO 9003-04, IEEE Std 1028-2008, IEEE Std 1465-2004, IEEE Std 12207-2008, ITIL). Revisões, auditoria e inspeções. Modelos e métricas de qualidade de software. Aspectos relacionados a qualidade de modelos de processos de software. Visão geral do CMMI. MPS.BR. Planejamento de qualidade. Garantia da qualidade. Análise de causa e prevenção de defeitos. Avaliação de atributos de qualidade. Métricas e medidas de qualidade de software. Desenvolver planos de qualidade de software em conformidade com o padrão IEEE Std 730-2002.', 'QUALIDADE DE SOFTWARE', 2, 'Metrics and Models in Software Quality Engineering, Stephen H. Kan, 2nd Edition, Addison-Wesley, 2002 (); - Quality Assurance: From Theory to Implementation, Daniel Galin, Addison-Wesley, 2004 (). - Qualidade de Software, José Carlos Maldonado, Ana Regina C. Rocha e Kival C. Weber, Ed. Makron, 2001. ', 'Qualidade de Software, André Kosciansk e Michel dos Santos Soares, Ed. Novatec, 2007.', 'Desenvolver percepção clara de qualidade aplicada a produto, projeto ou processo de software. Quanto a produto, não apenas o produto final, mas também artefatos intermediários (entregáveis ou não). Cabe a esta disciplina apresentar uma visão integral de qualidade, visto que disciplinas abordam o tema “isoladamente”. O estudante deve compreender conceitos de qualidade e reconhecer que requisitos definem características de qualidade de um software e influenciam critérios para a validação destas características.\r'),
(69, 32, 32, 'Modelos de referência em redes: camadas, protocolos e serviços. Camada de rede. Camada de transporte. Camada de aplicação. Programação com sockets. Caracterização de sistemas distribuídos. Arquitetura de Aplicações distribuídas. Sistemas de objetos distribuídos. Serviços de nomes. Comunicação assíncrona. Arquiteturas orientadas a serviços.', 'REDES E SISTEMAS DISTRIBUIDOS', 2, 'Kurose, James F. and Ross, Keith W., Computer Networking: A Top-Down Approach, 5th edition, Addison-Wesley, 2009; Coulouris, G. F. et al., Distributed Systems: Concepts and Design, 4th edition, Addison-Wesley, 2005; Tanenbaum, Andrew S., Computer Networks, 4th edition, Prentice-Hall, 2002. ', 'Stallings, William, Data and Computer Communications, 8th edition, Prentice-Hall, 2006; Tanenbaum, A. S. and M. van Steen, Distributed Systems: Principles and Paradigms, 2nd edition, Prentice-Hall, 2006; Dantas, Mário, Redes de Comunicação e Computadores: Abordagem Quantitati - va, Visual Books, 2009.', 'Ambientar o estudante com questões pertinentes ao de desenvolvimento de sistemas distribuídos e com a prática correspondente.\r'),
(70, 32, 32, 'Definição de requisitos (produto, projeto, processo). Processo de requisitos. Níveis de requisitos (necessidades, objetivos, requisitos dos usuários, requisitos de sistema, requisitos de software. Características de requisitos (testáveis, verificáveis e outras). Princípios de modelagem como decomposição e abstração. Pré e pós condições. Invariantes. Visão geral de modelos matemáticos e linguagens formais de especificação. Interpretação de modelos (sintaxe e semântica). Modelagem de: informações; fluxo de dados; comportamento; estrutura (arquitetura); domínio; processos de negócios e funcional. Padrões de análise. Fundamentos (completitude, consistência, robustez, análise estática, simulação, verificação de modelos, segurança, safety, usabilidade, desempenho, análise de causa/efeito, priorização, análise de impacto e rastreabilidade). Gerência de requisitos. Interação entre requisitos e arquitetura. Fontes e técnicas - de elicitação. Documentação de requisitos (normas, tipos, audiência, estrutura, qualidade). Especificação de requisitos. Revisões e inspeções.', 'REQUISITO DE SOFTWARE', 2, 'Software Requirements, Karl E. Wiegers, Microsoft Press, 2nd edition, 2003', 'More About Software Requirements: Thorny Issues and Practical Advice, Karl E. Wiegers, Microsoft Press, 2006. - Software Requirements Patterns, Stephen Withall, Microsoft Press, 2007. - Writing Effective Use Cases, Alistair Cockburn, Addison-Wesley, 2000. - User Stories Applied: For Agile Software Development, Mike Cohn, Addison-Wesley, 2004', 'Habilitar o estudante a elaborar e manter especificações de requisitos de software em conformidade com necessidades de diferentes tipos de projetos e restrições.\r'),
(71, 32, 32, 'Ameaças. Segurança como atributo qualitativo de projeto de software. Autenticação. Autorização. Integridade. Confidencialidade. Criptografia (chaves simétricas e assimétricas). Infraestrutura de chaves públicas brasileiras (ICP-Brasil). Certificados digitais. Assinaturas digitais. Fraquezas decorrentes de problemas na implementação e/ou arquitetura de um software. Desenvolvimento de software seguro. Noções de auditoria de sistemas. Norma NBR 27002.', 'SEGURANÇA', 2, 'Foundations of Security: What Every Programmer Needs to Know, Neil Daswani et al., Apress, 2007', 'Software Security: Building Security In, Gary McGraw, Addison-Wesley, 2006 - 19 Deadly Sins of Software Security, Michael Howard et al., McGraw-Hill, 2005 - Beginning Cryptography with Java, David Hook, Wrox, 2005', 'Desenvolver percepção clara de política de segurança e de problemas de projeto e implementação pertinentes a software seguro, além de estratégias para evitá-los, suficiente para o desenvolvimento de software no qual se pode confiar.\r'),
(72, 32, 32, 'Conceitos de Hardware e Software. Tipos de sistemas operacionais. Sistemas multiprogramáveis. Estrutura/organização de sistemas operacionais. Processo. Comunicação entre processos. Gerência de processos. Gerência de memória. Gerência de dispositivos. Sistemas de arquivos. Segurança. Estudos de casos de sistemas operacionais atuais.', 'SISTEMA OPERACIONAL', 2, 'anembaum, Andrew S., Sistemas Operacionais Modernos, Prentice-Hall Brasil, 3a. Edição, 2010; Silberschatz, A. et al., Operating Systems Concepts with Java, 8th edition, Wiley, 2009; Deitel, H. M. e Choffnes, Sistemas Operacionais, Prentice-Hall, 2005.', 'rissimi, Alexandre, Toscani, Simão e Oliveira, Romulo, S., Sistemas Operacionais, Ed. Bookman, 2010; ', 'Oferecer sólida noção de funções, serviços e compromissos de um sistema operacional, bem como familiaridade com opções adotadas por sistemas operacionais mais comuns.\r'),
(73, 32, 32, 'Métodos de programação avançados para programação em dispositivos móveis: otimização de código, comunicação de redes com economia de dados e de tempo. Integração com sistemas back-end: criação de APIs, consumo de dados de redes sociais, integração com sistemas REST. Automação com dispositivos móveis: entrada e saída de dados, leitura de pacotes de redes sem-fio, open-hardware', 'TÓPICOS DE ENGENHARIA DE SOFTWARE', 2, '--', '--', 'Manter o estudante ciente de assuntos relevantes em Engenharia de Software não cobertos no curso ou fortalecer a formação do perfil do egresso em assunto de uma ou mais disciplinas. Por exemplo, sistemas críticos, tolerância a falhas, especificação formal, sistemas de tempo real e programação funcional, dentre outros, são assuntos candidatos (não cobertos por disciplinas). Adicionalmente, pode-se trabalhar conteúdo coberto por uma ou mais disciplinas. A decisão cabe à coordenação do curso, assistida - tanto pelos estudantes quanto professores.\r'),
(74, 32, 32, 'Objetivos e restrições de V&V (Verificação e Validação). Planejamento de V&V. Documentação de estratégias de V&V, testes e outros artefatos. Medidas e Métricas. Análise estática de código. Atividades de V&V ao longo do ciclo de vida de um produto. Revisão de software. Testes de unidade. Análise de cobertura. Técnicas de teste funcional (caixa preta). Testes de integração. Desenvolvimento de casos de teste baseados em casos de uso e estórias de usuários. Testes de sistema. Testes de aceitação. Testes de atributos de qualidade. Testes de regressão. Ferramentas de teste (combinação com ferramentas de integração contínua). Análise de relatórios de falha. Técnicas para isolamento e falhas (depuração). Análise de defeitos. Acompanhamento de problemas (tracking). IEEE Std 1012-2004.', 'VERIFICAÇÃO E VALIDAÇÃO DE SOFTWARE', 2, 'nit Test Patterns: Refactoring Test Code, Gerard Meszaros, Addison-Wesley, 2007 ().  - Software Verification and Validation for Practitioners and Managers, Steven R. Rakitin, Artech House, 2nd edition, 2001 (). Tes; lise de Software: Processos, princípios e técnicas, Mauro Pezzè e Michal Young, Bookman, 2008 (). - JUnit R; Pctical Methods for Programmer Testing, J. B. Rainsberger, Manning, 2004', 'Software Verification and Validation, Marcus F. Fisher, Ed. Springer Verlay NY, 2006; Software Verification and Analysis, Willian Stanley, Ed. Springer Verlay NY, 2009', 'Oferecer experiências sólidas em Validação e Verificação de Software. Técnicas de Teste de Software, bem como preparar e planejar projetos de testes e validação de software e revisão de software.\r'),
(75, 32, 32, 'Escopo da Engenharia Econômica. Fornecimento, demanda e produção. Lucro produzido por capital (interest). Análise custo-benefício. Análise breakeven. Retorno de investimento. Avaliação de alternativas. Economia aplicada ao desenvolvimento de software.', 'ENGENHARIA ECONÔMICA PRA SOFTWARE', 2, 'Engineering Economy, Willian G. Sullivan et al., 14th edition, Prentice-Hall, 2008', 'Não existe sugestão realizada no PPC', 'Fornecer noções de engenharia econômica e outras questões econômicas aplicadas a software.\r'),
(76, 32, 32, 'Modelos de negócio para software (aluguel, serviço, open source). Leis, normas, impostos e legislação brasileira para o mercado local e para a exportação de software. Programas de incentivo à exportação de software. Fontes de recursos nacionais e internacionais para a produção de software. Características e exigências do mercado interno e externo. Identiﬁcação de oportunidades de inovação em software. Planos de negócio de software para o mercado nacional e global.', 'MERCADO DE SOFTWARE', 2, 'NSA', 'NSA', 'Capacitar o estudante a “ler” o contexto corrente de software do mercado interno e global, suas tendências, características, exigências e noção do esforço necessário para satisfazê-lo.\r'),
(77, 32, 32, 'Rever, exempliﬁcar o emprego da Engenharia de Software em abrangência e profundidade. Integrar todo o conhecimento das disciplinas do curso de tal forma a permitir a compreensão, a relação entre elas, a importância, os produtos e atividades pertinentes a cada uma delas.', 'INTEGRAÇÃO II', 2, 'NSA', 'NSA', 'Fornecer e elucidar uma visão coesa e integrada da Engenharia de Software de tal forma que o estudante desenvolva uma sólida percepção de como fazer uso do conhecimento desta área em toda a sua extensão.\r'),
(78, 32, 32, 'Não atribuida', 'PRÁTICA EM ENGENHARIA DE SOFTWARE', 2, 'nao foi atribuida no PPC', 'nao foi atribuida no PPC', 'Desenvolver habilidades do estudante em desenvolvimento de software em ambiente tão próximo do real quanto possível, no contexto de uma Fábrica de Software.\r'),
(79, 32, 32, 'Observação, análise, investigação e prática de técnicas e soluções avançadas de desenvolvimento de software.', 'TÉCNICAS AVANÇADAS EM CONSTRUÇÃO DE SOFTWARE', 2, '1 . Beautiful Code: Leading Programmers Explain How They Think, Andy Oram e Greg Wilson, O’Reilly Media, 2007.', '1 . Why Programs Fail: A Guide to Systematic Debugging, Andreas Zeller, Morgan Kauffman, 2005  - 2 . • Programming Challenges, Steve S. Skiena e Miguel Revilla, Springer, 2003', 'Solidificar a habilidade de programação do estudante por meio de técnicas avançadas de programação. \r'),
(91, 16, 48, 'Introduzir os conceitos fundamentais de sistemas distribuídos, a caracterização de sistemas de computação distribuída, aplicações distribuídas (características e aspectos de projeto), objetivos básicos de sistemas distribuídos (transparência, abertura, escalabilidade etc). Estudar e dominar os princípios e aplicações dos principais modelos de sistemas distribuídos: sistemas cliente/servidor e sistemas multi-camadas; sistemas peer-to-peer. Compreender a teoria e prática de objetos distribuídos: interface x implementação; objetos remotos; chamadas de métodos remotos (RMI). Estudar os princípios e uso dos principais serviços de sistemas distribuídos: serviços de nomes; compartilhamento de documentos / recursos distribuídos (ex.: WWW e sistemas de trabalho cooperativo). Princípio e implementação de SOA – Arquiteturas Orientadas a Serviço; Utilização de web services.', 'APLICAÇÕES DISTRIBUÍDAS', 4, 'COULOURIS, G.F., J. Dollimore and T. Kindberg. Distributed Systems: Concepts and Design. 4th edition. Addison Wesley, 2005. - KUROSE, J.F. & ROSS, K.W., Redes de Computadores e a Internet: Uma abordagem top-down, 3a. Edição, Addison Wesley/Pearson, 2006. - TANENBAUM, A.S. and STEEN, M. van . Distributed Systems: Principles and Paradigms. Prentice Hall, 2ª Edição, 2006.', 'BURKE, Bill and RUBINGER,Andrew Lee. Enterprise Java Beans 3.1, September, 2010.', '\r'),
(92, 16, 48, 'Visão geral dos computadores modernos. Evolução da arquitetura dos computadores. Sistemas de numeração e aritmética binária. Memória e representação da dados e instruções. Processador, ciclo de instrução, formatos, endereçamento, e programação em linguagem de montagem. Dispositivos de entrada e saída. Sistemas de interconexão (barramentos). Interfaceamento e técnicas de entrada e saída. Hierarquia de memória. Paralelismo ao nível de instrução. Arquiteturas paralelas.', 'ARQUITETURA DE COMPUTADORES', 4, 'TA N E N B A U M , A n d r e w, Organização Estruturada de Computadores, Editora LTC, 2006. - STALLINGS, W. Arquitetura e Organização de Computadores, Tradução da 5a Edição, Prentice-Hall, 2002. - PATTERSON, D.A.; HENNESSY, J.L. Projeto e Organização de Computadores:A Interface Hardware / Software, Tradução da 3a Edição, Campus, 2005.', 'HENNESSY, J.L. and PATTERSON, D.A. Arquitetura de Computadores: Uma Abordagem Quantitativa, Tradução da 3a. Edição, 1996, Morgan Kaufmann.  - WEBER, R.F., Fundamentos de Arquiteturas de Computadores, 2a Edição, Editora Sagra-Luzzatto, 2001.', '\r'),
(93, 0, 64, 'Auditoria de sistemas: conceituação, planejamento, controle interno, ponto de controle, produtos gerados, função do auditor e técnicas de auditoria de sistemas. Gerência de projetos: conceituação de projeto, modelo pmi, gerência da qualidade, proposta, ciclo de vida, estrutura organizacional, atuação do gerente de projeto, gerência de escopo, tempo e custo, ferramentas de gerencia de projeto e estimativas.', 'AUDITORIA DE SISTEMAS', 4, 'GIL, Antônio de Loureiro. Auditoria de Computadores, Ed. Atlas - CARUSO & STEFFEN. Segurança em Informática e de Informações, Ed. Senac - DUNCAN, William R. – A Guide to the Project Management Body of Knowledge, PMI-Project Mamagement Institute.', 'DIAS, Cláudia. Segurança e Auditoria da Tecnologia da Informação, Ed Axcel Books  - FONTES, Joaquim Rubens – Manual de Auditoria de Sistemas, Ed. Ciência Moderna - BERNSTEIN, Terry & colaboradores. Segurança na Internet, Ed. Campus - VARGAS, Ricardo Viana – Gerenciamento de Projetos com o MS-Project, Ed. Best Seller - M. da S., Portilho, C. - Projetando com Qualidade a Tecnologia em Sistemas de Informação. Rio de Janeiro. LTC, 1995. - BIO, Sérgio Rodrigues. – Sistemas de Informação: um enfoque gerencial. São Paulo. Ed. Atlas, 1996. - CAUTELA, Alciney Lourenço; POLLONI, Enrico Giulio Franco. - Sistemas de Informação. Prado, Darci – Pert/Cpm – Série Gerência de Projetos, 2a edição, Ed. DG. - Manual de Auditoria do FED-Federal Reserve (Banco Central Americano) - Manual de Auditoria de Sistemas do TCU-Tribunal de Contas da União  - Manual de Auditoria do Estado do Rio de Janeiro', '\r'),
(94, 32, 32, 'Conceitos de arquitetura de Software, estilo, estrutura, requisitos, análise de arquitetura. Projeto Arquitetural, Avaliação Arquitetura. Modelo de arquitetura em camadas – MVC (model – Viewcontroller), MDA (model drive architecture). Ferramentas.', 'ARQUITETURA DE SOFTWARE', 4, 'FOWLER, Martin. Patterns of Enterprise Application Architecture, Addison-Wesley, 2002 - BASS, Len et al. Software Architecture in Practice, Addison-Wesley, 2003', '', '\r'),
(95, 32, 32, 'Sistemas de bancos de dados. Projeto de banco de dados. Modelo entidade-relacionamento. Modelo relacional. Álgebra relacional. Normalização. SQL.', 'BANCO DE DADOS I', 4, '• BASS, Len et al. Software Architecture in Practice, Addison-Wesley, 2003', 'K O RT H , H e n r y F. , S I L B E R S C H AT Z , A ; A b r a h a m ; - SUDARSHAN,S. Sistema de Banco de Dados; Editora Campus, 5a Edição, 2006. - DATE, C.J. Banco de Dados: Tópicos Avançados. Rio de Janeiro: editora Campus, 1988. - ELMASRI, R.; Navathe, S., Fundamentals of Database Systems. Addison-Wesley, 1994. - MACHADO, F.N.R.; Abreu, M. Projeto de Banco de Dados: uma Visão Prática. São Paulo: editora Érica, 1995. - SETZER, V.W. Projeto Lógico e Projeto Físico de Bancos de Dados. Belo Horizonte: V Escola de Computação, 1986.', '\r'),
(96, 16, 48, 'Programação de Banco de Dados. Funções, gatilhos e procedimentos armazenados. Organização de Dados e Estruturas de Armazenamento. Transações. Controle de concorrência. Recuperação após falhas. Segurança.', 'BANCO DE DADOS II', 4, 'ELMASRI, R. E., NAVATHE, S. B., Sistemas de Banco de Dados, Addison Wesley, 4a edição, 2005. - K O RT H , H e n r y F. , S I L B E R S C H AT Z , A ; A b r a h a m ; - SUDARSHAN,S. Sistema de Banco de Dados; Editora Campus, 5a Edição, 2006. - DATE, C.J. Banco de Dados: Tópicos Avançados. Rio de Janeiro: editora Campus, 1988.', 'MACHADO, F.N.R.; Abreu, M. Projeto de Banco de Dados: uma Visão Prática. São Paulo: editora Érica, 1995.', '\r'),
(97, 0, 64, 'A comunicação nas organizações suas funções e fases; o volume de riqueza da informação nas organizações; as redes e os canais de comunicação nas organizações; comunicação empresarial e o processo de gestão; comunicações internas nas organizações em uma nova abordagem: endomarketing; comunicação e liderança; comunicação interpessoal nas organizações: conhecimento, habilidade e atitude; comunicação verbal e não-verbal; barreiras da comunicação.', 'COMUNICAÇÃO EMPRESARIAL', 4, 'BAHIA, Juarez. Introdução à comunicação empresarial. Rio de Janeiro: Murad, 1995. - BUENO, Wilson da Costa. Comunicação Empresarial: teoria e pesquisa. São Paulo, Editora Manole, 2003. - TORQUATO, Francisco Gaudêncio. Comunicação empresarial, comunicação institucional: conceitos, estratégias, sistemas, estruturas, planejamento e técnicas. São Paulo, Summus, 1986.', 'BORDENAVE, Juan E. Diaz. O que é Comunicação. 27.ed. São Paulo:Brasiliense, 2002. - BRUM, Analisa. Respirando Endomarketing. Porto Alegre: L&PM, 2003. - CAHEN, Roger. Tudo que seus não lhe contaram sobre comunicação empresarial. São Paulo: Best Seller, 1990. - CARNEGIE, Dale. Como falar em público e influenciar pessoas no mundo dos negócios. Rio de Janeiro: Record, 1994. - CESCA, Cleuza G. Gimenes. Comunicação dirigida escrita na empresa. São Paulo: Summus,1995. - CORRADO, Frank M. A força da comunicação. São Paulo, Makron Books, 1994. - CURVELLO, João J. A. Comunicação Interna e Cultura Organizacional. São Paulo:Scortecci, 2002. - GOLD, Miriam. Redação empresarial: escrevendo com sucesso na era da globalização. São Paulo : Makron Books, 1999. - KUNSCH, Margarida Maria Krohling. Planejamento de Relações Públicas na comunicação integrada. São Paulo: Summus, 2003. - NASSAR, Paulo & GOMES, Nelson. A comunicação da pequena empresa. São Paulo: Globo,1997. - PIMENTA, Maria Alzira. Comunicação Empresarial. 3. ed. Campinas: Alínea, 2002. - POLITO, Reinaldo. Como falar corretamente sem inibições. 97. ed. São Paulo: Saraiva, 2001. - VIANA, Francisco. Comunicação empresarial de A a Z: temas úteis para o cotidiano e o planejamento estratégico. São Paulo: Editora CLA, 2004.', '\r'),
(98, 64, 0, 'Definição de construção de software. Pré-requisitos para a construção. Boas práticas para definição dos requisitos. Arquitetura de software e seus componentes. Escolha da linguagem de programação. Convenções de programação. Principais práticas de construção de software. Projeto de software (conceitos, práticas, níveis e abordagens comuns). Formas de acoplamento. Classes e pacotes. Rotinas (métodos). Projeto de software em nível de rotina. Motivos para se criar uma rotina. Bons nomes para uma rotina. Tamanho adequado de uma rotina. Programação defensiva. Problemas gerais no uso de variáveis. Nomes adequados para variáveis. Tipos de dados fundamentais. Estruturas. Ponteiros e referências. Dados globais. Organizando código linear. Sentenças de decisão. Sentenças de iteração. Pesquisas em tabelas. Expressões lógicas. Blocos. Instruções nulas e aninhamentos profundos. Estruturas de controle e complexidade. Qualidade de software. - Construção colaborativa. Testes de desenvolvedor. Depuração. Refatoração. Estratégias e técnicas de otimização de código. Relação entre tamanho do código e construção. Gerenciando a construção. Integração. Ferramentas de programação. Leiaute e estilo.', 'CONSTRUÇÃO DE SOFTWARE', 4, 'McCONNELL, Steve. Code Complete: A Practical Handbook of Software Construction, Microsoft Press, 2nd Edition, 2004. - BEZERRA, E. Princípios de Análise e Projeto de Sistemas com UML. 2a ed., Elsevier, 2007. - DAN Pilone and Russ Miles, Head First Software Development, O’Reilly, 2008.', 'Code Complete: Um guia prático para a construção de software, Steve McConnell, Microsoft Press, 2nd Edition, 2004. - GAMA, E.; HELM. R.; JOHNSON, R.; VLISSIDES, J. Padrões de Projeto: soluções reutilizáveis de software orientado a objetos. 2a ed., Bookman, 2000. - GOODRICH, M.; TAMASSIA, R. Estruturas de Dados e Algoritmos em Java. 4a ed., Bookman, 2007.', '\r'),
(99, 32, 32, 'O perfil do empreendedor. Estudo de mecanismos e procedimentos para lançamento de uma empresa no mercado. Inovação tecnológica na geração de novos produtos e negócios. Sistemas de Gerenciamento, técnicas de negociação e legislação específica. Marketing e competitividade. Sistema de Produção. Sistema de Recursos Humanos. Constituição, tributação e legalização de empresa. Avaliação de desempenho empresarial. Análise de Casos. Elaboração de plano de negócios.', 'EMPREENDEDORISMO', 4, 'FARAH, Osvaldo; CAVALCANTI, Marly e MARCONDES, Luciana. Empreendedorismo Estratégico – Criação e Gestão de Pequenas Empresas. Editora Thomson Learning (Pioneira) ISBN-10: 8522106088, 2008. - SALIM, César; HOCHMAN, Nelson; RAMAL, Andrea, e RAMAL, Silvina.Construindo Planos de Negócios. Editora Campus, 2ª. Edição, 2003. - DORNELAS, José Carlos. Empreendorismo: transformando idéias em negócios. 2 ed. Rio de Janeiro: Campus, 2005.', 'BUKOWITZ, Wendi; WILLIAMS, Ruth L. Manual de gestão do conhecimento. Porto Alegre: Bookman Companhia, 2002. - STAL, Eva; SBRAGIA, Roberto; CAMPANARIO, Milton de A.; ANDREASSI, Tales. Inovação. São Paulo: Clio, 2006.', '\r'),
(100, 0, 64, 'Introdução ao Ciclo de Vida do Sistema de Software e ao Processo de Desenvolvimento de Software - Fase Requisitos; Conceitos sobre Requisitos; Requisitos de Sistema e Requisitos de Software (Funcionais e Não-Funcionais); Técnicas de - Levantamento de Requisitos (Joint Application Development); Gerência de Requisitos; CASE para Requisitos; Documentação da Visão.', 'ENGENHARIA DE REQUISITOS', 4, 'KOTONYA, Gerald e SOMMERVILLE Ian, Requirements Engineering: Process and Techniques 2 ed. John Wiley & Sons 1998. - IEEE STD 830-1998 – Recommended Practice for Software Requirements Specifications. 2a. ed. Springer Verlag NY, 2004. - SILVER, D.; WOOD, J.Joint Application Development 2 ed. John Wiley Professional, 1995. ISBN 0471042994.', 'HULL, Elizabeth, JACKSON, Ken and DICK, Jeremy. Requirements Engineering. 2a. ed. Springer Verlag NY, 2004.', '\r'),
(101, 0, 64, 'Caracterização de software enquanto produto e processo. Gerência de projetos de software: planejamento; métricas; análise e gerência de riscos e acompanhamento de projetos. Controle de qualidade de software. Gerência de configuração de software. Engenharia de sistemas. Análise e projeto de software. Ferramentas de apoio ao desenvolvimento de software. Noções de tópicos avançados em engenharia de software (métodos formais, reengenharia e outros). Técnicas e ferramentas CASE.', 'ENGENHARIA DE SOFTWARE', 4, 'SOMMERVILLE, Ian. Engenharia de Software. Addison-Wesley, Oitava Edição, 2007. - PFLEEGER, Shari L. Software Engineering: Theory and Practice. Prentice-Hall, Terceira Edição, 2006. - PRESSMAN, Roger S..Engenharia de Software. McGraw-Hill, Sexta Edição, 2006.', 'Guide to the Software Engineering Body of Knowledge. Editado por IEEE Computer Society. Disponível em http://www.swebok.org/. Último acesso em 10/08/2008. - BRAUDE, Eric. Software Engineering – An Object-Oriented Perspective. John Wiley & Sons, 2001. - ABRAN, Alain and MOORE, James W. Software Engineering Body of Knowledge. Executive Editors: Editors: Pierre Bourque and Robert Dupuis. Chair: Leonard L. Tripp. IEEE Computer Society, 2005. - BROOKS JR, Frederick P. The Mythical Man-Month – Essays on Software Engineering. Addison-Wesley, 1995. - McCONNELL, Steve. Rapid Development - Taming Wild Software Schedules. Microsoft Press, 1996. - PETERS, James e PEDRYCZ, Witold. Engenharia de Software – Teoria e Prática. Editora Campus, 2001. - Advances in Software Engineering. Hakan Erdogmus e Oryl Tanir (Editores). Springer, 2002.', '\r'),
(102, 32, 32, 'Estudo de estruturas de dados básicas, seus conceitos e operações. Estudo de Listas Lineares, compreensão de seu uso como Pilhas e Filas e de implementações usando vetores, listas lineares encadeadas, listas duplamente encadeadas, listas circulares e listas com descritores. Estudo dos conceitos de Árvores Binárias, dos algoritmos de caminhamentos e da sua aplicação como fila de prioridade. Estudo de Árvores de Pesquisa: árvores binárias balanceadas, árvore B. Estudo de Tabelas de Dispersão.', 'ESTRUTURAS DE DADOS', 4, 'SZWARCFITER, Jayme. MARNENZON, Lilian. Estruturas de Dados e Seus Algoritmos. LTC, 1994. - ZIVIANI, Nivio. Projeto de Algoritmos com implementações em Java e C++. Thompson, 2006. - PREISS, Bruno R. Estruturas de Dados e Algoritmos – Padrões de Projetos Orientados a Objetos com Java. Campus, 2000.', 'DROZDEK, Adam. Estrutura de Dados e Algoritmos em C++. Thompson, 2002. - CELES, Waldemar; CERQUEIRA, Renato; RANGEL, José Lucas. Introdução a Estruturas de Dados com Técnicas de Programação em C. Campus, 2004 - FEOFILOFF, Paulo. Algoritmos em Linguagem C. Campus, 2008.', '\r'),
(103, 0, 32, 'Conceitos de ética e critérios para tomadas de decisões éticas. Códigos de ética profissional. Computadores: campos de aplicação. Aspectos sociais e econômicos de sua utilização. Aspectos estratégicos do controle de tecnologia. Estudos de casos.', 'ÉTICA, COMPUTADOR E SOCIEDADE', 4, 'MASIERO, P. C - Ética em Computação, Editora da USP, 2000. - JUNIOR ARAUJO, Marco Antônio. Ética Profissional - Atualizada com o julgamento da ADIN 1.127/DF. São Paulo: Premier, 2008  - MARCHIONNI, Antônio. Ética A arte do Bom. Ed Vozes, 2008.', 'NALINI, José Renato. Ética Geral e Profissional. 5. ed., São Paulo: RT, 2006.', '\r'),
(104, 0, 64, 'Contabilidade: Conceito, objeto, objetivo, campo de aplicação, usuários e finalidades, técnicas contábeis e evolução contábil. Patrimônio: Ativo, Passivo, Situação Líquida, Receitas e Despesas. Demonstrações Contábeis: relatórios obrigatórios e não obrigatórios; estrutura e interpretação. Processo de contabilização e escrituração: livros contábeis, lançamentos, operações comerciais e inventários. Princípios contábeis: abordagem inicial. A contabilidade na tomada de decisões.', 'FUNDAMENTOS DE CONTABILIDADE', 4, 'MARION, José Carlos. Contabilidade empresarial.12ª ed. Atlas. São Paulo, 2006.  - PADOVEZE, Clóvis Luís. Manual de contabilidade básica. 5ª ed. Atlas. São Paulo, 2004. Livro texto. - FRANCO, Hilário. Contabilidade geral. 13ª ed. Atlas. São Paulo, 1996.', 'FIPECAFI. Manual das sociedades por ações. 7ª ed. Atlas. São Paulo, 2006. - IUDÍCIBUS, Sérgio de; MARION, José Carlos. Contabilidade comercial. 6ª ed. Atlas. São Paulo, 2004. Livro texto - PADOVEZE, Clóvis Luís. Contabilidade gerencial: um enfoque em sistema de informação contábil. 5ª ed. Atlas. São Paulo, 2007. Livro texto/exercícios - SILVA, César Augusto Tibúrcio; TRISTÃO, Gilberto. Contabilidade básica. 3ª ed. Atlas. São Paulo, 2008.', '\r'),
(105, 16, 48, 'Definição de Gerenciamento de Projetos. O Ciclo de Vida de um Projeto. As Fases do Ciclo de Vida do Projeto. Desempenho, Custo e Tempo em Projetos. Principais Áreas do Gerenciamento de Projetos. Preparando a Organização para Projetos. O Gerente de Projetos e suas Interfaces. Fluxograma do Projeto. Ferramentas Computacionais de Gerenciamento de Projetos.', 'GERÊNCIA DE PROJETOS I', 4, 'MULCAHY, Rita. PM Crash Course: A Crash Course in Real-World Project Management, RMC Publications, 2006. - LARMAN, Craig. Agile and Iterative Development: A Manager’s Guide, Addison-Wesley, 2003. - ROTHMAN, Johanna. Manage It!: Your Guide to Modern, Pragmatic Project Management, Pragmatic Bookshelf, 2007.', 'NEILL, C. J; LAPLANTE, P. A. Requirements Engineering for Software and Systems. Auerbach Publications, 2009. - FAIRLEY, R. E. Managing and Leading Software Projects. Wiley- IEEE Computer Society Press, 2009.', '\r');
INSERT INTO `disciplina` (`id`, `carga_horaria_pratica`, `carga_horaria_teorica`, `ementa`, `nome`, `curso_id`, `bibliografia_basica`, `bibliografia_complementar`, `objetivo_geral`) VALUES
(106, 32, 32, 'Gerência de escopo (planejamento, definição, WBS, verificação e controle de escopo). Gerência de tempo (definição de atividades, sequenciamento de atividades, estimativa de recursos, estimativa de duração, desenvolvimento de cronograma e controle de cronograma). Gerência de custos (estimativas, orçamento e controle). Gerência de qualidade (planejamento, garantia da qualidade e controle de qualidade). Gerência de recursos humanos (planejamento, adquirir equipe de projeto, desenvolver a equipe de projeto e gerenciar a equipe de projeto). Gerência de comunicação (planejamento, distribuição da informação, relato de desempenho e gerenciar interessados). Gerência de riscos (planejamento, identificação de riscos, análise qualitativa e quantitativa de riscos, planejamento de resposta a riscos, controle e monitoramento de riscos). Gerência de aquisições (planejamento, planejamento de contratos, requisitar respostas de fornecedores, selecion - ar fornecedores, administração de contratos e fechamento de contrato. Gerência de integração (desenvolver carta de projeto, desenvolver escopo preliminar, desenvolver plano de gerência de projeto, dirigir e gerenciar a execução de projetos, monitorar e controlar atividades de projeto, controle de mudanças e fechamento do projeto). Estabelecer relações com o MPS.BR. Gerência de aquisições deve ser observada da perspectiva do Guia de Aquisições de Software e Serviços Correlatos (MPS.BR).', 'GERÊNCIA DE PROJETOS II', 4, 'NEILL, C. J; LAPLANTE, P. A. Requirements Engineering for Software and Systems. Auerbach Publications, 2009. - FAIRLEY, R. E. Managing and Leading Software Projects. Wiley-IEEE Computer Society Press, 2009. - McCONNELL, Steve. Software Estimation: Demystifying the Black Art, Microsoft Press, 2006.', 'HIGHSMITH, Jim. Agile Project Management: Creating Innovative Products, Addison-Wesley, 2004. - RICHARDSON, Jared and GWALTNEY, William. Ship It!: A Practical Guide to Successful Software Projects.Pragmatic Bookshel, 2005.', '\r'),
(107, 0, 64, 'Áreas estratégias para Gestão de TI. Alinhamento de Estratégias de TI e do Negócio. Critérios para controle da Informação. Processos de Gestão de TI. Tipos de recursos em TI. Modelos de Gestão de TI. Controle de Processos de TI. Indicadores e métricas para gestão de TI. Maturidade e capacitação de processos de TI. Planejamento e organização da área de TI. Aquisição e Implementação de serviços de TI. Entrega e suporte a serviços de TI. Monitoramento e avaliação de resultados de TI.', 'GESTÃO EM TECNOLOGIA DA INFORMAÇÃO', 4, 'MAGALHÃES, Ivan e PINHEIRO. Walfrido. Gerenciamento de Serviços de TI na Prática – Uma abordagem com base na ITIL. Novatec Editora, 2007. - COBIT V. 4.1 - ISBN 1-933284-72-2 - IT Governance Institute, 2007. - ITIL V.3 - The Introduction to the ITIL Service Lifecycle. ISBN - 9780113310616 - Office of Government Commerce, 2007.', 'VIEIRA, Marconi. Gerenciamento de Projetos de Tecnologia da Informação. Editora Campus, 2003.', '\r'),
(108, 64, 0, 'Lógica de programação; constantes; tipos de dados primitivos; variáveis; atribuição; expressões aritméticas e lógicas; estruturas de decisão; estruturas de controle; estruturas de dados homogêneas e heterogêneas: vetores (arrays) e matrizes. Desenvolvimento de algoritmos.Transcrição de algoritmos para uma linguagem de programação. Desenvolvimento de pequenos programas.', 'INTRODUÇÃO À PROGRAMAÇÃO', 4, 'SCHILDT, Herbert. C - Completo e Total, Editora Makron Books, 3a edição. 2002. - LOPES, Anita e GARCIA Guto, Introdução à Programação – 500 Algoritmos Resolvidos, Editora Campus, 2002. - FORBELLONE, A.L.V. e EBERSPACHER, H.F., Lógica de Programação - A construção de algoritmos e estruturas de dados, 3a ed., Prentice Hall, São Paulo, 2005.', 'DEITEL, H.M. e DEITEL, P.J., Java – Como Programar, 6a ed., Pearson Education, 2005. - MANZANO; N. G. José Augusto. e OLIVEIRA, J. F., Algoritmos – Lógica para Desenvolvimento de Programação de Computadores, Editora Érica, São Paulo, 2000. - SALVETTI, D.D. e BARBOSA, L.M., Algoritmos, Makron Books, São Paulo, 1998. - SALIBA, W.L.C., Técnicas de Programação - Uma abordagem estruturada, Makron Books, São Paulo, 1993. - F A R R E R , H . e o u t r o s , Programação Estruturada de Computadores - Algoritmos Estruturados, 3a ed., LTC, RJ, 1989. - C A M A R Ã O , C . e F I G U E I R E D O , L . , Programação de Computadores em Java, LTC Editora, Rio de Janeiro, 2003. - PUGA, S. e RISSETTI, G., Lógica de Programação e Estruturas de Dados com Aplicações em Java, Makron Books, São Paulo, 2004. - FURGERI, S., Java 2 – Ensino Didático – Desenvolvendo e Implemantando Aplicações, 4a ed., São Paulo, 2002. - CORMEN T. H. et al., Algoritmos- Teoria e Prática, 2a Edição, Editora Campus 2002. - MANBER, Udi., Introduction to Algorithms: A Creative Approach, Pearson Education, 1989.', 'Desenvolver no aluno habilidades de desenvolvimento de algoritmos e codificação destes algoritmos em uma linguagem de programação de alto nível. - Mostrar novas formas de desenvolvimento de problemas. - \r'),
(109, 0, 64, 'Projeto Pedagógico do curso de Sistemas de Informação. Características do profissional de sistemas de informação e carreiras de sistemas de informação. Conceituação de computador, informática e software. Histórico da computação e de sistemas de informação. Conceitos, objetivos, tipos, funções, organização e componentes dos sistemas de informação. Sistemas de informação empresariais, empresa digital, tipos de sistemas de informação empresariais. Infra-estrutura de tecnologia de informação: hardware e software, bancos de dados, telecomunicações, Internet e redes. Aplicações de sistemas de informação: sistemas integrados, comércio eletrônico, gestão do conhecimento e sistemas de apoio à decisão. Desenvolvimento e gerenciamento de sistemas de informação. Aspectos de segurança, éticos e sociais.', 'INTRODUÇÃO A SISTEMAS DE INFORMAÇÕES', 4, 'LAUDON, Kenneth C.; LAUDON, Jane P. , Sistemas de Informação Gerenciais, 7a. Edição, Prentice Hall Brasil, 2007. - AUDY, J. L. N.; ANDRADE, G. K.; CIDRAL, A. Fundamento de Sistemas de Informação. Porto alegre: Bookman, 2005. - O’BRIEN, James, Sistemas de Informação, 2a. Edição, Editora Saraiva, 2006.', 'TAIR, R. M. Princípios de Sistemas de Informações. São Paulo : LTC, 1998.', '\r'),
(110, 32, 32, 'Fatores humanos em software interativo: teoria, princípios e regras básicas. Estilos interativos: linguagens de comandos, manipulação direta. Dispositivos de interação. Padrões para interface. Usabilidade: definição e métodos para avaliação.', 'INTERAÇÃO HOMEM-COMPUTADOR', 4, 'SHNEIDERMAN, Ben  and PLAISANT, Catherine. Designing the User Interface –Strategies for  Effective Human-Computer Interaction. Addison Wesley, Quarta Edição, 2004. - COOPER, Alan. About Face: the essentials of user interface design. . IDG Books, 1995.  - ACM Interactions magazine. http://www.acm.org/interactions/ ', 'LEN BASS, Joëlle Coutaz. Developing Software for the User Interface. Addison Wesley,1991.  - MAYHEW, Deborah. Principles and Guidelines in Software User Interface Design.  Prentice Hall, 1992. - NORMAN, Donald A. The Design of Everyday Things.  Basic Books, 2002. - SOMMERVILLE, Ian.  Engenharia de Software. Capítulo 15 – Projeto de Interface com o usuário. Addison-Wesley, Sexta Edição, 2003. - PRESSMAN, Roger S. Engenharia de Software. Capítulo 12 – Projeto de Interface com o usuário. McGraw-Hill, Sexta Edição, 2006. Ergolist. http://www.labiutil.inf.ufsc.br/ergolist/index.html. Laboratório de Utilizabilidade, UFSC.', '\r'),
(111, 0, 64, 'Lógica Proposicional. Proposições e conectivos. Operações Lógicas sobre proposições. Construção de tabelas-verdade. Tautologias, contradições e contingências. Implicação Lógica. Equivalência Lógica. Álgebra das proposições. Métodos para determinação da validade de fórmulas da Lógica Proposicional. Demonstração condicional e demonstração indireta. Lógica de Predicados.', 'LÓGICA', 4, 'SOUZA, João Nunes de. Lógica para Ciência da Computação. Editora Campus 2002. - Alencar Filho, Edgard de, Iniciação à Lógica Matemática. Ed. Nobel 2002. - MENDELSON, E. Introduction to Mathematical Logic. Lewis Publishers, Inc. 1997. - Enderton, H. A Mathematical Introduction to Logic. Academic Press 2000', 'DAGHLIAN, Jacob, Lógica e Álgebra de Boole. – 4a edição, Atlas, S. A . São Paulo, 1995.', '\r'),
(112, 0, 64, 'Princípios dos números naturais, inteiros, racionais e reais. Conjuntos: interseção, união, complemento, produto. Figuras de Venn. Noção de relação e de função. Classificação de funções. Técnicas de demonstração. Recursão e Relação de Recorrência. Matrizes. Sistema de equações lineares e solução. Grafos e dígrafos.', 'MATEMÁTICA DISCRETA', 4, 'GERSTING, Judith L., Fundamentos Matemáticos para a Ciência da Computação. 3a. edição, Editora LTC. - SCHEINERMAN, E. . Matematica Discreta, Thomson Pioneira, 2003. - ROSEN, K. Discrete Mathematics and its Applications. McGraw- Hill Science/Engineering/Math; 5th edition., 2002.', 'TREMBLEY and Manohar. Discrete Mathematical Structures with Applications to Computer Science. McGraw-Hill. - CORMEN T. H. et al., Algoritmos - Teoria e Prática, 2a Edição, Editora Campus 2002.', '\r'),
(113, 0, 64, 'Juros e capitalização simples. Capitalização composta. Desconto e taxas de desconto. - Séries de pagamento. Métodos de avaliação de fluxos de caixa. Taxas de juros. Sistemas de amortização. Operações realizadas no sistema financeiro brasileiro', 'MATEMÁTICA FINANCEIRA', 4, 'VIEIRA SOBRIHO, J. Dutra. Matemática financeira 6ª ed. São Paulo: Atlas, 1997. (livro texto) - PUCCINI, A. L. e PUCCINI, A. Matemática financeira: objetiva e aplicada (Edição compacta). Editora Saraiva, 2006. - ASSAF, A. N. Matemática Financeira e suas aplicações. Editora Atlas', 'PADOVEZE,  Clovis  Luiz.  Análise  das  Demonstrações Financeiras. Editora Thomson Heinle, 2007.', '\r'),
(114, 0, 64, 'Gestão estratégica: conceitos, requisitos, vantagens e processo; Definição das filosofias, políticas e diretrizes superiores. Definição de objetivos, estratégia, e tomada de decisão. Fatores Críticos de Sucesso. Análise do ambiente interno e externo da organização. Análise de recursos, requisitos, cenários e tendências. Processo de elaboração e implementação do planejamento estratégico. Planejamento estratégico versus análise competitiva. Estruturas e modelos para formulação de estratégias. Estratégias competitivas modernas. BSC (Balanced Scorecard) como instrumento de gestão estratégica. Mapeamentos de estratégias para ações. Indicadores de Desempenho.', 'PLANEJAMENTO ESTRATÉGICO', 4, 'FERNANDES, Bruno H. R; BERTON, Luiz H. Administração Estratégica: da competência empreendedora à avaliação de desempenho. São Paulo: Saraiva, 2005. - BETHLEM, Agrícola. Estratégia Empresarial: conceitos, processo e administração estratégica. 5 ed. São Paulo: Atlas, 2004. - COSTA, E. Gestão Estratégica. São Paulo: Saraiva, 2004.', 'HITT, Michael A; IRELAND, R. D; HOSKISSON, Robert E. Administração Estratégica. São Paulo: Pioneira THOMSON, 2003. - KAPLAN, R. S. Alinhamento: utilizando o BSC para criar sinergias corporativas. Rio de Janeiro:Elsevier, 2006. - PORTER, Michael E. Estratégia Competitiva: técnicas para análise de indústrias e da concorrência. Rio de Janeiro:Elsevier, 2004.', '\r'),
(115, 32, 32, 'Estudo dos conceitos da linguagens de programaçao imperativa e de seus paradigmas. Ambiente integrado de desenvolvimento (edição, compilação, depuração, empacotamento e distribuição). Estrutura de dados homogêneas e heterogêneas na programação imperativa. Alocação estática e dinâmica.. Modularização (passagem de parâmetros, procedimentos e funções recursivas e não recursivas). Manipulação de Arquivos. Estudos de caso de aplicações desenvolvidas com a programação imperativa.', 'PROGRAMAÇÃO IMPERATIVA', 4, 'ASCENCIO, Ana Fernanda Gomes / CAMPOS, Edilene Aparecida Veneruchi De. Fundamentos da Programação de Computadores. Editora: - Longman do Brasil, 2007. - SCHILDT, Herbert. C - Completo e Total, Editora Makron Books, 3a edição. 2002. - DEITEL, H. M., Java Como Programar, Pearson Prentice Hall Brasil, 6a. Edição, 2005.', 'LOPES, Anita e GARCIA Guto, Introdução à Programação – 500 Algoritmos Resolvidos, Editora Campus, 2002.', '\r'),
(116, 64, 32, 'Paradigma de orientação a objetos. Linguagens orientadas a objetos. Abstração e tipos abstratos. Classes, métodos, encapsulamento, interface. Mensagens, instâncias e inicialização. Herança e composição. A combinação de herança e composição. Polimorfismo: variáveis polimórficas, sobrecarga, construtores. Ambiente integrado de desenvolvimento (IDE): conceituação e utilização. Implementação de programas em Java. Introdução a objetos em Java: encapsulamento, classes, métodos, objetos, mensagens, construtores, composição, herança, polimorfismo. Estudos de caso de aplicações desenvolvidas com a linguagem de programação Java.', 'PROGRAMAÇÃO ORIENTADA A OBJETOS', 4, 'DEITEL, H. M., Java Como Programar, Pearson Prentice Hall Brasil, 6a. edição, 2005. - HORSTMANN, C. S. Core Java – Volume I – Fundamentals, Prentice Hall. 8a. edição, 2007. - SPEEGLE, G. D. JDBC : Practical Guide for Java Programmers. Morgan Kaufmann Publishers, 2002.', 'HORSTMANN, C. S. Core Java – Volume I – Fundamentals, Prentice Hall. 8a. edição, 2007. - HORSTMANN, C. S. Core Java – Volume II – Advanced Features, Prentice Hall, 8a. Edição, 2008.', '\r'),
(117, 80, 16, 'Paradigma e padrões de desenvolvimento de aplicações para a Web. Interface gráfica do usuário (GUI - Graphical User Interface) em ambiente Web. Plataforma Java para desenvolvimento de aplicações para a Web. Visão geral e Arquitetura de Servlets. Linguagem para conteúdo web dinâmico na arquitetura Java (JSP – Java Server Pages). Tratamento de Eventos em Java no ambiente Web. Acesso a Banco de dados em ambiente WEB (JDBC - Java Database Connectivity). Estudos de caso de aplicações desenvolvidas com a linguagem Java para web.', 'PROGRAMAÇÃO PARA WEB', 4, 'GONÇALVES, E. Desenvolvendo aplicações Web com NetBeans IDE 6. Ciência Moderna. Rio de Janeiro-RJ, 2008. - DEITEL, H. M., Java Como Programar, Bookman Companhia Ed., 6a. Edição, 2005 - KURNIAWAN, B. Java para a Web com Servlets, JSP e EJB. Ciência Moderna, Rio de Janeiro-RJ, 2002.', 'SPEEGLE, G. D. JDBC : Practical Guide for Java Programmers. Morgan Kaufmann Publishers, 2002. - MUKHAR K., TODD L., CARNELL J. Beginning Java databases. Wrox Press, Birmingham, UK, 2001. - HALL, M. Core Web Programming. Prentice Hall, 1997.', '\r'),
(118, 0, 64, 'Definição de projeto. Questões fundamentais (persistência dos dados, exceções e outras). Contexto de projeto em vários modelos de desenvolvimento de software (ciclos de vida). Princípios de projeto (encapsulamento de informações, coesão e acoplamento). Interação entre projeto e requisitos. Atributos qualitativos em um projeto (confiabilidade, usabilidade, manutenibilidade, testabilidade, desempenho, segurança, tolerância a falhas e outros). Compromissos (custo-benefício). Relação entre arquitetura de software e projeto de software. Projeto orientado a objetos. Projeto funcional. Noção de projeto baseado em estrutura de dados e projeto orientado a aspectos. Projeto orientado por responsabilidade. Projeto por contratos. Métodos de projeto de software. Padrões de projeto. Reutilização. Projeto de componentes. Projeto de interfaces entre componentes e sistemas. Notações de projeto. Ferramentas de suporte a projeto (análise estática, ava - liação dinâmica e outras). Medidas de atributos de projeto (acoplamento, coesão e outras). Métricas de projeto (principais métricas, interpretação).', 'PROJETO DE SOFTWARE', 4, 'Evans, Eric. Domain-Driven Design: Tackling Complexity in the Heart of Software. Addison-Wesley, 2003. - MANOLESCU, Dragos et al. Pattern Languages of Program Design 5, Addison-Wesley, 2006. - BUDGEN, David. Software Design, 2nd ediciton, Addison-Wesley, 2003.', 'SOMMERVILLE, Ian. Engenharia de Software. AddisonWesley. 2003.', '\r'),
(119, 0, 64, 'Visão geral de qualidade. Processo de software. Produto de Software. Qualidade de produto de software. Avaliação de qualidade de produto de software. Norma ISO 9126 para qualidade de produto. Avaliação de qualidade de processo de software. Melhoria de processo de software. Modelos Capability Maturity Model (CMM), Software Process Improvement and Capability Determination (SPICE) e Melhoria de Processo de Software Brasileiro (MPS.Br) para avaliação e melhoria de processo de software. Norma ISO 9000-3 - Diretrizes para Aplicação da ISO 9001 ao Desenvolvimento, Fornecimento e Manutenção de Software . Planejamento para melhoria de processo de software: gerenciamento de configuração, garantia de qualidade, planejamento e acompanhamento de projetos, gerenciamento de requisitos, gerenciamento de subcontratados.', 'QUALIDADE DE SOFTWARE', 4, 'ROCHA, A. R. C.; MALDONADO, J. C.; WEBER, K. C. Qualidade de Software - Teoria e Prática. Prentice Hall, São Paulo/SP, 2001. - HUMPHREY, W.S. - Managing the Software Process - Addison Wesley, 1989. - PAULK, M.C.;CURTIS, B.;CHRISSIS, M,B.; WEBER, C. V., Capability Maturity IEEE Standard for Software Project Management Plans, IEEE Software Engineering Standards Collection, primavera, 1991.', 'CHRISSIS, M. B.; KONRAD, M.; SHRUM, S. CMMI: Guidelines for Process Integration and Product Improvement. Addison-Wesley Professional. 2 ed. 2006. - KAN, S. H. Metrics and Models in Software Quality Engineering. Addison-Wesley Professional. 2 ed, 2002. - FUTRELL, R. T.; SHAFER, D. F.; SHAFER, L. I.. Quality Software Project Management. Prentice Hall PTR. 2002. - GALIN, D. Software Quality Assurance: From Theory to Implementation. Addison Wesley. 2003. - LOON, H. van. Process Assessment and Improvement: A Practical Guide for Managers, Quality Professionals and Assessors. Springer; 1 ed. 2004. - LOON, H. van. Process Assessment and ISO/IEC 15504: A Reference Book – Book 2. Springer; 2 ed. 2007.', '\r'),
(120, 16, 48, 'Fundamentos: arquitetura de redes de computadores e modelos de referência (OSI e TCP/IP); serviços e protocolos de comunicação; desempenho de redes de computadores (atraso, perda e largura de banda); arquitetura geral da Internet. Camada de Aplicações: interface de programação de aplicações; prática de programação de aplicações em rede; a Web e o protocolo HTTP; serviço de transferência de arquivos (FTP); correio eletrônico (SMTP e protocolos de acesso ao correio); serviço de nomes (DNS); aplicações e redes peer-to-peer (P2P) e redes sobrepostas (overlay networks). Camada de Transporte: tipos de serviço da camada de transporte; multiplexação e demultiplexação; transporte orientado a conexões; transferência confiável de dados; controle de congestionamento; protocolos de transporte da Internet (UDP e TCP). Camada de Rede: comutação de pacotes; redes de datagramas e de circuitos virtuais; princípios de roteamento unicast e multica - st; interconexão de redes; o protocolo IP. Camada de Enlace: enquadramento de dados; detecção e correção de erros; protocolos de acesso múltiplo; endereçamento na camada de enlace; redes Ethernet; comutadores e interconexão de redes; protocolo ponto-a-ponto (PPP); virtualização de enlaces (ATM e MPLS). Tópicos avançados: redes sem fio; redes e aplicações multimídia; segurança em redes; gerenciamento de redes.', 'REDES DE COMPUTADORES', 4, 'KUROSE, J.F. e ROSS, K.W. Redes de Computadores e a Internet – Uma abordagem top-down. 3a. ed. Pearson/Addison-Wesley, São Paulo, 2005. - PETERSON, L.L. e DAVIE, B.S. Redes de Computadores – Uma abordagem de sistemas. 3a. ed. Ed. Campus/Elsevier, Rio de Janeiro, 2004. - TANENBAUM, A.S. Redes de Computadores. 4a. ed. Ed. Campus, Rio de Janeiro, 2003.', 'KIZZA, J. Computer Network Security. Springer, 2005.', '\r'),
(121, 32, 32, 'Introdução a Segurança; Ameaças e Ataques; Estratégias de ataques e defesas; Vulnerabilidades em softwares, serviços e protocolos; Segurança nos serviços Internet; Técnicas de varredura; Técnicas de análise de vulnerabilidade; NAT; PROXY; Relacionamento das Aplicações com os Mecanismos de Segurança; Tipos e Arquitetura de Proteção; Firewalls; Políticas de Segurança, Criptografia; Normas de segurança; Gestão de riscos de Segurança; Conceitos de auditoria. Auditoria de sistemas e a área de SI. Avaliação de integridade e segurança de dados, de efetividade e de eficiência. Softwares de auditoria.', 'SEGURANÇA EM SISTEMAS DE INFORMAÇÃO', 4, 'KIZZA, J. Computer Network Security. Springer, 2005. - ONOME, Joshua. Auditoria de Sistemas de Informações, 2ª Edição, Atlas, 2008. - STALLINGS, W. Cryptography and Network Security. Prentice Hall, 2006. ', 'McCLURE, S. Hacking Exposed, Mcgraw-Hill Osborne Media, 2005.', '\r'),
(122, 32, 64, 'Conceitos de Hardware e Software ; Tipos de Sistemas Operacionais; Sistemas Multiprogramáveis; Estrutura do Sistema Operacional; Processo; Comunicação entre processos; Gerência do Processador; Gerência de Memória ; Gerência de Dispositivos; Sistemas de Arquivos; Estudos de casos de sistemas operacionais atuais.', 'SISTEMAS OPERACIONAIS', 4, 'TOSCANI, S. S.; CARISSIMI, A. da S.; OLIVEIRA, R. S. de. Sistemas Operacionais: Série Livros Didáticos Instituto de Informática da UFRGS. 2 ed. Porto Alegre: Sagra Luzzatto, 2001. - TANEMBAUM, A. S. Sistemas Operacionais Modernos. 2 ed. São Paulo: Ed. Prentice Hall, 2003. ISBN 85-87918-57-5. - NEMETH E.; SNYDER, G.; HEIN, TRENT R. Manual Completo do Linux, Guia do Administrador. 2. Ed. São Paulo : Pearson-Prentice Hall, 2007. ISBN 978-85-7605-112-1.', 'OLIVEIRA, R. S., CARISSIMI, A. S., TOSCANI, S. S. Sistemas Operacionais. Porto Alegre : Instituto de Informática da UFRGS: Editora Sagra Luzzatto, 2004.', '\r'),
(123, 0, 64, 'Evolução do pensamento administrativo – as escolas de administração. A natureza da ação administrativa. Processos administrativos – planejamento, organização, direção e controle. Tendências da administração.', 'TEORIA GERAL DA ADMINISTRAÇÃO', 4, 'LACAVA KWASNICKA, Eunice. Introdução à Administração. 6.ed. São Paulo: Atlas, 2007. - LACOMBE, Francisco; HEILBORN, Gilberto. Administração: princípios e tendências. São Paulo: Saraiva, 2003 - ROBBINS, Stephen P. Administração: mudanças e perspectivas. São Paulo: Saraiva, 2001 - STONER, J.A F., FREEMAN, R.E. Administração.5ª ed. Rio de Janeiro : LTC, 2002.', 'BERNARDES, C.; MARCONDES, R.C. Teoria Geral da Administração: gerenciando organizações. São Paulo: Saraiva, 2003. - CERTO, Samuel C. Administração Moderna. São Paulo: Prentice Hall, 2003. - CHIAVENATO, Idalberto. Administração nos novos tempos. Rio de Janeiro: Campus, 2004. - MAXIMIANO, Antonio C. Teoria Geral da Administração. São Paulo: Atlas, 1999.', '\r'),
(124, 32, 32, 'Abordagens de conteúdos ou temas relacionados à área computacional. A ementa e o plano de ensino serão definidos e aprovados de acordo com os conteúdos e temas propostos.', 'TÓPICOS III', 4, 'A DEFINIR', 'A DEFINIR', 'A DEFINIR\r'),
(125, 32, 32, 'Abordagens de conteúdos ou temas relacionados à área computacional. A ementa e o plano de ensino serão definidos e aprovados de acordo com os conteúdos e temas propostos.', 'TÓPICOS II', 4, 'A DEFINIR', 'A DEFINIR', 'A DEFINIR\r'),
(126, 32, 32, 'Abordagens de conteúdos ou temas relacionados à área computacional. A ementa e o plano de ensino serão definidos e aprovados de acordo com os conteúdos e temas propostos.', 'TÓPICOS I', 4, 'A DEFINIR', 'A DEFINIR', 'A DEFINIR\r'),
(127, 32, 32, 'Conceitos básicos sobre manutenção de software. Manutenabilidade. Processos de Manutenção. Gestão da manutenção de software (processo, planejamento, gestão de configuração, gestão de riscos). Teste de software e teste de regressão. Compreensão de programas. Engenharia reversa. Reengenharia. Ferramentas aplicadas à manutenção.', 'MANUTENÇÃO DE SOFTWARE', 4, 'GLASS, Robert L. Software maintenance guidebooks. Englewood Cliffs : Prentice-Hall, 1981. - GRUBB, P., TAKANG, A. A., Software Maintenance: Concepts and Practice, World Scientific Publishing Company; 2ed., 2003.', 'McCONNELL, Steve. Code Complete: A Practical Handbook of Software Construction. Microsoft Press; 2ed., 2004.', 'A DEFINIR\r'),
(128, 0, 64, 'O Direito como ciência, valor, poder, norma e fato social. Capacidade das pessoas, Noção de Direito das Obrigações, Regras gerais dos Contratos. Direito autoral e legislação de proteção ao software.', 'DIREITO', 4, 'PALAIA, Nelson -Noções Essenciais de Direito, 3a Edição, 2005 Editora Saraiva - MARTINS, Sérgio Pinto. Instituições de Direito Público e Privado, 6o Edição, 2006 Editora Atlas - PAESANI, Liliana Minardi. Direito e Internet, 3a Edição, 2006 Editora Atlas.', 'RODRIGUES, Silvio - Direito Civil vol. I a VII, 2002, Editora Atlas - CORRÊA, Gustavo Testa - Aspectos Jurídicos da Internet, 2000, Editora Saraiva. - Lei 8.078/1990 - Código de Proteção e Defesa do Consumidor.', '\r'),
(129, 0, 64, 'Um resgate conceitual e histórico dos modelos de gestão de pessoas. Os processos na área de gestão de pessoas: Recrutamento e Seleção; Cargos e Salários; Treinamento e Desenvolvimento (T&D); Plano de Carreira; Avaliação de Desempenho; Benefícios Sociais; Saúde e Segurança no Trabalho. Perspectivas e tendências da gestão de pessoas.', 'GESTÃO DE PESSOAS', 4, 'ARAÚJO, Luís César G. Gestão de pessoas: estratégias e integração organizacional. S.P.: Atlas, 2008. - AS PESSOAS NA ORGANIZAÇÃO. São Paulo: Editora Gente, 2002. Vários autores. - CHIA VENA TO, Idalberto. Gestão de Pessoas - o novo papel de Recursos Humanos nas organizações. 2.ed. R.J..: Ed. Campus, 2005.', 'DESSLER, Gary. Administração de recursos humanos. 2.ed. S.P.: Prentice Hall, 2003. - LACOMBE, Francisco. Recursos Humanos: princípios e tendências. S.P.: Saraiva, 2005. - RIBEIRO, Antonio de Lima. Gestão de pessoas. S.P.: Saraiva, 2006. - VERGARA, Sylvia Constant. Gestão de pessoas. S.P.: Atlas, 2000.', '\r'),
(1000000, 0, 0, '', 'Disciplina Teste Editada', 1, '', '', '');

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=702 ;

--
-- Extraindo dados da tabela `parecer`
--

INSERT INTO `parecer` (`id`, `requerimento_id`, `usuario_id`, `data_parecer`, `conteudo`, `status`) VALUES
(651, 1000157, 1002, '2013-11-11 11:11:34', 'Retirar sua declaração na secretária.', 2),
(701, 1000177, 1002, '2013-11-11 11:30:33', 'Não pode ser emitido o declaração de matrícula. Aluno com situação irregular.', 3);

-- --------------------------------------------------------

--
-- Estrutura da tabela `perfil`
--

CREATE TABLE IF NOT EXISTS `perfil` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=8 ;

--
-- Extraindo dados da tabela `perfil`
--

INSERT INTO `perfil` (`id`, `nome`) VALUES
(1, 'Administrador do Sistema'),
(2, 'Aluno'),
(3, 'Coordenador de Curso'),
(4, 'Coordenador de Estágio'),
(5, 'Professor'),
(6, 'Secretário(a)/Funcionário Administrativo'),
(7, 'Diretor');

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

--
-- Extraindo dados da tabela `professor`
--

INSERT INTO `professor` (`id`, `usuario_id`) VALUES
(1, 1002),
(3, 1002),
(4, 1002),
(5, 1002),
(6, 1002),
(7, 1002),
(8, 1002),
(9, 1002),
(10, 1002),
(11, 1002),
(12, 1002),
(13, 1002),
(14, 1002),
(15, 1002),
(16, 1002),
(17, 1002),
(18, 1002),
(19, 1002),
(20, 1002),
(21, 1002),
(22, 1002),
(23, 1002),
(24, 1002),
(25, 1002),
(26, 1002),
(27, 1002),
(28, 1002),
(29, 1002),
(30, 1002),
(31, 1002),
(32, 1002),
(33, 1002),
(34, 1002),
(35, 1002),
(36, 1002),
(37, 1002),
(38, 1002),
(39, 1002),
(40, 1002),
(41, 1002),
(42, 1002),
(43, 1002),
(44, 1002),
(45, 1002),
(46, 1002),
(47, 1002),
(48, 1002),
(49, 1002),
(50, 1002),
(51, 1002),
(52, 1002),
(53, 1002),
(54, 1002),
(55, 1002),
(56, 1002),
(57, 1002),
(58, 1002),
(59, 1002),
(60, 1002),
(61, 1002),
(62, 1002),
(63, 1002),
(64, 1002),
(65, 1002),
(66, 1002),
(67, 1002),
(68, 1002),
(69, 1002),
(70, 1002),
(71, 1002),
(72, 1002),
(73, 1002),
(74, 1002),
(75, 1002),
(76, 1002),
(77, 1002),
(78, 1002),
(79, 1002),
(80, 1002),
(81, 1002),
(82, 1002),
(83, 1002),
(84, 1002),
(88, 1002),
(89, 1002),
(90, 1002),
(91, 1002),
(92, 1002),
(93, 1002),
(94, 1002),
(1000004, 1003),
(1000005, 1004),
(96, 1008),
(95, 1015),
(1000003, 1024),
(1000006, 1044),
(1000007, 1046),
(1000008, 1047),
(1000001, 1058),
(97, 1077),
(1000000, 1079),
(1000002, 1193);

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

--
-- Extraindo dados da tabela `reqementa_disciplina`
--

INSERT INTO `reqementa_disciplina` (`Disciplina_id`, `Req_Ementa_Requerimento_id`) VALUES
(1, 1000143),
(1, 1000151),
(1, 1000178),
(1, 1000181),
(2, 1000154),
(2, 1000178),
(5, 1000167),
(35, 1000143),
(35, 1000151),
(35, 1000154),
(35, 1000165),
(35, 1000178),
(36, 1000143),
(36, 1000178),
(37, 1000143),
(37, 1000166),
(37, 1000178),
(38, 1000178),
(39, 1000178),
(52, 1000143),
(91, 1000143),
(91, 1000178),
(92, 1000178),
(93, 1000154),
(94, 1000178),
(97, 1000167),
(101, 1000143);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1000186 ;

--
-- Extraindo dados da tabela `requerimento`
--

INSERT INTO `requerimento` (`id`, `usuario_id`, `data_abertura`, `status_req`, `data_fechamento`, `justificativa`, `tipo`) VALUES
(1000141, 1027, '2013-11-11 08:53:13', 1, NULL, NULL, 4),
(1000142, 1027, '2013-11-11 08:54:09', 4, NULL, NULL, 5),
(1000143, 1027, '2013-11-11 08:54:39', 1, NULL, NULL, 6),
(1000144, 1027, '2013-11-11 08:54:56', 1, NULL, NULL, 7),
(1000145, 1027, '2013-11-11 08:55:41', 1, NULL, 'Estava viajando pela empresa.', 3),
(1000146, 1027, '2013-11-11 08:57:35', 1, NULL, NULL, 1),
(1000147, 1027, '2013-11-11 08:58:07', 1, NULL, NULL, 2),
(1000148, 1027, '2013-11-11 08:58:50', 1, NULL, NULL, 5),
(1000149, 1141, '2013-11-11 09:05:01', 1, NULL, NULL, 4),
(1000150, 1143, '2013-11-11 09:05:19', 1, NULL, NULL, 5),
(1000151, 1143, '2013-11-11 09:05:26', 1, NULL, NULL, 6),
(1000152, 1143, '2013-11-11 09:08:30', 1, NULL, 'Minha avÃÂ³ internou com problema sÃÂ©rio, tive que acompanhar.', 3),
(1000153, 1143, '2013-11-11 09:08:40', 1, NULL, NULL, 1),
(1000154, 1390, '2013-11-11 09:09:26', 1, NULL, NULL, 6),
(1000155, 1390, '2013-11-11 09:09:34', 1, NULL, NULL, 4),
(1000156, 1465, '2013-11-11 09:10:17', 1, NULL, NULL, 7),
(1000157, 1465, '2013-11-11 09:10:22', 2, '2013-11-11 11:11:34', NULL, 4),
(1000158, 1465, '2013-11-11 09:10:27', 1, NULL, NULL, 5),
(1000159, 1516, '2013-11-11 09:10:57', 1, NULL, NULL, 4),
(1000160, 1516, '2013-11-11 09:11:00', 1, NULL, NULL, 4),
(1000161, 1516, '2013-11-11 09:11:03', 1, NULL, NULL, 4),
(1000162, 1516, '2013-11-11 09:11:05', 1, NULL, NULL, 4),
(1000163, 1516, '2013-11-11 09:11:08', 1, NULL, NULL, 5),
(1000164, 1516, '2013-11-11 09:11:10', 1, NULL, NULL, 5),
(1000165, 1516, '2013-11-11 09:11:16', 1, NULL, NULL, 6),
(1000166, 1516, '2013-11-11 09:11:21', 1, NULL, NULL, 6),
(1000167, 1516, '2013-11-11 09:11:31', 1, NULL, NULL, 6),
(1000168, 1516, '2013-11-11 09:11:33', 1, NULL, NULL, 7),
(1000169, 1516, '2013-11-11 09:11:37', 1, NULL, NULL, 7),
(1000170, 1516, '2013-11-11 09:11:57', 1, NULL, 'Foi mal', 3),
(1000171, 1516, '2013-11-11 09:12:10', 1, NULL, NULL, 1),
(1000172, 1516, '2013-11-11 09:12:18', 1, NULL, NULL, 2),
(1000173, 1765, '2013-11-11 09:13:09', 1, NULL, NULL, 4),
(1000174, 1765, '2013-11-11 09:13:12', 1, NULL, NULL, 4),
(1000175, 1765, '2013-11-11 09:13:14', 4, NULL, NULL, 4),
(1000176, 1765, '2013-11-11 09:13:39', 4, NULL, NULL, 5),
(1000177, 1465, '2013-11-11 09:15:38', 3, '2013-11-11 11:30:33', NULL, 4),
(1000178, 1465, '2013-11-11 09:16:26', 1, NULL, NULL, 6),
(1000179, 2016, '2013-11-11 09:16:54', 1, NULL, NULL, 4),
(1000180, 2016, '2013-11-11 09:16:57', 1, NULL, NULL, 5),
(1000181, 2016, '2013-11-11 09:17:03', 1, NULL, NULL, 6),
(1000182, 2016, '2013-11-11 09:17:07', 1, NULL, NULL, 7),
(1000183, 2016, '2013-11-11 09:17:25', 1, NULL, '', 3),
(1000184, 2016, '2013-11-11 09:17:37', 4, NULL, NULL, 1),
(1000185, 2016, '2013-11-11 09:17:56', 4, NULL, NULL, 2);

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

--
-- Extraindo dados da tabela `req_acrescimodisciplina_turma`
--

INSERT INTO `req_acrescimodisciplina_turma` (`id`, `Req_Acrescimo_Disciplina_Requerimento_id`, `Turma_id`, `status_disciplina`) VALUES
(1000042, 1000146, 75, 1),
(1000043, 1000146, 73, 1),
(1000044, 1000146, 122, 1),
(1000045, 1000153, 148, 1),
(1000046, 1000171, 148, 1),
(1000047, 1000171, 73, 1),
(1000048, 1000184, 147, 1),
(1000049, 1000184, 73, 1),
(1000050, 1000184, 148, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `req_acrescimo_disciplina`
--

CREATE TABLE IF NOT EXISTS `req_acrescimo_disciplina` (
  `Requerimento_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Requerimento_id`),
  KEY `Req_Acrescimo_Disciplina_FKIndex1` (`Requerimento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `req_acrescimo_disciplina`
--

INSERT INTO `req_acrescimo_disciplina` (`Requerimento_id`) VALUES
(1000146),
(1000153),
(1000171),
(1000184);

-- --------------------------------------------------------

--
-- Estrutura da tabela `req_assinatura`
--

CREATE TABLE IF NOT EXISTS `req_assinatura` (
  `Requerimento_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Requerimento_id`),
  KEY `Req_Assinatura_FKIndex1` (`Requerimento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `req_assinatura`
--

INSERT INTO `req_assinatura` (`Requerimento_id`) VALUES
(1000144),
(1000156),
(1000168),
(1000169),
(1000182);

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

--
-- Extraindo dados da tabela `req_cancelamentodisciplina_turma`
--

INSERT INTO `req_cancelamentodisciplina_turma` (`id`, `Req_Cancelamento_Disciplina_Requerimento_id`, `Turma_id`, `status_disciplina`) VALUES
(11, 1000147, 34, 1),
(12, 1000147, 11, 1),
(13, 1000147, 109, 1),
(14, 1000172, 64, 1),
(15, 1000185, 148, 1),
(16, 1000185, 147, 1),
(17, 1000185, 63, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `req_cancelamento_disciplina`
--

CREATE TABLE IF NOT EXISTS `req_cancelamento_disciplina` (
  `Requerimento_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Requerimento_id`),
  KEY `Req_Cancelamento_Disciplina_FKIndex1` (`Requerimento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `req_cancelamento_disciplina`
--

INSERT INTO `req_cancelamento_disciplina` (`Requerimento_id`) VALUES
(1000147),
(1000172),
(1000185);

-- --------------------------------------------------------

--
-- Estrutura da tabela `req_declaracao_matricula`
--

CREATE TABLE IF NOT EXISTS `req_declaracao_matricula` (
  `Requerimento_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Requerimento_id`),
  KEY `Req_Declaracao_Matricula_FKIndex1` (`Requerimento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `req_declaracao_matricula`
--

INSERT INTO `req_declaracao_matricula` (`Requerimento_id`) VALUES
(1000141),
(1000149),
(1000155),
(1000157),
(1000159),
(1000160),
(1000161),
(1000162),
(1000173),
(1000174),
(1000175),
(1000177),
(1000179);

-- --------------------------------------------------------

--
-- Estrutura da tabela `req_ementa`
--

CREATE TABLE IF NOT EXISTS `req_ementa` (
  `Requerimento_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Requerimento_id`),
  KEY `Req_Ementa_FKIndex1` (`Requerimento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `req_ementa`
--

INSERT INTO `req_ementa` (`Requerimento_id`) VALUES
(1000143),
(1000151),
(1000154),
(1000165),
(1000166),
(1000167),
(1000178),
(1000181);

-- --------------------------------------------------------

--
-- Estrutura da tabela `req_extratoacademico`
--

CREATE TABLE IF NOT EXISTS `req_extratoacademico` (
  `Requerimento_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`Requerimento_id`),
  KEY `Req_ExtratoAcademico_FKIndex1` (`Requerimento_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `req_extratoacademico`
--

INSERT INTO `req_extratoacademico` (`Requerimento_id`) VALUES
(1000142),
(1000148),
(1000150),
(1000158),
(1000163),
(1000164),
(1000176),
(1000180);

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

--
-- Extraindo dados da tabela `req_segunda_chamada`
--

INSERT INTO `req_segunda_chamada` (`Requerimento_id`, `Turma_id`, `data_prova`) VALUES
(1000145, 73, '2013-11-22'),
(1000152, 151, '2013-09-25'),
(1000170, 111, '2013-11-04'),
(1000183, 34, '2013-10-31');

-- --------------------------------------------------------

--
-- Estrutura da tabela `SEQUENCE`
--

CREATE TABLE IF NOT EXISTS `SEQUENCE` (
  `SEQ_NAME` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Extraindo dados da tabela `SEQUENCE`
--

INSERT INTO `SEQUENCE` (`SEQ_NAME`, `SEQ_COUNT`) VALUES
('SEQ_GEN', '750'),
('tab_id_curso', '1000002'),
('tab_id_professor', '1000008'),
('tab_id_disciplina', '1000000'),
('tab_id_turma', '1000000'),
('tab_id_acrescimo', '1000009'),
('tab_id_req', '1000010');

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

--
-- Extraindo dados da tabela `turma`
--

INSERT INTO `turma` (`id`, `ano`, `nome`, `semestre`, `disciplina_id`, `professor_id`) VALUES
(3, 2014, 'A', 1, 7, 56),
(4, 2012, 'A', 1, 33, 53),
(5, 2012, 'A', 1, 34, 53),
(6, 2012, 'A', 1, 14, 70),
(7, 2012, 'A', 1, 12, 75),
(8, 2012, 'A', 1, 1, 70),
(9, 2012, 'A', 1, 31, 27),
(10, 2012, 'A', 1, 9, 95),
(11, 2012, 'A', 1, 5, 12),
(12, 2012, 'A', 1, 32, 13),
(13, 2012, 'A', 1, 13, 56),
(14, 2012, 'A', 1, 6, 64),
(15, 2012, 'A', 1, 29, 17),
(16, 2012, 'A', 1, 30, 17),
(17, 2012, 'A', 1, 16, 8),
(19, 2012, 'A', 1, 11, 20),
(21, 2012, 'A', 1, 10, 27),
(22, 2012, 'A', 1, 24, 20),
(23, 2012, 'A', 1, 3, 29),
(24, 2012, 'A', 1, 28, 29),
(25, 2012, 'A', 1, 19, 33),
(27, 2012, 'A', 1, 23, 77),
(28, 2012, 'A', 1, 25, 31),
(29, 2012, 'A', 1, 15, 74),
(30, 2012, 'A', 1, 17, 13),
(31, 2012, 'A', 1, 8, 71),
(32, 2012, 'A', 1, 18, 45),
(33, 2012, 'A', 1, 21, 78),
(34, 2012, 'A', 1, 4, 49),
(35, 2012, 'A', 1, 26, 70),
(36, 2012, 'A', 1, 27, 47),
(37, 2012, 'A', 1, 22, 47),
(38, 2012, 'A', 1, 2, 78),
(55, 2012, 'A', 1, 66, 58),
(56, 2012, 'A', 1, 51, 58),
(57, 2012, 'A', 1, 40, 58),
(58, 2012, 'B', 1, 40, 58),
(59, 2012, 'A', 1, 48, 58),
(60, 2012, 'A', 1, 67, 58),
(61, 2012, 'A', 1, 53, 58),
(62, 2012, 'A', 1, 36, 58),
(63, 2012, 'B', 1, 36, 58),
(64, 2012, 'A', 1, 75, 58),
(65, 2012, 'A', 1, 76, 58),
(66, 2012, 'A', 1, 56, 58),
(67, 2012, 'A', 1, 77, 58),
(68, 2012, 'A', 1, 78, 58),
(69, 2012, 'A', 1, 63, 58),
(70, 2012, 'A', 1, 79, 58),
(71, 2012, 'A', 1, 73, 58),
(72, 2012, 'A', 1, 71, 58),
(73, 2012, 'A', 1, 35, 58),
(74, 2012, 'A', 1, 62, 58),
(75, 2012, 'B', 1, 62, 58),
(76, 2012, 'A', 1, 38, 58),
(77, 2012, 'B', 1, 38, 58),
(78, 2012, 'A', 1, 70, 58),
(79, 2012, 'A', 1, 41, 58),
(80, 2012, 'B', 1, 41, 58),
(94, 2012, 'A', 1, 99, 67),
(95, 2012, 'A', 1, 98, 84),
(96, 2012, 'A', 1, 92, 46),
(97, 2012, 'A', 1, 118, 70),
(98, 2012, 'A', 1, 116, 46),
(99, 2012, 'A', 1, 93, 71),
(100, 2012, 'A', 1, 110, 12),
(101, 2012, 'A', 1, 107, 76),
(102, 2012, 'A', 1, 119, 15),
(103, 2012, 'A', 1, 112, 19),
(104, 2012, 'A', 1, 124, 28),
(105, 2012, 'A', 1, 94, 73),
(106, 2012, 'A', 1, 105, 23),
(107, 2012, 'A', 1, 102, 74),
(108, 2012, 'A', 1, 116, 58),
(109, 2012, 'A', 1, 96, 39),
(110, 2012, 'A', 1, 125, 52),
(111, 2012, 'A', 1, 100, 44),
(112, 2012, 'A', 1, 106, 23),
(113, 2012, 'A', 1, 121, 72),
(114, 2012, 'A', 1, 108, 32),
(115, 2012, 'B', 1, 108, 32),
(116, 2012, 'A', 1, 126, 9),
(117, 2012, 'A', 1, 95, 39),
(118, 2012, 'A', 1, 91, 41),
(119, 2012, 'A', 1, 111, 42),
(120, 2012, 'B', 1, 111, 42),
(121, 2012, 'A', 1, 115, 72),
(122, 2012, 'A', 1, 103, 1),
(123, 2012, 'A', 1, 127, 44),
(124, 2012, 'A', 1, 101, 71),
(125, 2012, 'A', 1, 109, 67),
(126, 2012, 'A', 1, 117, 52),
(127, 2012, 'A', 1, 120, 37),
(128, 2012, 'A', 1, 122, 37),
(131, 2012, 'A', 1, 114, 79),
(132, 2012, 'A', 1, 60, 19),
(133, 2012, 'A', 1, 58, 11),
(134, 2012, 'B', 1, 58, 11),
(135, 2012, 'A', 1, 55, 16),
(136, 2012, 'B', 1, 55, 16),
(137, 2012, 'A', 1, 54, 40),
(138, 2012, 'A', 1, 47, 1),
(139, 2012, 'A', 1, 72, 54),
(140, 2012, 'B', 1, 72, 54),
(141, 2012, 'A', 1, 69, 50),
(142, 2012, 'B', 1, 69, 50),
(143, 2012, 'A', 1, 46, 7),
(144, 2012, 'B', 1, 46, 7),
(145, 2012, 'A', 1, 57, 6),
(146, 2012, 'B', 1, 57, 6),
(147, 2012, 'A', 1, 37, 66),
(148, 2012, 'B', 1, 37, 66),
(149, 2012, 'A', 1, 39, 73),
(150, 2012, 'A', 1, 50, 30),
(151, 2012, 'A', 1, 49, 72),
(152, 2012, 'A', 1, 74, 5),
(153, 2012, 'A', 1, 59, 76),
(154, 2012, 'A', 1, 68, 15),
(155, 2012, 'A', 1, 45, 21),
(156, 2012, 'A', 1, 42, 47),
(157, 2012, 'A', 1, 43, 21),
(158, 2012, 'A', 1, 44, 5),
(159, 2012, 'A', 1, 52, 30),
(183, 2012, 'A', 1, 104, 83),
(184, 2012, 'A', 1, 123, 81),
(185, 2012, 'A', 1, 129, 82),
(186, 2012, 'C', 1, 108, 84),
(187, 2012, 'C', 1, 115, 84),
(188, 2014, 'TRM', 1, 92, 1),
(1000000, 2014, 'ABC', 1, 35, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `unidade`
--

CREATE TABLE IF NOT EXISTS `unidade` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=8 ;

--
-- Extraindo dados da tabela `unidade`
--

INSERT INTO `unidade` (`id`, `nome`) VALUES
(7, 'Instituto de Informática');

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

--
-- Extraindo dados da tabela `usuario`
--

INSERT INTO `usuario` (`id`, `telefone_celular`, `telefone_residencial`, `telefone_comercial`) VALUES
(1002, '(62) 84227198', '(62) 41417198', '(62) 35211246'),
(1003, NULL, NULL, NULL),
(1004, NULL, NULL, NULL),
(1005, NULL, NULL, NULL),
(1006, NULL, NULL, NULL),
(1007, NULL, NULL, NULL),
(1008, NULL, NULL, NULL),
(1009, '(11) 111111112', '(11) 22222222', '(33) 33333333'),
(1010, NULL, NULL, NULL),
(1011, NULL, NULL, NULL),
(1012, NULL, NULL, NULL),
(1013, NULL, NULL, NULL),
(1015, NULL, NULL, NULL),
(1016, NULL, NULL, NULL),
(1017, NULL, NULL, NULL),
(1018, NULL, NULL, NULL),
(1019, NULL, NULL, NULL),
(1020, NULL, NULL, NULL),
(1021, NULL, NULL, NULL),
(1022, NULL, NULL, NULL),
(1023, '(62) 98547985', '(62) 36547959', '(62) 54987978'),
(1024, NULL, NULL, NULL),
(1025, NULL, NULL, NULL),
(1026, NULL, NULL, NULL),
(1027, NULL, NULL, NULL),
(1036, NULL, NULL, NULL),
(1043, NULL, NULL, NULL),
(1044, NULL, NULL, NULL),
(1045, NULL, NULL, NULL),
(1046, NULL, NULL, NULL),
(1047, NULL, NULL, NULL),
(1048, NULL, NULL, NULL),
(1049, NULL, NULL, NULL),
(1050, NULL, NULL, NULL),
(1051, NULL, NULL, NULL),
(1052, NULL, NULL, NULL),
(1057, NULL, NULL, NULL),
(1058, NULL, NULL, NULL),
(1060, NULL, NULL, NULL),
(1063, NULL, NULL, NULL),
(1066, NULL, NULL, NULL),
(1071, NULL, NULL, NULL),
(1072, NULL, NULL, NULL),
(1076, NULL, NULL, NULL),
(1077, NULL, NULL, NULL),
(1079, NULL, NULL, NULL),
(1081, NULL, NULL, NULL),
(1082, NULL, NULL, NULL),
(1096, NULL, NULL, NULL),
(1141, NULL, NULL, NULL),
(1143, NULL, NULL, NULL),
(1193, NULL, NULL, NULL),
(1390, NULL, NULL, NULL),
(1465, '(62) 92312312', '(62) 45451234', '(62) 32511254'),
(1516, '(62) 81818181', '(62) 41414141', '(62) 32323232'),
(1654, '(62) 87452136', '(62) 32145698', '(62) 36521498'),
(1655, '', '', ''),
(1695, NULL, NULL, NULL),
(1696, NULL, NULL, NULL),
(1765, '(62) 82828282', '(62) 35353535', '(62) 32153251'),
(1860, NULL, NULL, NULL),
(1862, NULL, NULL, NULL),
(2016, NULL, NULL, NULL);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=112 ;

--
-- Extraindo dados da tabela `usuario_perfil`
--

INSERT INTO `usuario_perfil` (`id`, `Perfil_id`, `Usuario_id`, `Curso_id`) VALUES
(1, 1, 1002, NULL),
(7, 1, 1009, NULL),
(10, 1, 1004, 6),
(11, 1, 1005, 10),
(14, 3, 1002, 10),
(17, 2, 1005, 2),
(18, 6, 1007, 4),
(19, 2, 1082, 1),
(21, 5, 1008, NULL),
(22, 2, 1002, 10),
(23, 4, 1655, 10),
(24, 7, 1022, NULL),
(25, 6, 1011, 10),
(26, 5, 1015, NULL),
(27, 2, 1654, 10),
(28, 2, 1655, 10),
(29, 2, 1003, 1),
(30, 4, 1002, 7),
(31, 2, 1004, 10),
(32, 2, 1006, 2),
(33, 2, 1008, 7),
(34, 2, 1009, 10),
(35, 2, 1012, 9),
(36, 2, 1013, 8),
(38, 2, 1016, 1),
(39, 2, 1017, 1),
(40, 2, 1018, 1),
(41, 2, 1019, 1),
(42, 2, 1020, 1),
(43, 2, 1021, 1),
(44, 2, 1007, 5),
(45, 2, 1043, 1),
(46, 2, 1044, 1),
(47, 2, 1045, 1),
(48, 2, 1046, 1),
(49, 2, 1047, 1),
(50, 2, 1048, 1),
(51, 2, 1049, 1),
(52, 2, 1050, 1),
(53, 2, 1051, 1),
(54, 2, 1052, 1),
(55, 1, 1010, NULL),
(56, 6, 1002, 10),
(57, 7, 1006, NULL),
(58, 5, 1077, NULL),
(59, 2, 1081, 1),
(60, 6, 1063, 4),
(61, 6, 1010, 1),
(62, 2, 1066, 1),
(63, 2, 1071, 1),
(64, 2, 1076, 1),
(65, 2, 1096, 1),
(66, 5, 1079, NULL),
(69, 5, 1058, NULL),
(71, 5, 1193, NULL),
(73, 5, 1024, NULL),
(74, 5, 1003, NULL),
(75, 5, 1004, NULL),
(76, 5, 1044, NULL),
(77, 5, 1046, NULL),
(78, 5, 1047, NULL),
(80, 7, 1026, NULL),
(82, 2, 1025, 1),
(83, 2, 1036, 1),
(84, 7, 1002, NULL),
(85, 2, 1023, 1),
(86, 3, 1860, 1),
(87, 6, 1695, 1),
(88, 2, 1027, 1),
(89, 2, 1141, 4),
(90, 2, 1143, 4),
(91, 2, 1390, 5),
(92, 2, 1465, 10),
(93, 2, 1516, 9),
(94, 2, 1765, 7),
(95, 2, 2016, 8),
(96, 3, 1052, 4),
(97, 3, 1057, 9),
(98, 3, 1060, 2),
(99, 3, 1071, 8),
(100, 3, 1072, 6),
(101, 3, 1024, 5),
(102, 4, 1052, 10),
(103, 4, 1060, 2),
(105, 4, 1654, 1),
(106, 4, 1024, 5),
(107, 4, 1071, 8),
(108, 4, 1072, 6),
(109, 6, 1050, 2),
(110, 6, 1696, 6),
(111, 6, 1862, 5);

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
