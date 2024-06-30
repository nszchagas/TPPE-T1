package br.unb.service;
import br.unb.model.Produto;

public class CadastroDeProduto {
    public Produto cadastraProduto(String descricao, String valorDeVenda, String unidade){
        if (descricao == null || descricao.isEmpty() || descricao.trim().isEmpty()) {
            String msg = descricao == null ? "Descrição não pode ser vazia." : String.format("Descrição inválida: \"%s\".", descricao);
            throw new IllegalArgumentException(msg);
        }
        Float valor;
        try {
            if (valorDeVenda.contains(",")) {
                throw new IllegalArgumentException(String.format("Valor inválido. Utilize ponto para separar as casas decimais. Valor inserido: \"%s\".", valorDeVenda));
            }
            valor = Float.valueOf(valorDeVenda);
            assert valor > 0;
        } catch (NullPointerException e)
        {
            throw  new IllegalArgumentException("Valor de venda não pode estar vazio.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Valor de venda deve ser numérico. Valor inserido: \"%s\".", valorDeVenda));
        } catch (AssertionError e) {
            throw new IllegalArgumentException(String.format("Valor de venda deve ser positivo. Valor inserido: \"%s\".", valorDeVenda));
        }


        return new Produto(descricao, valor, unidade);
    }
}
