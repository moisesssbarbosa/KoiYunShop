package janelas.componentes;

import janelas.estilos.TemaKoi;
import javax.swing.*;
import java.awt.*;

public class FormInsumo extends JPanel {

    private JTextField txtNomeInsumo;
    private JTextField txtQuantidadeAtualKg;
    private JTextField txtQuantidadeMinimaAlerta;
    private JTextField txtPrecoCustoPorKg;
    private Integer idInsumoEmEdicao = null;

    public FormInsumo() {
        // Fundo do painel padronizado
        setBackground(TemaKoi.COR_FUNDO_TELA);

        // Layout de grade ajustado para 4 linhas x 2 colunas com espaçamentos adequados
        setLayout(new GridLayout(4, 2, 10, 12));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Instanciação dos campos
        txtNomeInsumo = new JTextField();
        txtQuantidadeAtualKg = new JTextField();
        txtQuantidadeMinimaAlerta = new JTextField();
        txtPrecoCustoPorKg = new JTextField();

        // Aplicação do estilo nos campos de texto
        TemaKoi.estilizarCampoTexto(txtNomeInsumo);
        TemaKoi.estilizarCampoTexto(txtQuantidadeAtualKg);
        TemaKoi.estilizarCampoTexto(txtQuantidadeMinimaAlerta);
        TemaKoi.estilizarCampoTexto(txtPrecoCustoPorKg);

        // Criando e estilizando os rótulos (Labels)
        JLabel lblNomeInsumo = new JLabel("Nome Insumo:");
        JLabel lblQuantidadeAtualKg = new JLabel("Qtd. Atual (Kg):");
        JLabel lblQuantidadeMinimaAlerta = new JLabel("Qtd. Mínima Alerta (Kg):");
        JLabel lblPrecoCustoPorKg = new JLabel("Preço Custo por Kg (R$):");

        TemaKoi.estilizarLabel(lblNomeInsumo);
        TemaKoi.estilizarLabel(lblQuantidadeAtualKg);
        TemaKoi.estilizarLabel(lblQuantidadeMinimaAlerta);
        TemaKoi.estilizarLabel(lblPrecoCustoPorKg);

        // Adição dos rótulos e componentes ao formulário
        add(lblNomeInsumo);
        add(txtNomeInsumo);

        add(lblQuantidadeAtualKg);
        add(txtQuantidadeAtualKg);

        add(lblQuantidadeMinimaAlerta);
        add(txtQuantidadeMinimaAlerta);

        add(lblPrecoCustoPorKg);
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