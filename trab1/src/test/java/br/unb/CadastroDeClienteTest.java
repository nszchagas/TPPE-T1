package br.unb;

import org.junit.Test;
import static org.junit.Assert.*;

public class CadastroDeClienteTest {

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
//    Parametrizar
    @Test
    public void testCategoriaValidaCaseInsensitive(){
        CadastroDeCliente c = new CadastroDeCliente();
        try {
            Cliente j = c.cadastraCliente("José", "Capital", "SP", "Padrao");
            assertEquals(j.categoria, "padrao");
        } catch (IllegalArgumentException e){
            fail("Não deveria ter lançado exceção.");
        }
    }

}
