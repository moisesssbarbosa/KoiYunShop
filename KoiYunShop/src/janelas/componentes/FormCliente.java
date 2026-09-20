package janelas.componentes;

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
        // Layout em Grade: 5 linhas (uma para cada campo inserível) x 2 colunas
        setLayout(new GridLayout(5, 2, 8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Instanciação dos campos
        txtNome = new JTextField();
        txtCpfCnpj = new JTextField();
        txtTelefone = new JTextField();
        txtEmail = new JTextField();
        txtCidadeEstado = new JTextField();

        // Adição dos rótulos e componentes ao formulário
        add(new JLabel("Nome:"));
        add(txtNome);

        add(new JLabel("CPF/CNPJ:"));
        add(txtCpfCnpj);

        add(new JLabel("Telefone:"));
        add(txtTelefone);

        add(new JLabel("E-mail:"));
        add(txtEmail);

        add(new JLabel("Cidade/Estado:"));
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