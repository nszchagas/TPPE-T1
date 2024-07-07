package br.unb.model;


import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class Database {
    private static Database db;
    private final ArrayList<Cliente> clientes;
    private final ArrayList<Produto> produtos;

    private final ArrayList<Venda> vendas;

    private Database() {
        clientes = new ArrayList<>();
        produtos = new ArrayList<>();
        vendas = new ArrayList<>();
    }

    public static Database getInstance() {
        if (db == null) {
            db = new Database();
        }
        return db;
    }

    public void insereCliente(Cliente cliente) throws SQLIntegrityConstraintViolationException {
        if (clientes.contains(cliente))
            throw new SQLIntegrityConstraintViolationException(String.format("Cliente já inserido no banco de dados. %s\n", cliente.toString()));
        clientes.add(cliente);
    }

    public void insereProduto(Produto produto) throws SQLIntegrityConstraintViolationException {
        if (produtos.contains(produto))
            throw new SQLIntegrityConstraintViolationException(String.format("Produto já inserido no banco de dados. %s\n", produto.toString()));
        produtos.add(produto);
    }

    public void insereVenda(Venda venda) throws SQLIntegrityConstraintViolationException {
        if (vendas.contains(venda))
            throw new SQLIntegrityConstraintViolationException(String.format("Venda já inserida no banco de dados. %s\n", venda.toString()));
        vendas.add(venda);
    }

    public Produto getProdutoByCodigo(String codigo) {
        for (Produto produto : produtos) {
            if (produto.codigo.equals(codigo))
                return produto;
        }
        return null;
    }

    public Cliente getClienteByEmail(String email) {
        for (Cliente cliente : clientes) {
            if (cliente.email.equals(email))
                return cliente;
        }
        return null;
    }

}
