package br.unb.util;

import br.unb.model.Cliente;
import br.unb.model.Produto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestUtils {

    private TestUtils() {

    }
    public static List<Cliente> getClientesValidos() {
    return Arrays.asList(
        new Cliente("JOSÉ SILVA", "PADRAO", "SP", "INTERIOR", "email1@domain.com"),
        new Cliente("MARIA SOUZA", "ESPECIAL", "RJ", "CAPITAL", "email2@domain.com"),
        new Cliente("ANA LIMA", "PRIME", "MG", "INTERIOR", "email3@domain.com"),
        new Cliente("CARLOS PEREIRA", "PADRAO", "BA", "CAPITAL", "email4@domain.com"),
        new Cliente("FERNANDA ALVES", "ESPECIAL", "PR", "INTERIOR", "email5@domain.com")
    );
    }

    public static List<Produto> getProdutosValidos(){
        return Arrays.asList(
          new Produto("Produto A", 15.2F, "M", "123"),
            new Produto("Produto B", 10.5F, "P", "456"),
            new Produto("Produto C", 22.3F, "L", "789"),
            new Produto("Produto D", 18.0F, "S", "101"),
            new Produto("Produto E", 30.0F, "M", "202")
        );
    }

    public static List<String> getCodigosDeProdutosValidos(){
        ArrayList<String> lista = new ArrayList<>();
        for (Produto p : TestUtils.getProdutosValidos())
            lista.add(p.codigo);
        return lista;
    }

}
