package br.unb.venda;

import br.unb.model.Cliente;
import br.unb.model.Database;
import br.unb.model.Produto;
import br.unb.model.Venda;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.MockedStatic;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static br.unb.service.Cadastro.criaVenda;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@RunWith(Parameterized.class)
public class ProdutosTest {

    final List<String> entrada;
    final boolean isValid;
    String emailValido = "email1@domain.com", metodoDePagamento = "BOLETO", data = "2024-07-01";
    List<String> idsValidos = List.of("123", "456", "789", "101", "202");

    public ProdutosTest(List<String> entrada, boolean isValid) {
        this.entrada = entrada;
        this.isValid = isValid;
    }

    @Parameterized.Parameters(name = "{index}:entrada={0},isValid={1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {List.of(), false},
                {List.of("1", "2", "3"), false},
                {List.of("123", "456", "3"), false},
                {List.of("123", "456"), true},
                {List.of("456", "456"), true},
        });
    }


    @Test
    public void testItensId() {
        try (MockedStatic<Database> mockedStatic = mockStatic(Database.class)) {
            Database db = mock(Database.class);
            when(db.getClienteByEmail(emailValido)).thenReturn(mock(Cliente.class));
            for (String idValido : idsValidos) {

                when(db.getProdutoByCodigo(idValido)).thenReturn(new Produto("Descricao valida", 15.6, "M", idValido));

            }
            mockedStatic.when(Database::getInstance).thenReturn(db);
            if (!isValid) {
                assertThrows(IllegalArgumentException.class,
                        () ->
                                criaVenda(emailValido, entrada, metodoDePagamento, data)
                );
            } else {
                Venda vendaRegistrada = criaVenda(emailValido, entrada, metodoDePagamento, data);
                List<Produto> produtosRegistrados = vendaRegistrada.getProdutos();
                HashSet<String> idsEntrada = new HashSet<>(entrada);
                HashSet<String> idsFromProdutos = new HashSet<>();
                for (Produto produto : produtosRegistrados) {
                    idsFromProdutos.add(produto.codigo);
                }

                assertEquals(idsEntrada, new HashSet<>(vendaRegistrada.getCodProdutos()));
                assertEquals(entrada.size(), produtosRegistrados.size());
                assertEquals(idsEntrada, idsFromProdutos);
            }
        }
    }

}
