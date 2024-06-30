package br.unb.model;

import java.util.*;

public class UnidadeValida {
    private static final List<String> UNIDADES = Arrays.asList(
            "UNIDADE", "UN",
            "METRO", "M",
            "CENTIMETRO", "CM",
            "MILIMETRO", "MM",
            "QUILOMETRO", "KM",
            "POLEGADA", "IN",
            "LITRO", "L",
            "MILILITRO", "ML",
            "METRO CUBICO", "M3",
            "CENTIMETRO CUBICO", "CM3",
            "ONCA FLUIDA", "FL OZ",
            "QUILOGRAMA", "KG",
            "GRAMA", "G",
            "MILIGRAMA", "MG",
            "TONELADA", "T",
            "LIBRA", "LB",
            "ONCA", "OZ",
            "METRO QUADRADO", "M2",
            "CENTIMETRO QUADRADO", "CM2"
    );


    public static String normaliza(String valor1) throws IllegalArgumentException {

        String unidade = valor1.strip().toUpperCase()
                .replace('Í', 'I')
                .replace('Ô', 'O')
                .replace('Ú', 'U')
                .replace('Ç', 'C');
        if (unidade.isEmpty()) {
            return null;
        }
        if (UNIDADES.contains(unidade)) {
            return unidade;
        }
        throw new IllegalArgumentException();
    }

}

