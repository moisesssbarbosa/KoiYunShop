package modelo;

import java.math.BigDecimal;

public class Lago {
    private int idLago;
    private String nomeLago;
    private BigDecimal capacidadeLitros;
    private String tipo;
    private String statusAgua;
    private BigDecimal temperatura;

    public Lago() {}

    public Lago(int idLago, String nomeLago, BigDecimal capacidadeLitros, String tipo, String statusAgua, BigDecimal temperatura) {
        this.idLago = idLago;
        this.nomeLago = nomeLago;
        this.capacidadeLitros = capacidadeLitros;
        this.tipo = tipo;
        this.statusAgua = statusAgua;
        this.temperatura = temperatura;
    }


    public int getIdLago() {
        return idLago;
    }
    public void setIdLago(int idLago) {
        this.idLago = idLago;
    }

    public String getNomeLago() {
        return nomeLago;
    }
    public void setNomeLago(String nomeLago) {
        this.nomeLago = nomeLago;
    }

    public BigDecimal getCapacidadeLitros() {
        return capacidadeLitros;
    }
    public void setCapacidadeLitros(BigDecimal capacidadeLitros) {
        this.capacidadeLitros = capacidadeLitros;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatusAgua() {
        return statusAgua;
    }
    public void setStatusAgua(String statusAgua) {
        this.statusAgua = statusAgua;
    }

    public BigDecimal getTemperatura() {
        return temperatura;
    }
    public void setTemperatura(BigDecimal temperatura) {
        this.temperatura = temperatura;
    }
}
