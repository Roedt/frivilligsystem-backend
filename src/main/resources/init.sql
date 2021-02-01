SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+01:00";
SET NAMES utf8;


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `fylker` (
  `id` int(2) NOT NULL PRIMARY KEY,
  `name` varchar(60) NOT NULL
);

INSERT INTO `fylker` (`id`, `name`) VALUES
(-1, 'Udefinert fylke'),
(01, 'Østfold'),
(02, 'Akershus'),
(03, 'Oslo'),
(04, 'Hedmark'),
(05, 'Oppland'),
(06, 'Buskerud'),
(07, 'Vestfold'),
(08, 'Telemark'),
(09, 'Aust-Agder'),
(10, 'Vest-Agder'),
(11, 'Rogaland'),
(12, 'Hordaland'),
(14, 'Sogn og Fjordane'),
(15, 'Møre og Romsdal'),
(18, 'Nordland'),
(19, 'Troms'),
(20, 'Finnmark'),
(21, 'Svalbard'),
(30, 'Viken'),
(34, 'Innlandet'),
(38, 'Vestfold og Telemark'),
(42, 'Agder'),
(46, 'Vestland'),
(50, 'Trøndelag'),
(54, 'Troms og Finnmark');

-- --------------------------------------------------------

CREATE TABLE postnummer(
   POSTNR                  INTEGER  NOT NULL PRIMARY KEY
  ,POSTSTAD                VARCHAR(39) NOT NULL
  ,POSTNR_OG_STAD          VARCHAR(44) NOT NULL
  ,BRUKSOMRÅDE             VARCHAR(38) NOT NULL
  ,FOLKETAL                INTEGER
  ,BYDEL                   VARCHAR(17)
  ,KOMMNR                  INTEGER  NOT NULL
  ,KOMMUNE                 VARCHAR(15) NOT NULL
  ,FYLKE                   VARCHAR(20) NOT NULL
  ,LAT                     NUMERIC(10,6) NOT NULL
  ,LON                     NUMERIC(9,6) NOT NULL
  ,DATAKVALITET            INTEGER
  ,DATAKVALITETSFORKLARING VARCHAR(48)
  ,SIST_OPPDATERT          VARCHAR(19) NOT NULL
);

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `lokallag` (
  `id` int(3) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(100) NOT NULL UNIQUE
);

insert into `lokallag` (name) values
  ('Rødt Grorud'),
  ('Rødt Bergen Nord'),
  ('Rødt Bergen Studentlag'),
  ('Rødt Bergen Sør'),
  ('Rødt Bergen Sentrum'),
  ('Rødt Bergen Vest'),
  ('Rødt Oslo Østensjø'),
  ('Rødt Skien'),
  ('Rødt Tromsø'),
  ('Rødt Ålesund'),
  ('Rødt Notodden');


-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `login_attempts` (
  `user_id` int(6) NOT NULL,
  `time` varchar(30) NOT NULL
);

-- --------------------------------------------------------

DELIMITER //
  DROP PROCEDURE IF EXISTS sp_recordLoginAttempt;
  CREATE PROCEDURE sp_recordLoginAttempt (
    userId_in int(6)
  )
BEGIN
INSERT INTO `login_attempts` (user_id, time)
VALUES
  (userId_in, UNIX_TIMESTAMP(now()));
END //

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `person` (
  `id` int(6) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `navn` varchar(150) DEFAULT NULL,
  `epost` varchar(100) DEFAULT NULL,
  `telefonnummer` varchar(15) NOT NULL UNIQUE,
  `postnummer` integer DEFAULT NULL,
  `hypersysID` int(6) unsigned DEFAULT NULL UNIQUE,
  `lokallag_id` int(3) unsigned DEFAULT NULL,
  `rolle` varchar(20) DEFAULT NULL,
  FOREIGN KEY(`lokallag_id`) REFERENCES `lokallag` (`id`),
  FOREIGN KEY(`postnummer`) REFERENCES `postnummer` (`postnummer`),
  INDEX (`telefonnummer`),
  INDEX (`lokallag_id`),
  INDEX (`postnummer`)
);
-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `frivillig` (
  `id` int(6) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `person_id` int(6) unsigned UNIQUE,
  `alleredeAktivILokallag` bit,
  `medlemIRoedt` int,
  `spesiellKompetanse` varchar(300) DEFAULT NULL,
  `andreTingDuVilBidraMed` varchar(300) DEFAULT NULL,
  `fortellLittOmDegSelv` varchar(300) DEFAULT NULL,
  FOREIGN KEY(`person_id`) REFERENCES `person` (`id`),
  INDEX (`person_id`)
);
-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `kontakt` (
  `id` int(6) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `registrert_av` int(6) unsigned,
  `frivillig_id` int(6) unsigned,
  `tilbakemelding` varchar(1000) DEFAULT NULL,
  `registrert` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY(`frivillig_id`) REFERENCES `frivillig` (`id`),
  INDEX (`frivillig_id`),
  FOREIGN KEY(`registrert_av`) REFERENCES `person`(`id`),
  INDEX(`registrert_av`)
);

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
    `next_val` int
);
INSERT INTO `hibernate_sequence`(`next_val`) values (1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;