package br.unb.cadastro;

import br.unb.CadastroDeCliente;
import br.unb.Cliente;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class TipoDeClienteTest {

    private final String entrada;
    private final String saida;
    private final Class <?extends Throwable> excecaoEsperada;
    CadastroDeCliente c = new CadastroDeCliente();
    public TipoDeClienteTest(String entrada, String saida, Class <?extends Throwable> excecaoEsperada){
        this.entrada = entrada;
        this.saida = saida;
        this.excecaoEsperada = excecaoEsperada;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][] {
                        {"PaDraO","PADRAO", null},
                        {" especial","ESPECIAL", null},
                        {"PriME","PRIME", null},
                        {" PADRAO","PADRAO", null},
                        {"Padrao","PADRAO", null},
                        {"Tipo invalido", "Categoria inválida: \"Tipo invalido\".", IllegalArgumentException.class}
                });

    }
    @Test
    public void testaValores(){
        if (excecaoEsperada == null ){
            Cliente j = c.cadastraCliente("José", "Capital", "SP", entrada);
            assertEquals(j.categoria, saida);

        } else {
            Throwable e = assertThrows(excecaoEsperada, () -> c.cadastraCliente("José", "Capital", "SP", entrada));
            assertEquals(e.getMessage(), saida);

        }

    }

}
