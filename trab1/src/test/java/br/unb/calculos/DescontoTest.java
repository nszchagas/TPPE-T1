package br.unb.calculos;

import br.unb.model.categorias.CategoriaDeCliente;
import br.unb.util.TabelaDeDesconto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static br.unb.model.categorias.CategoriaDeCliente.*;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class DescontoTest {

    double valorGasto, frete, descontoEsperado;
    CategoriaDeCliente categoriaDeCliente;
    String metodoDePagamento;

    public DescontoTest(double valorGasto, double frete, CategoriaDeCliente categoriaDeCliente, String metodoDePagamento, double descontoEsperado) {
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
                        // Especial sem cartão loja = (100) * 0.1 = 10
                        {100.00,  5.00, ESPECIAL, "BOLETO"     , 10.0},
                        // Especial com cartão =  105 - (((100 * 0.9) + 5 ) * 0.9)
                        {100.00, 5.00, ESPECIAL, "CARTAO_LOJA" , 19.5},
                        {200.00, 15.00, PADRAO, "BOLETO"      ,0},
                        {200.00, 20.00, PADRAO, "CARTAO_LOJA" ,0},
                        {200.00, 0, PRIME,    "CARTAO_EXTERNO"  ,0},
                        {200.00, 0, PRIME,    "CARTAO_LOJA"     ,0}
                });

    }
    @Test
    public void testDesconto() {
        assertEquals(descontoEsperado, TabelaDeDesconto.aplicaDesconto(valorGasto, frete, categoriaDeCliente, metodoDePagamento), 0.01);
    }

}

