package br.unb;

import br.unb.model.Cliente;
import br.unb.service.Cadastro;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean sair = false;

        while (!sair) {
            System.out.println("Menu:");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("q. Sair");
            System.out.print("Escolha uma opção: ");

            char opcao = scanner.next().charAt(0);
            scanner.nextLine();

            switch (opcao) {
                case '1':
                    cadastrarCliente(scanner);
                    break;

                case 'q':
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


}
