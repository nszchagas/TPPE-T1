package br.unb.venda;

import br.unb.model.Produto;
import br.unb.model.Venda;
import br.unb.service.CadastroDeVenda;
import br.unb.util.TestUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ProdutosTest {

    final List<String> entrada;
    final boolean isValid;
    final CadastroDeVenda cadastroDeVenda;
    final HashSet<String> idsEntrada;
    Venda vendaRegistrada;
    List<Produto> produtosRegistrados;
    HashSet<String> idsFromProdutos;

    public ProdutosTest(List<String> entrada, boolean isValid) {
        this.entrada = entrada;
        this.isValid = isValid;
        cadastroDeVenda = new CadastroDeVenda();
        idsEntrada = new HashSet<>(entrada);
        // Códigos válidos: 123, 456, 789, 101, 202 (TestUtils)
    }

    @BeforeClass
    public static void setUpDatabase() {
        TestUtils.populaBanco();
    }

    @Parameterized.Parameters(name = "{index}:entrada={0},isValid={1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {List.of(), false},
                {List.of("1", "2", "3"), false},
                {List.of("123", "456", "3"), false},
                {List.of("123", "456"), true},
                {List.of("456", "456"), true},
        });
    }

    @Before
    public void setUpTestCase() {
        if (isValid) {
            vendaRegistrada = cadastroDeVenda.criaVenda("email1@domain.com", entrada, "BOLETO", "2024-07-01");
            produtosRegistrados = vendaRegistrada.getProdutos();
            idsFromProdutos = new HashSet<>();
            for (Produto produto : produtosRegistrados) {
                idsFromProdutos.add(produto.codigo);
            }
        }

    }

    @Test
    public void testItensId() {
        CadastroDeVenda c = new CadastroDeVenda();
        if (!isValid) {
            assertThrows(IllegalArgumentException.class,
                    () ->
                            c.criaVenda("email1@domain.com", entrada, "BOLETO", "2024-07-01")
            );
        } else {
            assertEquals(new HashSet<>(vendaRegistrada.produtosId), idsEntrada);
            assertEquals(produtosRegistrados.size(), entrada.size());
            assertEquals(idsFromProdutos, idsEntrada);
        }
    }

}
