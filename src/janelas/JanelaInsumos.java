package janelas;

import dao.InsumoDAO;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.Insumo;

public class JanelaInsumos extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private InsumoDAO insumoDAO;

    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtQtdAtual;
    private JTextField txtQtdMinima;
    private JTextField txtPrecoCusto;

    private JButton btnSalvar;
    private JButton btnExcluir;
    private JButton btnLimpar;

    public JanelaInsumos(Connection conexao) {
        super("Gerenciamento de Insumos");
        this.insumoDAO = new InsumoDAO(conexao);

        setSize(850, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
        carregarDadosTabela();
    }

    private void inicializarComponentes() {
        String[] colunas = {"ID", "Nome Insumo", "Qtd. Atual (Kg)", "Qtd. Mínima (Kg)", "Preço Custo/Kg"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabela);

        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabela.getSelectedRow() != -1) {
                preencherCamposComLinhaSelecionada();
            }
        });

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBorder(BorderFactory.createTitledBorder("Dados do Insumo"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID
        gbc.gridx = 0; gbc.gridy = 0;
        painelFormulario.add(new JLabel("ID:"), gbc);
        txtId = new JTextField(5);
        txtId.setEditable(false);
        gbc.gridx = 1; gbc.gridy = 0;
        painelFormulario.add(txtId, gbc);

        // Nome
        gbc.gridx = 0; gbc.gridy = 1;
        painelFormulario.add(new JLabel("Nome:"), gbc);
        txtNome = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 1;
        painelFormulario.add(txtNome, gbc);

        // Qtd Atual
        gbc.gridx = 0; gbc.gridy = 2;
        painelFormulario.add(new JLabel("Qtd. Atual (Kg):"), gbc);
        txtQtdAtual = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 2;
        painelFormulario.add(txtQtdAtual, gbc);

        // Qtd Minima
        gbc.gridx = 0; gbc.gridy = 3;
        painelFormulario.add(new JLabel("Qtd. Mínima (Kg):"), gbc);
        txtQtdMinima = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 3;
        painelFormulario.add(txtQtdMinima, gbc);

        // Preco Custo
        gbc.gridx = 0; gbc.gridy = 4;
        painelFormulario.add(new JLabel("Preço Custo/Kg:"), gbc);
        txtPrecoCusto = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 4;
        painelFormulario.add(txtPrecoCusto, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnSalvar = new JButton("Salvar");
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Novo / Limpar");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        painelFormulario.add(painelBotoes, gbc);

        btnSalvar.addActionListener(e -> salvarInsumo());
        btnExcluir.addActionListener(e -> excluirInsumo());
        btnLimpar.addActionListener(e -> limparCampos());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, painelFormulario);
        splitPane.setDividerLocation(420);
        splitPane.setResizeWeight(0.5);

        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);
    }

    public void carregarDadosTabela() {
        modeloTabela.setRowCount(0);
        try {
            List<Insumo> lista = insumoDAO.listarTodos();
            for (Insumo i : lista) {
                Object[] linha = {
                    i.getIdInsumo(),
                    i.getNomeInsumo(),
                    i.getQuantidadeAtualKg(),
                    i.getQuantidadeMinimaAlerta(),
                    i.getPrecoCustoPorKg()
                };
                modeloTabela.addRow(linha);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar insumos:\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherCamposComLinhaSelecionada() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada != -1) {
            txtId.setText(modeloTabela.getValueAt(linhaSelecionada, 0).toString());
            txtNome.setText(modeloTabela.getValueAt(linhaSelecionada, 1).toString());
            txtQtdAtual.setText(modeloTabela.getValueAt(linhaSelecionada, 2).toString());
            txtQtdMinima.setText(modeloTabela.getValueAt(linhaSelecionada, 3).toString());
            txtPrecoCusto.setText(modeloTabela.getValueAt(linhaSelecionada, 4).toString());
        }
    }

    private void salvarInsumo() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome do insumo.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Insumo insumo = new Insumo();
            insumo.setNomeInsumo(txtNome.getText().trim());
            insumo.setQuantidadeAtualKg(new BigDecimal(txtQtdAtual.getText().trim().replace(",", ".")));
            insumo.setQuantidadeMinimaAlerta(new BigDecimal(txtQtdMinima.getText().trim().replace(",", ".")));
            insumo.setPrecoCustoPorKg(new BigDecimal(txtPrecoCusto.getText().trim().replace(",", ".")));

            if (txtId.getText().isEmpty()) {
                insumoDAO.cadastrar(insumo);
                JOptionPane.showMessageDialog(this, "Insumo cadastrado com sucesso!");
            } else {
                insumo.setIdInsumo(Integer.parseInt(txtId.getText()));
                insumoDAO.atualizar(insumo);
                JOptionPane.showMessageDialog(this, "Insumo atualizado com sucesso!");
            }

            limparCampos();
            carregarDadosTabela();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valores numéricos inválidos nos campos numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar insumo:\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirInsumo() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um insumo para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (JOptionPane.showConfirmDialog(this, "Deseja excluir este insumo?", "Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                insumoDAO.deletar(Integer.parseInt(txtId.getText()));
                JOptionPane.showMessageDialog(this, "Insumo excluído com sucesso!");
                limparCampos();
                carregarDadosTabela();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir insumo:\n" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtQtdAtual.setText("");
        txtQtdMinima.setText("");
        txtPrecoCusto.setText("");
        tabela.clearSelection();
    }
}