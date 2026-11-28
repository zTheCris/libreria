-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: Biblioteca
-- ------------------------------------------------------
-- Server version	26.10.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '46b4ca7c-d702-11f1-834e-c2e45bba4b4f:1-140';

--
-- Table structure for table `Autori`
--

DROP TABLE IF EXISTS `Autori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Autori` (
  `idAutore` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `cognome` varchar(255) NOT NULL,
  `data` date DEFAULT NULL,
  `fk_nazionalita` int DEFAULT NULL,
  PRIMARY KEY (`idAutore`),
  KEY `id_nazionalita_idx` (`fk_nazionalita`),
  CONSTRAINT `id_nazionalita` FOREIGN KEY (`fk_nazionalita`) REFERENCES `Nazionalita` (`id_nazionalita`)
) ENGINE=InnoDB AUTO_INCREMENT=259 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Autori`
--

LOCK TABLES `Autori` WRITE;
/*!40000 ALTER TABLE `Autori` DISABLE KEYS */;
INSERT INTO `Autori` VALUES (1,'J.R.R.','Tolkien','1892-01-03',2),(2,'Agatha','Christie','1890-09-15',2),(3,'George','Orwell','1903-06-25',2),(4,'Stephen','King','1947-09-21',3),(5,'Jules','Verne','1828-02-08',4),(6,'Dan','Brown','1964-06-22',3),(7,'Italo','Calvino','1923-10-15',1),(8,'Umberto','Eco','1932-01-05',1),(9,'Jane','Austen','1775-12-16',2),(10,'Isaac','Asimov','1920-01-02',3),(52,'Cristian','Perozzi','2006-09-21',1),(103,'Simone','Volpe','1995-12-11',52),(104,'Davide','Di Francesco','2006-09-16',53),(105,'Lorenzo','Barretta','2001-09-11',53),(106,'Pippo','Pippetto','2026-11-24',53),(200,'Mary','Shelley','1797-08-30',2),(201,'Bram','Stoker','1847-11-08',2),(202,'Arthur Conan','Doyle','1859-05-22',2),(203,'Victor','Hugo','1802-02-26',4),(204,'Alexandre','Dumas','1802-07-24',4),(205,'Dante','Alighieri','1265-05-29',1),(206,'Alessandro','Manzoni','1785-03-07',1),(207,'Giovanni','Boccaccio','1313-06-16',1),(208,'Lev','Tolstoj','1828-09-09',7),(209,'Fëdor','Dostoevskij','1821-11-11',7),(210,'Gabriel García','Márquez','1927-03-06',9),(211,'Jorge Luis','Borges','1899-08-24',9),(212,'Herman','Melville','1819-08-01',3),(213,'Mark','Twain','1835-11-30',3),(214,'Ernest','Hemingway','1899-07-21',3),(215,'Virginia','Woolf','1882-01-25',2),(216,'Charles','Dickens','1812-02-07',2),(217,'Charlotte','Brontë','1816-04-21',2),(218,'Emily','Brontë','1818-07-30',2),(219,'Oscar','Wilde','1854-10-16',2),(220,'Franz','Kafka','1883-07-03',5),(221,'Hermann','Hesse','1877-07-02',5),(222,'Albert','Camus','1913-11-07',4),(223,'Antoine de','Saint-Exupéry','1900-06-29',4),(224,'Haruki','Murakami','1949-01-12',8),(225,'Kenzaburō','Ōe','1935-01-31',8),(226,'José','Saramago','1922-11-16',6),(227,'Isabel','Allende','1942-08-02',6),(228,'Paulo','Coelho','1947-08-24',10),(229,'Khaled','Hosseini','1965-03-04',3);
/*!40000 ALTER TABLE `Autori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CasaEditrice`
--

DROP TABLE IF EXISTS `CasaEditrice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CasaEditrice` (
  `id_casaeditrice` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  PRIMARY KEY (`id_casaeditrice`),
  UNIQUE KEY `nome` (`nome`),
  UNIQUE KEY `nome_2` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CasaEditrice`
--

LOCK TABLES `CasaEditrice` WRITE;
/*!40000 ALTER TABLE `CasaEditrice` DISABLE KEYS */;
INSERT INTO `CasaEditrice` VALUES (7,'Adelphi'),(6,'Bompiani'),(3,'Einaudi'),(2,'Feltrinelli'),(4,'Garzanti'),(1,'Mondadori'),(5,'Rizzoli');
/*!40000 ALTER TABLE `CasaEditrice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Condizione`
--

DROP TABLE IF EXISTS `Condizione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Condizione` (
  `idDisponibilita` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idDisponibilita`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Condizione`
--

LOCK TABLES `Condizione` WRITE;
/*!40000 ALTER TABLE `Condizione` DISABLE KEYS */;
INSERT INTO `Condizione` VALUES (1,'Ottime condizioni'),(2,'Buone condizioni'),(3,'Condizioni sufficienti'),(4,'Danneggiato'),(5,'Da riparare');
/*!40000 ALTER TABLE `Condizione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Copie`
--

DROP TABLE IF EXISTS `Copie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Copie` (
  `idCopia` int NOT NULL AUTO_INCREMENT,
  `fk_isbn` int NOT NULL,
  `fk_casaeditrice` int DEFAULT NULL,
  `numero_copie` int NOT NULL,
  PRIMARY KEY (`idCopia`),
  KEY `fk_casaeditrice_idx` (`fk_casaeditrice`),
  KEY `Copie_ibfk_1` (`fk_isbn`),
  CONSTRAINT `Copie_ibfk_1` FOREIGN KEY (`fk_isbn`) REFERENCES `Libri` (`isbn`),
  CONSTRAINT `fk_casaeditrice` FOREIGN KEY (`fk_casaeditrice`) REFERENCES `CasaEditrice` (`id_casaeditrice`)
) ENGINE=InnoDB AUTO_INCREMENT=206 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Copie`
--

LOCK TABLES `Copie` WRITE;
/*!40000 ALTER TABLE `Copie` DISABLE KEYS */;
INSERT INTO `Copie` VALUES (2,100000002,1,4),(3,100000003,2,6),(4,100000004,3,8),(5,100000005,3,5),(6,100000006,5,3),(7,100000007,5,4),(8,100000008,7,6),(9,100000009,7,5),(10,100000010,2,7),(11,100000011,3,4),(12,100000012,2,6),(13,100000013,3,3),(14,100000014,4,5),(15,100000015,4,4),(16,100000016,5,6),(17,100000017,5,3),(18,100000018,7,5),(19,100000019,3,4),(20,100000020,NULL,7),(53,100000005,1,5),(103,100000006,1,5),(104,100000022,1,1),(105,100000022,4,1),(106,100000023,2,1),(107,100000023,5,1),(108,100000024,3,1),(109,100000024,6,1),(110,100000025,4,1),(111,100000025,7,1),(112,100000026,5,1),(113,100000026,NULL,1),(114,100000027,6,1),(115,100000027,1,1),(116,100000028,7,1),(117,100000028,2,1),(118,100000029,NULL,1),(119,100000029,3,1),(120,100000030,1,1),(121,100000030,4,1),(122,100000031,2,1),(123,100000031,5,1),(124,100000032,3,1),(125,100000032,6,1),(126,100000033,4,1),(127,100000033,7,1),(128,100000034,5,1),(129,100000034,NULL,1),(130,100000035,6,1),(131,100000035,1,1),(132,100000036,7,1),(133,100000036,2,1),(134,100000037,NULL,1),(135,100000037,3,1),(136,100000038,1,1),(137,100000038,4,1),(138,100000039,2,1),(139,100000039,5,1),(140,100000040,3,1),(141,100000040,6,1),(142,100000041,4,1),(143,100000041,7,1),(144,100000042,5,1),(145,100000042,NULL,1),(146,100000043,6,1),(147,100000043,1,1),(148,100000044,7,1),(149,100000044,2,1),(150,100000045,NULL,1),(151,100000045,3,1),(152,100000046,1,1),(153,100000046,4,1),(154,100000047,2,1),(155,100000047,5,1),(156,100000048,3,1),(157,100000048,6,1),(158,100000049,4,1),(159,100000049,7,1),(160,100000050,5,1),(161,100000050,NULL,1),(162,100000051,6,1),(163,100000051,1,1),(164,100000052,7,1),(165,100000052,2,1),(166,100000053,NULL,1),(167,100000053,3,1),(168,100000054,1,1),(169,100000054,4,1),(170,100000055,2,1),(171,100000055,5,1),(172,100000056,3,1),(173,100000056,6,1),(174,100000057,4,1),(175,100000057,7,1),(176,100000058,5,1),(177,100000058,NULL,1),(178,100000059,6,1),(179,100000059,1,1),(180,100000060,7,1),(181,100000060,2,1),(182,100000061,NULL,1),(183,100000061,3,1),(184,100000062,1,1),(185,100000062,4,1),(186,100000063,2,1),(187,100000063,5,1),(188,100000064,3,1),(189,100000064,6,1),(190,100000065,4,1),(191,100000065,7,1),(192,100000066,5,1),(193,100000066,NULL,1),(194,100000067,6,1),(195,100000067,1,1),(196,100000068,7,1),(197,100000068,2,1),(198,100000069,NULL,1),(199,100000069,3,1),(200,100000070,1,1),(201,100000070,4,1),(202,100000071,2,1),(203,100000071,5,1);
/*!40000 ALTER TABLE `Copie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Dipendenti`
--

DROP TABLE IF EXISTS `Dipendenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Dipendenti` (
  `idDipendente` int NOT NULL AUTO_INCREMENT,
  `cf` varchar(16) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `cognome` varchar(255) DEFAULT NULL,
  `stipendio` int DEFAULT NULL,
  PRIMARY KEY (`idDipendente`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Dipendenti`
--

LOCK TABLES `Dipendenti` WRITE;
/*!40000 ALTER TABLE `Dipendenti` DISABLE KEYS */;
INSERT INTO `Dipendenti` VALUES (1,'RSSMRA80A01H501A','Roberto','Rossi',1500),(2,'BNCLGU85B12F205B','Luigi','Bianchi',1600),(3,'VRDPLA90C15H501C','Paola','Verdi',1450),(4,'NREGLL88D20F205D','Giorgia','Neri',1700),(5,'FRRMRC82E10H501E','Marco','Ferrari',1550),(6,'RMNSRA91F25F205F','Sara','Romano',1500),(7,'GLLNDR87G05H501G','Andrea','Gallo',1800),(8,'CSTLNA89H18F205H','Elena','Costa',1650),(9,'FNTMRC86L22H501L','Marco','Fontana',1550),(10,'MRTCHR92M30F205M','Chiara','Moretti',1750);
/*!40000 ALTER TABLE `Dipendenti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Generi`
--

DROP TABLE IF EXISTS `Generi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Generi` (
  `idGenere` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idGenere`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Generi`
--

LOCK TABLES `Generi` WRITE;
/*!40000 ALTER TABLE `Generi` DISABLE KEYS */;
INSERT INTO `Generi` VALUES (1,'Fantasy'),(2,'Giallo'),(3,'Romanzo'),(4,'Fantascienza'),(5,'Horror'),(6,'Storico'),(7,'Avventura'),(8,'Biografia'),(53,'fatntasy');
/*!40000 ALTER TABLE `Generi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Libri`
--

DROP TABLE IF EXISTS `Libri`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Libri` (
  `isbn` int NOT NULL,
  `titolo` varchar(45) DEFAULT NULL,
  `anno` date DEFAULT NULL,
  `prezzo` int DEFAULT NULL,
  `fk_genere` int DEFAULT NULL,
  PRIMARY KEY (`isbn`),
  KEY `fk_genere` (`fk_genere`),
  CONSTRAINT `Libri_ibfk_1` FOREIGN KEY (`fk_genere`) REFERENCES `Generi` (`idGenere`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Libri`
--

LOCK TABLES `Libri` WRITE;
/*!40000 ALTER TABLE `Libri` DISABLE KEYS */;
INSERT INTO `Libri` VALUES (93893894,'Luca non c\'è','2023-12-23',20,1),(100000002,'Lo Hobbit','1937-09-21',18,1),(100000003,'Assassinio sull Orient Express','1934-01-01',15,2),(100000004,'1984','1949-06-08',14,3),(100000005,'La fattoria degli animali','1945-08-17',12,3),(100000006,'IT','1986-09-15',22,5),(100000007,'Shining','1977-01-28',19,5),(100000008,'Viaggio al centro della Terra','1864-11-25',16,7),(100000009,'Ventimila leghe sotto i mari','1870-06-20',17,7),(100000010,'Il codice da Vinci','2003-03-18',20,2),(100000011,'Il barone rampante','1957-01-01',13,3),(100000012,'Il nome della rosa','1980-01-01',21,2),(100000013,'Orgoglio e pregiudizio','1813-01-28',14,3),(100000014,'Fondazione','1951-06-01',18,4),(100000015,'Io robot','1950-12-02',16,4),(100000016,'Dracula','1897-05-26',15,5),(100000017,'Frankenstein','1818-01-01',14,5),(100000018,'Il giro del mondo','1873-01-01',16,7),(100000019,'La coscienza di Zeno','1923-01-01',17,3),(100000020,'Il diario di Anna Frank','1947-06-25',12,8),(100000021,'Shatter Me','2011-11-15',20,1),(100000022,'Frankenstein','1818-01-01',16,5),(100000023,'Dracula','1897-05-26',17,5),(100000024,'Il mastino dei Baskerville','1902-04-01',14,2),(100000025,'I miserabili','1862-01-01',22,6),(100000026,'Il conte di Montecristo','1844-08-28',20,7),(100000027,'La divina commedia','1321-01-01',19,3),(100000028,'I promessi sposi','1827-01-01',15,3),(100000029,'Decameron','1353-01-01',18,3),(100000030,'Guerra e pace','1869-01-01',24,6),(100000031,'Delitto e castigo','1866-01-01',18,3),(100000032,'Cent\'anni di solitudine','1967-05-30',21,3),(100000033,'Finzioni','1944-01-01',16,4),(100000034,'Moby Dick','1851-10-18',19,7),(100000035,'Le avventure di Tom Sawyer','1876-06-01',14,7),(100000036,'Il vecchio e il mare','1952-09-01',13,3),(100000037,'La signora Dalloway','1925-05-14',17,3),(100000038,'Oliver Twist','1838-01-01',15,3),(100000039,'Jane Eyre','1847-10-16',16,3),(100000040,'Cime tempestose','1847-12-01',16,3),(100000041,'Il ritratto di Dorian Gray','1890-07-01',15,3),(100000042,'La metamorfosi','1915-01-01',12,3),(100000043,'Siddharta','1922-01-01',14,3),(100000044,'Lo straniero','1942-01-01',13,3),(100000045,'Il piccolo principe','1943-04-06',12,7),(100000046,'Norwegian Wood','1987-09-04',18,3),(100000047,'Una questione personale','1964-01-01',17,3),(100000048,'Cecità','1995-01-01',19,3),(100000049,'La casa degli spiriti','1982-01-01',18,3),(100000050,'L\'alchimista','1988-01-01',14,7),(100000051,'Il cacciatore di aquiloni','2003-05-29',18,3),(100000052,'Il libro della giungla','1894-01-01',14,7),(100000053,'Robinson Crusoe','1719-04-25',15,7),(100000054,'I viaggi di Gulliver','1726-10-28',15,7),(100000055,'Il giro di vite','1898-01-01',13,5),(100000056,'Cuore','1886-10-15',12,3),(100000057,'Le avventure di Pinocchio','1883-02-07',13,7),(100000058,'Il fu Mattia Pascal','1904-01-01',14,3),(100000059,'Uno, nessuno e centomila','1926-01-01',15,3),(100000060,'Il deserto dei Tartari','1940-01-01',16,3),(100000061,'Se questo è un uomo','1947-01-01',17,8),(100000062,'La luna e i falò','1950-01-01',15,3),(100000063,'Il Gattopardo','1958-01-01',18,6),(100000064,'Il nome della rosa','1980-01-01',20,2),(100000065,'Il pendolo di Foucault','1988-01-01',22,2),(100000066,'La coscienza di Zeno','1923-01-01',17,3),(100000067,'Il sentiero dei nidi di ragno','1947-01-01',14,3),(100000068,'Le città invisibili','1972-01-01',16,3),(100000069,'Fahrenheit 451','1953-10-19',16,4),(100000070,'Cronache marziane','1950-05-04',17,4),(100000071,'Dune','1965-08-01',21,4);
/*!40000 ALTER TABLE `Libri` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Libri_Autori`
--

DROP TABLE IF EXISTS `Libri_Autori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Libri_Autori` (
  `fk_isbn` int NOT NULL,
  `fk_autore` int NOT NULL,
  PRIMARY KEY (`fk_isbn`,`fk_autore`),
  KEY `fk_autore` (`fk_autore`),
  CONSTRAINT `Libri_Autori_ibfk_1` FOREIGN KEY (`fk_isbn`) REFERENCES `Libri` (`isbn`),
  CONSTRAINT `Libri_Autori_ibfk_2` FOREIGN KEY (`fk_autore`) REFERENCES `Autori` (`idAutore`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Libri_Autori`
--

LOCK TABLES `Libri_Autori` WRITE;
/*!40000 ALTER TABLE `Libri_Autori` DISABLE KEYS */;
INSERT INTO `Libri_Autori` VALUES (93893894,1),(100000002,1),(100000021,1),(100000052,1),(100000062,1),(93893894,2),(100000003,2),(100000021,2),(100000053,2),(100000068,2),(93893894,3),(100000004,3),(100000005,3),(100000021,3),(100000054,3),(100000063,3),(100000069,3),(100000006,4),(100000007,4),(100000016,4),(100000055,4),(100000064,4),(100000070,4),(100000008,5),(100000009,5),(100000017,5),(100000018,5),(100000056,5),(100000065,5),(100000010,6),(100000057,6),(100000011,7),(100000019,7),(100000058,7),(100000066,7),(100000012,8),(100000020,8),(100000059,8),(100000067,8),(100000013,9),(100000060,9),(100000014,10),(100000015,10),(100000061,10),(100000071,10),(100000022,200),(100000023,201),(100000024,202),(100000025,203),(100000026,204),(100000027,205),(100000028,206),(100000029,207),(100000030,208),(100000031,209),(100000032,210),(100000033,211),(100000034,212),(100000035,213),(100000036,214),(100000037,215),(100000038,216),(100000039,217),(100000040,218),(100000041,219),(100000042,220),(100000043,221),(100000044,222),(100000045,223),(100000046,224),(100000047,225),(100000048,226),(100000049,227),(100000050,228),(100000051,229);
/*!40000 ALTER TABLE `Libri_Autori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Nazionalita`
--

DROP TABLE IF EXISTS `Nazionalita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Nazionalita` (
  `id_nazionalita` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  PRIMARY KEY (`id_nazionalita`),
  UNIQUE KEY `nome` (`nome`),
  UNIQUE KEY `nome_2` (`nome`),
  UNIQUE KEY `nome_3` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Nazionalita`
--

LOCK TABLES `Nazionalita` WRITE;
/*!40000 ALTER TABLE `Nazionalita` DISABLE KEYS */;
INSERT INTO `Nazionalita` VALUES (54,''),(3,'Americana'),(52,'Australiano'),(10,'Brasiliana'),(9,'Colombiana'),(4,'Francese'),(8,'Giapponese'),(2,'Inglese'),(1,'Italiana'),(53,'Italiano'),(7,'Russa'),(6,'Spagnola'),(5,'Tedesca'),(105,'Tedesco');
/*!40000 ALTER TABLE `Nazionalita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Prestiti`
--

DROP TABLE IF EXISTS `Prestiti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Prestiti` (
  `idPrestito` int NOT NULL AUTO_INCREMENT,
  `stato` varchar(45) DEFAULT NULL,
  `fk_libro` int DEFAULT NULL,
  `fk_utente` int DEFAULT NULL,
  `fk_condizione` int DEFAULT NULL,
  PRIMARY KEY (`idPrestito`),
  KEY `fk_libro` (`fk_libro`),
  KEY `fk_utente` (`fk_utente`),
  KEY `fk_condizione` (`fk_condizione`),
  CONSTRAINT `Prestiti_ibfk_1` FOREIGN KEY (`fk_libro`) REFERENCES `Libri` (`isbn`),
  CONSTRAINT `Prestiti_ibfk_2` FOREIGN KEY (`fk_utente`) REFERENCES `Utenti` (`idUtente`),
  CONSTRAINT `Prestiti_ibfk_3` FOREIGN KEY (`fk_condizione`) REFERENCES `Condizione` (`idDisponibilita`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Prestiti`
--

LOCK TABLES `Prestiti` WRITE;
/*!40000 ALTER TABLE `Prestiti` DISABLE KEYS */;
/*!40000 ALTER TABLE `Prestiti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Utenti`
--

DROP TABLE IF EXISTS `Utenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Utenti` (
  `idUtente` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `cognome` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  PRIMARY KEY (`idUtente`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Utenti`
--

LOCK TABLES `Utenti` WRITE;
/*!40000 ALTER TABLE `Utenti` DISABLE KEYS */;
INSERT INTO `Utenti` VALUES (1,'anna@mail.it','Anna','Rossi',''),(2,'luca@mail.it','Luca','Bianchi',''),(3,'mario@mail.it','Mario','Verdi',''),(4,'giulia@mail.it','Giulia','Neri',''),(5,'paolo@mail.it','Paolo','Ferrari',''),(6,'sara@mail.it','Sara','Romano',''),(7,'marco@mail.it','Marco','Gallo',''),(8,'elena@mail.it','Elena','Costa',''),(9,'andrea@mail.it','Andrea','Fontana',''),(10,'chiara@mail.it','Chiara','Moretti',''),(52,'crismrp3@gmail.com','Cristian','Perozzi','$2a$10$2lpvOG.5NpXPr1hyozbeauAgmdE3qFCNHngSI/pcfTL9/1CjHqqSC'),(53,'merytrmon@gmail.com','Mery','Potato','$2a$10$Ztzbv6c.xsUgST9MIUyrJuXKDDcQRCS/CrbDy5tXpzKEuh3JARL7G');
/*!40000 ALTER TABLE `Utenti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Vendite`
--

DROP TABLE IF EXISTS `Vendite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Vendite` (
  `idVendite` int NOT NULL AUTO_INCREMENT,
  `fk_utente` int DEFAULT NULL,
  `fk_dipendente` int DEFAULT NULL,
  `data_vendita` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idVendite`),
  KEY `fk_utente` (`fk_utente`),
  KEY `fk_dipendente` (`fk_dipendente`),
  CONSTRAINT `Vendite_ibfk_1` FOREIGN KEY (`fk_utente`) REFERENCES `Utenti` (`idUtente`),
  CONSTRAINT `Vendite_ibfk_2` FOREIGN KEY (`fk_dipendente`) REFERENCES `Dipendenti` (`idDipendente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Vendite`
--

LOCK TABLES `Vendite` WRITE;
/*!40000 ALTER TABLE `Vendite` DISABLE KEYS */;
/*!40000 ALTER TABLE `Vendite` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-28 12:14:37
