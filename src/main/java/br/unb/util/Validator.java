package br.unb.util;

import br.unb.model.UnidadeValida;
import br.unb.model.categorias.CategoriaDeCliente;
import br.unb.model.categorias.RegiaoDoEstado;

public class Validator {
    public static String validaCampoTextual(String entrada, String tipo) {
        if (entrada == null || entrada.isEmpty() || entrada.trim().isEmpty()) {
            String msg = entrada == null ? String.format("O valor de %s não pode ser vazio.", tipo) : String.format("Valor de %s inválido: \"%s\".", tipo, entrada);
            throw new IllegalArgumentException(msg);
        }
        return entrada.trim();
    }

    public static Object validaEnum(String valor, String nome) {
        try {
            switch (nome) {
                case "Categoria":
                    return CategoriaDeCliente.valueOf(valor.trim().toUpperCase());
                case "Regiao":
                    return RegiaoDoEstado.valueOf(valor.trim().toUpperCase());

            }
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("%s inválida: \"%s\".", nome, valor));
        }
        return null;
    }

    public static double validaDouble(String valorDeVenda) {
        try {
            if (valorDeVenda.contains(",")) {
                throw new IllegalArgumentException(String.format("Valor inválido. Utilize ponto para separar as casas decimais. Valor inserido: \"%s\".", valorDeVenda));
            }
            double valor = Double.parseDouble(valorDeVenda);
            assert valor > 0;
            return valor;
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Valor de venda não pode estar vazio.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Valor de venda deve ser numérico. Valor inserido: \"%s\".", valorDeVenda));
        } catch (AssertionError e) {
            throw new IllegalArgumentException(String.format("Valor de venda deve ser positivo. Valor inserido: \"%s\".", valorDeVenda));
        }
    }

    public static String validaUnidadeDeMedida(String unidade) {
        try {
            String unidadeNormalizada = UnidadeValida.normaliza(unidade);
            assert unidadeNormalizada != null;
            return unidadeNormalizada;

        } catch (AssertionError | NullPointerException e) {
            throw new IllegalArgumentException("Unidade não pode estar vazia.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format("Unidade inválida. Valor inserido: \"%s\".", unidade));
        }
    }
}
