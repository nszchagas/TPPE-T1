package br.unb.produto;

import br.unb.model.Produto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static br.unb.service.Cadastro.criaProduto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(Parameterized.class)

public class CodigoTest {
    private final String entrada;
    private final String saidaEsperada;
    final Class<? extends Throwable> excecaoEsperada;

    public CodigoTest(String entrada, String saidaEsperada, Class<? extends Throwable> excecaoEsperada) {
        this.entrada = entrada;
        this.saidaEsperada = saidaEsperada;
        this.excecaoEsperada = excecaoEsperada;
    }


    @Parameterized.Parameters(name = "{index}: código {0} deve gerar exceção {2} com mensagem {1}.")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"", "Valor de código inválido: \"\".", IllegalArgumentException.class},
                {" ", "Valor de código inválido: \" \".", IllegalArgumentException.class},
                {null, "O valor de código não pode ser vazio.", IllegalArgumentException.class},
                {"123456 ", "123456", null},

        });
    }

    @Test()
    public void testeValores() {

        if (excecaoEsperada != null) {
            Throwable a = assertThrows(excecaoEsperada, () -> criaProduto("Caderno das Princesas Disney", "100.20", "UNIDADE", entrada));
            assertEquals(a.getMessage(), saidaEsperada);
        } else {
            Produto p = criaProduto("Caderno das Princesas Disney", "100.20", "UNIDADE", entrada);
            assertEquals(p.codigo, saidaEsperada);
        }

    }

}
