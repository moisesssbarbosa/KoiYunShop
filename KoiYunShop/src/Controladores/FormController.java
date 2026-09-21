package controladores;

import dao.*;
import modelo.*;
import janelas.componentes.*;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormController {

    public void salvarPeixe(FormPeixe form, JDialog modal) {
        // 1. Validação dos campos obrigatórios do formulário
        if (!form.isCamposValidos()) {
            JOptionPane.showMessageDialog(
                modal, 
                "Por favor, preencha todos os campos obrigatórios!", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            // 2. Tratamento e conversão de dados
            int codigoVerificador = Integer.parseInt(form.getCodigoVerificador().trim());
            String variedade = form.getVariedade();
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date dataEntrada = sdf.parse(form.getDataEntrada());

            BigDecimal tamanhoCm = new BigDecimal(form.getTamanho().replace(".", "").replace(",", "."));
            BigDecimal precoVenda = new BigDecimal(form.getPrecoVenda().replace(".", "").replace(",", "."));
            String status = form.getStatus();
            int idLago = Integer.parseInt(form.getIdLagoFk().trim());

            // 3. Instanciação da classe de modelo
            Peixe peixe = new Peixe();
            peixe.setCodigoIdentificador(codigoVerificador);
            peixe.setVariedade(variedade);
            peixe.setDataEntrada(dataEntrada);
            peixe.setTamanhoCm(tamanhoCm);
            peixe.setPrecoVenda(precoVenda);
            peixe.setStatus(status);
            peixe.setIdLago(idLago);

            // 4. Instanciação do DAO
            PeixeDAO dao = new PeixeDAO();

            // 5. Verificação para Salvar ou Atualizar
            if (form.isEdicao()) {
                peixe.setIdPeixe(form.getIdPeixeEmEdicao());
                dao.atualizar(peixe);
                JOptionPane.showMessageDialog(modal, "Peixe atualizado com sucesso!");
            } else {
                dao.salvar(peixe);
                JOptionPane.showMessageDialog(modal, "Peixe cadastrado com sucesso!");
            }

            // 6. Fechamento do modal
            modal.dispose();

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Data de entrada inválida! Utilize o formato dd/MM/yyyy.", 
                "Erro na Data", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Erro de formatação! Verifique se Código, Tamanho, Preço e ID do Lago contêm valores numéricos válidos.", 
                "Erro de Formatação", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Erro de banco de dados ao salvar peixe: " + e.getMessage(), 
                "Erro SQL", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Erro inesperado ao salvar peixe: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void salvarCliente(FormCliente form, JDialog modal) {
        // 1. Validação dos campos obrigatórios na tela
        if (!form.isCamposValidos()) {
            JOptionPane.showMessageDialog(
                modal, 
                "Por favor, preencha todos os campos obrigatórios!", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            // 2. Cria o modelo e popula com as informações vindas da tela
            Cliente cliente = new Cliente();
            
            cliente.setNome(form.getNome());
            cliente.setCpfCnpj(form.getCpfCnpj());
            cliente.setTelefone(form.getTelefone());
            cliente.setEmail(form.getEmail());
            cliente.setCidadeEstado(form.getCidadeEstado());

            // 3. Instancia o DAO de clientes
            ClientesDAO dao = new ClientesDAO();

            // 4. Checa se é edição ou inserção
            if (form.isEdicao()) {
                cliente.setIdCliente(form.getIdClienteEmEdicao());
                dao.atualizar(cliente);
                JOptionPane.showMessageDialog(modal, "Cliente atualizado com sucesso!");
            } else {
                dao.salvar(cliente); // Chama o método 'inserir' do seu ClientesDAO
                JOptionPane.showMessageDialog(modal, "Cliente cadastrado com sucesso!");
            }

            // 5. Fecha a janela modal após salvar
            modal.dispose();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Erro de banco de dados ao salvar o cliente: " + e.getMessage(), 
                "Erro SQL", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Erro inesperado ao salvar cliente: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void salvarInsumo(FormInsumo form, JDialog modal) {
        // 1. Validação dos campos obrigatórios na tela
        if (!form.isCamposValidos()) {
            JOptionPane.showMessageDialog(
                modal, 
                "Por favor, preencha todos os campos obrigatórios!", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            // 2. Extrai os dados do formulário e realiza as conversões numéricas para BigDecimal
            String nome = form.getNomeInsumo();
            BigDecimal qtdAtual = new BigDecimal(form.getQuantidadeAtualKg().replace(",", "."));
            BigDecimal qtdMinima = new BigDecimal(form.getQuantidadeMinimaAlerta().replace(",", "."));
            BigDecimal precoCusto = new BigDecimal(form.getPrecoCustoPorKg().replace(",", "."));

            InsumoDAO dao = new InsumoDAO();

            // 4. Verifica se é EDIÇÃO ou CADASTRAR
            if (form.isEdicao()) {
                Insumo insumo = new Insumo(
                    form.getIdInsumoEmEdicao(),
                    nome,
                    qtdAtual,
                    qtdMinima,
                    precoCusto
                );
                dao.atualizar(insumo);
                JOptionPane.showMessageDialog(modal, "Insumo atualizado com sucesso!");
            } else {
                Insumo insumo = new Insumo(
                    0, // ID zero ou ignorado no AUTO_INCREMENT
                    nome,
                    qtdAtual,
                    qtdMinima,
                    precoCusto
                );
                dao.salvar(insumo); // Chama 'cadastrar' conforme definido no seu DAO
                JOptionPane.showMessageDialog(modal, "Insumo cadastrado com sucesso!");
            }

            // 5. Fecha o modal
            modal.dispose();
           

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Erro de formatação nos valores numéricos (Quantidade ou Preço). Verifique os valores digitados.", 
                "Erro de Formatação", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Erro de banco de dados ao salvar o insumo: " + e.getMessage(), 
                "Erro SQL", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Erro inesperado ao salvar insumo: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void salvarItemVenda(FormItemVenda form, JDialog modal) {
        // 1. Validação dos campos obrigatórios
        if (!form.isCamposValidos()) {
            JOptionPane.showMessageDialog(
                modal, 
                "Por favor, preencha todos os campos obrigatórios!", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            // 2. Extrai e converte os dados da tela
            BigDecimal precoPago = new BigDecimal(form.getPreco().replace(",", "."));
            int idVenda = Integer.parseInt(form.getIdVendaFk());
            int idPeixe = Integer.parseInt(form.getIdPeixeFk());

            // 3. Popula o objeto de modelo
            ItemVenda item = new ItemVenda();
            item.setPrecoPago(precoPago);
            item.setIdVenda(idVenda);
            item.setIdPeixe(idPeixe);

            // 4. Instancia o DAO
            ItensVendaDAO dao = new ItensVendaDAO();

            // 5. Executa atualização ou inserção conforme o estado da tela
            if (form.isEdicao()) {
                item.setIdItemVenda(form.getIdItemVendaEmEdicao());
                dao.atualizar(item);
                JOptionPane.showMessageDialog(modal, "Item de venda atualizado com sucesso!");
            } else {
                dao.salvar(item); // Chama 'salvar' conforme definido no seu DAO
                JOptionPane.showMessageDialog(modal, "Item de venda cadastrado com sucesso!");
            }

            // 6. Fecha o modal
            modal.dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Erro de formatação! Verifique se os campos Preço, ID Venda e ID Peixe contêm números válidos.", 
                "Erro de Formatação", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Erro de banco de dados ao salvar item de venda: " + e.getMessage(), 
                "Erro SQL", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Erro inesperado ao salvar item de venda: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void salvarLago(FormLago form, JDialog modal) {
        // 1. Validação dos campos obrigatórios
        if (!form.isCamposValidos()) {
            JOptionPane.showMessageDialog(
                modal, 
                "Por favor, preencha todos os campos obrigatórios!", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            // 2. Extrai e converte os dados numéricos para BigDecimal
            BigDecimal capacidade = new BigDecimal(form.getCapacidadeLitros().replace(",", "."));
            BigDecimal temperatura = new BigDecimal(form.getTemperatura().replace(",", "."));

            // 3. Popula o modelo Lago
            Lago lago = new Lago();
            lago.setNomeLago(form.getNomeLago());
            lago.setCapacidadeLitros(capacidade);
            lago.setTipo(form.getTipo());
            lago.setStatusAgua(form.getStatusAgua());
            lago.setTemperatura(temperatura);

            // 4. Instancia o LagoDAO (utiliza ConexaoDB no construtor padrão)
            LagoDAO dao = new LagoDAO();

            // 5. Verifica se é edição ou inserção
            if (form.isEdicao()) {
                lago.setIdLago(form.getIdLagoEmEdicao());
                dao.atualizar(lago);
                JOptionPane.showMessageDialog(modal, "Lago atualizado com sucesso!");
            } else {
                dao.salvar(lago); // Chama 'salvar' do LagoDAO
                JOptionPane.showMessageDialog(modal, "Lago cadastrado com sucesso!");
            }

            // 6. Fecha o modal após o salvamento
            modal.dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Erro de formatação! Verifique se Capacidade e Temperatura contêm valores numéricos válidos.", 
                "Erro de Formatação", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Erro de banco de dados ao salvar o lago: " + e.getMessage(), 
                "Erro SQL", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Erro inesperado ao salvar lago: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void salvarMovimentacao(FormMovimentacao form, JDialog modal) {
        // 1. Validação dos campos obrigatórios da tela
        if (!form.isCamposValidos()) {
            JOptionPane.showMessageDialog(
                modal, 
                "Por favor, preencha todos os campos obrigatórios!", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            // 2. Tratamento e conversão dos dados
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false); // Validação estrita de data
            Date dataMovimentacao = sdf.parse(form.getDataMovimentacao());

            BigDecimal valor = new BigDecimal(form.getValor().replace(".", "").replace(",", "."));
            String categoria = form.getCategoria();
            String descricao = form.getDescricao();

            // ID do insumo opcional (0 se não preenchido)
            int idInsumo = 0;
            if (form.getIdInsumoFk() != null && !form.getIdInsumoFk().trim().isEmpty()) {
                idInsumo = Integer.parseInt(form.getIdInsumoFk().trim());
            }

            // 3. Instancia o DAO diretamente
            MovimentacaoDAO dao = new MovimentacaoDAO();

            // 4. Salvar ou Atualizar
            if (form.isEdicao()) {
                Movimentacao mov = new Movimentacao(
                    form.getIdMovimentacaoEmEdicao(),
                    dataMovimentacao,
                    valor,
                    categoria,
                    descricao,
                    idInsumo
                );
                dao.atualizar(mov);
                JOptionPane.showMessageDialog(modal, "Movimentação atualizada com sucesso!");
            } else {
                Movimentacao mov = new Movimentacao(
                    0, // ID gerado pelo AUTO_INCREMENT do banco
                    dataMovimentacao,
                    valor,
                    categoria,
                    descricao,
                    idInsumo
                );
                dao.salvar(mov);
                JOptionPane.showMessageDialog(modal, "Movimentação registrada com sucesso!");
            }

            // 5. Encerra o modal
            modal.dispose();

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Data inválida! Por favor, utilize o formato dd/MM/yyyy.", 
                "Erro na Data", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Erro de formatação nos valores numéricos (Valor ou ID Insumo). Verifique os campos digitados.", 
                "Erro de Formatação", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Erro de banco de dados ao salvar a movimentação: " + e.getMessage(), 
                "Erro SQL", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Erro inesperado ao salvar movimentação: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void salvarVenda(FormVenda form, JDialog modal) {
        // 1. Validação dos campos obrigatórios da tela
        if (!form.isCamposValidos()) {
            JOptionPane.showMessageDialog(
                modal, 
                "Por favor, preencha todos os campos obrigatórios!", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            // 2. Tratamento e conversão dos dados (Corrigido para o formato AAAA-MM-DD do FormVenda)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            java.util.Date dataUtil = sdf.parse(form.getDataVenda());
            // Se o seu VendaDAO usar java.sql.Date, você pode converter assim:
            java.sql.Date dataVenda = new java.sql.Date(dataUtil.getTime());

            // Corrigido: O FormVenda.getValorTotal() já converte vírgula para ponto.
            BigDecimal valorTotal = new BigDecimal(form.getValorTotal());
            
            String formaPagamento = form.getFormaPagamento();
            String statusEntrega = form.getStatusEntrega();
            int idCliente = Integer.parseInt(form.getIdClienteFk());

            // 3. Montagem do modelo Venda
            Venda venda = new Venda();
            venda.setDataVenda(dataVenda);
            venda.setValorTotal(valorTotal);
            venda.setFormaPagamento(formaPagamento);
            venda.setStatusEntrega(statusEntrega);
            venda.setIdCliente(idCliente);

            // 4. Instancia o DAO
            VendaDAO dao = new VendaDAO();

            // 5. Edição vs Novo Cadastro
            if (form.isEdicao()) {
                venda.setIdVenda(form.getIdVendaEmEdicao());
                dao.atualizar(venda);
                JOptionPane.showMessageDialog(modal, "Venda atualizada com sucesso!");
            } else {
                int idGerado = dao.salvar(venda);
                JOptionPane.showMessageDialog(modal, "Venda registrada com sucesso! (ID: " + idGerado + ")");
            }

            // 6. Encerra o modal
            modal.dispose();

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Data de venda inválida! Utilize o formato AAAA-MM-DD (Ex: 2024-12-31).", 
                "Erro na Data", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Erro nos valores numéricos (Valor Total ou ID do Cliente). Verifique os campos digitados.", 
                "Erro de Formatação", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Erro de banco de dados ao salvar a venda: " + e.getMessage(), 
                "Erro SQL", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                modal, 
                "Erro inesperado ao salvar venda: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}