package janelas.componentes;

import javax.swing.*;
import java.awt.*;

public class FormInsumo extends JPanel {

    private JTextField txtNomeInsumo;
    private JTextField txtQuantidadeAtualKg;
    private JTextField txtQuantidadeMinimaAlerta;
    private JTextField txtPrecoCustoPorKg;
    private Integer idInsumoEmEdicao = null;

    public FormInsumo() {
        // Layout em Grade: 4 linhas (uma para cada campo inserível) x 2 colunas
        setLayout(new GridLayout(4, 2, 8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Instanciação dos campos
        txtNomeInsumo = new JTextField();
        txtQuantidadeAtualKg = new JTextField();
        txtQuantidadeMinimaAlerta = new JTextField();
        txtPrecoCustoPorKg = new JTextField();

        // Adição dos rótulos e componentes ao formulário
        add(new JLabel("Nome Insumo:"));
        add(txtNomeInsumo);

        add(new JLabel("Qtd. Atual (Kg):"));
        add(txtQuantidadeAtualKg);

        add(new JLabel("Qtd. Mínima Alerta (Kg):"));
        add(txtQuantidadeMinimaAlerta);

        add(new JLabel("Preço Custo por Kg (R$):"));
        add(txtPrecoCustoPorKg);
    }

    // Método para preencher os campos quando for EDITAR
    public void carregarDadosParaEdicao(int id, String nome, String qtdAtual, String qtdMinima, String precoCusto) {
        this.idInsumoEmEdicao = id;
        this.txtNomeInsumo.setText(nome);
        this.txtQuantidadeAtualKg.setText(qtdAtual);
        this.txtQuantidadeMinimaAlerta.setText(qtdMinima);
        this.txtPrecoCustoPorKg.setText(precoCusto);
    }

    // Método para saber se está no modo edição
    public boolean isEdicao() {
        return this.idInsumoEmEdicao != null;
    }

    public Integer getIdInsumoEmEdicao() {
        return this.idInsumoEmEdicao;
    }

    // --- Métodos Getters para resgatar os dados digitados ---

    public String getNomeInsumo() {
        return txtNomeInsumo.getText().trim();
    }

    public String getQuantidadeAtualKg() {
        return txtQuantidadeAtualKg.getText().trim().replace(",", ".");
    }

    public String getQuantidadeMinimaAlerta() {
        return txtQuantidadeMinimaAlerta.getText().trim().replace(",", ".");
    }

    public String getPrecoCustoPorKg() {
        return txtPrecoCustoPorKg.getText().trim().replace(",", ".");
    }

    // Reseta de volta para modo "Novo Cadastro"
    public void limparCampos() {
        this.idInsumoEmEdicao = null;
        txtNomeInsumo.setText("");
        txtQuantidadeAtualKg.setText("");
        txtQuantidadeMinimaAlerta.setText("");
        txtPrecoCustoPorKg.setText("");
    }

    // Valida se os campos obrigatórios foram preenchidos
    public boolean isCamposValidos() {
        return !txtNomeInsumo.getText().trim().isEmpty() &&
               !txtQuantidadeAtualKg.getText().trim().isEmpty() &&
               !txtQuantidadeMinimaAlerta.getText().trim().isEmpty() &&
               !txtPrecoCustoPorKg.getText().trim().isEmpty();
    }
}