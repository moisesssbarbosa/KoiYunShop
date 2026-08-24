import java.math.BigDecimal;

public class ItemVenda {
    private int idItemVenda;
    private BigDecimal precoPago;
    private int idVenda;
    private int idPeixe;

    public ItemVenda() {}

    public ItemVenda(int idItemVenda, BigDecimal precoPago, int idVenda, int idPeixe) {
        this.idItemVenda = idItemVenda;
        this.precoPago = precoPago;
        this.idVenda = idVenda;
        this.idPeixe = idPeixe;
    }


    public int getIdItemVenda() {
        return idItemVenda;
    }
    public void setIdItemVenda(int idItemVenda) {
        this.idItemVenda = idItemVenda;
    }

    public BigDecimal getPrecoPago() {
        return precoPago;
    }
    public void setPrecoPago(BigDecimal precoPago) {
        this.precoPago = precoPago;
    }

    public int getIdVenda() {
        return idVenda;
    }
    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public int getIdPeixe() {
        return idPeixe;
    }
    public void setIdPeixe(int idPeixe) {
        this.idPeixe = idPeixe;
    }
}
