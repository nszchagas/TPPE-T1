package br.unb.service;
import br.unb.model.Database;
import br.unb.model.Venda;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Pattern;

public class CadastroDeVenda {
    private final Database db= Database.getInstance();
    public Venda criaVenda(String emailCliente, List<String> produtosId, String metodoDePagamento, String dataInserida){
        LocalDate data;

        try {
            if (dataInserida.isEmpty()) throw new DateTimeParseException("dataInserida", dataInserida, 0);
            data = LocalDate.parse(dataInserida);
            assert ! data.isAfter(LocalDate.now());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(String.format("O valor de data não pode ser nulo: \"%s\"", dataInserida));
        } catch (DateTimeParseException | AssertionError e) {
             String msg = String.format("Formato ou valor de data inválido: \"%s\". Utilize o padrão ISO (Ex: 2024-06-30).", dataInserida);
             throw new IllegalArgumentException(msg);
        }

        // Valida cliente
        if (db.getClienteByEmail(emailCliente) == null)
            throw new IllegalArgumentException(String.format("Nenhum cliente com email \"%s\" encontrado.", emailCliente));

        // Valida produtos
        if (produtosId.isEmpty())
            throw new IllegalArgumentException("A lista de produtos não pode ser vazia.");

        for (String id : produtosId)
            if (db.getProdutoByCodigo(id) == null)
                throw new IllegalArgumentException(String.format("Nenhum produto com código \"%s\" encontrado.", id ));




        return new Venda(emailCliente, produtosId, metodoDePagamento, data);
    }

    public Venda criaVenda(String emailCliente, List<String> produtosId, String metodoDePagamento, String numeroCartao, String dataInserida){
        // Valida método de pagamento
        if (metodoDePagamento == null || metodoDePagamento.isEmpty())
            throw new IllegalArgumentException("Método de pagamento não pode estar vazio.");

        List<String> metodosValidos = List.of("BOLETO", "PIX", "CARTAO", "CARTÃO", "DINHEIRO");
        metodoDePagamento = metodoDePagamento.toUpperCase().replace('Ã','A');
        if (! metodosValidos.contains(metodoDePagamento))
            throw new IllegalArgumentException(String.format("Método de pagamento inválido: \"%s\"", metodoDePagamento));
        if (metodoDePagamento.equals("CARTAO")){
            if (numeroCartao == null)
                throw new IllegalArgumentException("Número do cartão não pode estar vazio.");
            if (! Pattern.compile("^(\\d{4} ){3}\\d{4}$").matcher(numeroCartao).matches()){
                throw new IllegalArgumentException(String.format("Formato inválido para número de cartão: \"%s\"", numeroCartao));
            }
            if (Pattern.compile("^4296 13(\\d| ){12}$").matcher(numeroCartao).matches()){
                metodoDePagamento = "CARTAO_LOJA";
            } else
                metodoDePagamento = "CARTAO_EXTERNO";
        }
        return this.criaVenda(emailCliente, produtosId, metodoDePagamento, dataInserida);
    }
}
