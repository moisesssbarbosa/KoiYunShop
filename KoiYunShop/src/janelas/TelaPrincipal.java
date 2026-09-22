package janelas;

import janelas.paineis.*; // Importa todos os seus painéis

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaPrincipal extends JFrame {

    private JPanel painelConteudo; 
    private CardLayout cardLayout;
    
    // Cores do Tema KoiYunShop
    private final Color COR_LARANJA_KOI = new Color(255, 102, 0); // Laranja vivo
    private final Color COR_FUNDO_MENU = Color.WHITE;
    private final Color COR_TEXTO_HOVER = Color.WHITE;

    public TelaPrincipal() {

        // Tenta aplicar o design system EXCLUSIVO do Windows
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
            // Se falhar (por exemplo, se estiver rodando em um Mac ou Linux), 
            // cai para o look and feel padrão do sistema atual.
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }

        setTitle("KoiYunShop - Gestão de Piscicultura");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza na tela
        setLayout(new BorderLayout());

        // 1. TOPO: Faixada KoiYunShop (Header)
        add(criarCabecalho(), BorderLayout.NORTH);

        // 2. ESQUERDA: Menu Lateral Estilizado
        add(criarMenuLateral(), BorderLayout.WEST);

        // 3. CENTRO: Painel com CardLayout para alternar as telas
        cardLayout = new CardLayout();
        painelConteudo = new JPanel(cardLayout);
        painelConteudo.setBackground(new Color(245, 245, 245)); // Fundo levemente cinza para destacar os painéis

        // Adicionando as instâncias dos painéis ao CardLayout
        painelConteudo.add(new PainelPeixe(), "tela_peixes");
        painelConteudo.add(new PainelCliente(), "tela_clientes");
        painelConteudo.add(new PainelLago(), "tela_lagos");
        painelConteudo.add(new PainelVenda(), "tela_vendas");
        painelConteudo.add(new PainelItemVenda(), "tela_itens_venda");

        add(painelConteudo, BorderLayout.CENTER);

        // 4. Define a tela de Peixes como a tela inicial
        cardLayout.show(painelConteudo, "tela_peixes");
    }

    private JPanel criarCabecalho() {
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(COR_LARANJA_KOI);
        painelTopo.setPreferredSize(new Dimension(0, 70)); // Altura da faixada
        
        JLabel lblLogo = new JLabel("  KoiYunShop");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblLogo.setForeground(Color.WHITE);
        
        // Subtítulo opcional
        JLabel lblSub = new JLabel("Sistema de Gestão  ");
        lblSub.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblSub.setForeground(new Color(255, 220, 200));

        painelTopo.add(lblLogo, BorderLayout.WEST);
        painelTopo.add(lblSub, BorderLayout.EAST);

        return painelTopo;
    }

    private JPanel criarMenuLateral() {
        JPanel painelMenu = new JPanel();
        painelMenu.setLayout(new BoxLayout(painelMenu, BoxLayout.Y_AXIS));
        painelMenu.setBackground(COR_FUNDO_MENU);
        painelMenu.setPreferredSize(new Dimension(200, 0));
        painelMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, COR_LARANJA_KOI)); // Borda direita laranja

        // Espaçamento no topo do menu
        painelMenu.add(Box.createVerticalStrut(20));

        // Criando os botões estilizados
        JButton btnPeixes = criarBotaoMenu("Peixes (Catálogo)");
        JButton btnClientes = criarBotaoMenu("Clientes");
        JButton btnLagos = criarBotaoMenu("Lagos");
        JButton btnVendas = criarBotaoMenu("Vendas");
        JButton btnItensVenda = criarBotaoMenu("Itens da Venda");

        // Adicionando ao menu com espaçamento
        painelMenu.add(btnPeixes);
        painelMenu.add(Box.createVerticalStrut(10));
        painelMenu.add(btnClientes);
        painelMenu.add(Box.createVerticalStrut(10));
        painelMenu.add(btnLagos);
        painelMenu.add(Box.createVerticalStrut(10));
        painelMenu.add(btnVendas);
        painelMenu.add(Box.createVerticalStrut(10));
        painelMenu.add(btnItensVenda);

        // Ações de clique para trocar os cards
        btnPeixes.addActionListener(e -> cardLayout.show(painelConteudo, "tela_peixes"));
        btnClientes.addActionListener(e -> cardLayout.show(painelConteudo, "tela_clientes"));
        btnLagos.addActionListener(e -> cardLayout.show(painelConteudo, "tela_lagos"));
        btnVendas.addActionListener(e -> cardLayout.show(painelConteudo, "tela_vendas"));
        btnItensVenda.addActionListener(e -> cardLayout.show(painelConteudo, "tela_itens_venda"));

        return painelMenu;
    }

    // Método auxiliar para criar botões bonitos e padronizados
    private JButton criarBotaoMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(COR_LARANJA_KOI); // Texto laranja
        btn.setBackground(COR_FUNDO_MENU);  // Fundo branco
        btn.setFocusPainted(false); // Tira aquele quadrado de seleção feio do Java
        btn.setBorder(new EmptyBorder(10, 20, 10, 20)); // Espaçamento interno
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Cursor de "mãozinha"
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 40));

        // Efeito Hover (passar o mouse)
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(COR_LARANJA_KOI);
                btn.setForeground(COR_TEXTO_HOVER); // Fica com fundo laranja e letra branca
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(COR_FUNDO_MENU);
                btn.setForeground(COR_LARANJA_KOI); // Volta ao original
            }
        });

        return btn;
    }

    // Método main para você rodar a tela e testar!
    public static void main(String[] args) {
        // Tenta aplicar o design system do sistema operacional (deixa as bordas mais modernas)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
}