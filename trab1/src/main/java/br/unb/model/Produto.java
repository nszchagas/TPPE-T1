package br.unb.model;

public class Produto {

    public String descricao;
    public String codigo;
    public float valorDeVenda;
    public String unidade;

    public Produto(String descricao, float valorDeVenda, String unidade, String codigo) {
        this.descricao = descricao;
        this.codigo = codigo;
        this.valorDeVenda = valorDeVenda;
        this.unidade = unidade;
    }
}
