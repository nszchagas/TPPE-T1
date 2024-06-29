package br.unb; 

public class CadastroDeCliente{
    public void cadastraCliente(String nome, String regiao, String estado, String categoria) {
        // Categorias validas: padrao, especial, prime 
        if (! (categoria.equals("padrao") || categoria.equals("especial") || categoria.equals("prime"))) {
            throw new IllegalArgumentException(String.format("Categoria inválida: %s.", categoria));
        }
    }
}
