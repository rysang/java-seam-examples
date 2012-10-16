SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `auktion` ;
CREATE SCHEMA IF NOT EXISTS `auktion` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
SHOW WARNINGS;
USE `auktion` ;

-- -----------------------------------------------------
-- Table `auktion`.`auk_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `auktion`.`auk_user` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `auktion`.`auk_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `username` VARCHAR(120) NOT NULL ,
  `password` VARCHAR(150) NOT NULL ,
  `enabled` BIT NOT NULL DEFAULT 1 ,
  `account_non_expired` BIT NOT NULL DEFAULT 1 ,
  `account_non_locked` BIT NOT NULL DEFAULT 1 ,
  `credentials_non_expired` BIT NOT NULL DEFAULT 1 ,
  `created_date` TIMESTAMP NOT NULL ,
  `created_by` VARCHAR(120) NOT NULL DEFAULT 'system' ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `auktion`.`auk_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `auktion`.`auk_role` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `auktion`.`auk_role` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `authority` VARCHAR(145) NOT NULL ,
  `created_date` TIMESTAMP NOT NULL ,
  `created_by` VARCHAR(120) NOT NULL DEFAULT 'system' ,
  `private` BIT NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `authority_UNIQUE` (`authority` ASC) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `auktion`.`auk_role_2_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `auktion`.`auk_role_2_user` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `auktion`.`auk_role_2_user` (
  `id_role` BIGINT NOT NULL ,
  `id_user` BIGINT NOT NULL ,
  INDEX `auk_role_2_usr_role_fk` (`id_role` ASC) ,
  INDEX `auk_role_2_usr_usr_fk` (`id_user` ASC) ,
  UNIQUE INDEX `auk_role_2_usr_unq` (`id_role` ASC, `id_user` ASC) ,
  CONSTRAINT `auk_role_2_usr_role_fk`
    FOREIGN KEY (`id_role` )
    REFERENCES `auktion`.`auk_role` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `auk_role_2_usr_user_fk`
    FOREIGN KEY (`id_user` )
    REFERENCES `auktion`.`auk_user` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `auktion`.`auk_product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `auktion`.`auk_product` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `auktion`.`auk_product` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  `description` LONGTEXT NOT NULL ,
  `created_date` TIMESTAMP NOT NULL ,
  `created_by` VARCHAR(120) NOT NULL DEFAULT 'system' ,
  `price` INT NOT NULL ,
  `currency` VARCHAR(10) NOT NULL DEFAULT 'EUR' ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `auktion`.`auk_right`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `auktion`.`auk_right` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `auktion`.`auk_right` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `role_id` BIGINT NOT NULL ,
  `product_id` BIGINT NOT NULL ,
  `read` BIT NOT NULL DEFAULT 0 ,
  `write` BIT NOT NULL DEFAULT 0 ,
  UNIQUE INDEX `auk_rights_unq` (`role_id` ASC, `product_id` ASC) ,
  INDEX `auk_rights_prod_fk` (`product_id` ASC) ,
  INDEX `auk_rights_role_fk` (`role_id` ASC) ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `auk_rights_prod_fk`
    FOREIGN KEY (`product_id` )
    REFERENCES `auktion`.`auk_product` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `auk_rights_role_fk`
    FOREIGN KEY (`role_id` )
    REFERENCES `auktion`.`auk_role` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `auktion`.`auk_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `auktion`.`auk_category` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `auktion`.`auk_category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(120) NOT NULL ,
  `created_date` TIMESTAMP NOT NULL ,
  `created_by` VARCHAR(120) NOT NULL DEFAULT 'system' ,
  `description` LONGTEXT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `auktion`.`auk_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `auktion`.`auk_type` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `auktion`.`auk_type` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(120) NOT NULL ,
  `category_id` BIGINT NOT NULL ,
  `created_date` TIMESTAMP NOT NULL ,
  `created_by` VARCHAR(120) NOT NULL DEFAULT 'system' ,
  `description` LONGTEXT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `auk_type_2_categ_fk` (`category_id` ASC) ,
  UNIQUE INDEX `unique_type_name` (`category_id` ASC, `name` ASC) ,
  CONSTRAINT `auk_type_2_categ_fk`
    FOREIGN KEY (`category_id` )
    REFERENCES `auktion`.`auk_category` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `auktion`.`auk_product_2_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `auktion`.`auk_product_2_type` ;

SHOW WARNINGS;
CREATE  TABLE IF NOT EXISTS `auktion`.`auk_product_2_type` (
  `product_id` BIGINT NOT NULL ,
  `type_id` BIGINT NOT NULL ,
  UNIQUE INDEX `auk_product_2_pk` (`product_id` ASC, `type_id` ASC) ,
  INDEX `auk_product_2_type_p_fk` (`product_id` ASC) ,
  INDEX `auk_product_2_type_t_fk` (`type_id` ASC) ,
  CONSTRAINT `auk_product_2_type_p_fk`
    FOREIGN KEY (`product_id` )
    REFERENCES `auktion`.`auk_product` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `auk_product_2_type_t_fk`
    FOREIGN KEY (`type_id` )
    REFERENCES `auktion`.`auk_type` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
