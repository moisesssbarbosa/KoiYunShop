import java.math.BigDecimal;

public class Insumo {
    private int idInsumo;
    private String nomeInsumo;
    private BigDecimal quantidadeAtualKg;
    private BigDecimal quantidadeMinimaAlerta;
    private BigDecimal precoCustoPorKg;

    public Insumo() {}

    public Insumo(int idInsumo, String nomeInsumo, BigDecimal quantidadeAtualKg, BigDecimal quantidadeMinimaAlerta, BigDecimal precoCustoPorKg) {
        this.idInsumo = idInsumo;
        this.nomeInsumo = nomeInsumo;
        this.quantidadeAtualKg = quantidadeAtualKg;
        this.quantidadeMinimaAlerta = quantidadeMinimaAlerta;
        this.precoCustoPorKg = precoCustoPorKg;
    }


    public int getIdInsumo() {
        return idInsumo;
    }
    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getNomeInsumo() {
        return nomeInsumo;
    }
    public void setNomeInsumo(String nomeInsumo) {
        this.nomeInsumo = nomeInsumo;
    }

    public BigDecimal getQuantidadeAtualKg() {
        return quantidadeAtualKg;
    }
    public void setQuantidadeAtualKg(BigDecimal quantidadeAtualKg) {
        this.quantidadeAtualKg = quantidadeAtualKg;
    }

    public BigDecimal getQuantidadeMinimaAlerta() {
        return quantidadeMinimaAlerta;
    }
    public void setQuantidadeMinimaAlerta(BigDecimal quantidadeMinimaAlerta) {
        this.quantidadeMinimaAlerta = quantidadeMinimaAlerta;
    }

    public BigDecimal getPrecoCustoPorKg() {
        return precoCustoPorKg;
    }
    public void setPrecoCustoPorKg(BigDecimal precoCustoPorKg) {
        this.precoCustoPorKg = precoCustoPorKg;
    }
}
