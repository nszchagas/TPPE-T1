package br.unb;

import br.unb.calculos.TabelaDeFreteTest;
import br.unb.venda.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DataTest.class,
        ClienteTest.class,
        ProdutosTest.class,
        MetodoPagamentoTest.class,
        TabelaDeFreteTest.class,
        FreteTest.class
})
public class Requisito3Suite {


}
