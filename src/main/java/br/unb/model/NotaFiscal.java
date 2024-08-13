package br.unb.model;


import br.unb.util.CalculadorDeValoresNF;

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


    public NotaFiscal(Venda venda) {
        String estado = venda.getCliente().getEstado();
        List<Produto> produtos = venda.getProdutos();
        this.venda = venda;
        this.data = venda.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.listaDeProdutos = new ListaDeProdutos(produtos, estado);

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


    //          OPERAÇÃO: EXTRAIR MÉTODO (montaCabecalhoNF e montaProdutosNF)
    // Smelly Code: o método estava muito longo e com diversas atribuições.
    // Como foi feito: os trechos de código relativos à montar o cabeçalho da nota e montar a listagem de produtos foram
    //  retirados desse método e inseridos em novos métodos. Após a inserção, as variáveis que não estavam definidas no escopo
    //  foram transformadas em argumentos da função.

    public String emiteNotaFiscal() {
        StringBuilder nota = new StringBuilder();
        // Método especializado para imprimir o cabeçalho da nota com os dados do cliente.
        nota.append(montaCabecalhoNF(data, venda.getCliente().getNome(), venda.getCliente().getEmail()));
        // Método especializado em listar os produtos e seus impostos
        nota.append(montaProdutosNF(listaDeProdutos));

        //      OPERAÇÃO: SUBSTITUIR MÉTODO POR OBJETO-MÉTODO
        // Smelly Code: o método calcularValores estava muito complexo, diminuindo também a especialidade da classe NotaFiscal,
        //   que era utilizada para armazenar os valores intermediários dos cálculos (para viabilizar os testes unitários).
        // Como foi feito: o método calcularValores foi movido para a nova classe CalculadoraDeValoresNF, e então as variáveis
        //   envolvidas foram transformadas em atributos da classe, e os argumentos que não estavam relacionados aos cálculos
        //   foram retirados do método (data). Em seguida, os testes precisaram ser refatorados, para utilizar os valores
        //   calculados a partir da classe, tornando-os mais simples e reduzindo o tamanho da classe NotaFiscal.
        //

        CalculadorDeValoresNF calculadora = new CalculadorDeValoresNF();
        calculadora.calcular(venda);

        nota.append("Frete: R$").append(calculadora.getFrete()).append('\n');
        nota.append("Total: R$").append(calculadora.getValorTotal()).append('\n');
        nota.append("Descontos: - R$").append(calculadora.getDesconto()).append('\n');
        nota.append("Valor Final: R$").append(calculadora.getValorFinal()).append('\n');
        nota.append("Método de Pagamento: ").append(venda.getMetodoDePagamento()).append('\n');
        System.out.println(nota);
        return nota.toString();
    }


}
