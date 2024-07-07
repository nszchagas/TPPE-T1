package br.unb.cliente;

import br.unb.model.Cliente;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static br.unb.service.Cadastro.cadastraCliente;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CategoriaTest {

    private final String entrada;
    private final String saida;
    private final Class <?extends Throwable> excecaoEsperada;
    public CategoriaTest(String entrada, String saida, Class <?extends Throwable> excecaoEsperada){
        this.entrada = entrada;
        this.saida = saida;
        this.excecaoEsperada = excecaoEsperada;
    }

    @Parameterized.Parameters(name="{index} Entrada: {0}, saída esperada: {1}, exceção esperada: {2}.")
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
            Cliente j = cadastraCliente("José", "Capital", "SP", entrada, "jose@gmail.com");
            assertEquals(j.categoria, saida);

        } else {
            Throwable e = assertThrows(excecaoEsperada, () -> cadastraCliente("José", "Capital", "SP", entrada, "jose@gmail.com"));
            assertEquals(e.getMessage(), saida);

        }

    }

}
