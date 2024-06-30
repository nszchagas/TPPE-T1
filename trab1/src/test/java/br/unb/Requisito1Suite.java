package br.unb;

import br.unb.cliente.CategoriaTest;
import br.unb.cliente.EstadoTest;
import br.unb.cliente.InsercaoDatabaseTest;
import br.unb.cliente.RegiaoTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CategoriaTest.class,
        EstadoTest.class,
        RegiaoTest.class,
        InsercaoDatabaseTest.class,
})
public class Requisito1Suite {
}
