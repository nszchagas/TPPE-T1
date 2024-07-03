package br.unb.model;

public class Produto {

    public final String descricao;
    public final String codigo;
    public final float valorDeVenda;
    public final String unidade;

    public Produto(String descricao, float valorDeVenda, String unidade, String codigo) {
        this.descricao = descricao;
        this.codigo = codigo;
        this.valorDeVenda = valorDeVenda;
        this.unidade = unidade;
    }
}
