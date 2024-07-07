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

import static br.unb.Main.COD_CADASTRO_CLIENTE;
import static br.unb.Main.COD_SAIR;
import static br.unb.model.categorias.CategoriaDeCliente.PADRAO;
import static br.unb.model.categorias.RegiaoDoEstado.INTERIOR;
import static org.junit.Assert.*;

public class MainTest {


    @Test
    public void testCadastrarCliente() {
        String nomeCliente = "João da Silva", emailCliente = "joao.silva@gmail.com", estadoCliente = "RS";
        RegiaoDoEstado regiaoCliente = INTERIOR;
        CategoriaDeCliente categoriaCliente = PADRAO;

        String entrada = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n", COD_CADASTRO_CLIENTE, nomeCliente, regiaoCliente, estadoCliente, categoriaCliente, emailCliente, COD_SAIR);

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


    @Test
    public void testCadastrarProduto() {
        String input = "2\nProduto Teste\n10.00\nUnidade\n12345\n3\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Main.main(new String[0]);

        String output = new String(out.toByteArray());
        assertTrue(output.contains("Produto cadastrado: Produto{descricao='Produto Teste'"));
        assertTrue(output.contains("Saindo do programa..."));
    }

}


