package janelas.paineis;

import dao.LagoDAO; // Certifique-se de que o pacote e nome estão corretos
import janelas.ModalCadastroBase;
import janelas.componentes.FormLago;
import modelo.Lago; // Certifique-se de que o pacote e nome estão corretos
import controladores.FormController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class PainelLago extends JPanel {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField txtPesquisa;

    // Botões de Ação
    private JButton btnNovo;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnAtualizar;

    public PainelLago() {
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
        btnNovo = new JButton("Novo Lago");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnAtualizar = new JButton("Atualizar Tabela");

        // Estilizando larguras
        Dimension maxDim = new Dimension(170, 35);
        btnNovo.setMaximumSize(maxDim);
        btnEditar.setMaximumSize(maxDim);
        btnExcluir.setMaximumSize(maxDim);
        btnAtualizar.setMaximumSize(maxDim);

        // Adicionando componentes
        painel.add(btnNovo);
        painel.add(Box.createVerticalStrut(8));
        painel.add(btnEditar);
        painel.add(Box.createVerticalStrut(8));
        painel.add(btnExcluir);
        painel.add(Box.createVerticalStrut(8));
        painel.add(btnAtualizar);

        // --- EVENTOS DOS BOTÕES ---
        btnNovo.addActionListener(e -> abrirModalCadastro(null));
        btnEditar.addActionListener(e -> editarSelecionado());
        btnExcluir.addActionListener(e -> excluirSelecionado());
        btnAtualizar.addActionListener(e -> carregarTabela());

        return painel;
    }

    private JPanel criarPainelDireito() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));

        // Sub-painel topo (Barra de Pesquisa)
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.add(new JLabel("Pesquisar por Nome do Lago:"));
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

        // Configuração da JTable
        String[] colunas = {"ID", "Nome do Lago", "Capacidade (L)", "Tipo", "Status da Água", "Temperatura (°C)"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impede edição direta na célula
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
            LagoDAO dao = new LagoDAO();
            List<Lago> lista = dao.listarTodos();

            for (Lago l : lista) {
                modeloTabela.addRow(new Object[]{
                    l.getIdLago(),
                    l.getNomeLago(),
                    l.getCapacidadeLitros(),
                    l.getTipo(),
                    l.getStatusAgua(),
                    l.getTemperatura()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar lagos: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtrarTabela(String termo) {
        if (termo.trim().isEmpty()) {
            carregarTabela();
            return;
        }

        try {
            LagoDAO dao = new LagoDAO();
            List<Lago> lista = dao.buscarPorNome(termo); // Certifique-se de que este método existe usando LIKE %termo%

            modeloTabela.setRowCount(0);
            for (Lago l : lista) {
                modeloTabela.addRow(new Object[]{
                    l.getIdLago(),
                    l.getNomeLago(),
                    l.getCapacidadeLitros(),
                    l.getTipo(),
                    l.getStatusAgua(),
                    l.getTemperatura()
                });
            }
        } catch (SQLException e) {
            // Falha silenciosa para a busca em tempo real não travar a tela
        }
    }

    private void abrirModalCadastro(Lago lagoParaEditar) {
        FormLago formLago = new FormLago();

        if (lagoParaEditar != null) {
            formLago.carregarDadosParaEdicao(
                lagoParaEditar.getIdLago(),
                lagoParaEditar.getNomeLago(),
                String.valueOf(lagoParaEditar.getCapacidadeLitros()),
                lagoParaEditar.getTipo(),
                lagoParaEditar.getStatusAgua(),
                String.valueOf(lagoParaEditar.getTemperatura())
            );
        }

        Frame framePai = (Frame) SwingUtilities.getWindowAncestor(this);

        ModalCadastroBase modal = new ModalCadastroBase(
            framePai, 
            lagoParaEditar == null ? "Cadastrar Lago" : "Editar Lago", 
            formLago, 
            e -> {
                FormController controller = new FormController();
                ModalCadastroBase dialogAtual = (ModalCadastroBase) SwingUtilities.getWindowAncestor((Component) e.getSource());
                
                // Chamada do seu controlador!
                controller.salvarLago(formLago, dialogAtual);
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
            JOptionPane.showMessageDialog(this, "Selecione um lago para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idLago = (int) tabela.getValueAt(linha, 0);

        try {
            LagoDAO dao = new LagoDAO();
            Lago lago = dao.buscarPorId(idLago); 
            if (lago != null) {
                abrirModalCadastro(lago);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar lago: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um lago para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idLago = (int) tabela.getValueAt(linha, 0);
        String nome = (String) tabela.getValueAt(linha, 1);

        int confirmacao = JOptionPane.showConfirmDialog(
            this, 
            "Tem certeza que deseja excluir o lago '" + nome + "'?", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                LagoDAO dao = new LagoDAO();
                dao.deletar(idLago); 
                carregarTabela();
                JOptionPane.showMessageDialog(this, "Lago excluído com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir o lago: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}