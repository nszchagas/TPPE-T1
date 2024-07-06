package br.unb.cliente;

import br.unb.model.Cliente;
import br.unb.model.Database;
import br.unb.service.CadastroDeCliente;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InsercaoDatabaseTest {
    final CadastroDeCliente cadastroDeCliente = new CadastroDeCliente();
    final Database db = Database.getInstance();

    @Test
    public void assertNaoInsereDuplicado() {
        Cliente a = new Cliente("JOSÉ SILVA", "PADRAO", "SP", "INTERIOR", "jose@email.com");
        Cliente b = new Cliente("MARIA SILVA", "PREMIUM", "BA", "CAPITAL", "jose@email.com");
        cadastroDeCliente.insereClienteNoBanco(a);
        cadastroDeCliente.insereClienteNoBanco(b);
        Cliente clienteInserido = db.getClienteByEmail("jose@email.com");
        assertNotNull(clienteInserido);
        assertEquals(clienteInserido.nome, a.nome);
        assertEquals(clienteInserido.categoria, a.categoria);
        assertEquals(clienteInserido.getEstado(), a.getEstado());
        assertEquals(clienteInserido.getRegiao(), a.getRegiao());
        assertEquals(clienteInserido.email, a.email);
    }


}

