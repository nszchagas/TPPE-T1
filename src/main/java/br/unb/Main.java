package br.unb;

import br.unb.model.Cliente;
import br.unb.model.Database;
import br.unb.model.Produto;
import br.unb.model.Venda;
import br.unb.model.categorias.CategoriaDeCliente;
import br.unb.model.categorias.MetodoDePagamento;
import br.unb.service.Cadastro;
import br.unb.service.VendaService;
import br.unb.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static br.unb.model.categorias.CategoriaDeCliente.PRIME;
import static br.unb.service.Cadastro.criaProduto;

public class Main {
    public final static String COD_CADASTRO_CLIENTE = "1", COD_CADASTRO_PRODUTO = "2", COD_CADASTRO_VENDA = "3", COD_SAIR = "0";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean sair = false;

        while (!sair) {
            System.out.println("Menu:");
            System.out.printf("%s. Cadastrar Cliente", COD_CADASTRO_CLIENTE);
            System.out.printf("%s. Cadastrar Produto", COD_CADASTRO_PRODUTO);
            System.out.printf("%s. Cadastrar Venda", COD_CADASTRO_VENDA);

            System.out.printf("%s. Sair", COD_SAIR);
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case COD_CADASTRO_CLIENTE:
                    cadastrarCliente(scanner);
                    break;
                case COD_CADASTRO_PRODUTO:
                    cadastrarProduto(scanner);
                    break;
                case COD_CADASTRO_VENDA:
                    cadastrarVenda(scanner);
                    break;
                case COD_SAIR:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        System.out.println("Saindo do programa...");
    }

    private static void cadastrarCliente(Scanner scanner) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Região: ");
        String regiao = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        System.out.print("Categoria: ");
        String categoria = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Cliente cliente = Cadastro.criaCliente(nome, regiao, estado, categoria, email);
        System.out.println("Cliente cadastrado: " + cliente);
    }

    private static void cadastrarProduto(Scanner scanner) {
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Valor de Venda: ");
        String valorDeVenda = scanner.nextLine();
        System.out.print("Unidade: ");
        String unidade = scanner.nextLine();
        System.out.print("Código: ");
        String codigo = scanner.nextLine();

        Produto produto = criaProduto(descricao, valorDeVenda, unidade, codigo);
        System.out.println("Produto cadastrado: " + produto);
    }

    private static void cadastrarVenda(Scanner scanner) {
        System.out.print("Email do Cliente: ");
        String emailCliente = scanner.nextLine();
        CategoriaDeCliente categoria;

        try {
            categoria = Database.getInstance().getClienteByEmail(emailCliente).getCategoria();

        } catch (NullPointerException e) {
            System.err.printf("Nenhum cliente com email %s foi encontrado.", emailCliente);
            return;
        }

        System.out.print("Quantidade de Produtos: ");
        int quantidadeProdutos = Integer.parseInt(scanner.nextLine());
        List<String> produtosId = new ArrayList<>();
        for (int i = 0; i < quantidadeProdutos; i++) {
            System.out.print("Código do Produto " + (i + 1) + ": ");
            String produtoId = scanner.nextLine();
            produtosId.add(produtoId);
        }
        boolean usarCashback = false;
        // PRIME
        if (categoria.equals(PRIME)) {
            System.out.println("Usar cashback no pagamento? (S/N)");
            String l = scanner.nextLine();
            usarCashback = l.equalsIgnoreCase("S");
        }
        System.out.print("Método de Pagamento: ");
        String metodoDePagamento = scanner.nextLine();

        if ("cartao".equalsIgnoreCase(metodoDePagamento)) {
            System.out.print("Número do Cartão: ");
            String numeroCartao = scanner.nextLine();
            System.out.print("Data da Venda (yyyy-MM-dd): ");
            String dataInserida = scanner.nextLine();
            MetodoDePagamento metodo = Validator.getMetodoDePagamento(metodoDePagamento, numeroCartao);
            Venda venda = VendaService.criaVenda(emailCliente, produtosId, metodo, dataInserida);
            System.out.println("Venda cadastrada: " + venda);
        } else {
            System.out.print("Data da Venda (yyyy-MM-dd): ");
            String dataInserida = scanner.nextLine();
            MetodoDePagamento metodo = Validator.getMetodoDePagamento(metodoDePagamento);
            Venda venda = VendaService.criaVenda(emailCliente, produtosId, metodo, dataInserida);
            System.out.println("Venda cadastrada: " + venda);
        }
    }
}

