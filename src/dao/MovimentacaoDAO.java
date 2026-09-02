package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import modelo.Movimentacao;

public class MovimentacaoDAO {

    private Connection connection;

    public MovimentacaoDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void cadastrar(Movimentacao movimentacao) throws SQLException {
        String sql = "INSERT INTO movimentacoes (data_movimentacao, categoria, descricao, id_insumo_fk, valor) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, new Date(movimentacao.getDataMovimentacao().getTime()));
            stmt.setString(2, movimentacao.getCategoria());
            stmt.setString(3, movimentacao.getDescricao());

            // Trata a chave estrangeira nula caso idInsumo não seja obrigatório
            if (movimentacao.getIdInsumo() > 0) {
                stmt.setInt(4, movimentacao.getIdInsumo());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.setBigDecimal(5, movimentacao.getValor());
            stmt.executeUpdate();
        }
    }

    // READ (Buscar por ID)
    public Movimentacao buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM movimentacoes WHERE id_movimentacao = ?";
        Movimentacao movimentacao = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    movimentacao = new Movimentacao(
                        rs.getInt("id_movimentacao"),
                        rs.getDate("data_movimentacao"),
                        rs.getBigDecimal("valor"),
                        rs.getString("categoria"),
                        rs.getString("descricao"),
                        rs.getInt("id_insumo_fk")
                    );
                }
            }
        }
        return movimentacao;
    }

    // READ (Listar todos)
    public List<Movimentacao> listarTodos() throws SQLException {
        List<Movimentacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimentacoes";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Movimentacao movimentacao = new Movimentacao(
                    rs.getInt("id_movimentacao"),
                    rs.getDate("data_movimentacao"),
                    rs.getBigDecimal("valor"),
                    rs.getString("categoria"),
                    rs.getString("descricao"),
                    rs.getInt("id_insumo_fk")
                );
                lista.add(movimentacao);
            }
        }
        return lista;
    }

    // UPDATE
    public void atualizar(Movimentacao movimentacao) throws SQLException {
        String sql = "UPDATE movimentacoes SET data_movimentacao = ?, categoria = ?, descricao = ?, id_insumo_fk = ?, valor = ? WHERE id_movimentacao = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, new Date(movimentacao.getDataMovimentacao().getTime()));
            stmt.setString(2, movimentacao.getCategoria());
            stmt.setString(3, movimentacao.getDescricao());

            if (movimentacao.getIdInsumo() > 0) {
                stmt.setInt(4, movimentacao.getIdInsumo());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.setBigDecimal(5, movimentacao.getValor());
            stmt.setInt(6, movimentacao.getIdMovimentacao());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM movimentacoes WHERE id_movimentacao = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}