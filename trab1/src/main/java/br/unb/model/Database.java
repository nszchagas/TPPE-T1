package br.unb.model;


import java.util.ArrayList;

public final class Database {
    private static Database db;
    private ArrayList<Cliente> clientes;
    private ArrayList<Produto> produtos;

    private Database() {
        this.clientes = new ArrayList<>();
        this.produtos = new ArrayList<>();
    }

    public static Database getInstance() {
        if (db == null) {
            db = new Database();
        }
        return  db;
    }

    public void insereCliente(Cliente cliente) throws Exception {
        if (clientes.contains(cliente))
        {
            throw new Exception(String.format("Cliente já inserido no banco de dados. %s", cliente.toString()));
        }
        this.clientes.add(cliente);
    }

    public void insereProduto(Produto produto) throws Exception {
        if (produtos.contains(produto))
            throw new Exception(String.format("Produto já inserido no banco de dados. %s", produto.toString()));
        this.produtos.add(produto);
    }

    public int getQtdClientes(){
        return this.clientes.size();
    }
    public int getQtdProdutos(){
        return this.produtos.size();
    }
}
