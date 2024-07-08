package br.unb.cliente;

import br.unb.model.Cliente;
import br.unb.model.Database;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static br.unb.model.categorias.CategoriaDeCliente.PADRAO;
import static br.unb.model.categorias.CategoriaDeCliente.PRIME;
import static br.unb.model.categorias.RegiaoDoEstado.CAPITAL;
import static br.unb.model.categorias.RegiaoDoEstado.INTERIOR;
import static br.unb.service.Cadastro.criaCliente;
import static br.unb.service.Cadastro.insereNoBanco;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class EstadoTest {
    final String entrada;
    final String saidaEsperada;
    final Class<? extends Throwable> excecaoEsperada;

    public EstadoTest(String entrada, String saidaEsperada, Class<? extends Throwable> excecaoEsperada) {
        this.entrada = entrada;
        this.saidaEsperada = saidaEsperada;
        this.excecaoEsperada = excecaoEsperada;
    }

    @Parameterized.Parameters(name = "{index}: Estado {0} deve sair como {1} (Excecao: {2}).")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"sp", "SP", null},
                {"df", "DF", null},
                {" pr", "PR", null},
                {"BA ", "BA", null},
                {"São Paulo", "Insira a sigla do estado.", IllegalArgumentException.class,},
                {"ZS", "Estado inválido: ZS.", IllegalArgumentException.class,}
        });
    }

    @Test
    public void testaValores() {
        if (excecaoEsperada == null) {
            Cliente a = criaCliente("José", "interior", entrada, "padrao", "jose@gmail.com");
            assertEquals(a.getEstado(), saidaEsperada);
        } else {
            Throwable e = assertThrows(excecaoEsperada,
                    () -> criaCliente("José", "interior", entrada, "padrao", "jose@gmail.com")
            );
            assertEquals(e.getMessage(), saidaEsperada);
        }
    }


}
