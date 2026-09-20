package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    // Configurações do seu banco de dados
    private static final String URL = "jdbc:postgresql://localhost:5432/koiyunshop";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "1234";

    // Método responsável por estabelecer e retornar a conexão
    public static Connection getConexao() throws SQLException {
        try {
            // Opcional para versões mais antigas do JDBC (carrega o driver do MySQL):
            // Class.forName("com.mysql.cj.jdbc.Driver"); 
            
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
            throw e; // Lança a exceção para ser tratada pela camada que chamou
        }
    }

    public static void main(String[] args) {
        try {
            // Instancia a classe de conexão e obtém o objeto Connection
            ConexaoDB conexaoDB = new ConexaoDB();
            java.sql.Connection conexao = ConexaoDB.getConexao(); // Ajuste o nome do método se na sua classe for diferente
    
            if (conexao != null && !conexao.isClosed()) {
                System.out.println("Conexão realizada com sucesso!");
                
                // Fecha a conexão após o teste
                conexao.close();
                System.out.println("Conexão fechada.");
            } else {
                System.out.println("Falha ao conectar: Conexão nula ou fechada.");
            }
    
        } catch (Exception e) {
            System.err.println("Erro ao conectar com o banco de dados:");
            e.printStackTrace(); // Exibe a pilha de erros completa no terminal
        }
        
    }
}