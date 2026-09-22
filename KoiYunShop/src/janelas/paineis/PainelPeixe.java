package janelas.paineis;

import dao.PeixeDAO;
import janelas.ModalCadastroBase;
import janelas.componentes.FormPeixe;
import modelo.Peixe;
import controladores.FormController;
import janelas.estilos.TemaKoi;  // Import da classe de estilização

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
    private Border bordaNormal = BorderFactory.createLineBorder(new Color(220, 220, 220), 1);
    private Border bordaSelecionada = BorderFactory.createLineBorder(TemaKoi.COR_LARANJA, 3); // Borda Laranja Koi quando selecionado

    public PainelPeixe() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(TemaKoi.COR_FUNDO_TELA);

        // 1. Painel Superior (Botões CRUD + Barra de Pesquisa)
        add(criarPainelTopo(), BorderLayout.NORTH);

        // 2. Painel Central (Catálogo de Cards)
        add(criarPainelCentral(), BorderLayout.CENTER);

        // 3. Carrega os dados iniciais do banco
        carregarCatalogo();
    }

    private JPanel criarPainelTopo() {
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));
        painelTopo.setOpaque(false);

        // --- LINHA 1: BOTÕES DE AÇÃO (CRUD) NO TOPO ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        painelBotoes.setOpaque(false);

        btnNovo = new JButton("+ Novo Peixe");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnAtualizar = new JButton("Atualizar Catálogo");

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

        JLabel lblPesquisa = new JLabel("Filtrar por Variedade (Ex: Kohaku):");
        lblPesquisa.setFont(TemaKoi.FONTE_INPUTS);
        lblPesquisa.setForeground(TemaKoi.COR_TEXTO_ESCURO);

        txtPesquisa = new JTextField(25);
        TemaKoi.estilizarCampoTexto(txtPesquisa);

        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filtrarCatalogo(txtPesquisa.getText());
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
        btnAtualizar.addActionListener(e -> carregarCatalogo());

        return painelTopo;
    }

    private JScrollPane criarPainelCentral() {
        // Container do Catálogo usando FlowLayout (permite quebrar linha)
        painelCatalogo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        painelCatalogo.setBackground(Color.WHITE);

        // JScrollPane para permitir rolagem
        JScrollPane scrollPane = new JScrollPane(painelCatalogo);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        return scrollPane;
    }

    // --- LÓGICA DE CARREGAMENTO DOS CARDS (MANTIDA 100% INTACTA) ---

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
            // Falha silenciosa para a busca em tempo real não travar a tela
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

        painelCatalogo.revalidate();
        painelCatalogo.repaint();
    }

    private JPanel criarCardPeixe(Peixe peixe) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(160, 220));
        card.setBackground(new Color(250, 250, 250));
        card.setBorder(bordaNormal);

        JLabel lblImagem = new JLabel(carregarImagemVariedade(peixe.getVariedade()));
        lblImagem.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblCod = new JLabel("Cód: " + peixe.getCodigoIdentificador());
        lblCod.setFont(TemaKoi.FONTE_BOTOES);
        lblCod.setForeground(TemaKoi.COR_TEXTO_ESCURO);
        lblCod.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblVar = new JLabel(peixe.getVariedade());
        lblVar.setFont(TemaKoi.FONTE_INPUTS);
        lblVar.setForeground(TemaKoi.COR_TEXTO_ESCURO);
        lblVar.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTamanho = new JLabel("Tam: " + peixe.getTamanhoCm() + "cm");
        lblTamanho.setFont(TemaKoi.FONTE_INPUTS);
        lblTamanho.setForeground(Color.GRAY);
        lblTamanho.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblPreco = new JLabel("R$ " + peixe.getPrecoVenda());
        lblPreco.setFont(TemaKoi.FONTE_BOTOES);
        lblPreco.setForeground(TemaKoi.COR_VERDE);
        lblPreco.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalStrut(10));
        card.add(lblImagem);
        card.add(Box.createVerticalStrut(10));
        card.add(lblCod);
        card.add(lblVar);
        card.add(lblTamanho);
        card.add(lblPreco);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (cardSelecionado != null) {
                    cardSelecionado.setBorder(bordaNormal);
                    cardSelecionado.setBackground(new Color(250, 250, 250));
                }
                card.setBorder(bordaSelecionada);
                card.setBackground(Color.WHITE); // Destaca o fundo ao clicar
                cardSelecionado = card;
                peixeSelecionado = peixe;

                if (e.getClickCount() == 2) {
                    editarSelecionado();
                }
            }
        });

        return card;
    }

    // --- GERENCIAMENTO DE IMAGENS ---

    private ImageIcon carregarImagemVariedade(String variedade) {
        String nomeArquivo = variedade.trim().toLowerCase().replace(" ", "_") + ".png";
        java.net.URL imgURL = getClass().getResource("/imagens/" + nomeArquivo);

        if (imgURL != null) {
            ImageIcon iconeOriginal = new ImageIcon(imgURL);
            Image imagemRedimensionada = iconeOriginal.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            return new ImageIcon(imagemRedimensionada);
        } else {
            return criarImagemPadrao(variedade);
        }
    }

    private ImageIcon criarImagemPadrao(String texto) {
        BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(220, 220, 220));
        g2d.fillRoundRect(0, 0, 100, 100, 15, 15);
        g2d.setColor(Color.DARK_GRAY);

        String sigla = texto.length() >= 3 ? texto.substring(0, 3).toUpperCase() : "P/X";
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 24));
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
            "Deseja excluir o peixe " + peixeSelecionado.getVariedade() + "?", 
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