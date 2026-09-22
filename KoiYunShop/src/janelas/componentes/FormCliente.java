package janelas.componentes;

import janelas.estilos.TemaKoi;
import javax.swing.*;
import java.awt.*;

public class FormCliente extends JPanel {

    private JTextField txtNome;
    private JTextField txtCpfCnpj;
    private JTextField txtTelefone;
    private JTextField txtEmail;
    private JTextField txtCidadeEstado;
    private Integer idClienteEmEdicao = null;

    public FormCliente() {
        // Fundo do painel padronizado
        setBackground(TemaKoi.COR_FUNDO_TELA);

        // Layout de grade ajustado para acomodar melhor os componentes maiores
        setLayout(new GridLayout(5, 2, 10, 12));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Instanciação dos campos
        txtNome = new JTextField();
        txtCpfCnpj = new JTextField();
        txtTelefone = new JTextField();
        txtEmail = new JTextField();
        txtCidadeEstado = new JTextField();

        // Aplicação do estilo nos campos de texto
        TemaKoi.estilizarCampoTexto(txtNome);
        TemaKoi.estilizarCampoTexto(txtCpfCnpj);
        TemaKoi.estilizarCampoTexto(txtTelefone);
        TemaKoi.estilizarCampoTexto(txtEmail);
        TemaKoi.estilizarCampoTexto(txtCidadeEstado);

        // Criando e estilizando os rótulos (Labels)
        JLabel lblNome = new JLabel("Nome:");
        JLabel lblCpfCnpj = new JLabel("CPF/CNPJ:");
        JLabel lblTelefone = new JLabel("Telefone:");
        JLabel lblEmail = new JLabel("E-mail:");
        JLabel lblCidadeEstado = new JLabel("Cidade/Estado:");

        TemaKoi.estilizarLabel(lblNome);
        TemaKoi.estilizarLabel(lblCpfCnpj);
        TemaKoi.estilizarLabel(lblTelefone);
        TemaKoi.estilizarLabel(lblEmail);
        TemaKoi.estilizarLabel(lblCidadeEstado);

        // Adição dos componentes ao formulário
        add(lblNome);
        add(txtNome);

        add(lblCpfCnpj);
        add(txtCpfCnpj);

        add(lblTelefone);
        add(txtTelefone);

        add(lblEmail);
        add(txtEmail);

        add(lblCidadeEstado);
        add(txtCidadeEstado);
    }

    // Método para preencher os campos quando for EDITAR
    public void carregarDadosParaEdicao(int id, String nome, String cpfCnpj, String telefone, String email, String cidadeEstado) {
        this.idClienteEmEdicao = id;
        this.txtNome.setText(nome);
        this.txtCpfCnpj.setText(cpfCnpj);
        this.txtTelefone.setText(telefone);
        this.txtEmail.setText(email);
        this.txtCidadeEstado.setText(cidadeEstado);
    }

    // Método para saber se está no modo edição
    public boolean isEdicao() {
        return this.idClienteEmEdicao != null;
    }

    public Integer getIdClienteEmEdicao() {
        return this.idClienteEmEdicao;
    }

    // --- Métodos Getters para resgatar os dados digitados ---

    public String getNome() {
        return txtNome.getText().trim();
    }

    public String getCpfCnpj() {
        return txtCpfCnpj.getText().trim();
    }

    public String getTelefone() {
        return txtTelefone.getText().trim();
    }

    public String getEmail() {
        return txtEmail.getText().trim();
    }

    public String getCidadeEstado() {
        return txtCidadeEstado.getText().trim();
    }

    // Reseta de volta para modo "Novo Cadastro"
    public void limparCampos() {
        this.idClienteEmEdicao = null;
        txtNome.setText("");
        txtCpfCnpj.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtCidadeEstado.setText("");
    }

    // Valida se os campos obrigatórios foram preenchidos
    public boolean isCamposValidos() {
        return !txtNome.getText().trim().isEmpty() &&
               !txtCpfCnpj.getText().trim().isEmpty() &&
               !txtTelefone.getText().trim().isEmpty() &&
               !txtEmail.getText().trim().isEmpty() &&
               !txtCidadeEstado.getText().trim().isEmpty();
    }
}