package br.unb.model;


import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Database db;
    private static ArrayList<Cliente> clientes = new ArrayList<>();
    private static ArrayList<Produto> produtos = new ArrayList<>();
    private static ArrayList<Venda> vendas = new ArrayList<>();

    private Database() {
    }

    public static Database getInstance() {

        if (db == null) {
            db = new Database();
        }
        return db;
    }

    public Venda getVendaById(int id) {
        return new Venda(null, List.of(), null, null);
    }

    public int insereCliente(Cliente cliente) throws SQLIntegrityConstraintViolationException {
        if (clientes.contains(cliente))
            throw new SQLIntegrityConstraintViolationException(String.format("Cliente já inserido no banco de dados. %s\n", cliente.toString()));
        clientes.add(cliente);
        return clientes.size() - 1;
    }

    public int insereProduto(Produto produto) throws SQLIntegrityConstraintViolationException {
        if (produtos.contains(produto))
            throw new SQLIntegrityConstraintViolationException(String.format("Produto já inserido no banco de dados. %s\n", produto.toString()));
        produtos.add(produto);
        return produtos.size() - 1;
    }

    public int insereVenda(Venda venda) throws SQLIntegrityConstraintViolationException {
        if (vendas.contains(venda))
            throw new SQLIntegrityConstraintViolationException(String.format("Venda já inserida no banco de dados. %s\n", venda.toString()));
        vendas.add(venda);
        return vendas.size() - 1;
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

    public Venda getVendaByDataCliente(LocalDate dataVenda, String emailCliente) {
        for (Venda venda : vendas) {
            if (venda.getData().equals(dataVenda) && venda.getCliente().getEmail().equals(emailCliente)) {
                return venda;
            }
        }
        return null;
    }
}
