package br.unb.produto;

import br.unb.model.Produto;
import br.unb.service.CadastroDeProduto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ValorDeVendaTest {
    private String entrada;
    private String saidaEsperada;
    Class <? extends Throwable> excecaoEsperada;

    public ValorDeVendaTest(String entrada, String  saidaEsperada, Class <? extends Throwable> excecaoEsperada) {
        this.entrada = entrada;
        this.saidaEsperada = saidaEsperada;
        this.excecaoEsperada = excecaoEsperada;
    }


    @Parameterized.Parameters(name="{index}: valor de venda {0} deve gerar exceção {2} com mensagem {1}.")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"-1", "Valor de venda deve ser positivo. Valor inserido: \"-1\".", IllegalArgumentException.class},
                {"0", "Valor de venda deve ser positivo. Valor inserido: \"0\".", IllegalArgumentException.class},
                {"Não numérico", "Valor de venda deve ser numérico. Valor inserido: \"Não numérico\".", IllegalArgumentException.class},
                {"", "Valor de venda deve ser numérico. Valor inserido: \"\".", IllegalArgumentException.class},
                {" ", "Valor de venda deve ser numérico. Valor inserido: \" \".", IllegalArgumentException.class},
                {null, "Valor de venda não pode estar vazio.", IllegalArgumentException.class},
                {"1,2", "Valor inválido. Utilize ponto para separar as casas decimais. Valor inserido: \"1,2\".", IllegalArgumentException.class},
                {"52.6", "52.6", null},
        });
    }

    @Test()
    public void testeValores(){
        CadastroDeProduto c = new CadastroDeProduto();
        if (excecaoEsperada != null ){
            Throwable a = assertThrows(excecaoEsperada, () ->
                c.cadastraProduto("Caderno 96 Folhas", entrada)
            );
            assertEquals(a.getMessage(), saidaEsperada);
        } else
        {
            Produto p = c.cadastraProduto("Caderno 96 Folhas", entrada);
            assertEquals(p.valorDeVenda, Float.valueOf(saidaEsperada));
        }

    }




}
