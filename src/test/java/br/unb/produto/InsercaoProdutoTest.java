package br.unb.produto;

import br.unb.model.Database;
import br.unb.model.Produto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static br.unb.service.Cadastro.insereNoBanco;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class InsercaoProdutoTest {
    final Database db = Database.getInstance();

    // Parâmetros dos testes
    private final String descricao;
    private final double valorDeVenda;
    private final String unidade;
    private final String codigo;

    public InsercaoProdutoTest(String descricao, double valorDeVenda, String unidade, String codigo) {
        this.descricao = descricao;
        this.valorDeVenda = valorDeVenda;
        this.unidade = unidade;
        this.codigo = codigo;
    }

    @Parameterized.Parameters(name = "Produto: {0}, Valor: {1}, Unidade: {2}, Código: {3}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"BOLA DE FUTEBOL", 12.6, "UN", "123456"},
                {"fio de cobre", 3.5, "METRO", "654321"},
                {"Água mineral", 2.0, "LITRO", "789012"},
                {"CABO DE REDE", 1.2, "METRO", "210987"},
                {"OLEO DE COZINHA", 6.5, "LITRO", "345678"},
                {"BOLA DE FUTEBOL", 12.6, "UN", "18161"} // Adicionando um caso extra para testar atributos
        });
    }

    @Test
    public void assertInsereProdutos() {
        Produto produto = new Produto(descricao, valorDeVenda, unidade, codigo);
        insereNoBanco(produto);
        Produto produtoCadastrado = db.getProdutoByCodigo(codigo);
        assertNotNull(produtoCadastrado);
        assertEquals(produto, produtoCadastrado);
    }

    @Test
    public void assertAtributos() {
        if ("18161".equals(codigo)) {  // Somente para o caso de teste específico
            Produto inserido = db.getProdutoByCodigo(codigo);
            assertNotNull(inserido);
            assertEquals(descricao, inserido.descricao);
            assertEquals(unidade, inserido.unidade);
            assertEquals(codigo, inserido.codigo);
            assertEquals(valorDeVenda, inserido.getValorDeVenda(), 0.05);
        }
    }

    @Test
    public void assertNaoInsereDuplicado() {
        String codigoRepetido = "921478";
        String[] descricoes = {"BOLA DE FUTEBOL", "OLEO DE COZINHA"};
        String[] unidades = {"UN", "L"};
        double[] valores = {12.6, 7.69};

        insereNoBanco(new Produto(descricoes[0], valores[0], unidades[0], codigoRepetido));
        insereNoBanco(new Produto(descricoes[1], valores[1], unidades[1], codigoRepetido));

        Produto produtoInserido = db.getProdutoByCodigo(codigoRepetido);
        assertNotNull(produtoInserido);
        assertEquals(codigoRepetido, produtoInserido.codigo);
        assertEquals(descricoes[0], produtoInserido.descricao);
        assertEquals(unidades[0], produtoInserido.unidade);
        assertEquals(valores[0], produtoInserido.getValorDeVenda(), 0.05);
    }
}



