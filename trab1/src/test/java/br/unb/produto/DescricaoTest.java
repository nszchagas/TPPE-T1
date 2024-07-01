package br.unb.produto;

import br.unb.model.Produto;
import br.unb.service.CadastroDeProduto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(Parameterized.class)
public class DescricaoTest {
    private String entrada, saidaEsperada;
    Class<? extends Throwable> excecaoEsperada;

    public DescricaoTest(String entrada, String saidaEsperada, Class<? extends Throwable> excecaoEsperada) {
        this.entrada = entrada;
        this.saidaEsperada = saidaEsperada;
        this.excecaoEsperada = excecaoEsperada;
    }


    @Parameterized.Parameters(name = "{index}: descrição {0} deve gerar exceção {2} com mensagem {1}.")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"", "Valor de descrição inválido: \"\".", IllegalArgumentException.class},
                {" ", "Valor de descrição inválido: \" \".", IllegalArgumentException.class},
                {null, "O valor de descrição não pode ser vazio.", IllegalArgumentException.class},
                {"Bola de futebol", "Bola de futebol", null}});
    }

    @Test()
    public void testeValores() {
        CadastroDeProduto c = new CadastroDeProduto();
        if (excecaoEsperada != null) {
            Throwable a = assertThrows(excecaoEsperada, () ->
                    c.cadastraProduto(entrada, "100.20", "UNIDADE", "123456"));
            assertEquals(a.getMessage(), saidaEsperada);
        } else {
            Produto p = c.cadastraProduto(entrada, "100.20", "UNIDADE",  "123456");
            assertEquals(p.descricao, saidaEsperada);
        }

    }


}
