package janelas.componentes;

import janelas.estilos.TemaKoi;
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
        // Fundo do painel padronizado
        setBackground(TemaKoi.COR_FUNDO_TELA);

        // Layout de grade ajustado para 5 linhas x 2 colunas com espaçamentos adequados
        setLayout(new GridLayout(5, 2, 10, 12));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Instanciação dos campos com a data atual preenchida por padrão
        txtDataMovimentacao = new JTextField(java.time.LocalDate.now().toString());
        txtCategoria = new JTextField();
        txtDescricao = new JTextField();
        txtIdInsumoFk = new JTextField();
        txtValor = new JTextField();

        // Aplicação do estilo nos campos de texto
        TemaKoi.estilizarCampoTexto(txtDataMovimentacao);
        TemaKoi.estilizarCampoTexto(txtCategoria);
        TemaKoi.estilizarCampoTexto(txtDescricao);
        TemaKoi.estilizarCampoTexto(txtIdInsumoFk);
        TemaKoi.estilizarCampoTexto(txtValor);

        // Criando e estilizando os rótulos (Labels)
        JLabel lblDataMovimentacao = new JLabel("Data Movimentação (AAAA-MM-DD):");
        JLabel lblCategoria = new JLabel("Categoria:");
        JLabel lblDescricao = new JLabel("Descrição:");
        JLabel lblIdInsumoFk = new JLabel("ID Insumo (FK):");
        JLabel lblValor = new JLabel("Valor (R$):");

        TemaKoi.estilizarLabel(lblDataMovimentacao);
        TemaKoi.estilizarLabel(lblCategoria);
        TemaKoi.estilizarLabel(lblDescricao);
        TemaKoi.estilizarLabel(lblIdInsumoFk);
        TemaKoi.estilizarLabel(lblValor);

        // Adição dos rótulos e componentes ao formulário
        add(lblDataMovimentacao);
        add(txtDataMovimentacao);

        add(lblCategoria);
        add(txtCategoria);

        add(lblDescricao);
        add(txtDescricao);

        add(lblIdInsumoFk);
        add(txtIdInsumoFk);

        add(lblValor);
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