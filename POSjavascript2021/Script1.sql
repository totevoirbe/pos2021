--<ScriptOptions statementTerminator=";"/>

ALTER TABLE `posdb`.`tutorials_tbl` DROP PRIMARY KEY;

DROP TABLE `posdb`.`tutorials_tbl`;

CREATE TABLE `posdb`.`tutorials_tbl` (
	`tutorial_id` INT NOT NULL,
	`tutorial_title` VARCHAR(100) NOT NULL,
	`tutorial_author` VARCHAR(40) NOT NULL,
	`submission_date` DATE,
	PRIMARY KEY (`tutorial_id`)
) ENGINE=InnoDB;

