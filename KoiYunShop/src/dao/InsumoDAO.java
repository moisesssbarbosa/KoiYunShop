import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InsumoDAO {

    private Connection connection;

    public InsumoDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void cadastrar(Insumo insumo) throws SQLException {
        String sql = "INSERT INTO insumos (nome_insumo, quantidade_atual_kg, quantidade_minima_alerta, preco_custo_por_kg) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, insumo.getNomeInsumo());
            stmt.setBigDecimal(2, insumo.getQuantidadeAtualKg());
            stmt.setBigDecimal(3, insumo.getQuantidadeMinimaAlerta());
            stmt.setBigDecimal(4, insumo.getPrecoCustoPorKg());
            stmt.executeUpdate();
        }
    }

    // READ (Buscar por ID)
    public Insumo buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM insumos WHERE id_insumo = ?";
        Insumo insumo = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    insumo = new Insumo(
                        rs.getInt("id_insumo"),
                        rs.getString("nome_insumo"),
                        rs.getBigDecimal("quantidade_atual_kg"),
                        rs.getBigDecimal("quantidade_minima_alerta"),
                        rs.getBigDecimal("preco_custo_por_kg")
                    );
                }
            }
        }
        return insumo;
    }

    // READ (Listar todos)
    public List<Insumo> listarTodos() throws SQLException {
        List<Insumo> lista = new ArrayList<>();
        String sql = "SELECT * FROM insumos";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Insumo insumo = new Insumo(
                    rs.getInt("id_insumo"),
                    rs.getString("nome_insumo"),
                    rs.getBigDecimal("quantidade_atual_kg"),
                    rs.getBigDecimal("quantidade_minima_alerta"),
                    rs.getBigDecimal("preco_custo_por_kg")
                );
                lista.add(insumo);
            }
        }
        return lista;
    }

    // UPDATE
    public void atualizar(Insumo insumo) throws SQLException {
        String sql = "UPDATE insumos SET nome_insumo = ?, quantidade_atual_kg = ?, quantidade_minima_alerta = ?, preco_custo_por_kg = ? WHERE id_insumo = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, insumo.getNomeInsumo());
            stmt.setBigDecimal(2, insumo.getQuantidadeAtualKg());
            stmt.setBigDecimal(3, insumo.getQuantidadeMinimaAlerta());
            stmt.setBigDecimal(4, insumo.getPrecoCustoPorKg());
            stmt.setInt(5, insumo.getIdInsumo());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM insumos WHERE id_insumo = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}