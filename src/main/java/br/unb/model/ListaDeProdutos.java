package br.unb.model;

import java.util.ArrayList;
import java.util.List;

import static br.unb.util.OperacoesFinanceiras.calculaImposto;
public class ListaDeProdutos {
    // Atributos relacionados aos impostos e produtos
    private final List<Double> icms;
    private final List<Double> municipais;
    private final List<Produto> produtos;
    private double valorGasto;

    public ListaDeProdutos(List<Produto> produtos, String estado) {
        valorGasto = 0;
        this.icms = new ArrayList<>();
        this.municipais = new ArrayList<>();
        this.produtos = produtos;
        for (Produto produto : produtos) {
            double valor = produto.getValorDeVenda();

            double i = calculaImposto("ICMS", estado, valor);
            double m = calculaImposto("MUNICIPAL", estado, valor);

            icms.add(i);
            municipais.add(m);
            valorGasto += valor + i + m;
        }

    }

    public List<Double> getIcms() {
        return icms;
    }

    public List<Double> getMunicipais() {
        return municipais;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public double getValorGasto() {
        return valorGasto;
    }
}
