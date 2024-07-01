package br.unb;

import br.unb.model.Cliente;
import br.unb.model.Database;
import br.unb.model.Produto;
import br.unb.service.CadastroDeCliente;
import br.unb.service.CadastroDeProduto;
import br.unb.util.TestUtils;
import br.unb.venda.*;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DataTest.class,
        ClienteTest.class,
        ItensTest.class
})
public class Requisito3Suite {
    @BeforeClass
    public static void setUp() {
        // Cadastra os clientes válidos de TestUtils
        CadastroDeCliente cc = new CadastroDeCliente();
        Database db = Database.getInstance();
        //  Cadastra Usuários Válidos
        // Emails: email1@domain.com, email2@domain.com, ..., email5@domain.com

        for (Cliente c : TestUtils.getClientesValidos())
            cc.insereClienteNoBanco(c);

        assert db.getQtdClientes() >= TestUtils.getClientesValidos().size();

        // Cadastra os itens válidos
        CadastroDeProduto cp = new CadastroDeProduto();
        for (Produto p : TestUtils.getProdutosValidos())
            cp.insereProdutoNoBanco(p);

        assert db.getQtdProdutos() >= TestUtils.getProdutosValidos().size();

    }

}
