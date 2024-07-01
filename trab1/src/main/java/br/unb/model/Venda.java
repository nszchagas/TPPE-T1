package br.unb.model;

import java.time.LocalDate;
import java.util.List;

public class Venda {
    public LocalDate data;
    public String email;
    public List<String> produtosId;
    public Venda(String email, List<String> produtosId, String metodoDePagamento, LocalDate data){
        this.data = data;
        this.email = email;
        this.produtosId = produtosId;
    }
    public Cliente getCliente() {
        return Database.getInstance().getClienteByEmail(this.email);
    }

    public List<Produto> getProdutos() {
        return List.of();
    }
}
