package br.unb;

import java.util.Objects;

public class Cliente {
    public String nome, categoria, estado, regiao;

    public Cliente(String nome, String categoria, String estado, String regiao){
        this.nome = nome;
        this.categoria = categoria;
        this.estado = estado;
        this.regiao = regiao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(nome, cliente.nome) && Objects.equals(categoria, cliente.categoria) && Objects.equals(estado, cliente.estado) && Objects.equals(regiao, cliente.regiao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, categoria, estado, regiao);
    }

    @Override
    public String toString() {
        return String.format("{nome: %s, categoria: %s, estado: %s, região: %s}",nome,categoria,estado,regiao);
    }
}
