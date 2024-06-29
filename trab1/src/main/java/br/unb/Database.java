package br.unb;


public final class Database {
    private static Database db;

    private Database() {
    }

    public static Database getInstance() {
        if (db == null) {
            db = new Database();
        }
        return  db;
    }

    public Long getQtdClientes(){
        return 0L;
    }

}
