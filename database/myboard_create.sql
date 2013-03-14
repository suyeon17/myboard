SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `myboard` ;
CREATE SCHEMA IF NOT EXISTS `myboard` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `myboard` ;

-- -----------------------------------------------------
-- Table `myboard`.`Account_Permissions`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myboard`.`Account_Permissions` ;

CREATE  TABLE IF NOT EXISTS `myboard`.`Account_Permissions` (
  `permission_id` INT NOT NULL ,
  `permission_name` VARCHAR(20) NOT NULL ,
  PRIMARY KEY (`permission_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myboard`.`Department`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myboard`.`Department` ;

CREATE  TABLE IF NOT EXISTS `myboard`.`Department` (
  `dept_id` INT NOT NULL ,
  `department_name` MEDIUMTEXT NOT NULL ,
  PRIMARY KEY (`dept_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myboard`.`Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myboard`.`Users` ;

CREATE  TABLE IF NOT EXISTS `myboard`.`Users` (
  `uid` VARCHAR(8) NOT NULL ,
  `first_name` VARCHAR(35) NOT NULL ,
  `last_name` VARCHAR(35) NOT NULL ,
  `department` INT NOT NULL ,
  `password` VARCHAR(20) NOT NULL ,
  `permission_id` INT NOT NULL ,
  `creation_date` DATETIME NOT NULL ,
  `last_login` DATETIME NOT NULL ,
  `private_directory` VARCHAR(45) NOT NULL ,
  `active` TINYINT(1) NOT NULL ,
  `email_address` VARCHAR(45) NULL ,
  PRIMARY KEY (`uid`) ,
  INDEX `permission_id` (`permission_id` ASC) ,
  INDEX `dept_id_ref` (`department` ASC) ,
  CONSTRAINT `permission_id`
    FOREIGN KEY (`permission_id` )
    REFERENCES `myboard`.`Account_Permissions` (`permission_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `dept_id_ref`
    FOREIGN KEY (`department` )
    REFERENCES `myboard`.`Department` (`dept_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myboard`.`Course_Info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myboard`.`Course_Info` ;

CREATE  TABLE IF NOT EXISTS `myboard`.`Course_Info` (
  `course_info_id` INT NOT NULL AUTO_INCREMENT ,
  `course_id` VARCHAR(10) NOT NULL ,
  `course_name` VARCHAR(45) NOT NULL ,
  `course_description` MEDIUMTEXT NOT NULL ,
  `department` INT NOT NULL ,
  `credits` INT NOT NULL ,
  PRIMARY KEY (`course_info_id`) ,
  INDEX `dept_ref` (`department` ASC) ,
  CONSTRAINT `dept_ref`
    FOREIGN KEY (`department` )
    REFERENCES `myboard`.`Department` (`dept_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myboard`.`Course_Section`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myboard`.`Course_Section` ;

CREATE  TABLE IF NOT EXISTS `myboard`.`Course_Section` (
  `course_section_id` INT NOT NULL AUTO_INCREMENT ,
  `course_info_id` INT NOT NULL ,
  `section` INT NOT NULL ,
  PRIMARY KEY (`course_section_id`) ,
  INDEX `course_info_id` (`course_info_id` ASC) ,
  CONSTRAINT `course_info_id`
    FOREIGN KEY (`course_info_id` )
    REFERENCES `myboard`.`Course_Info` (`course_info_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myboard`.`Semester`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myboard`.`Semester` ;

CREATE  TABLE IF NOT EXISTS `myboard`.`Semester` (
  `course_semester_id` INT NOT NULL AUTO_INCREMENT ,
  `semester_name` VARCHAR(45) NOT NULL ,
  `start_date` DATETIME NOT NULL ,
  `end_date` DATETIME NOT NULL ,
  PRIMARY KEY (`course_semester_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myboard`.`Courses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myboard`.`Courses` ;

CREATE  TABLE IF NOT EXISTS `myboard`.`Courses` (
  `course_id` INT NOT NULL AUTO_INCREMENT ,
  `course_section_id` INT NOT NULL ,
  `course_semester_id` INT NOT NULL ,
  `course_root_directory` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`course_id`) ,
  INDEX `course_section_id` (`course_section_id` ASC) ,
  INDEX `course_semester_id` (`course_semester_id` ASC) ,
  CONSTRAINT `course_section_id`
    FOREIGN KEY (`course_section_id` )
    REFERENCES `myboard`.`Course_Section` (`course_section_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `course_semester_id`
    FOREIGN KEY (`course_semester_id` )
    REFERENCES `myboard`.`Semester` (`course_semester_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myboard`.`Course_Roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myboard`.`Course_Roles` ;

CREATE  TABLE IF NOT EXISTS `myboard`.`Course_Roles` (
  `role_id` INT NOT NULL ,
  `role_name` VARCHAR(20) NOT NULL ,
  PRIMARY KEY (`role_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myboard`.`Course_Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myboard`.`Course_Users` ;

CREATE  TABLE IF NOT EXISTS `myboard`.`Course_Users` (
  `course_uid` INT NOT NULL AUTO_INCREMENT ,
  `course_id` INT NOT NULL ,
  `uid` VARCHAR(8) NOT NULL ,
  `role_id` INT NOT NULL ,
  PRIMARY KEY (`course_uid`) ,
  INDEX `course_id` (`course_id` ASC) ,
  INDEX `uid` (`uid` ASC) ,
  INDEX `role` (`role_id` ASC) ,
  CONSTRAINT `course_id`
    FOREIGN KEY (`course_id` )
    REFERENCES `myboard`.`Courses` (`course_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `uid`
    FOREIGN KEY (`uid` )
    REFERENCES `myboard`.`Users` (`uid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `role`
    FOREIGN KEY (`role_id` )
    REFERENCES `myboard`.`Course_Roles` (`role_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myboard`.`Assignment_Types`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myboard`.`Assignment_Types` ;

CREATE  TABLE IF NOT EXISTS `myboard`.`Assignment_Types` (
  `assignment_type_id` INT NOT NULL ,
  `assignment_type_name` VARCHAR(45) NULL ,
  PRIMARY KEY (`assignment_type_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myboard`.`Assignments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myboard`.`Assignments` ;

CREATE  TABLE IF NOT EXISTS `myboard`.`Assignments` (
  `assignment_id` INT NOT NULL AUTO_INCREMENT ,
  `course_id` INT NOT NULL ,
  `title` VARCHAR(45) NOT NULL ,
  `description` MEDIUMTEXT NOT NULL ,
  `total_points` INT NOT NULL ,
  `start_date` DATETIME NOT NULL ,
  `end_date` DATETIME NOT NULL ,
  `late_submission_date` DATETIME NULL ,
  `timer` INT NULL ,
  `num_attempts` INT NOT NULL ,
  `assignment_type_id` INT NOT NULL ,
  `assignment_filename` VARCHAR(45) NOT NULL ,
  `creator_uid` INT NOT NULL ,
  `visible_date` DATETIME NOT NULL ,
  PRIMARY KEY (`assignment_id`) ,
  INDEX `assignment_typeid` (`assignment_type_id` ASC) ,
  INDEX `courseid` (`course_id` ASC) ,
  INDEX `creatoruid` (`creator_uid` ASC) ,
  CONSTRAINT `assignment_typeid`
    FOREIGN KEY (`assignment_type_id` )
    REFERENCES `myboard`.`Assignment_Types` (`assignment_type_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `courseid`
    FOREIGN KEY (`course_id` )
    REFERENCES `myboard`.`Courses` (`course_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `creatoruid`
    FOREIGN KEY (`creator_uid` )
    REFERENCES `myboard`.`Course_Users` (`course_uid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myboard`.`Assignment_Submission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myboard`.`Assignment_Submission` ;

CREATE  TABLE IF NOT EXISTS `myboard`.`Assignment_Submission` (
  `submission_id` INT NOT NULL AUTO_INCREMENT ,
  `assignment_id` INT NOT NULL ,
  `course_uid` INT NOT NULL ,
  `date_started` DATETIME NOT NULL ,
  `date_submitted` DATETIME NULL ,
  `points_earned` INT NULL ,
  `submission_filename` VARCHAR(45) NULL ,
  `comments` MEDIUMTEXT NULL ,
  PRIMARY KEY (`submission_id`) ,
  INDEX `assignment_id` (`assignment_id` ASC) ,
  INDEX `course_uid` (`course_uid` ASC) ,
  CONSTRAINT `assignment_id`
    FOREIGN KEY (`assignment_id` )
    REFERENCES `myboard`.`Assignments` (`assignment_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `course_uid`
    FOREIGN KEY (`course_uid` )
    REFERENCES `myboard`.`Course_Users` (`course_uid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myboard`.`Message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myboard`.`Message` ;

CREATE  TABLE IF NOT EXISTS `myboard`.`Message` (
  `message_id` INT NOT NULL AUTO_INCREMENT ,
  `parent_id` INT NULL ,
  `from_uid` INT NOT NULL ,
  `course_id` INT NOT NULL ,
  `title` VARCHAR(45) NOT NULL ,
  `message` MEDIUMTEXT NOT NULL ,
  `attachment_filename` VARCHAR(45) NULL ,
  PRIMARY KEY (`message_id`) ,
  INDEX `from_id` (`from_uid` ASC) ,
  INDEX `cid` (`course_id` ASC) ,
  CONSTRAINT `from_id`
    FOREIGN KEY (`from_uid` )
    REFERENCES `myboard`.`Course_Users` (`course_uid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `cid`
    FOREIGN KEY (`course_id` )
    REFERENCES `myboard`.`Courses` (`course_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myboard`.`Announcements`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myboard`.`Announcements` ;

CREATE  TABLE IF NOT EXISTS `myboard`.`Announcements` (
  `announcement_id` INT NOT NULL AUTO_INCREMENT ,
  `course_id` INT NOT NULL ,
  `title` VARCHAR(50) NOT NULL ,
  `description` MEDIUMTEXT NOT NULL ,
  `date_posted` DATETIME NOT NULL ,
  `creator_uid` INT NOT NULL ,
  PRIMARY KEY (`announcement_id`) ,
  INDEX `from_course_id` (`course_id` ASC) ,
  INDEX `from_creator_uid` (`creator_uid` ASC) ,
  CONSTRAINT `from_course_id`
    FOREIGN KEY (`course_id` )
    REFERENCES `myboard`.`Courses` (`course_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `from_creator_uid`
    FOREIGN KEY (`creator_uid` )
    REFERENCES `myboard`.`Course_Users` (`course_uid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myboard`.`Message_Recipient`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myboard`.`Message_Recipient` ;

CREATE  TABLE IF NOT EXISTS `myboard`.`Message_Recipient` (
  `message_recipient_id` INT NOT NULL AUTO_INCREMENT ,
  `message_id` INT NOT NULL ,
  `course_uid` INT NOT NULL ,
  `deleted` TINYINT(1) NOT NULL ,
  `is_new` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`message_recipient_id`) ,
  INDEX `message_id_ref` (`message_id` ASC) ,
  INDEX `course_uid_ref` (`course_uid` ASC) ,
  CONSTRAINT `message_id_ref`
    FOREIGN KEY (`message_id` )
    REFERENCES `myboard`.`Message` (`message_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `course_uid_ref`
    FOREIGN KEY (`course_uid` )
    REFERENCES `myboard`.`Course_Users` (`course_uid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myboard`.`Additional_Calendar_Entries`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myboard`.`Additional_Calendar_Entries` ;

CREATE  TABLE IF NOT EXISTS `myboard`.`Additional_Calendar_Entries` (
  `entry_id` INT NOT NULL AUTO_INCREMENT ,
  `uid` VARCHAR(8) NOT NULL ,
  `title` VARCHAR(45) NOT NULL ,
  `description` MEDIUMTEXT NOT NULL ,
  `date` DATETIME NOT NULL ,
  PRIMARY KEY (`entry_id`) ,
  INDEX `uid_ref` (`uid` ASC) ,
  CONSTRAINT `uid_ref`
    FOREIGN KEY (`uid` )
    REFERENCES `myboard`.`Users` (`uid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myboard`.`Additional_Grade_Entries`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myboard`.`Additional_Grade_Entries` ;

CREATE  TABLE IF NOT EXISTS `myboard`.`Additional_Grade_Entries` (
  `entry_id` INT NOT NULL AUTO_INCREMENT ,
  `course_uid` INT NOT NULL ,
  `points_earned` INT NULL ,
  `total_points` INT NOT NULL ,
  `title` VARCHAR(45) NOT NULL ,
  `comments` MEDIUMTEXT NULL ,
  `created_by` INT NOT NULL ,
  PRIMARY KEY (`entry_id`) ,
  INDEX `for_course_uid_ref` (`course_uid` ASC) ,
  INDEX `created_by_ref` (`created_by` ASC) ,
  CONSTRAINT `for_course_uid_ref`
    FOREIGN KEY (`course_uid` )
    REFERENCES `myboard`.`Course_Users` (`course_uid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `created_by_ref`
    FOREIGN KEY (`created_by` )
    REFERENCES `myboard`.`Course_Users` (`course_uid` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `myboard`.`Course_Material`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `myboard`.`Course_Material` ;

CREATE  TABLE IF NOT EXISTS `myboard`.`Course_Material` (
  `course_material_id` INT NOT NULL AUTO_INCREMENT ,
  `title` VARCHAR(45) NOT NULL ,
  `description` MEDIUMTEXT NULL ,
  `upload_date` DATETIME NOT NULL ,
  `creator_uid` INT NOT NULL ,
  `material_filename` VARCHAR(45) NOT NULL ,
  `course_id` INT NOT NULL ,
  PRIMARY KEY (`course_material_id`) ,
  INDEX `mat_course_id_ref` (`course_id` ASC) ,
  INDEX `mat_creator_uid_ref` (`creator_uid` ASC) ,
  CONSTRAINT `mat_course_id_ref`
    FOREIGN KEY (`course_id` )
    REFERENCES `myboard`.`Courses` (`course_id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `mat_creator_uid_ref`
    FOREIGN KEY (`creator_uid` )
    REFERENCES `myboard`.`Course_Users` (`course_uid` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `myboard`.`Account_Permissions`
-- -----------------------------------------------------
START TRANSACTION;
USE `myboard`;
INSERT INTO `myboard`.`Account_Permissions` (`permission_id`, `permission_name`) VALUES (0, 'admin');
INSERT INTO `myboard`.`Account_Permissions` (`permission_id`, `permission_name`) VALUES (1, 'department_admin');
INSERT INTO `myboard`.`Account_Permissions` (`permission_id`, `permission_name`) VALUES (2, 'faculty');
INSERT INTO `myboard`.`Account_Permissions` (`permission_id`, `permission_name`) VALUES (3, 'regular_user');

COMMIT;

-- -----------------------------------------------------
-- Data for table `myboard`.`Department`
-- -----------------------------------------------------
START TRANSACTION;
USE `myboard`;
INSERT INTO `myboard`.`Department` (`dept_id`, `department_name`) VALUES (0, 'No Association');
INSERT INTO `myboard`.`Department` (`dept_id`, `department_name`) VALUES (1, 'Computer Science');
INSERT INTO `myboard`.`Department` (`dept_id`, `department_name`) VALUES (2, 'Mathematics');

COMMIT;

-- -----------------------------------------------------
-- Data for table `myboard`.`Users`
-- -----------------------------------------------------
START TRANSACTION;
USE `myboard`;
INSERT INTO `myboard`.`Users` (`uid`, `first_name`, `last_name`, `department`, `password`, `permission_id`, `creation_date`, `last_login`, `private_directory`, `active`, `email_address`) VALUES ('00000000', 'John', 'Doe', 0, 'password', 0, '2013-03-08 00:00:00', '2013-03-08 00:00:00', '/00000000/', 0, 'johndoe@csuohio.edu');
INSERT INTO `myboard`.`Users` (`uid`, `first_name`, `last_name`, `department`, `password`, `permission_id`, `creation_date`, `last_login`, `private_directory`, `active`, `email_address`) VALUES ('11111111', 'Jane', 'Doe', 1, 'password', 1, '2013-03-08 00:00:00', '2013-03-08 00:00:00', '/11111111/', 0, 'janedoe@csuohio.edu');
INSERT INTO `myboard`.`Users` (`uid`, `first_name`, `last_name`, `department`, `password`, `permission_id`, `creation_date`, `last_login`, `private_directory`, `active`, `email_address`) VALUES ('22222222', 'Donald', 'Knuth', 1, 'password', 2, '2013-03-08 00:00:00', '2013-03-08 00:00:00', '/22222222/', 0, 'donaldknuth@csuohio.edu');
INSERT INTO `myboard`.`Users` (`uid`, `first_name`, `last_name`, `department`, `password`, `permission_id`, `creation_date`, `last_login`, `private_directory`, `active`, `email_address`) VALUES ('33333333', 'Steve', 'Jobs', 1, 'password', 3, '2013-03-08 00:00:00', '2013-03-08 00:00:00', '/33333333/', 0, 'stevejobs@csuohio.edu');
INSERT INTO `myboard`.`Users` (`uid`, `first_name`, `last_name`, `department`, `password`, `permission_id`, `creation_date`, `last_login`, `private_directory`, `active`, `email_address`) VALUES ('44444444', 'Paul', 'Erdos', 2, 'password', 2, '2013-03-08 00:00:00', '2013-03-08 00:00:00', '/44444444/', 0, 'paulerdos@csuohio.edu');
INSERT INTO `myboard`.`Users` (`uid`, `first_name`, `last_name`, `department`, `password`, `permission_id`, `creation_date`, `last_login`, `private_directory`, `active`, `email_address`) VALUES ('55555555', 'John', 'Smith', 2, 'password', 1, '2013-03-08 00:00:00', '2013-03-08 00:00:00', '/55555555/', 0, 'johnsmith@csuohio.edu');
INSERT INTO `myboard`.`Users` (`uid`, `first_name`, `last_name`, `department`, `password`, `permission_id`, `creation_date`, `last_login`, `private_directory`, `active`, `email_address`) VALUES ('66666666', 'Jane', 'Smith', 2, 'password', 3, '2013-03-08 00:00:00', '2013-03-08 00:00:00', '/6666666/', 0, 'janesmith@csuohio.edu');

COMMIT;

-- -----------------------------------------------------
-- Data for table `myboard`.`Course_Info`
-- -----------------------------------------------------
START TRANSACTION;
USE `myboard`;
INSERT INTO `myboard`.`Course_Info` (`course_info_id`, `course_id`, `course_name`, `course_description`, `department`, `credits`) VALUES (NULL, 'CIS485', 'Senior Project', 'This is the course description', 1, 4);
INSERT INTO `myboard`.`Course_Info` (`course_info_id`, `course_id`, `course_name`, `course_description`, `department`, `credits`) VALUES (NULL, 'MTH181', 'Calculus I', 'This is the course description', 2, 4);

COMMIT;

-- -----------------------------------------------------
-- Data for table `myboard`.`Course_Section`
-- -----------------------------------------------------
START TRANSACTION;
USE `myboard`;
INSERT INTO `myboard`.`Course_Section` (`course_section_id`, `course_info_id`, `section`) VALUES (NULL, 1, 1);
INSERT INTO `myboard`.`Course_Section` (`course_section_id`, `course_info_id`, `section`) VALUES (NULL, 1, 2);
INSERT INTO `myboard`.`Course_Section` (`course_section_id`, `course_info_id`, `section`) VALUES (NULL, 2, 1);
INSERT INTO `myboard`.`Course_Section` (`course_section_id`, `course_info_id`, `section`) VALUES (NULL, 2, 2);

COMMIT;

-- -----------------------------------------------------
-- Data for table `myboard`.`Semester`
-- -----------------------------------------------------
START TRANSACTION;
USE `myboard`;
INSERT INTO `myboard`.`Semester` (`course_semester_id`, `semester_name`, `start_date`, `end_date`) VALUES (NULL, 'Fall2013', '2013-03-08 00:00:00', '2013-05-01 00:00:00');

COMMIT;

-- -----------------------------------------------------
-- Data for table `myboard`.`Courses`
-- -----------------------------------------------------
START TRANSACTION;
USE `myboard`;
INSERT INTO `myboard`.`Courses` (`course_id`, `course_section_id`, `course_semester_id`, `course_root_directory`) VALUES (NULL, 1, 1, '/courses/1/');
INSERT INTO `myboard`.`Courses` (`course_id`, `course_section_id`, `course_semester_id`, `course_root_directory`) VALUES (NULL, 2, 1, '/courses/2/');
INSERT INTO `myboard`.`Courses` (`course_id`, `course_section_id`, `course_semester_id`, `course_root_directory`) VALUES (NULL, 3, 1, '/courses/3/');
INSERT INTO `myboard`.`Courses` (`course_id`, `course_section_id`, `course_semester_id`, `course_root_directory`) VALUES (NULL, 4, 1, '/courses/4/');

COMMIT;

-- -----------------------------------------------------
-- Data for table `myboard`.`Course_Roles`
-- -----------------------------------------------------
START TRANSACTION;
USE `myboard`;
INSERT INTO `myboard`.`Course_Roles` (`role_id`, `role_name`) VALUES (0, 'Instructor');
INSERT INTO `myboard`.`Course_Roles` (`role_id`, `role_name`) VALUES (1, 'Teaching Assistant');
INSERT INTO `myboard`.`Course_Roles` (`role_id`, `role_name`) VALUES (2, 'Student');

COMMIT;

-- -----------------------------------------------------
-- Data for table `myboard`.`Course_Users`
-- -----------------------------------------------------
START TRANSACTION;
USE `myboard`;
INSERT INTO `myboard`.`Course_Users` (`course_uid`, `course_id`, `uid`, `role_id`) VALUES (NULL, 1, '22222222', 0);
INSERT INTO `myboard`.`Course_Users` (`course_uid`, `course_id`, `uid`, `role_id`) VALUES (NULL, 1, '33333333', 1);
INSERT INTO `myboard`.`Course_Users` (`course_uid`, `course_id`, `uid`, `role_id`) VALUES (NULL, 1, '66666666', 2);
INSERT INTO `myboard`.`Course_Users` (`course_uid`, `course_id`, `uid`, `role_id`) VALUES (NULL, 2, '22222222', 0);
INSERT INTO `myboard`.`Course_Users` (`course_uid`, `course_id`, `uid`, `role_id`) VALUES (NULL, 2, '33333333', 2);
INSERT INTO `myboard`.`Course_Users` (`course_uid`, `course_id`, `uid`, `role_id`) VALUES (NULL, 3, '44444444', 0);
INSERT INTO `myboard`.`Course_Users` (`course_uid`, `course_id`, `uid`, `role_id`) VALUES (NULL, 3, '66666666', 1);
INSERT INTO `myboard`.`Course_Users` (`course_uid`, `course_id`, `uid`, `role_id`) VALUES (NULL, 3, '33333333', 2);
INSERT INTO `myboard`.`Course_Users` (`course_uid`, `course_id`, `uid`, `role_id`) VALUES (NULL, 4, '44444444', 0);
INSERT INTO `myboard`.`Course_Users` (`course_uid`, `course_id`, `uid`, `role_id`) VALUES (NULL, 4, '66666666', 2);

COMMIT;

-- -----------------------------------------------------
-- Data for table `myboard`.`Assignment_Types`
-- -----------------------------------------------------
START TRANSACTION;
USE `myboard`;
INSERT INTO `myboard`.`Assignment_Types` (`assignment_type_id`, `assignment_type_name`) VALUES (0, 'Homework');
INSERT INTO `myboard`.`Assignment_Types` (`assignment_type_id`, `assignment_type_name`) VALUES (1, 'Quiz');
INSERT INTO `myboard`.`Assignment_Types` (`assignment_type_id`, `assignment_type_name`) VALUES (2, 'Test');

COMMIT;

-- -----------------------------------------------------
-- Data for table `myboard`.`Assignments`
-- -----------------------------------------------------
START TRANSACTION;
USE `myboard`;
INSERT INTO `myboard`.`Assignments` (`assignment_id`, `course_id`, `title`, `description`, `total_points`, `start_date`, `end_date`, `late_submission_date`, `timer`, `num_attempts`, `assignment_type_id`, `assignment_filename`, `creator_uid`, `visible_date`) VALUES (NULL, 1, 'Homework 1', 'This is the homework description', 100, '2013-03-08 00:00:00', '2013-05-01 00:00:00', '2013-05-02 00:00:00', 0, 0, 0, '1.xml', 1, '2013-03-08 00:00:00');
INSERT INTO `myboard`.`Assignments` (`assignment_id`, `course_id`, `title`, `description`, `total_points`, `start_date`, `end_date`, `late_submission_date`, `timer`, `num_attempts`, `assignment_type_id`, `assignment_filename`, `creator_uid`, `visible_date`) VALUES (NULL, 2, 'Homework 1', 'This is the homework description', 100, '2013-03-08 00:00:00', '2013-05-01 00:00:00', '2013-05-02 00:00:00', 0, 0, 0, '2.xml', 4, '2013-03-08 00:00:00');
INSERT INTO `myboard`.`Assignments` (`assignment_id`, `course_id`, `title`, `description`, `total_points`, `start_date`, `end_date`, `late_submission_date`, `timer`, `num_attempts`, `assignment_type_id`, `assignment_filename`, `creator_uid`, `visible_date`) VALUES (NULL, 3, 'Quiz 1', 'This is the quiz description', 100, '2013-03-08 00:00:00', '2013-05-01 00:00:00', '2013-05-02 00:00:00', 120, 1, 1, '3.xml', 6, '2013-03-08 00:00:00');
INSERT INTO `myboard`.`Assignments` (`assignment_id`, `course_id`, `title`, `description`, `total_points`, `start_date`, `end_date`, `late_submission_date`, `timer`, `num_attempts`, `assignment_type_id`, `assignment_filename`, `creator_uid`, `visible_date`) VALUES (NULL, 4, 'Test 1', 'This is the test desription', 100, '2013-03-08 00:00:00', '2013-05-01 00:00:00', '2013-05-02 00:00:00', 120, 1, 2, '4.xml', 9, '2013-03-08 00:00:00');

COMMIT;

-- -----------------------------------------------------
-- Data for table `myboard`.`Assignment_Submission`
-- -----------------------------------------------------
START TRANSACTION;
USE `myboard`;
INSERT INTO `myboard`.`Assignment_Submission` (`submission_id`, `assignment_id`, `course_uid`, `date_started`, `date_submitted`, `points_earned`, `submission_filename`, `comments`) VALUES (NULL, 1, 3, '2013-03-08 00:00:00', '2013-03-08 00:00:00', 100, '1.xml', 'This is the comment for HW1');
INSERT INTO `myboard`.`Assignment_Submission` (`submission_id`, `assignment_id`, `course_uid`, `date_started`, `date_submitted`, `points_earned`, `submission_filename`, `comments`) VALUES (NULL, 2, 5, '2013-03-08 00:00:00', '2013-03-08 00:00:00', 100, '2.xml', 'This is the comment for HW2');
INSERT INTO `myboard`.`Assignment_Submission` (`submission_id`, `assignment_id`, `course_uid`, `date_started`, `date_submitted`, `points_earned`, `submission_filename`, `comments`) VALUES (NULL, 3, 8, '2013-03-08 00:00:00', '2013-03-08 00:00:00', 100, '3.xml', 'This is the comment for Quiz1');
INSERT INTO `myboard`.`Assignment_Submission` (`submission_id`, `assignment_id`, `course_uid`, `date_started`, `date_submitted`, `points_earned`, `submission_filename`, `comments`) VALUES (NULL, 4, 10, '2013-03-08 00:00:00', '2013-03-08 00:00:00', 100, '4.xml', 'This is the comment for Test1');

COMMIT;

-- -----------------------------------------------------
-- Data for table `myboard`.`Message`
-- -----------------------------------------------------
START TRANSACTION;
USE `myboard`;
INSERT INTO `myboard`.`Message` (`message_id`, `parent_id`, `from_uid`, `course_id`, `title`, `message`, `attachment_filename`) VALUES (NULL, NULL, 1, 1, 'Message 1', 'This is the message', '1.zip');
INSERT INTO `myboard`.`Message` (`message_id`, `parent_id`, `from_uid`, `course_id`, `title`, `message`, `attachment_filename`) VALUES (NULL, NULL, 2, 1, 'Message 2', 'This is the message', '2.zip');
INSERT INTO `myboard`.`Message` (`message_id`, `parent_id`, `from_uid`, `course_id`, `title`, `message`, `attachment_filename`) VALUES (NULL, NULL, 3, 1, 'Message 3', 'This is the message', '3.zip');
INSERT INTO `myboard`.`Message` (`message_id`, `parent_id`, `from_uid`, `course_id`, `title`, `message`, `attachment_filename`) VALUES (NULL, NULL, 4, 2, 'Message 4', 'This is the message', '4.zip');
INSERT INTO `myboard`.`Message` (`message_id`, `parent_id`, `from_uid`, `course_id`, `title`, `message`, `attachment_filename`) VALUES (NULL, NULL, 5, 2, 'Message 5', 'This is the message', '5.zip');
INSERT INTO `myboard`.`Message` (`message_id`, `parent_id`, `from_uid`, `course_id`, `title`, `message`, `attachment_filename`) VALUES (NULL, NULL, 6, 3, 'Message 6', 'This is the message', '6.zip');
INSERT INTO `myboard`.`Message` (`message_id`, `parent_id`, `from_uid`, `course_id`, `title`, `message`, `attachment_filename`) VALUES (NULL, NULL, 7, 3, 'Message 7', 'This is the message', '7.zip');
INSERT INTO `myboard`.`Message` (`message_id`, `parent_id`, `from_uid`, `course_id`, `title`, `message`, `attachment_filename`) VALUES (NULL, NULL, 8, 3, 'Message 8', 'This is the message', '8.zip');
INSERT INTO `myboard`.`Message` (`message_id`, `parent_id`, `from_uid`, `course_id`, `title`, `message`, `attachment_filename`) VALUES (NULL, NULL, 9, 4, 'Message 9', 'This is the message', '9.zip');
INSERT INTO `myboard`.`Message` (`message_id`, `parent_id`, `from_uid`, `course_id`, `title`, `message`, `attachment_filename`) VALUES (NULL, NULL, 10, 4, 'Message 10', 'This is the message', '10.zip');

COMMIT;

-- -----------------------------------------------------
-- Data for table `myboard`.`Announcements`
-- -----------------------------------------------------
START TRANSACTION;
USE `myboard`;
INSERT INTO `myboard`.`Announcements` (`announcement_id`, `course_id`, `title`, `description`, `date_posted`, `creator_uid`) VALUES (NULL, 1, 'Announcement 1', 'This is the description', '2013-03-08 00:00:00', 1);
INSERT INTO `myboard`.`Announcements` (`announcement_id`, `course_id`, `title`, `description`, `date_posted`, `creator_uid`) VALUES (NULL, 2, 'Announcement 2', 'This is the description', '2013-03-08 00:00:00', 4);
INSERT INTO `myboard`.`Announcements` (`announcement_id`, `course_id`, `title`, `description`, `date_posted`, `creator_uid`) VALUES (NULL, 3, 'Announcement 3', 'This is the description', '2013-03-08 00:00:00', 6);
INSERT INTO `myboard`.`Announcements` (`announcement_id`, `course_id`, `title`, `description`, `date_posted`, `creator_uid`) VALUES (NULL, 4, 'Announcement 4', 'This is the description', '2013-03-08 00:00:00', 9);

COMMIT;

-- -----------------------------------------------------
-- Data for table `myboard`.`Message_Recipient`
-- -----------------------------------------------------
START TRANSACTION;
USE `myboard`;
INSERT INTO `myboard`.`Message_Recipient` (`message_recipient_id`, `message_id`, `course_uid`, `deleted`, `is_new`) VALUES (NULL, 1, 2, 0, 0);
INSERT INTO `myboard`.`Message_Recipient` (`message_recipient_id`, `message_id`, `course_uid`, `deleted`, `is_new`) VALUES (NULL, 2, 1, 0, 0);
INSERT INTO `myboard`.`Message_Recipient` (`message_recipient_id`, `message_id`, `course_uid`, `deleted`, `is_new`) VALUES (NULL, 3, 3, 0, 0);
INSERT INTO `myboard`.`Message_Recipient` (`message_recipient_id`, `message_id`, `course_uid`, `deleted`, `is_new`) VALUES (NULL, 4, 5, 0, 0);
INSERT INTO `myboard`.`Message_Recipient` (`message_recipient_id`, `message_id`, `course_uid`, `deleted`, `is_new`) VALUES (NULL, 5, 4, 0, 0);
INSERT INTO `myboard`.`Message_Recipient` (`message_recipient_id`, `message_id`, `course_uid`, `deleted`, `is_new`) VALUES (NULL, 6, 7, 0, 0);
INSERT INTO `myboard`.`Message_Recipient` (`message_recipient_id`, `message_id`, `course_uid`, `deleted`, `is_new`) VALUES (NULL, 7, 8, 0, 0);
INSERT INTO `myboard`.`Message_Recipient` (`message_recipient_id`, `message_id`, `course_uid`, `deleted`, `is_new`) VALUES (NULL, 8, 6, 0, 0);
INSERT INTO `myboard`.`Message_Recipient` (`message_recipient_id`, `message_id`, `course_uid`, `deleted`, `is_new`) VALUES (NULL, 9, 10, 0, 0);
INSERT INTO `myboard`.`Message_Recipient` (`message_recipient_id`, `message_id`, `course_uid`, `deleted`, `is_new`) VALUES (NULL, 10, 9, 0, 0);

COMMIT;

-- -----------------------------------------------------
-- Data for table `myboard`.`Additional_Calendar_Entries`
-- -----------------------------------------------------
START TRANSACTION;
USE `myboard`;
INSERT INTO `myboard`.`Additional_Calendar_Entries` (`entry_id`, `uid`, `title`, `description`, `date`) VALUES (NULL, '00000000', 'Calendar Entry 1', 'This is the description', '2013-04-12 00:00:00');
INSERT INTO `myboard`.`Additional_Calendar_Entries` (`entry_id`, `uid`, `title`, `description`, `date`) VALUES (NULL, '11111111', 'Calendar Entry 2', 'This is the description', '2013-04-12 00:00:00');
INSERT INTO `myboard`.`Additional_Calendar_Entries` (`entry_id`, `uid`, `title`, `description`, `date`) VALUES (NULL, '22222222', 'Calendar Entry 3', 'This is the description', '2013-04-12 00:00:00');
INSERT INTO `myboard`.`Additional_Calendar_Entries` (`entry_id`, `uid`, `title`, `description`, `date`) VALUES (NULL, '33333333', 'Calendar Entry 4', 'This is the description', '2013-04-12 00:00:00');
INSERT INTO `myboard`.`Additional_Calendar_Entries` (`entry_id`, `uid`, `title`, `description`, `date`) VALUES (NULL, '44444444', 'Calendar Entry 5', 'This is the description', '2013-04-12 00:00:00');
INSERT INTO `myboard`.`Additional_Calendar_Entries` (`entry_id`, `uid`, `title`, `description`, `date`) VALUES (NULL, '55555555', 'Calendar Entry 6', 'This is the description', '2013-04-12 00:00:00');
INSERT INTO `myboard`.`Additional_Calendar_Entries` (`entry_id`, `uid`, `title`, `description`, `date`) VALUES (NULL, '66666666', 'Calendar Entry 7', 'This is the description', '2013-04-12 00:00:00');

COMMIT;

-- -----------------------------------------------------
-- Data for table `myboard`.`Additional_Grade_Entries`
-- -----------------------------------------------------
START TRANSACTION;
USE `myboard`;
INSERT INTO `myboard`.`Additional_Grade_Entries` (`entry_id`, `course_uid`, `points_earned`, `total_points`, `title`, `comments`, `created_by`) VALUES (NULL, 3, 90, 90, 'Test 1', 'This is the comment', 1);
INSERT INTO `myboard`.`Additional_Grade_Entries` (`entry_id`, `course_uid`, `points_earned`, `total_points`, `title`, `comments`, `created_by`) VALUES (NULL, 5, 85, 85, 'Test 1', 'This is the comment', 4);
INSERT INTO `myboard`.`Additional_Grade_Entries` (`entry_id`, `course_uid`, `points_earned`, `total_points`, `title`, `comments`, `created_by`) VALUES (NULL, 8, 95, 95, 'Homework 1', 'This is the comment', 6);
INSERT INTO `myboard`.`Additional_Grade_Entries` (`entry_id`, `course_uid`, `points_earned`, `total_points`, `title`, `comments`, `created_by`) VALUES (NULL, 10, 100, 100, 'Homework 1', 'This is the comment', 9);

COMMIT;

-- -----------------------------------------------------
-- Data for table `myboard`.`Course_Material`
-- -----------------------------------------------------
START TRANSACTION;
USE `myboard`;
INSERT INTO `myboard`.`Course_Material` (`course_material_id`, `title`, `description`, `upload_date`, `creator_uid`, `material_filename`, `course_id`) VALUES (NULL, 'Syllabus1', 'Course Syllabus 1', '2013-03-08 00:00:00', 1, '1.pdf', 1);
INSERT INTO `myboard`.`Course_Material` (`course_material_id`, `title`, `description`, `upload_date`, `creator_uid`, `material_filename`, `course_id`) VALUES (NULL, 'Syllabus2', 'Course Syllabus 2', '2013-03-08 00:00:00', 4, '2.pdf', 2);
INSERT INTO `myboard`.`Course_Material` (`course_material_id`, `title`, `description`, `upload_date`, `creator_uid`, `material_filename`, `course_id`) VALUES (NULL, 'Syllabus3', 'Course Syllabus 3', '2013-03-08 00:00:00', 6, '3.pdf', 3);
INSERT INTO `myboard`.`Course_Material` (`course_material_id`, `title`, `description`, `upload_date`, `creator_uid`, `material_filename`, `course_id`) VALUES (NULL, 'Syllabus4', 'Course Syllabus 4', '2013-03-08 00:00:00', 9, '4.pdf', 4);

COMMIT;
