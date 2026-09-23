package janelas.componentes;

import janelas.estilos.TemaKoi;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormPeixe extends JPanel {

    private JTextField txtCodigoVerificador;
    private JComboBox<String> cbVariedade;
    private JTextField txtDataEntrada;
    private JTextField txtTamanho;
    private JTextField txtPrecoVenda;
    private JComboBox<String> cbStatus;
    private JTextField txtIdLagoFk;
    private Integer idPeixeEmEdicao = null;

    // Lista de variedades de carpas Koi
    private static final String[] VARIEDADES = {
        "Kohaku",
        "Taisho Sanshoku (Sanke)",
        "Showa Sanshoku (Showa)",
        "Ogon",
        "Kujaku",
        "Yamatonishiki",
        "Sakura Ogon",
        "Shiro Utsuri",
        "Ki Utsuri",
        "Hi Utsuri",
        "Shusui",
        "Kumonryu",
        "Asagi",
        "Tancho",
        "Goshiki",
        "Chagoi",
        "Soragoi",
        "Ochiba Shigure",
        "Kikoromo (Koromo)",
        "Benigoi"
    };

    public FormPeixe() {
        // Fundo do painel padronizado
        setBackground(TemaKoi.COR_FUNDO_TELA);

        // Layout de grade ajustado para 7 linhas x 2 colunas com espaçamentos adequados
        setLayout(new GridLayout(7, 2, 10, 12));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Instanciação dos campos
        txtCodigoVerificador = new JTextField();
        cbVariedade = new JComboBox<>(VARIEDADES);
        txtDataEntrada = new JTextField(); 
        txtTamanho = new JTextField();
        txtPrecoVenda = new JTextField();
        cbStatus = new JComboBox<>(new String[]{"DISPONIVEL", "VENDIDO", "QUARENTENA"});
        txtIdLagoFk = new JTextField();

        // Aplicação do estilo nos campos de texto
        TemaKoi.estilizarCampoTexto(txtCodigoVerificador);
        TemaKoi.estilizarCampoTexto(txtDataEntrada);
        TemaKoi.estilizarCampoTexto(txtTamanho);
        TemaKoi.estilizarCampoTexto(txtPrecoVenda);
        TemaKoi.estilizarCampoTexto(txtIdLagoFk);

        // Criando e estilizando os rótulos (Labels)
        JLabel lblCodigoVerificador = new JLabel("Cód. Verificador:");
        JLabel lblVariedade = new JLabel("Variedade:");
        JLabel lblDataEntrada = new JLabel("Data Entrada (dd/MM/yyyy):");
        JLabel lblTamanho = new JLabel("Tamanho (cm):");
        JLabel lblPrecoVenda = new JLabel("Preço Venda (R$):");
        JLabel lblStatus = new JLabel("Status:");
        JLabel lblIdLagoFk = new JLabel("ID Lago (FK):");

        TemaKoi.estilizarLabel(lblCodigoVerificador);
        TemaKoi.estilizarLabel(lblVariedade);
        TemaKoi.estilizarLabel(lblDataEntrada);
        TemaKoi.estilizarLabel(lblTamanho);
        TemaKoi.estilizarLabel(lblPrecoVenda);
        TemaKoi.estilizarLabel(lblStatus);
        TemaKoi.estilizarLabel(lblIdLagoFk);

        // Adição dos rótulos e componentes ao formulário
        add(lblCodigoVerificador);
        add(txtCodigoVerificador);

        add(lblVariedade);
        add(cbVariedade);

        add(lblDataEntrada);
        add(txtDataEntrada);

        add(lblTamanho);
        add(txtTamanho);

        add(lblPrecoVenda);
        add(txtPrecoVenda);

        add(lblStatus);
        add(cbStatus);

        add(lblIdLagoFk);
        add(txtIdLagoFk);
    }

    public void carregarDadosParaEdicao(int id, String cod, String var, String data, String tam, String preco, String status, String lago) {
        this.idPeixeEmEdicao = id;
        this.txtCodigoVerificador.setText(cod);
        this.cbVariedade.setSelectedItem(var);

        // Se a data vier como String no formato "yyyy-MM-dd"
        if (data != null && !data.isEmpty()) {
            try {
                SimpleDateFormat sdfBanco = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdfTela = new SimpleDateFormat("dd/MM/yyyy");
                
                Date dateObj = sdfBanco.parse(data);
                this.txtDataEntrada.setText(sdfTela.format(dateObj));
            } catch (Exception e) {
                this.txtDataEntrada.setText(data); // Se falhar a conversão, mantém o texto original
            }
        } else {
            this.txtDataEntrada.setText("");
        }

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
        return (String) cbVariedade.getSelectedItem();
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

    // Reseta de volta para modo "Novo Cadastro"
    public void limparCampos() {
        this.idPeixeEmEdicao = null;
        txtCodigoVerificador.setText("");
        if (cbVariedade.getItemCount() > 0) {
            cbVariedade.setSelectedIndex(0);
        }
        txtDataEntrada.setText("");
        txtTamanho.setText("");
        txtPrecoVenda.setText("");
        cbStatus.setSelectedIndex(0);
        txtIdLagoFk.setText("");
    }

    public boolean isCamposValidos() {
        return !txtCodigoVerificador.getText().trim().isEmpty() &&
               cbVariedade.getSelectedItem() != null &&
               !txtDataEntrada.getText().trim().isEmpty() &&
               !txtTamanho.getText().trim().isEmpty() &&
               !txtPrecoVenda.getText().trim().isEmpty() &&
               !txtIdLagoFk.getText().trim().isEmpty();
    }
}