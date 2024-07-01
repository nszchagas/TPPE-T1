package br.unb.cliente;

import br.unb.service.CadastroDeCliente;
import br.unb.model.Cliente;
import br.unb.model.Database;
import br.unb.util.TestUtils;
import org.junit.Test;
import java.util.List;

import static org.junit.Assert.*;

public class InsercaoDatabaseTest {
    CadastroDeCliente cadastroDeCliente = new CadastroDeCliente();
    Database db = Database.getInstance();


    @Test
    public void assertTamanho() {
        List<Cliente> clientes = TestUtils.getClientesValidos();
        cadastroDeCliente.insereClienteNoBanco(clientes.get(0));
        cadastroDeCliente.insereClienteNoBanco(clientes.get(1));
        cadastroDeCliente.insereClienteNoBanco(clientes.get(2));
        cadastroDeCliente.insereClienteNoBanco(clientes.get(3));
        cadastroDeCliente.insereClienteNoBanco(clientes.get(4));
        assertEquals(db.getQtdClientes(), 5);
    }

    @Test
    public void assertNaoInsereDuplicado(){
        Cliente a = new Cliente("JOSÉ SILVA", "PADRAO", "SP", "INTERIOR", "jose@email.com");
        Cliente b = new Cliente("MARIA SILVA", "PREMIUM", "BA", "CAPITAL", "jose@email.com");
        cadastroDeCliente.insereClienteNoBanco(a);
        cadastroDeCliente.insereClienteNoBanco(b);
        assertEquals(db.getQtdClientes(), 1);
    }


}

