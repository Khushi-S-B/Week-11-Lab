DROP SCHEMA IF EXISTS `notesdb`;
CREATE SCHEMA IF NOT EXISTS `notesdb` DEFAULT CHARACTER SET latin1;
USE `notesdb`;

-- Create Tables

CREATE TABLE IF NOT EXISTS `notesdb`.`role` (
  `role_id` INT(11) NOT NULL,
  `role_name` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`role_id`));
COMMIT;

CREATE TABLE IF NOT EXISTS `notesdb`.`user` (
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `email` VARCHAR(40) NOT NULL,
  `first_name` VARCHAR(20),
  `last_name` VARCHAR(20),
  `active` BOOLEAN DEFAULT true,
  `role` INT(11) NOT NULL,
   `ResetPasswordUUID` varchar(50),
  PRIMARY KEY (`username`),
  CONSTRAINT `fk_user_role`
    FOREIGN KEY (`role`)
    REFERENCES `notesdb`.`role` (`role_id`)
);
COMMIT;

CREATE TABLE `notesdb`.`note` (
  `note_id` int(11) NOT NULL AUTO_INCREMENT,
  `datecreated` datetime DEFAULT CURRENT_TIMESTAMP,
  `title` varchar(30) NOT NULL,
  `contents` varchar(20000) CHARACTER SET utf8 NOT NULL,
  `owner` varchar(40) NOT NULL,
  PRIMARY KEY (`note_id`),
  KEY `FK_Note_User` (`owner`),
  CONSTRAINT `FK_Note_User`
    FOREIGN KEY (`owner`)
    REFERENCES `notesdb`.`user` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);
COMMIT;

-- Insert Default Data

INSERT INTO `notesdb`.`role`(`role_id`,`role_name`)
VALUES
(1, 'Administrator'),
(2, 'Moderator'),
(3, 'Regular User');
COMMIT;

INSERT INTO `notesdb`.`user`(`username`,`password`,`email`,`first_name`,`last_name`,`role`)
VALUES
('admin','password','sait.cprg.352+admin@gmail.com',null, null,1),
('anne','password','sait.cprg.352+anne@gmail.com','Anne','Teak',3),
('matilda','password','sait.cprg.352+matilda@gmail.com','Matilda','Keybroke',3),
('jerry','password','sait.cprg.352+jerry@gmail.com','Jerry','Atrick',2),
('anita','password','sait.cprg.352+anita@gmail.com','Anita','Knapp',3),
('billy','password','hereshtesting@gmail.com','Billy','Maizear',3),
('patty','password','sait.cprg.352+patty@gmail.com','Patrick','O''Furniture',3);
COMMIT;

INSERT INTO `notesdb`.`note`(`title`,`contents`,`owner`)
VALUES
('Quote #1', 'Writing is nature''s way of letting you know how sloppy your thinking is.', 'anne'),
('Another Quote', '"Java is to JavaScript as ham is to hamster." -  Jeremy Keith', 'anne' ),
('Matilda''s Note', 'I do not want Anne reading this note. She stole my broccoli casserole recipe 8 years ago and claimed it was hers.','matilda');
COMMIT;