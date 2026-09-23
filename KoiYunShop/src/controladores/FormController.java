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

    // Método auxiliar para exibição padronizada de mensagens de aviso
    private void exibirAviso(JDialog modal, String mensagem) {
        JOptionPane.showMessageDialog(modal, mensagem, "Aviso de Validação", JOptionPane.WARNING_MESSAGE);
    }

    // =========================================================================
    // 1. PEIXE
    // =========================================================================
    public void salvarPeixe(FormPeixe form, JDialog modal) {
        if (!form.isCamposValidos()) {
            exibirAviso(modal, "Por favor, preencha todos os campos obrigatórios!");
            return;
        }

        try {
            int codigoVerificador = Integer.parseInt(form.getCodigoVerificador().trim());
            String variedade = form.getVariedade().trim();

            if (codigoVerificador <= 0) {
                exibirAviso(modal, "O código identificador deve ser um número maior que zero!");
                return;
            }

            if (variedade.matches(".*\\d.*")) {
                exibirAviso(modal, "A variedade do peixe não pode conter números!");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date dataEntrada = sdf.parse(form.getDataEntrada());

            // Validação: Data de entrada não pode ser posterior à data atual
            if (dataEntrada.after(new Date())) {
                exibirAviso(modal, "A data de entrada não pode exceder a data atual do cadastro!");
                return;
            }

            BigDecimal tamanhoCm = new BigDecimal(form.getTamanho().replace(",", "."));
            BigDecimal precoVenda = new BigDecimal(form.getPrecoVenda().replace(",", "."));

            // Validação: Tamanho e Preço devem ser > 0
            if (tamanhoCm.compareTo(BigDecimal.ZERO) <= 0) {
                exibirAviso(modal, "O tamanho do peixe deve ser maior que zero!");
                return;
            }

            if (precoVenda.compareTo(BigDecimal.ZERO) <= 0) {
                exibirAviso(modal, "O preço de venda do peixe deve ser maior que zero!");
                return;
            }

            String status = form.getStatus();
            int idLago = Integer.parseInt(form.getIdLagoFk().trim());

            Peixe peixe = new Peixe();
            peixe.setCodigoIdentificador(codigoVerificador);
            peixe.setVariedade(variedade);
            peixe.setDataEntrada(dataEntrada);
            peixe.setTamanhoCm(tamanhoCm);
            peixe.setPrecoVenda(precoVenda);
            peixe.setStatus(status);
            peixe.setIdLago(idLago);

            PeixeDAO dao = new PeixeDAO();

            // --- LÓGICA DE VALIDAÇÃO DE CÓDIGO CORRIGIDA ---
            if (form.isEdicao()) {
                int idAtual = form.getIdPeixeEmEdicao();
                peixe.setIdPeixe(idAtual);

                // MODO EDIÇÃO: Passa o ID atual para IGNOAR o próprio peixe no banco
                if (dao.existeCodigoIdentificador(codigoVerificador, idAtual)) {
                    JOptionPane.showMessageDialog(modal, "O código verificador desse peixe já existe em outro registro, insira outro.", "Código verificador já existente", JOptionPane.ERROR_MESSAGE);
                    return; // Interrompe para não fechar o modal
                }

                dao.atualizar(peixe);
                JOptionPane.showMessageDialog(modal, "Peixe atualizado com sucesso!");

            } else {
                // MODO NOVO CADASTRO: Verifica se o código já existe na tabela
                if (dao.existeCodigoIdentificador(codigoVerificador)) {
                    JOptionPane.showMessageDialog(modal, "O código verificador desse peixe já existe, insira outro.", "Código verificador já existente", JOptionPane.ERROR_MESSAGE);
                    return; // Interrompe para não fechar o modal
                }

                dao.salvar(peixe);
                JOptionPane.showMessageDialog(modal, "Peixe cadastrado com sucesso!");
            }

            // Fecha a janela apenas se salvou/atualizou com sucesso
            modal.dispose();

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(modal, "Data de entrada inválida! Utilize o formato dd/MM/yyyy.", "Erro na Data", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(modal, "Erro de formatação! Verifique se Código, Tamanho, Preço e ID do Lago contêm valores numéricos válidos.", "Erro de Formatação", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            String msgError = e.getMessage().toLowerCase();
            if (msgError.contains("duplicate") || msgError.contains("unique") || msgError.contains("key")) {
                JOptionPane.showMessageDialog(modal, "Já existe um peixe cadastrado com este Código Identificador!", "Código Duplicado", JOptionPane.ERROR_MESSAGE);
            } else if (msgError.contains("foreign key") || msgError.contains("foreign")) {
                JOptionPane.showMessageDialog(modal, "O Lago informado (ID) não existe no banco de dados!", "Lago Não Encontrado", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(modal, "Erro de banco de dados ao salvar peixe: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(modal, "Erro inesperado ao salvar peixe: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================================
    // 2. CLIENTE
    // =========================================================================
    public void salvarCliente(FormCliente form, JDialog modal) {
        if (!form.isCamposValidos()) {
            exibirAviso(modal, "Por favor, preencha todos os campos obrigatórios!");
            return;
        }

        try {
            String nome = form.getNome().trim();
            String cidadeEstado = form.getCidadeEstado().trim();
            String cpfCnpj = form.getCpfCnpj().trim();
            String telefone = form.getTelefone().trim();

            // Validação: Nome e Cidade não devem conter números
            if (nome.matches(".*\\d.*")) {
                exibirAviso(modal, "O nome do cliente não pode conter números!");
                return;
            }
            if (cidadeEstado.matches(".*\\d.*")) {
                exibirAviso(modal, "A cidade/estado não pode conter números!");
                return;
            }

            // Validação: CPF/CNPJ e Telefone não devem conter letras
            if (cpfCnpj.matches(".*[a-zA-Z].*")) {
                exibirAviso(modal, "O CPF/CNPJ não pode conter letras!");
                return;
            }
            if (telefone.matches(".*[a-zA-Z].*")) {
                exibirAviso(modal, "O telefone não pode conter letras!");
                return;
            }

            Cliente cliente = new Cliente();
            cliente.setNome(nome);
            cliente.setCpfCnpj(cpfCnpj);
            cliente.setTelefone(telefone);
            cliente.setEmail(form.getEmail().trim());
            cliente.setCidadeEstado(cidadeEstado);

            ClientesDAO dao = new ClientesDAO();

            if (form.isEdicao()) {
                int idAtual = form.getIdClienteEmEdicao();
                cliente.setIdCliente(idAtual);

                // EDIÇÃO: Ignora o próprio cliente no banco
                if (dao.existeCpf(cpfCnpj, idAtual)) {
                    JOptionPane.showMessageDialog(modal, "Já existe outro cliente cadastrado com este CPF!", "CPF Duplicado", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                dao.atualizar(cliente);
                JOptionPane.showMessageDialog(modal, "Cliente atualizado com sucesso!");

            } else {
                // NOVO CADASTRO: Verifica se o CPF já existe na tabela
                if (dao.existeCpf(cpfCnpj)) {
                    JOptionPane.showMessageDialog(modal, "Já existe um cliente cadastrado com este CPF!", "CPF Duplicado", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                dao.salvar(cliente);
                JOptionPane.showMessageDialog(modal, "Cliente cadastrado com sucesso!");
            }

            modal.dispose();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(modal, "Erro de banco de dados ao salvar o cliente: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(modal, "Erro inesperado ao salvar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================================
    // 3. INSUMO
    // =========================================================================
    public void salvarInsumo(FormInsumo form, JDialog modal) {
        if (!form.isCamposValidos()) {
            exibirAviso(modal, "Por favor, preencha todos os campos obrigatórios!");
            return;
        }

        try {
            String nome = form.getNomeInsumo().trim();

            // Validação: Nome do insumo não pode conter números
            if (nome.matches(".*\\d.*")) {
                exibirAviso(modal, "O nome do insumo não pode conter números!");
                return;
            }

            BigDecimal qtdAtual = new BigDecimal(form.getQuantidadeAtualKg().replace(",", "."));
            BigDecimal qtdMinima = new BigDecimal(form.getQuantidadeMinimaAlerta().replace(",", "."));
            BigDecimal precoCusto = new BigDecimal(form.getPrecoCustoPorKg().replace(",", "."));

            // Validação: Quantidades e Preço de Custo não podem ser <= 0
            if (qtdAtual.compareTo(BigDecimal.ZERO) <= 0) {
                exibirAviso(modal, "A quantidade atual deve ser maior que zero!");
                return;
            }
            if (qtdMinima.compareTo(BigDecimal.ZERO) <= 0) {
                exibirAviso(modal, "A quantidade mínima de alerta deve ser maior que zero!");
                return;
            }
            if (precoCusto.compareTo(BigDecimal.ZERO) <= 0) {
                exibirAviso(modal, "O preço de custo por Kg deve ser maior que zero!");
                return;
            }

            InsumoDAO dao = new InsumoDAO();

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
                    0,
                    nome,
                    qtdAtual,
                    qtdMinima,
                    precoCusto
                );
                dao.salvar(insumo);
                JOptionPane.showMessageDialog(modal, "Insumo cadastrado com sucesso!");
            }

            modal.dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(modal, "Erro de formatação nos valores numéricos (Quantidade ou Preço). Verifique os valores digitados.", "Erro de Formatação", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(modal, "Erro de banco de dados ao salvar o insumo: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(modal, "Erro inesperado ao salvar insumo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================================
    // 4. ITEM VENDA
    // =========================================================================
    public void salvarItemVenda(FormItemVenda form, JDialog modal) {
        if (!form.isCamposValidos()) {
            exibirAviso(modal, "Por favor, preencha todos os campos obrigatórios!");
            return;
        }

        try {
            BigDecimal precoPago = new BigDecimal(form.getPreco().replace(",", "."));
            int idVenda = Integer.parseInt(form.getIdVendaFk().trim());
            int idPeixe = Integer.parseInt(form.getIdPeixeFk().trim());

            // Validação: Preço pago deve ser > 0
            if (precoPago.compareTo(BigDecimal.ZERO) <= 0) {
                exibirAviso(modal, "O preço pago pelo item deve ser maior que zero!");
                return;
            }

            ItemVenda item = new ItemVenda();
            item.setPrecoPago(precoPago);
            item.setIdVenda(idVenda);
            item.setIdPeixe(idPeixe);

            ItensVendaDAO dao = new ItensVendaDAO();

            if (form.isEdicao()) {
                item.setIdItemVenda(form.getIdItemVendaEmEdicao());
                dao.atualizar(item, idPeixe);
                JOptionPane.showMessageDialog(modal, "Item de venda atualizado com sucesso!");
            } else {
                dao.salvar(item, idPeixe);
                JOptionPane.showMessageDialog(modal, "Item de venda cadastrado com sucesso!");
            }

            modal.dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(modal, "Erro de formatação! Verifique se os campos Preço, ID Venda e ID Peixe contêm números válidos.", "Erro de Formatação", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            String msgError = e.getMessage().toLowerCase();
            if (msgError.contains("foreign key") || msgError.contains("foreign") || msgError.contains("constraint")) {
                JOptionPane.showMessageDialog(modal, "Erro de Chave Estrangeira: A Venda ou o Peixe informado não existe no banco de dados!", "Erro de Vínculo", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(modal, "Erro de banco de dados ao salvar item de venda: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(modal, "Erro inesperado ao salvar item de venda: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================================
    // 5. LAGO
    // =========================================================================
    public void salvarLago(FormLago form, JDialog modal) {
        if (!form.isCamposValidos()) {
            exibirAviso(modal, "Por favor, preencha todos os campos obrigatórios!");
            return;
        }

        try {
            String tipo = form.getTipo().trim();
            String statusAgua = form.getStatusAgua().trim();

            // Validação: Tipo e Status d'água não devem conter números
            if (tipo.matches(".*\\d.*")) {
                exibirAviso(modal, "O tipo do lago não pode conter números!");
                return;
            }
            if (statusAgua.matches(".*\\d.*")) {
                exibirAviso(modal, "O status d'água não pode conter números!");
                return;
            }

            BigDecimal capacidade = new BigDecimal(form.getCapacidadeLitros().replace(",", "."));
            BigDecimal temperatura = new BigDecimal(form.getTemperatura().replace(",", "."));

            // Validação: Capacidade > 0 e Temperatura >= 0
            if (capacidade.compareTo(BigDecimal.ZERO) <= 0) {
                exibirAviso(modal, "A capacidade do lago deve ser maior que zero!");
                return;
            }
            if (temperatura.compareTo(BigDecimal.ZERO) < 0) {
                exibirAviso(modal, "A temperatura do lago não pode ser negativa!");
                return;
            }

            Lago lago = new Lago();
            lago.setNomeLago(form.getNomeLago().trim());
            lago.setCapacidadeLitros(capacidade);
            lago.setTipo(tipo);
            lago.setStatusAgua(statusAgua);
            lago.setTemperatura(temperatura);

            LagoDAO dao = new LagoDAO();

            if (form.isEdicao()) {
                lago.setIdLago(form.getIdLagoEmEdicao());
                dao.atualizar(lago);
                JOptionPane.showMessageDialog(modal, "Lago atualizado com sucesso!");
            } else {
                dao.salvar(lago);
                JOptionPane.showMessageDialog(modal, "Lago cadastrado com sucesso!");
            }

            modal.dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(modal, "Erro de formatação! Verifique se Capacidade e Temperatura contêm valores numéricos válidos.", "Erro de Formatação", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(modal, "Erro de banco de dados ao salvar o lago: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(modal, "Erro inesperado ao salvar lago: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================================
    // 6. MOVIMENTAÇÃO
    // =========================================================================
    public void salvarMovimentacao(FormMovimentacao form, JDialog modal) {
        if (!form.isCamposValidos()) {
            exibirAviso(modal, "Por favor, preencha todos os campos obrigatórios!");
            return;
        }

        try {
            String categoria = form.getCategoria().trim();

            // Validação: Categoria não pode conter números
            if (categoria.matches(".*\\d.*")) {
                exibirAviso(modal, "A categoria da movimentação não pode conter números!");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date dataMovimentacao = sdf.parse(form.getDataMovimentacao());

            BigDecimal valor = new BigDecimal(form.getValor().replace(",", "."));

            // Validação: Valor > 0
            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                exibirAviso(modal, "O valor da movimentação deve ser maior que zero!");
                return;
            }

            String descricao = form.getDescricao();

            int idInsumo = 0;
            if (form.getIdInsumoFk() != null && !form.getIdInsumoFk().trim().isEmpty()) {
                idInsumo = Integer.parseInt(form.getIdInsumoFk().trim());
            }

            MovimentacaoDAO dao = new MovimentacaoDAO();

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
                    0,
                    dataMovimentacao,
                    valor,
                    categoria,
                    descricao,
                    idInsumo
                );
                dao.salvar(mov);
                JOptionPane.showMessageDialog(modal, "Movimentação registrada com sucesso!");
            }

            modal.dispose();

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(modal, "Data inválida! Por favor, utilize o formato dd/MM/yyyy.", "Erro na Data", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(modal, "Erro de formatação nos valores numéricos (Valor ou ID Insumo). Verifique os campos digitados.", "Erro de Formatação", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(modal, "Erro de banco de dados ao salvar a movimentação: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(modal, "Erro inesperado ao salvar movimentação: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================================
    // 7. VENDA
    // =========================================================================
    public void salvarVenda(FormVenda form, JDialog modal) {
        if (!form.isCamposValidos()) {
            exibirAviso(modal, "Por favor, preencha todos os campos obrigatórios!");
            return;
        }

        try {
            String formaPagamento = form.getFormaPagamento().trim();
            String statusEntrega = form.getStatusEntrega().trim();

            // Validação: Forma de pagamento e Status não podem conter números
            if (formaPagamento.matches(".*\\d.*")) {
                exibirAviso(modal, "A forma de pagamento não pode conter números!");
                return;
            }
            if (statusEntrega.matches(".*\\d.*")) {
                exibirAviso(modal, "O status da entrega não pode conter números!");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            java.util.Date dataUtil = sdf.parse(form.getDataVenda());
            java.sql.Date dataVenda = new java.sql.Date(dataUtil.getTime());

            BigDecimal valorTotal = new BigDecimal(form.getValorTotal());

            // Validação: Valor total > 0
            if (valorTotal.compareTo(BigDecimal.ZERO) <= 0) {
                exibirAviso(modal, "O valor total da venda deve ser maior que zero!");
                return;
            }

            int idCliente = Integer.parseInt(form.getIdClienteFk().trim());

            Venda venda = new Venda();
            venda.setDataVenda(dataVenda);
            venda.setValorTotal(valorTotal);
            venda.setFormaPagamento(formaPagamento);
            venda.setStatusEntrega(statusEntrega);
            venda.setIdCliente(idCliente);

            VendaDAO dao = new VendaDAO();

            if (form.isEdicao()) {
                venda.setIdVenda(form.getIdVendaEmEdicao());
                dao.atualizar(venda);
                JOptionPane.showMessageDialog(modal, "Venda atualizada com sucesso!");
            } else {
                int idGerado = dao.salvar(venda);
                JOptionPane.showMessageDialog(modal, "Venda registrada com sucesso! (ID: " + idGerado + ")");
            }

            modal.dispose();

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(modal, "Data de venda inválida! Utilize o formato AAAA-MM-DD (Ex: 2026-12-31).", "Erro na Data", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(modal, "Erro nos valores numéricos (Valor Total ou ID do Cliente). Verifique os campos digitados.", "Erro de Formatação", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            String msgError = e.getMessage().toLowerCase();
            if (msgError.contains("foreign key") || msgError.contains("foreign") || msgError.contains("constraint")) {
                JOptionPane.showMessageDialog(modal, "O Cliente informado (ID) não existe no banco de dados!", "Cliente Não Encontrado", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(modal, "Erro de banco de dados ao salvar a venda: " + e.getMessage(), "Erro SQL", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(modal, "Erro inesperado ao salvar venda: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}