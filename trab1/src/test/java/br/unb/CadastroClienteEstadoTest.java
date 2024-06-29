package br.unb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(Parameterized.class)
public class CadastroClienteEstadoTest {
    String estadoEntrada, mensagemEsperada;
    Class <? extends Throwable> excecaoEsperada;
    CadastroDeCliente c = new CadastroDeCliente();

    public CadastroClienteEstadoTest(String i, Class<? extends Throwable> e, String o){
        this.estadoEntrada = i;
        this.excecaoEsperada = e;
        this.mensagemEsperada = o;
    }

    @Parameterized.Parameters(name = "{index}: Estado {0} deve lancar {1} com mensagem {2}.")
    public static Collection<Object[]> data(){
        return Arrays.asList( new Object[][] {
                {"São Paulo", IllegalArgumentException.class, "Insira a sigla do estado."},
                {"ZS", IllegalArgumentException.class, "Estado inválido: ZS."}
        });
    }

    @Test
    public void estadoInvalidoDeveGerarExcecao() {
        Throwable e = assertThrows(excecaoEsperada,
                () ->   c.cadastraCliente("José", "interior", estadoEntrada, "padrao")
        );
        assertEquals(e.getMessage(), mensagemEsperada);
    }

}
