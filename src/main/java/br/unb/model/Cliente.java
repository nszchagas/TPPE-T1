package br.unb.model;

import br.unb.model.categorias.CategoriaDeCliente;
import br.unb.model.categorias.Endereco;
import br.unb.model.categorias.RegiaoDoEstado;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cliente {
    public final String nome;
    public final CategoriaDeCliente categoria;
    public final String email;
    public final Endereco endereco;

    public Cliente(String nome, CategoriaDeCliente categoria, String estado, RegiaoDoEstado regiao, String email) {
        this.nome = nome;
        this.categoria = categoria;
        this.email = email;
        this.endereco = new Endereco(estado, regiao);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(email, cliente.email);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getEstado() {
        return endereco.uf;
    }

    public RegiaoDoEstado getRegiao() {
        return endereco.regiaoDoEstado;
    }

    public CategoriaDeCliente getCategoria() {
        return categoria;
    }

}
