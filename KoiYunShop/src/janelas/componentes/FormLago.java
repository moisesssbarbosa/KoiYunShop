package janelas.componentes;

import janelas.estilos.TemaKoi;
import javax.swing.*;
import java.awt.*;

public class FormLago extends JPanel {

    private JTextField txtNomeLago;
    private JTextField txtCapacidadeLitros;
    private JComboBox<String> cbTipo;
    private JComboBox<String> cbStatusAgua;
    private JTextField txtTemperatura;
    private Integer idLagoEmEdicao = null;

    // Opções dos ComboBoxes
    private static final String[] TIPOS_LAGO = {
        "natural",
        "artificial"
    };

    private static final String[] STATUS_AGUA = {
        "pronta",
        "tratando",
        "alerta"
    };

    public FormLago() {
        // Fundo do painel padronizado
        setBackground(TemaKoi.COR_FUNDO_TELA);

        // Layout de grade ajustado para 5 linhas x 2 colunas com espaçamentos adequados
        setLayout(new GridLayout(5, 2, 10, 12));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Instanciação dos campos
        txtNomeLago = new JTextField();
        txtCapacidadeLitros = new JTextField();
        cbTipo = new JComboBox<>(TIPOS_LAGO);
        cbStatusAgua = new JComboBox<>(STATUS_AGUA);
        txtTemperatura = new JTextField();

        // Aplicação do estilo nos campos de texto
        TemaKoi.estilizarCampoTexto(txtNomeLago);
        TemaKoi.estilizarCampoTexto(txtCapacidadeLitros);
        TemaKoi.estilizarCampoTexto(txtTemperatura);

        // Criando e estilizando os rótulos (Labels)
        JLabel lblNomeLago = new JLabel("Nome do Lago:");
        JLabel lblCapacidadeLitros = new JLabel("Capacidade (L):");
        JLabel lblTipo = new JLabel("Tipo:");
        JLabel lblStatusAgua = new JLabel("Status da Água:");
        JLabel lblTemperatura = new JLabel("Temperatura (°C):");

        TemaKoi.estilizarLabel(lblNomeLago);
        TemaKoi.estilizarLabel(lblCapacidadeLitros);
        TemaKoi.estilizarLabel(lblTipo);
        TemaKoi.estilizarLabel(lblStatusAgua);
        TemaKoi.estilizarLabel(lblTemperatura);

        // Adição dos rótulos e componentes ao formulário
        add(lblNomeLago);
        add(txtNomeLago);

        add(lblCapacidadeLitros);
        add(txtCapacidadeLitros);

        add(lblTipo);
        add(cbTipo);

        add(lblStatusAgua);
        add(cbStatusAgua);

        add(lblTemperatura);
        add(txtTemperatura);
    }

    // Método para preencher os campos quando for EDITAR
    public void carregarDadosParaEdicao(int id, String nome, String capacidade, String tipo, String statusAgua, String temperatura) {
        this.idLagoEmEdicao = id;
        this.txtNomeLago.setText(nome);
        this.txtCapacidadeLitros.setText(capacidade);
        this.cbTipo.setSelectedItem(tipo);
        this.cbStatusAgua.setSelectedItem(statusAgua);
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
        return (String) cbTipo.getSelectedItem();
    }

    public String getStatusAgua() {
        return (String) cbStatusAgua.getSelectedItem();
    }

    public String getTemperatura() {
        return txtTemperatura.getText().trim().replace(",", ".");
    }

    // Reseta de volta para modo "Novo Cadastro"
    public void limparCampos() {
        this.idLagoEmEdicao = null;
        txtNomeLago.setText("");
        txtCapacidadeLitros.setText("");
        if (cbTipo.getItemCount() > 0) cbTipo.setSelectedIndex(0);
        if (cbStatusAgua.getItemCount() > 0) cbStatusAgua.setSelectedIndex(0);
        txtTemperatura.setText("");
    }

    // Valida se os campos obrigatórios foram preenchidos
    public boolean isCamposValidos() {
        return !txtNomeLago.getText().trim().isEmpty() &&
               !txtCapacidadeLitros.getText().trim().isEmpty() &&
               cbTipo.getSelectedItem() != null &&
               cbStatusAgua.getSelectedItem() != null &&
               !txtTemperatura.getText().trim().isEmpty();
    }
}