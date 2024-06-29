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
         List<String> regioesValidas = List.of("interior", "capital");

        categoria = categoria.toLowerCase().trim();
        estado = estado.trim().toUpperCase();
        String regiao_inserida = regiao;
        regiao = regiao.trim().toLowerCase();

        if (! categoriasValidas.contains(categoria) ) {
            throw new IllegalArgumentException(String.format("Categoria inválida: %s.", categoria));
        }
        if (estado.length() != 2 ) {
            throw new IllegalArgumentException("Insira a sigla do estado.");
        }
        if (! estadosValidos.contains(estado)) {
            throw new IllegalArgumentException(String.format("Estado inválido: %s.", estado));
        }
        if (! regioesValidas.contains(regiao)) {
            throw new IllegalArgumentException(String.format("Região inválida: \"%s\".", regiao_inserida));
        }



        return new Cliente(categoria, estado, regiao);
    }
}
