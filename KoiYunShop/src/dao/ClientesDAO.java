package dao;

import modelo.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import factory.ConexaoDB;

public class ClientesDAO {
    private Connection conexao;

    // Construtor que recebe a conexão por parâmetro
    public ClientesDAO() throws SQLException {
        this.conexao = ConexaoDB.getConexao();
    }

    // 1. INSERIR (Create)
    public void inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nome, cpf_cnpj, telefone, email, cidade_estado) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpfCnpj());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getCidadeEstado());

            stmt.executeUpdate();
        }
    }

    // 2. ATUALIZAR (Update)
    public void atualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET nome = ?, cpf_cnpj = ?, telefone = ?, email = ?, cidade_estado = ? WHERE id_cliente = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpfCnpj());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getCidadeEstado());
            stmt.setInt(6, cliente.getIdCliente());

            stmt.executeUpdate();
        } 
    }

    // 3. DELETAR (Delete)
    public void deletar(int idCliente) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.executeUpdate();
        }
    }

    // 4. LISTAR TODOS (Read - ListarTodos)
    public List<Cliente> listarTodos() throws SQLException {
        String sql = "SELECT * FROM clientes";
        List<Cliente> lista = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }
        }
        return lista;
    }

    // 5. BUSCAR POR ID (Read - BuscarClientePorId)
    public Cliente buscarClientePorId(int idCliente) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        }
        return null; // Retorna null caso não encontre
    }

    // 6. BUSCAR POR NOME (Read - BuscarClientePorNome)
    public List<Cliente> buscarClientePorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE nome LIKE ?";
        List<Cliente> lista = new ArrayList<>();

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%"); // Permite busca parcial ex: "Moi" acha "Moisés"

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearCliente(rs));
                }
            }
        } 
        return lista;
    }

    // Método auxiliar para evitar repetição de código ao ler o ResultSet
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setIdCliente(rs.getInt("id_cliente"));
        c.setNome(rs.getString("nome"));
        c.setCpfCnpj(rs.getString("cpf_cnpj"));
        c.setTelefone(rs.getString("telefone"));
        c.setEmail(rs.getString("email"));
        c.setCidadeEstado(rs.getString("cidade_estado"));
        return c;
    }
}