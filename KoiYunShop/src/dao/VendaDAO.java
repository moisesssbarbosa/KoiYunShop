package dao;

import modelo.Venda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import factory.ConexaoDB;

public class VendaDAO {

    private Connection conexao;

    public VendaDAO() throws SQLException {
        this.conexao = ConexaoDB.getConexao();
    }

    // 1. INSERIR (Retorna o id_venda gerado para usar no VendaItemDAO)
    public int salvar(Venda venda) throws SQLException {
        String sql = "INSERT INTO vendas (data_venda, valor_total, forma_pagamento, status_entrega, id_cliente_fk) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, new java.sql.Date(venda.getDataVenda().getTime())); // Converte LocalDate para java.sql.Date
            stmt.setBigDecimal(2, venda.getValorTotal());
            stmt.setString(3, venda.getFormaPagamento());
            stmt.setString(4, venda.getStatusEntrega());
            stmt.setInt(5, venda.getIdCliente());

            stmt.executeUpdate();

            // Pega o ID auto-incrementado gerado pelo banco
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    venda.setIdVenda(idGerado);
                    return idGerado;
                }
            }
        } 
        return 0;
    }

    // 2. ATUALIZAR (Update)
    public void atualizar(Venda venda) throws SQLException {
        String sql = "UPDATE vendas SET data_venda = ?, valor_total = ?, forma_pagamento = ?, status_entrega = ?, id_cliente_fk = ? WHERE id_venda = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(venda.getDataVenda().getTime()));
            stmt.setBigDecimal(2, venda.getValorTotal());
            stmt.setString(3, venda.getFormaPagamento());
            stmt.setString(4, venda.getStatusEntrega());
            stmt.setInt(5, venda.getIdCliente());
            stmt.setInt(6, venda.getIdVenda());

            stmt.executeUpdate();
        } 
    }

    // 3. DELETAR (Delete)
    public void deletar(int idVenda) throws SQLException {
        String sql = "DELETE FROM vendas WHERE id_venda = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idVenda);
            stmt.executeUpdate();
        } 
    }

    // 4. LISTAR TODOS (ListarTodos)
    public List<Venda> listarTodos() throws SQLException {
        String sql = "SELECT * FROM vendas ORDER BY data_venda DESC";
        List<Venda> lista = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearVenda(rs));
            }
        } 
        return lista;
    }

    // 5. BUSCAR POR ID (BuscarVendaPorId)
    public Venda buscarPorId(int idVenda) throws SQLException {
        String sql = "SELECT * FROM vendas WHERE id_venda = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idVenda);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearVenda(rs);
                }
            }
        } 
        return null;
    }

    // 6. BUSCAR VENDAS POR CLIENTE (BuscarVendasPorCliente)
    public List<Venda> buscarPorCliente(int idClienteFk) throws SQLException {
        String sql = "SELECT * FROM vendas WHERE id_cliente_fk = ? ORDER BY data_venda DESC";
        List<Venda> lista = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idClienteFk);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearVenda(rs));
                }
            }
        } 
        return lista;
    }

    // Mapeamento auxiliar do ResultSet para o modelo Venda
    private Venda mapearVenda(ResultSet rs) throws SQLException {
        Venda v = new Venda();
        v.setIdVenda(rs.getInt("id_venda"));
        
        // Trata a conversão de java.sql.Date para java.time.LocalDate
        Date dataSql = rs.getDate("data_venda");
        if (dataSql != null) {
            v.setDataVenda(dataSql);
        }
        
        v.setValorTotal(rs.getBigDecimal("valor_total"));
        v.setFormaPagamento(rs.getString("forma_pagamento"));
        v.setStatusEntrega(rs.getString("status_entrega"));
        v.setIdCliente(rs.getInt("id_cliente_fk"));
        return v;
    }
}