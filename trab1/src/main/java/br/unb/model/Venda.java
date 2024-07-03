package br.unb.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Venda {
    public final LocalDate data;
    public final String email;
    public final List<String> produtosId;
    public final String metodoDePagamento;
    public Venda(String email, List<String> produtosId, String metodoDePagamento, LocalDate data){
        this.data = data;
        this.email = email;
        this.produtosId = produtosId;
        this.metodoDePagamento = metodoDePagamento;
    }
    public Cliente getCliente() {
        return Database.getInstance().getClienteByEmail(this.email);
    }

    public List<Produto> getProdutos() {
        List<Produto> produtos = new ArrayList<>();
        this.produtosId.forEach(codigo -> produtos.add(Database.getInstance().getProdutoByCodigo(codigo)));
        return produtos;
    }
}
