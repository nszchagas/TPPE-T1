package br.unb.util;

import br.unb.model.categorias.*;

import static br.unb.model.categorias.CategoriaDeCliente.ESPECIAL;
import static br.unb.model.categorias.MetodoDePagamento.CARTAO_LOJA;

public class OperacoesFinanceiras {

    public static double calculaFrete(String estado, String regiao) {
        RegiaoDoPais regiaoDoPais = Endereco.getRegiaoDoPais(estado);
        boolean isCapital = regiao.equalsIgnoreCase("CAPITAL");
        if (regiaoDoPais == null || !isCapital && !regiao.equalsIgnoreCase("INTERIOR"))
            return -1;
        switch (regiaoDoPais) {
            case DF:
                return 5;
            case CENTRO_OESTE:
            case SUL:
                if (isCapital)
                    return 10;
                return 13;
            case NORDESTE:
                if (isCapital)
                    return 15;
                return 18;
            case NORTE:
                if (isCapital)
                    return 20;
                return 25;
            case SUDESTE:
                if (isCapital)
                    return 7;
                return 10;
        }
        return -1;
    }

    public static double calculaFrete(String estado, RegiaoDoEstado regiao, CategoriaDeCliente categoriaDeCliente) {
        switch (categoriaDeCliente) {
            case PRIME:
                return 0;
            case ESPECIAL:
                return 0.7F * calculaFrete(estado, regiao.toString());
            case PADRAO:
                return calculaFrete(estado, regiao.toString());
            default:
                return -1F;
        }
    }

    public static double calculaDesconto(double valorGasto, double frete, CategoriaDeCliente categoriaDeCliente, MetodoDePagamento metodoDePagamento) {
        if (categoriaDeCliente != ESPECIAL)
            return 0;
        if (!metodoDePagamento.equals(CARTAO_LOJA))
            return 0.1 * (valorGasto);
        return 0.1 * valorGasto + 0.1 * (0.9 * valorGasto + frete);

    }

    public static double calculaCashback(CategoriaDeCliente cliente, MetodoDePagamento pagamento, double valorTotal) {
        switch (cliente) {
            case ESPECIAL:
            case PADRAO:
                return 0;
            case PRIME:
                if (pagamento == CARTAO_LOJA)
                    return 0.05 * valorTotal;
                return 0.03 * valorTotal;
        }
        return -1;

    }

    public static double calculaImposto(String tipo, String estado, double valor) {

        if (estado.equals("DF")) {
            if (tipo.equals("MUNICIPAL"))
                return 0;
            return 0.18 * valor;
        }

        if (tipo.equals("ICMS"))
            return 0.12 * valor;

        assert tipo.equals("MUNICIPAL");
        return 0.04 * valor;

    }


}
