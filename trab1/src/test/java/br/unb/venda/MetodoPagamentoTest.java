package br.unb.venda;

import br.unb.model.Venda;
import br.unb.service.CadastroDeVenda;
import br.unb.util.TestUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(Parameterized.class)
public class MetodoPagamentoTest {

    // Se for com cartão de crédito, pegar também o número
    // Inserir cartao loja ou outro cartao, não precisa guardar o número do cartão
    // Se for boleto, pix ou dinheiro não precisa pegar nada
    // Validar tamanho
    // Validar caracteres numéricos
    // Validar verificação loja/externo
        // 4296 13XX XXXX XXXX

    final String entrada;
    final String numero;
    final String saidaEsperada;
    final CadastroDeVenda cadastroDeVenda;
    Venda vendaCadastrada;
    String emailValido, dataValida;
    List<String> idProdutosValidos;
    @BeforeClass
    public static void setUpBeforeClass()  {
        TestUtils.populaBanco();
    }

    @Before
    public void setUp(){
        emailValido = TestUtils.getClientesValidos().get(0).email;
        dataValida = "2023-04-12";
        idProdutosValidos = TestUtils.getCodigosDeProdutosValidos();
        if (saidaEsperada != null)
            vendaCadastrada = cadastroDeVenda.criaVenda(emailValido,
                    idProdutosValidos, entrada, numero, dataValida);


    }
    public MetodoPagamentoTest(String entrada, String numero, String saidaEsperada){
        this.entrada = entrada;
        this.numero = numero;
        this.saidaEsperada = saidaEsperada;
        cadastroDeVenda = new CadastroDeVenda();
    }

   @Parameterized.Parameters(name = "{index}: entrada={0}, numero={1}, saidaEsperada={2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"CARTAO", "4296 1370 0000 0000", "CARTAO_LOJA"},
                {"CARTÃO", "5296 1370 0000 0001", "CARTAO_EXTERNO"},
                {"CARTAO", "4296137000000001", null},
                {"CARTAO", "4296 13XX XXXX XXXX", null},
                {"CARTAO", "5296 13XX XXXX XXXX", null},
                {"CARTAO", "4296-1370-0000-0000", null},
                {"CARTAO", "5296.1370.0000.0002", null},
                {"CARTAO", "4296 1370", null},
                {"CARTAO", "52@6 1370 0000 0001", null},
                {"BOLETO", null, "BOLETO"},
                {"PIX", null, "PIX"},
                {"DINHEIRO", null, "DINHEIRO"},
                {"boleto", "", "BOLETO"},
                {"pix", "", "PIX"},
                {"dinheiro", "", "DINHEIRO"},
                {"gado", "50", null},
                {"", null, null},
                {null, null,null}
        });
    }
    @Test
    public void testaValores(){
       if (saidaEsperada == null) {
           assertThrows(IllegalArgumentException.class, () ->
                   cadastroDeVenda.criaVenda(emailValido, idProdutosValidos, entrada, numero, dataValida)
           );
       } else {
           assertEquals(saidaEsperada, vendaCadastrada.metodoDePagamento);
       }

    }


}
