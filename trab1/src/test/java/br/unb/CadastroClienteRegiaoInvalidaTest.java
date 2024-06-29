package br.unb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(Parameterized.class)
public class CadastroClienteRegiaoInvalidaTest {
    String entrada,saidaEsperada;
    Class <?extends Throwable> excecaoEsperada;
    CadastroDeCliente cadastroDeCliente = new CadastroDeCliente();
    public CadastroClienteRegiaoInvalidaTest(String entrada, String saidaEsperada, Class <?extends  Throwable> excecaoEsperada) {
        this.entrada = entrada;
        this.saidaEsperada = saidaEsperada;
        this.excecaoEsperada = excecaoEsperada;
    }
    @Parameterized.Parameters(name="{index} Região {0} deve gerar saída/mensagem de erro {1} (Exceção: {2}).")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"CapiTal", "capital", null},
                {"InTERIOR   ", "interior", null},
                {"outro canto",   "Região inválida: \"outro canto\"."    , IllegalArgumentException.class},
                {" minha casa   ", "Região inválida: \" minha casa   \".",  IllegalArgumentException.class}
        });

    }

    @Test
    public void testaValores(){
        if (excecaoEsperada == null) {
            Cliente cliente = cadastroDeCliente.cadastraCliente("José", entrada, "BA", "padrao");
            assertEquals(cliente.regiao, saidaEsperada);
        } else {
            Throwable e = assertThrows(excecaoEsperada, () -> cadastroDeCliente.cadastraCliente("José", entrada, "BA", "padrao"));
            assertEquals(e.getMessage(), saidaEsperada);

        }


    }


}
