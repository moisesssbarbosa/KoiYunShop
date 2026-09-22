package janelas.paineis;

import dao.ClientesDAO;
import janelas.ModalCadastroBase;
import janelas.componentes.FormCliente;
import janelas.componentes.FormVenda;
import modelo.Cliente;
import janelas.estilos.TemaKoi; // Import da classe de estilização

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controladores.FormController;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class PainelCliente extends JPanel {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField txtPesquisa;

    // Botões de Ação
    private JButton btnNovo;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnAtualizar;
    
    // Botão de Atalho / Fluxo Integrado
    private JButton btnNovaVenda;

    public PainelCliente() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(TemaKoi.COR_FUNDO_TELA);

        // 1. Painel Superior (Botões CRUD + Barra de Pesquisa)
        add(criarPainelTopo(), BorderLayout.NORTH);

        // 2. Painel Central (Tabela de Clientes)
        add(criarPainelTabela(), BorderLayout.CENTER);

        // 3. Carrega os dados iniciais do banco
        carregarTabela();
    }

    private JPanel criarPainelTopo() {
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));
        painelTopo.setOpaque(false);

        // --- LINHA 1: BOTÕES DE AÇÃO (CRUD) NO TOPO ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        painelBotoes.setOpaque(false);

        btnNovo = new JButton("+ Novo Cliente");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnAtualizar = new JButton("Atualizar Tabela");
        btnNovaVenda = new JButton("+ Nova Venda");

        // Aplicação da Estilização TemaKoi nos Botões
        TemaKoi.estilizarBotaoCrud(btnNovo, TemaKoi.COR_VERDE);
        TemaKoi.estilizarBotaoCrud(btnEditar, TemaKoi.COR_AZUL);
        TemaKoi.estilizarBotaoCrud(btnExcluir, TemaKoi.COR_VERMELHO);
        TemaKoi.estilizarBotaoCrud(btnAtualizar, new Color(108, 117, 125)); // Cinza neutro
        TemaKoi.estilizarBotaoCrud(btnNovaVenda, TemaKoi.COR_LARANJA);

        painelBotoes.add(btnNovo);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(Box.createHorizontalStrut(15)); // Espaçador visual
        painelBotoes.add(btnNovaVenda);

        // --- LINHA 2: BARRA DE PESQUISA ---
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        painelBusca.setOpaque(false);

        JLabel lblPesquisa = new JLabel("Pesquisar Cliente:");
        lblPesquisa.setFont(TemaKoi.FONTE_INPUTS);
        lblPesquisa.setForeground(TemaKoi.COR_TEXTO_ESCURO);

        txtPesquisa = new JTextField(25);
        TemaKoi.estilizarCampoTexto(txtPesquisa);

        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filtrarTabela(txtPesquisa.getText());
            }
        });

        painelBusca.add(lblPesquisa);
        painelBusca.add(txtPesquisa);

        // Adiciona as duas linhas no container superior
        painelTopo.add(painelBotoes);
        painelTopo.add(Box.createVerticalStrut(5));
        painelTopo.add(painelBusca);

        // Eventos dos Botões
        btnNovo.addActionListener(e -> abrirModalCadastro(null));
        btnEditar.addActionListener(e -> editarSelecionado());
        btnExcluir.addActionListener(e -> excluirSelecionado());
        btnAtualizar.addActionListener(e -> carregarTabela());
        btnNovaVenda.addActionListener(e -> iniciarVendaParaCliente());

        return painelTopo;
    }

    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setOpaque(false);

        String[] colunas = {"ID", "Nome", "CPF/CNPJ", "Telefone", "E-mail", "Cidade/UF"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Estilização da Tabela
        TemaKoi.estilizarTabela(tabela);

        // Evento de Duplo Clique para Editar
        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tabela.getSelectedRow() != -1) {
                    editarSelecionado();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        scrollPane.getViewport().setBackground(Color.WHITE);

        painel.add(scrollPane, BorderLayout.CENTER);

        return painel;
    }

    // --- MÉTODOS DE LÓGICA E BANCO (MANTIDOS 100% INTACTOS) ---

    public void carregarTabela() {
        modeloTabela.setRowCount(0);
        try {
            ClientesDAO dao = new ClientesDAO();
            List<Cliente> lista = dao.listarTodos();

            for (Cliente c : lista) {
                modeloTabela.addRow(new Object[]{
                    c.getIdCliente(),
                    c.getNome(),
                    c.getCpfCnpj(),
                    c.getTelefone(),
                    c.getEmail(),
                    c.getCidadeEstado()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtrarTabela(String termo) {
        if (termo.trim().isEmpty()) {
            carregarTabela();
            return;
        }
        
        try {
            ClientesDAO dao = new ClientesDAO();
            List<Cliente> lista = dao.buscarClientePorNome(termo);

            modeloTabela.setRowCount(0);
            for (Cliente c : lista) {
                modeloTabela.addRow(new Object[]{
                    c.getIdCliente(), c.getNome(), c.getCpfCnpj(), c.getTelefone(), c.getEmail(), c.getCidadeEstado()
                });
            }
        } catch (SQLException e) {
            // Trata exceção de busca
        }
    }

    private void abrirModalCadastro(Cliente clienteParaEditar) {
        FormCliente formCliente = new FormCliente();

        if (clienteParaEditar != null) {
            formCliente.carregarDadosParaEdicao(
                clienteParaEditar.getIdCliente(),
                clienteParaEditar.getNome(),
                clienteParaEditar.getCpfCnpj(),
                clienteParaEditar.getTelefone(),
                clienteParaEditar.getEmail(),
                clienteParaEditar.getCidadeEstado()
            );
        }

        Frame framePai = (Frame) SwingUtilities.getWindowAncestor(this);

        ModalCadastroBase modal = new ModalCadastroBase(
            framePai, 
            clienteParaEditar == null ? "Cadastrar Cliente" : "Editar Cliente", 
            formCliente, 
            e -> {
                FormController controller = new FormController();
                ModalCadastroBase dialogAtual = (ModalCadastroBase) SwingUtilities.getWindowAncestor((Component) e.getSource());
                controller.salvarCliente(formCliente, dialogAtual);
            }
        );

        modal.pack();
        modal.setLocationRelativeTo(framePai);
        modal.setVisible(true);

        carregarTabela();
    }

    private void editarSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idCliente = (int) tabela.getValueAt(linha, 0);
        
        try {
            ClientesDAO dao = new ClientesDAO();
            Cliente cliente = dao.buscarClientePorId(idCliente);
            if (cliente != null) {
                abrirModalCadastro(cliente);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCliente = (int) tabela.getValueAt(linha, 0);
        String nome = (String) tabela.getValueAt(linha, 1);

        int confirmacao = JOptionPane.showConfirmDialog(
            this, 
            "Tem certeza que deseja excluir o cliente '" + nome + "'?", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                ClientesDAO dao = new ClientesDAO();
                dao.deletar(idCliente);
                carregarTabela();
                JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir (verifique se o cliente possui vendas vinculadas): " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void iniciarVendaParaCliente() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para iniciar a venda.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
       
        int idCliente = (int) tabela.getValueAt(linha, 0);

        FormVenda formVenda = new FormVenda();
        formVenda.preencherCliente(String.valueOf(idCliente));

        Frame framePai = (Frame) SwingUtilities.getWindowAncestor(this);

        ModalCadastroBase modal = new ModalCadastroBase(
            framePai, 
            "Nova Venda", 
            formVenda, 
            e -> {
                FormController controller = new FormController();
                ModalCadastroBase dialogAtual = (ModalCadastroBase) SwingUtilities.getWindowAncestor((Component) e.getSource());
                controller.salvarVenda(formVenda, dialogAtual);
            }
        );

        modal.pack();
        modal.setLocationRelativeTo(framePai);
        modal.setVisible(true);
    }
}