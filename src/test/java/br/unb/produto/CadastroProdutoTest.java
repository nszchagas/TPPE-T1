package br.unb.produto;

import br.unb.Main;
import br.unb.model.Database;
import br.unb.model.Produto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;

import static br.unb.Main.COD_CADASTRO_PRODUTO;
import static br.unb.Main.COD_SAIR;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CadastroProdutoTest {
    String descricaoProduto, valorDeVendaProduto, unidadeProduto, codigoProduto;
    Produto produtoCadastrado;
    String outputConsole;

    public CadastroProdutoTest(String descricao, String valorDeVenda, String unidade, String codigo) {
        this.descricaoProduto = descricao;
        this.valorDeVendaProduto = valorDeVenda;
        this.unidadeProduto = unidade;
        this.codigoProduto = codigo;

        String[] params = {COD_CADASTRO_PRODUTO, descricaoProduto, valorDeVendaProduto, unidadeProduto, codigoProduto, COD_SAIR};

        StringBuilder sb = new StringBuilder();
        for (String param : params) {
            sb.append(param).append('\n');
        }
        ByteArrayInputStream in = new ByteArrayInputStream(sb.toString().getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Main.main(new String[0]);

        outputConsole = out.toString();

        produtoCadastrado = Database.getInstance().getProdutoByCodigo(codigoProduto);

        assertNotNull(produtoCadastrado);
    }

    @Parameterized.Parameters(name = "{index}: descricao={0}, valorDeVenda={1}, unidade={2}, codigo={3}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Bola de Futebol Americano", "72.98", "unidade", "12345812"},
                {"Camiseta Esportiva", "49.99", "unidade", "12345813"}
        });
    }

    @Test
    public void testMensagemDeCadastroCorreta() {
        assertTrue(outputConsole.contains("Produto cadastrado"));
    }


    @Test
    public void testaSaidaCorreta() {
        assertTrue(outputConsole.contains("Saindo do programa..."));
    }

    @Test
    public void testProdutoCadastrado() {
        assertNotNull(produtoCadastrado);
    }

    @Test
    public void testDescricaoProduto() {
        assertEquals(descricaoProduto, produtoCadastrado.getDescricao());
    }

    @Test
    public void testValorDeVendaProduto() {
        assertEquals(Double.parseDouble(valorDeVendaProduto), produtoCadastrado.getValorDeVenda(), 0.1);
    }

    @Test
    public void testUnidadeProduto() {
        assertEquals(unidadeProduto.toUpperCase(), produtoCadastrado.getUnidade());
    }

}