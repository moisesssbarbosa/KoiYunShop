package janelas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ModalCadastroBase extends JDialog {

    private JPanel painelFormularioAtual;

    // O construtor recebe o formulário (JPanel) que deve ser exibido
    public ModalCadastroBase(Frame parent, String titulo, JPanel painelFormulario, ActionListener acaoSalvar) {
        super(parent, titulo, true); // Modal = true
        this.painelFormularioAtual = painelFormulario;

        setSize(400, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Adiciona o formulário recebido no CENTRO do modal
        add(painelFormularioAtual, BorderLayout.CENTER);

        // Painel de Botões padrão no RODAPÉ
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        // Executa a ação de salvar que foi passada por parâmetro
        btnSalvar.addActionListener(acaoSalvar);
        btnCancelar.addActionListener(e -> dispose());

        pnlBotoes.add(btnSalvar);
        pnlBotoes.add(btnCancelar);

        add(pnlBotoes, BorderLayout.SOUTH);
    }
}