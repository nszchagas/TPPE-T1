package br.unb;

import br.unb.calculos.TabelaDeFreteTest;
import br.unb.venda.ClienteTest;
import br.unb.venda.DataTest;
import br.unb.venda.MetodoPagamentoTest;
import br.unb.venda.ProdutosTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DataTest.class,
        ClienteTest.class,
        ProdutosTest.class,
        MetodoPagamentoTest.class,
        TabelaDeFreteTest.class
})
public class Requisito3Suite {


}
