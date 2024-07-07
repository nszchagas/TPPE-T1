package br.unb.cliente;

import br.unb.Main;
import br.unb.model.Cliente;
import br.unb.model.Database;
import br.unb.model.categorias.CategoriaDeCliente;
import br.unb.model.categorias.RegiaoDoEstado;
import org.junit.Test;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static br.unb.model.categorias.CategoriaDeCliente.PADRAO;
import static br.unb.model.categorias.RegiaoDoEstado.INTERIOR;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    char opcaoCadastroCliente = '1';
    char opcaoSair = 'q';

    @Test
    public void testCadastrarCliente() {
        String nomeCliente = "João da Silva", emailCliente = "joao.silva@gmail.com", estadoCliente = "RS";
        RegiaoDoEstado regiaoCliente = INTERIOR;
        CategoriaDeCliente categoriaCliente = PADRAO;

        String entrada = String.format("%c\n%s\n%s\n%s\n%s\n%s\n%c\n", opcaoCadastroCliente, nomeCliente, regiaoCliente, estadoCliente, categoriaCliente, emailCliente, opcaoSair);

        ByteArrayInputStream in = new ByteArrayInputStream(entrada.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Main.main(new String[0]);

        String output = out.toString();
        assertTrue(output.contains("Cliente cadastrado: Cliente{nome='João da Silva'"));
        assertTrue(output.contains("Saindo do programa..."));
        Cliente clienteCadastrado = Database.getInstance().getClienteByEmail(emailCliente);
        assertNotNull(clienteCadastrado);

        assertEquals(nomeCliente, clienteCadastrado.nome);
        assertEquals(emailCliente, clienteCadastrado.email);
        assertEquals(estadoCliente, clienteCadastrado.getEstado());
        assertEquals(regiaoCliente, clienteCadastrado.getRegiao());

        assertEquals(categoriaCliente, clienteCadastrado.categoria);


    }

}


