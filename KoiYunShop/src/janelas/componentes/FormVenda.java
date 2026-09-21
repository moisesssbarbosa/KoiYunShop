package janelas.componentes;

import javax.swing.*;
import java.awt.*;

public class FormVenda extends JPanel {

    private JTextField txtDataVenda;
    private JTextField txtValorTotal;
    private JTextField txtFormaPagamento;
    private JTextField txtStatusEntrega;
    private JTextField txtIdClienteFk;
    private Integer idVendaEmEdicao = null;

    public FormVenda() {
        // Layout em Grade: 5 linhas (uma para cada campo inserível) x 2 colunas
        setLayout(new GridLayout(5, 2, 8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Instanciação dos campos com a data atual preenchida por padrão
        txtDataVenda = new JTextField(java.time.LocalDate.now().toString());
        txtValorTotal = new JTextField();
        txtFormaPagamento = new JTextField();
        txtStatusEntrega = new JTextField();
        txtIdClienteFk = new JTextField();

        // Adição dos rótulos e componentes ao formulário
        add(new JLabel("Data Venda (AAAA-MM-DD):"));
        add(txtDataVenda);

        add(new JLabel("Valor Total (R$):"));
        add(txtValorTotal);

        add(new JLabel("Forma de Pagamento:"));
        add(txtFormaPagamento);

        add(new JLabel("Status da Entrega:"));
        add(txtStatusEntrega);

        add(new JLabel("ID Cliente (FK):"));
        add(txtIdClienteFk);
    }

    // Método para preencher os campos quando for EDITAR
    public void carregarDadosParaEdicao(int id, String data, String valorTotal, String formaPagamento, String statusEntrega, String idCliente) {
        this.idVendaEmEdicao = id;
        this.txtDataVenda.setText(data);
        this.txtValorTotal.setText(valorTotal);
        this.txtFormaPagamento.setText(formaPagamento);
        this.txtStatusEntrega.setText(statusEntrega);
        this.txtIdClienteFk.setText(idCliente);
    }

    // Método para saber se está no modo edição
    public boolean isEdicao() {
        return this.idVendaEmEdicao != null;
    }

    public Integer getIdVendaEmEdicao() {
        return this.idVendaEmEdicao;
    }

    // --- Métodos Getters para resgatar os dados digitados ---

    public String getDataVenda() {
        return txtDataVenda.getText().trim();
    }

    public String getValorTotal() {
        return txtValorTotal.getText().trim().replace(",", ".");
    }

    public String getFormaPagamento() {
        return txtFormaPagamento.getText().trim();
    }

    public String getStatusEntrega() {
        return txtStatusEntrega.getText().trim();
    }

    public String getIdClienteFk() {
        return txtIdClienteFk.getText().trim();
    }

    // Método para preencher apenas o cliente numa NOVA venda
    public void preencherCliente(String idCliente) {
        this.txtIdClienteFk.setText(idCliente);
        this.txtIdClienteFk.setEditable(false); // Opcional: bloqueia o campo para o usuário não digitar o ID errado sem querer
    }

    // Reseta de volta para modo "Novo Cadastro"
    public void limparCampos() {
        this.idVendaEmEdicao = null;
        txtDataVenda.setText(java.time.LocalDate.now().toString());
        txtValorTotal.setText("");
        txtFormaPagamento.setText("");
        txtStatusEntrega.setText("");
        txtIdClienteFk.setText("");
    }

    // Valida se os campos obrigatórios foram preenchidos
    public boolean isCamposValidos() {
        return !txtDataVenda.getText().trim().isEmpty() &&
               !txtValorTotal.getText().trim().isEmpty() &&
               !txtFormaPagamento.getText().trim().isEmpty() &&
               !txtStatusEntrega.getText().trim().isEmpty() &&
               !txtIdClienteFk.getText().trim().isEmpty();
    }
}