package br.unb.model;


import java.util.ArrayList;
import java.util.List;

import static br.unb.util.OperacoesFinanceiras.calculaImposto;

public class NotaFiscal {
    List<Double> icms;
    List<Double> municipais;
    List<Produto> produtos;
    double total, frete;



    public NotaFiscal(Venda venda) {
        icms = new ArrayList<>();
        municipais = new ArrayList<>();
        produtos = venda.getProdutos();
        total = 0;
        for (Produto produto : produtos) {
            double valor = produto.getValorDeVenda();
            String estado = venda.getCliente().getEstado();

            double i = calculaImposto("ICMS", estado, valor);
            double m = calculaImposto("MUNICIPAL", estado, valor);

            icms.add(i);
            municipais.add(m);
            total += valor + i + m;
        }

        frete = venda.getFrete();
        total += frete;

    }

    public static void emiteNotaFiscal() {
        Venda venda;
        double valor = 0.0, frete = 0.0;
        List<Double> descontos = List.of();

    }

    public double getFrete() {
        return frete;
    }

    public double getTotal() {
        return total;
    }

    public List<Double> getImpostosICMS(){
        return icms;
    }

    public List<Double> getImpostosMunicipal(){
        return municipais;
    }


}
