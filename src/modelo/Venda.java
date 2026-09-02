package modelo;

import java.math.BigDecimal;
import java.util.Date;

public class Venda {
    private int idVenda;
    private Date dataVenda;
    private BigDecimal valorTotal;
    private String formaPagamento;
    private String statusEntrega;
    private int idCliente;

    public Venda() {}

    public Venda(int idVenda, Date dataVenda, BigDecimal valorTotal, String formaPagamento, String statusEntrega, int idCliente) {
        this.idVenda = idVenda;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.formaPagamento = formaPagamento;
        this.statusEntrega = statusEntrega;
        this.idCliente = idCliente;
    }

    public int getIdVenda() {
        return idVenda;
    }
    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public Date getDataVenda() {
        return dataVenda;
    }
    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }
    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getStatusEntrega() {
        return statusEntrega;
    }
    public void setStatusEntrega(String statusEntrega) {
        this.statusEntrega = statusEntrega;
    }

    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
