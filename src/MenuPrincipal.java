import dao.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import modelo.*;

public class MenuPrincipal {

    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        // Configuração de conexão com o banco de dados (Ajuste os dados conforme seu ambiente)
        String url = "jdbc:mysql://localhost:3306/seu_banco_de_dados";
        String usuario = "root";
        String senha = "password";

        try (Connection conexao = DriverManager.getConnection(url, usuario, senha)) {
            System.out.println("Conexão estabelecida com sucesso!");

            // Instancia os DAOs
            ClientesDAO clientesDAO = new ClientesDAO(conexao);
            InsumoDAO insumoDAO = new InsumoDAO(conexao);
            LagoDAO lagoDAO = new LagoDAO(conexao);
            MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO(conexao);
            PeixeDAO peixeDAO = new PeixeDAO(conexao);
            VendaDAO vendaDAO = new VendaDAO(conexao);
            ItensVendaDAO itensVendaDAO = new ItensVendaDAO(conexao);

            boolean rodando = true;

            while (rodando) {
                exibirMenuPrincipal();
                int opcao = lerOpcao();

                switch (opcao) {
                    case 1:
                        menuClientes(clientesDAO);
                        break;
                    case 2:
                        menuInsumos(insumoDAO);
                        break;
                    case 3:
                        menuLagos(lagoDAO);
                        break;
                    case 4:
                        menuMovimentacoes(movimentacaoDAO);
                        break;
                    case 5:
                        menuPeixes(peixeDAO);
                        break;
                    case 6:
                        menuVendas(vendaDAO);
                        break;
                    case 7:
                        menuItensVenda(itensVendaDAO);
                        break;
                    case 0:
                        rodando = false;
                        System.out.println("Saindo do sistema... Até logo!");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n==================================");
        System.out.println("      SISTEMA DE GERENCIAMENTO     ");
        System.out.println("==================================");
        System.out.println("1. Gerenciar Clientes");
        System.out.println("2. Gerenciar Insumos");
        System.out.println("3. Gerenciar Lagos");
        System.out.println("4. Gerenciar Movimentações");
        System.out.println("5. Gerenciar Peixes");
        System.out.println("6. Gerenciar Vendas");
        System.out.println("7. Gerenciar Itens de Venda");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // =========================================================================
    // 1. SUBMENU CLIENTES (ClientesDAO)
    // =========================================================================
    private static void menuClientes(ClientesDAO dao) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--- GERENCIAR CLIENTES ---");
            System.out.println("1. Inserir Cliente");
            System.out.println("2. Atualizar Cliente");
            System.out.println("3. Deletar Cliente");
            System.out.println("4. Listar Todos");
            System.out.println("5. Buscar por ID");
            System.out.println("6. Buscar por Nome");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();
            try {
                switch (opcao) {
                    case 1:
                        Cliente novo = new Cliente();
                        System.out.print("Nome: "); novo.setNome(scanner.nextLine());
                        System.out.print("CPF/CNPJ: "); novo.setCpfCnpj(scanner.nextLine());
                        System.out.print("Telefone: "); novo.setTelefone(scanner.nextLine());
                        System.out.print("Email: "); novo.setEmail(scanner.nextLine());
                        System.out.print("Cidade/Estado: "); novo.setCidadeEstado(scanner.nextLine());
                        dao.inserir(novo);
                        System.out.println("Cliente cadastrado!");
                        break;
                    case 2:
                        Cliente alt = new Cliente();
                        System.out.print("ID do Cliente a atualizar: "); alt.setIdCliente(lerOpcao());
                        System.out.print("Novo Nome: "); alt.setNome(scanner.nextLine());
                        System.out.print("Novo CPF/CNPJ: "); alt.setCpfCnpj(scanner.nextLine());
                        System.out.print("Novo Telefone: "); alt.setTelefone(scanner.nextLine());
                        System.out.print("Novo Email: "); alt.setEmail(scanner.nextLine());
                        System.out.print("Nova Cidade/Estado: "); alt.setCidadeEstado(scanner.nextLine());
                        dao.atualizar(alt);
                        System.out.println("Cliente atualizado!");
                        break;
                    case 3:
                        System.out.print("ID do Cliente a deletar: ");
                        dao.deletar(lerOpcao());
                        System.out.println("Cliente deletado!");
                        break;
                    case 4:
                        List<Cliente> clientes = dao.listarTodos();
                        clientes.forEach(c -> System.out.println(c.getIdCliente() + " - " + c.getNome() + " | " + c.getEmail()));
                        break;
                    case 5:
                        System.out.print("ID do Cliente: ");
                        Cliente c = dao.buscarClientePorId(lerOpcao());
                        System.out.println(c != null ? c.getIdCliente() + " - " + c.getNome() : "Não encontrado.");
                        break;
                    case 6:
                        System.out.print("Nome do Cliente: ");
                        List<Cliente> buscaNome = dao.buscarClientePorNome(scanner.nextLine());
                        buscaNome.forEach(cli -> System.out.println(cli.getIdCliente() + " - " + cli.getNome()));
                        break;
                    case 0:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro no banco: " + e.getMessage());
            }
        }
    }

    // =========================================================================
    // 2. SUBMENU INSUMOS (InsumoDAO)
    // =========================================================================
    private static void menuInsumos(InsumoDAO dao) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--- GERENCIAR INSUMOS ---");
            System.out.println("1. Cadastrar Insumo");
            System.out.println("2. Atualizar Insumo");
            System.out.println("3. Deletar Insumo");
            System.out.println("4. Listar Todos");
            System.out.println("5. Buscar por ID");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();
            try {
                switch (opcao) {
                    case 1:
                        Insumo ins = lerDadosInsumo(0);
                        dao.cadastrar(ins);
                        System.out.println("Insumo cadastrado!");
                        break;
                    case 2:
                        System.out.print("ID do Insumo a atualizar: ");
                        int id = lerOpcao();
                        Insumo insAlt = lerDadosInsumo(id);
                        dao.atualizar(insAlt);
                        System.out.println("Insumo atualizado!");
                        break;
                    case 3:
                        System.out.print("ID do Insumo a deletar: ");
                        dao.deletar(lerOpcao());
                        System.out.println("Insumo deletado!");
                        break;
                    case 4:
                        List<Insumo> insumos = dao.listarTodos();
                        insumos.forEach(i -> System.out.println(i.getIdInsumo() + " - " + i.getNomeInsumo() + " | Qtd: " + i.getQuantidadeAtualKg() + "kg"));
                        break;
                    case 5:
                        System.out.print("ID do Insumo: ");
                        Insumo i = dao.buscarPorId(lerOpcao());
                        System.out.println(i != null ? i.getIdInsumo() + " - " + i.getNomeInsumo() : "Não encontrado.");
                        break;
                    case 0:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro no banco: " + e.getMessage());
            }
        }
    }

    private static Insumo lerDadosInsumo(int id) {
        System.out.print("Nome Insumo: "); String nome = scanner.nextLine();
        System.out.print("Qtd Atual (kg): "); BigDecimal qtd = new BigDecimal(scanner.nextLine());
        System.out.print("Qtd Mínima Alerta: "); BigDecimal min = new BigDecimal(scanner.nextLine());
        System.out.print("Preço Custo por kg: "); BigDecimal preco = new BigDecimal(scanner.nextLine());
        return new Insumo(id, nome, qtd, min, preco);
    }

    // =========================================================================
    // 3. SUBMENU LAGOS (LagoDAO)
    // =========================================================================
    private static void menuLagos(LagoDAO dao) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--- GERENCIAR LAGOS ---");
            System.out.println("1. Cadastrar Lago");
            System.out.println("2. Atualizar Lago");
            System.out.println("3. Deletar Lago");
            System.out.println("4. Listar Todos");
            System.out.println("5. Buscar por ID");
            System.out.println("6. Buscar por Nome");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();
            try {
                switch (opcao) {
                    case 1:
                        Lago lago = lerDadosLago(0);
                        dao.salvar(lago);
                        System.out.println("Lago salvo!");
                        break;
                    case 2:
                        System.out.print("ID do Lago a atualizar: ");
                        int id = lerOpcao();
                        Lago lagoAlt = lerDadosLago(id);
                        dao.atualizar(lagoAlt);
                        System.out.println("Lago atualizado!");
                        break;
                    case 3:
                        System.out.print("ID do Lago a deletar: ");
                        dao.deletar(lerOpcao());
                        System.out.println("Lago deletado!");
                        break;
                    case 4:
                        List<Lago> lagos = dao.listarTodos();
                        lagos.forEach(l -> System.out.println(l.getIdLago() + " - " + l.getNomeLago() + " | Temp: " + l.getTemperatura() + "°C"));
                        break;
                    case 5:
                        System.out.print("ID do Lago: ");
                        Lago l = dao.buscarPorId(lerOpcao());
                        System.out.println(l != null ? l.getIdLago() + " - " + l.getNomeLago() : "Não encontrado.");
                        break;
                    case 6:
                        System.out.print("Nome do Lago: ");
                        List<Lago> buscaNome = dao.buscarPorNome(scanner.nextLine());
                        buscaNome.forEach(lg -> System.out.println(lg.getIdLago() + " - " + lg.getNomeLago()));
                        break;
                    case 0:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro no banco: " + e.getMessage());
            }
        }
    }

    private static Lago lerDadosLago(int id) {
        Lago lago = new Lago();
        lago.setIdLago(id);
        System.out.print("Nome do Lago: "); lago.setNomeLago(scanner.nextLine());
        System.out.print("Capacidade (Litros): "); lago.setCapacidadeLitros(new BigDecimal(scanner.nextLine()));
        System.out.print("Tipo: "); lago.setTipo(scanner.nextLine());
        System.out.print("Status Água: "); lago.setStatusAgua(scanner.nextLine());
        System.out.print("Temperatura: "); lago.setTemperatura(new BigDecimal(scanner.nextLine()));
        return lago;
    }

    // =========================================================================
    // 4. SUBMENU MOVIMENTAÇÕES (MovimentacaoDAO)
    // =========================================================================
    private static void menuMovimentacoes(MovimentacaoDAO dao) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--- GERENCIAR MOVIMENTAÇÕES ---");
            System.out.println("1. Cadastrar Movimentação");
            System.out.println("2. Atualizar Movimentação");
            System.out.println("3. Deletar Movimentação");
            System.out.println("4. Listar Todas");
            System.out.println("5. Buscar por ID");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();
            try {
                switch (opcao) {
                    case 1:
                        Movimentacao mov = lerDadosMovimentacao(0);
                        dao.cadastrar(mov);
                        System.out.println("Movimentação cadastrada!");
                        break;
                    case 2:
                        System.out.print("ID da Movimentação a atualizar: ");
                        int id = lerOpcao();
                        Movimentacao movAlt = lerDadosMovimentacao(id);
                        dao.atualizar(movAlt);
                        System.out.println("Movimentação atualizada!");
                        break;
                    case 3:
                        System.out.print("ID da Movimentação a deletar: ");
                        dao.deletar(lerOpcao());
                        System.out.println("Movimentação deletada!");
                        break;
                    case 4:
                        List<Movimentacao> movimentacoes = dao.listarTodos();
                        movimentacoes.forEach(m -> System.out.println(m.getIdMovimentacao() + " - " + m.getCategoria() + " | Valor: R$ " + m.getValor()));
                        break;
                    case 5:
                        System.out.print("ID da Movimentação: ");
                        Movimentacao m = dao.buscarPorId(lerOpcao());
                        System.out.println(m != null ? m.getIdMovimentacao() + " - " + m.getCategoria() + " - R$ " + m.getValor() : "Não encontrada.");
                        break;
                    case 0:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (SQLException | ParseException e) {
                System.err.println("Erro na operação: " + e.getMessage());
            }
        }
    }

    private static Movimentacao lerDadosMovimentacao(int id) throws ParseException {
        System.out.print("Data (dd/MM/yyyy): "); Date data = dateFormat.parse(scanner.nextLine());
        System.out.print("Valor: "); BigDecimal valor = new BigDecimal(scanner.nextLine());
        System.out.print("Categoria: "); String cat = scanner.nextLine();
        System.out.print("Descrição: "); String desc = scanner.nextLine();
        System.out.print("ID Insumo FK (0 se nenhum): "); int idInsumo = lerOpcao();
        return new Movimentacao(id, data, valor, cat, desc, idInsumo);
    }

    // =========================================================================
    // 5. SUBMENU PEIXES (PeixeDAO)
    // =========================================================================
    private static void menuPeixes(PeixeDAO dao) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--- GERENCIAR PEIXES ---");
            System.out.println("1. Cadastrar Peixe");
            System.out.println("2. Atualizar Peixe");
            System.out.println("3. Deletar Peixe");
            System.out.println("4. Listar Todos");
            System.out.println("5. Buscar por ID");
            System.out.println("6. Buscar Peixes por Lago");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();
            try {
                switch (opcao) {
                    case 1:
                        Peixe p = lerDadosPeixe(0);
                        dao.salvar(p);
                        System.out.println("Peixe cadastrado!");
                        break;
                    case 2:
                        System.out.print("ID do Peixe a atualizar: ");
                        int id = lerOpcao();
                        Peixe pAlt = lerDadosPeixe(id);
                        dao.atualizar(pAlt);
                        System.out.println("Peixe atualizado!");
                        break;
                    case 3:
                        System.out.print("ID do Peixe a deletar: ");
                        dao.deletar(lerOpcao());
                        System.out.println("Peixe deletado!");
                        break;
                    case 4:
                        List<Peixe> peixes = dao.listarTodos();
                        peixes.forEach(px -> System.out.println(px.getIdPeixe() + " - " + px.getVariedade() + " | Status: " + px.getStatus()));
                        break;
                    case 5:
                        System.out.print("ID do Peixe: ");
                        Peixe px = dao.buscarPorId(lerOpcao());
                        System.out.println(px != null ? px.getIdPeixe() + " - " + px.getVariedade() : "Não encontrado.");
                        break;
                    case 6:
                        System.out.print("ID do Lago (FK): ");
                        List<Peixe> porLago = dao.buscarPorLago(lerOpcao());
                        porLago.forEach(pxL -> System.out.println(pxL.getIdPeixe() + " - " + pxL.getVariedade()));
                        break;
                    case 0:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (SQLException | ParseException e) {
                System.err.println("Erro na operação: " + e.getMessage());
            }
        }
    }

    private static Peixe lerDadosPeixe(int id) throws ParseException {
        Peixe p = new Peixe();
        p.setIdPeixe(id);
        System.out.print("Código Identificador: "); p.setCodigoIdentificador(lerOpcao());
        System.out.print("Variedade: "); p.setVariedade(scanner.nextLine());
        System.out.print("Data Entrada (dd/MM/yyyy): "); p.setDataEntrada(dateFormat.parse(scanner.nextLine()));
        System.out.print("Tamanho (cm): "); p.setTamanhoCm(new BigDecimal(scanner.nextLine()));
        System.out.print("Preço Venda: "); p.setPrecoVenda(new BigDecimal(scanner.nextLine()));
        System.out.print("Status: "); p.setStatus(scanner.nextLine());
        System.out.print("ID Lago FK: "); p.setIdLago(lerOpcao());
        return p;
    }

    // =========================================================================
    // 6. SUBMENU VENDAS (VendaDAO)
    // =========================================================================
    private static void menuVendas(VendaDAO dao) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--- GERENCIAR VENDAS ---");
            System.out.println("1. Cadastrar Venda");
            System.out.println("2. Atualizar Venda");
            System.out.println("3. Deletar Venda");
            System.out.println("4. Listar Todas");
            System.out.println("5. Buscar por ID");
            System.out.println("6. Buscar Vendas por Cliente");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();
            try {
                switch (opcao) {
                    case 1:
                        Venda v = lerDadosVenda(0);
                        int idGerado = dao.salvar(v);
                        System.out.println("Venda salva com sucesso! ID Gerado: " + idGerado);
                        break;
                    case 2:
                        System.out.print("ID da Venda a atualizar: ");
                        int id = lerOpcao();
                        Venda vAlt = lerDadosVenda(id);
                        dao.atualizar(vAlt);
                        System.out.println("Venda atualizada!");
                        break;
                    case 3:
                        System.out.print("ID da Venda a deletar: ");
                        dao.deletar(lerOpcao());
                        System.out.println("Venda deletada!");
                        break;
                    case 4:
                        List<Venda> vendas = dao.listarTodos();
                        vendas.forEach(vd -> System.out.println(vd.getIdVenda() + " - Valor Total: R$ " + vd.getValorTotal() + " | Pago em: " + vd.getFormaPagamento()));
                        break;
                    case 5:
                        System.out.print("ID da Venda: ");
                        Venda vd = dao.buscarPorId(lerOpcao());
                        System.out.println(vd != null ? vd.getIdVenda() + " - R$ " + vd.getValorTotal() : "Não encontrada.");
                        break;
                    case 6:
                        System.out.print("ID do Cliente (FK): ");
                        List<Venda> porCliente = dao.buscarPorCliente(lerOpcao());
                        porCliente.forEach(vCli -> System.out.println(vCli.getIdVenda() + " - R$ " + vCli.getValorTotal()));
                        break;
                    case 0:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (SQLException | ParseException e) {
                System.err.println("Erro na operação: " + e.getMessage());
            }
        }
    }

    private static Venda lerDadosVenda(int id) throws ParseException {
        Venda v = new Venda();
        v.setIdVenda(id);
        System.out.print("Data Venda (dd/MM/yyyy): "); v.setDataVenda(dateFormat.parse(scanner.nextLine()));
        System.out.print("Valor Total: "); v.setValorTotal(new BigDecimal(scanner.nextLine()));
        System.out.print("Forma Pagamento: "); v.setFormaPagamento(scanner.nextLine());
        System.out.print("Status Entrega: "); v.setStatusEntrega(scanner.nextLine());
        System.out.print("ID Cliente FK: "); v.setIdCliente(lerOpcao());
        return v;
    }

    // =========================================================================
    // 7. SUBMENU ITENS DE VENDA (ItensVendaDAO)
    // =========================================================================
    private static void menuItensVenda(ItensVendaDAO dao) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--- GERENCIAR ITENS DE VENDA ---");
            System.out.println("1. Cadastrar Item de Venda");
            System.out.println("2. Atualizar Item de Venda");
            System.out.println("3. Deletar Item de Venda");
            System.out.println("4. Listar Todos");
            System.out.println("5. Buscar por ID");
            System.out.println("6. Buscar Itens por Venda");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();
            try {
                switch (opcao) {
                    case 1:
                        ItemVenda item = lerDadosItemVenda(0);
                        dao.salvar(item);
                        System.out.println("Item de venda cadastrado!");
                        break;
                    case 2:
                        System.out.print("ID do Item a atualizar: ");
                        int id = lerOpcao();
                        ItemVenda itemAlt = lerDadosItemVenda(id);
                        dao.atualizar(itemAlt);
                        System.out.println("Item atualizado!");
                        break;
                    case 3:
                        System.out.print("ID do Item a deletar: ");
                        dao.deletar(lerOpcao());
                        System.out.println("Item deletado!");
                        break;
                    case 4:
                        List<ItemVenda> itens = dao.listarTodos();
                        itens.forEach(it -> System.out.println(it.getIdItemVenda() + " - Preço: R$ " + it.getPrecoPago() + " | Venda FK: " + it.getIdVenda()));
                        break;
                    case 5:
                        System.out.print("ID do Item: ");
                        ItemVenda it = dao.buscarPorId(lerOpcao());
                        System.out.println(it != null ? it.getIdItemVenda() + " - R$ " + it.getPrecoPago() : "Não encontrado.");
                        break;
                    case 6:
                        System.out.print("ID da Venda (FK): ");
                        List<ItemVenda> porVenda = dao.buscarPorVenda(lerOpcao());
                        porVenda.forEach(iv -> System.out.println(iv.getIdItemVenda() + " - Preço: R$ " + iv.getPrecoPago()));
                        break;
                    case 0:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (SQLException e) {
                System.err.println("Erro no banco: " + e.getMessage());
            }
        }
    }

    private static ItemVenda lerDadosItemVenda(int id) {
        ItemVenda item = new ItemVenda();
        item.setIdItemVenda(id);
        System.out.print("Preço Pago: "); item.setPrecoPago(new BigDecimal(scanner.nextLine()));
        System.out.print("ID Venda FK: "); item.setIdVenda(lerOpcao());
        System.out.print("ID Peixe FK: "); item.setIdPeixe(lerOpcao());
        return item;
    }

    // Função auxiliar para evitar inconsistência ao ler inteiros via Scanner
    private static int lerOpcao() {
        try {
            int val = Integer.parseInt(scanner.nextLine());
            return val;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}