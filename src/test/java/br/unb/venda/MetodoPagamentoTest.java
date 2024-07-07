package br.unb.venda;

import br.unb.model.Cliente;
import br.unb.model.Database;
import br.unb.model.Produto;
import br.unb.model.Venda;
import br.unb.model.categorias.MetodoDePagamento;
import br.unb.service.Cadastro;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.MockedStatic;

import java.util.*;

import static br.unb.model.categorias.MetodoDePagamento.*;
import static br.unb.service.Cadastro.criaVenda;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class MetodoPagamentoTest {

    // Se for com cartão de crédito, pegar também o número
    // Inserir cartao loja ou outro cartao, não precisa guardar o número do cartão
    // Se for boleto, pix ou dinheiro não precisa pegar nada
    // Validar tamanho
    // Validar caracteres numéricos
    // Validar verificação loja/externo
    // 4296 13XX XXXX XXXX

    final String entrada;
    final String numero;
    final MetodoDePagamento saidaEsperada;
    Venda vendaCadastrada;

    String emailValido = "email1@domain.com";
    String dataValida = "2023-04-12";
    List<String> idProdutosValidos = List.of("123");

    public MetodoPagamentoTest(String entrada, String numero, MetodoDePagamento saidaEsperada) {
        this.entrada = entrada;
        this.numero = numero;
        this.saidaEsperada = saidaEsperada;
    }

    @Parameterized.Parameters(name = "{index}: entrada={0}, numero={1}, saidaEsperada={2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"CARTAO", "4296 1370 0000 0000", CARTAO_LOJA},
                {"CARTÃO", "5296 1370 0000 0001", CARTAO_EXTERNO},
                {"CARTAO", "4296137000000001", null},
                {"CARTAO", "4296 13XX XXXX XXXX", null},
                {"CARTAO", "5296 13XX XXXX XXXX", null},
                {"CARTAO", "4296-1370-0000-0000", null},
                {"CARTAO", "5296.1370.0000.0002", null},
                {"CARTAO", "4296 1370", null},
                {"CARTAO", "52@6 1370 0000 0001", null},
                {"BOLETO", null, BOLETO},
                {"PIX", null, PIX},
                {"DINHEIRO", null, DINHEIRO},
                {"boleto", "", BOLETO},
                {"pix", "", PIX},
                {"dinheiro", "", DINHEIRO},
                {"gado", "50", null},
                {"", null, null},
                {null, null, null}
        });
    }

    @Before
    public void setUp() {
        try (MockedStatic<Database> mockedStatic = mockStatic(Database.class)) {
            Database db = mock(Database.class);
            when(db.getClienteByEmail(emailValido)).thenReturn(mock(Cliente.class));
            when(db.getProdutoByCodigo(idProdutosValidos.get(0))).thenReturn(mock(Produto.class));
            mockedStatic.when(Database::getInstance).thenReturn(db);
            if (saidaEsperada != null)
                vendaCadastrada = criaVenda(emailValido,
                        idProdutosValidos, entrada, numero, dataValida);
        }
    }

    @Test
    public void testaValores() {
        if (saidaEsperada != null) {
            assertEquals(saidaEsperada, vendaCadastrada.metodoDePagamento);
        } else {
            assertThrows(IllegalArgumentException.class, () ->
                    criaVenda(emailValido, idProdutosValidos, entrada, numero, dataValida)
            );
        }
    }


}
