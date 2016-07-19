CREATE TABLE `tapestrydb`.`user` (
  `id` INT NOT NULL,
  `firstName` VARCHAR(45) NULL,
  `lastName` VARCHAR(45) NULL,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `email` VARCHAR(45) NOT NULL,
  `mobile` VARCHAR(45) NULL,
  `Userscol` VARCHAR(45) NULL,
  PRIMARY KEY (`id`, `email`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC));