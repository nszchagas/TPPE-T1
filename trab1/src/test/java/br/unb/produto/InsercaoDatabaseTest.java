package br.unb.produto;

import br.unb.model.Produto;
import br.unb.model.Database;
import br.unb.service.CadastroDeProduto;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InsercaoDatabaseTest {
    CadastroDeProduto cadastro = new CadastroDeProduto();
    Database db = Database.getInstance();


    @Test
    public void assertTamanho() {
        Produto a = new Produto("BOLA DE FUTEBOL" ,12.6F, "UN", "123456");
        Produto b = new Produto("fio de cobre", 3.5F, "METRO", "654321");
        Produto c = new Produto("Água mineral", 2.0F, "LITRO", "789012");
        Produto d = new Produto("CABO DE REDE", 1.2F, "METRO", "210987");
        Produto e = new Produto("OLEO DE COZINHA", 6.5F, "LITRO", "345678");
        cadastro.insereProdutoNoBanco(a);
        cadastro.insereProdutoNoBanco(b);
        cadastro.insereProdutoNoBanco(c);
        cadastro.insereProdutoNoBanco(d);
        cadastro.insereProdutoNoBanco(e);
        assertEquals(db.getQtdProdutos(), 5);
    }

    @Test
    public void assertNaoInsereDuplicado(){
        cadastro.insereProdutoNoBanco(new Produto("BOLA DE FUTEBOL" ,12.6F, "UN", "123456"));
        cadastro.insereProdutoNoBanco(new Produto("OLEO DE COZINHA", 6.5F, "LITRO", "123456"));
        assertEquals(db.getQtdProdutos(), 1);
    }


}

