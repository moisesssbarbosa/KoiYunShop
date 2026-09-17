package dao;

import modelo.Lago;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.ConexaoDB;

public class LagoDAO {

    private Connection conexao;

    public LagoDAO() throws SQLException {
        this.conexao = ConexaoDB.getConexao();
    }

    // 1. INSERIR (Create)
    public void salvar(Lago lago) throws SQLException {
        String sql = "INSERT INTO lagos (nome_lago, capacidade_litros, tipo, status_agua, temperatura) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, lago.getNomeLago());
            stmt.setBigDecimal(2, lago.getCapacidadeLitros());
            stmt.setString(3, lago.getTipo());
            stmt.setString(4, lago.getStatusAgua());
            stmt.setBigDecimal(5, lago.getTemperatura());

            stmt.executeUpdate();
        }
    }

    // 2. ATUALIZAR (Update)
    public void atualizar(Lago lago) throws SQLException {
        String sql = "UPDATE lagos SET nome_lago = ?, capacidade_litros = ?, tipo = ?, status_agua = ?, temperatura = ? WHERE id_lago = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, lago.getNomeLago());
            stmt.setBigDecimal(2, lago.getCapacidadeLitros());
            stmt.setString(3, lago.getTipo());
            stmt.setString(4, lago.getStatusAgua());
            stmt.setBigDecimal(5, lago.getTemperatura());
            stmt.setInt(6, lago.getIdLago());

            stmt.executeUpdate();
        }
    }

    // 3. DELETAR (Delete)
    public void deletar(int idLago) throws SQLException {
        String sql = "DELETE FROM lagos WHERE id_lago = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idLago);
            stmt.executeUpdate();
        }
    }

    // 4. LISTAR TODOS (Read - ListarTodos)
    public List<Lago> listarTodos() throws SQLException {
        String sql = "SELECT * FROM lagos";
        List<Lago> lista = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearLago(rs));
            }
        }
        return lista;
    }

    // 5. BUSCAR POR ID (Read - BuscarPorId)
    public Lago buscarPorId(int idLago) throws SQLException {
        String sql = "SELECT * FROM lagos WHERE id_lago = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idLago);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearLago(rs);
                }
            }
        }
        return null;
    }

    // 6. BUSCAR POR NOME (Read - BuscarPorNome)
    public List<Lago> buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM lagos WHERE nome_lago LIKE ?";
        List<Lago> lista = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearLago(rs));
                }
            }
        }
        return lista;
    }

    // Mapeamento auxiliar do ResultSet para o modelo Lago
    private Lago mapearLago(ResultSet rs) throws SQLException {
        Lago lago = new Lago();
        lago.setIdLago(rs.getInt("id_lago"));
        lago.setNomeLago(rs.getString("nome_lago"));
        lago.setCapacidadeLitros(rs.getBigDecimal("capacidade_litros"));
        lago.setTipo(rs.getString("tipo"));
        lago.setStatusAgua(rs.getString("status_agua"));
        lago.setTemperatura(rs.getBigDecimal("temperatura"));
        return lago;
    }
}