// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Statement;
// import java.util.ArrayList;
// import java.util.List;

// public class InsumoDAO {

//     private Connection connection;

//     public InsumoDAO(Connection connection) {
//         this.connection = connection;
//     }

//     // Inserir um novo insumo
//     public void Inserir(Insumo insumo) throws SQLException {
//         String sql = "INSERT INTO insumos (nome_insumo, quantidade_atual_kg, quantidade_minima_alerta, preco_custo_por_kg) " + "VALUES (?, ?, ?, ?)";

//         try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//             stmt.setString(1, insumo.getNomeInsumo());
//             stmt.setBigDecimal(2, insumo.getQuantidadeAtualKg());
//             stmt.setBigDecimal(3, insumo.getQuantidadeMinimaAlerta());
//             stmt.setBigDecimal(4, insumo.getPrecoCustoPorKg());

//             stmt.executeUpdate();

//             try (ResultSet rs = stmt.getGeneratedKeys()) {
//                 if (rs.next()) {
//                     insumo.setIdInsumo(rs.getInt(1));
//                 }
//             }
//         }
//     }

//     // Buscar insumo por ID
//     public Insumo buscarPorId(Integer id) throws SQLException {
//         String sql = "SELECT * FROM insumos WHERE id_insumo = ?";
//         Insumo insumo = null;

//         try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//             stmt.setInt(1, id);

//             try (ResultSet rs = stmt.executeQuery()) {
//                 if (rs.next()) {
//                     insumo = mapearResultSet(rs);
//                 }
//             }
//         }
//         return insumo;
//     }

//     // Listar todos os insumos
//     public List<Insumo> listarTodos() throws SQLException {
//         String sql = "SELECT * FROM insumos";
//         List<Insumo> insumos = new ArrayList<>();

//         try (PreparedStatement stmt = connection.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {

//             while (rs.next()) {
//                 insumos.add(mapearResultSet(rs));
//             }
//         }
//         return insumos;
//     }

//     // Atualizar insumo
//     public void atualizar(Insumo insumo) throws SQLException {
//         String sql = "UPDATE insumos SET nome_insumo = ?, quantidade_atual_kg = ?, " + "quantidade_minima_alerta = ?, preco_custo_por_kg = ? WHERE id_insumo = ?";

//         try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//             stmt.setString(1, insumo.getNomeInsumo());
//             stmt.setBigDecimal(2, insumo.getQuantidadeAtualKg());
//             stmt.setBigDecimal(3, insumo.getQuantidadeMinimaAlerta());
//             stmt.setBigDecimal(4, insumo.getPrecoCustoPorKg());
//             stmt.setInt(5, insumo.getIdInsumo());

//             stmt.executeUpdate();
//         }
//     }

//     // Excluir insumo por ID
//     public void deletar(Integer id) throws SQLException {
//         String sql = "DELETE FROM insumos WHERE id_insumo = ?";

//         try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//             stmt.setInt(1, id);
//             stmt.executeUpdate();
//         }
//     }

//     // Método auxiliar para converter ResultSet em objeto Insumo
//     private Insumo mapearResultSet(ResultSet rs) throws SQLException {
//         Insumo insumo = new Insumo();
//         insumo.setIdInsumo(rs.getInt("id_insumo"));
//         insumo.setNomeInsumo(rs.getString("nome_insumo"));
//         insumo.setQuantidadeAtualKg(rs.getBigDecimal("quantidade_atual_kg"));
//         insumo.setQuantidadeMinimaAlerta(rs.getBigDecimal("quantidade_minima_alerta"));
//         insumo.setPrecoCustoPorKg(rs.getBigDecimal("preco_custo_por_kg"));
//         return insumo;
//     }
// }









package modelo.Insumo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InsumoDAO {

    private Connection conexao;

    // Construtor que recebe a conexão por parâmetro
    public InsumoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // CREATE - Inserir cliente
    public void inserir(Insumo insumo) throws SQLException {
        String sql = "INSERT INTO insumos (nome_insumo, quantidade_atual_kg, quantidade_minima_alerta, preco_custo_por_kg) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, insumo.getNomeInsumo());
            stmt.setBigDecimal(2, insumo.getQuantidadeAtualKg());
            stmt.setBigDecimal(3, insumo.getQuantidadeMinimaAlerta());
            stmt.setBigDecimal(4, insumo.getPrecoCustoPorKg());
            stmt.executeUpdate();
        }
    }

    // READ - Listar todos os clientes
    public List<Insumo> listarTodos() throws SQLException {
        List<Insumo> Insumos = new ArrayList<>();
        String sql = "SELECT * FROM insumos";
        try (PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Insumo i = new Insumo();
                i.setIdInsumo(rs.getInt("id_insumo"));
                i.setNomeInsumo(rs.getString("nome_insumo"));
                i.setQuantidadeAtualKg(rs.getBigDecimal("quantidade_atual_kg"));
                i.setQuantidadeMinimaAlerta(rs.getBigDecimal("quantidade_minima_alerta"));
                i.setPrecoCustoPorKg(rs.getBigDecimal("preco_custo_por_kg"));
            }
        }
        return clientes;
    }

    // UPDATE - Atualizar dados do cliente
    public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nome = ?, endereco = ? WHERE id_cliente = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEndereco());
            stmt.setInt(3, cliente.getIdCliente());
            stmt.executeUpdate();
        }
    }

    // DELETE - Remover cliente por ID
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
