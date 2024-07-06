package br.unb.calculos;

import br.unb.util.OperacoesFinanceiras;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ImpostoTest {

    String tipo, estado; double valor, impostoEsperado;

    public ImpostoTest(String tipo, String estado, double valor, double impostoEsperado) {
        this.tipo = tipo;
        this.estado = estado;
        this.valor = valor;
        this.impostoEsperado = impostoEsperado;
    }
    @Parameterized.Parameters(name="{0} tipo={0},estado={1},valor={2},impostoEsperado={3}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                // Fora do DF
                {"ICMS", "BA", 125, 15 },
                {"MUNICIPAL", "SE", 258.5, 10.34 },

                // No DF
                {"ICMS", "DF", 125, 22.5},
                {"MUNICIPAL", "DF", 258.5, 0}
        });
    }

    @Test
    public void testaCalculoDeImposto(){
        assertEquals(impostoEsperado, OperacoesFinanceiras.calculaImposto(tipo, estado, valor), 0.01);
    }


}
