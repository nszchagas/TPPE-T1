package br.unb.venda;

import br.unb.Main;
import br.unb.model.Cliente;
import br.unb.model.Database;
import br.unb.model.Produto;
import br.unb.model.Venda;
import br.unb.model.categorias.CategoriaDeCliente;
import br.unb.model.categorias.MetodoDePagamento;
import br.unb.model.categorias.RegiaoDoEstado;
import br.unb.service.Cadastro;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static br.unb.model.categorias.CategoriaDeCliente.PADRAO;
import static br.unb.model.categorias.CategoriaDeCliente.PRIME;
import static br.unb.model.categorias.RegiaoDoEstado.CAPITAL;
import static br.unb.model.categorias.RegiaoDoEstado.INTERIOR;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CadastroVendasTest {
    final Database db = Database.getInstance();

    // Parâmetros dos testes
    private final String nomeCliente;
    private final String emailCliente;
    private final String estadoCliente;
    private final RegiaoDoEstado regiaoCliente;
    private final CategoriaDeCliente categoriaCliente;
    private final String metodoDePagamento;
    private final String numeroCartao;
    private final String dataVenda;
    private final MetodoDePagamento metodoDePagamentoEsperado;
    private final boolean usarCashback;

    public CadastroVendasTest(
            String nomeCliente, String emailCliente, String estadoCliente, RegiaoDoEstado regiaoCliente, CategoriaDeCliente categoriaCliente,
            String metodoDePagamento, String numeroCartao, String dataVenda, MetodoDePagamento metodoDePagamentoEsperado, boolean usarCashback
    ) {
        this.nomeCliente = nomeCliente;
        this.emailCliente = emailCliente;
        this.estadoCliente = estadoCliente;
        this.regiaoCliente = regiaoCliente;
        this.categoriaCliente = categoriaCliente;
        this.metodoDePagamento = metodoDePagamento;
        this.numeroCartao = numeroCartao;
        this.dataVenda = dataVenda;
        this.metodoDePagamentoEsperado = metodoDePagamentoEsperado;
        this.usarCashback = usarCashback;
    }

    @Parameterized.Parameters(name = "Cliente: {0}, Email: {1}, Estado: {2}, Região: {3}, Categoria: {4}, Pagamento: {5}, Número: {6}, Data: {7}, Cashback: {8}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"João da Silva", "joao.silva@gmail.com", "RS", INTERIOR, PADRAO, "cartao", "4296 1370 0000 0000", "2024-07-07", MetodoDePagamento.CARTAO_LOJA, false},
                {"Maria da Silva", "maria.silva@gmail.com", "RS", CAPITAL, PADRAO, "boleto", "", "2024-07-07", MetodoDePagamento.BOLETO, false},
                {"José da Silva", "jose.silva@gmail.com", "RS", INTERIOR, PRIME, "cartao", "4296 1370 0000 0000", "2024-07-07", MetodoDePagamento.CARTAO_LOJA, true},
        });
    }

    @Test
    public void testCadastrarVenda() {
        Cliente cliente = new Cliente(nomeCliente, categoriaCliente, estadoCliente, regiaoCliente, emailCliente);
        if (usarCashback) {
            cliente.adicionarCashback(20.00); // Adiciona cashback ao cliente
        }

        List<Produto> produtos = List.of(
                new Produto("Produto 1", 10.00, "UNIDADE", "11111"),
                new Produto("Produto 2", 15.00, "UNIDADE", "22222")
        );
        produtos.forEach(Cadastro::insereNoBanco);
        Cadastro.insereNoBanco(cliente);

        StringBuilder entrada = new StringBuilder();
        entrada.append(Main.COD_CADASTRO_VENDA).append('\n')
                .append(emailCliente).append('\n')
                .append(produtos.size()).append('\n');
        for (Produto produto : produtos) {
            entrada.append(produto.getCodigo()).append('\n');
        }
        entrada.append(metodoDePagamento).append('\n');
        if ("cartao".equals(metodoDePagamento)) {
            entrada.append(numeroCartao).append('\n');
        }
        entrada.append(dataVenda).append('\n');
        if (usarCashback) {
            entrada.append("S").append('\n'); // Usar cashback
        }
        entrada.append(Main.COD_SAIR).append('\n');

        ByteArrayInputStream in = new ByteArrayInputStream(entrada.toString().getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Main.main(new String[0]);

        String output = out.toString();
        assertTrue(output.contains("Venda cadastrada"));
        assertTrue(output.contains("Saindo do programa..."));

        Venda vendaCadastrada = db.getVendaByDataCliente(LocalDate.parse(dataVenda), emailCliente);
        assertNotNull(vendaCadastrada);
        assertEquals(emailCliente, vendaCadastrada.getCliente().getEmail());
        assertEquals(produtos.size(), vendaCadastrada.getProdutos().size());
        assertEquals(metodoDePagamentoEsperado, vendaCadastrada.getMetodoDePagamento());
        assertEquals(LocalDate.parse(dataVenda), vendaCadastrada.getData());

        if (usarCashback) {
            double valorTotalProdutos = produtos.stream().mapToDouble(Produto::getValorDeVenda).sum();
            double valorEsperadoComCashback = valorTotalProdutos - cliente.getCashback();
            if (valorEsperadoComCashback < 0) {
                valorEsperadoComCashback = 0;
            }

            assertEquals(valorEsperadoComCashback, vendaCadastrada.getValorFinal(), 0.01);
        }
    }
}
