package br.unb.venda;

import br.unb.model.Cliente;
import br.unb.model.Database;
import br.unb.model.Produto;
import br.unb.model.Venda;
import br.unb.service.Cadastro;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.MockedStatic;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static br.unb.service.Cadastro.criaVenda;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class ClienteTest {

    final String entrada;
    final boolean isValid;

    final List<String> itens = List.of("123");
    Venda vendaCriada;
    Cliente clienteCriado;

    public ClienteTest(String entrada, boolean isValid) {
        this.entrada = entrada;
        this.isValid = isValid;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"email_nao_cadastrado@gmail.com", false},
                {"email1@domain.com", true}
        });
    }

    @Before
    public void setUp() {

        try (MockedStatic<Database> mockedStatic = mockStatic(Database.class)) {
            Database db = mock(Database.class);
            String emailValido = "email1@domain.com";
            when(db.getClienteByEmail(emailValido)).thenReturn(new Cliente("Nome", "Padrao", "BA", "Capital", emailValido));
            for (String codigoValido : itens)
                when(db.getProdutoByCodigo(codigoValido)).thenReturn(mock(Produto.class));
            mockedStatic.when(Database::getInstance).thenReturn(db);

            if (isValid){
                vendaCriada = criaVenda(entrada, itens, "BOLETO", "2024-07-01");
                clienteCriado = vendaCriada.getCliente();
                assertNotNull(clienteCriado);
            }


        }

    }

    @Test
    public void testValoresClienteId() {
        if (!isValid) {
            assertThrows(IllegalArgumentException.class,
                    () ->
                            criaVenda(entrada, itens, "BOLETO", "2024-07-01")
            );
        } else {
            assertEquals(vendaCriada.email, entrada);
        }
    }

    @Test
    public void testValoresEmailCliente() {
        if (!isValid) {
            assertThrows(IllegalArgumentException.class,
                    () ->
                            criaVenda(entrada, itens, "BOLETO", "2024-07-01")
            );
        } else {
            assertEquals(clienteCriado.email, entrada);
        }
    }

}
