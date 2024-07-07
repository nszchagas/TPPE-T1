package br.unb.service;

import br.unb.model.*;
import br.unb.model.categorias.Endereco;
import br.unb.model.categorias.MetodoDePagamento;
import org.apache.commons.validator.routines.EmailValidator;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static br.unb.model.categorias.MetodoDePagamento.CARTAO_EXTERNO;
import static br.unb.model.categorias.MetodoDePagamento.CARTAO_LOJA;

public class Cadastro {
    private Cadastro() {

    }


    public static Cliente criaCliente(String nome, String regiao, String estado, String categoria, String email) {
        List<String> categoriasValidas = Cliente.getCategoriasValidas();
        List<String> estadosValidos = Endereco.getUfsValidas();
        List<String> regioesValidas = Endereco.getRegioesValidas();

        String regiao_inserida = regiao;
        String categoria_inserida = categoria;
        categoria = categoria.toUpperCase().trim();
        estado = estado.trim().toUpperCase();
        regiao = regiao.trim().toUpperCase();

        if (!categoriasValidas.contains(categoria)) {
            throw new IllegalArgumentException(String.format("Categoria inválida: \"%s\".", categoria_inserida));
        }
        if (estado.length() != 2) {
            throw new IllegalArgumentException("Insira a sigla do estado.");
        }
        if (!estadosValidos.contains(estado)) {
            throw new IllegalArgumentException(String.format("Estado inválido: %s.", estado));
        }
        if (!regioesValidas.contains(regiao)) {
            throw new IllegalArgumentException(String.format("Região inválida: \"%s\".", regiao_inserida));
        }
        // Validação de email
        if (!EmailValidator.getInstance().isValid(email))
            throw new IllegalArgumentException(String.format("Email inválido: \"%s\"", email));
        return new Cliente(nome, categoria, estado, regiao, email);
    }


    public static Produto criaProduto(String descricao, String valorDeVenda, String unidade, String codigo) {
        double valor;

        // Validação de Descrição
        descricao = validaCampoTextual(descricao, "descrição");

        // Validação do Código
        codigo = validaCampoTextual(codigo, "código");


        // Validação de valor

        try {
            if (valorDeVenda.contains(",")) {
                throw new IllegalArgumentException(String.format("Valor inválido. Utilize ponto para separar as casas decimais. Valor inserido: \"%s\".", valorDeVenda));
            }
            valor = Double.parseDouble(valorDeVenda);
            assert valor > 0;
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Valor de venda não pode estar vazio.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Valor de venda deve ser numérico. Valor inserido: \"%s\".", valorDeVenda));
        } catch (AssertionError e) {
            throw new IllegalArgumentException(String.format("Valor de venda deve ser positivo. Valor inserido: \"%s\".", valorDeVenda));
        }

        // Validação de Unidade
        String unidadeNormalizada;
        try {
            unidadeNormalizada = UnidadeValida.normaliza(unidade);
            if (unidadeNormalizada == null)
                throw new NullPointerException();

        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Unidade não pode estar vazia.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format("Unidade inválida. Valor inserido: \"%s\".", unidade));
        }


        return new Produto(descricao, valor, unidadeNormalizada, codigo);
    }

    private static String validaCampoTextual(String entrada, String tipo) {
        if (entrada == null || entrada.isEmpty() || entrada.trim().isEmpty()) {
            String msg = entrada == null ? String.format("O valor de %s não pode ser vazio.", tipo) : String.format("Valor de %s inválido: \"%s\".", tipo, entrada);
            throw new IllegalArgumentException(msg);
        }
        return entrada.trim();
    }

    public static Venda criaVenda(String emailCliente, List<String> produtosId, Object metodoDePagamento, String dataInserida) {
        Database db = Database.getInstance();
        LocalDate data;
        Cliente cliente;
        List<Produto> produtos = new ArrayList<>();
        try {
            if (dataInserida.isEmpty()) throw new DateTimeParseException("dataInserida", dataInserida, 0);
            data = LocalDate.parse(dataInserida);
            assert !data.isAfter(LocalDate.now());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(String.format("O valor de data não pode ser nulo: \"%s\"", dataInserida));
        } catch (DateTimeParseException | AssertionError e) {
            String msg = String.format("Formato ou valor de data inválido: \"%s\". Utilize o padrão ISO (Ex: 2024-06-30).", dataInserida);
            throw new IllegalArgumentException(msg);
        }

        // Valida cliente
        try {
            cliente = db.getClienteByEmail(emailCliente);
            assert cliente != null;
        } catch (AssertionError e) {
            System.err.printf("Nenhum cliente com email \"%s\" encontrado.", emailCliente);
            throw new IllegalArgumentException(String.format("Nenhum cliente com email \"%s\" encontrado.", emailCliente));
        }

        // Valida produtos
        if (produtosId.isEmpty())
            throw new IllegalArgumentException("A lista de produtos não pode ser vazia.");

        for (String id : produtosId) {
            try {
                Produto p = db.getProdutoByCodigo(id);
                assert p != null;
                produtos.add(p);
            } catch (AssertionError e) {
                throw new IllegalArgumentException(String.format("Nenhum produto com código \"%s\" encontrado.", id));
            }
        }

        MetodoDePagamento metodo;
        if (metodoDePagamento instanceof String)
            metodo = MetodoDePagamento.valueOf((String) metodoDePagamento);
        else if (metodoDePagamento instanceof MetodoDePagamento)
            metodo = (MetodoDePagamento) metodoDePagamento;
        else
            throw new IllegalArgumentException("Método de pagamento inválido.");


        return new Venda(cliente, produtos, metodo, data);
    }

    public static Venda criaVenda(String emailCliente, List<String> produtosId, String metodoDePagamento, String numeroCartao, String dataInserida) {
        // Valida método de pagamento
        if (metodoDePagamento == null || metodoDePagamento.isEmpty())
            throw new IllegalArgumentException("Método de pagamento não pode estar vazio.");
        MetodoDePagamento metodo;

        if (metodoDePagamento.toUpperCase().replace('Ã', 'A').equals("CARTAO")) {
            if (numeroCartao == null)
                throw new IllegalArgumentException("Número do cartão não pode estar vazio.");
            if (!Pattern.compile("^(\\d{4} ){3}\\d{4}$").matcher(numeroCartao).matches()) {
                throw new IllegalArgumentException(String.format("Formato inválido para número de cartão: \"%s\"", numeroCartao));
            }
            if (Pattern.compile("^4296 13(\\d| ){12}$").matcher(numeroCartao).matches()) {
                metodo = CARTAO_LOJA;
            } else
                metodo = CARTAO_EXTERNO;
        } else {
            try {
                metodo = MetodoDePagamento.valueOf(metodoDePagamento.toUpperCase().replace('Ã', 'A'));
            } catch (IllegalArgumentException e) {
                System.err.printf("Método de pagamento inválido: \"%s\"%n", metodoDePagamento);
                throw e;

            }
        }
        return criaVenda(emailCliente, produtosId, metodo, dataInserida);
    }

    public static int insereNoBanco(Object entidade) {
        Database db = Database.getInstance();
        try {
            if (entidade instanceof Cliente)
                return db.insereCliente((Cliente) entidade);
            else if (entidade instanceof Produto)
                return db.insereProduto((Produto) entidade);
            else if (entidade instanceof Venda)
                return db.insereVenda((Venda) entidade);

        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println(e.getMessage());
        }
        return -1;

    }

}
