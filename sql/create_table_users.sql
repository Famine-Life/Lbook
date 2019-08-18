USE ebook;
CREATE TABLE `users` (
  `account` VARCHAR(50) NOT NULL,
  `password` VARCHAR(50) NULL,
  `name` VARCHAR(50) NULL,
  `allowed` TINYINT NULL,
  `ismanager` TINYINT NULL,
	`mail` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`account`));