package br.unb.util;

import java.util.HashMap;
import java.util.List;

public class TabelaDeFrete {
    private enum RegiaoDoPais {
        DF, CENTRO_OESTE, NORDESTE, NORTE, SUDESTE, SUL
    }

    private enum RegiaoDoEstado {
        CAPITAL, INTERIOR
    }


    public static float calcula(String estado, String regiao) {
        RegiaoDoPais regiaoDoPais = getRegiaoDoPais(estado);
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

    private static RegiaoDoPais getRegiaoDoPais(String estado) {

        HashMap<RegiaoDoPais, List<String>> regioes = new HashMap<>();
        regioes.put(RegiaoDoPais.DF, List.of("DF"));
        regioes.put(RegiaoDoPais.CENTRO_OESTE, List.of("GO", "MT", "MS"));
        regioes.put(RegiaoDoPais.NORDESTE, List.of("AL", "BA", "CE", "MA", "PB", "PE", "PI", "RN", "SE"));
        regioes.put(RegiaoDoPais.NORTE, List.of("AC", "AP", "AM", "PA", "RO", "RR", "TO"));
        regioes.put(RegiaoDoPais.SUDESTE, List.of("ES", "MG", "RJ", "SP"));
        regioes.put(RegiaoDoPais.SUL, List.of("PR", "RS", "SC"));

        for (RegiaoDoPais regiao : regioes.keySet()) {
            if (regioes.get(regiao).contains(estado))
                return regiao;
        }
        return null;

    }
}
