# 🎏 Koi Yun Shop — Sistema de Gestão de Carpas Koi

O Koi Yun Shop é um sistema desktop desenvolvido em Java para o gerenciamento completo de um comércio e criadouro especializado em carpas ornamentais (Nishikigoi).

O projeto foi estruturado seguindo os padrões MVC (Model-View-Controller), DAO (Data Access Object) e arquiteto de componentização reutilizável com Java Swing.


## 📌 Funcionalidades do Sistema

   + **🐟 Gestão de Peixes:** Cadastro e controle do plantel com variedades pré-definidas    (Kohaku, Sanke, Showa, Ogon, etc.), tamanho (cm), data de entrada, preço, status    (Disponível, Vendido, Quarentena) e vínculo com o lago.
   
   + **🏞️ Controle de Lagos:** Monitoramento dos tanques de criação com dados de 
   capacidade em litros, tipo (Natural ou Artificial), temperatura (°C) e qualidade da água    (Pronta, Tratando, Alerta).
   
   + **🛒 Gestão de Vendas:** Registro de vendas associadas aos clientes, com formas    depagamento (Débito, Crédito, PIX, Boleto) e acompanhamento de entregas (Entregue,    Emrota, Preparando).
   
   + **💰 Movimentação Financeira:** Controle de entradas e saídas categorizadas(Insumos,    Despesas, Aquisições) com vínculo opcional a insumos do estoque.
   
   + **👥 Clientes e Insumos:** Módulos de cadastro para suporte ao atendimento econtrole    de estoque de produtos e rações.


## 🛠️ Tecnologias Utilizadas

   + **Linguagem:** Java 17+
   
   + **Interface Gráfica:** Java Swing / AWT
   
   + **Persistência de Dados:** JDBC (Java Database Connectivity)
   
   + **Banco de Dados:** PostgreSQL
   
   + **Design Pattern:**
    MVC (Model-View-Controller) para separação de responsabilidades.
    DAO (Data Access Object) para isolamento das queries SQL.
    Factory Method (ConexaoDB) para gestão da conexão com banco.
    Estratégia de Painéis Dinâmicos: JDialog base reaproveitável recebendo formulários    modulares (JPanel).

## 📂 Estrutura do Projeto
```plaintext
src/
├── .vscode/                      # Configurações do ambiente no VS Code
├── controladores/                # Classes Controladoras (Regras de Negócio)
├── dao/                          # Objetos de Acesso a Dados (Queries SQL / JDBC)
├── factory/                      # Fábrica de Conexões
│   └── ConexaoDB.java            # Gerenciador de Conexão com o Banco de Dados
├── imagens/                      # Recursos visuais e ícones do sistema
├── janelas/                      # Camada de Apresentação (Swing / AWT)
│   ├── componentes/              # Formulários modulares para os cadastros
│   ├── estilos/                  # Customização estética e padrão visual
│   │   └── TemaKoi.java          # Estilização global de componentes (Cores, Fontes)
│   ├── paineis/                  # Painéis auxiliares e visões secundárias
│   ├── ModalCadastroBase.java    # Janela JDialog base genérica para os formulários
│   └── TelaPrincipal.java        # Dashboard e janela principal da aplicação
├── modelo/                       # Classes de Entidades (Peixe, Lago, Venda, etc.)
└── App.java                      # Ponto de entrada da aplicação (Método main)
```

## 🎨 Padronização Visual (TemaKoi)

Para manter a consistência estética do sistema, as telas e formulários utilizam a classe utilitária TemaKoi, que aplica:

   + Cores temáticas para fundo, botões e seleção de tabelas.
   
   + Tipografia padronizada e legível.
   
   + Estilização automática para JTextField, JComboBox, JLabel e JTable.



## 🗄️ Modelo do Banco de Dados (Exemplo de Tabelas)

O script do banco está disponível na raiz do projeto para informações detalhadas.


## 🚀 Como Executar o Projeto

1. Clonar o Repositório:
```bash
git clone https://github.com/seu-usuario/koi-yun-shop.git
``` 
2. Configurar o Banco de Dados:

   + Execute o script SQL acima no PgAdmin4.
   
   + Ajuste as credenciais (URL, usuário e senha) na classe factory.ConexaoDB.

3. Importar em uma IDE:
   + Abra o projeto na sua IDE de preferência.

   + Adicione o driver JDBC (postgresql-42.7.10.jar) no Build Path do projeto.

4. Executar:
   + Execute a classe App.java.

👥 Autores & Projeto Integrador

   + Projeto Integrador: Desenvolvimento de Sistema de Gestão Comercial Java Desktop

   + Instituição: Senac - Técnico em Desenvolvimento de Sistemas

   + Desenvolvido por: Lucas Machado e Moisés Barbosa

   + Contatos: [LinkedIn Moisés](www.linkedin.com/in/moisésssb) - [LinkedIn Lucas](https://www.linkedin.com/in/lucas-machado-telles-da-luz-a3b199273?utm_source=share_via&utm_content=profile&utm_medium=member_android)