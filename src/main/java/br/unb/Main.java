package br.unb;

import br.unb.model.Cliente;
import br.unb.model.Produto;
import br.unb.service.Cadastro;

import java.util.Scanner;

import static br.unb.service.Cadastro.criaProduto;

public class Main {
    public static String COD_CADASTRO_CLIENTE = "1", COD_CADASTRO_PRODUTO = "2", COD_SAIR = "0";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean sair = false;

        while (!sair) {
            System.out.println("Menu:");
            System.out.printf("%s. Cadastrar Cliente", COD_CADASTRO_CLIENTE);
            System.out.printf("%s. Cadastrar Produto", COD_CADASTRO_PRODUTO);
            System.out.printf("%s. Sair", COD_SAIR);
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    cadastrarCliente(scanner);
                    break;
                case "2":
                    cadastrarProduto(scanner);
                    break;
                case "0":
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



}
