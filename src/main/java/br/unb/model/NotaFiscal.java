package br.unb.model;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static br.unb.util.OperacoesFinanceiras.calculaImposto;

public class NotaFiscal {
    // Venda e Dados do cliente
    final Venda venda;
    // Atributos relacionados aos impostos e produtos
    private final List<Double> icms;
    private final List<Double> municipais;
    private final List<Produto> produtos;
    String data;
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

    private static String montaCabecalhoNF(String data, String nome, String email) {
        return "------- Nota Fiscal -------" + '\n' +
                "Data: " + data + '\n' +
                "Cliente: " + nome + '\n' +
                "Email: " + email + '\n' +
                "----------------------------" + '\n';
    }

    private static String montaProdutosNF(List<Produto> produtos, List<Double> icms, List<Double> municipais) {
        StringBuilder nota = new StringBuilder("Produtos:\n");
        double total = 0.0;
        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);
            total += produto.getValorDeVenda();
            nota.append(
                    String.format("%s   %s   - R$%.2f Impostos: (ICMS: %.2f, Municipal: %.2f)",
                            produto.getCodigo(),
                            produto.getDescricao(),
                            produto.getValorDeVenda(),
                            icms.get(i),
                            municipais.get(i)
                    ));
        }
        nota.append("----------------------------").append('\n');
        nota.append(String.format("Produtos: R$%.2f\n", total));
        return nota.toString();
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

    //          OPERAÇÃO: EXTRAIR MÉTODO (montaCabecalhoNF e montaProdutosNF)
    // Smelly Code: o método estava muito longo e com diversas atribuições.
    // Como foi feito: os trechos de código relativos à montar o cabeçalho da nota e montar a listagem de produtos foram
    // retirados desse método e inseridos em novos métodos. Após a inserção, as variáveis que não estavam definidas no escopo
    // foram transformadas em argumentos da função.

    public String emiteNotaFiscal() {
        StringBuilder nota = new StringBuilder();
        // Método especializado para imprimir o cabeçalho da nota com os dados do cliente.
        nota.append(montaCabecalhoNF(data, venda.getCliente().getNome(), venda.getCliente().getEmail()));
        // Método especializado em listar os produtos e seus impostos
        nota.append(montaProdutosNF(produtos, icms, municipais));

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

    public List<Double> getImpostosICMS() {
        return icms;
    }

    public List<Double> getImpostosMunicipal() {
        return municipais;
    }

    public double getValorGasto() {
        return valorGasto;
    }

    public double getValorFinal() {
        return valorFinal;
    }
}
