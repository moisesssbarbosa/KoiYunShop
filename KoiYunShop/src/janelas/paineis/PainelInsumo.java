package janelas.paineis;

import dao.InsumoDAO;
import janelas.ModalCadastroBase;
import janelas.componentes.FormInsumo;
import modelo.Insumo;
import controladores.FormController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class PainelInsumo extends JPanel {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField txtPesquisa;

    // Botões de Ação
    private JButton btnNovo;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnAtualizar;

    public PainelInsumo() {
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
        btnNovo = new JButton("Novo Insumo");
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
        painelBusca.add(new JLabel("Pesquisar Insumo:"));
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
        String[] colunas = {"ID", "Nome Insumo", "Qtd. Atual (Kg)", "Qtd. Mínima Alerta", "Preço Custo (R$)"};
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
            InsumoDAO dao = new InsumoDAO();
            List<Insumo> lista = dao.listarTodos();

            for (Insumo i : lista) {
                modeloTabela.addRow(new Object[]{
                    i.getIdInsumo(),
                    i.getNomeInsumo(),
                    i.getQuantidadeAtualKg(),
                    i.getQuantidadeMinimaAlerta(),
                    i.getPrecoCustoPorKg()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar insumos: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtrarTabela(String termo) {
        if (termo.trim().isEmpty()) {
            carregarTabela();
            return;
        }

        try {
            InsumoDAO dao = new InsumoDAO();
            List<Insumo> lista = dao.buscarInsumoPorNome(termo); // Certifique-se que esse método existe no InsumoDAO

            modeloTabela.setRowCount(0);
            for (Insumo i : lista) {
                modeloTabela.addRow(new Object[]{
                    i.getIdInsumo(), 
                    i.getNomeInsumo(), 
                    i.getQuantidadeAtualKg(), 
                    i.getQuantidadeMinimaAlerta(), 
                    i.getPrecoCustoPorKg()
                });
            }
        } catch (SQLException e) {
            // Trata exceção de busca silenciosamente ou com log
        }
    }

    private void abrirModalCadastro(Insumo insumoParaEditar) {
        // 1. Instancia o painel do formulário
        FormInsumo formInsumo = new FormInsumo();

        // 2. Se for edição, utiliza o método para preencher o formulário
        if (insumoParaEditar != null) {
            formInsumo.carregarDadosParaEdicao(
                insumoParaEditar.getIdInsumo(),
                insumoParaEditar.getNomeInsumo(),
                String.valueOf(insumoParaEditar.getQuantidadeAtualKg()),
                String.valueOf(insumoParaEditar.getQuantidadeMinimaAlerta()),
                String.valueOf(insumoParaEditar.getPrecoCustoPorKg())
            );
        }

        // 3. Captura a janela principal
        Frame framePai = (Frame) SwingUtilities.getWindowAncestor(this);

        // 4. Instancia o ModalCadastroBase
        ModalCadastroBase modal = new ModalCadastroBase(
            framePai, 
            insumoParaEditar == null ? "Cadastrar Insumo" : "Editar Insumo", 
            formInsumo, 
            e -> {
                FormController controller = new FormController();
                ModalCadastroBase dialogAtual = (ModalCadastroBase) SwingUtilities.getWindowAncestor((Component) e.getSource());
                
                // Chama o seu controlador que já está pronto!
                controller.salvarInsumo(formInsumo, dialogAtual);
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
            JOptionPane.showMessageDialog(this, "Selecione um insumo para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idInsumo = (int) tabela.getValueAt(linha, 0);

        try {
            InsumoDAO dao = new InsumoDAO();
            Insumo insumo = dao.buscarPorId(idInsumo); // Certifique-se que esse método existe no InsumoDAO
            if (insumo != null) {
                abrirModalCadastro(insumo);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar insumo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um insumo para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idInsumo = (int) tabela.getValueAt(linha, 0);
        String nome = (String) tabela.getValueAt(linha, 1);

        int confirmacao = JOptionPane.showConfirmDialog(
            this, 
            "Tem certeza que deseja excluir o insumo '" + nome + "'?", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                InsumoDAO dao = new InsumoDAO();
                dao.deletar(idInsumo); // Certifique-se que esse método existe no InsumoDAO
                carregarTabela();
                JOptionPane.showMessageDialog(this, "Insumo excluído com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir o insumo: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}