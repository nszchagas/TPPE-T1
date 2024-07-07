package br.unb.model;

import br.unb.model.categorias.MetodoDePagamento;
import br.unb.util.OperacoesFinanceiras;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.unb.model.categorias.CategoriaDeCliente.ESPECIAL;
import static br.unb.model.categorias.MetodoDePagamento.CARTAO_LOJA;
import static br.unb.util.OperacoesFinanceiras.calculaDesconto;
import static br.unb.util.OperacoesFinanceiras.calculaFrete;

public class Venda {
    private final LocalDate data;
    private final Cliente cliente;
    private final List<Produto> produtos;
    private final MetodoDePagamento metodoDePagamento;

    public Venda(Cliente cliente, List<Produto> produtos, MetodoDePagamento metodoDePagamento, LocalDate data) {
        this.data = data;
        this.cliente = cliente;
        this.produtos = produtos;
        this.metodoDePagamento = metodoDePagamento;
    }

    public double getFrete() {
        return calculaFrete(getCliente().getEstado(), getCliente().getRegiao(), getCliente().getCategoria());
    }

    public double getDesconto() {
        return calculaDesconto(getValorGasto(), getFrete(), getCliente().getCategoria(), getMetodoDePagamento());
    }

    public double getValorGasto() {
        return -1;
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
}
