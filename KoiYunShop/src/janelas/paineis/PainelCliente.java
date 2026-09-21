package janelas.paineis;

import dao.ClientesDAO;
import janelas.ModalCadastroBase;
import janelas.componentes.FormCliente;
import janelas.componentes.FormVenda;
import modelo.Cliente;

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
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Painel Esquerdo (Botões e Ações)
        add(criarPainelEsquerdo(), BorderLayout.WEST);

        // 2. Painel Direito (Pesquisa + Tabela)
        add(criarPainelDireito(), BorderLayout.CENTER);

        // 3. Carrega os dados iniciais do banco
        carregarTabela();
    }

    private JPanel criarPainelEsquerdo() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setPreferredSize(new Dimension(180, 0));

        // Criando os botões
        btnNovo = new JButton("Novo Cliente");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnAtualizar = new JButton("Atualizar Tabela");
        btnNovaVenda = new JButton("+ Nova Venda");

        // Estilizando larguras
        Dimension maxDim = new Dimension(170, 35);
        btnNovo.setMaximumSize(maxDim);
        btnEditar.setMaximumSize(maxDim);
        btnExcluir.setMaximumSize(maxDim);
        btnAtualizar.setMaximumSize(maxDim);
        btnNovaVenda.setMaximumSize(maxDim);

        // Adicionando componentes
        painel.add(btnNovo);
        painel.add(Box.createVerticalStrut(8));
        painel.add(btnEditar);
        painel.add(Box.createVerticalStrut(8));
        painel.add(btnExcluir);
        painel.add(Box.createVerticalStrut(8));
        painel.add(btnAtualizar);
        
        painel.add(Box.createVerticalStrut(25)); // Espaçamento para o atalho
        painel.add(new JSeparator(JSeparator.HORIZONTAL));
        painel.add(Box.createVerticalStrut(10));
        
        painel.add(btnNovaVenda);

        // --- EVENTOS DOS BOTÕES ---

        btnNovo.addActionListener(e -> abrirModalCadastro(null));

        btnEditar.addActionListener(e -> editarSelecionado());

        btnExcluir.addActionListener(e -> excluirSelecionado());

        btnAtualizar.addActionListener(e -> carregarTabela());

        btnNovaVenda.addActionListener(e -> iniciarVendaParaCliente());

        return painel;
    }

    private JPanel criarPainelDireito() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));

        // Sub-painel topo (Barra de Pesquisa)
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.add(new JLabel("Pesquisar Cliente:"));
        txtPesquisa = new JTextField(20);
        
        // Listener de busca em tempo real
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filtrarTabela(txtPesquisa.getText());
            }
        });
        
        painelBusca.add(txtPesquisa);
        painel.add(painelBusca, BorderLayout.NORTH);

        // Configuração da JTable (Campos baseados na tabela clientes do BD)
        String[] colunas = {"ID", "Nome", "CPF/CNPJ", "Telefone", "E-mail", "Cidade/UF"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impede edição direta na célula da tabela
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
        painel.add(scrollPane, BorderLayout.CENTER);

        return painel;
    }

    // --- MÉTODOS DE LÓGICA E BANCO ---

    public void carregarTabela() {
        modeloTabela.setRowCount(0); // Limpa a tabela
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
        // Método simples de busca ou recarga filtrada do DAO
        if (termo.trim().isEmpty()) {
            carregarTabela();
            return;
        }
        
        try {
            ClientesDAO dao = new ClientesDAO();
            List<Cliente> lista = dao.buscarClientePorNome(termo); // Requer método buscarPorNome no DAO

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
        // 1. Instancia o painel do formulário
        FormCliente formCliente = new FormCliente();

        // 2. Se for edição, utiliza o seu método específico para preencher o formulário
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

        // 3. Captura a janela principal
        Frame framePai = (Frame) SwingUtilities.getWindowAncestor(this);

        // 4. Instancia o ModalCadastroBase
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

        // 5. Ajusta e exibe o modal
        modal.pack();
        modal.setLocationRelativeTo(framePai);
        modal.setVisible(true);

        // 6. Atualiza a tabela assim que o modal for fechado
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
       
        // Pega o ID do cliente selecionado na tabela
        int idCliente = (int) tabela.getValueAt(linha, 0);

        // 1. Cria o formulário de venda vazio
        FormVenda formVenda = new FormVenda();

        // 2. Preenche apenas o ID do Cliente (usando o método novo que criamos)
        formVenda.preencherCliente(String.valueOf(idCliente));

        // 3. Pega a janela principal para ancorar o modal
        Frame framePai = (Frame) SwingUtilities.getWindowAncestor(this);

        // 4. Abre o modal passando o FormVenda
        ModalCadastroBase modal = new ModalCadastroBase(
            framePai, 
            "Nova Venda", 
            formVenda, 
            e -> {
                // Instancia o seu controller
                FormController controller = new FormController();
                
                // Descobre qual é a janela modal atual
                ModalCadastroBase dialogAtual = (ModalCadastroBase) SwingUtilities.getWindowAncestor((Component) e.getSource());
                
                // Chama o método que acabamos de arrumar
                controller.salvarVenda(formVenda, dialogAtual);
            }
        );

        // Exibe o modal na tela
        modal.pack();
        modal.setLocationRelativeTo(framePai);
        modal.setVisible(true);
    }
}