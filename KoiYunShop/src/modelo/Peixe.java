package modelo;

import java.math.BigDecimal;
import java.util.Date;

public class Peixe {
    private int idPeixe;
    private int codigoIdentificador;
    private String variedade;
    private Date dataEntrada;
    private BigDecimal tamanhoCm;
    private BigDecimal precoVenda;
    private String status;
    private int idLago;

    public Peixe() {}

    public Peixe(int idPeixe, int codigoIdentificador, String variedade, Date dataEntrada, BigDecimal tamanhoCm, BigDecimal precoVenda, String status, int idLago) {
        this.idPeixe = idPeixe;
        this.codigoIdentificador = codigoIdentificador;
        this.variedade = variedade;
        this.dataEntrada = dataEntrada;
        this.tamanhoCm = tamanhoCm;
        this.precoVenda = precoVenda;
        this.status = status;
        this.idLago = idLago;
    }

    public int getIdPeixe() {
        return idPeixe;
    }
    public void setIdPeixe(int idPeixe) {
        this.idPeixe = idPeixe;
    }

    public int getCodigoIdentificador() {
        return codigoIdentificador;
    }
    public void setCodigoIdentificador(int codigoIdentificador) {
        this.codigoIdentificador = codigoIdentificador;
    }

    public String getVariedade() {
        return variedade;
    }
    public void setVariedade(String variedade) {
        this.variedade = variedade;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }
    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public BigDecimal getTamanhoCm() {
        return tamanhoCm;
    }
    public void setTamanhoCm(BigDecimal tamanhoCm) {
        this.tamanhoCm = tamanhoCm;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }
    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdLago() {
        return idLago;
    }
    public void setIdLago(int idLago) {
        this.idLago = idLago;
    }
}