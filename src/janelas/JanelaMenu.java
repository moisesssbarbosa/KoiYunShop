package janelas;

import dao.ClientesDAO;
import modelo.Cliente;
import dao.InsumoDAO;
import modelo.Insumo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class JanelaMenu extends JFrame {

    public JanelaMenu(Connection conexao) {
        // Configurações básicas da janela
        setTitle("Sistema de Gestão - Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setResizable(false);

        // Painel principal com layout de grade (4 linhas, 1 coluna)
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Título/Cabeçalho
        JLabel lblTitulo = new JLabel("Menu Principal", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lblTitulo); 

        // Instanciação dos botões
        JButton btnCliente = new JButton("Cadastro de Cliente");
        JButton btnInsumo = new JButton("Cadastro de Insumo");
        JButton btnSair = new JButton("Sair");

        // Personalização visual simples (opcional)
        btnCliente.setFocusable(false);
        btnInsumo.setFocusable(false);
        btnSair.setFocusable(false);

        // Adicionando ações aos botões
        btnCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCadastroCliente(conexao);
            }
        });

        btnInsumo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCadastroInsumo(conexao);
            }
        });

        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fecharPrograma();
            }
        });

        // Adiciona os botões ao painel
        panel.add(btnCliente);
        panel.add(btnInsumo);
        panel.add(btnSair);

        // Adiciona o painel à janela
        add(panel);
    }

    // Métodos para tratar os cliques
    private void abrirCadastroCliente(Connection conexao) {
        SwingUtilities.invokeLater(() -> {
                JanelaClientes telaGrid = new JanelaClientes(conexao);
                telaGrid.setVisible(true);
                });       
    }

    private void abrirCadastroInsumo(Connection conexao) {
        SwingUtilities.invokeLater(() -> {
                JanelaInsumos telaGrid = new JanelaInsumos(conexao);
                telaGrid.setVisible(true);
                });       
    }


    private void fecharPrograma() {
        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente sair do sistema?",
            "Atenção",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

}
