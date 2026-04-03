package br.com.victor.vicgamesconsole.command;

import br.com.victor.vicgamesconsole.client.ClientHttpConfiguration;
import br.com.victor.vicgamesconsole.dto.ClienteDTO;
import br.com.victor.vicgamesconsole.dto.ListarJogoDto;
import br.com.victor.vicgamesconsole.service.ClienteService;
import br.com.victor.vicgamesconsole.service.ConverteDados;
import tools.jackson.databind.exc.MismatchedInputException;

import java.util.List;
import java.util.Scanner;

public class DeletarClienteCommand implements Command {
    private Scanner sc;

    public DeletarClienteCommand(Scanner sc) {
        this.sc = sc;
    }

    @Override
    public void execute() {
        try {
            ClientHttpConfiguration client = new ClientHttpConfiguration();
            ConverteDados conversor = new ConverteDados();
            ClienteService service = new ClienteService(client, conversor);

            System.out.println("Digite o Id do usuário para deletar: ");
            Long id = Long.parseLong(sc.nextLine().strip());

            System.out.println("Certeza que quer deletar o usuário com ID " + id + "? (S/N)");
            System.out.println("Não há como desfazer esta ação.");
            String escolha = sc.nextLine().strip().toUpperCase();

            if (escolha.startsWith("S")) {
                service.deletarCliente(id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Digite um valor numérico válido.");
        } catch (RuntimeException e) {
            System.out.println("Ocorreu um erro.");
        }
    }
}
