package dao;

import modelo.ItemVenda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.ConexaoDB;

public class ItensVendaDAO {

    private Connection conexao;

    public ItensVendaDAO() throws SQLException {
        this.conexao = ConexaoDB.getConexao();
    }

    // 1. INSERIR (Create) usando o código identificador para buscar o ID do peixe
    public void salvar(ItemVenda item, int codigoIdentificadorPeixe) throws SQLException {
        // O sub-select (SELECT id_peixe FROM peixes WHERE codigo_identificador = ?) busca a FK automaticamente
        String sql = "INSERT INTO itens_venda (preco, id_venda_fk, id_peixe_fk) " +
                     "VALUES (?, ?, (SELECT id_peixe FROM peixes WHERE codigo_identificador = ?))";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setBigDecimal(1, item.getPrecoPago());
            stmt.setInt(2, item.getIdVenda());
            stmt.setInt(3, codigoIdentificadorPeixe); // Passa o código exibido na tela

            stmt.executeUpdate();
        }
    }

    // 2. ATUALIZAR (Update) usando o código identificador
    public void atualizar(ItemVenda item, int codigoIdentificadorPeixe) throws SQLException {
        String sql = "UPDATE itens_venda SET preco = ?, id_venda_fk = ?, " +
                     "id_peixe_fk = (SELECT id_peixe FROM peixes WHERE codigo_identificador = ?) " +
                     "WHERE id_item_venda = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setBigDecimal(1, item.getPrecoPago());
            stmt.setInt(2, item.getIdVenda());
            stmt.setInt(3, codigoIdentificadorPeixe); // Passa o código exibido na tela
            stmt.setInt(4, item.getIdItemVenda());

            stmt.executeUpdate();
        }
    }

    // 3. DELETAR (Delete)
    public void deletar(int idItemVenda) throws SQLException {
        String sql = "DELETE FROM itens_venda WHERE id_item_venda = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idItemVenda);
            stmt.executeUpdate();
        }
    }

    // 4. LISTAR TODOS OS ITENS DE UMA VENDA ESPECÍFICA (BuscarPorVenda)
    public List<ItemVenda> buscarPorVenda(int idVendaFk) throws SQLException {
        String sql = "SELECT * FROM itens_venda WHERE id_venda_fk = ?";
        List<ItemVenda> lista = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idVendaFk);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearItemVenda(rs));
                }
            }
        }
        return lista;
    }

    // 5. BUSCAR POR ID (BuscarPorId)
    public ItemVenda buscarPorId(int idItemVenda) throws SQLException {
        String sql = "SELECT * FROM itens_venda WHERE id_item_venda = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idItemVenda);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearItemVenda(rs);
                }
            }
        }
        return null;
    }

    // 6. LISTAR TODOS (ListarTodos)
    public List<ItemVenda> listarTodos() throws SQLException {
        String sql = "SELECT * FROM itens_venda";
        List<ItemVenda> lista = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearItemVenda(rs));
            }
        }
        return lista;
    }

    // Mapeamento auxiliar do ResultSet para a model ItemVenda
    private ItemVenda mapearItemVenda(ResultSet rs) throws SQLException {
        ItemVenda item = new ItemVenda();
        item.setIdItemVenda(rs.getInt("id_item_venda"));
        item.setPrecoPago(rs.getBigDecimal("preco")); // pega a coluna 'preco' da tabela
        item.setIdVenda(rs.getInt("id_venda_fk"));
        item.setIdPeixe(rs.getInt("id_peixe_fk"));
        return item;
    }
}