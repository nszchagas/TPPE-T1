package br.unb.model;

import java.util.Objects;

public class Produto {

    public final String descricao;
    public final String codigo;
    public final String unidade;
    private final double valorDeVenda;

    public Produto(String descricao, double valorDeVenda, String unidade, String codigo) {
        this.descricao = descricao;
        this.codigo = codigo;
        this.valorDeVenda = valorDeVenda;
        this.unidade = unidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;
        Produto produto = (Produto) o;
        return Objects.equals(codigo, produto.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }

    @Override
    public String toString() {
        return "Produto{descricao='" + descricao + "'}";
    }

    public double getValorDeVenda() {
        return valorDeVenda;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getUnidade() {
        return unidade;
    }
}
