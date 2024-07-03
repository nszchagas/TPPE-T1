package br.unb.produto;

import br.unb.model.Produto;
import br.unb.model.Database;
import br.unb.service.CadastroDeProduto;
import org.junit.Test;

import static org.junit.Assert.*;

public class InsercaoDatabaseTest {
    final CadastroDeProduto cadastro = new CadastroDeProduto();
    final Database db = Database.getInstance();


    @Test
    public void assertInsereProdutos() {
        Produto[] produtos = {
        new Produto("BOLA DE FUTEBOL" ,12.6F, "UN", "123456"),
        new Produto("fio de cobre", 3.5F, "METRO", "654321"),
        new Produto("Água mineral", 2.0F, "LITRO", "789012"),
        new Produto("CABO DE REDE", 1.2F, "METRO", "210987"),
        new Produto("OLEO DE COZINHA", 6.5F, "LITRO", "345678")
        };
        for (Produto produto : produtos) {
            cadastro.insereProdutoNoBanco(produto);
        }
        for (Produto produto : produtos ){
            Produto produtoCadastrado = db.getProdutoByCodigo(produto.codigo);
            assertEquals(produtoCadastrado, produto);

        }
    }

    @Test
    public void assertAtributos(){
        String codigo = "123456", unidade = "UN", descricao = "BOLA DE FUTEBOL";
        float valorDeVenda = 12.6F;

        cadastro.insereProdutoNoBanco(new Produto(descricao,valorDeVenda, unidade, codigo));
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
        float[] valores = {12.6f,7.69f};

        cadastro.insereProdutoNoBanco(new Produto(descricoes[0] ,valores[0], unidades[0], codigoRepetido));
        cadastro.insereProdutoNoBanco(new Produto(descricoes[1], valores[1], unidades[1], codigoRepetido));

        Produto produtoInserido = db.getProdutoByCodigo("123456");
        assertNotNull(produtoInserido);
        assertEquals(codigoRepetido, produtoInserido.codigo);
        assertEquals(descricoes[0], produtoInserido.descricao);
        assertEquals(unidades[0], produtoInserido.unidade);
        assertEquals(valores[0], produtoInserido.valorDeVenda, 0.0);


    }


}

