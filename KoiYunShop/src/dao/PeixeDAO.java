package dao;

import modelo.Peixe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import factory.ConexaoDB;

public class PeixeDAO {

    private Connection conexao;
    
    public PeixeDAO() throws SQLException {
        this.conexao = ConexaoDB.getConexao();
    }

    // 1. INSERIR (Create)
    public void salvar(Peixe peixe) throws SQLException {
        String sql = "INSERT INTO peixes (codigo_verificador, variedade, data_entrada, tamanho_cm, preco_venda, status, id_lago_fk) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, peixe.getCodigoIdentificador());
            stmt.setString(2, peixe.getVariedade());
            stmt.setDate(3, peixe.getDataEntrada() != null ? new Date(peixe.getDataEntrada().getTime()) : null);
            stmt.setBigDecimal(4, peixe.getTamanhoCm());
            stmt.setBigDecimal(5, peixe.getPrecoVenda());
            stmt.setString(6, peixe.getStatus());
            stmt.setInt(7, peixe.getIdLago());

            stmt.executeUpdate();
        }
    }

    // 2. ATUALIZAR (Update)
    public void atualizar(Peixe peixe) throws SQLException {
        String sql = "UPDATE peixes SET codigo_verificador = ?, variedade = ?, data_entrada = ?, tamanho_cm = ?, preco_venda = ?, status = ?, id_lago_fk = ? WHERE id_peixe = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, peixe.getCodigoIdentificador());
            stmt.setString(2, peixe.getVariedade());
            stmt.setDate(3, peixe.getDataEntrada() != null ? new Date(peixe.getDataEntrada().getTime()) : null);
            stmt.setBigDecimal(4, peixe.getTamanhoCm());
            stmt.setBigDecimal(5, peixe.getPrecoVenda());
            stmt.setString(6, peixe.getStatus());
            stmt.setInt(7, peixe.getIdLago());
            stmt.setInt(8, peixe.getIdPeixe());

            stmt.executeUpdate();
        }
    }

    // 3. DELETAR (Delete)
    public void deletar(int idPeixe) throws SQLException {
        String sql = "DELETE FROM peixes WHERE id_peixe = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idPeixe);
            stmt.executeUpdate();
        }
    }

    // 4. LISTAR TODOS (ListarTodos)
    public List<Peixe> listarTodos() throws SQLException {
        String sql = "SELECT * FROM peixes";
        List<Peixe> lista = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearPeixe(rs));
            }
        }
        return lista;
    }

    // 5. BUSCAR POR ID (BuscarPorId)
    public Peixe buscarPorId(int idPeixe) throws SQLException {
        String sql = "SELECT * FROM peixes WHERE id_peixe = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idPeixe);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearPeixe(rs);
                }
            }
        }
        return null;
    }

    public List<Peixe> buscarPorVariedade(String variedade) throws SQLException {
        String sql = "SELECT * FROM peixes WHERE variedade LIKE ?";
        List<Peixe> lista = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            // Os '%' permitem buscar substrings, ex: "koha" encontra "Kohaku"
            stmt.setString(1, "%" + variedade + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearPeixe(rs));
                }
            }
        }
        return lista;
    }

    // 6. BUSCAR PEIXES POR LAGO (BuscarPorLago)
    public List<Peixe> buscarPorLago(int idLagoFk) throws SQLException {
        String sql = "SELECT * FROM peixes WHERE id_lago_fk = ?";
        List<Peixe> lista = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idLagoFk);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearPeixe(rs));
                }
            }
        }
        return lista;
    }

    // Mapeamento auxiliar do ResultSet para a model Peixe
    private Peixe mapearPeixe(ResultSet rs) throws SQLException {
        Peixe p = new Peixe();
        p.setIdPeixe(rs.getInt("id_peixe"));
        p.setCodigoIdentificador(rs.getInt("codigo_verificador"));
        p.setVariedade(rs.getString("variedade"));
        p.setDataEntrada(rs.getDate("data_entrada")); // Atribuição direta para java.util.Date
        p.setTamanhoCm(rs.getBigDecimal("tamanho_cm"));
        p.setPrecoVenda(rs.getBigDecimal("preco_venda"));
        p.setStatus(rs.getString("status"));
        p.setIdLago(rs.getInt("id_lago_fk"));
        return p;
    }
}