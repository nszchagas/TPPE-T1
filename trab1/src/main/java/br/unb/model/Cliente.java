package br.unb.model;

import br.unb.model.categorias.CategoriaDeCliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cliente {
    public String nome, categoria, estado, regiao, email;

    public Cliente(String nome, String categoria, String estado, String regiao, String email) {
        this.nome = nome;
        this.categoria = categoria;
        this.estado = estado;
        this.regiao = regiao;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(email, cliente.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, categoria, estado, regiao);
    }

    @Override
    public String toString() {
        return String.format("{nome: %s, categoria: %s, estado: %s, região: %s}", nome, categoria, estado, regiao);
    }

    public static List<String> getCategoriasValidas() {
        List<String> categorias = new ArrayList<>();
        for (CategoriaDeCliente c : CategoriaDeCliente.values())
            categorias.add(c.toString());
        return categorias;
    }
}
