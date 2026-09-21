package janelas;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    private JPanel painelConteudo; // Painel central com CardLayout
    private CardLayout cardLayout;

    public TelaPrincipal() {
        setTitle("Sistema de Gestão - Piscicultura");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Menu / Barra Lateral (O que NÃO muda)
        JPanel painelMenu = new JPanel();
        painelMenu.setLayout(new BoxLayout(painelMenu, BoxLayout.Y_AXIS));

        JButton btnClientes = new JButton("Clientes");
        JButton btnPeixes = new JButton("Peixes");
        JButton btnVendas = new JButton("Vendas");

        painelMenu.add(btnClientes);
        painelMenu.add(btnPeixes);
        painelMenu.add(btnVendas);

        add(painelMenu, BorderLayout.WEST);

        // 2. Painel Central com CardLayout
        cardLayout = new CardLayout();
        painelConteudo = new JPanel(cardLayout);

        // TODO // Adiciona cada painel/tela associado a uma chave (Nome único)
        // TODO painelConteudo.add(new PainelCliente(), "tela_clientes");
        // TODO painelConteudo.add(new PainelPeixe(), "tela_peixes");
        // TODO painelConteudo.add(new PainelVenda(), "tela_vendas");

        add(painelConteudo, BorderLayout.CENTER);

        // 3. Eventos dos Botões do Menu para trocar de tela
        btnClientes.addActionListener(e -> cardLayout.show(painelConteudo, "tela_clientes"));
        btnPeixes.addActionListener(e -> cardLayout.show(painelConteudo, "tela_peixes"));
        btnVendas.addActionListener(e -> cardLayout.show(painelConteudo, "tela_vendas"));
    }
}
