-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema conectaco
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema conectaco
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `conectaco` DEFAULT CHARACTER SET utf8 ;
USE `conectaco` ;

-- -----------------------------------------------------
-- Table `conectaco`.`Usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conectaco`.`Usuarios` (
  `id_usuario` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `tipo_usuario` ENUM('Turista', 'Negocio') NOT NULL,
  `fecha_registro` DATE NOT NULL,
  PRIMARY KEY (`id_usuario`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `conectaco`.`Turistas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conectaco`.`Turistas` (
  `id_turista` INT NOT NULL,
  `Idioma_preferido` VARCHAR(45) NOT NULL,
  `nivel` INT NOT NULL,
  `puntos_experiencia` INT NOT NULL,
  `fk_id_usuario` INT NOT NULL,
  PRIMARY KEY (`id_turista`),
  INDEX `fk_Turistas_Usuarios1_idx` (`fk_id_usuario` ASC) VISIBLE,
  CONSTRAINT `fk_Turistas_Usuarios1`
    FOREIGN KEY (`fk_id_usuario`)
    REFERENCES `conectaco`.`Usuarios` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `conectaco`.`Negocios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conectaco`.`Negocios` (
  `id_negocio` INT NOT NULL,
  `rfc` VARCHAR(45) NULL,
  `telefono` VARCHAR(45) NULL,
  `estado_verificación` ENUM('PENDIENTE', 'APROBADO', 'RECHAZADO') NULL,
  `fk_id_usuario` INT NOT NULL,
  PRIMARY KEY (`id_negocio`),
  INDEX `fk_Negocios_Usuarios1_idx` (`fk_id_usuario` ASC) VISIBLE,
  CONSTRAINT `fk_Negocios_Usuarios1`
    FOREIGN KEY (`fk_id_usuario`)
    REFERENCES `conectaco`.`Usuarios` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `conectaco`.`Categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conectaco`.`Categoria` (
  `id_categoria` INT NOT NULL,
  `nombre_categoria` VARCHAR(100) NULL,
  PRIMARY KEY (`id_categoria`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `conectaco`.`Lugar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conectaco`.`Lugar` (
  `id_lugar` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(500) NOT NULL,
  `direccion` VARCHAR(100) NOT NULL,
  `latitud` DECIMAL(10,8) NOT NULL,
  `longitud` DECIMAL(10,8) NOT NULL,
  `horario` VARCHAR(45) NOT NULL,
  `costo` VARCHAR(45) NOT NULL,
  `calificacion_promedio` FLOAT NOT NULL,
  `fk_id_categoria` INT NOT NULL,
  `fk_id_negocio` INT NULL,
  PRIMARY KEY (`id_lugar`),
  INDEX `fk_Lugar_Categoria1_idx` (`fk_id_categoria` ASC) VISIBLE,
  INDEX `fk_Lugar_Negocios1_idx` (`fk_id_negocio` ASC) VISIBLE,
  CONSTRAINT `fk_Lugar_Categoria1`
    FOREIGN KEY (`fk_id_categoria`)
    REFERENCES `conectaco`.`Categoria` (`id_categoria`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Lugar_Negocios1`
    FOREIGN KEY (`fk_id_negocio`)
    REFERENCES `conectaco`.`Negocios` (`id_negocio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `conectaco`.`Recuerdo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conectaco`.`Recuerdo` (
  `id_recuerdo` INT NOT NULL,
  `titulo` VARCHAR(45) NOT NULL,
  `reseña` VARCHAR(500) NOT NULL,
  `fecha` DATE NOT NULL,
  `imagen_url` VARCHAR(100) NOT NULL,
  `fk_id_turista` INT NOT NULL,
  `cancion_url` VARCHAR(100) NOT NULL,
  `fk_id_lugar` INT NOT NULL,
  PRIMARY KEY (`id_recuerdo`),
  INDEX `fk_Recuerdo_Turistas1_idx` (`fk_id_turista` ASC) VISIBLE,
  INDEX `fk_Recuerdo_Lugar1_idx` (`fk_id_lugar` ASC) VISIBLE,
  CONSTRAINT `fk_Recuerdo_Turistas1`
    FOREIGN KEY (`fk_id_turista`)
    REFERENCES `conectaco`.`Turistas` (`id_turista`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Recuerdo_Lugar1`
    FOREIGN KEY (`fk_id_lugar`)
    REFERENCES `conectaco`.`Lugar` (`id_lugar`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `conectaco`.`Foto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conectaco`.`Foto` (
  `id_foto` INT NOT NULL,
  `url` VARCHAR(100) NOT NULL,
  `fk_id_recuerdo` INT NOT NULL,
  PRIMARY KEY (`id_foto`),
  INDEX `fk_Foto_Recuerdo1_idx` (`fk_id_recuerdo` ASC) VISIBLE,
  CONSTRAINT `fk_Foto_Recuerdo1`
    FOREIGN KEY (`fk_id_recuerdo`)
    REFERENCES `conectaco`.`Recuerdo` (`id_recuerdo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `conectaco`.`Mascota`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conectaco`.`Mascota` (
  `id_mascota` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `nivel` INT NOT NULL,
  `experiencia` INT NOT NULL,
  `fk_id_turista` INT NOT NULL,
  PRIMARY KEY (`id_mascota`),
  INDEX `fk_Mascota_Turistas1_idx` (`fk_id_turista` ASC) VISIBLE,
  CONSTRAINT `fk_Mascota_Turistas1`
    FOREIGN KEY (`fk_id_turista`)
    REFERENCES `conectaco`.`Turistas` (`id_turista`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `conectaco`.`Misiones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conectaco`.`Misiones` (
  `id_mision` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `descripción` VARCHAR(45) NOT NULL,
  `tipo` VARCHAR(45) NOT NULL,
  `puntos_xp` INT NOT NULL,
  `activa` TINYINT(1) NOT NULL,
  `fk_id_lugar` INT NOT NULL,
  PRIMARY KEY (`id_mision`),
  INDEX `fk_Misiones_Lugar1_idx` (`fk_id_lugar` ASC) VISIBLE,
  CONSTRAINT `fk_Misiones_Lugar1`
    FOREIGN KEY (`fk_id_lugar`)
    REFERENCES `conectaco`.`Lugar` (`id_lugar`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `conectaco`.`Recompensa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conectaco`.`Recompensa` (
  `id_recompensa` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `tipo` VARCHAR(45) NOT NULL,
  `descripcon` VARCHAR(500) NOT NULL,
  `imagen_url` VARCHAR(100) NOT NULL,
  `requisito_xp` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id_recompensa`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `conectaco`.`Test`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conectaco`.`Test` (
  `id_test` INT NOT NULL,
  `pregunta` VARCHAR(100) NOT NULL,
  `respuesta` ENUM('Me gusta', 'No me gusta', 'Me da igual') NOT NULL,
  `fk_id_usuario` INT NOT NULL,
  PRIMARY KEY (`id_test`),
  INDEX `fk_Test_Usuarios1_idx` (`fk_id_usuario` ASC) VISIBLE,
  CONSTRAINT `fk_Test_Usuarios1`
    FOREIGN KEY (`fk_id_usuario`)
    REFERENCES `conectaco`.`Usuarios` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `conectaco`.`Inventario_turista`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conectaco`.`Inventario_turista` (
  `id_inventario` INT NOT NULL,
  `fecha_canje` DATE NOT NULL,
  `estado` ENUM('DISPONIBLE', 'USADO', 'EXPIRADO') NOT NULL,
  `fk_id_turista` INT NOT NULL,
  `fk_id_recompensa` INT NOT NULL,
  PRIMARY KEY (`id_inventario`),
  INDEX `fk_Inventario_turista_Turistas1_idx` (`fk_id_turista` ASC) VISIBLE,
  INDEX `fk_Inventario_turista_Recompensa1_idx` (`fk_id_recompensa` ASC) VISIBLE,
  CONSTRAINT `fk_Inventario_turista_Turistas1`
    FOREIGN KEY (`fk_id_turista`)
    REFERENCES `conectaco`.`Turistas` (`id_turista`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Inventario_turista_Recompensa1`
    FOREIGN KEY (`fk_id_recompensa`)
    REFERENCES `conectaco`.`Recompensa` (`id_recompensa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `conectaco`.`Progreso_mision`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conectaco`.`Progreso_mision` (
  `id_progreso_mision` INT NOT NULL,
  `fk_id_mision` INT NOT NULL,
  `fk_id_turista` INT NOT NULL,
  `estado` ENUM('PENDIENTE', 'EN_PROGRESO', 'COMPLETADA') NOT NULL,
  `fecha_asignacion` DATETIME NOT NULL,
  `fecha_completado` DATETIME NULL,
  PRIMARY KEY (`id_progreso_mision`),
  INDEX `fk_Progreso_mision_Misiones1_idx` (`fk_id_mision` ASC) VISIBLE,
  INDEX `fk_Progreso_mision_Turistas1_idx` (`fk_id_turista` ASC) VISIBLE,
  CONSTRAINT `fk_Progreso_mision_Misiones1`
    FOREIGN KEY (`fk_id_mision`)
    REFERENCES `conectaco`.`Misiones` (`id_mision`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Progreso_mision_Turistas1`
    FOREIGN KEY (`fk_id_turista`)
    REFERENCES `conectaco`.`Turistas` (`id_turista`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;