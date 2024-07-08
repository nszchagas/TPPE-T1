package br.unb.cliente;

import br.unb.Main;
import br.unb.model.Cliente;
import br.unb.model.Database;
import br.unb.model.categorias.CategoriaDeCliente;
import br.unb.model.categorias.RegiaoDoEstado;
import br.unb.util.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;

import static br.unb.Main.COD_CADASTRO_CLIENTE;
import static br.unb.Main.COD_SAIR;
import static br.unb.model.categorias.RegiaoDoEstado.CAPITAL;
import static br.unb.model.categorias.RegiaoDoEstado.INTERIOR;
import static br.unb.service.Cadastro.criaCliente;
import static org.junit.Assert.*;

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
            Cliente cliente = criaCliente("José", entrada, "BA", "padrao", "jose@gmail.com");
            assertEquals(saidaEsperada, cliente.getEndereco().regiaoDoEstado);
        } else {
            Throwable e = assertThrows(IllegalArgumentException.class, () -> criaCliente("José", entrada, "BA", "PADRAO", "jose@gmail.com"));
            assertTrue(e.getMessage().contains(entrada));
        }
    }


}
