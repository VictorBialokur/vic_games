package br.com.victor.vicgamesconsole.command;

import br.com.victor.vicgamesconsole.client.ClientHttpConfiguration;
import br.com.victor.vicgamesconsole.service.ClienteService;
import br.com.victor.vicgamesconsole.service.ConverteDados;

import java.util.Scanner;

public class ComprarJogoCommand implements Command {
    private Scanner sc;

    public ComprarJogoCommand(Scanner sc) {
        this.sc = sc;
    }

    @Override
    public void execute() {
        try {
            ClientHttpConfiguration client = new ClientHttpConfiguration();
            ConverteDados conversor = new ConverteDados();
            ClienteService service = new ClienteService(client, conversor);

            System.out.println("Digite o ID do cliente que quer comprar: ");
            long idCliente = Long.parseLong(sc.nextLine().strip());

            System.out.println("Digite o ID do jogo a ser comprado: ");
            long idJogo = Long.parseLong(sc.nextLine().strip());

            service.comprarJogo(idCliente, idJogo);
        } catch (NumberFormatException e) {
            System.out.println("Digite um valor numérico válido.");
        } catch (RuntimeException e) {
            System.out.println("Ocorreu um erro.");
        }
    }
}
