DROP DATABASE IF EXISTS eros; 
CREATE DATABASE eros; 
USE eros;

CREATE TABLE `DATING_MAPPING`(
     `ID` bigint(11) NOT NULL AUTO_INCREMENT,
     `MAPPING` text NOT NULL,
     PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `STORY` (
     `ID` bigint(11) NOT NULL AUTO_INCREMENT,
     `CONTENT` text NOT NULL,
     `STORY_TYPE` enum('Prompted', 'Unprompted') DEFAULT 'Prompted',
     `QUESTION` text NOT NULL,
     `ANSWER_A` text NOT NULL,
     `ANSWER_B` text NOT NULL,
     PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `EVENT` (
    `ID` bigint(11) NOT NULL AUTO_INCREMENT,
    `EVENT_DATE` varchar(10) DEFAULT NULL,
    `LOCATION` varchar(50) DEFAULT NULL,
    `NAME` varchar(20) DEFAULT NULL,
    `STARTED` tinyint DEFAULT 0,
    `ENDED` tinyint DEFAULT 0,
    `MAPPING_ID` bigint(11) DEFAULT 1,
    PRIMARY KEY (`ID`),
    CONSTRAINT `DATING_MAPPING_fk_1` FOREIGN KEY (`MAPPING_ID`) REFERENCES `DATING_MAPPING` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `DATER` (
    `ID` bigint(11) NOT NULL AUTO_INCREMENT,
    `EMAIL` varchar(30) DEFAULT NULL,
    `PWD` varchar(20) DEFAULT NULL,
    `PROFILE_NAME` varchar(20) DEFAULT NULL,
    `NAME` varchar(60) DEFAULT NULL,
    `LAST_NAME` varchar(20) DEFAULT NULL,
    `GENDER` enum('MALE', 'FEMALE') DEFAULT 'MALE',
    `ACCEPT_CONSENT_PAGE_FLAG` tinyint DEFAULT 0,
   `ACCEPTANCE_QUESTION_PAGE_FLAG` tinyint DEFAULT 0, 
   `PROFILE_CREATION_PAGE_FLAG` tinyint DEFAULT 0,
   `REJECTED` tinyint DEFAULT 0,
    PRIMARY KEY (`ID`),
    UNIQUE KEY `dater_cred` (`EMAIL`,`PWD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `EVENT_DATERS` (
    `ID` bigint(11) NOT NULL AUTO_INCREMENT,
    `EVENT_ID` bigint(11) NOT NULL,
    `DATER_ID` bigint(11) NOT NULL,
    `PROFILE_EVALUATION_COMPLETED_FLAG` tinyint DEFAULT 0,
    `MESSAGING_EVALUATION_COMPLETED_FLAG` tinyint DEFAULT 0,
    PRIMARY KEY (`ID`),
    CONSTRAINT `EVENT_ID_fk_1` FOREIGN KEY (`EVENT_ID`) REFERENCES `EVENT` (`ID`) ON DELETE CASCADE,
    CONSTRAINT `DATER_ID_fk_1` FOREIGN KEY (`DATER_ID`) REFERENCES `DATER` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `EVENT_STORIES` (
    `ID` bigint(11) NOT NULL AUTO_INCREMENT,
    `EVENT_ID` bigint(11) NOT NULL,
    `STORY_ID` bigint(11) NOT NULL,
    `LABEL` enum('PA', 'PB', 'PC', 'PD', 'U'),
    PRIMARY KEY (`ID`),
    CONSTRAINT `EVENT_ID_fk_5` FOREIGN KEY (`EVENT_ID`) REFERENCES `EVENT` (`ID`) ON DELETE CASCADE,
    CONSTRAINT `STORY_ID_fk_2` FOREIGN KEY (`STORY_ID`) REFERENCES `STORY` (`ID`) ON DELETE CASCADE,
    UNIQUE KEY `event_story_label` (`EVENT_ID`,`LABEL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `IMAGE` (
    `ID` bigint(11) NOT NULL AUTO_INCREMENT,
    `DATER_ID` bigint(11) NOT NULL,
    `NAME` varchar(50) NOT NULL,
    PRIMARY KEY (`ID`),
    CONSTRAINT `DATER_ID_fk_2` FOREIGN KEY (`DATER_ID`) REFERENCES `DATER` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `QUESTION` (
   `ID` bigint(11) NOT NULL AUTO_INCREMENT,
   `CONTENT` text NOT NULL,
   `PAGE_TYPE` enum('Consent Page','Acceptance Page','Profile Creation','Profile Evaluation','Messaging Evaluation') DEFAULT 'Profile Creation',
   PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `EVALUATION_ANSWERS` (
   `ID` bigint(11) NOT NULL AUTO_INCREMENT,
   `DATER_ID` bigint(11) NOT NULL,
   `EVENT_ID` bigint(11) NOT NULL,
   `MATCH_ID` bigint(11) NOT NULL,
   `QUESTION_ID` bigint(11) NOT NULL,
   `ANSWER` text NOT NULL,
    PRIMARY KEY (`ID`),
    CONSTRAINT `EVENT_ID_fk_6` FOREIGN KEY (`EVENT_ID`) REFERENCES `EVENT` (`ID`),
    CONSTRAINT `DATER_ID_fk_6` FOREIGN KEY (`DATER_ID`) REFERENCES `DATER` (`ID`) ON DELETE CASCADE,
    CONSTRAINT `MATCH_ID_fk_1` FOREIGN KEY (`MATCH_ID`) REFERENCES `DATER` (`ID`),
    CONSTRAINT `QUESTION_ID_fk_2` FOREIGN KEY (`QUESTION_ID`) REFERENCES `QUESTION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `PROFILE_ANSWERS` (
    `ID` bigint(11) NOT NULL AUTO_INCREMENT,
   `DATER_ID` bigint(11) NOT NULL,
   `QUESTION_ID` bigint(11) NOT NULL,
   `ANSWER` text NOT NULL,
    PRIMARY KEY (`ID`),
    CONSTRAINT `DATER_ID_fk_10` FOREIGN KEY (`DATER_ID`) REFERENCES `DATER` (`ID`) ON DELETE CASCADE,
    CONSTRAINT `QUESTION_ID_fk_1` FOREIGN KEY (`QUESTION_ID`) REFERENCES `QUESTION` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `STORY_ANSWERS` (
    `ID` bigint(11) NOT NULL AUTO_INCREMENT,
   `DATER_ID` bigint(11) NOT NULL,
   `STORY_ID` bigint(11) NOT NULL,
   `ANSWER` enum('A', 'B') DEFAULT 'A',
    PRIMARY KEY (`ID`),
    CONSTRAINT `DATERS_ID_fk_9` FOREIGN KEY (`DATER_ID`) REFERENCES `DATER` (`ID`) ON DELETE CASCADE,
    CONSTRAINT `STORY_ID_fk_1` FOREIGN KEY (`STORY_ID`) REFERENCES `STORY` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `MESSAGES`(
    `FROM_DATER_ID` bigint(11) NOT NULL,
    `TO_DATER_ID` bigint(11) NOT NULL,
    `ID` bigint(11) NOT NULL AUTO_INCREMENT,
    `EVENT_ID` bigint(11) NOT NULL,
    `CONTENT` mediumtext NOT NULL,
    `TIME_RECEIVED` TIMESTAMP default CURRENT_TIMESTAMP,
    PRIMARY KEY (`ID`),
    CONSTRAINT `EVENT_ID_fk_7` FOREIGN KEY (`EVENT_ID`) REFERENCES `EVENT` (`ID`),
    CONSTRAINT `DATER_ID_fk_7` FOREIGN KEY (`FROM_DATER_ID`) REFERENCES `DATER` (`ID`) ON DELETE CASCADE,
    CONSTRAINT `DATER_ID_fk_8` FOREIGN KEY (`TO_DATER_ID`) REFERENCES `DATER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
