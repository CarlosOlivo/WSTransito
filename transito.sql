SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `transito`
--
CREATE DATABASE IF NOT EXISTS `transito` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci;
USE `transito`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `aseguradora`
--

CREATE TABLE `aseguradora` (
  `idAseguradora` int(11) NOT NULL,
  `aseguradora` varchar(20) COLLATE utf8mb4_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- RELACIONES PARA LA TABLA `aseguradora`:
--

--
-- Volcado de datos para la tabla `aseguradora`
--

INSERT INTO `aseguradora` (`idAseguradora`, `aseguradora`) VALUES
(1, 'GNP'),
(2, 'MAPFRE'),
(3, 'AXA'),
(4, 'ZURICH'),
(5, 'Qualitas'),
(6, 'HDI'),
(7, 'ABA'),
(8, 'Atlas'),
(9, 'MetLife'),
(10, 'BBVA Bancomer');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cargo`
--

CREATE TABLE `cargo` (
  `idCargo` int(11) NOT NULL,
  `cargo` varchar(20) COLLATE utf8mb4_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- RELACIONES PARA LA TABLA `cargo`:
--

--
-- Volcado de datos para la tabla `cargo`
--

INSERT INTO `cargo` (`idCargo`, `cargo`) VALUES
(1, 'Administrativo'),
(2, 'Perito'),
(3, 'Soporte'),
(4, 'Atención');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `conductor`
--

CREATE TABLE `conductor` (
  `idConductor` int(11) NOT NULL,
  `nombre` varchar(30) COLLATE utf8mb4_spanish_ci NOT NULL,
  `apellidoPaterno` varchar(45) COLLATE utf8mb4_spanish_ci NOT NULL,
  `apellidoMaterno` varchar(45) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `fechaNacimiento` date NOT NULL,
  `numLicencia` int(11) NOT NULL,
  `numCelular` varchar(10) COLLATE utf8mb4_spanish_ci NOT NULL,
  `contrasenia` varchar(18) COLLATE utf8mb4_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- RELACIONES PARA LA TABLA `conductor`:
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dictamen`
--

CREATE TABLE `dictamen` (
  `idDictamen` int(11) NOT NULL,
  `descripcion` varchar(250) COLLATE utf8mb4_spanish_ci NOT NULL,
  `fechaHora` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `idPersonal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- RELACIONES PARA LA TABLA `dictamen`:
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `implicado`
--

CREATE TABLE `implicado` (
  `idImplicado` int(11) NOT NULL,
  `idAseguradora` int(11) DEFAULT NULL,
  `nombre` varchar(50) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `placas` varchar(10) COLLATE utf8mb4_spanish_ci NOT NULL,
  `poliza` varchar(20) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `marca` varchar(20) COLLATE utf8mb4_spanish_ci NOT NULL,
  `modelo` varchar(20) COLLATE utf8mb4_spanish_ci NOT NULL,
  `color` varchar(25) COLLATE utf8mb4_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- RELACIONES PARA LA TABLA `implicado`:
--   `idAseguradora`
--       `aseguradora` -> `idAseguradora`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personal`
--

CREATE TABLE `personal` (
  `idPersonal` int(11) NOT NULL,
  `idCargo` int(11) NOT NULL,
  `nombre` varchar(128) COLLATE utf8mb4_spanish_ci NOT NULL,
  `usuario` varchar(10) COLLATE utf8mb4_spanish_ci NOT NULL,
  `contrasenia` varchar(18) COLLATE utf8mb4_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- RELACIONES PARA LA TABLA `personal`:
--   `idCargo`
--       `cargo` -> `idCargo`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reporte`
--

CREATE TABLE `reporte` (
  `idReporte` int(11) NOT NULL,
  `idConductor` int(11) NOT NULL,
  `idImplicado` int(11) NOT NULL,
  `idDictamen` int(11) DEFAULT NULL,
  `imgs` tinyint(1) NOT NULL DEFAULT '0',
  `placas` varchar(10) COLLATE utf8mb4_spanish_ci NOT NULL,
  `latitud` double NOT NULL,
  `longitud` double NOT NULL,
  `fechaHora` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- RELACIONES PARA LA TABLA `reporte`:
--   `idConductor`
--       `conductor` -> `idConductor`
--   `idImplicado`
--       `implicado` -> `idImplicado`
--   `idDictamen`
--       `dictamen` -> `idDictamen`
--   `placas`
--       `vehiculo` -> `placas`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculo`
--

CREATE TABLE `vehiculo` (
  `placas` varchar(10) COLLATE utf8mb4_spanish_ci NOT NULL,
  `idConductor` int(11) NOT NULL,
  `idAseguradora` int(11) DEFAULT NULL,
  `marca` varchar(20) COLLATE utf8mb4_spanish_ci NOT NULL,
  `modelo` varchar(20) COLLATE utf8mb4_spanish_ci NOT NULL,
  `anio` int(4) NOT NULL,
  `color` varchar(25) COLLATE utf8mb4_spanish_ci NOT NULL,
  `poliza` varchar(20) COLLATE utf8mb4_spanish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- RELACIONES PARA LA TABLA `vehiculo`:
--   `idConductor`
--       `conductor` -> `idConductor`
--   `idAseguradora`
--       `aseguradora` -> `idAseguradora`
--

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `aseguradora`
--
ALTER TABLE `aseguradora`
  ADD PRIMARY KEY (`idAseguradora`) USING BTREE;

--
-- Indices de la tabla `cargo`
--
ALTER TABLE `cargo`
  ADD PRIMARY KEY (`idCargo`);

--
-- Indices de la tabla `conductor`
--
ALTER TABLE `conductor`
  ADD PRIMARY KEY (`idConductor`);

--
-- Indices de la tabla `dictamen`
--
ALTER TABLE `dictamen`
  ADD PRIMARY KEY (`idDictamen`) USING BTREE;

--
-- Indices de la tabla `implicado`
--
ALTER TABLE `implicado`
  ADD PRIMARY KEY (`idImplicado`) USING BTREE,
  ADD KEY `idAseguradora` (`idAseguradora`);

--
-- Indices de la tabla `personal`
--
ALTER TABLE `personal`
  ADD PRIMARY KEY (`idPersonal`),
  ADD UNIQUE KEY `usuario` (`usuario`),
  ADD KEY `idCargo` (`idCargo`);

--
-- Indices de la tabla `reporte`
--
ALTER TABLE `reporte`
  ADD PRIMARY KEY (`idReporte`) USING BTREE,
  ADD KEY `idDictamen` (`idDictamen`) USING BTREE,
  ADD KEY `reporte_ibfk_2` (`idImplicado`),
  ADD KEY `reporte_ibfk_1` (`idConductor`),
  ADD KEY `reporte_ibfk_3` (`placas`) USING BTREE;

--
-- Indices de la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  ADD PRIMARY KEY (`placas`),
  ADD KEY `idConductor` (`idConductor`),
  ADD KEY `idAseguradora` (`idAseguradora`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `aseguradora`
--
ALTER TABLE `aseguradora`
  MODIFY `idAseguradora` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `cargo`
--
ALTER TABLE `cargo`
  MODIFY `idCargo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `conductor`
--
ALTER TABLE `conductor`
  MODIFY `idConductor` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `dictamen`
--
ALTER TABLE `dictamen`
  MODIFY `idDictamen` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `implicado`
--
ALTER TABLE `implicado`
  MODIFY `idImplicado` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `personal`
--
ALTER TABLE `personal`
  MODIFY `idPersonal` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `reporte`
--
ALTER TABLE `reporte`
  MODIFY `idReporte` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `implicado`
--
ALTER TABLE `implicado`
  ADD CONSTRAINT `implicado_ibfk_1` FOREIGN KEY (`idAseguradora`) REFERENCES `aseguradora` (`idAseguradora`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `personal`
--
ALTER TABLE `personal`
  ADD CONSTRAINT `personal_ibfk_1` FOREIGN KEY (`idCargo`) REFERENCES `cargo` (`idCargo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `reporte`
--
ALTER TABLE `reporte`
  ADD CONSTRAINT `reporte_ibfk_1` FOREIGN KEY (`idConductor`) REFERENCES `conductor` (`idConductor`) ON UPDATE NO ACTION,
  ADD CONSTRAINT `reporte_ibfk_2` FOREIGN KEY (`idImplicado`) REFERENCES `implicado` (`idImplicado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `reporte_ibfk_3` FOREIGN KEY (`idDictamen`) REFERENCES `dictamen` (`idDictamen`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `reporte_ibfk_4` FOREIGN KEY (`placas`) REFERENCES `vehiculo` (`placas`) ON UPDATE NO ACTION;

--
-- Filtros para la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  ADD CONSTRAINT `vehiculo_ibfk_1` FOREIGN KEY (`idConductor`) REFERENCES `conductor` (`idConductor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `vehiculo_ibfk_2` FOREIGN KEY (`idAseguradora`) REFERENCES `aseguradora` (`idAseguradora`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;