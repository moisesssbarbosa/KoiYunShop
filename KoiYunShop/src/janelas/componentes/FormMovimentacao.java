package janelas.componentes;

import javax.swing.*;
import java.awt.*;

public class FormMovimentacao extends JPanel {

    private JTextField txtDataMovimentacao;
    private JTextField txtCategoria;
    private JTextField txtDescricao;
    private JTextField txtIdInsumoFk;
    private JTextField txtValor;
    private Integer idMovimentacaoEmEdicao = null;

    public FormMovimentacao() {
        // Layout em Grade: 5 linhas (uma para cada campo inserível) x 2 colunas
        setLayout(new GridLayout(5, 2, 8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Instanciação dos campos com a data atual preenchida por padrão
        txtDataMovimentacao = new JTextField(java.time.LocalDate.now().toString());
        txtCategoria = new JTextField();
        txtDescricao = new JTextField();
        txtIdInsumoFk = new JTextField();
        txtValor = new JTextField();

        // Adição dos rótulos e componentes ao formulário
        add(new JLabel("Data Movimentação (AAAA-MM-DD):"));
        add(txtDataMovimentacao);

        add(new JLabel("Categoria:"));
        add(txtCategoria);

        add(new JLabel("Descrição:"));
        add(txtDescricao);

        add(new JLabel("ID Insumo (FK):"));
        add(txtIdInsumoFk);

        add(new JLabel("Valor (R$):"));
        add(txtValor);
    }

    // Método para preencher os campos quando for EDITAR
    public void carregarDadosParaEdicao(int id, String data, String categoria, String descricao, String idInsumo, String valor) {
        this.idMovimentacaoEmEdicao = id;
        this.txtDataMovimentacao.setText(data);
        this.txtCategoria.setText(categoria);
        this.txtDescricao.setText(descricao);
        this.txtIdInsumoFk.setText(idInsumo);
        this.txtValor.setText(valor);
    }

    // Método para saber se está no modo edição
    public boolean isEdicao() {
        return this.idMovimentacaoEmEdicao != null;
    }

    public Integer getIdMovimentacaoEmEdicao() {
        return this.idMovimentacaoEmEdicao;
    }

    // --- Métodos Getters para resgatar os dados digitados ---

    public String getDataMovimentacao() {
        return txtDataMovimentacao.getText().trim();
    }

    public String getCategoria() {
        return txtCategoria.getText().trim();
    }

    public String getDescricao() {
        return txtDescricao.getText().trim();
    }

    public String getIdInsumoFk() {
        return txtIdInsumoFk.getText().trim();
    }

    public String getValor() {
        return txtValor.getText().trim().replace(",", ".");
    }

    // Reseta de volta para modo "Novo Cadastro"
    public void limparCampos() {
        this.idMovimentacaoEmEdicao = null;
        txtDataMovimentacao.setText(java.time.LocalDate.now().toString());
        txtCategoria.setText("");
        txtDescricao.setText("");
        txtIdInsumoFk.setText("");
        txtValor.setText("");
    }

    // Valida se os campos obrigatórios foram preenchidos
    public boolean isCamposValidos() {
        return !txtDataMovimentacao.getText().trim().isEmpty() &&
               !txtCategoria.getText().trim().isEmpty() &&
               !txtDescricao.getText().trim().isEmpty() &&
               !txtIdInsumoFk.getText().trim().isEmpty() &&
               !txtValor.getText().trim().isEmpty();
    }
}