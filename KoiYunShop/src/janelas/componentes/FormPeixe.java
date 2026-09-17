package janelas.componentes;

import javax.swing.*;
import java.awt.*;

public class FormPeixe extends JPanel {

    private JTextField txtCodigoVerificador;
    private JTextField txtVariedade;
    private JTextField txtDataEntrada;
    private JTextField txtTamanho;
    private JTextField txtPrecoVenda;
    private JComboBox<String> cbStatus;
    private JTextField txtIdLagoFk;
    private Integer idPeixeEmEdicao = null;

    public FormPeixe() {
        // Layout em Grade: 7 linhas (uma para cada campo inserível) x 2 colunas
        setLayout(new GridLayout(7, 2, 8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Instanciação dos campos
        txtCodigoVerificador = new JTextField();
        txtVariedade = new JTextField();
        
        // Campo de data com valor atual preenchido como sugestão (AAAA-MM-DD)
        txtDataEntrada = new JTextField(java.time.LocalDate.now().toString()); 
        
        txtTamanho = new JTextField();
        txtPrecoVenda = new JTextField();
        
        // Opções para o campo status VARCHAR(10)
        cbStatus = new JComboBox<>(new String[]{"DISPONIVEL", "VENDIDO", "QUARENTENA"});
        
        txtIdLagoFk = new JTextField();

        // Adição dos rótulos e componentes ao formulário
        add(new JLabel("Cód. Verificador:"));
        add(txtCodigoVerificador);

        add(new JLabel("Variedade:"));
        add(txtVariedade);

        add(new JLabel("Data Entrada (AAAA-MM-DD):"));
        add(txtDataEntrada);

        add(new JLabel("Tamanho (cm):"));
        add(txtTamanho);

        add(new JLabel("Preço Venda (R$):"));
        add(txtPrecoVenda);

        add(new JLabel("Status:"));
        add(cbStatus);

        add(new JLabel("ID Lago (FK):"));
        add(txtIdLagoFk);
    }

    // Método para preencher os campos quando for EDITAR
    public void carregarDadosParaEdicao(int id, String cod, String var, String data, String tam, String preco, String status, String lago) {
        this.idPeixeEmEdicao = id;
        this.txtCodigoVerificador.setText(cod);
        this.txtVariedade.setText(var);
        this.txtDataEntrada.setText(data);
        this.txtTamanho.setText(tam);
        this.txtPrecoVenda.setText(preco);
        this.cbStatus.setSelectedItem(status);
        this.txtIdLagoFk.setText(lago);
    }

    // Método para saber se está no modo edição
    public boolean isEdicao() {
        return this.idPeixeEmEdicao != null;
    }

    public Integer getIdPeixeEmEdicao() {
        return this.idPeixeEmEdicao;
    }

    // --- Métodos Getters para resgatar os dados digitados ---

    public String getCodigoVerificador() {
        return txtCodigoVerificador.getText().trim();
    }

    public String getVariedade() {
        return txtVariedade.getText().trim();
    }

    public String getDataEntrada() {
        return txtDataEntrada.getText().trim();
    }

    public String getTamanho() {
        return txtTamanho.getText().trim().replace(",", ".");
    }

    public String getPrecoVenda() {
        return txtPrecoVenda.getText().trim().replace(",", ".");
    }

    public String getStatus() {
        return (String) cbStatus.getSelectedItem();
    }

    public String getIdLagoFk() {
        return txtIdLagoFk.getText().trim();
    }

    // Atualize o método limparCampos() para zerar o ID também!
    public void limparCampos() {
        this.idPeixeEmEdicao = null; // Reseta de volta para modo "Novo Cadastro"
        txtCodigoVerificador.setText("");
        txtVariedade.setText("");
        txtDataEntrada.setText(java.time.LocalDate.now().toString());
        txtTamanho.setText("");
        txtPrecoVenda.setText("");
        cbStatus.setSelectedIndex(0);
        txtIdLagoFk.setText("");
    }

    public boolean isCamposValidos() {
        return !txtCodigoVerificador.getText().trim().isEmpty() &&
            !txtVariedade.getText().trim().isEmpty() &&
            !txtDataEntrada.getText().trim().isEmpty() &&
            !txtTamanho.getText().trim().isEmpty() &&
            !txtPrecoVenda.getText().trim().isEmpty() &&
            !txtIdLagoFk.getText().trim().isEmpty();
    }
}