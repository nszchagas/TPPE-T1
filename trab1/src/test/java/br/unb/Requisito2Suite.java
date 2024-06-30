package br.unb;

import br.unb.produto.DescricaoTest;
import br.unb.produto.ValorDeVendaTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DescricaoTest.class,
        ValorDeVendaTest.class
})
public class Requisito2Suite {
}
