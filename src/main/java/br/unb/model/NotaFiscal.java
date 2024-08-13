package br.unb.model;


import java.time.format.DateTimeFormatter;
import java.util.List;


public class NotaFiscal {
    // Venda e Dados do cliente
    final Venda venda;
    // Atributos relacionados aos impostos e produtos

    //          OPERAÇÃO: EXTRAIR CLASSE (ListaDeProdutos)
    // Smelly Code: a classe NotaFiscal tinha várias atribuições e métodos longos, então para reduzir o tamanho
    // desse método (calcularValores), foi criada uma nova classe (ListaDeProdutos) encarregada de armazenar os produtos
    // e seus valores.
    // Como foi feito: O método calcularValores foi movido para a classe ListaDeProdutos, e foram retiradas as variáveis
    // que são relacionadas à nota fiscal. As operações que restaram foram inseridas no construtor.
    private final ListaDeProdutos listaDeProdutos;
    String data;
    // Atributos relacionados ao valor da nota fiscal
    private double valorGasto;
    private double valorTotal;
    private double frete;
    private double desconto;
    private double valorFinal;


    public NotaFiscal(Venda venda) {
        String estado = venda.getCliente().getEstado();
        List<Produto> produtos = venda.getProdutos();
        this.venda = venda;
        this.listaDeProdutos = new ListaDeProdutos(produtos, estado);
        calcularValores();

    }

    private static String montaCabecalhoNF(String data, String nome, String email) {
        return "------- Nota Fiscal -------" + '\n' +
                "Data: " + data + '\n' +
                "Cliente: " + nome + '\n' +
                "Email: " + email + '\n' +
                "----------------------------" + '\n';
    }

    private static String montaProdutosNF(ListaDeProdutos listaDeProdutos) {
        List<Produto> produtos = listaDeProdutos.getProdutos();
        List<Double> icms = listaDeProdutos.getIcms();
        List<Double> municipais = listaDeProdutos.getMunicipais();
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

        valorGasto = listaDeProdutos.getValorGasto();
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
        nota.append(montaProdutosNF(listaDeProdutos));

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
        return listaDeProdutos.getIcms();
    }

    public List<Double> getImpostosMunicipal() {
        return listaDeProdutos.getMunicipais();
    }

    public double getValorGasto() {
        return valorGasto;
    }

}
