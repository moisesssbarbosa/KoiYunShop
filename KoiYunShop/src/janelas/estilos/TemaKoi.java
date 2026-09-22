package janelas.estilos;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TemaKoi {

    // Cores Globais da Marca
    public static final Color COR_FUNDO_TELA = new Color(245, 245, 245);
    public static final Color COR_LARANJA = new Color(255, 102, 0);
    public static final Color COR_LARANJA_HOVER = new Color(230, 90, 0);
    public static final Color COR_CINZA_MENU = new Color(235, 235, 235);
    public static final Color COR_AZUL = new Color(0, 120, 215);
    public static final Color COR_VERMELHO = new Color(220, 53, 69);
    public static final Color COR_VERDE = new Color(40, 167, 69);
    public static final Color COR_CINZA_CANCELAR = new Color(108, 117, 125);
    public static final Color COR_TEXTO_ESCURO = new Color(40, 40, 40);

    // Fontes
    public static final Font FONTE_MENU = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONTE_BOTOES = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONTE_INPUTS = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONTE_LABEL = new Font("Segoe UI", Font.BOLD, 13); // Aumentada e em Negrito
    public static final Font FONTE_TITULO_MODAL = new Font("Segoe UI", Font.BOLD, 16);

    // 1. Estilização dos Botões do Menu Lateral
    public static void estilizarBotaoMenu(JButton botao, boolean ativo) {
        botao.setFont(FONTE_MENU);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setHorizontalAlignment(SwingConstants.LEFT);
        
        Border padding = new EmptyBorder(12, 20, 12, 15);

        if (ativo) {
            botao.setBackground(COR_LARANJA);
            botao.setForeground(Color.WHITE);
            Border indicador = new MatteBorder(0, 5, 0, 0, new Color(180, 70, 0));
            botao.setBorder(new CompoundBorder(indicador, padding));
        } else {
            botao.setBackground(COR_CINZA_MENU);
            botao.setForeground(COR_TEXTO_ESCURO);
            botao.setBorder(padding);

            botao.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (botao.getBackground() != COR_LARANJA) {
                        botao.setBackground(new Color(220, 220, 220));
                    }
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    if (botao.getBackground() != COR_LARANJA) {
                        botao.setBackground(COR_CINZA_MENU);
                    }
                }
            });
        }
    }

    // 2. Estilização de Botões de Ação (CRUD e Modais) - Arredondados
    public static void estilizarBotaoCrud(JButton botao, Color corFundo) {
        botao.setFont(FONTE_BOTOES);
        botao.setForeground(Color.white);
        botao.setFocusPainted(false);
        botao.setContentAreaFilled(false);
        botao.setOpaque(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBorder(new EmptyBorder(8, 16, 8, 16));

        botao.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                AbstractButton b = (AbstractButton) c;
                Graphics2D g2 = (Graphics2D) g.create();
                
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (b.getModel().isPressed()) {
                    g2.setColor(corFundo.darker());
                } else if (b.getModel().isRollover()) {
                    g2.setColor(corFundo.brighter());
                } else {
                    g2.setColor(corFundo);
                }

                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 12, 12);
                g2.dispose();
                super.paint(g, c);
            }
        });
    }

    public static void estilizarBotaoSucesso(JButton btn) {
        btn.setBackground(new Color(40, 167, 69)); // Verde de confirmação / sucesso
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void estilizarBotaoSecundario(JButton btn) {
        btn.setBackground(new Color(230, 230, 230));
        btn.setForeground(new Color(60, 60, 60));
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // 3. Estilização de Campos de Texto (Inputs)
    public static void estilizarCampoTexto(JTextField campo) {
        campo.setFont(FONTE_INPUTS);
        campo.setPreferredSize(new Dimension(campo.getPreferredSize().width, 34));
        Border bordaLinha = BorderFactory.createLineBorder(new Color(200, 200, 200), 1);
        Border padding = new EmptyBorder(4, 8, 4, 8);
        campo.setBorder(new CompoundBorder(bordaLinha, padding));
    }

    // 4. Estilização de Labels dos Formulários/Modais
    public static void estilizarLabel(JLabel label) {
        label.setFont(FONTE_LABEL);
        label.setForeground(COR_TEXTO_ESCURO);
    }

    public static void estilizarLabelTitulo(JLabel label) {
        label.setFont(FONTE_TITULO_MODAL);
        label.setForeground(COR_TEXTO_ESCURO);
    }

    // 5. Estilização de Painéis e Diálogos Modais
    public static void estilizarContainerModal(JPanel painel) {
        painel.setBackground(COR_FUNDO_TELA);
        painel.setBorder(new EmptyBorder(15, 15, 15, 15));
    }

    public static void estilizarDialog(JDialog dialog) {
        dialog.getContentPane().setBackground(COR_FUNDO_TELA);
    }

    // 6. Estilização Completa e Padronizada de JTable
    public static void estilizarTabela(JTable tabela) {
        tabela.setRowHeight(32);
        tabela.setFont(FONTE_INPUTS);
        tabela.setSelectionBackground(new Color(255, 230, 210));
        tabela.setSelectionForeground(Color.BLACK);
        tabela.setGridColor(new Color(230, 230, 230));
        tabela.setShowGrid(true);

        JTableHeader header = tabela.getTableHeader();
        header.setFont(FONTE_BOTOES);
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 36));

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(COR_LARANJA);
                label.setForeground(Color.WHITE);
                label.setFont(FONTE_BOTOES);
                label.setHorizontalAlignment(SwingConstants.LEFT);
                label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                label.setOpaque(true);
                return label;
            }
        };
        header.setDefaultRenderer(headerRenderer);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (c instanceof JLabel) {
                    ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                }

                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(250, 250, 250));
                }
                return c;
            }
        };

        tabela.setDefaultRenderer(Object.class, cellRenderer);
        
    }
}