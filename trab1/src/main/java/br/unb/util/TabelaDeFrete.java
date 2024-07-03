package br.unb.util;

import br.unb.model.categorias.Endereco;
import br.unb.model.categorias.RegiaoDoPais;

public class TabelaDeFrete {


    public static float calcula(String estado, String regiao) {
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


}
