package br.unb.calculos;

import br.unb.model.categorias.RegiaoDoEstado;
import br.unb.util.OperacoesFinanceiras;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static br.unb.model.categorias.RegiaoDoEstado.CAPITAL;
import static br.unb.model.categorias.RegiaoDoEstado.INTERIOR;

@RunWith(Parameterized.class)
public class TabelaDeFreteTest {

    final String estado;
    final RegiaoDoEstado regiao;
    final double valorEsperado;

    public TabelaDeFreteTest(String estado, RegiaoDoEstado regiao, double valorEsperado) {
        this.estado = estado;
        this.regiao = regiao;
        this.valorEsperado = valorEsperado;
    }

    @Parameterized.Parameters(name = "{index} estado={0},regiao={1},valorEsperado={2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"DF", CAPITAL, 5},
                {"GO", CAPITAL, 10},
                {"GO", INTERIOR, 13},
                {"MT", CAPITAL, 10},
                {"MT", INTERIOR, 13},
                {"MS", CAPITAL, 10},
                {"MS", INTERIOR, 13},
                {"AL", CAPITAL, 15},
                {"AL", INTERIOR, 18},
                {"BA", CAPITAL, 15},
                {"BA", INTERIOR, 18},
                {"CE", CAPITAL, 15},
                {"CE", INTERIOR, 18},
                {"MA", CAPITAL, 15},
                {"MA", INTERIOR, 18},
                {"PB", CAPITAL, 15},
                {"PB", INTERIOR, 18},
                {"PE", CAPITAL, 15},
                {"PE", INTERIOR, 18},
                {"PI", CAPITAL, 15},
                {"PI", INTERIOR, 18},
                {"RN", CAPITAL, 15},
                {"RN", INTERIOR, 18},
                {"SE", CAPITAL, 15},
                {"SE", INTERIOR, 18},
                {"AC", CAPITAL, 20},
                {"AC", INTERIOR, 25},
                {"AP", CAPITAL, 20},
                {"AP", INTERIOR, 25},
                {"AM", CAPITAL, 20},
                {"AM", INTERIOR, 25},
                {"PA", CAPITAL, 20},
                {"PA", INTERIOR, 25},
                {"RO", CAPITAL, 20},
                {"RO", INTERIOR, 25},
                {"RR", CAPITAL, 20},
                {"RR", INTERIOR, 25},
                {"TO", CAPITAL, 20},
                {"TO", INTERIOR, 25},
                {"ES", CAPITAL, 7},
                {"ES", INTERIOR, 10},
                {"MG", CAPITAL, 7},
                {"MG", INTERIOR, 10},
                {"RJ", CAPITAL, 7},
                {"RJ", INTERIOR, 10},
                {"SP", CAPITAL, 7},
                {"SP", INTERIOR, 10},
                {"PR", CAPITAL, 10},
                {"PR", INTERIOR, 13},
                {"RS", CAPITAL, 10},
                {"RS", INTERIOR, 13},
                {"SC", CAPITAL, 10},
                {"SC", INTERIOR, 13},
                {"XX", CAPITAL, -1},
                {"XX", INTERIOR, -1}
        });
    }

    @Test
    public void testaValorCorreto() {
        double freteCalculado = OperacoesFinanceiras.calculaFrete(estado, regiao);
        assert valorEsperado == freteCalculado;

    }


}
