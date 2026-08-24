import java.math.BigDecimal;
import java.util.Date;

public class Movimentacao {
    private int idMovimentacao;
    private Date dataMovimentacao;
    private BigDecimal valor;
    private String categoria;
    private String descricao;
    private int idInsumo;

    public Movimentacao() {}

    public Movimentacao(int idMovimentacao, Date dataMovimentacao, BigDecimal valor, String categoria, String descricao, int idInsumo) {
        this.idMovimentacao = idMovimentacao;
        this.dataMovimentacao = dataMovimentacao;
        this.valor = valor;
        this.categoria = categoria;
        this.descricao = descricao;
        this.idInsumo = idInsumo;
    }


    public int getIdMovimentacao() {
        return idMovimentacao;
    }
    public void setIdMovimentacao(int idMovimentacao) {
        this.idMovimentacao = idMovimentacao;
    }

    public Date getDataMovimentacao() {
        return dataMovimentacao;
    }
    public void setDataMovimentacao(Date dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public BigDecimal getValor() {
        return valor;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdInsumo() {
        return idInsumo;
    }
    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = idInsumo;
    }
}