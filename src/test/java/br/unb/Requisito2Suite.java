package br.unb;

import br.unb.produto.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DescricaoTest.class,
        ValorDeVendaTest.class,
        UnidadeTest.class,
        CodigoTest.class,
        InsercaoDatabaseTest.class
})
public class Requisito2Suite {
}
