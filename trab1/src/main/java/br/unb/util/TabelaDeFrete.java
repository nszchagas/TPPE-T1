package br.unb.util;

import br.unb.model.categorias.CategoriaDeCliente;
import br.unb.model.categorias.Endereco;
import br.unb.model.categorias.RegiaoDoEstado;
import br.unb.model.categorias.RegiaoDoPais;

public class TabelaDeFrete {

    public static double calcula(String estado, String regiao) {
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
    public static double calcula(String estado, RegiaoDoEstado regiao, CategoriaDeCliente categoriaDeCliente) {
        switch (categoriaDeCliente){
            case PRIME:
                return 0;
            case ESPECIAL:
                return 0.7F * calcula(estado, regiao.toString());
            case PADRAO:
                return calcula(estado, regiao.toString());
            default:
                return  -1F;
        }
    }

}
