package janelas.paineis;

import dao.ItensVendaDAO;
import janelas.ModalCadastroBase;
import janelas.componentes.FormItemVenda;
import modelo.ItemVenda; // Ajuste para o nome exato do seu Modelo
import controladores.FormController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class PainelItemVenda extends JPanel {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField txtPesquisa;

    // Botões de Ação
    private JButton btnNovo;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnAtualizar;

    public PainelItemVenda() {
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
        btnNovo = new JButton("Novo Item");
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
        painelBusca.add(new JLabel("Pesquisar por ID da Venda:"));
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
        String[] colunas = {"ID Item", "Preço (R$)", "ID Venda (FK)", "ID Peixe (FK)"};
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
            ItensVendaDAO dao = new ItensVendaDAO();
            List<ItemVenda> lista = dao.listarTodos();

            for (ItemVenda item : lista) {
                modeloTabela.addRow(new Object[]{
                    item.getIdItemVenda(),
                    item.getPrecoPago(),
                    item.getIdVenda(),
                    item.getIdPeixe()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar itens da venda: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtrarTabela(String termo) {
        if (termo.trim().isEmpty()) {
            carregarTabela();
            return;
        }

        try {
            int idVendaBusca = Integer.parseInt(termo.trim());
            ItensVendaDAO dao = new ItensVendaDAO();
            
            List<ItemVenda> lista = dao.buscarPorVenda(idVendaBusca); 

            modeloTabela.setRowCount(0);
            for (ItemVenda item : lista) {
                modeloTabela.addRow(new Object[]{
                    item.getIdItemVenda(),
                    item.getPrecoPago(),
                    item.getIdVenda(),
                    item.getIdPeixe()
                });
            }
        } catch (NumberFormatException ex) {
            // Se o usuário digitar letras ao invés de números no ID, a gente ignora silenciosamente
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar itens da venda: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirModalCadastro(ItemVenda itemParaEditar) {
        FormItemVenda formItem = new FormItemVenda();

        if (itemParaEditar != null) {
            formItem.carregarDadosParaEdicao(
                itemParaEditar.getIdItemVenda(),
                String.valueOf(itemParaEditar.getPrecoPago()),
                String.valueOf(itemParaEditar.getIdVenda()),
                String.valueOf(itemParaEditar.getIdPeixe())
            );
        }

        Frame framePai = (Frame) SwingUtilities.getWindowAncestor(this);

        ModalCadastroBase modal = new ModalCadastroBase(
            framePai, 
            itemParaEditar == null ? "Cadastrar Item da Venda" : "Editar Item da Venda", 
            formItem, 
            e -> {
                FormController controller = new FormController();
                ModalCadastroBase dialogAtual = (ModalCadastroBase) SwingUtilities.getWindowAncestor((Component) e.getSource());
                
                // Chamada do seu controlador!
                controller.salvarItemVenda(formItem, dialogAtual);
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
            JOptionPane.showMessageDialog(this, "Selecione um item para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idItemVenda = (int) tabela.getValueAt(linha, 0);

        try {
            ItensVendaDAO dao = new ItensVendaDAO();
            ItemVenda item = dao.buscarPorId(idItemVenda); 
            if (item != null) {
                abrirModalCadastro(item);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar item da venda: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idItemVenda = (int) tabela.getValueAt(linha, 0);

        int confirmacao = JOptionPane.showConfirmDialog(
            this, 
            "Tem certeza que deseja excluir o Item ID " + idItemVenda + "?", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                ItensVendaDAO dao = new ItensVendaDAO();
                dao.deletar(idItemVenda); 
                carregarTabela();
                JOptionPane.showMessageDialog(this, "Item excluído com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir o item: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}