package br.unb.venda;

import br.unb.model.Database;
import br.unb.model.Venda;
import br.unb.service.CadastroDeVenda;
import br.unb.util.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ClienteTest {

    final String entrada;
    final boolean isValid;
    final Database db;
    final List<String> itens = TestUtils.getCodigosDeProdutosValidos();
    final CadastroDeVenda c = new CadastroDeVenda();
    public ClienteTest(String entrada, boolean isValid) {
        this.entrada = entrada;
        this.isValid = isValid;
        this.db = Database.getInstance();
    }

    @Before
    public void verificaConfiguracao(){
        // Assegura que há cliente cadastrado com o email
        if (isValid)
            assertNotNull(db.getClienteByEmail(entrada));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"email_nao_cadastrado@gmail.com", false},
                {"email1@domain.com", true}
        });
    }

    @Test
    public void testValoresClienteId(){
        if (! isValid) {
            assertThrows(IllegalArgumentException.class,
                    () ->
                            c.criaVenda(entrada, itens, "BOLETO", "2024-07-01" )
            );
        } else {
            Venda v = c.criaVenda(entrada, itens, "BOLETO", "2024-07-01" );
            assertEquals(v.email, entrada);
        }
    }

    @Test
    public void testValoresEmailCliente(){
        if (! isValid) {
            assertThrows(IllegalArgumentException.class,
                    () ->
                            c.criaVenda(entrada, itens , "BOLETO", "2024-07-01" )
            );
        } else {
            Venda v = c.criaVenda(entrada, itens , "BOLETO", "2024-07-01" );
            assertNotNull(v.getCliente());
            assertEquals(v.getCliente().email, entrada);
        }
    }

}
