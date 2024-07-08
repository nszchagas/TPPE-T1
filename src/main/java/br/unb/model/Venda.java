package br.unb.model;

import br.unb.model.categorias.MetodoDePagamento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.unb.util.OperacoesFinanceiras.calculaDesconto;
import static br.unb.util.OperacoesFinanceiras.calculaFrete;

public class Venda {
    private final LocalDate data;
    private final Cliente cliente;
    private final List<Produto> produtos;
    private final MetodoDePagamento metodoDePagamento;
    private final boolean usaCashback;

    public Venda(Cliente cliente, List<Produto> produtos, MetodoDePagamento metodoDePagamento, LocalDate data, boolean usaCashback) {
        this.data = data;
        this.cliente = cliente;
        this.produtos = produtos;
        this.metodoDePagamento = metodoDePagamento;
        this.usaCashback = usaCashback;
    }

    public Venda(Cliente cliente, List<Produto> produtos, MetodoDePagamento metodoDePagamento, LocalDate data) {
        this(cliente, produtos, metodoDePagamento, data, false);
    }


    public double getFrete() {
        return calculaFrete(getCliente().getEstado(), getCliente().getRegiao(), getCliente().getCategoria());
    }

    public double getDesconto() {
        return calculaDesconto(getValorGasto(), getFrete(), getCliente().getCategoria(), getMetodoDePagamento());
    }

    public double getValorGasto() {
        double total = 0;
        for (Produto produto : getProdutos()) {
            total += produto.getValorDeVenda();
        }
        return total;
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

    public MetodoDePagamento getMetodoDePagamento() {
        return metodoDePagamento;
    }

    public LocalDate getData() {
        return data;

    }


    public double getValorFinal() {
        return -1;
    }
}
