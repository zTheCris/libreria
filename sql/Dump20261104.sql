-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: Biblioteca
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

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '46b4ca7c-d702-11f1-834e-c2e45bba4b4f:1-61';

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
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Autori`
--

LOCK TABLES `Autori` WRITE;
/*!40000 ALTER TABLE `Autori` DISABLE KEYS */;
INSERT INTO `Autori` VALUES (1,'J.R.R.','Tolkien','1892-01-03',2),(2,'Agatha','Christie','1890-09-15',2),(3,'George','Orwell','1903-06-25',2),(4,'Stephen','King','1947-09-21',3),(5,'Jules','Verne','1828-02-08',4),(6,'Dan','Brown','1964-06-22',3),(7,'Italo','Calvino','1923-10-15',1),(8,'Umberto','Eco','1932-01-05',1),(9,'Jane','Austen','1775-12-16',2),(10,'Isaac','Asimov','1920-01-02',3);
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
  PRIMARY KEY (`id_casaeditrice`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CasaEditrice`
--

LOCK TABLES `CasaEditrice` WRITE;
/*!40000 ALTER TABLE `CasaEditrice` DISABLE KEYS */;
INSERT INTO `CasaEditrice` VALUES (1,'Mondadori'),(2,'Feltrinelli'),(3,'Einaudi'),(4,'Garzanti'),(5,'Rizzoli'),(6,'Bompiani'),(7,'Adelphi'),(8,'Salani');
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
  `fk_casaeditrice` int NOT NULL,
  `numero_copie` int NOT NULL,
  PRIMARY KEY (`idCopia`),
  KEY `fk_casaeditrice_idx` (`fk_casaeditrice`),
  KEY `Copie_ibfk_1` (`fk_isbn`),
  CONSTRAINT `Copie_ibfk_1` FOREIGN KEY (`fk_isbn`) REFERENCES `Libri` (`isbn`),
  CONSTRAINT `fk_casaeditrice` FOREIGN KEY (`fk_casaeditrice`) REFERENCES `CasaEditrice` (`id_casaeditrice`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Copie`
--

LOCK TABLES `Copie` WRITE;
/*!40000 ALTER TABLE `Copie` DISABLE KEYS */;
INSERT INTO `Copie` VALUES (1,100000001,1,5),(2,100000002,1,4),(3,100000003,2,6),(4,100000004,3,8),(5,100000005,3,5),(6,100000006,5,3),(7,100000007,5,4),(8,100000008,7,6),(9,100000009,7,5),(10,100000010,2,7),(11,100000011,3,4),(12,100000012,2,6),(13,100000013,3,3),(14,100000014,4,5),(15,100000015,4,4),(16,100000016,5,6),(17,100000017,5,3),(18,100000018,7,5),(19,100000019,3,4),(20,100000020,8,7);
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
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Generi`
--

LOCK TABLES `Generi` WRITE;
/*!40000 ALTER TABLE `Generi` DISABLE KEYS */;
INSERT INTO `Generi` VALUES (1,'Fantasy'),(2,'Giallo'),(3,'Romanzo'),(4,'Fantascienza'),(5,'Horror'),(6,'Storico'),(7,'Avventura'),(8,'Biografia');
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
INSERT INTO `Libri` VALUES (100000001,'Il Signore degli Anelli','1954-07-29',25,1),(100000002,'Lo Hobbit','1937-09-21',18,1),(100000003,'Assassinio sull Orient Express','1934-01-01',15,2),(100000004,'1984','1949-06-08',14,3),(100000005,'La fattoria degli animali','1945-08-17',12,3),(100000006,'IT','1986-09-15',22,5),(100000007,'Shining','1977-01-28',19,5),(100000008,'Viaggio al centro della Terra','1864-11-25',16,7),(100000009,'Ventimila leghe sotto i mari','1870-06-20',17,7),(100000010,'Il codice da Vinci','2003-03-18',20,2),(100000011,'Il barone rampante','1957-01-01',13,3),(100000012,'Il nome della rosa','1980-01-01',21,2),(100000013,'Orgoglio e pregiudizio','1813-01-28',14,3),(100000014,'Fondazione','1951-06-01',18,4),(100000015,'Io robot','1950-12-02',16,4),(100000016,'Dracula','1897-05-26',15,5),(100000017,'Frankenstein','1818-01-01',14,5),(100000018,'Il giro del mondo','1873-01-01',16,7),(100000019,'La coscienza di Zeno','1923-01-01',17,3),(100000020,'Il diario di Anna Frank','1947-06-25',12,8);
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
INSERT INTO `Libri_Autori` VALUES (100000001,1),(100000002,1),(100000003,2),(100000004,3),(100000005,3),(100000006,4),(100000007,4),(100000016,4),(100000008,5),(100000009,5),(100000017,5),(100000018,5),(100000010,6),(100000011,7),(100000019,7),(100000012,8),(100000020,8),(100000013,9),(100000014,10),(100000015,10);
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
  PRIMARY KEY (`id_nazionalita`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Nazionalita`
--

LOCK TABLES `Nazionalita` WRITE;
/*!40000 ALTER TABLE `Nazionalita` DISABLE KEYS */;
INSERT INTO `Nazionalita` VALUES (1,'Italiana'),(2,'Inglese'),(3,'Americana'),(4,'Francese'),(5,'Tedesca'),(6,'Spagnola'),(7,'Russa'),(8,'Giapponese'),(9,'Colombiana'),(10,'Brasiliana');
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
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Prestiti`
--

LOCK TABLES `Prestiti` WRITE;
/*!40000 ALTER TABLE `Prestiti` DISABLE KEYS */;
INSERT INTO `Prestiti` VALUES (1,'In corso',100000001,1,1),(2,'Restituito',100000002,2,2),(3,'In corso',100000003,3,1),(4,'Restituito',100000004,4,3),(5,'In ritardo',100000005,5,2),(6,'In corso',100000006,6,1),(7,'Restituito',100000007,7,2),(8,'In corso',100000008,8,1),(9,'In ritardo',100000009,9,3),(10,'Restituito',100000010,10,1),(11,'In corso',100000011,1,2),(12,'Restituito',100000012,2,1),(13,'In corso',100000013,3,2),(14,'In ritardo',100000014,4,3),(15,'Restituito',100000015,5,1),(16,'In corso',100000016,6,2),(17,'Restituito',100000017,7,1),(18,'In corso',100000018,8,2),(19,'In ritardo',100000019,9,3),(20,'Restituito',100000020,10,1);
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
  `email` varchar(16) DEFAULT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `cognome` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idUtente`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Utenti`
--

LOCK TABLES `Utenti` WRITE;
/*!40000 ALTER TABLE `Utenti` DISABLE KEYS */;
INSERT INTO `Utenti` VALUES (1,'anna@mail.it','Anna','Rossi'),(2,'luca@mail.it','Luca','Bianchi'),(3,'mario@mail.it','Mario','Verdi'),(4,'giulia@mail.it','Giulia','Neri'),(5,'paolo@mail.it','Paolo','Ferrari'),(6,'sara@mail.it','Sara','Romano'),(7,'marco@mail.it','Marco','Gallo'),(8,'elena@mail.it','Elena','Costa'),(9,'andrea@mail.it','Andrea','Fontana'),(10,'chiara@mail.it','Chiara','Moretti');
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
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Vendite`
--

LOCK TABLES `Vendite` WRITE;
/*!40000 ALTER TABLE `Vendite` DISABLE KEYS */;
INSERT INTO `Vendite` VALUES (1,1,1,'2026-01-10'),(2,2,2,'2026-01-12'),(3,3,3,'2026-01-15'),(4,4,4,'2026-01-18'),(5,5,5,'2026-01-20'),(6,6,6,'2026-02-01'),(7,7,7,'2026-02-05'),(8,8,8,'2026-02-10'),(9,9,9,'2026-02-15'),(10,10,10,'2026-02-20'),(11,1,2,'2026-03-01'),(12,3,4,'2026-03-05'),(13,5,6,'2026-03-10'),(14,7,8,'2026-03-15'),(15,9,10,'2026-03-20');
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

-- Dump completed on 2026-11-04 16:39:07
