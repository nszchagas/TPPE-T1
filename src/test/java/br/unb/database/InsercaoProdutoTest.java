package br.unb.database;

import br.unb.model.Produto;
import br.unb.model.Database;
import org.junit.Test;

import static br.unb.service.Cadastro.insereNoBanco;
import static org.junit.Assert.*;

public class InsercaoProdutoTest {
    final Database db = Database.getInstance();


    @Test
    public void assertInsereProdutos() {
        Produto[] produtos = {
        new Produto("BOLA DE FUTEBOL" ,12.6, "UN", "123456"),
        new Produto("fio de cobre", 3.5, "METRO", "654321"),
        new Produto("Água mineral", 2.0, "LITRO", "789012"),
        new Produto("CABO DE REDE", 1.2, "METRO", "210987"),
        new Produto("OLEO DE COZINHA", 6.5, "LITRO", "345678")
        };
        for (Produto produto : produtos) {
            insereNoBanco(produto);
        }
        for (Produto produto : produtos ){
            Produto produtoCadastrado = db.getProdutoByCodigo(produto.codigo);
            assertEquals( produto, produtoCadastrado);

        }
    }

    @Test
    public void assertAtributos(){
        String codigo = "123456", unidade = "UN", descricao = "BOLA DE FUTEBOL";
        double valorDeVenda = 12.6;

        insereNoBanco(new Produto(descricao,valorDeVenda, unidade, codigo));
        Produto inserido = db.getProdutoByCodigo(codigo);
        assertNotNull(inserido);
        assertEquals(inserido.descricao,descricao);
        assertEquals(inserido.unidade,unidade);
        assertEquals(inserido.codigo,codigo);
        assert inserido.valorDeVenda == valorDeVenda;
    }
    @Test
    public void assertNaoInsereDuplicado(){
        String codigoRepetido = "123456";
        String[] descricoes = {"BOLA DE FUTEBOL", "OLEO DE COZINHA"};
        String[] unidades = {"UN", "L"};
        double[] valores = {12.6,7.69};

        insereNoBanco(new Produto(descricoes[0] ,valores[0], unidades[0], codigoRepetido));
        insereNoBanco(new Produto(descricoes[1], valores[1], unidades[1], codigoRepetido));

        Produto produtoInserido = db.getProdutoByCodigo("123456");
        assertNotNull(produtoInserido);
        assertEquals(codigoRepetido, produtoInserido.codigo);
        assertEquals(descricoes[0], produtoInserido.descricao);
        assertEquals(unidades[0], produtoInserido.unidade);
        assertEquals(valores[0], produtoInserido.valorDeVenda, 0.05);


    }


}

