package br.unb.venda;

import br.unb.model.Cliente;
import br.unb.model.Database;
import br.unb.model.Venda;
import br.unb.service.CadastroDeCliente;
import br.unb.service.CadastroDeVenda;
import br.unb.util.TestUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ClienteTest {

    String entrada;
    boolean isValid;
    Database db;
    public ClienteTest(String entrada, boolean isValid) {
        this.entrada = entrada;
        this.isValid = isValid;
        this.db = Database.getInstance();
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
        // Assegura que há clientes cadastrados
        assertEquals(db.getQtdClientes(), TestUtils.getClientesValidos().size());
        CadastroDeVenda c = new CadastroDeVenda();
        if (! isValid) {
            assertThrows(IllegalArgumentException.class,
                    () ->
                            c.criaVenda(entrada, new String[]{"123", "144"} , "BOLETO", "2024-07-01" )
            );
        } else {
            Venda v = c.criaVenda(entrada, new String[]{"123", "144"} , "BOLETO", "2024-07-01" );
            assertEquals(v.email, entrada);

        }

    }

    @Test
    public void testValoresEmailCliente(){
        // Assegura que há clientes cadastrados
        assertEquals(db.getQtdClientes(), TestUtils.getClientesValidos().size());

        CadastroDeVenda c = new CadastroDeVenda();

        if (! isValid) {
            assertThrows(IllegalArgumentException.class,
                    () ->
                            c.criaVenda(entrada, new String[]{"123", "144"} , "BOLETO", "2024-07-01" )
            );
        } else {
            Venda v = c.criaVenda(entrada, new String[]{"123", "144"} , "BOLETO", "2024-07-01" );
            assertNotNull(v.getCliente());
            assertEquals(v.getCliente().email, entrada);
        }
    }

}
