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
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CadastroClienteTest {
    String nomeCliente, emailCliente, estadoCliente;
    RegiaoDoEstado regiaoCliente;
    CategoriaDeCliente categoriaCliente;
    Cliente clienteCadastrado;
    String outputConsole;

    public CadastroClienteTest(
            String nomecliente, String emailcliente, String estadocliente, String categoriacliente, String regiao
    ) {
        this.nomeCliente = nomecliente;
        this.emailCliente = emailcliente;
        this.estadoCliente = estadocliente;
        this.categoriaCliente = (CategoriaDeCliente) Validator.validaEnum(categoriacliente, "Categoria");
        this.regiaoCliente = (RegiaoDoEstado) Validator.validaEnum(regiao, "Regiao");

        String[] params = {COD_CADASTRO_CLIENTE, nomeCliente, String.valueOf(regiaoCliente), estadoCliente, String.valueOf(categoriaCliente), emailCliente, COD_SAIR};

        StringBuilder sb = new StringBuilder();
        for (String param : params) {
            sb.append(param).append('\n');
        }
        ByteArrayInputStream in = new ByteArrayInputStream(sb.toString().getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Main.main(new String[0]);

        outputConsole = out.toString();

        clienteCadastrado = Database.getInstance().getClienteByEmail(emailCliente);

        assertNotNull(clienteCadastrado);

    }

    @Parameterized.Parameters(name = "{index}, nome={0}, email={1}, estado={2}, categoria={3}, regiao={4}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                        {"João da Silva", "joao.silva@gmail.com", "RS", "padrão", "interior"},
                        {"João Bezerra", "joao.bezerra@gmail.com", "BA", "prime", "capital"}
                }
        );
    }


    @Test
    public void testMensagemDeCadastroCorreta() {
        assertTrue(outputConsole.contains(String.format("Cliente{nome='%s'", nomeCliente)));


    }

    @Test
    public void testaSaidaCorreta() {
        assertTrue(outputConsole.contains("Saindo do programa..."));
    }

    @Test
    public void testaclienteCadastrado() {
        assertNotNull(clienteCadastrado);
    }

    @Test
    public void testanomeCliente() {
        assertEquals(nomeCliente, clienteCadastrado.nome);
    }

    @Test
    public void testaemailCliente() {
        assertEquals(emailCliente, clienteCadastrado.email);
    }

    @Test
    public void testaestadoCliente() {
        assertEquals(estadoCliente, clienteCadastrado.getEstado());
    }

    @Test
    public void testaregiaoCliente() {
        assertEquals(regiaoCliente, clienteCadastrado.getRegiao());
    }

    @Test
    public void testacategoriaCliente() {
        assertEquals(categoriaCliente, clienteCadastrado.getCategoria());
    }
}