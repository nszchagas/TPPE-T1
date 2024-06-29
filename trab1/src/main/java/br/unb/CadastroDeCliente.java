package br.unb;

import java.util.List;

public class CadastroDeCliente {
    public Cliente cadastraCliente(String nome, String regiao, String estado, String categoria) {
        List<String> categoriasValidas = List.of("PADRAO", "ESPECIAL", "PRIME");
         List<String> estadosValidos = List.of(
            "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA",
            "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN",
            "RS", "RO", "RR", "SC", "SP", "SE", "TO"
         );
         List<String> regioesValidas = List.of("INTERIOR", "CAPITAL");

        String regiao_inserida = regiao;
        String categoria_inserida = categoria;
        categoria = categoria.toUpperCase().trim();
        estado = estado.trim().toUpperCase();
        regiao = regiao.trim().toUpperCase();

        if (! categoriasValidas.contains(categoria) ) {
            throw new IllegalArgumentException(String.format("Categoria inválida: \"%s\".", categoria_inserida));
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

        return new Cliente(nome, categoria, estado, regiao);
    }

    public void insereClienteNoBanco(Cliente cliente) {};
}
