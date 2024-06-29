package br.unb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CadastroClienteEstadoUpperCaseTest {
    String entrada, saidaEsperada;
    CadastroDeCliente c = new CadastroDeCliente();

    public CadastroClienteEstadoUpperCaseTest(String entrada, String saidaEsperada) {
        this.entrada = entrada;
        this.saidaEsperada = saidaEsperada;
    }
    @Parameterized.Parameters(name="{index}: Estado {0} deve sair como {1}.")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"sp", "SP"},
                {"df", "DF"},
                {" pr", "PR"},
                {"BA ", "BA"}
        });
    }
    @Test
    public void testaValores(){
        Cliente a = c.cadastraCliente("José", "interior", entrada, "padrao");
        assertEquals(a.estado, saidaEsperada);
    }

}
