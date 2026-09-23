-- Remove as tabelas se elas já existirem (respeitando a ordem de dependência)
DROP TABLE IF EXISTS itens_venda CASCADE;
DROP TABLE IF EXISTS movimentacoes CASCADE;
DROP TABLE IF EXISTS vendas CASCADE;
DROP TABLE IF EXISTS peixes CASCADE;
DROP TABLE IF EXISTS lagos CASCADE;
DROP TABLE IF EXISTS insumos CASCADE;
DROP TABLE IF EXISTS clientes CASCADE;

-- 1. Tabela Clientes
CREATE TABLE clientes (
  id_cliente SERIAL PRIMARY KEY,
  nome VARCHAR(45) NOT NULL,
  cpf_cnpj VARCHAR(14) NOT NULL,
  telefone VARCHAR(30) NOT NULL,
  email VARCHAR(45) NOT NULL,
  cidade_estado VARCHAR(45) NOT NULL
);

-- 2. Tabela Insumos
CREATE TABLE insumos (
  id_insumo SERIAL PRIMARY KEY,
  nome_insumo VARCHAR(45) NOT NULL,
  quantidade_atual_kg DECIMAL(5,2) NOT NULL,
  quantidade_minima_alerta DECIMAL(5,2) NOT NULL,
  preco_custo_por_kg DECIMAL(5,2) NOT NULL
);

-- 3. Tabela Lagos
CREATE TABLE lagos (
  id_lago SERIAL PRIMARY KEY,
  nome_lago VARCHAR(20) NOT NULL,
  capacidade_litros DECIMAL(5,2) NOT NULL,
  tipo VARCHAR(14) NOT NULL,
  status_agua VARCHAR(14) NOT NULL,
  temperatura DECIMAL(5,2) NOT NULL
);

-- 4. Tabela Peixes
CREATE TABLE peixes (
  id_peixe SERIAL PRIMARY KEY,
  codigo_verificador INT NOT NULL,
  variedade VARCHAR(20) NOT NULL,
  data_entrada DATE NOT NULL DEFAULT CURRENT_DATE,
  tamanho_cm DECIMAL(5,2) NOT NULL,
  preco_venda DECIMAL(5,2) NOT NULL,
  status VARCHAR(10) NOT NULL,
  id_lago_fk INT,
  CONSTRAINT fk_id_lago FOREIGN KEY (id_lago_fk) REFERENCES lagos (id_lago) ON DELETE CASCADE
);

-- 5. Tabela Vendas
CREATE TABLE vendas (
  id_venda SERIAL PRIMARY KEY,
  data_venda DATE NOT NULL DEFAULT CURRENT_DATE,
  valor_total DECIMAL(5,1) NOT NULL,
  forma_pagamento VARCHAR(30) NOT NULL,
  status_entrega VARCHAR(16) NOT NULL,
  id_cliente_fk INT,
  CONSTRAINT fk_id_cliente FOREIGN KEY (id_cliente_fk) REFERENCES clientes (id_cliente) ON DELETE CASCADE
);

-- 6. Tabela Itens Venda
CREATE TABLE itens_venda (
  id_item_venda SERIAL PRIMARY KEY,
  preco DECIMAL(5,2) NOT NULL,
  id_venda_fk INT,
  id_peixe_fk INT,
  CONSTRAINT fk_id_venda FOREIGN KEY (id_venda_fk) REFERENCES vendas (id_venda),
  CONSTRAINT fk_id_peixe FOREIGN KEY (id_peixe_fk) REFERENCES peixes (id_peixe) ON DELETE CASCADE
);

-- 7. Tabela Movimentações
CREATE TABLE movimentacoes (
  id_movimentacao SERIAL PRIMARY KEY,
  data_movimentacao DATE NOT NULL DEFAULT CURRENT_DATE,
  categoria VARCHAR(20) NOT NULL,
  descricao VARCHAR(60) NOT NULL,
  id_insumo_fk INT,
  valor DECIMAL(5,2) NOT NULL,
  CONSTRAINT fk_id_insumo FOREIGN KEY (id_insumo_fk) REFERENCES insumos (id_insumo) ON DELETE CASCADE
);