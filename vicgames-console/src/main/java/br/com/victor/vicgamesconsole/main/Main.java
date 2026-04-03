package br.com.victor.vicgamesconsole.main;

import br.com.victor.vicgamesconsole.command.*;

import java.util.Scanner;

public class Main {

    public void executaPrograma() {
        Scanner sc = new Scanner(System.in);
        CommandExecutor executor = new CommandExecutor();
        System.out.println("******* BEM-VINDO AO SISTEMA VIC GAMES CONSOLE *******");
            int opcaoEscolhida = 0;
            while (true) {
                exibeMenu();

                try {
                    opcaoEscolhida = Integer.parseInt(sc.nextLine());

                    switch (opcaoEscolhida) {
                        case 1 -> executor.executeCommand(new ListarClientesCommand());
                        case 2 -> executor.executeCommand(new CadastrarClienteCommand(sc));
                        case 3 -> executor.executeCommand(new ListarClientePorIdCommand(sc));
                        case 4 -> executor.executeCommand(new AlterarClienteCommand(sc));
                        case 5 -> executor.executeCommand(new ComprarJogoCommand(sc));
                        case 6 -> executor.executeCommand(new ListarJogosCommand());
                        case 7 -> executor.executeCommand(new CadastrarJogoCommand(sc));
                        case 8 -> executor.executeCommand(new AlterarJogoCommand(sc));
                        case 9 -> executor.executeCommand(new DeletarClienteCommand(sc));
                        case 0 -> System.exit(0);
                        default -> System.out.println("Escolha uma opção válida.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Digite um número inteiro válido");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
    }

    private void exibeMenu() {
        System.out.println("\nDIGITE O NÚMERO DA OPERAÇÃO DESEJADA:");
        System.out.println("1 - Listar todos os clientes cadastrados");
        System.out.println("2 - Cadastrar um novo cliente");
        System.out.println("3 - Buscar cliente por id");
        System.out.println("4 - Alterar informações de um cliente");
        System.out.println("5 - Comprar um jogo");
        System.out.println("6 - Listar todos os jogos cadastrados");
        System.out.println("7 - Cadastrar um novo jogo");
        System.out.println("8 - Alterar informações de um jogo");
        System.out.println("9 - Excluir um cliente");
        System.out.println("0 - Sair");
    }
}
