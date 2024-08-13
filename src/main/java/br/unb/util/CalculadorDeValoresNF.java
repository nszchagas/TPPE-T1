package br.unb.util;

import br.unb.model.ListaDeProdutos;
import br.unb.model.Venda;


public class CalculadorDeValoresNF {

    private double valorGasto;
    private double valorTotal;
    private double frete;
    private double desconto;
    private double valorFinal;

    public double getValorGasto() {
        return valorGasto;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public double getFrete() {
        return frete;
    }

    public double getDesconto() {
        return desconto;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public void calcular(Venda venda) {
        ListaDeProdutos listaDeProdutos = new ListaDeProdutos(venda.getProdutos(), venda.getCliente().getEstado());
        valorGasto = 0;

        valorGasto = listaDeProdutos.getValorGasto();
        frete = venda.getFrete();
        desconto = venda.getDesconto();
        valorTotal = valorGasto + frete;
        valorFinal = valorTotal - desconto;
    }


}
