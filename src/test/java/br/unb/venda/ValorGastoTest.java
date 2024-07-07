package br.unb.venda;

import br.unb.model.Cliente;
import br.unb.model.NotaFiscal;
import br.unb.model.Produto;
import br.unb.model.Venda;
import br.unb.model.categorias.CategoriaDeCliente;
import br.unb.model.categorias.MetodoDePagamento;
import br.unb.model.categorias.RegiaoDoEstado;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

import static br.unb.model.categorias.CategoriaDeCliente.PADRAO;
import static br.unb.model.categorias.MetodoDePagamento.DINHEIRO;
import static br.unb.model.categorias.RegiaoDoEstado.CAPITAL;
import static br.unb.model.categorias.RegiaoDoEstado.INTERIOR;
import static java.util.regex.Pattern.compile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ValorGastoTest {

    private final List<Produto> produtos;
    private final List<Double> precos;
    private final List<Double> listaICMS;
    private final List<Double> listaMunicipal;
    private final Cliente cliente;
    private final NotaFiscal notaFiscal;
    private final String conteudoDaNotaFiscal;
    private final double frete;
    private final double valorFinal;
    private final double descontos;
    private final Venda venda;
    private final MetodoDePagamento metodoDePagamento;
    private final LocalDate data;
    private double valorTotal;

    public ValorGastoTest(
            String estado,
            List<Double> precos,
            List<Double> listaICMS,
            List<Double> listaMunicipal,
            RegiaoDoEstado regiaoDoEstado,
            double frete
    ) {
        this.precos = precos;
        this.listaICMS = listaICMS;
        this.listaMunicipal = listaMunicipal;
        this.frete = frete;


        // Produtos
        // Criar a lista de produtos
        List<String> codigos = List.of("18191", "41857", "158151");
        List<String> unidades = List.of("METRO", "KG", "LITRO");
        produtos = new ArrayList<>();
        valorTotal = 0;
        for (int i = 0; i < codigos.size(); i++) {
            produtos.add(new Produto("Produto",
                    precos.get(i),
                    unidades.get(i),
                    codigos.get(i)));
            valorTotal += precos.get(i) + listaICMS.get(i) + listaMunicipal.get(i);
        }
        CategoriaDeCliente categoriaDeCliente = PADRAO;
        metodoDePagamento = DINHEIRO;
        String email = "email@gmail.com";
        cliente = new Cliente("João", categoriaDeCliente, estado, regiaoDoEstado, email);

        data = LocalDate.of(2024, 5, 31);
        venda = new Venda(cliente, produtos, metodoDePagamento, data);
        this.descontos = venda.getDesconto();

        notaFiscal = new NotaFiscal(venda);
        conteudoDaNotaFiscal = notaFiscal.emiteNotaFiscal();

        valorTotal += frete;
        valorFinal = valorTotal - descontos;


    }

    @Parameterized.Parameters(name = "{index} estado={0}, precos={1}, listaICMS={2}, listaMunicipal={3}, regiaoDoEstado={4}, frete={5}")
    public static Collection<Object[]> populaTestes() {
        List<Double> valores = List.of(50.0, 200.0, 100.0);

        List<Double> valoresDFICMS = List.of(9.0, 36.0, 18.0);
        List<Double> valoresICMS = List.of(6.0, 24.0, 12.0);

        List<Double> valoresMunicipal = List.of(2.0, 8.0, 4.0);
        List<Double> valoresMunicipalDF = List.of(0.0, 0.0, 0.0);

        return Arrays.asList(new Object[][]{
                {"DF", valores, valoresDFICMS, valoresMunicipalDF, CAPITAL, 5.0}, // ICMS 18% Mun 0%
                {"DF", valores, valoresDFICMS, valoresMunicipalDF, INTERIOR, 5.0},

                {"MT", valores, valoresICMS, valoresMunicipal, CAPITAL, 10.0}, // ICMS 12% Mun 4%
                {"MT", valores, valoresICMS, valoresMunicipal, INTERIOR, 13.0},

                {"SE", valores, valoresICMS, valoresMunicipal, CAPITAL, 15.0}, // ICMS 12% Mun 4%
                {"SE", valores, valoresICMS, valoresMunicipal, INTERIOR, 18.0},

                {"AM", valores, valoresICMS, valoresMunicipal, CAPITAL, 20.0}, // ICMS 12% Mun 4%
                {"AM", valores, valoresICMS, valoresMunicipal, INTERIOR, 25.0},

                {"SP", valores, valoresICMS, valoresMunicipal, CAPITAL, 7.0}, // ICMS 12% Mun 4%
                {"SP", valores, valoresICMS, valoresMunicipal, INTERIOR, 10.0},

                {"SC", valores, valoresICMS, valoresMunicipal, CAPITAL, 10.0}, // ICMS 12% Mun 4%
                {"SC", valores, valoresICMS, valoresMunicipal, INTERIOR, 13.0},
        });
    }

    @Test
    public void testCalcularValorGasto() {
        assertEquals(valorTotal - frete, notaFiscal.getValorGasto(), 0.001);
    }

    @Test
    public void testValorTotalComImpostosEFrete() {
        double total = 0;
        for (int i = 0; i < precos.size(); i++) {
            total += precos.get(i) + listaICMS.get(i) + listaMunicipal.get(i);
        }
        total += frete;
        assertEquals(total, valorTotal, 0.01);
    }

    @Test
    public void testListasDeImpostoICMS() {
        assertEquals(listaICMS, notaFiscal.getImpostosICMS());
    }

    @Test
    public void testListasDeImpostoMunicipal() {
        assertEquals(listaMunicipal, notaFiscal.getImpostosMunicipal());
    }

    @Test
    public void testValorFreteNaNotaFiscal() {
        assertEquals(frete, notaFiscal.getFrete(), 0.01);
    }

    @Test
    public void testDataNaNotaFiscal() {
        String texto = "Data.*" + data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        assertTrue(compile(texto).matcher(conteudoDaNotaFiscal).find());
    }

    @Test
    public void testFreteNaNotaFiscal() {
        String texto = "Frete.*" + frete;
        assertTrue(compile(texto).matcher(conteudoDaNotaFiscal).find());
    }

    @Test
    public void testMetodoDePagamentoNaNotaFiscal() {
        String texto = "Método de Pagamento.*" + metodoDePagamento;
        assertTrue(compile(texto).matcher(conteudoDaNotaFiscal).find());
    }

    @Test
    public void testValorTotalNaNotaFiscal() {
        String texto = "Total.*" + valorTotal;
        assertTrue(compile(texto).matcher(conteudoDaNotaFiscal).find());
    }

    @Test
    public void testValorFinalNaNotaFiscal() {
        String texto = "Valor Final.*" + valorFinal;
        assertTrue(compile(texto).matcher(conteudoDaNotaFiscal).find());
    }

    @Test
    public void testDescontosNaNotaFiscal() {
        String texto = "Descontos.*" + descontos;
        assertTrue(compile(texto).matcher(conteudoDaNotaFiscal).find());
    }

    @Test
    public void testDescricaoProdutosNaNotaFiscal() {
        for (Produto produto : produtos) {
            String texto = produto.getCodigo() + ".*" + produto.getDescricao();
            assertTrue(compile(texto).matcher(conteudoDaNotaFiscal).find());
        }
    }

    @Test
    public void testValorProdutosNaNotaFiscal() {
        for (Produto produto : produtos) {
            String texto = produto.getCodigo() + ".*" + produto.getValorDeVenda();
            assertTrue(compile(texto).matcher(conteudoDaNotaFiscal).find());
        }
    }

    @Test
    public void testImpostoProdutosNaNotaFiscal() {
        for (int i = 0; i < produtos.size(); i++) {
            String textoICMS = "ICMS.*" + listaICMS.get(i);
            String textoMunicipal = "Municipal.*" + listaMunicipal.get(i);
            assertTrue(compile(textoICMS).matcher(conteudoDaNotaFiscal).find());
            assertTrue(compile(textoMunicipal).matcher(conteudoDaNotaFiscal).find());
        }
    }


    @Test
    public void testCodigoProdutoNaNotaFiscal() {
        for (Produto produto : produtos) {
            assertTrue(compile(produto.getCodigo() + " *" + produto.getDescricao()).matcher(conteudoDaNotaFiscal).find());
        }
    }
}

