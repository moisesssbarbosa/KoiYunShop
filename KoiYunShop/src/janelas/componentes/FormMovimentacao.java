package janelas.componentes;

import janelas.estilos.TemaKoi;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormMovimentacao extends JPanel {

    private JTextField txtDataMovimentacao;
    private JComboBox<String> cbCategoria;
    private JTextField txtDescricao;
    private JTextField txtIdInsumoFk;
    private JTextField txtValor;
    private Integer idMovimentacaoEmEdicao = null;

    // Opções do ComboBox de Categoria
    private static final String[] CATEGORIAS = {
        "Insumos",
        "Despesas",
        "Aquisições"
    };

    public FormMovimentacao() {
        // Fundo do painel padronizado
        setBackground(TemaKoi.COR_FUNDO_TELA);

        // Layout de grade ajustado para 5 linhas x 2 colunas com espaçamentos adequados
        setLayout(new GridLayout(5, 2, 10, 12));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Instanciação dos campos
        txtDataMovimentacao = new JTextField();
        cbCategoria = new JComboBox<>(CATEGORIAS);
        txtDescricao = new JTextField();
        txtIdInsumoFk = new JTextField();
        txtValor = new JTextField();

        // Aplicação do estilo nos campos de texto
        TemaKoi.estilizarCampoTexto(txtDataMovimentacao);
        TemaKoi.estilizarCampoTexto(txtDescricao);
        TemaKoi.estilizarCampoTexto(txtIdInsumoFk);
        TemaKoi.estilizarCampoTexto(txtValor);

        // Criando e estilizando os rótulos (Labels)
        JLabel lblDataMovimentacao = new JLabel("Data Movimentação (dd/MM/yyyy):");
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
        add(cbCategoria);

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
        
        // Se a data vier como String no formato "yyyy-MM-dd"
        if (data != null && !data.isEmpty()) {
            try {
                SimpleDateFormat sdfBanco = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdfTela = new SimpleDateFormat("dd/MM/yyyy");
                
                Date dateObj = sdfBanco.parse(data);
                this.txtDataMovimentacao.setText(sdfTela.format(dateObj));
            } catch (Exception e) {
                this.txtDataMovimentacao.setText(data); // Se falhar a conversão, mantém o texto original
            }
        } else {
            this.txtDataMovimentacao.setText("");
        }


        this.cbCategoria.setSelectedItem(categoria);
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
        return (String) cbCategoria.getSelectedItem();
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
        if (cbCategoria.getItemCount() > 0) cbCategoria.setSelectedIndex(0);
        txtDescricao.setText("");
        txtIdInsumoFk.setText("");
        txtValor.setText("");
    }

    // Valida se os campos obrigatórios foram preenchidos
    public boolean isCamposValidos() {
        return !txtDataMovimentacao.getText().trim().isEmpty() &&
               cbCategoria.getSelectedItem() != null &&
               !txtDescricao.getText().trim().isEmpty() &&
               !txtIdInsumoFk.getText().trim().isEmpty() &&
               !txtValor.getText().trim().isEmpty();
    }
}