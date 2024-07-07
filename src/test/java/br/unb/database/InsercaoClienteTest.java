package br.unb.database;

import br.unb.model.Cliente;
import br.unb.model.Database;
import br.unb.service.Cadastro;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InsercaoClienteTest {
    final Database db = Database.getInstance();

    @Test
    public void assertNaoInsereDuplicado() {
        Cliente a = new Cliente("JOSÉ SILVA", "PADRAO", "SP", "INTERIOR", "jose@email.com");
        Cliente b = new Cliente("MARIA SILVA", "PREMIUM", "BA", "CAPITAL", "jose@email.com");
        Cadastro.insereClienteNoBanco(a);
        Cadastro.insereClienteNoBanco(b);
        Cliente clienteInserido = db.getClienteByEmail("jose@email.com");
        assertNotNull(clienteInserido);
        assertEquals(clienteInserido.nome, a.nome);
        assertEquals(clienteInserido.categoria, a.categoria);
        assertEquals(clienteInserido.getEstado(), a.getEstado());
        assertEquals(clienteInserido.getRegiao(), a.getRegiao());
        assertEquals(clienteInserido.email, a.email);
    }


}

