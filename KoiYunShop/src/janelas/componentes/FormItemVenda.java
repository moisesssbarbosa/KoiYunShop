package janelas.componentes;

import janelas.estilos.TemaKoi;
import javax.swing.*;
import java.awt.*;

public class FormItemVenda extends JPanel {

    private JTextField txtPreco;
    private JTextField txtIdVendaFk;
    private JTextField txtIdPeixeFk;
    private Integer idItemVendaEmEdicao = null;

    public FormItemVenda() {
        // Fundo do painel padronizado
        setBackground(TemaKoi.COR_FUNDO_TELA);

        // Layout de grade ajustado para 3 linhas x 2 colunas com espaçamentos adequados
        setLayout(new GridLayout(3, 2, 10, 12));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Instanciação dos campos
        txtPreco = new JTextField();
        txtIdVendaFk = new JTextField();
        txtIdPeixeFk = new JTextField();

        // Aplicação do estilo nos campos de texto
        TemaKoi.estilizarCampoTexto(txtPreco);
        TemaKoi.estilizarCampoTexto(txtIdVendaFk);
        TemaKoi.estilizarCampoTexto(txtIdPeixeFk);

        // Criando e estilizando os rótulos (Labels)
        JLabel lblPreco = new JLabel("Preço (R$):");
        JLabel lblIdVendaFk = new JLabel("ID Venda (FK):");
        JLabel lblIdPeixeFk = new JLabel("Código Identificador do peixe (FK):");

        TemaKoi.estilizarLabel(lblPreco);
        TemaKoi.estilizarLabel(lblIdVendaFk);
        TemaKoi.estilizarLabel(lblIdPeixeFk);

        // Adição dos rótulos e componentes ao formulário
        add(lblPreco);
        add(txtPreco);

        add(lblIdVendaFk);
        add(txtIdVendaFk);

        add(lblIdPeixeFk);
        add(txtIdPeixeFk);
    }

    // Método para preencher os campos quando for EDITAR
    public void carregarDadosParaEdicao(int id, String preco, String idVenda, String idPeixe) {
        this.idItemVendaEmEdicao = id;
        this.txtPreco.setText(preco);
        this.txtIdVendaFk.setText(idVenda);
        this.txtIdPeixeFk.setText(idPeixe);
    }

    // Método para definir o ID da venda automaticamente (útil ao inserir itens dentro da tela de venda)
    public void setIdVendaFk(String idVenda) {
        this.txtIdVendaFk.setText(idVenda);
    }

    // Método para saber se está no modo edição
    public boolean isEdicao() {
        return this.idItemVendaEmEdicao != null;
    }

    public Integer getIdItemVendaEmEdicao() {
        return this.idItemVendaEmEdicao;
    }

    // --- Métodos Getters para resgatar os dados digitados ---

    public String getPreco() {
        return txtPreco.getText().trim().replace(",", ".");
    }

    public String getIdVendaFk() {
        return txtIdVendaFk.getText().trim();
    }

    public String getIdPeixeFk() {
        return txtIdPeixeFk.getText().trim();
    }

    // Reseta de volta para modo "Novo Cadastro"
    public void limparCampos() {
        this.idItemVendaEmEdicao = null;
        txtPreco.setText("");
        txtIdVendaFk.setText("");
        txtIdPeixeFk.setText("");
    }

    // Valida se os campos obrigatórios foram preenchidos
    public boolean isCamposValidos() {
        return !txtPreco.getText().trim().isEmpty() &&
               !txtIdVendaFk.getText().trim().isEmpty() &&
               !txtIdPeixeFk.getText().trim().isEmpty();
    }
}