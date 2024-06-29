package br.unb.cadastro;

import br.unb.CadastroDeCliente;
import br.unb.Cliente;
import br.unb.Database;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CriacaoClienteTest {
    CadastroDeCliente cadastroDeCliente = new CadastroDeCliente();
    Database db = Database.getInstance();
    Cliente a = new Cliente("JOSÉ SILVA", "PADRAO", "SP", "INTERIOR");
        Cliente b = new Cliente("MARIA SOUZA", "ESPECIAL", "RJ", "CAPITAL");
        Cliente c = new Cliente("ANA LIMA", "PRIME", "MG", "INTERIOR");
        Cliente d = new Cliente("CARLOS PEREIRA", "PADRAO", "BA", "CAPITAL");
        Cliente e = new Cliente("FERNANDA ALVES", "ESPECIAL", "PR", "INTERIOR");
        Cliente f = new Cliente("RAFAEL SANTOS", "PRIME", "RS", "CAPITAL");
        Cliente g = new Cliente("CLAUDIA OLIVEIRA", "PADRAO", "SC", "INTERIOR");


    @Test
    public void assertTamanho() {
        cadastroDeCliente.insereClienteNoBanco(a);
        cadastroDeCliente.insereClienteNoBanco(b);
        cadastroDeCliente.insereClienteNoBanco(c);
        cadastroDeCliente.insereClienteNoBanco(d);
        cadastroDeCliente.insereClienteNoBanco(e);
        assertEquals(db.getQtdClientes(), 5);
    }


}

