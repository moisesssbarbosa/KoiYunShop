package janelas.paineis;

import dao.VendaDAO;
import janelas.ModalCadastroBase;
import janelas.componentes.FormVenda;
import modelo.Venda;
import controladores.FormController;
import janelas.estilos.TemaKoi; // Import da classe de estilização

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class PainelVenda extends JPanel {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField txtPesquisa;

    // Botões de Ação
    private JButton btnNovo;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnAtualizar;

    public PainelVenda() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(TemaKoi.COR_FUNDO_TELA);

        // 1. Painel Superior (Botões CRUD + Barra de Pesquisa)
        add(criarPainelTopo(), BorderLayout.NORTH);

        // 2. Painel Central (Tabela de Vendas)
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

        btnNovo = new JButton("+ Nova Venda");
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

        JLabel lblPesquisa = new JLabel("Pesquisar por ID do Cliente:");
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

        String[] colunas = {"ID Venda", "Data", "Valor Total (R$)", "Forma Pgto", "Status Entrega", "ID Cliente (FK)"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Estilização da Tabela com TemaKoi
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

    // --- MÉTODOS DE LÓGICA E BANCO ---

    public void carregarTabela() {
        modeloTabela.setRowCount(0); // Limpa a tabela
        try {
            VendaDAO dao = new VendaDAO();
            List<Venda> lista = dao.listarTodos();

            for (Venda v : lista) {
                modeloTabela.addRow(new Object[]{
                    v.getIdVenda(),
                    v.getDataVenda(),
                    v.getValorTotal(),
                    v.getFormaPagamento(),
                    v.getStatusEntrega(),
                    v.getIdCliente()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar vendas: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtrarTabela(String termo) {
        if (termo.trim().isEmpty()) {
            carregarTabela();
            return;
        }

        try {
            int idClienteBusca = Integer.parseInt(termo.trim());
            VendaDAO dao = new VendaDAO();
            List<Venda> lista = dao.buscarPorCliente(idClienteBusca); 

            modeloTabela.setRowCount(0);
            for (Venda v : lista) {
                modeloTabela.addRow(new Object[]{
                    v.getIdVenda(),
                    v.getDataVenda(),
                    v.getValorTotal(),
                    v.getFormaPagamento(),
                    v.getStatusEntrega(),
                    v.getIdCliente()
                });
            }
        } catch (NumberFormatException ex) {
            // Ignora silenciosamente se o usuário digitar letras no campo de ID
        } catch (SQLException e) {
            // Trata exceção de banco de dados
        }
    }

    private void abrirModalCadastro(Venda vendaParaEditar) {
        FormVenda formVenda = new FormVenda();

        if (vendaParaEditar != null) {
            formVenda.carregarDadosParaEdicao(
                vendaParaEditar.getIdVenda(),
                String.valueOf(vendaParaEditar.getDataVenda()), 
                String.valueOf(vendaParaEditar.getValorTotal()),
                vendaParaEditar.getFormaPagamento(),
                vendaParaEditar.getStatusEntrega(),
                String.valueOf(vendaParaEditar.getIdCliente())
            );
        }

        Frame framePai = (Frame) SwingUtilities.getWindowAncestor(this);

        ModalCadastroBase modal = new ModalCadastroBase(
            framePai, 
            vendaParaEditar == null ? "Cadastrar Venda" : "Editar Venda", 
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

        carregarTabela();
    }

    private void editarSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma venda para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idVenda = (int) tabela.getValueAt(linha, 0);

        try {
            VendaDAO dao = new VendaDAO();
            Venda venda = dao.buscarPorId(idVenda); 
            if (venda != null) {
                abrirModalCadastro(venda);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar venda: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma venda para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idVenda = (int) tabela.getValueAt(linha, 0);

        int confirmacao = JOptionPane.showConfirmDialog(
            this, 
            "Tem certeza que deseja excluir a Venda ID " + idVenda + "?\n(Isso pode afetar os itens de venda atrelados a ela)", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                VendaDAO dao = new VendaDAO();
                dao.deletar(idVenda); 
                carregarTabela();
                JOptionPane.showMessageDialog(this, "Venda excluída com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir a venda: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}