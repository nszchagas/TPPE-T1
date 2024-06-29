package br.unb;


import java.util.ArrayList;

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

    public void insereCliente(Cliente cliente){
        this.clientes.add(cliente);
    }

    public int getQtdClientes(){
        return this.clientes.size();
    }

}
