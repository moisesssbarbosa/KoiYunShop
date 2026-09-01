package modelo;

public class Cliente {
    private int idCliente;
    private String nome;
    private String cpfCnpj; // Mapeado como String por conta da formatação/tamanho
    private String telefone;
    private String email;
    private String cidadeEstado;

    public Cliente() {}

    public Cliente(int idCliente, String nome, String cpfCnpj, String telefone, String email, String cidadeEstado) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.telefone = telefone;
        this.email = email;
        this.cidadeEstado = cidadeEstado;
    }

    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }
    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getCidadeEstado() {
        return cidadeEstado;
    }
    public void setCidadeEstado(String cidadeEstado) {
        this.cidadeEstado = cidadeEstado;
    }
}
