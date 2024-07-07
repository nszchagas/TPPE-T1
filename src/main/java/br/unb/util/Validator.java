package br.unb.util;

import br.unb.model.Cliente;
import br.unb.model.Database;
import br.unb.model.Produto;
import br.unb.model.UnidadeValida;
import br.unb.model.categorias.CategoriaDeCliente;
import br.unb.model.categorias.MetodoDePagamento;
import br.unb.model.categorias.RegiaoDoEstado;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static br.unb.model.categorias.MetodoDePagamento.CARTAO_EXTERNO;
import static br.unb.model.categorias.MetodoDePagamento.CARTAO_LOJA;

public class Validator {
    public static String validaCampoTextual(String entrada, String tipo) {
        if (entrada == null || entrada.isEmpty() || entrada.trim().isEmpty()) {
            String msg = entrada == null ? String.format("O valor de %s não pode ser vazio.", tipo) : String.format("Valor de %s inválido: \"%s\".", tipo, entrada);
            throw new IllegalArgumentException(msg);
        }
        return entrada.trim();
    }

    public static Object validaEnum(String valor, String nome) {
        try {
            switch (nome.toUpperCase()) {
                case "CATEGORIA":
                    return CategoriaDeCliente.valueOf(valor.trim().toUpperCase().replace('Ã', 'A'));
                case "REGIAO":
                    return RegiaoDoEstado.valueOf(valor.trim().toUpperCase());
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("%s inválida: \"%s\".", nome, valor));
        }
        return null;
    }

    public static MetodoDePagamento getMetodoDePagamento(String metodoDePagamento) {
        return getMetodoDePagamento(metodoDePagamento, null);
    }

    public static MetodoDePagamento getMetodoDePagamento(String metodoDePagamento, String numeroCartao) {
        if (metodoDePagamento == null || metodoDePagamento.isEmpty())
            throw new IllegalArgumentException("Método de pagamento não pode estar vazio.");
        if (metodoDePagamento.toUpperCase().replace('Ã', 'A').equals("CARTAO")) {
            if (numeroCartao == null)
                throw new IllegalArgumentException("Número do cartão não pode estar vazio.");
            if (!Pattern.compile("^(\\d{4} ){3}\\d{4}$").matcher(numeroCartao).matches()) {
                throw new IllegalArgumentException(String.format("Formato inválido para número de cartão: \"%s\"", numeroCartao));
            }
            if (Pattern.compile("^4296 13(\\d| ){12}$").matcher(numeroCartao).matches()) {
                return CARTAO_LOJA;
            } else
                return CARTAO_EXTERNO;
        } else {
            try {
                return MetodoDePagamento.valueOf(metodoDePagamento.toUpperCase().replace('Ã', 'A'));
            } catch (IllegalArgumentException e) {
                System.err.printf("Método de pagamento inválido: \"%s\"%n", metodoDePagamento);
                throw e;

            }
        }
    }

    public static double validaDouble(String valorDeVenda) {
        try {
            if (valorDeVenda.contains(",")) {
                throw new IllegalArgumentException(String.format("Valor inválido. Utilize ponto para separar as casas decimais. Valor inserido: \"%s\".", valorDeVenda));
            }
            double valor = Double.parseDouble(valorDeVenda);
            assert valor > 0;
            return valor;
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Valor de venda não pode estar vazio.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Valor de venda deve ser numérico. Valor inserido: \"%s\".", valorDeVenda));
        } catch (AssertionError e) {
            throw new IllegalArgumentException(String.format("Valor de venda deve ser positivo. Valor inserido: \"%s\".", valorDeVenda));
        }
    }

    public static String validaUnidadeDeMedida(String unidade) {
        try {
            String unidadeNormalizada = UnidadeValida.normaliza(unidade);
            assert unidadeNormalizada != null;
            return unidadeNormalizada;

        } catch (AssertionError | NullPointerException e) {
            throw new IllegalArgumentException("Unidade não pode estar vazia.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format("Unidade inválida. Valor inserido: \"%s\".", unidade));
        }
    }

    public static Cliente validaCliente(String emailCliente) {
        try {
            Cliente cliente = Database.getInstance().getClienteByEmail(emailCliente);
            assert cliente != null;
            return cliente;
        } catch (AssertionError e) {
            System.err.printf("Nenhum cliente com email \"%s\" encontrado.", emailCliente);
            throw new IllegalArgumentException(String.format("Nenhum cliente com email \"%s\" encontrado.", emailCliente));
        }
    }

    public static List<Produto> validaProdutos(List<String> produtosId) {
        // Valida produtos
        ArrayList<Produto> produtos = new ArrayList<>();
        if (produtosId.isEmpty())
            throw new IllegalArgumentException("A lista de produtos não pode ser vazia.");

        for (String id : produtosId) {
            try {
                Produto p = Database.getInstance().getProdutoByCodigo(id);
                assert p != null;
                produtos.add(p);
            } catch (AssertionError e) {
                throw new IllegalArgumentException(String.format("Nenhum produto com código \"%s\" encontrado.", id));
            }
        }
        return produtos;
    }

    public static LocalDate validaData(String dataInserida) {
        try {
            if (dataInserida.isEmpty()) throw new DateTimeParseException("dataInserida", dataInserida, 0);
            LocalDate data = LocalDate.parse(dataInserida);
            assert !data.isAfter(LocalDate.now());
            return data;
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(String.format("O valor de data não pode ser nulo: \"%s\"", dataInserida));
        } catch (DateTimeParseException | AssertionError e) {
            String msg = String.format("Formato ou valor de data inválido: \"%s\". Utilize o padrão ISO (Ex: 2024-06-30).", dataInserida);
            throw new IllegalArgumentException(msg);
        }
    }
}
