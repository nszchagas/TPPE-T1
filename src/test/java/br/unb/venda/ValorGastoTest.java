package br.unb.venda;

import br.unb.model.Cliente;
import br.unb.model.Produto;
import br.unb.model.Venda;
import br.unb.model.categorias.MetodoDePagamento;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValorGastoTest {

    @Test
    public void testCalcularValorGasto() {
        List<Produto> produtos = new ArrayList<>();
        List<Double> valores = List.of(
            338.98, 66.73, 179.23, 197.11, 426.96, 158.27, 303.37, 320.89, 73.99, 344.16
        );
        double totalEsperado = 0;
        for (Double valor : valores) {
            Produto produto = mock(Produto.class);
            when(produto.getValorDeVenda()).thenReturn(valor);
            totalEsperado += valor;
            produtos.add(produto);
        }
        Venda venda = new Venda(mock(Cliente.class), produtos, mock(MetodoDePagamento.class), mock(LocalDate.class));
        assertEquals(totalEsperado, venda.getValorGasto(), 0.001);
    }

    // Imposto só depende do estado
    @Test
    public void testValorAPagarComImpostos() {
        List<String> estados = List.of("AM", "SE", "DF", "MT", "SP", "SC");


    }

}
