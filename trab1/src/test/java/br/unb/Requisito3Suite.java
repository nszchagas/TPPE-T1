package br.unb;

import br.unb.model.Cliente;
import br.unb.model.Database;
import br.unb.service.CadastroDeCliente;
import br.unb.util.TestUtils;
import br.unb.venda.ClienteTest;
import br.unb.venda.DataTest;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DataTest.class,
        ClienteTest.class
})
public class Requisito3Suite {
    @BeforeClass
    public static void setUp() {
        // Cadastra os clientes válidos de TestUtils
        CadastroDeCliente cc = new CadastroDeCliente();
        Database db = Database.getInstance();
        //  Cadastra Usuários Válidos
        // Emails: email1@domain.com, email2@domain.com, ..., email5@domain.com

        for (Cliente c : TestUtils.getClientesValidos()) {
            cc.insereClienteNoBanco(c);
        }
        assert db.getQtdClientes() == TestUtils.getClientesValidos().size();
    }

}
