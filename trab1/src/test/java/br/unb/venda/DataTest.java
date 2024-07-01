package br.unb.venda;

import br.unb.model.Venda;
import br.unb.service.CadastroDeVenda;
import br.unb.util.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(Parameterized.class)
public class DataTest {

    String entrada;
    Object saidaEsperada;
    Class <? extends  Throwable> excecaoEsperada;
    List<String> itens = TestUtils.getCodigosDeProdutosValidos();

    public DataTest(String entrada, Object saidaEsperada, Class<? extends  Throwable> excecaoEsperada) {
        this.entrada = entrada;
        this.saidaEsperada = saidaEsperada;
        this.excecaoEsperada = excecaoEsperada;
    }
    @Parameterized.Parameters(name="{index} entrada={0}, saidaEsperada={1}, excecaoEsperada={2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"", "Formato ou valor de data inválido: \"\". Utilize o padrão ISO (Ex: 2024-06-30).", IllegalArgumentException.class},
                {" ", "Formato ou valor de data inválido: \" \". Utilize o padrão ISO (Ex: 2024-06-30).", IllegalArgumentException.class},
                // Formato inválido
                {"10/12/2023", "Formato ou valor de data inválido: \"10/12/2023\". Utilize o padrão ISO (Ex: 2024-06-30).", IllegalArgumentException.class},
                // Data que não chegou
                {"2024-12-05", "Formato ou valor de data inválido: \"2024-12-05\". Utilize o padrão ISO (Ex: 2024-06-30).", IllegalArgumentException.class},
                // Dia que não existe
                {"2023-02-29", "Formato ou valor de data inválido: \"2023-02-29\". Utilize o padrão ISO (Ex: 2024-06-30).", IllegalArgumentException.class},
                // Data válida
                {"2024-01-15", LocalDate.of(2024,1,15), null}
        });
    }
    @Test()
    public void testeValores() {
        CadastroDeVenda c = new CadastroDeVenda();
        if (excecaoEsperada != null) {
            Throwable a = assertThrows(excecaoEsperada, () ->
                    c.criaVenda("email1@domain.com", itens, "PIX",entrada ));
            assertEquals(a.getMessage(), saidaEsperada);
        } else {
            Venda v = c.criaVenda("email1@domain.com", itens, "PIX", entrada);
            assertEquals(v.data, saidaEsperada);
        }
    }
}
