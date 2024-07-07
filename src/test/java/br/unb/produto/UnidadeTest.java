package br.unb.produto;

import br.unb.model.Produto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static br.unb.service.Cadastro.cadastraProduto;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class UnidadeTest {
    private final String entrada;
    private final String saidaEsperada;
    final Class <? extends Throwable> excecaoEsperada;

    public UnidadeTest(String entrada, String  saidaEsperada, Class <? extends Throwable> excecaoEsperada) {
        this.entrada = entrada;
        this.saidaEsperada = saidaEsperada;
        this.excecaoEsperada = excecaoEsperada;
    }


    @Parameterized.Parameters(name="{index}: unidade {0} deve gerar exceção {2} com mensagem {1}.")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {null, "Unidade não pode estar vazia.", IllegalArgumentException.class},
                {"", "Unidade não pode estar vazia.", IllegalArgumentException.class},
                {" ", "Unidade não pode estar vazia.", IllegalArgumentException.class},
                {"Galáxias", "Unidade inválida. Valor inserido: \"Galáxias\".", IllegalArgumentException.class},
                {"cm3", "CM3", null },
                // Caracteres especiais: í, ô, ú, ç
                {"onça FLUIDa", "ONCA FLUIDA", null},
                {"centímetro cúbico", "CENTIMETRO CUBICO", null},
                {"centimetro", "CENTIMETRO", null },
                {"quilômetro", "QUILOMETRO", null}

        });
    }

    @Test()
    public void testeValores(){
        if (excecaoEsperada != null ){
            Throwable a = assertThrows(excecaoEsperada, () ->
                cadastraProduto("Caderno 96 Folhas", "10", entrada,"123456")
            );
            assertEquals(a.getMessage(), saidaEsperada);
        } else
        {
            Produto p = cadastraProduto("Caderno 96 Folhas", "10", entrada, "123456");
            assertEquals(p.unidade, saidaEsperada);
        }

    }




}
