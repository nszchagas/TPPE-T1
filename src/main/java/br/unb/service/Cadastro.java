package br.unb.service;

import br.unb.model.*;
import br.unb.model.categorias.CategoriaDeCliente;
import br.unb.model.categorias.Endereco;
import br.unb.model.categorias.RegiaoDoEstado;
import br.unb.util.Validator;
import org.apache.commons.validator.routines.EmailValidator;


public class Cadastro {
    private Cadastro() {

    }


    public static Cliente criaCliente(String nome, String regiaoInserida, String estado, String categoriaInserida, String email) {


        CategoriaDeCliente categoria = (CategoriaDeCliente) Validator.validaEnum(categoriaInserida, "Categoria");
        RegiaoDoEstado regiao = (RegiaoDoEstado) Validator.validaEnum(regiaoInserida, "Regiao");

        if (!Endereco.isUfValida(estado)) {
            throw new IllegalArgumentException(String.format("Estado inválido: %s.", estado));
        }

        // Validação de email
        if (!EmailValidator.getInstance().isValid(email))
            throw new IllegalArgumentException(String.format("Email inválido: \"%s\"", email));

        Cliente cliente = new Cliente(nome, categoria, estado.trim().toUpperCase(), regiao, email);
        Database.insereNoBanco(cliente);
        return cliente;
    }

    public static Produto criaProduto(String descricao, String valorDeVenda, String unidade, String codigo) {

        // Validação de Descrição
        String descricaoValidada = Validator.validaCampoTextual(descricao, "descrição");

        // Validação do Código
        String codigoValidado = Validator.validaCampoTextual(codigo, "código");

        // Validação de valor
        double valor = Validator.validaDouble(valorDeVenda);

        // Validação de Unidade
        String unidadeNormalizada = Validator.validaUnidadeDeMedida(unidade);

        Produto produto = new Produto(descricaoValidada, valor, unidadeNormalizada, codigoValidado);
        Database.insereNoBanco(produto);
        return produto;
    }


}
