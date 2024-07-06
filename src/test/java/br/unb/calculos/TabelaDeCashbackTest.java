package br.unb.calculos;

import br.unb.model.categorias.CategoriaDeCliente;
import br.unb.model.categorias.MetodoDePagamento;
import br.unb.util.OperacoesFinanceiras;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import static br.unb.model.categorias.CategoriaDeCliente.*;
import static br.unb.model.categorias.MetodoDePagamento.*;



@RunWith(Parameterized.class)
public class TabelaDeCashbackTest {


    CategoriaDeCliente cliente;
    MetodoDePagamento pagamento;
    double valorTotal, cashbackEsperado;

    public TabelaDeCashbackTest(CategoriaDeCliente cliente, MetodoDePagamento pagamento,double valorTotal, double cashbackEsperado)
    {
        this.cliente = cliente;
        this.pagamento = pagamento;
        this.cashbackEsperado=cashbackEsperado;
        this.valorTotal=valorTotal;

    }
    @Parameterized.Parameters(name="{index} cliente={0},pagamento={1},valorTotal={2},cashbackEsperado={3}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                // Cliente Prime com cartão externo
                {PRIME, CARTAO_EXTERNO, 1 ,0.03 },
                {PRIME, BOLETO, 50, 1.5 },
                {PRIME, PIX, 1 ,0.03 },
                {PRIME, DINHEIRO, 50, 1.5 },

                // Cliente Prime com cartão da loja
                {PRIME, CARTAO_LOJA, 1 ,0.05 },
                {PRIME, CARTAO_LOJA, 50, 2.5 },

                // Outros tipos de clientes com pagamento (X)
                {PADRAO, CARTAO_LOJA, 100 ,0 },
                {ESPECIAL, BOLETO, 590, 0 },

        });
    }


    @Test
    public void testaValores(){
        assertEquals(cashbackEsperado,
                OperacoesFinanceiras.calculaCashback(cliente, pagamento, valorTotal), 0.01);
    }


}
