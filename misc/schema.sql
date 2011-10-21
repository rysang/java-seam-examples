SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `notifier` ;
CREATE SCHEMA IF NOT EXISTS `notifier` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `notifier` ;

-- -----------------------------------------------------
-- Table `notifier`.`notif_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `notifier`.`notif_user` ;

CREATE  TABLE IF NOT EXISTS `notifier`.`notif_user` (
  `login` VARCHAR(255) NOT NULL ,
  `password` VARCHAR(10) NOT NULL ,
  `first_name` VARCHAR(100) NULL ,
  `last_name` VARCHAR(100) NULL ,
  `active` BIT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (`login`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `notifier`.`notif_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `notifier`.`notif_role` ;

CREATE  TABLE IF NOT EXISTS `notifier`.`notif_role` (
  `name` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`name`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `notifier`.`user2role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `notifier`.`user2role` ;

CREATE  TABLE IF NOT EXISTS `notifier`.`user2role` (
  `user_login` VARCHAR(255) NOT NULL ,
  `role_name` VARCHAR(100) NOT NULL ,
  INDEX `u2r_user_login_fk` (`user_login` ASC) ,
  INDEX `u2r_role_name_fk` (`role_name` ASC) ,
  UNIQUE INDEX `u2r_uq` (`user_login` ASC, `role_name` ASC) ,
  CONSTRAINT `u2r_user_login_fk`
    FOREIGN KEY (`user_login` )
    REFERENCES `notifier`.`notif_user` (`login` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `u2r_role_name_fk`
    FOREIGN KEY (`role_name` )
    REFERENCES `notifier`.`notif_role` (`name` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `notifier`.`email_account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `notifier`.`email_account` ;

CREATE  TABLE IF NOT EXISTS `notifier`.`email_account` (
  `id` INT(11)  NOT NULL AUTO_INCREMENT ,
  `email_user` VARCHAR(255) NOT NULL ,
  `description` TEXT NULL ,
  `server_name` VARCHAR(150) NOT NULL ,
  `port` INT NOT NULL ,
  `user_login` VARCHAR(255) NOT NULL ,
  INDEX `em_acc_user_login_fk` (`user_login` ASC) ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `em_acc_user_login_fk`
    FOREIGN KEY (`user_login` )
    REFERENCES `notifier`.`notif_user` (`login` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `notifier`.`user_preferences`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `notifier`.`user_preferences` ;

CREATE  TABLE IF NOT EXISTS `notifier`.`user_preferences` (
  `id` INT(11)  NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL ,
  `value` VARCHAR(255) NULL ,
  `user_login` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `unique_prop_idx` USING BTREE (`name` ASC, `id` ASC) ,
  INDEX `up_usr_fk` (`user_login` ASC) ,
  CONSTRAINT `up_usr_fk`
    FOREIGN KEY (`user_login` )
    REFERENCES `notifier`.`notif_user` (`login` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `notifier`.`notification`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `notifier`.`notification` ;

CREATE  TABLE IF NOT EXISTS `notifier`.`notification` (
  `id` INT(11)  NOT NULL AUTO_INCREMENT ,
  `email_account_id` INT(11)  NOT NULL ,
  `title` VARCHAR(300) NOT NULL ,
  `body` LONGTEXT NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `notif_email_acc_fk` (`email_account_id` ASC) ,
  CONSTRAINT `notif_email_acc_fk`
    FOREIGN KEY (`email_account_id` )
    REFERENCES `notifier`.`email_account` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `notifier`.`google_cal_acc`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `notifier`.`google_cal_acc` ;

CREATE  TABLE IF NOT EXISTS `notifier`.`google_cal_acc` (
  `id` INT(11)  NOT NULL AUTO_INCREMENT ,
  `user_login` VARCHAR(255) NOT NULL ,
  `google_login` VARCHAR(255) NOT NULL ,
  `google_password` VARCHAR(150) NOT NULL ,
  `url` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `gca_nurs_fk` (`user_login` ASC) ,
  CONSTRAINT `gca_nurs_fk`
    FOREIGN KEY (`user_login` )
    REFERENCES `notifier`.`notif_user` (`login` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
