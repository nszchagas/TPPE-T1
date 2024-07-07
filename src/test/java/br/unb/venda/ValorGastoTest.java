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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

import static br.unb.model.categorias.CategoriaDeCliente.PADRAO;
import static br.unb.model.categorias.MetodoDePagamento.DINHEIRO;
import static br.unb.model.categorias.RegiaoDoEstado.CAPITAL;
import static br.unb.model.categorias.RegiaoDoEstado.INTERIOR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class ValorGastoTest {

    List<Produto> produtos;
    Cliente cliente;
    List<Double> precos;
    List<Double> listaICMS;
    List<Double> listaMunicipal;
    String conteudoDaNotaFiscal;
    NotaFiscal notaFiscal;
    double frete;
    Venda venda;
    MetodoDePagamento metodoDePagamento;
    LocalDate data;

    public ValorGastoTest(
            String estado,
            List<Double> precos,
            List<Double> listaICMS,
            List<Double> listaMunicipal,
            RegiaoDoEstado regiaoDoEstado,
            double frete
    ) {
        produtos = new ArrayList<>();

        List<String> codigos = List.of("18191", "41857", "158151");
        List<String> unidades = List.of("METRO", "KG", "LITRO");

        for (int i = 0; i < codigos.size(); i++) {
            produtos.add(new Produto("Produto",
                    precos.get(i),
                    unidades.get(i),
                    codigos.get(i)));


        }
        // Cliente padrão não tem nenhum tipo de desconto.
        CategoriaDeCliente categoriaDeCliente = PADRAO;

        metodoDePagamento = DINHEIRO;

        String email = "email@gmail.com";

        cliente = new Cliente("Nome", categoriaDeCliente, estado, regiaoDoEstado, email);


        this.precos = precos;
        this.listaICMS = listaICMS;
        this.listaMunicipal = listaMunicipal;
        this.frete = frete;

        data = LocalDate.of(2024, 5, 31);

        venda = new Venda(cliente, produtos, metodoDePagamento, data);
        notaFiscal = new NotaFiscal(venda);

        // Setup Nota
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        conteudoDaNotaFiscal = outContent.toString();


    }

    @Parameterized.Parameters(name = "{index} estado={0}, precos={1}, listaICMS={2}, listaMunicipal={3},regiaoDoEstado={4},frete{5}")
    public static Collection<Object[]> populaTestes() {
        List<Double> valores = List.of(50.0, 200.0, 100.0);

        List<Double> valoresDFICMS = List.of(9.0, 36.0, 18.0);
        List<Double> valoresICMS = List.of(6.0, 24.0, 12.0);

        List<Double> valoresMunicipal = List.of(2.0, 8.0, 4.0);
        List<Double> valoresMunicipalDF = List.of(0.0, 0.0, 0.0);


        return Arrays.asList(new Object[][]{
                {"DF", valores, valoresDFICMS, valoresMunicipalDF, CAPITAL, 5.0}, // ICMS 18% Mun 0%
                {"DF", valores, valoresDFICMS, valoresMunicipalDF, INTERIOR, 5.0},

                {"MT", valores, valoresICMS, valoresMunicipal, CAPITAL, 10}, // ICMS 12% Mun 4%
                {"MT", valores, valoresICMS, valoresMunicipal, INTERIOR, 13},

                {"SE", valores, valoresICMS, valoresMunicipal, CAPITAL, 15}, // ICMS 12% Mun 4%
                {"SE", valores, valoresICMS, valoresMunicipal, INTERIOR, 18},

                {"AM", valores, valoresICMS, valoresMunicipal, CAPITAL, 20}, // ICMS 12% Mun 4%
                {"AM", valores, valoresICMS, valoresMunicipal, INTERIOR, 25},

                {"SP", valores, valoresICMS, valoresMunicipal, CAPITAL, 7}, // ICMS 12% Mun 4%
                {"SP", valores, valoresICMS, valoresMunicipal, INTERIOR, 10},

                {"SC", valores, valoresICMS, valoresMunicipal, CAPITAL, 10}, // ICMS 12% Mun 4%
                {"SC", valores, valoresICMS, valoresMunicipal, INTERIOR, 13},

        });
    }

    @Test
    public void testCalcularValorGasto() {

        double valorGasto = 0;
        for (Double v : precos)
            valorGasto += v;

        assertEquals(valorGasto, venda.getValorGasto(), 0.001);
    }

    // Imposto só depende do estado
    @Test
    public void testValorAPagarComImpostos() {
        double total = 0;
        for (int i = 0; i < produtos.size(); i++) {
            total += precos.get(i) + listaICMS.get(i) + listaMunicipal.get(i);
        }
        total += frete;
        assertEquals(total, notaFiscal.getTotal(), 0.01);

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
    public void testFreteNaNotaFiscal() {
        assertEquals(frete, notaFiscal.getFrete(), 0.01);
    }


    @Test
    @Ignore
    public void testEmiteNotaFiscal() {
        NotaFiscal.emiteNotaFiscal();

        // Verificar se a saída contém as informações corretas
        assertTrue(conteudoDaNotaFiscal.contains("Data: 31/05/2024"));
        assertTrue(conteudoDaNotaFiscal.contains("Descrição: Produto"));
        assertTrue(conteudoDaNotaFiscal.contains(String.format("Frete: %f", frete)));
        assertTrue(conteudoDaNotaFiscal.contains("Método de Pagamento: CARTAO_LOJA"));
        assertTrue(conteudoDaNotaFiscal.contains("Valor Final: R$20.9"));
        assertTrue(conteudoDaNotaFiscal.contains("Descontos: R$7.5"));
        assertTrue(conteudoDaNotaFiscal.contains("Valor Total: R$18.4"));
        for (int i = 0; i < precos.size(); i++) {
            assertTrue(conteudoDaNotaFiscal.contains("Valor: R$100.0"));
            assertTrue(conteudoDaNotaFiscal.contains("Imposto: R$1.59"));
            assertTrue(conteudoDaNotaFiscal.contains("Unidade: UNIDADE"));
            assertTrue(conteudoDaNotaFiscal.contains("Código: 12345678"));
            assertTrue(conteudoDaNotaFiscal.contains("Descrição: Caneta Esferográfica"));
            assertTrue(conteudoDaNotaFiscal.contains("Valor: R$2.5"));
            assertTrue(conteudoDaNotaFiscal.contains("Imposto: R$0.25"));
            assertTrue(conteudoDaNotaFiscal.contains("Unidade: UNIDADE"));
            assertTrue(conteudoDaNotaFiscal.contains("Código: 87654321"));
        }
    }

}

