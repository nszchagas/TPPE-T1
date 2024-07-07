package br.unb.cliente;

import br.unb.model.Cliente;
import br.unb.model.categorias.CategoriaDeCliente;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static br.unb.model.categorias.CategoriaDeCliente.*;
import static br.unb.service.Cadastro.criaCliente;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CategoriaTest {

    private final String entrada;
    private final CategoriaDeCliente saida;

    public CategoriaTest(String entrada, CategoriaDeCliente saida) {
        this.entrada = entrada;
        this.saida = saida;
    }

    @Parameterized.Parameters(name = "{index} Entrada: {0}, saída esperada: {1}.")
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][]{
                        {"PaDraO", PADRAO},
                        {" especial", ESPECIAL},
                        {"PriME", PRIME},
                        {" PADRAO", PADRAO},
                        {"Padrao", PADRAO},
                        {"Tipo invalido", null}
                });

    }

    @Test
    public void testaValores() {
        if (saida != null) {
            Cliente j = criaCliente("José", "Capital", "SP", entrada, "jose@gmail.com");
            assertEquals(j.getCategoria(), saida);
        } else {
            Throwable e = assertThrows(IllegalArgumentException.class, () -> criaCliente("José", "Capital", "SP", entrada, "jose@gmail.com"));
            assertTrue(e.getMessage().contains(entrada));

        }

    }

}
