package br.unb.cliente;

import br.unb.service.CadastroDeCliente;
import br.unb.model.Cliente;
import br.unb.model.Database;
import org.junit.Test;

import static org.junit.Assert.*;

public class InsercaoDatabaseTest {
    CadastroDeCliente cadastroDeCliente = new CadastroDeCliente();
    Database db = Database.getInstance();
    Cliente a = new Cliente("JOSÉ SILVA", "PADRAO", "SP", "INTERIOR");
        Cliente b = new Cliente("MARIA SOUZA", "ESPECIAL", "RJ", "CAPITAL");
        Cliente c = new Cliente("ANA LIMA", "PRIME", "MG", "INTERIOR");
        Cliente d = new Cliente("CARLOS PEREIRA", "PADRAO", "BA", "CAPITAL");
        Cliente e = new Cliente("FERNANDA ALVES", "ESPECIAL", "PR", "INTERIOR");


    @Test
    public void assertTamanho() {
        cadastroDeCliente.insereClienteNoBanco(a);
        cadastroDeCliente.insereClienteNoBanco(b);
        cadastroDeCliente.insereClienteNoBanco(c);
        cadastroDeCliente.insereClienteNoBanco(d);
        cadastroDeCliente.insereClienteNoBanco(e);
        assertEquals(db.getQtdClientes(), 5);
    }

    @Test
    public void assertNaoInsereDuplicado(){
        cadastroDeCliente.insereClienteNoBanco(a);
        cadastroDeCliente.insereClienteNoBanco(a);
        assertEquals(db.getQtdClientes(), 1);
    }


}

