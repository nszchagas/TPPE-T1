package br.unb;

import br.unb.venda.ClienteTest;
import br.unb.venda.DataTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DataTest.class,
        ClienteTest.class
})
public class Requisito3Suite {
}
