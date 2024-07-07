package br.unb.main;

import br.unb.Main;
import br.unb.model.Cliente;
import br.unb.model.Database;
import br.unb.model.Produto;
import br.unb.model.categorias.CategoriaDeCliente;
import br.unb.model.categorias.MetodoDePagamento;
import br.unb.model.categorias.RegiaoDoEstado;
import br.unb.service.Cadastro;
import org.junit.Ignore;
import org.junit.Test;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;

import static br.unb.Main.*;
import static br.unb.model.categorias.CategoriaDeCliente.PADRAO;
import static br.unb.model.categorias.CategoriaDeCliente.PRIME;
import static br.unb.model.categorias.MetodoDePagamento.*;
import static br.unb.model.categorias.RegiaoDoEstado.INTERIOR;
import static org.junit.Assert.*;

public class MainTest {


    String nomeCliente = "João da Silva", emailCliente = "joao.silva@gmail.com", estadoCliente = "RS";
    RegiaoDoEstado regiaoCliente = INTERIOR;
    CategoriaDeCliente categoriaCliente = PADRAO;
    List<Produto> produtos = List.of(
            new Produto("Produto 1", 10.00, "UNIDADE", "11111"),
            new Produto("Produto 2", 15.00, "UNIDADE", "22222")
    );
    String cartaoDaLoja = "4296 1370 0000 0000", cartaoExterno = "5296 1370 0000 0001";
    String dataVenda = "2024-07-07";


    @Test
    public void testCadastrarCliente() {

        String[] params = {COD_CADASTRO_CLIENTE, nomeCliente, String.valueOf(regiaoCliente), estadoCliente, String.valueOf(categoriaCliente), emailCliente, COD_SAIR};

        StringBuilder entrada = new StringBuilder();
        for (String param : params) {
            entrada.append(param).append('\n');
        }

        ByteArrayInputStream in = new ByteArrayInputStream(entrada.toString().getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Main.main(new String[0]);

        String output = out.toString();
        assertTrue(output.contains("Cliente cadastrado: Cliente{nome='João da Silva'"));
        assertTrue(output.contains("Saindo do programa..."));
        Cliente clienteCadastrado = Database.getInstance().getClienteByEmail(emailCliente);
        assertNotNull(clienteCadastrado);
        assertEquals(nomeCliente, clienteCadastrado.nome);
        assertEquals(emailCliente, clienteCadastrado.email);
        assertEquals(estadoCliente, clienteCadastrado.getEstado());
        assertEquals(regiaoCliente, clienteCadastrado.getRegiao());
        assertEquals(categoriaCliente, clienteCadastrado.getCategoria());

    }


    @Test
    public void testCadastrarProduto() {
        StringBuilder sb = new StringBuilder();
        String descricaoProduto = "Bola de Futebol Americano", valorDeVendaProduto = "72.98", unidadeProduto = "unidade", codigoProduto = "12345812";

        String[] params = {COD_CADASTRO_PRODUTO, descricaoProduto, valorDeVendaProduto, unidadeProduto, codigoProduto, COD_SAIR};
        for (String param : params) {
            sb.append(param).append('\n');
        }

        ByteArrayInputStream in = new ByteArrayInputStream(sb.toString().getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Main.main(new String[0]);

        String output = out.toString();
        assertTrue(output.contains("Produto cadastrado"));
        assertTrue(output.contains("Saindo do programa..."));

        Produto produto = Database.getInstance().getProdutoByCodigo(codigoProduto);

        assertEquals(descricaoProduto, produto.descricao);
        assertEquals(Double.parseDouble(valorDeVendaProduto), produto.valorDeVenda, 0.1);
        assertEquals(unidadeProduto.toUpperCase(), produto.unidade);
        assertEquals(codigoProduto, produto.codigo);

    }

    @Test
    @Ignore
    public void testaCadastrarVendaComCashback() {
        List<String> produtosIds = produtos.stream().map((produto) -> produto.codigo).collect(Collectors.toList());
        CategoriaDeCliente categoriaCliente = PRIME;
        MetodoDePagamento metodoDePagamento = BOLETO;
        String usarCashback = "sim";

        Cliente cliente = new Cliente(nomeCliente, categoriaCliente, estadoCliente, regiaoCliente, emailCliente);

        Cadastro.insereNoBanco(cliente);
        for (Produto p : produtos)
            Cadastro.insereNoBanco(p);


    }

}


