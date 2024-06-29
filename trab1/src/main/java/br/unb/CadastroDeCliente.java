package br.unb;

import java.util.ArrayList;
import java.util.List;

public class CadastroDeCliente{
    public Cliente cadastraCliente(String nome, String regiao, String estado, String categoria) {
        List<String> categoriasValidas = List.of("padrao", "especial", "prime");

        if (! categoriasValidas.contains(categoria) ) {
            throw new IllegalArgumentException(String.format("Categoria inválida: %s.", categoria));
        }
        return null;
    }
}
