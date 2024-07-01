package br.unb.service;
import br.unb.model.Venda;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CadastroDeVenda {
    public Venda criaVenda(String clienteId, String[] itensId, String metodoDePagamento, String dataInserida){
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


        return new Venda(clienteId, itensId, metodoDePagamento, data);
    }
}
