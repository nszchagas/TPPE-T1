package br.unb.database;

import br.unb.model.Cliente;
import br.unb.model.Database;
import br.unb.model.Produto;
import br.unb.model.Venda;
import br.unb.model.categorias.CategoriaDeCliente;
import br.unb.model.categorias.MetodoDePagamento;
import br.unb.model.categorias.RegiaoDoEstado;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.stubbing.Answer;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static br.unb.model.categorias.CategoriaDeCliente.ESPECIAL;
import static br.unb.model.categorias.CategoriaDeCliente.PADRAO;
import static br.unb.model.categorias.MetodoDePagamento.CARTAO_LOJA;
import static br.unb.model.categorias.RegiaoDoEstado.CAPITAL;
import static br.unb.model.categorias.RegiaoDoEstado.INTERIOR;
import static br.unb.service.Cadastro.insereNoBanco;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@Ignore

public class InsercaoVendaTest {

    // Testa com valores válidos e confere os cálculos
    @Test
    public void testaInsercaoNoBanco() {
        HashMap<String, Double> precosPorProduto = new HashMap<>();
        precosPorProduto.put("789012", 123.74);
        precosPorProduto.put("345678", 250.2);
        precosPorProduto.put("654321", 148.9);
        precosPorProduto.put("210987", 470.0);


        // Clientes válidos | Dos clientes só importam email, estado, regiao e categoria
        HashMap<String, Object[]> dadosPorEmail = new HashMap<>();
        dadosPorEmail.put("email1@gmail.com", new Object[]{"BA", INTERIOR, PADRAO});
        dadosPorEmail.put("email2@gmail.com", new Object[]{"BA", CAPITAL, PADRAO});
        dadosPorEmail.put("email3@gmail.com", new Object[]{"BA", INTERIOR, ESPECIAL});
        dadosPorEmail.put("email4@gmail.com", new Object[]{"BA", CAPITAL, ESPECIAL});
        try (MockedStatic<Database> mockedStatic = mockStatic(Database.class)) {

            Database db = mock(Database.class);

            // Adiciona os produtos:
            for (String codigo : precosPorProduto.keySet()) {
                when(db.getProdutoByCodigo(codigo)).thenAnswer((Answer<Produto>) invocationOnMock -> {
                    Produto mockProduto = mock(Produto.class);
                    when(mockProduto.getValorDeVenda()).thenReturn(precosPorProduto.get(codigo));
                    return mockProduto;
                });
            }

            for (String email : precosPorProduto.keySet()) {
                when(db.getClienteByEmail(email)).thenAnswer((Answer<Cliente>) invocationOnMock -> {
                    Cliente mockCliente = mock(Cliente.class);
                    String estado = (String) dadosPorEmail.get(email)[0];
                    RegiaoDoEstado regiao = (RegiaoDoEstado) dadosPorEmail.get(email)[1];
                    CategoriaDeCliente categoria = (CategoriaDeCliente) dadosPorEmail.get(email)[2];
                    when(mockCliente.getEstado()).thenReturn(estado);
                    when(mockCliente.getRegiao()).thenReturn(regiao);
                    when(mockCliente.getCategoria()).thenReturn(categoria);

                    return mockCliente;
                });
            }

            when(db.insereVenda(any())).thenCallRealMethod();
            when(db.getVendaById(anyInt())).thenCallRealMethod();

            mockedStatic.when(Database::getInstance).thenReturn(db);


            // Contexto da venda
            String email = "email1@gmail.com";
            List<String> codProdutos = List.of("789012", "345678");
            MetodoDePagamento metodoDePagamento = CARTAO_LOJA;
            LocalDate data = LocalDate.of(2024, 7, 3);
            Venda venda = new Venda(null, null, metodoDePagamento, data);

            int id = insereNoBanco(venda);

            Venda inserida = db.getVendaById(id);
            assertEquals(email, inserida.getCliente().email);


            // Cadastro de venda recebe:
            //  cliente (email)
            //  data
            //  itens (códigos)
            //  método de pagamento


            // O cadastro >calcula<:
            //  Frete
            //  Descontos
            //  ICMS
            //  Imposto municipal

        } catch (SQLIntegrityConstraintViolationException e) {
            fail();
        }
    }

}




