package br.unb.service;

public class CadastroDeProduto {
    public void cadastraProduto(String descricao){
        if (descricao == null || descricao.isEmpty() || descricao.trim().isEmpty()) {
            String msg = descricao == null ? "Descrição não pode ser vazia." : String.format("Descrição inválida: \"%s\".", descricao);
            throw new IllegalArgumentException(msg);
        }

    }
}
