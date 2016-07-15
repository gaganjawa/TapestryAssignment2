CREATE database IF NOT EXISTS tapestrydb;
DROP TABLE IF EXISTS tapestrydb.user;
CREATE TABLE tapestrydb.user (
  id INT NOT NULL AUTO_INCREMENT,
  firstName VARCHAR(45) NULL,
  lastName VARCHAR(45) NULL,
  username VARCHAR(45) NULL,
  password VARCHAR(45) NULL,
  email VARCHAR(45) NULL,
  mobile VARCHAR(45) NULL,
  enabled TINYINT NULL,
  locked TINYINT NULL,
  PRIMARY KEY (id, email))
ENGINE = InnoDB;