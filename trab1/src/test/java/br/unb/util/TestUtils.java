package br.unb.util;

import br.unb.model.Cliente;

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

}
