package br.unb.model;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static br.unb.util.OperacoesFinanceiras.calculaImposto;

public class NotaFiscal {
    // Venda e Dados do cliente
    Venda venda;
    String data;

    // Atributos relacionados aos impostos e produtos
    private List<Double> icms;
    private List<Double> municipais;
    private List<Produto> produtos;
    // Atributos relacionados ao valor da nota fiscal
    private double valorGasto;
    private double valorTotal;
    private double frete;
    private double desconto;
    private double valorFinal;


    public NotaFiscal(Venda venda) {
        this.venda = venda;
        this.produtos = venda.getProdutos();
        this.icms = new ArrayList<>();
        this.municipais = new ArrayList<>();
        calcularValores();

    }

    private void calcularValores() {
        valorGasto = 0;
        for (Produto produto : produtos) {
            double valor = produto.getValorDeVenda();
            String estado = venda.getCliente().getEstado();

            double i = calculaImposto("ICMS", estado, valor);
            double m = calculaImposto("MUNICIPAL", estado, valor);

            icms.add(i);
            municipais.add(m);
            valorGasto += valor + i + m;
        }
        frete = venda.getFrete();
        valorTotal = valorGasto + frete;
        desconto = venda.getDesconto();
        valorFinal = valorTotal - desconto;
        data = venda.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String emiteNotaFiscal() {
        StringBuilder nota = new StringBuilder();
        nota.append("------- Nota Fiscal -------").append('\n');
        nota.append("Data: ").append(data).append('\n');
        nota.append("Cliente: ").append(venda.getCliente().getNome()).append('\n');
        nota.append("Email: ").append(venda.getCliente().getEmail()).append('\n');
        nota.append("----------------------------").append('\n');
        nota.append("Produtos:").append('\n');
        for (Produto produto : produtos) {
            nota.append(produto.getCodigo()).append("   ").append(produto.getDescricao()).append(" - R$").append(produto.getValorDeVenda()).append('\n');
        }
        nota.append("----------------------------").append('\n');
        nota.append("Frete: R$").append(frete).append('\n');
        nota.append("Total: R$").append(valorTotal).append('\n');
        nota.append("Descontos: - R$").append(desconto).append('\n');
        nota.append("Valor Final: R$").append(valorFinal).append('\n');
        nota.append("Método de Pagamento: ").append(venda.getMetodoDePagamento()).append('\n');
        System.out.println(nota);
        return nota.toString();
    }
    public double getFrete() {
        return frete;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public List<Double> getImpostosICMS() {
        return icms;
    }

    public List<Double> getImpostosMunicipal() {
        return municipais;
    }

    public double getValorGasto() {
        return valorGasto;
    }

    public double getDesconto() {
        return desconto;
    }

    public double getValorFinal() {
        return valorFinal;
    }
}
