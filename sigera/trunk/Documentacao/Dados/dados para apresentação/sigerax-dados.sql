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

--
-- Extraindo dados da tabela `configuracao`
--

INSERT INTO `configuracao` (`id`, `data_final_ajustes_matricula`, `data_inicial_ajustes_matricula`, `data_inicial_semestre`, `data_final_semestre`, `ano_corrente`, `semestre_corrente`) VALUES
(1, '2014-07-31', '2012-01-01', '2012-01-01', '2012-07-31', 2012, 1);

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

--
-- Extraindo dados da tabela `unidade`
--

INSERT INTO `unidade` (`id`, `nome`) VALUES
(7, 'Instituto de Informática');

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
(1135, NULL, NULL, NULL),
(1138, NULL, NULL, NULL),
(1140, NULL, NULL, NULL),
(1141, NULL, NULL, NULL),
(1143, NULL, NULL, NULL),
(1193, NULL, NULL, NULL),
(1390, NULL, NULL, NULL),
(1391, NULL, NULL, NULL),
(1465, '(62) 92312312', '(62) 45451234', '(62) 32511254'),
(1516, '(62) 81818181', '(62) 41414141', '(62) 32323232'),
(1654, '(62) 87452136', '(62) 32145698', '(62) 36521498'),
(1655, '(62) 96587487', '(62) 32654178', '(62) 23514567'),
(1695, NULL, NULL, NULL),
(1696, NULL, NULL, NULL),
(1765, '(62) 82828282', '(62) 35353535', '(62) 32153251'),
(1860, NULL, NULL, NULL),
(1862, NULL, NULL, NULL),
(2016, NULL, NULL, NULL);

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
(111, 6, 1862, 5),
(112, 2, 1391, 10),
(113, 2, 1140, 1),
(114, 2, 1135, 1),
(115, 2, 1138, 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
