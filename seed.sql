-- =========================================================
-- SCRIPT DE POVOAMENTO DO BANCO DE DADOS (20 REGISTROS/TABELA)
-- =========================================================

-- 1. POVOANDO A TABELA CLIENTES (20 Registros)
INSERT INTO clientes (nome, cpf_cnpj, telefone, email, cidade_estado) VALUES
('Carlos Eduardo Silva', '12345678901', '(51) 99887-1122', 'carlos.silva@email.com', 'Porto Alegre/RS'),
('Mariana Oliveira', '98765432100', '(54) 99123-4455', 'mariana.oli@email.com', 'Caxias do Sul/RS'),
('Roberto Santos', '45678912300', '(51) 98456-7890', 'roberto.santos@email.com', 'Canoas/RS'),
('Fernanda Costa', '32165498711', '(51) 99988-7766', 'nanda.costa@email.com', 'Novo Hamburgo/RS'),
('Lucas Pereira', '65498732122', '(55) 99776-5544', 'lucas.p@email.com', 'Santa Maria/RS'),
('Aline Rodrigues', '78912345633', '(53) 99665-4433', 'aline.rod@email.com', 'Pelotas/RS'),
('Carvalho Aquarismo LTDA', '12345678000199', '(51) 3344-5566', 'contato@carvalho.com', 'Gravataí/RS'),
('Patricia Lima', '85296374144', '(51) 99554-3322', 'patricia.lima@email.com', 'Viamão/RS'),
('Gustavo Mendes', '96385274155', '(54) 99443-2211', 'gustavo.m@email.com', 'Bento Gonçalves/RS'),
('Juliana Barbosa', '14725836966', '(51) 99332-1100', 'juliana.b@email.com', 'São Leopoldo/RS'),
('Thiago Martins', '25836914777', '(51) 99221-0099', 'thiago.m@email.com', 'Montenegro/RS'),
('Camila Souza', '36914725888', '(51) 99110-9988', 'camila.s@email.com', 'Lajeado/RS'),
('Kois & Lagos ME', '98765432000188', '(51) 3211-4455', 'compras@koiselagos.com', 'Porto Alegre/RS'),
('Rafael Ribeiro', '74185296399', '(55) 99009-8877', 'rafael.r@email.com', 'Passo Fundo/RS'),
('Vanessa Castro', '85214796300', '(53) 98998-7766', 'vanessa.c@email.com', 'Rio Grande/RS'),
('Diego Almeida', '96314785211', '(51) 98887-6655', 'diego.a@email.com', 'Guaíba/RS'),
('Beatriz Rocha', '15935725822', '(54) 98776-5544', 'beatriz.r@email.com', 'Erechim/RS'),
('Marcelo Nunes', '35715945633', '(51) 98665-4433', 'marcelo.n@email.com', 'Esteio/RS'),
('Lethicia Ramos', '45685296344', '(51) 98554-3322', 'lethicia.r@email.com', 'Sapucaia do Sul/RS'),
('Aquários do Sul LTDA', '45678912000177', '(51) 3455-6677', 'contato@aquariosdosul.com', 'Canoas/RS');

-- 2. POVOANDO A TABELA INSUMOS (20 Registros)
INSERT INTO insumos (nome_insumo, quantidade_atual_kg, quantidade_minima_alerta, preco_custo_por_kg) VALUES
('Ração Crescimento Koi Premium', 150.00, 30.00, 45.50),
('Ração Realce de Cor Nishikigoi', 80.00, 20.00, 68.00),
('Ração Manutenção Multiestação', 200.00, 50.00, 32.90),
('Ração Alevinos Alta Proteína', 45.00, 15.00, 89.00),
('Condicionador Água Neutra (Pó)', 12.50, 5.00, 120.00),
('Bactérias Nitrificantes Secas', 8.00, 2.50, 210.00),
('Sal Solar Refinado Especial', 500.00, 100.00, 2.50),
('Carvão Ativado Granulado', 60.00, 15.00, 28.00),
('Mídia Biológica Cerâmica (Bag)', 90.00, 25.00, 35.00),
('Ração Germe de Trigo (Wheat Germ)', 75.00, 20.00, 58.00),
('Anti-cloro Concentrado (Pó)', 10.00, 3.00, 95.00),
('Algicida Biológico Seguro', 15.00, 5.00, 140.00),
('Teste de pH Tampão em Pó', 5.00, 1.00, 85.00),
('Vitamina C para Carpas', 7.50, 2.00, 160.00),
('Elemento Filtrante Perlon', 30.00, 10.00, 18.50),
('Ração Medicada Antiparasitária', 25.00, 8.00, 115.00),
('Zeólita para Remoção de Amônia', 120.00, 30.00, 15.00),
('Extrato de Turfa Natural', 18.00, 5.00, 42.00),
('Ração Flutuante Sticks 6mm', 110.00, 25.00, 38.00),
('Suplemento de Cálcio e Mineral', 40.00, 10.00, 52.00);

-- 3. POVOANDO A TABELA LAGOS (20 Registros)
-- Nota: Capacidade limitada a DECIMAL(5,2) -> Máximo 999.99 m³/mil litros
INSERT INTO lagos (nome_lago, capacidade_litros, tipo, status_agua, temperatura) VALUES
('Lago Matrizes 01', 850.00, 'Artificial', 'Pronta', 22.50),
('Lago Engorda A', 600.00, 'Natural', 'Pronta', 23.00),
('Lago Quarentena 01', 150.00, 'Artificial', 'Tratando', 24.50),
('Lago Exposição', 300.00, 'Artificial', 'Pronta', 21.00),
('Lago Alevinagem 01', 250.00, 'Artificial', 'Pronta', 25.00),
('Lago Matrizes 02', 900.00, 'Artificial', 'Pronta', 22.00),
('Lago Engorda B', 750.00, 'Natural', 'Alerta', 23.50),
('Lago Quarentena 02', 150.00, 'Artificial', 'Tratando', 24.00),
('Lago Crescimento 01', 450.00, 'Artificial', 'Pronta', 22.80),
('Lago Crescimento 02', 450.00, 'Artificial', 'Pronta', 22.50),
('Lago Venda Rápida', 200.00, 'Artificial', 'Pronta', 21.50),
('Lago Alevinagem 02', 250.00, 'Artificial', 'Pronta', 25.20),
('Lago Berçário 01', 100.00, 'Artificial', 'Pronta', 26.00),
('Lago Berçário 02', 100.00, 'Artificial', 'Tratando', 26.00),
('Lago isolamento', 120.00, 'Artificial', 'Alerta', 23.00),
('Lago Reserva TÉC', 500.00, 'Natural', 'Pronta', 21.80),
('Lago Showroom', 350.00, 'Artificial', 'Pronta', 20.50),
('Lago Alevinagem 03', 280.00, 'Artificial', 'Pronta', 24.80),
('Lago Engorda C', 800.00, 'Natural', 'Pronta', 23.20),
('Lago Matrizes VIP', 950.00, 'Artificial', 'Pronta', 22.10);

-- 4. POVOANDO A TABELA PEIXES (20 Registros - 20 Variedades Distintas)
INSERT INTO peixes (codigo_verificador, variedade, data_entrada, tamanho_cm, preco_venda, status, id_lago_fk) VALUES
(1001, 'Kohaku', '2026-01-10', 35.50, 450.00, 'Disponivel', 1),
(1002, 'Taisho Sanke', '2026-01-12', 42.00, 680.00, 'Disponivel', 1),
(1003, 'Showa Sanshoku', '2026-01-15', 38.00, 550.00, 'Disponivel', 6),
(1004, 'Tancho', '2026-01-20', 28.50, 890.00, 'Vendido', 4),
(1005, 'Yamabuki Ogon', '2026-02-01', 30.00, 320.00, 'Disponivel', 9),
(1006, 'Shusui', '2026-02-03', 25.00, 280.00, 'Disponivel', 10),
(1007, 'Asagi', '2026-02-05', 40.00, 520.00, 'Vendido', 2),
(1008, 'Shiro Utsuri', '2026-02-10', 32.50, 410.00, 'Disponivel', 9),
(1009, 'Ki Utsuri', '2026-02-12', 22.00, 190.00, 'Quarentena', 3),
(1010, 'Hi Utsuri', '2026-02-15', 29.00, 310.00, 'Disponivel', 10),
(1011, 'Shiro Bekko', '2026-02-18', 26.50, 220.00, 'Disponivel', 11),
(1012, 'Aka Bekko', '2026-02-20', 24.00, 200.00, 'Quarentena', 8),
(1013, 'Goshiki', '2026-02-22', 31.00, 460.00, 'Disponivel', 4),
(1014, 'Budo Goromo', '2026-02-25', 36.00, 750.00, 'Disponivel', 20),
(1015, 'Gin Matsuba', '2026-03-01', 33.00, 380.00, 'Vendido', 17),
(1016, 'Kujaku', '2026-03-02', 27.50, 340.00, 'Disponivel', 11),
(1017, 'Chagoi', '2026-03-05', 50.00, 600.00, 'Disponivel', 7),
(1018, 'Soragoi', '2026-03-08', 48.00, 580.00, 'Disponivel', 7),
(1019, 'Karashigoi', '2026-03-10', 45.00, 620.00, 'Disponivel', 19),
(1020, 'Platinum Ogon', '2026-03-12', 29.50, 350.00, 'Disponivel', 17);

-- 5. POVOANDO A TABELA VENDAS (20 Registros)
-- Formas de pagamento: debito, credito, pix, boleto
-- Status entrega: entregue, em rota, preparando
INSERT INTO vendas (data_venda, valor_total, forma_pagamento, status_entrega, id_cliente_fk) VALUES
('2026-02-01', 890.0, 'pix', 'entregue', 1),
('2026-02-03', 520.0, 'credito', 'entregue', 2),
('2026-02-05', 380.0, 'debito', 'entregue', 3),
('2026-02-10', 450.0, 'pix', 'entregue', 4),
('2026-02-12', 680.0, 'credito', 'entregue', 5),
('2026-02-15', 320.0, 'boleto', 'entregue', 6),
('2026-02-18', 950.0, 'pix', 'entregue', 7),
('2026-02-20', 280.0, 'debito', 'entregue', 8),
('2026-02-22', 410.0, 'credito', 'entregue', 9),
('2026-02-25', 600.0, 'pix', 'entregue', 10),
('2026-03-01', 750.0, 'boleto', 'em rota', 11),
('2026-03-03', 340.0, 'pix', 'em rota', 12),
('2026-03-05', 850.0, 'credito', 'em rota', 13),
('2026-03-08', 220.0, 'debito', 'preparando', 14),
('2026-03-10', 580.0, 'pix', 'preparando', 15),
('2026-03-12', 310.0, 'credito', 'preparando', 16),
('2026-03-15', 620.0, 'boleto', 'preparando', 17),
('2026-03-18', 460.0, 'pix', 'preparando', 18),
('2026-03-20', 350.0, 'credito', 'preparando', 19),
('2026-03-22', 500.0, 'debito', 'preparando', 20);

-- 6. POVOANDO A TABELA ITENS_VENDA (20 Registros)
INSERT INTO itens_venda (preco, id_venda_fk, id_peixe_fk) VALUES
(890.00, 1, 4),
(520.00, 2, 7),
(380.00, 3, 15),
(450.00, 4, 1),
(680.00, 5, 2),
(320.00, 6, 5),
(550.00, 7, 3),
(280.00, 8, 6),
(410.00, 9, 8),
(600.00, 10, 17),
(750.00, 11, 14),
(340.00, 12, 16),
(620.00, 13, 19),
(220.00, 14, 11),
(580.00, 15, 18),
(310.00, 16, 10),
(620.00, 17, 19),
(460.00, 18, 13),
(350.00, 19, 20),
(200.00, 20, 12);

-- 7. POVOANDO A TABELA MOVIMENTAÇÕES (20 Registros)
-- Categorias: Insumos, Despesas, Aquisições
INSERT INTO movimentacoes (data_movimentacao, categoria, descricao, id_insumo_fk, valor) VALUES
('2026-01-05', 'Insumos', 'Compra de Ração Crescimento Koi', 1, 455.00),
('2026-01-08', 'Despesas', 'Pagamento da conta de energia elétrica', NULL, 850.00),
('2026-01-12', 'Insumos', 'Aquisição de Nitrificantes em Pó', 6, 420.00),
('2026-01-15', 'Aquisições', 'Compra de novo lote de alevinos', NULL, 950.00),
('2026-01-20', 'Insumos', 'Reposição de Sal Refinado', 7, 125.00),
('2026-01-25', 'Despesas', 'Manutenção da bomba de água do Lago 01', NULL, 320.00),
('2026-02-02', 'Insumos', 'Carvão ativado para sistema de filtragem', 8, 280.00),
('2026-02-05', 'Insumos', 'Compra de Ração Realce de Cor', 2, 680.00),
('2026-02-10', 'Despesas', 'Serviço de análise biológica de água', NULL, 250.00),
('2026-02-14', 'Aquisições', 'Aquisição de matriz Koi Kohaku Importada', NULL, 990.00),
('2026-02-18', 'Insumos', 'Filtro perlon e mídias cerâmicas', 9, 350.00),
('2026-02-22', 'Insumos', 'Ração Medicada Antiparasitária', 16, 230.00),
('2026-02-28', 'Despesas', 'Pagamento da conta de água do criadouro', NULL, 480.00),
('2026-03-02', 'Insumos', 'Suplemento de Cálcio e Vitamina C', 14, 320.00),
('2026-03-06', 'Insumos', 'Sacos de Zeólita para Amônia', 17, 300.00),
('2026-03-10', 'Despesas', 'Troca de lâmpadas UV dos esterilizadores', NULL, 640.00),
('2026-03-14', 'Insumos', 'Ração Wheat Germ Multiestação', 10, 580.00),
('2026-03-18', 'Aquisições', 'Acessórios para transporte de peixes (Sacos/O2)', NULL, 410.00),
('2026-03-20', 'Insumos', 'Algicida biológico preventivo', 12, 280.00),
('2026-03-22', 'Despesas', 'Material de limpeza e sanitização de tanques', NULL, 190.00);