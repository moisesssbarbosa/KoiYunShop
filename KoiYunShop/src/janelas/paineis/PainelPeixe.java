package janelas.paineis;

import dao.PeixeDAO;
import janelas.ModalCadastroBase;
import janelas.componentes.FormPeixe;
import modelo.Peixe; // Certifique-se de que o pacote e nome estão corretos
import controladores.FormController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.List;

public class PainelPeixe extends JPanel {

    private JPanel painelCatalogo;
    private JTextField txtPesquisa;

    // Botões de Ação
    private JButton btnNovo;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnAtualizar;

    // Controle de Seleção
    private JPanel cardSelecionado = null;
    private Peixe peixeSelecionado = null;
    private Border bordaNormal = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
    private Border bordaSelecionada = BorderFactory.createLineBorder(new Color(0, 120, 215), 3);

    public PainelPeixe() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Painel Esquerdo (Botões e Ações)
        add(criarPainelEsquerdo(), BorderLayout.WEST);

        // 2. Painel Direito (Pesquisa + Catálogo de Cards)
        add(criarPainelDireito(), BorderLayout.CENTER);

        // 3. Carrega os dados iniciais do banco
        carregarCatalogo();
    }

    private JPanel criarPainelEsquerdo() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setPreferredSize(new Dimension(180, 0));

        btnNovo = new JButton("Novo Peixe");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnAtualizar = new JButton("Atualizar Catálogo");

        Dimension maxDim = new Dimension(170, 35);
        btnNovo.setMaximumSize(maxDim);
        btnEditar.setMaximumSize(maxDim);
        btnExcluir.setMaximumSize(maxDim);
        btnAtualizar.setMaximumSize(maxDim);

        painel.add(btnNovo);
        painel.add(Box.createVerticalStrut(8));
        painel.add(btnEditar);
        painel.add(Box.createVerticalStrut(8));
        painel.add(btnExcluir);
        painel.add(Box.createVerticalStrut(8));
        painel.add(btnAtualizar);

        // Eventos
        btnNovo.addActionListener(e -> abrirModalCadastro(null));
        btnEditar.addActionListener(e -> editarSelecionado());
        btnExcluir.addActionListener(e -> excluirSelecionado());
        btnAtualizar.addActionListener(e -> carregarCatalogo());

        return painel;
    }

    private JPanel criarPainelDireito() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));

        // Barra de Pesquisa por Variedade
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.add(new JLabel("Filtrar por Variedade (Ex: Kohaku):"));
        txtPesquisa = new JTextField(20);

        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filtrarCatalogo(txtPesquisa.getText());
            }
        });

        painelBusca.add(txtPesquisa);
        painel.add(painelBusca, BorderLayout.NORTH);

        // Container do Catálogo usando FlowLayout (permite quebrar linha)
        painelCatalogo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        painelCatalogo.setBackground(Color.WHITE);

        // JScrollPane para permitir rolagem caso haja muitos peixes
        JScrollPane scrollPane = new JScrollPane(painelCatalogo);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        painel.add(scrollPane, BorderLayout.CENTER);

        return painel;
    }

    // --- LÓGICA DE CARREGAMENTO DOS CARDS ---

    public void carregarCatalogo() {
        try {
            PeixeDAO dao = new PeixeDAO();
            List<Peixe> lista = dao.listarTodos();
            renderizarCards(lista);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os peixes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtrarCatalogo(String termo) {
        try {
            PeixeDAO dao = new PeixeDAO();
            List<Peixe> lista = termo.trim().isEmpty() ? dao.listarTodos() : dao.buscarPorVariedade(termo);
            renderizarCards(lista);
        } catch (SQLException e) {
            // Ignora falhas de digitação rápida
        }
    }

    private void renderizarCards(List<Peixe> lista) {
        painelCatalogo.removeAll();
        peixeSelecionado = null;
        cardSelecionado = null;

        for (Peixe p : lista) {
            JPanel card = criarCardPeixe(p);
            painelCatalogo.add(card);
        }

        // Atualiza a interface gráfica
        painelCatalogo.revalidate();
        painelCatalogo.repaint();
    }

    private JPanel criarCardPeixe(Peixe peixe) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(160, 220));
        card.setBackground(new Color(245, 245, 245));
        card.setBorder(bordaNormal);

        // 1. Imagem da Variedade
        JLabel lblImagem = new JLabel(carregarImagemVariedade(peixe.getVariedade()));
        lblImagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // 2. Informações de Texto
        JLabel lblCod = new JLabel("Cód: " + peixe.getCodigoIdentificador());
        lblCod.setFont(new Font("Arial", Font.BOLD, 12));
        lblCod.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblVar = new JLabel(peixe.getVariedade());
        lblVar.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTamanho = new JLabel("Tam: " + peixe.getTamanhoCm() + "cm");
        lblTamanho.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblPreco = new JLabel("R$ " + peixe.getPrecoVenda());
        lblPreco.setForeground(new Color(0, 128, 0));
        lblPreco.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Montando o Card
        card.add(Box.createVerticalStrut(10));
        card.add(lblImagem);
        card.add(Box.createVerticalStrut(10));
        card.add(lblCod);
        card.add(lblVar);
        card.add(lblTamanho);
        card.add(lblPreco);

        // Evento de Clique para Selecionar o Card
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (cardSelecionado != null) {
                    cardSelecionado.setBorder(bordaNormal); // Tira o foco do anterior
                }
                card.setBorder(bordaSelecionada); // Põe foco no atual
                cardSelecionado = card;
                peixeSelecionado = peixe;

                // Duplo clique para editar
                if (e.getClickCount() == 2) {
                    editarSelecionado();
                }
            }
        });

        return card;
    }

    // --- GERENCIAMENTO DE IMAGENS ---

    private ImageIcon carregarImagemVariedade(String variedade) {
        // Tenta buscar a imagem na pasta resources/imagens baseada no nome (ex: kohaku.png)
        String nomeArquivo = variedade.trim().toLowerCase().replace(" ", "_") + ".png";
        java.net.URL imgURL = getClass().getResource("/imagens/" + nomeArquivo);

        if (imgURL != null) {
            ImageIcon iconeOriginal = new ImageIcon(imgURL);
            Image imagemRedimensionada = iconeOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            return new ImageIcon(imagemRedimensionada);
        } else {
            return criarImagemPadrao(variedade); // Imagem genérica se não encontrar
        }
    }

    private ImageIcon criarImagemPadrao(String texto) {
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(new Color(200, 200, 200));
        g2d.fillRoundRect(0, 0, 100, 100, 15, 15);
        g2d.setColor(Color.DARK_GRAY);
        
        // Desenha a inicial ou as primeiras letras se não houver foto
        String sigla = texto.length() >= 3 ? texto.substring(0, 3).toUpperCase() : "P/X";
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = g2d.getFontMetrics();
        int x = (100 - fm.stringWidth(sigla)) / 2;
        int y = ((100 - fm.getHeight()) / 2) + fm.getAscent();
        g2d.drawString(sigla, x, y);
        g2d.dispose();
        
        return new ImageIcon(img);
    }

    // --- AÇÕES DE CRUD ---

    private void abrirModalCadastro(Peixe peixeEditar) {
        FormPeixe formPeixe = new FormPeixe();

        if (peixeEditar != null) {
            formPeixe.carregarDadosParaEdicao(
                peixeEditar.getIdPeixe(),
                String.valueOf(peixeEditar.getCodigoIdentificador()),
                peixeEditar.getVariedade(),
                String.valueOf(peixeEditar.getDataEntrada()),
                String.valueOf(peixeEditar.getTamanhoCm()),
                String.valueOf(peixeEditar.getPrecoVenda()),
                peixeEditar.getStatus(),
                String.valueOf(peixeEditar.getIdLago())
            );
        }

        Frame framePai = (Frame) SwingUtilities.getWindowAncestor(this);

        ModalCadastroBase modal = new ModalCadastroBase(
            framePai, 
            peixeEditar == null ? "Cadastrar Peixe" : "Editar Peixe", 
            formPeixe, 
            e -> {
                FormController controller = new FormController();
                ModalCadastroBase dialogAtual = (ModalCadastroBase) SwingUtilities.getWindowAncestor((Component) e.getSource());
                controller.salvarPeixe(formPeixe, dialogAtual);
            }
        );

        modal.pack();
        modal.setLocationRelativeTo(framePai);
        modal.setVisible(true);

        carregarCatalogo();
    }

    private void editarSelecionado() {
        if (peixeSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Clique em um card para selecionar o peixe que deseja editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        abrirModalCadastro(peixeSelecionado);
    }

    private void excluirSelecionado() {
        if (peixeSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Clique em um card para selecionar o peixe que deseja excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
            this, 
            "Deseja excluir o peixe " + peixeSelecionado.getVariedade() + " (Cód: " + peixeSelecionado.getCodigoIdentificador() + ")?", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                PeixeDAO dao = new PeixeDAO();
                dao.deletar(peixeSelecionado.getIdPeixe()); 
                carregarCatalogo();
                JOptionPane.showMessageDialog(this, "Peixe excluído com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir o peixe: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}