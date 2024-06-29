package br.unb;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class Database {
    private static Database db;
    private ArrayList<Cliente> clientes;

    private Database() {
        this.clientes = new ArrayList<>();
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

    public int getQtdClientes(){
        return this.clientes.size();
    }

}
