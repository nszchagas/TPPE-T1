package br.unb.model;

import br.unb.model.categorias.MetodoDePagamento;
import br.unb.util.OperacoesFinanceiras;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Venda {
    public final LocalDate data;
    public final Cliente cliente;
    public final List<Produto> produtos;
    public final MetodoDePagamento metodoDePagamento;

    public Venda(Cliente cliente, List<Produto> produtos, MetodoDePagamento metodoDePagamento, LocalDate data) {
        this.data = data;
        this.cliente = cliente;
        this.produtos = produtos;
        this.metodoDePagamento = metodoDePagamento;
    }

    public double getFrete() {
        return OperacoesFinanceiras.calculaFrete(cliente.getEstado(), cliente.getRegiao(), cliente.getCategoria());
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public List<String> getCodProdutos() {
        List<String> codsProdutos = new ArrayList<>();
        for (Produto produto : produtos) {
            codsProdutos.add(produto.codigo);
        }
        return codsProdutos;
    }
}
