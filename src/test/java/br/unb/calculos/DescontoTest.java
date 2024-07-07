package br.unb.calculos;

import br.unb.model.Cliente;
import br.unb.model.Produto;
import br.unb.model.Venda;
import br.unb.model.categorias.CategoriaDeCliente;
import br.unb.model.categorias.MetodoDePagamento;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static br.unb.model.categorias.CategoriaDeCliente.*;
import static br.unb.model.categorias.MetodoDePagamento.*;
import static br.unb.util.OperacoesFinanceiras.calculaDesconto;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class DescontoTest {

    double valorGasto, frete, descontoEsperado;
    CategoriaDeCliente categoriaDeCliente;
    MetodoDePagamento metodoDePagamento;

    public DescontoTest(
            double valorGasto,
            double frete,
            CategoriaDeCliente categoriaDeCliente,
            MetodoDePagamento metodoDePagamento,
            double descontoEsperado) {
        this.valorGasto = valorGasto;
        this.categoriaDeCliente = categoriaDeCliente;
        this.metodoDePagamento = metodoDePagamento;
        this.descontoEsperado = descontoEsperado;
        this.frete = frete;
    }

    @Parameterized.Parameters(name = "{index}: valorGasto={0},frete={1},categoriaCliente={2},metodoDePagamento={3},descontoEsperado={4}")
    public static Iterable<Object[]> data() {
        return Arrays.asList
                (new Object[][]{
                        {101.00, 5.00, ESPECIAL, BOLETO, 10.1},
                        {100.00, 5.00, ESPECIAL, CARTAO_LOJA, 19.5},
                        {200.00, 15.00, PADRAO, BOLETO, 0.0},
                        {200.00, 20.00, PADRAO, CARTAO_LOJA, 0.0},
                        {200.00, 0.0, PRIME, CARTAO_EXTERNO, 0.0},
                        {200.00, 0.0, PRIME, CARTAO_LOJA, 0.0}
                });

    }


    @Test
    public void testAtributo() {
        Cliente cliente = mock(Cliente.class);
        List<Produto> produtos = List.of(mock(Produto.class));
        when(cliente.getCategoria()).thenReturn(categoriaDeCliente);

        Venda venda = mock(Venda.class);


        when(venda.getCliente()).thenReturn(cliente);
        when(venda.getProdutos()).thenReturn(produtos);
        when(venda.getMetodoDePagamento()).thenReturn(metodoDePagamento);
        when(venda.getValorGasto()).thenReturn(valorGasto);
        when(venda.getDesconto()).thenCallRealMethod();


        assertEquals(descontoEsperado, venda.getDesconto(), 0.001);

    }

}

