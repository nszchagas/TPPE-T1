package br.unb;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CadastroDeCliente{
    public Cliente cadastraCliente(String nome, String regiao, String estado, String categoria) {
        List<String> categoriasValidas = List.of("padrao", "especial", "prime");
        categoria = categoria.toLowerCase().trim();
        if (! categoriasValidas.contains(categoria) ) {
            throw new IllegalArgumentException(String.format("Categoria inválida: %s.", categoria));
        }


        return new Cliente(categoria);
    }
}
