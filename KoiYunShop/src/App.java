// =======================================================================================
// IMPORTAÇÕES OBRIGATÓRIAS DE INTERFACE GRÁFICA, EVENTOS E PERSISTÊNCIA EM DISCO
// =======================================================================================
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.PeixeDAO;
import factory.ConexaoDB;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import janelas.ModalCadastroBase;
// Importações de classes
import janelas.componentes.FormPeixe;
import modelo.Peixe;

// A classe herda de JFrame (Herança): Torna-se uma janela gráfica do sistema operacional
public class App extends JFrame {

    private JTable tabelaGE;
    private DefaultTableModel modeloTabela;
    private JLabel lblReembolso;
    private double totalReembolso = 0.0;

    private JTextField txtNomeCooperadoDoador;
    private JComboBox<String> cbcMateriais;
    private JTextField txtPesoColetado;

    // Constante com o nome fixo da base de dados física no formato CSV
    private final String BANCO_DADOS = "ecotrash_lotes.csv";

    public App() {

        // -----------------------------------------------------------------------------------
        // [PASSO 1]: APLICANDO LOOK AND FEEL NATIVO E ESTRUTURAÇÃO DA JANELA
        // -----------------------------------------------------------------------------------
        try {
            // Ativa o tema moderno nativo do SO do computador (Windows/Linux/Mac)
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Em caso de inconsistência de tema, mantém a interface padrão
        }

        ConexaoDB conexao = new ConexaoDB();

        setTitle("Gestão de Logística Reversa");
        setSize(1980, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza no monitor
        setLayout(new BorderLayout(10, 10)); // Divisão de layout em regiões

        // -----------------------------------------------------------------------------------
        // [PASSO 2]: BARRA DE MENUS ACESSÍVEL COM ATALHOS DE TECLADO
        // -----------------------------------------------------------------------------------
        JMenuBar barraMenu = new JMenuBar();
        
        JMenu menuAtend = new JMenu("Operações");
        menuAtend.setMnemonic(KeyEvent.VK_A); // Alt + O

        JMenuItem itemNovo = new JMenuItem("Lançar Novo Lote...");
        // Atalho global Ctrl + N para focar diretamente no formulário
        itemNovo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));

        JMenuItem itemSair = new JMenuItem("Sair do Sistema");
        itemSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));

        menuAtend.add(itemNovo);
        menuAtend.addSeparator();
        menuAtend.add(itemSair);
        barraMenu.add(menuAtend);
        
        setJMenuBar(barraMenu); // Fixa o menu na janela

        // -----------------------------------------------------------------------------------
        // [PASSO 3]: CABEÇALHO COM ESTILIZAÇÃO E ALTO CONTRASTE (REGIÃO NORTE)
        // -----------------------------------------------------------------------------------
        JPanel pnlTopo = new JPanel();
        pnlTopo.setBackground(new Color(40, 50, 40)); // Cor cinza-chumbo escuro

        JLabel lblTitulo = new JLabel("GESTÃO DE LOGÍSTICA REVERSA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE); // Texto branco para alto contraste (UI)

        pnlTopo.add(lblTitulo);

        // -----------------------------------------------------------------------------------
        // [PASSO 4]: FORMULÁRIO DE CADASTRO COM CAMPOS E SELEÇÕES
        // -----------------------------------------------------------------------------------
        JPanel pnlForm = new JPanel(new GridLayout(3, 2, 10, 8));
        pnlForm.setBackground(new Color(245, 245, 245));

        JLabel lblCli = new JLabel(" Nome do Cooperador:");
        lblCli.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtNomeCooperadoDoador = new JTextField();
        txtNomeCooperadoDoador.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JLabel lblServ = new JLabel(" Tipo de material:");
        lblServ.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        String[] servicos = {"Plástico (R$ 2,50/Kg)", "Papel / Papelão (R$ 1,50/Kg)", "Metal / Alumínio (R$ 6,00/Kg)", "Vidro (R$ 0,80/Kg)"};
        cbcMateriais = new JComboBox<>(servicos);
        cbcMateriais.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JLabel lblVal = new JLabel(" Peso coletado (Kg):");
        estilizarLabel(lblVal);
        txtPesoColetado = new JTextField();
        txtPesoColetado.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        pnlForm.add(lblCli);  pnlForm.add(txtNomeCooperadoDoador);
        pnlForm.add(lblServ); pnlForm.add(cbcMateriais);
        pnlForm.add(lblVal);  pnlForm.add(txtPesoColetado);

        // Agrupa Cabeçalho + Formulário no Painel Norte usando BorderLayout
        JPanel pnlNorteIntegrado = new JPanel(new BorderLayout(5, 5));
        pnlNorteIntegrado.add(pnlTopo, BorderLayout.NORTH);
        pnlNorteIntegrado.add(pnlForm, BorderLayout.CENTER);
        
        add(pnlNorteIntegrado, BorderLayout.NORTH);

        // -----------------------------------------------------------------------------------
        // [PASSO 5]: ESTRUTURAÇÃO DA JTABLE E PAINEL CENTRAL (REGIÃO CENTRO)
        // -----------------------------------------------------------------------------------
        String[] colunas = {"Cooperado", "Material", "Valor Total (R$)"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaGE = new JTable(modeloTabela);
        tabelaGE.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabelaGE.setRowHeight(22); // Aumenta a altura da linha para melhor leitura visual

        add(new JScrollPane(tabelaGE), BorderLayout.CENTER);

        // -----------------------------------------------------------------------------------
        // [PASSO 6]: PAINEL DE BOTÕES DE AÇÃO E FATURAMENTO (REGIÃO SUL)
        // -----------------------------------------------------------------------------------
        JPanel pnlSul = new JPanel(new BorderLayout(10, 10));
        pnlSul.setBackground(new Color(245, 245, 245));

        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlBotoes.setBackground(new Color(245, 245, 245));

        // Botão Cadastrar (Verde)
        JButton btnSalvar = new JButton("Confirmar Lote");
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSalvar.setBackground(new Color(40, 167, 69));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setOpaque(true);
        btnSalvar.setBorderPainted(false);
        btnSalvar.setMnemonic(KeyEvent.VK_C); // Alt + C

        // Botão Excluir (Vermelho)
        JButton btnExcluir = new JButton("Remover Lote");
        btnExcluir.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnExcluir.setBackground(new Color(220, 53, 69));
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setOpaque(true);
        btnExcluir.setBorderPainted(false);
        btnExcluir.setMnemonic(KeyEvent.VK_R); // Alt + R

        pnlBotoes.add(btnSalvar);
        pnlBotoes.add(btnExcluir);

        lblReembolso = new JLabel("Total Reembolsado: R$ 0.00  ");
        lblReembolso.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblReembolso.setForeground(new Color(13, 71, 161)); // Azul corporativo

        pnlSul.add(pnlBotoes, BorderLayout.WEST);
        pnlSul.add(lblReembolso, BorderLayout.EAST);

        add(pnlSul, BorderLayout.SOUTH);

        // -----------------------------------------------------------------------------------
        // [PASSO 7]: CARGA INICIAL AUTOMÁTICA DOS DADOS EM DISCO
        // -----------------------------------------------------------------------------------
        carregarDadosDoDisco();

        // Foco inicial automático no primeiro campo
        txtNomeCooperadoDoador.requestFocus();

        // -----------------------------------------------------------------------------------
        // [PASSO 8]: EVENTOS DOS BOTÕES E LÓGICA DE NEGÓCIO
        // -----------------------------------------------------------------------------------
        
        // Evento 1: Salvar Nova Ordem de Serviço
        btnSalvar.addActionListener(e -> salvarNovoLote());
        

        // Evento 2: Remover Ordem de Serviço Selecionada
        btnExcluir.addActionListener(e -> removerLoteSelecionado());

        // Eventos do Menu
        itemNovo.addActionListener(e -> {
            FormPeixe formPeixe = new FormPeixe();

            //TODO ModalCadastroBase modal = new ModalCadastroBase(
            //TODO     this,
            //TODO     "Novo Peixe"
            //TODO );
        });
        itemSair.addActionListener(e -> System.exit(0));
    }

    // =======================================================================================
    // MÉTODO AUXILIAR 1: VALIDAÇÃO, TRATAMENTO DE ERROS E GRAVAÇÃO DE NOVO LOTE
    // =======================================================================================
    private void salvarNovoLote() {
        String cliente = txtNomeCooperadoDoador.getText().trim();
        String material = (String) cbcMateriais.getSelectedItem();
        String valorTexto = txtPesoColetado.getText().trim().replace(",", ".");

        // 1. Validação de campos vazios
        if (cliente.isEmpty() || valorTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha o Cooperador e o Peso!", "Aviso de Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Trava de segurança para não corromper o arquivo CSV
        if (cliente.contains(";")) {
            JOptionPane.showMessageDialog(this, "O nome do Cooperador não pode conter o caractere ';'", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Tratamento pragmático de exceções para conversão numérica (try-catch)
        try {
            double valorNum = Double.parseDouble(valorTexto);

            switch (material) {
                case "Plástico (R$ 2,50/Kg)" -> {
                    valorNum = (valorNum * 2.50);  
                }
                case "Papel / Papelão (R$ 1,50/Kg)" -> {
                    valorNum = (valorNum * 1.50);
                }
                case "Metal / Alumínio (R$ 6,00/Kg)" -> {
                    valorNum = (valorNum * 6.00);
                }
                case "Vidro (R$ 0,80/Kg)" -> {
                    valorNum = (valorNum * 0.80);
                }
            }

            if (valorNum > 50) {
                JOptionPane.showMessageDialog(this, "Peso superior a 50Kg. Bônus de 10% aplicado.", "Bônus de 10%", JOptionPane.INFORMATION_MESSAGE);
                valorNum += (valorNum / 10);
            }

            // Adiciona visualmente na JTable da tela
            Object[] novaLinha = {cliente, material, String.format("R$ %.2f", valorNum)};
            modeloTabela.addRow(novaLinha);

            // Atualiza a variável de faturamento total
            totalReembolso += valorNum;
            lblReembolso.setText(String.format("Total Faturado: R$ %.2f  ", totalReembolso));

            // Grava a nova linha no arquivo CSV (modo append = true)
            gravarLinhaNoArquivo(cliente, material, valorNum);

            // Limpa os campos e devolve o foco do cursor
            txtNomeCooperadoDoador.setText("");
            txtPesoColetado.setText("");
            txtNomeCooperadoDoador.requestFocus();

            JOptionPane.showMessageDialog(this, "Lote salvo e gravado em disco!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            // Captura erro caso o usuário digite letras no campo de valor
            JOptionPane.showMessageDialog(this, "Digite um número válido para o Peso Coletado (ex: 150.00)!", "Erro de Formatação", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =======================================================================================
    // MÉSTATIC AUXILIAR 2: LEITURA E CARGA DO ARQUIVO CSV PARA A JTABLE
    // =======================================================================================
    private void carregarDadosDoDisco() {
        File arq = new File(BANCO_DADOS);
        if (!arq.exists()) return; // Se o arquivo ainda não existir, encerra em silêncio

        try (FileReader fr = new FileReader(arq);
             BufferedReader br = new BufferedReader(fr)) {

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                
                // Se a linha contiver as 3 colunas corretas
                if (dados.length == 3) {
                    double val = Double.parseDouble(dados[2]);
                    
                    Object[] linhaTabela = {dados[0], dados[1], String.format("R$ %.2f", val)};
                    modeloTabela.addRow(linhaTabela);

                    // Soma ao faturamento total na inicialização
                    totalReembolso += val;
                }
            }

            // Atualiza o rótulo de faturamento na tela
            lblReembolso.setText(String.format("Total Reembolsado: R$ %.2f  ", totalReembolso));

        } catch (IOException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar banco de dados de disco: " + ex.getMessage(), "Erro I/O", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =======================================================================================
    // MÉTODO AUXILIAR 3: GRAVAÇÃO DA LINHA NO ARQUIVO CSV (MODO APPEND)
    // =======================================================================================
    private void gravarLinhaNoArquivo(String cliente, String servico, double valor) {
        File arq = new File(BANCO_DADOS);

        // try-with-resources garante o fechamento automático do arquivo após escrever
        try (FileWriter fw = new FileWriter(arq, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(cliente + ";" + servico + ";" + valor);
            bw.newLine();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar registro no HD: " + ex.getMessage(), "Erro I/O", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =======================================================================================
    // MÉTODO AUXILIAR 4: REMOÇÃO E REESCRITA TOTAL DE SINCRONIZAÇÃO NO DISCO
    // =======================================================================================
    private void removerLoteSelecionado() {
        int linhaSel = tabelaGE.getSelectedRow();

        if (linhaSel == -1) {
            JOptionPane.showMessageDialog(this, "Selecione Lote na tabela para remover!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente remover este Lote?", "Confirmação", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Remove a linha da JTable na RAM
            modeloTabela.removeRow(linhaSel);

            // Reescreve o arquivo no disco com as linhas restantes (false = sobrescrever)
            sincronizarTabelaComDisco();

            // Recalcula o faturamento total do zero
            recalcularReembolso();

            JOptionPane.showMessageDialog(this, "Lote removido e base sincronizada!");
        }
    }

    // =======================================================================================
    // MÉTODO AUXILIAR 5: REESCRITA TOTAL DO ARQUIVO CSV (SOBREESCRITA)
    // =======================================================================================
    private void sincronizarTabelaComDisco() {
        File arq = new File(BANCO_DADOS);

        // new FileWriter(arq, false) -> Apaga o arquivo antigo e reescreve a base limpa
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arq, false))) {

            for (int i = 0; i < modeloTabela.getRowCount(); i++) {
                String cli = (String) modeloTabela.getValueAt(i, 0);
                String serv = (String) modeloTabela.getValueAt(i, 1);
                
                // Tratamento para extrair apenas o número limpo descartando o "R$ " da exibição visual
                String valStr = modeloTabela.getValueAt(i, 2).toString().replace("R$", "").replace(",", ".").trim();

                bw.write(cli + ";" + serv + ";" + valStr);
                bw.newLine();
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao sincronizar dados no HD: " + ex.getMessage(), "Erro I/O", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Recalcula o totalizador do faturamento a partir das linhas visíveis na tabela
    private void recalcularReembolso() {
        totalReembolso = 0.0;
        for (int i = 0; i < modeloTabela.getRowCount(); i++) {
            String valStr = modeloTabela.getValueAt(i, 2).toString().replace("R$", "").replace(",", ".").trim();
            totalReembolso += Double.parseDouble(valStr);
        }
        lblReembolso.setText(String.format("Total Reembolsado: R$ %.2f  ", totalReembolso));
    }

    private void estilizarLabel(JLabel lbl) {
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    };


   // =======================================================================================
    // MÉTODO SALVAR PEIXE
    // =======================================================================================
    private void salvarPeixe(FormPeixe form) {
    try {
        // Validação básica dos campos
        if (!form.isCamposValidos()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Converte os dados da tela para o Objeto Modelo
        //TODO String especie = form.getEspecie();
        //TODO double preco = Double.parseDouble(form.getPreco().replace(",", "."));

        Peixe novoPeixe = new Peixe();

        // Chama o DAO para salvar no Banco de Dados
        PeixeDAO dao = new PeixeDAO();
        dao.salvar(novoPeixe);

        JOptionPane.showMessageDialog(this, "Peixe cadastrado com sucesso!");

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Digite um preço válido (ex: 29.90)!", "Erro de Formatação", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao salvar no banco: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
    }
}

    public static void main(String[] args) {
        new App().setVisible(true);
    }
}