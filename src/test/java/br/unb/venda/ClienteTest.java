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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.MockedStatic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.*;

import static br.unb.Main.COD_CADASTRO_VENDA;
import static br.unb.Main.COD_SAIR;
import static br.unb.model.categorias.CategoriaDeCliente.PADRAO;
import static br.unb.model.categorias.RegiaoDoEstado.CAPITAL;
import static br.unb.service.Cadastro.criaVenda;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class ClienteTest {

    final String entrada;
    final boolean isValid;

    final List<String> itens = List.of("123");
    Venda vendaCriada;
    Cliente clienteCriado;
    HashMap<String, Cliente> clientesByEmail = new HashMap<>();
    HashMap<String, Produto> produtosByCodigo = new HashMap<>();

    public ClienteTest(String entrada, boolean isValid) {
        this.entrada = entrada;
        this.isValid = isValid;

        clientesByEmail.put("email1@domain.com", new Cliente("Nome", PADRAO, "BA", CAPITAL, "email1@domain.com"));

        produtosByCodigo.put("123", mock(Produto.class));


    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"email_nao_cadastrado@gmail.com", false},
                {"email1@domain.com", true}
        });
    }

    @Before
    public void setUp() {

        try (MockedStatic<Database> mockedStatic = mockStatic(Database.class)) {
            Database db = mock(Database.class);

            when(db.getClienteByEmail(anyString()))
                    .thenAnswer(
                            (context) ->
                                    clientesByEmail.get((String) context.getArgument(0))
                    );

            when(db.getProdutoByCodigo(anyString()))
                    .thenAnswer(
                            (context) ->
                                    produtosByCodigo.get((String) context.getArgument(0)));

            mockedStatic.when(Database::getInstance).thenReturn(db);

            if (isValid) {
                vendaCriada = criaVenda(entrada, itens, "BOLETO", "2024-07-01");
                clienteCriado = vendaCriada.getCliente();
                assertNotNull(clienteCriado);
            }

        }
    }

    @Test
    public void testValoresClienteId() {
        if (!isValid) {
            assertThrows(IllegalArgumentException.class,
                    () ->
                            criaVenda(entrada, itens, "BOLETO", "2024-07-01")
            );
        } else {
            assertEquals(vendaCriada.getCliente().email, entrada);
        }
    }

    @Test
    public void testValoresEmailCliente() {
        if (!isValid) {
            assertThrows(IllegalArgumentException.class,
                    () ->
                            criaVenda(entrada, itens, "BOLETO", "2024-07-01")
            );
        } else {
            assertEquals(clienteCriado.email, entrada);
        }
    }

//    public static class CadastroVendaTest {
//
//
//        @Test
//        public void testCadastrarVendaComCartao() {
//            String nomeCliente = "João da Silva";
//            String emailCliente = "joao.silva@gmail.com";
//            String estadoCliente = "RS";
//            RegiaoDoEstado regiaoCliente = RegiaoDoEstado.INTERIOR;
//            CategoriaDeCliente categoriaCliente = PADRAO;
//
//            Cliente cliente = new Cliente(nomeCliente, categoriaCliente, estadoCliente, regiaoCliente, emailCliente);
//
//            List<Produto> produtos = List.of(
//                    new Produto("Produto 1", 10.00, "UNIDADE", "11111"),
//                    new Produto("Produto 2", 15.00, "UNIDADE", "22222")
//            );
//            produtos.forEach(Cadastro::insereNoBanco);
//            Cadastro.insereNoBanco(cliente);
//            String metodoDePagamento = "cartao";
//            String numeroCartao = "4296 1370 0000 0000";
//            String dataVenda = "2024-07-07";
//
//            LocalDate dataVendaEsperada = LocalDate.of(2024, 7, 7);
//            MetodoDePagamento metodoDePagamentoEsperado = MetodoDePagamento.CARTAO_LOJA;
//
//            StringBuilder entrada = new StringBuilder();
//            entrada.append(COD_CADASTRO_VENDA).append('\n')
//                    .append(emailCliente).append('\n')
//                    .append(produtos.size()).append('\n');
//            for (Produto produto : produtos) {
//                entrada.append(produto.getCodigo()).append('\n');
//            }
//            entrada.append(metodoDePagamento).append('\n')
//                    .append(numeroCartao).append('\n')
//                    .append(dataVenda).append('\n')
//                    .append(COD_SAIR).append('\n');
//
//            ByteArrayInputStream in = new ByteArrayInputStream(entrada.toString().getBytes());
//            System.setIn(in);
//
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            System.setOut(new PrintStream(out));
//
//            Main.main(new String[0]);
//
//            String output = out.toString();
//            assertTrue(output.contains("Venda cadastrada"));
//            assertTrue(output.contains("Saindo do programa..."));
//
//            Venda vendaCadastrada = Database.getInstance().getVendaByDataCliente(dataVendaEsperada, emailCliente);
//            assertNotNull(vendaCadastrada);
//            assertEquals(emailCliente, vendaCadastrada.getCliente().getEmail());
//            assertEquals(produtos.size(), vendaCadastrada.getProdutos().size());
//            assertEquals(metodoDePagamentoEsperado, vendaCadastrada.getMetodoDePagamento());
//            assertEquals(LocalDate.parse(dataVenda), vendaCadastrada.getData());
//        }
//
//        @Test
//        public void testCadastrarVendaSemCartao() {
//            String nomeCliente = "João da Silva";
//            String emailCliente = "joao.silva@gmail.com";
//            String estadoCliente = "RS";
//            RegiaoDoEstado regiaoCliente = RegiaoDoEstado.INTERIOR;
//            CategoriaDeCliente categoriaCliente = PADRAO;
//
//            Cliente cliente = new Cliente(nomeCliente, categoriaCliente, estadoCliente, regiaoCliente, emailCliente);
//
//            List<Produto> produtos = List.of(
//                    new Produto("Produto 1", 10.00, "UNIDADE", "11111"),
//                    new Produto("Produto 2", 15.00, "UNIDADE", "22222")
//            );
//            produtos.forEach(Cadastro::insereNoBanco);
//            Cadastro.insereNoBanco(cliente);
//
//            String metodoDePagamento = "boleto";
//            String dataVenda = "2024-07-07";
//
//            LocalDate dataVendaEsperada = LocalDate.of(2024, 7, 7);
//            MetodoDePagamento metodoDePagamentoEsperado = MetodoDePagamento.BOLETO;
//
//            StringBuilder entrada = new StringBuilder();
//            entrada.append(COD_CADASTRO_VENDA).append('\n')
//                    .append(emailCliente).append('\n')
//                    .append(produtos.size()).append('\n');
//            for (Produto produto : produtos) {
//                entrada.append(produto.getCodigo()).append('\n');
//            }
//            entrada.append(metodoDePagamento).append('\n')
//                    .append(dataVenda).append('\n')
//                    .append(COD_SAIR).append('\n');
//
//            ByteArrayInputStream in = new ByteArrayInputStream(entrada.toString().getBytes());
//            System.setIn(in);
//
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            System.setOut(new PrintStream(out));
//
//            Main.main(new String[0]);
//
//            String output = out.toString();
//            assertTrue(output.contains("Venda cadastrada"));
//            assertTrue(output.contains("Saindo do programa..."));
//
//            Venda vendaCadastrada = Database.getInstance().getVendaByDataCliente(dataVendaEsperada, emailCliente);
//            assertNotNull(vendaCadastrada);
//            assertEquals(emailCliente, vendaCadastrada.getCliente().getEmail());
//            assertEquals(produtos.size(), vendaCadastrada.getProdutos().size());
//            assertEquals(metodoDePagamentoEsperado, vendaCadastrada.getMetodoDePagamento());
//            assertEquals(LocalDate.parse(dataVenda), vendaCadastrada.getData());
//        }
//
//        @Test
//        public void testCadastrarVendaComCartaoEUsarCashback() {
//            String nomeCliente = "João da Silva";
//            String emailCliente = "joao.silva@gmail.com";
//            String estadoCliente = "RS";
//            RegiaoDoEstado regiaoCliente = RegiaoDoEstado.INTERIOR;
//            CategoriaDeCliente categoriaCliente = CategoriaDeCliente.PRIME; // Cliente Prime
//
//            Cliente cliente = new Cliente(nomeCliente, categoriaCliente, estadoCliente, regiaoCliente, emailCliente);
//            cliente.adicionarCashback(20.00); // Adiciona cashback ao cliente
//
//            List<Produto> produtos = List.of(
//                    new Produto("Produto 1", 10.00, "UNIDADE", "11111"),
//                    new Produto("Produto 2", 15.00, "UNIDADE", "22222")
//            );
//            produtos.forEach(Cadastro::insereNoBanco);
//            Cadastro.insereNoBanco(cliente);
//
//            String metodoDePagamento = "cartao";
//            String numeroCartao = "4296 1370 0000 0000";
//            String dataVenda = "2024-07-07";
//
//            LocalDate dataVendaEsperada = LocalDate.of(2024, 7, 7);
//            MetodoDePagamento metodoDePagamentoEsperado = MetodoDePagamento.CARTAO_LOJA;
//
//            StringBuilder entrada = new StringBuilder();
//            entrada.append(COD_CADASTRO_VENDA).append('\n')
//                    .append(emailCliente).append('\n')
//                    .append(produtos.size()).append('\n');
//            for (Produto produto : produtos) {
//                entrada.append(produto.getCodigo()).append('\n');
//            }
//            entrada.append(metodoDePagamento).append('\n')
//                    .append(numeroCartao).append('\n')
//                    .append(dataVenda).append('\n')
//                    .append("S").append('\n') // Usar cashback
//                    .append(COD_SAIR).append('\n');
//
//            ByteArrayInputStream in = new ByteArrayInputStream(entrada.toString().getBytes());
//            System.setIn(in);
//
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            System.setOut(new PrintStream(out));
//
//            Main.main(new String[0]);
//
//            String output = out.toString();
//            assertTrue(output.contains("Venda cadastrada"));
//            assertTrue(output.contains("Saindo do programa..."));
//
//            Venda vendaCadastrada = Database.getInstance().getVendaByDataCliente(dataVendaEsperada, emailCliente);
//            assertNotNull(vendaCadastrada);
//            assertEquals(emailCliente, vendaCadastrada.getCliente().getEmail());
//            assertEquals(produtos.size(), vendaCadastrada.getProdutos().size());
//            assertEquals(metodoDePagamentoEsperado, vendaCadastrada.getMetodoDePagamento());
//            assertEquals(LocalDate.parse(dataVenda), vendaCadastrada.getData());
//
//            double valorTotalProdutos = produtos.stream().mapToDouble(Produto::getValorDeVenda).sum();
//            double valorEsperadoComCashback = valorTotalProdutos - cliente.getCashback();
//            if (valorEsperadoComCashback < 0) {
//                valorEsperadoComCashback = 0;
//            }
//
//            assertEquals(valorEsperadoComCashback, vendaCadastrada.getValorFinal(), 0.01);
//        }
//
//    }
}
