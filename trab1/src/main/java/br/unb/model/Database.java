package br.unb.model;


import java.util.ArrayList;

public final class Database {
    private static Database db;
    private final ArrayList<Cliente> clientes;
    private final ArrayList<Produto> produtos;

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
            throw new Exception(String.format("Cliente já inserido no banco de dados. %s\n", cliente.toString()));
        }
        this.clientes.add(cliente);
    }

    public void insereProduto(Produto produto) throws Exception {
        if (produtos.contains(produto))
            throw new Exception(String.format("Produto já inserido no banco de dados. %s\n", produto.toString()));
        this.produtos.add(produto);
    }

    public Produto getProdutoByCodigo(String codigo){
        for (Produto produto : produtos){
            if (produto.codigo.equals(codigo))
                return produto;
        }
        return null;
    }

    public Cliente getClienteByEmail(String email){
        for (Cliente cliente : clientes){
            if (cliente.email.equals(email))
                return cliente;
        }
        return null;
    }

    public int getQtdClientes(){
        return this.clientes.size();
    }
    public int getQtdProdutos(){
        return this.produtos.size();
    }
}
