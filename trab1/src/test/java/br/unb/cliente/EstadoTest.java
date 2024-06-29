package br.unb.cliente;

import br.unb.service.CadastroDeCliente;
import br.unb.model.Cliente;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(Parameterized.class)
public class EstadoTest {
    String entrada, saidaEsperada;
    CadastroDeCliente c = new CadastroDeCliente();
    Class <?extends Throwable> excecaoEsperada;

    public EstadoTest(String entrada, String saidaEsperada, Class<? extends  Throwable> excecaoEsperada) {
        this.entrada = entrada;
        this.saidaEsperada = saidaEsperada;
        this.excecaoEsperada = excecaoEsperada;
    }
    @Parameterized.Parameters(name="{index}: Estado {0} deve sair como {1} (Excecao: {2}).")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"sp", "SP",null},
                {"df", "DF",null},
                {" pr", "PR",null},
                {"BA ", "BA",null},
                {"São Paulo",  "Insira a sigla do estado.",IllegalArgumentException.class,},
                {"ZS", "Estado inválido: ZS.",IllegalArgumentException.class,}
        });
    }
    @Test
    public void testaValores(){
        if (excecaoEsperada == null ){
            Cliente a = c.cadastraCliente("José", "interior", entrada, "padrao");
            assertEquals(a.estado, saidaEsperada);
        } else {
            Throwable e = assertThrows(excecaoEsperada,
                () ->   c.cadastraCliente("José", "interior", entrada, "padrao")
        );
        assertEquals(e.getMessage(), saidaEsperada);
        }
    }

}
