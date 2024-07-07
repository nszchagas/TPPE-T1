package br.unb.venda;


import br.unb.model.Cliente;
import br.unb.model.Database;
import br.unb.model.Produto;
import br.unb.model.Venda;
import br.unb.model.categorias.CategoriaDeCliente;
import br.unb.model.categorias.Endereco;
import br.unb.model.categorias.MetodoDePagamento;
import br.unb.model.categorias.RegiaoDoEstado;
import br.unb.util.OperacoesFinanceiras;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static br.unb.model.categorias.CategoriaDeCliente.*;
import static br.unb.model.categorias.RegiaoDoEstado.CAPITAL;
import static br.unb.model.categorias.RegiaoDoEstado.INTERIOR;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class FreteTest {

    double valorEsperado;
    String estado;
    RegiaoDoEstado regiaoDoEstado;
    CategoriaDeCliente categoriaDeCliente;
    MetodoDePagamento metodoDePagamento = MetodoDePagamento.BOLETO;
    LocalDate data = LocalDate.of(2020, 1, 1);

    public FreteTest(
            double valorEsperado,
            String estado,
            RegiaoDoEstado regiaoDoEstado,
            CategoriaDeCliente categoriaDeCliente) {
        this.valorEsperado = valorEsperado;
        this.estado = estado;
        this.regiaoDoEstado = regiaoDoEstado;
        this.categoriaDeCliente = categoriaDeCliente;
    }

    @BeforeClass
    public static void setUpClass() {
        Database db = Database.getInstance();
        List<Produto> produtos = List.of(
                new Produto("Caderno Espiral", 15.90, "UNIDADE", "12345678"),
                new Produto("Caneta Esferográfica", 2.50, "UNIDADE", "87654321"),
                new Produto("Mochila Escolar", 99.90, "UNIDADE", "11223344"),
                new Produto("Calculadora Científica", 120.75, "UNIDADE", "44332211"),
                new Produto("Lápis de Cor", 25.60, "UNIDADE", "55667788")
        );
    }

    @Parameterized.Parameters(name = "{index}:valorEsperado={0},estado={1},regiaoDoEstado={2},categoriaDeCliente={3}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {5.00f, "DF", CAPITAL, PADRAO},
                {7.00f, "SP", CAPITAL, PADRAO},
                {10.00f, "GO", CAPITAL, PADRAO},
                {15.00f, "BA", CAPITAL, PADRAO},
                {20.00f, "AM", CAPITAL, PADRAO},
                {13.00f, "GO", INTERIOR, PADRAO},
                {18.00f, "BA", INTERIOR, PADRAO},
                {25.00f, "AM", INTERIOR, PADRAO},
                {3.50f, "DF", CAPITAL, ESPECIAL},
                {4.90f, "SP", CAPITAL, ESPECIAL},
                {7.00f, "GO", CAPITAL, ESPECIAL},
                {10.50f, "BA", CAPITAL, ESPECIAL},
                {14.00f, "AM", CAPITAL, ESPECIAL},
                {9.10f, "GO", INTERIOR, ESPECIAL},
                {12.60f, "BA", INTERIOR, ESPECIAL},
                {17.50f, "AM", INTERIOR, ESPECIAL},
                {0.00f, "DF", CAPITAL, PRIME},
                {0.00f, "SP", CAPITAL, PRIME},
                {0.00f, "GO", CAPITAL, PRIME},
                {0.00f, "BA", CAPITAL, PRIME},
                {0.00f, "AM", CAPITAL, PRIME},
                {0.00f, "GO", INTERIOR, PRIME},
                {0.00f, "BA", INTERIOR, PRIME},
                {0.00f, "AM", INTERIOR, PRIME}
        });
    }

    @Test
    public void testeValores() {
        assertEquals(valorEsperado, OperacoesFinanceiras.calculaFrete(estado, regiaoDoEstado, categoriaDeCliente), 0.05);
    }

    @Test
    public void testeFreteCorreto() {
        Cliente cliente = mock(Cliente.class);
        List<Produto> produtos = List.of(mock(Produto.class));

        when(cliente.getEstado()).thenReturn(estado);
        when(cliente.getRegiao()).thenReturn(regiaoDoEstado);
        when(cliente.getCategoria()).thenReturn(categoriaDeCliente);

        Venda venda = new Venda(cliente, produtos, metodoDePagamento, data);

        assertEquals(valorEsperado, venda.getFrete(), 0.1);

    }


}
