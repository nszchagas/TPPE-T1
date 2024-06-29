package br.unb;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CadastroDeCliente{
    public Cliente cadastraCliente(String nome, String regiao, String estado, String categoria) {
        List<String> categoriasValidas = List.of("padrao", "especial", "prime");
         List<String> estadosValidos = List.of(
            "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA",
            "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN",
            "RS", "RO", "RR", "SC", "SP", "SE", "TO"
         );

        categoria = categoria.toLowerCase().trim();

        if (! categoriasValidas.contains(categoria) ) {
            throw new IllegalArgumentException(String.format("Categoria inválida: %s.", categoria));
        }
        if (estado.length() != 2 ) {
            throw new IllegalArgumentException("Insira a sigla do estado.");
        }
        if (! estadosValidos.contains(estado)) {
            throw new IllegalArgumentException(String.format("Estado inválido: %s.", estado));
        }


        return new Cliente(categoria, estado);
    }
}
