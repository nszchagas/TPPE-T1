package br.unb.service;

import br.unb.model.Cliente;
import br.unb.model.Database;
import br.unb.model.Produto;
import br.unb.model.Venda;
import br.unb.model.categorias.MetodoDePagamento;
import br.unb.util.Validator;

import java.time.LocalDate;
import java.util.List;

public class VendaService {


    //    Entrypoint
    public static Venda criaVenda(String emailCliente, List<String> produtosId, MetodoDePagamento metodoDePagamento, String dataInserida) {
        LocalDate data = Validator.validaData(dataInserida);


        // Valida cliente
        Cliente cliente = Validator.validaCliente(emailCliente);
        Venda venda = new Venda(cliente);
        List<Produto> produtos = Validator.validaProdutos(produtosId);

        venda.setProdutos(produtos);
        venda.setProdutos(produtos);
        venda.setData(data);
        venda.setMetodoDePagamento(metodoDePagamento);
        persisteVenda(venda);
        return venda;
    }

    public static Venda criaVenda(String emailCliente, List<String> produtosId, String metodoDePagamento, String numeroCartao, String dataInserida, boolean usaCashback) {
        // Valida método de pagamento
        Venda venda = criaVenda(emailCliente, produtosId, null, dataInserida);

        MetodoDePagamento metodo = Validator.getMetodoDePagamento(metodoDePagamento, numeroCartao);

        venda.setMetodoDePagamento(metodo);
        venda.setUsaCashback(usaCashback);
        persisteVenda(venda);
        return venda;
    }

    public static Venda criaVenda(String emailCliente, List<String> produtosId, String metodoDePagamento, String numeroCartao, String dataInserida) {
        return criaVenda(emailCliente, produtosId, metodoDePagamento, numeroCartao, dataInserida, false);
    }

    private static void persisteVenda(Venda venda) {
        Database.insereNoBanco(venda);
    }
}
