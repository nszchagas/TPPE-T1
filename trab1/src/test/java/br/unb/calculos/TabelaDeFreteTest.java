package br.unb.calculos;

import br.unb.util.TabelaDeFrete;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TabelaDeFreteTest {

    String estado, regiao;
    float valorEsperado;

    public TabelaDeFreteTest(String estado, String regiao, float valorEsperado) {
        this.estado = estado;
        this.regiao = regiao;
        this.valorEsperado = valorEsperado;
    }

    @Parameterized.Parameters(name = "{index} estado={0},regiao={1},valorEsperado={2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"DF", "Capital", 5},
                {"GO", "Capital", 10},
                {"GO", "Interior", 13},
                {"MT", "Capital", 10},
                {"MT", "Interior", 13},
                {"MS", "Capital", 10},
                {"MS", "Interior", 13},
                {"AL", "Capital", 15},
                {"AL", "Interior", 18},
                {"BA", "Capital", 15},
                {"BA", "Interior", 18},
                {"CE", "Capital", 15},
                {"CE", "Interior", 18},
                {"MA", "Capital", 15},
                {"MA", "Interior", 18},
                {"PB", "Capital", 15},
                {"PB", "Interior", 18},
                {"PE", "Capital", 15},
                {"PE", "Interior", 18},
                {"PI", "Capital", 15},
                {"PI", "Interior", 18},
                {"RN", "Capital", 15},
                {"RN", "Interior", 18},
                {"SE", "Capital", 15},
                {"SE", "Interior", 18},
                {"AC", "Capital", 20},
                {"AC", "Interior", 25},
                {"AP", "Capital", 20},
                {"AP", "Interior", 25},
                {"AM", "Capital", 20},
                {"AM", "Interior", 25},
                {"PA", "Capital", 20},
                {"PA", "Interior", 25},
                {"RO", "Capital", 20},
                {"RO", "Interior", 25},
                {"RR", "Capital", 20},
                {"RR", "Interior", 25},
                {"TO", "Capital", 20},
                {"TO", "Interior", 25},
                {"ES", "Capital", 7},
                {"ES", "Interior", 10},
                {"MG", "Capital", 7},
                {"MG", "Interior", 10},
                {"RJ", "Capital", 7},
                {"RJ", "Interior", 10},
                {"SP", "Capital", 7},
                {"SP", "Interior", 10},
                {"PR", "Capital", 10},
                {"PR", "Interior", 13},
                {"RS", "Capital", 10},
                {"RS", "Interior", 13},
                {"SC", "Capital", 10},
                {"SC", "Interior", 13},
                {"XX", "Capital", -1},
                {"XX", "Interior", -1}
        });
    }

    @Test
    public void testaValorCorreto() {
        float freteCalculado = TabelaDeFrete.calcula(estado, regiao);
        assert valorEsperado == freteCalculado;

    }


}
