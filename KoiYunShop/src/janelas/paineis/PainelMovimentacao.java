package janelas.paineis;

import dao.MovimentacaoDAO; // Certifique-se de que o pacote e nome estão corretos
import janelas.ModalCadastroBase;
import janelas.componentes.FormMovimentacao;
import modelo.Movimentacao; // Certifique-se de que o pacote e nome estão corretos
import controladores.FormController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class PainelMovimentacao extends JPanel {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField txtPesquisa;

    // Botões de Ação
    private JButton btnNovo;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnAtualizar;

    public PainelMovimentacao() {
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
        btnNovo = new JButton("Nova Movimentação");
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
        painelBusca.add(new JLabel("Pesquisar por Descrição:"));
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
        String[] colunas = {"ID", "Data", "Categoria", "Descrição", "ID Insumo (FK)", "Valor (R$)"};
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
            MovimentacaoDAO dao = new MovimentacaoDAO();
            List<Movimentacao> lista = dao.listarTodos();

            for (Movimentacao m : lista) {
                modeloTabela.addRow(new Object[]{
                    m.getIdMovimentacao(),
                    m.getDataMovimentacao(),
                    m.getCategoria(),
                    m.getDescricao(),
                    m.getIdInsumo(),
                    m.getValor()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar movimentações: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtrarTabela(String termo) {
        if (termo.trim().isEmpty()) {
            carregarTabela();
            return;
        }

        try {
            MovimentacaoDAO dao = new MovimentacaoDAO();
            List<Movimentacao> lista = dao.buscarPorDescricao(termo); // Filtra usando LIKE no banco

            modeloTabela.setRowCount(0);
            for (Movimentacao m : lista) {
                modeloTabela.addRow(new Object[]{
                    m.getIdMovimentacao(),
                    m.getDataMovimentacao(),
                    m.getCategoria(),
                    m.getDescricao(),
                    m.getIdInsumo(),
                    m.getValor()
                });
            }
        } catch (SQLException e) {
            // Falha silenciosa para a busca em tempo real não travar a tela
        }
    }

    private void abrirModalCadastro(Movimentacao movParaEditar) {
        FormMovimentacao formMov = new FormMovimentacao();

        if (movParaEditar != null) {
            formMov.carregarDadosParaEdicao(
                movParaEditar.getIdMovimentacao(),
                String.valueOf(movParaEditar.getDataMovimentacao()), // Converte LocalDate/Date para String
                movParaEditar.getCategoria(),
                movParaEditar.getDescricao(),
                String.valueOf(movParaEditar.getIdInsumo()),
                String.valueOf(movParaEditar.getValor())
            );
        }

        Frame framePai = (Frame) SwingUtilities.getWindowAncestor(this);

        ModalCadastroBase modal = new ModalCadastroBase(
            framePai, 
            movParaEditar == null ? "Cadastrar Movimentação" : "Editar Movimentação", 
            formMov, 
            e -> {
                FormController controller = new FormController();
                ModalCadastroBase dialogAtual = (ModalCadastroBase) SwingUtilities.getWindowAncestor((Component) e.getSource());
                
                // Chamada do controlador!
                controller.salvarMovimentacao(formMov, dialogAtual);
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
            JOptionPane.showMessageDialog(this, "Selecione uma movimentação para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idMovimentacao = (int) tabela.getValueAt(linha, 0);

        try {
            MovimentacaoDAO dao = new MovimentacaoDAO();
            Movimentacao mov = dao.buscarPorId(idMovimentacao); 
            if (mov != null) {
                abrirModalCadastro(mov);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar movimentação: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma movimentação para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idMovimentacao = (int) tabela.getValueAt(linha, 0);
        String descricao = (String) tabela.getValueAt(linha, 3);

        int confirmacao = JOptionPane.showConfirmDialog(
            this, 
            "Tem certeza que deseja excluir a movimentação: '" + descricao + "'?", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                MovimentacaoDAO dao = new MovimentacaoDAO();
                dao.deletar(idMovimentacao); 
                carregarTabela();
                JOptionPane.showMessageDialog(this, "Movimentação excluída com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir a movimentação: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}