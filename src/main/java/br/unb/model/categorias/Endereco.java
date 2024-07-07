package br.unb.model.categorias;

import java.util.HashMap;
import java.util.List;

public class Endereco {

    public final String uf;
    public final RegiaoDoEstado regiaoDoEstado;

    public Endereco(String uf, RegiaoDoEstado regiaoDoEstado) {
        this.uf = uf;
        if (uf.equals("DF"))
            this.regiaoDoEstado = null;
        else
            this.regiaoDoEstado = regiaoDoEstado;
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

    public static boolean isUfValida(String uf) {
        if (uf.trim().length() != 2) {
            throw new IllegalArgumentException("Insira a sigla do estado.");
        }
        List<String> ufs = List.of("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO");
        return ufs.contains(uf.toUpperCase().trim());
    }

    public RegiaoDoPais getRegiaoDoPais() {
        return getRegiaoDoPais(this.uf);

    }

}
