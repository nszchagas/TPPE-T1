package br.unb.cliente;

import br.unb.model.Cliente;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static br.unb.service.Cadastro.criaCliente;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class EmailTest {

    private final String entrada;
    private final Class <?extends Throwable> excecaoEsperada;
    public EmailTest(String entrada, Class <?extends Throwable> excecaoEsperada){
        this.entrada = entrada;
        this.excecaoEsperada = excecaoEsperada;
    }

    @Parameterized.Parameters(name="{index} entrada={0},excecaoEsperada={1}.")
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][] {
                        {"invalid.email@", IllegalArgumentException.class},
                        {"@invalid.com", IllegalArgumentException.class},
                        {"invalid@.com", IllegalArgumentException.class},
                        {"invalid@domain", IllegalArgumentException.class},
                        {"invalid email@domain.com", IllegalArgumentException.class},
                        {"invalid@domain,com", IllegalArgumentException.class},
                        {"invalid@domain..com", IllegalArgumentException.class},
                        {"invalid@domain.c", IllegalArgumentException.class},
                        {"invalid@domain@another.com", IllegalArgumentException.class},
                        {"invalid@-domain.com", IllegalArgumentException.class},
                        {".invalid@domain.com", IllegalArgumentException.class},
                        {"invalid@ domain.com", IllegalArgumentException.class},
                        {"invalid@domain .com", IllegalArgumentException.class},
                        {"inva lid@domain.com", IllegalArgumentException.class},
                        {"invalid@domain.com,", IllegalArgumentException.class},
                        {"invalid@domain.com;", IllegalArgumentException.class},
                        {"invalid@domain.com:", IllegalArgumentException.class},
                        {"invalid@domain.com/", IllegalArgumentException.class},
                        {"invalid@domain.c\\om", IllegalArgumentException.class},
                        {"invalid@domain.com[", IllegalArgumentException.class},
                        {"invalid@domain.com]", IllegalArgumentException.class},
                        {"invalid@domain.com(", IllegalArgumentException.class},
                        {"invalid@domain.com)", IllegalArgumentException.class},
                        {"invalid@domain.com<", IllegalArgumentException.class},
                        {"invalid@domain.com>", IllegalArgumentException.class},
                        {"invalid@domain.com{", IllegalArgumentException.class},
                        {"invalid@domain.com}", IllegalArgumentException.class},
                        {"invalid@domain.com|", IllegalArgumentException.class},
                        {"invalid@domain.com^", IllegalArgumentException.class},
                        {"invalid@domain.com&", IllegalArgumentException.class},
                        {"invalid@domain.com*", IllegalArgumentException.class},
                        {"invalid@domain.com$", IllegalArgumentException.class},
                        {"invalid@domain.com#", IllegalArgumentException.class},
                        {"invalid@domain.com!", IllegalArgumentException.class},
                        {"invalid@domain.com~", IllegalArgumentException.class},
                        {"valid@domail.com", null}
                });

    }
    @Test
    public void testaValores(){
        if (excecaoEsperada == null ){
            Cliente j = criaCliente("José", "Capital", "SP", "PADRAO", entrada);
            assertEquals(j.email, entrada);

        } else {
            Throwable e = assertThrows(excecaoEsperada,
                    () -> criaCliente("José", "Capital", "SP","PADRAO", entrada));
            assertTrue(e.getMessage().contains(entrada));
        }
    }
}
