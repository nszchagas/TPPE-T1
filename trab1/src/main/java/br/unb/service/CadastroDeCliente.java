package br.unb.service;

import br.unb.model.Cliente;
import br.unb.model.Database;
import br.unb.model.categorias.Endereco;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.List;

public class CadastroDeCliente {
    private Database db = Database.getInstance();

    public Cliente cadastraCliente(String nome, String regiao, String estado, String categoria, String email) {
        List<String> categoriasValidas = Cliente.getCategoriasValidas();
        List<String> estadosValidos = Endereco.getUfsValidas();
        List<String> regioesValidas = Endereco.getRegioesValidas();

        String regiao_inserida = regiao;
        String categoria_inserida = categoria;
        categoria = categoria.toUpperCase().trim();
        estado = estado.trim().toUpperCase();
        regiao = regiao.trim().toUpperCase();

        if (!categoriasValidas.contains(categoria)) {
            throw new IllegalArgumentException(String.format("Categoria inválida: \"%s\".", categoria_inserida));
        }
        if (estado.length() != 2) {
            throw new IllegalArgumentException("Insira a sigla do estado.");
        }
        if (!estadosValidos.contains(estado)) {
            throw new IllegalArgumentException(String.format("Estado inválido: %s.", estado));
        }
        if (!regioesValidas.contains(regiao)) {
            throw new IllegalArgumentException(String.format("Região inválida: \"%s\".", regiao_inserida));
        }
        // Validação de email
        if (!EmailValidator.getInstance().isValid(email))
            throw new IllegalArgumentException(String.format("Email inválido: \"%s\"", email));
        return new Cliente(nome, categoria, estado, regiao, email);
    }

    public void insereClienteNoBanco(Cliente cliente) {
        try {
            db.insereCliente(cliente);
        } catch (Exception e) {
            System.out.printf("Cliente já inserido no banco de dados. %s\n", cliente.toString());
        }
    }

    ;
}
