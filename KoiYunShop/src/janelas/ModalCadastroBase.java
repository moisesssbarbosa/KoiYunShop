package janelas;

import janelas.estilos.TemaKoi;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ModalCadastroBase extends JDialog {

    public ModalCadastroBase(Frame parent, String titulo, JPanel painelConteudo, ActionListener acaoSalvar) {
        super(parent, titulo, true); // Modal bloqueia a janela de trás
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- 1. CABEÇALHO DO MODAL (Topo Laranja) ---
        JPanel painelHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 12));
        painelHeader.setBackground(TemaKoi.COR_LARANJA); // Utiliza a cor laranja do topo da app

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        painelHeader.add(lblTitulo);

        // --- 2. PAINEL CENTRAL (Formulário + Margens Internas) ---
        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.setBackground(TemaKoi.COR_FUNDO_TELA);
        
        // Borda vazia para dar espaçamento entre o conteúdo e as extremidades
        painelCentral.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20)); 
        painelCentral.add(painelConteudo, BorderLayout.CENTER);

        // --- 3. RODAPÉ (Botões Salvar e Cancelar Estilizados) ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        painelBotoes.setBackground(TemaKoi.COR_FUNDO_TELA);

        JButton btnCancelar = new JButton("Cancelar");
        JButton btnSalvar = new JButton("Salvar");

        // Estilizando os botões conforme os padrões da aplicação
        TemaKoi.estilizarBotaoSecundario(btnCancelar); // Estilo cinza/suave
        TemaKoi.estilizarBotaoSucesso(btnSalvar);       // Estilo verde padronizado

        // Ajusta tamanhos mínimos para garantir boa área de clique
        Dimension dimBotao = new Dimension(100, 32);
        btnCancelar.setPreferredSize(dimBotao);
        btnSalvar.setPreferredSize(dimBotao);

        // Eventos dos botões
        btnCancelar.addActionListener(e -> dispose());
        btnSalvar.addActionListener(acaoSalvar);

        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnSalvar);

        // Montando a estrutura principal do modal
        painelCentral.add(painelBotoes, BorderLayout.SOUTH);

        add(painelHeader, BorderLayout.NORTH);
        add(painelCentral, BorderLayout.CENTER);

        // Ajustes finos de exibição
        pack(); // Ajusta o tamanho da janela automaticamente ao formulário
        setResizable(false);
        setLocationRelativeTo(parent); // Centraliza exatamente em cima da tela principal
    }
}