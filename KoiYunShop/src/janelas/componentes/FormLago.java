package janelas.componentes;

import javax.swing.*;
import java.awt.*;

public class FormLago extends JPanel {

    private JTextField txtNomeLago;
    private JTextField txtCapacidadeLitros;
    private JTextField txtTipo;
    private JTextField txtStatusAgua;
    private JTextField txtTemperatura;
    private Integer idLagoEmEdicao = null;

    public FormLago() {
        // Layout em Grade: 5 linhas (uma para cada campo inserível) x 2 colunas
        setLayout(new GridLayout(5, 2, 8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Instanciação dos campos
        txtNomeLago = new JTextField();
        txtCapacidadeLitros = new JTextField();
        txtTipo = new JTextField();
        txtStatusAgua = new JTextField();
        txtTemperatura = new JTextField();

        // Adição dos rótulos e componentes ao formulário
        add(new JLabel("Nome do Lago:"));
        add(txtNomeLago);

        add(new JLabel("Capacidade (L):"));
        add(txtCapacidadeLitros);

        add(new JLabel("Tipo:"));
        add(txtTipo);

        add(new JLabel("Status da Água:"));
        add(txtStatusAgua);

        add(new JLabel("Temperatura (°C):"));
        add(txtTemperatura);
    }

    // Método para preencher os campos quando for EDITAR
    public void carregarDadosParaEdicao(int id, String nome, String capacidade, String tipo, String statusAgua, String temperatura) {
        this.idLagoEmEdicao = id;
        this.txtNomeLago.setText(nome);
        this.txtCapacidadeLitros.setText(capacidade);
        this.txtTipo.setText(tipo);
        this.txtStatusAgua.setText(statusAgua);
        this.txtTemperatura.setText(temperatura);
    }

    // Método para saber se está no modo edição
    public boolean isEdicao() {
        return this.idLagoEmEdicao != null;
    }

    public Integer getIdLagoEmEdicao() {
        return this.idLagoEmEdicao;
    }

    // --- Métodos Getters para resgatar os dados digitados ---

    public String getNomeLago() {
        return txtNomeLago.getText().trim();
    }

    public String getCapacidadeLitros() {
        return txtCapacidadeLitros.getText().trim().replace(",", ".");
    }

    public String getTipo() {
        return txtTipo.getText().trim();
    }

    public String getStatusAgua() {
        return txtStatusAgua.getText().trim();
    }

    public String getTemperatura() {
        return txtTemperatura.getText().trim().replace(",", ".");
    }

    // Reseta de volta para modo "Novo Cadastro"
    public void limparCampos() {
        this.idLagoEmEdicao = null;
        txtNomeLago.setText("");
        txtCapacidadeLitros.setText("");
        txtTipo.setText("");
        txtStatusAgua.setText("");
        txtTemperatura.setText("");
    }

    // Valida se os campos obrigatórios foram preenchidos
    public boolean isCamposValidos() {
        return !txtNomeLago.getText().trim().isEmpty() &&
               !txtCapacidadeLitros.getText().trim().isEmpty() &&
               !txtTipo.getText().trim().isEmpty() &&
               !txtStatusAgua.getText().trim().isEmpty() &&
               !txtTemperatura.getText().trim().isEmpty();
    }
}