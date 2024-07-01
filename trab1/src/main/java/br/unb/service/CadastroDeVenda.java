package br.unb.service;
import br.unb.model.Database;
import br.unb.model.Venda;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class CadastroDeVenda {
    public Venda criaVenda(String emailCliente, List<String> itensId, String metodoDePagamento, String dataInserida){
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
        if ( Database.getInstance().getClienteByEmail(emailCliente) == null)
            throw new IllegalArgumentException(String.format("Nenhum cliente com email \"%s\" encontrado.", emailCliente));

        return new Venda(emailCliente, itensId, metodoDePagamento, data);
    }
}
