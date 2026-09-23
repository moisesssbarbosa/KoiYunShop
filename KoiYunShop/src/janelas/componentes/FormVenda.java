package janelas.componentes;

import janelas.estilos.TemaKoi;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormVenda extends JPanel {

    private JTextField txtDataVenda;
    private JTextField txtValorTotal;
    private JComboBox<String> cbFormaPagamento;
    private JComboBox<String> cbStatusEntrega;
    private JTextField txtIdClienteFk;
    private Integer idVendaEmEdicao = null;

    // Opções dos ComboBoxes
    private static final String[] FORMAS_PAGAMENTO = {
        "debito",
        "credito",
        "pix",
        "boleto"
    };

    private static final String[] STATUS_ENTREGA = {
        "entregue",
        "em rota",
        "preparando"
    };

    public FormVenda() {
        // Fundo do painel padronizado
        setBackground(TemaKoi.COR_FUNDO_TELA);

        // Layout em Grade: 5 linhas x 2 colunas com espaçamentos adequados
        setLayout(new GridLayout(5, 2, 10, 12));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Instanciação dos campos
        txtDataVenda = new JTextField();
        txtValorTotal = new JTextField();
        cbFormaPagamento = new JComboBox<>(FORMAS_PAGAMENTO);
        cbStatusEntrega = new JComboBox<>(STATUS_ENTREGA);
        txtIdClienteFk = new JTextField();

        // Aplicação do estilo nos campos de texto
        TemaKoi.estilizarCampoTexto(txtDataVenda);
        TemaKoi.estilizarCampoTexto(txtValorTotal);
        TemaKoi.estilizarCampoTexto(txtIdClienteFk);

        // Criando e estilizando os rótulos (Labels)
        JLabel lblDataVenda = new JLabel("Data Venda (dd/MM/yyyy):");
        JLabel lblValorTotal = new JLabel("Valor Total (R$):");
        JLabel lblFormaPagamento = new JLabel("Forma de Pagamento:");
        JLabel lblStatusEntrega = new JLabel("Status da Entrega:");
        JLabel lblIdClienteFk = new JLabel("ID Cliente (FK):");

        TemaKoi.estilizarLabel(lblDataVenda);
        TemaKoi.estilizarLabel(lblValorTotal);
        TemaKoi.estilizarLabel(lblFormaPagamento);
        TemaKoi.estilizarLabel(lblStatusEntrega);
        TemaKoi.estilizarLabel(lblIdClienteFk);

        // Adição dos rótulos e componentes ao formulário
        add(lblDataVenda);
        add(txtDataVenda);

        add(lblValorTotal);
        add(txtValorTotal);

        add(lblFormaPagamento);
        add(cbFormaPagamento);

        add(lblStatusEntrega);
        add(cbStatusEntrega);

        add(lblIdClienteFk);
        add(txtIdClienteFk);
    }

    // Método para preencher os campos quando for EDITAR
    public void carregarDadosParaEdicao(int id, String data, String valorTotal, String formaPagamento, String statusEntrega, String idCliente) {
        this.idVendaEmEdicao = id;
        
        
        // Se a data vier como String no formato "yyyy-MM-dd"
        if (data != null && !data.isEmpty()) {
            try {
                SimpleDateFormat sdfBanco = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdfTela = new SimpleDateFormat("dd/MM/yyyy");
                
                Date dateObj = sdfBanco.parse(data);
                this.txtDataVenda.setText(sdfTela.format(dateObj));
            } catch (Exception e) {
                this.txtDataVenda.setText(data); // Se falhar a conversão, mantém o texto original
            }
        } else {
            this.txtDataVenda.setText("");
        }

        this.txtValorTotal.setText(valorTotal);
        this.cbFormaPagamento.setSelectedItem(formaPagamento);
        this.cbStatusEntrega.setSelectedItem(statusEntrega);
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
        return (String) cbFormaPagamento.getSelectedItem();
    }

    public String getStatusEntrega() {
        return (String) cbStatusEntrega.getSelectedItem();
    }

    public String getIdClienteFk() {
        return txtIdClienteFk.getText().trim();
    }

    // Método para preencher apenas o cliente numa NOVA venda
    public void preencherCliente(String idCliente) {
        this.txtIdClienteFk.setText(idCliente);
        this.txtIdClienteFk.setEditable(false); // Bloqueia o campo para evitar digitação manual incorreta
    }

    // Reseta de volta para modo "Novo Cadastro"
    public void limparCampos() {
        this.idVendaEmEdicao = null;
        txtDataVenda.setText("");
        txtValorTotal.setText("");
        if (cbFormaPagamento.getItemCount() > 0) cbFormaPagamento.setSelectedIndex(0);
        if (cbStatusEntrega.getItemCount() > 0) cbStatusEntrega.setSelectedIndex(0);
        txtIdClienteFk.setText("");
        txtIdClienteFk.setEditable(true);
    }

    // Valida se os campos obrigatórios foram preenchidos
    public boolean isCamposValidos() {
        return !txtDataVenda.getText().trim().isEmpty() &&
               !txtValorTotal.getText().trim().isEmpty() &&
               cbFormaPagamento.getSelectedItem() != null &&
               cbStatusEntrega.getSelectedItem() != null &&
               !txtIdClienteFk.getText().trim().isEmpty();
    }
}