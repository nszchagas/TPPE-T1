package br.unb.service;
import br.unb.model.Produto;
import br.unb.model.UnidadeValida;

public class CadastroDeProduto {
    public Produto cadastraProduto(String descricao, String valorDeVenda, String unidade, String codigo){
        Float valor;

        // Validação de Descrição
        descricao = validaCampoTextual(descricao, "descrição");

        // Validação do Código
        codigo = validaCampoTextual(codigo, "código");


        // Validação de valor

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

        // Validação de Unidade
        String unidadeNormalizada;
        try {
            unidadeNormalizada = UnidadeValida.normaliza(unidade);
            if (unidadeNormalizada == null)
                throw new NullPointerException();


        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Unidade não pode estar vazia.");
        } catch (IllegalArgumentException e){
            throw  new IllegalArgumentException(String.format("Unidade inválida. Valor inserido: \"%s\".", unidade));
        }


        return new Produto(descricao, valor, unidadeNormalizada, codigo);
    }


    private String validaCampoTextual(String entrada, String tipo){
          if (entrada == null || entrada.isEmpty() || entrada.trim().isEmpty()) {
            String msg = entrada == null ? String.format("O valor de %s não pode ser vazio.", tipo) : String.format("Valor de %s inválido: \"%s\".", tipo ,entrada);
            throw new IllegalArgumentException(msg);
        }
        return entrada.trim();
    }

    public void insereProdutoNoBanco(Produto produto){

    }

}
