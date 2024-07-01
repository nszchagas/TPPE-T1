package br.unb.model;

import java.time.LocalDate;
import java.util.List;

public class Venda {
    public LocalDate data;
    public String email;
    public List<String> itensId;
    public Venda(String email, List<String> itensId, String metodoDePagamento, LocalDate data){
        this.data = data;
        this.email = email;
    }
    public Cliente getCliente() {
        return Database.getInstance().getClienteByEmail(this.email);
    }
}
