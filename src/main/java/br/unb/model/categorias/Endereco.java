package br.unb.model.categorias;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Endereco {

    public final String uf;
    public final RegiaoDoEstado regiaoDoEstado;
    public final RegiaoDoPais regiaoDoPais;

    public Endereco(String uf, String regiaoDoEstado) {
        this.uf = uf;
        if (uf.equals("DF"))
            this.regiaoDoEstado = null;
        else
            this.regiaoDoEstado = RegiaoDoEstado.valueOf(regiaoDoEstado.toUpperCase());
        this.regiaoDoPais = getRegiaoDoPais();
    }

    public static RegiaoDoPais getRegiaoDoPais(String uf) {

        HashMap<RegiaoDoPais, List<String>> regioes = new HashMap<>();
        regioes.put(RegiaoDoPais.DF, List.of("DF"));
        regioes.put(RegiaoDoPais.CENTRO_OESTE, List.of("GO", "MT", "MS"));
        regioes.put(RegiaoDoPais.NORDESTE, List.of("AL", "BA", "CE", "MA", "PB", "PE", "PI", "RN", "SE"));
        regioes.put(RegiaoDoPais.NORTE, List.of("AC", "AP", "AM", "PA", "RO", "RR", "TO"));
        regioes.put(RegiaoDoPais.SUDESTE, List.of("ES", "MG", "RJ", "SP"));
        regioes.put(RegiaoDoPais.SUL, List.of("PR", "RS", "SC"));

        for (RegiaoDoPais regiao : regioes.keySet()) {
            if (regioes.get(regiao).contains(uf))
                return regiao;
        }
        return null;
    }

    public RegiaoDoPais getRegiaoDoPais() {
        return getRegiaoDoPais(this.uf);

    }

    public static List<String> getUfsValidas() {
        return List.of("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO");
    }

    public static List<String> getRegioesValidas() {
        List<String> regioes = new ArrayList<>();
        for (RegiaoDoEstado regiaoDoEstado : RegiaoDoEstado.values()) {
            regioes.add(regiaoDoEstado.toString());
        }
        return regioes;
    }

}
