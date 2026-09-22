package janelas.paineis;

import dao.InsumoDAO;
import janelas.ModalCadastroBase;
import janelas.componentes.FormInsumo;
import modelo.Insumo;
import controladores.FormController;
import janelas.estilos.TemaKoi;  // Import da classe de estilização

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
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(TemaKoi.COR_FUNDO_TELA);

        // 1. Painel Superior (Botões CRUD + Barra de Pesquisa)
        add(criarPainelTopo(), BorderLayout.NORTH);

        // 2. Painel Central (Tabela de Insumos)
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

        btnNovo = new JButton("+ Novo Insumo");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnAtualizar = new JButton("Atualizar Tabela");

        // Aplicação da Estilização TemaKoi nos Botões
        TemaKoi.estilizarBotaoCrud(btnNovo, TemaKoi.COR_VERDE);
        TemaKoi.estilizarBotaoCrud(btnEditar, TemaKoi.COR_AZUL);
        TemaKoi.estilizarBotaoCrud(btnExcluir, TemaKoi.COR_VERMELHO);
        TemaKoi.estilizarBotaoCrud(btnAtualizar, new Color(108, 117, 125)); // Cinza neutro

        painelBotoes.add(btnNovo);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnAtualizar);

        // --- LINHA 2: BARRA DE PESQUISA ---
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        painelBusca.setOpaque(false);

        JLabel lblPesquisa = new JLabel("Pesquisar Insumo:");
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

        return painelTopo;
    }

    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setOpaque(false);

        String[] colunas = {"ID", "Nome Insumo", "Qtd. Atual (Kg)", "Qtd. Mínima Alerta", "Preço Custo (R$)"};
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
            List<Insumo> lista = dao.buscarInsumoPorNome(termo);

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
            // Trata exceção de busca silenciosamente
        }
    }

    private void abrirModalCadastro(Insumo insumoParaEditar) {
        FormInsumo formInsumo = new FormInsumo();

        if (insumoParaEditar != null) {
            formInsumo.carregarDadosParaEdicao(
                insumoParaEditar.getIdInsumo(),
                insumoParaEditar.getNomeInsumo(),
                String.valueOf(insumoParaEditar.getQuantidadeAtualKg()),
                String.valueOf(insumoParaEditar.getQuantidadeMinimaAlerta()),
                String.valueOf(insumoParaEditar.getPrecoCustoPorKg())
            );
        }

        Frame framePai = (Frame) SwingUtilities.getWindowAncestor(this);

        ModalCadastroBase modal = new ModalCadastroBase(
            framePai, 
            insumoParaEditar == null ? "Cadastrar Insumo" : "Editar Insumo", 
            formInsumo, 
            e -> {
                FormController controller = new FormController();
                ModalCadastroBase dialogAtual = (ModalCadastroBase) SwingUtilities.getWindowAncestor((Component) e.getSource());
                controller.salvarInsumo(formInsumo, dialogAtual);
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
            JOptionPane.showMessageDialog(this, "Selecione um insumo para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idInsumo = (int) tabela.getValueAt(linha, 0);

        try {
            InsumoDAO dao = new InsumoDAO();
            Insumo insumo = dao.buscarPorId(idInsumo);
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
                dao.deletar(idInsumo);
                carregarTabela();
                JOptionPane.showMessageDialog(this, "Insumo excluído com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir o insumo: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}