package br.unb.model;

import java.time.LocalDate;

public class Venda {
    public LocalDate data;
    public String email;
    public Venda(String email, String[] itensId, String metodoDePagamento, LocalDate data){
        this.data = data;
        this.email = email;
    }
    public Cliente getCliente() {
        return Database.getInstance().getClienteByEmail(this.email);
    }
}
