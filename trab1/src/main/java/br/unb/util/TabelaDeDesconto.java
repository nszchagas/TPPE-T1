package br.unb.util;

import br.unb.model.categorias.CategoriaDeCliente;

import static br.unb.model.categorias.CategoriaDeCliente.ESPECIAL;

public class TabelaDeDesconto {

    public static double aplicaDesconto(double valorGasto, double frete, CategoriaDeCliente categoriaDeCliente, String metodoDePagamento){
        if (categoriaDeCliente != ESPECIAL)
            return 0;
        if (! metodoDePagamento.equals("CARTAO_LOJA"))
            return 0.1 * (valorGasto);
        return 0.1 * valorGasto + 0.1 * (0.9 * valorGasto + frete);

    }
}
