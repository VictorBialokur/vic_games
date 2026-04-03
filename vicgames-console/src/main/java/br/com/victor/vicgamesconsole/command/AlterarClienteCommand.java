package br.com.victor.vicgamesconsole.command;

import br.com.victor.vicgamesconsole.client.ClientHttpConfiguration;
import br.com.victor.vicgamesconsole.dto.ClienteDTO;
import br.com.victor.vicgamesconsole.service.ClienteService;
import br.com.victor.vicgamesconsole.service.ConverteDados;

import java.util.Scanner;

public class AlterarClienteCommand implements Command {
    private Scanner sc;

    public AlterarClienteCommand(Scanner sc) {
        this.sc = sc;
    }

    @Override
    public void execute() {
        try {
            ClientHttpConfiguration client = new ClientHttpConfiguration();
            ConverteDados conversor = new ConverteDados();
            ClienteService service = new ClienteService(client, conversor);

            System.out.println("Digite o ID do cliente que quer alterar: ");
            long id = Long.parseLong(sc.nextLine().strip());

            System.out.println("Digite o novo nome: ");
            String nome = sc.nextLine().strip();

            System.out.println("Digite o novo telefone: ");
            String telefone = sc.nextLine().strip();

            System.out.println("Digite o novo email: ");
            String email = sc.nextLine().strip();

            System.out.println("Digite o novo CPF: ");
            String cpf = sc.nextLine().strip();

            ClienteDTO dto = new ClienteDTO(nome, telefone, email, cpf);

            service.alterarCliente(id, dto);
        } catch (NumberFormatException e) {
            System.out.println("Digite um valor numérico válido.");
        } catch (RuntimeException e) {
            System.out.println("Ocorreu um erro.");
        }
    }
}
