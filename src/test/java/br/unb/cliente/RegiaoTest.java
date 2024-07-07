package br.unb.cliente;

import br.unb.model.Cliente;
import br.unb.model.categorias.RegiaoDoEstado;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static br.unb.model.categorias.RegiaoDoEstado.CAPITAL;
import static br.unb.model.categorias.RegiaoDoEstado.INTERIOR;
import static br.unb.service.Cadastro.cadastraCliente;
import static org.junit.Assert.*;

//@TODO: assert que não existe região com DF.
@RunWith(Parameterized.class)
public class RegiaoTest {
    final String entrada;
    final RegiaoDoEstado saidaEsperada;

    public RegiaoTest(String entrada, RegiaoDoEstado saidaEsperada) {
        this.entrada = entrada;
        this.saidaEsperada = saidaEsperada;
    }

    @Parameterized.Parameters(name = "{index} entrada={0},saida={1}.")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"CapiTal", CAPITAL},
                {"InTERIOR   ", INTERIOR},
                {"outro canto", null},
                {" minha casa   ", null}
        });

    }

    @Test
    public void testaValores() {
        if (saidaEsperada != null) {
            Cliente cliente = cadastraCliente("José", entrada, "BA", "padrao", "jose@gmail.com");
            assertEquals(cliente.getRegiao(), saidaEsperada);
        } else {
            Throwable e = assertThrows(IllegalArgumentException.class, () -> cadastraCliente("José", entrada, "BA", "padrao", "jose@gmail.com"));
            assertTrue(e.getMessage().contains(entrada));
        }
    }

}
