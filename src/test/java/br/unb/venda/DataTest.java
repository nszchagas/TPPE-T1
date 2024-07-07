package br.unb.venda;

import br.unb.model.Cliente;
import br.unb.model.Database;
import br.unb.model.Produto;
import br.unb.model.Venda;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static br.unb.model.categorias.MetodoDePagamento.PIX;
import static br.unb.service.VendaService.criaVenda;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class DataTest {

    final String entrada;
    final Object saidaEsperada;
    final List<String> itens;


    public DataTest(String entrada, Object saidaEsperada) {
        this.entrada = entrada;
        this.saidaEsperada = saidaEsperada;
        this.itens = List.of("123");
    }


    @Parameterized.Parameters(name = "{index} entrada={0}, saidaEsperada={1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"", null},
                {" ", null},
                // Formato inválido
                {"10/12/2023", null},
                // Data que não chegou
                {"2024-12-05", null},
                // Dia que não existe
                {"2023-02-29", null},
                // Data válida
                {"2024-01-15", LocalDate.of(2024, 1, 15)}
        });
    }

    @Test()
    public void testeValores() {

        try (MockedStatic<Database> mockedStatic = mockStatic(Database.class)) {
            Database db = mock(Database.class);
            when(db.getClienteByEmail("email1@domain.com")).thenReturn(mock(Cliente.class));
            when(db.getProdutoByCodigo("123")).thenReturn(mock(Produto.class));

            mockedStatic.when(Database::getInstance).thenReturn(db);


            if (saidaEsperada == null) {
                assertThrows(IllegalArgumentException.class, () ->
                        criaVenda("email1@domain.com", itens, PIX, entrada));
            } else {

                Venda v = criaVenda("email1@domain.com", itens, PIX, entrada);
                assertEquals(v.getData(), saidaEsperada);
            }
        }
    }
}
