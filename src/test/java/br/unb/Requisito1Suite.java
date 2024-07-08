package br.unb;

import br.unb.cliente.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CategoriaTest.class,
        EstadoTest.class,
        RegiaoTest.class,
        InsercaoClienteTest.class,
        EmailTest.class,
        CadastroClienteTest.class
})
public class Requisito1Suite {
}
