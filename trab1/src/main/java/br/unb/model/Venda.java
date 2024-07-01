package br.unb.model;

import java.time.LocalDate;

public class Venda {
    public LocalDate data;
    public String clienteId;
    public Venda(String clienteId, String[] itensId, String metodoDePagamento, LocalDate data){
        this.data = data;

    }
    public Cliente getCliente() {
        return null;
    }
}
