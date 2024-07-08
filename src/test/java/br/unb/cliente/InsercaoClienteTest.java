package br.unb.cliente;

import br.unb.model.Cliente;
import br.unb.model.Database;
import org.junit.Test;

import static br.unb.model.categorias.CategoriaDeCliente.PADRAO;
import static br.unb.model.categorias.CategoriaDeCliente.PRIME;
import static br.unb.model.categorias.RegiaoDoEstado.CAPITAL;
import static br.unb.model.categorias.RegiaoDoEstado.INTERIOR;
import static br.unb.service.Cadastro.insereNoBanco;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InsercaoClienteTest {
    final Database db = Database.getInstance();

    @Test
    public void assertNaoInsereDuplicado() {
        String emailRepetido = "email123456@email.com";
        Cliente a = new Cliente("JOSÉ SILVA", PADRAO, "SP", INTERIOR, emailRepetido);
        Cliente b = new Cliente("MARIA SILVA", PRIME, "BA", CAPITAL, emailRepetido);
        insereNoBanco(a);
        insereNoBanco(b);
        Cliente clienteInserido = db.getClienteByEmail(emailRepetido);
        assertNotNull(clienteInserido);
        assertEquals(clienteInserido.nome, a.nome);
        assertEquals(clienteInserido.getCategoria(), a.getCategoria());
        assertEquals(clienteInserido.getEstado(), a.getEstado());
        assertEquals(clienteInserido.getRegiao(), a.getRegiao());
        assertEquals(clienteInserido.email, a.email);
    }


}