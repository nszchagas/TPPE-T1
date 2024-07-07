package br.unb.model;

import br.unb.model.categorias.MetodoDePagamento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.unb.model.categorias.CategoriaDeCliente.PRIME;
import static br.unb.util.OperacoesFinanceiras.calculaDesconto;
import static br.unb.util.OperacoesFinanceiras.calculaFrete;

public class Venda {
    private LocalDate data;
    private Cliente cliente;
    private List<Produto> produtos;
    private double saldoCashback;
    private MetodoDePagamento metodoDePagamento;
    private boolean usaCashback;

    public Venda(Cliente cliente, List<Produto> produtos, MetodoDePagamento metodoDePagamento, LocalDate data, boolean usaCashback) {
        this.data = data;
        this.cliente = cliente;
        this.produtos = produtos;
        this.metodoDePagamento = metodoDePagamento;
        this.usaCashback = usaCashback;
        if (usaCashback && cliente.getCategoria().equals(PRIME))
            this.saldoCashback = cliente.getCashback();
        else
            this.saldoCashback = 0;

    }

    public Venda(Cliente cliente, List<Produto> produtos, MetodoDePagamento metodoDePagamento, LocalDate data) {
        this(cliente, produtos, metodoDePagamento, data, false);
    }

    public Venda(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setSaldoCashback(double saldoCashback) {
        this.saldoCashback = saldoCashback;
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

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
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

    public void setMetodoDePagamento(MetodoDePagamento metodoDePagamento) {
        this.metodoDePagamento = metodoDePagamento;
    }

    public LocalDate getData() {
        return data;

    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getValorFinal() {
//        double vf = new NotaFiscal(this).getValorFinal();
        double vf = this.getValorGasto();
        if (saldoCashback >= vf)
            return 0;
        return vf - saldoCashback;
    }

    public boolean isUsaCashback() {
        return usaCashback;
    }

    public void setUsaCashback(boolean usaCashback) {
        this.usaCashback = usaCashback;
    }
}
