package br.unb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CadastroDeClienteTest {

    private final String tipoInserido;
    private final String tipoEsperado;

    public CadastroDeClienteTest(String tipoInserido, String tipoEsperado){
        this.tipoInserido = tipoInserido;
        this.tipoEsperado = tipoEsperado;
    }
    @Test
    public void testCriarClienteComCategoriaInvalidaDeveLancarExcecao() {
        CadastroDeCliente c = new CadastroDeCliente();
        try {
            c.cadastraCliente("José", "Capital", "SP", "tipoinvalido");
            fail("Deveria lançar exceção.");
        } catch (IllegalArgumentException e) {
            assertEquals("Categoria inválida: tipoinvalido.", e.getMessage());
        } 
        
    }
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][] {
                        {"PaDraO","padrao"},
                        {" especial","especial"},
                        {"PriME","prime"},
                        {" PADRAO","padrao"},
                        {"Padrao","padrao"}
                });

    }
    @Test
    public void testCategoriaValidaCaseInsensitive(){
        CadastroDeCliente c = new CadastroDeCliente();
        try {
            Cliente j = c.cadastraCliente("José", "Capital", "SP", tipoInserido);
            assertEquals(j.categoria, tipoEsperado);
        } catch (IllegalArgumentException e){
            fail("Não deveria ter lançado exceção.");
        }
    }

}
