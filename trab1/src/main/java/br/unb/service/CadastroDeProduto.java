package br.unb.service;
import br.unb.model.Produto;

public class CadastroDeProduto {
    public Produto cadastraProduto(String descricao, String valorDeVenda){
        if (descricao == null || descricao.isEmpty() || descricao.trim().isEmpty()) {
            String msg = descricao == null ? "Descrição não pode ser vazia." : String.format("Descrição inválida: \"%s\".", descricao);
            throw new IllegalArgumentException(msg);
        }
        return new Produto();
    }
}
