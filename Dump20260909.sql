CREATE DATABASE  IF NOT EXISTS `koiyunshop` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `koiyunshop`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: koiyunshop
-- ------------------------------------------------------
-- Server version	8.0.45

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

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `id_cliente` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `cpf_cnpj` varchar(14) NOT NULL,
  `telefone` varchar(30) NOT NULL,
  `email` varchar(45) NOT NULL,
  `cidade_estado` varchar(45) NOT NULL,
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `insumos`
--

DROP TABLE IF EXISTS `insumos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `insumos` (
  `id_insumo` int NOT NULL AUTO_INCREMENT,
  `nome_insumo` varchar(45) NOT NULL,
  `quantidade_atual_kg` decimal(5,2) NOT NULL,
  `quantidade_minima_alerta` decimal(5,2) NOT NULL,
  `preco_custo_por_kg` decimal(5,2) NOT NULL,
  PRIMARY KEY (`id_insumo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `insumos`
--

LOCK TABLES `insumos` WRITE;
/*!40000 ALTER TABLE `insumos` DISABLE KEYS */;
/*!40000 ALTER TABLE `insumos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itens_venda`
--

DROP TABLE IF EXISTS `itens_venda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `itens_venda` (
  `id_item_venda` int NOT NULL AUTO_INCREMENT,
  `preco` decimal(5,2) NOT NULL,
  `id_venda_fk` int DEFAULT NULL,
  `id_peixe_fk` int DEFAULT NULL,
  PRIMARY KEY (`id_item_venda`),
  KEY `fk_id_venda` (`id_venda_fk`),
  KEY `fk_id_peixe` (`id_peixe_fk`),
  CONSTRAINT `fk_id_peixe` FOREIGN KEY (`id_peixe_fk`) REFERENCES `peixes` (`id_peixe`) ON DELETE CASCADE,
  CONSTRAINT `fk_id_venda` FOREIGN KEY (`id_venda_fk`) REFERENCES `vendas` (`id_venda`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itens_venda`
--

LOCK TABLES `itens_venda` WRITE;
/*!40000 ALTER TABLE `itens_venda` DISABLE KEYS */;
/*!40000 ALTER TABLE `itens_venda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lagos`
--

DROP TABLE IF EXISTS `lagos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lagos` (
  `id_lago` int NOT NULL AUTO_INCREMENT,
  `nome_lago` varchar(20) NOT NULL,
  `capacidade_litros` decimal(5,2) NOT NULL,
  `tipo` varchar(14) NOT NULL,
  `status_agua` varchar(14) NOT NULL,
  `temperatura` decimal(5,2) NOT NULL,
  PRIMARY KEY (`id_lago`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lagos`
--

LOCK TABLES `lagos` WRITE;
/*!40000 ALTER TABLE `lagos` DISABLE KEYS */;
/*!40000 ALTER TABLE `lagos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimentacoes`
--

DROP TABLE IF EXISTS `movimentacoes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimentacoes` (
  `id_movimentacao` int NOT NULL AUTO_INCREMENT,
  `data_movimentacao` date NOT NULL DEFAULT (curdate()),
  `categoria` varchar(20) NOT NULL,
  `descricao` varchar(60) NOT NULL,
  `id_insumo_fk` int DEFAULT NULL,
  `valor` decimal(5,2) NOT NULL,
  PRIMARY KEY (`id_movimentacao`),
  KEY `fk_id_insumo` (`id_insumo_fk`),
  CONSTRAINT `fk_id_insumo` FOREIGN KEY (`id_insumo_fk`) REFERENCES `insumos` (`id_insumo`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimentacoes`
--

LOCK TABLES `movimentacoes` WRITE;
/*!40000 ALTER TABLE `movimentacoes` DISABLE KEYS */;
/*!40000 ALTER TABLE `movimentacoes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `peixes`
--

DROP TABLE IF EXISTS `peixes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `peixes` (
  `id_peixe` int NOT NULL AUTO_INCREMENT,
  `codigo_verificador` int NOT NULL,
  `variedade` varchar(20) NOT NULL,
  `data_entrada` date NOT NULL DEFAULT (curdate()),
  `tamanho_cm` decimal(5,2) NOT NULL,
  `preco_venda` decimal(5,2) NOT NULL,
  `status` varchar(10) NOT NULL,
  `id_lago_fk` int DEFAULT NULL,
  PRIMARY KEY (`id_peixe`),
  KEY `fk_id_lago` (`id_lago_fk`),
  CONSTRAINT `fk_id_lago` FOREIGN KEY (`id_lago_fk`) REFERENCES `lagos` (`id_lago`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `peixes`
--

LOCK TABLES `peixes` WRITE;
/*!40000 ALTER TABLE `peixes` DISABLE KEYS */;
/*!40000 ALTER TABLE `peixes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendas`
--

DROP TABLE IF EXISTS `vendas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendas` (
  `id_venda` int NOT NULL AUTO_INCREMENT,
  `data_venda` date NOT NULL DEFAULT (curdate()),
  `valor_total` decimal(5,1) NOT NULL,
  `forma_pagamento` varchar(30) NOT NULL,
  `status_entrega` varchar(16) NOT NULL,
  `id_cliente_fk` int DEFAULT NULL,
  PRIMARY KEY (`id_venda`),
  KEY `fk_id_cliente` (`id_cliente_fk`),
  CONSTRAINT `fk_id_cliente` FOREIGN KEY (`id_cliente_fk`) REFERENCES `clientes` (`id_cliente`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendas`
--

LOCK TABLES `vendas` WRITE;
/*!40000 ALTER TABLE `vendas` DISABLE KEYS */;
/*!40000 ALTER TABLE `vendas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-09-09 19:51:43
