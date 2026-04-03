package br.com.victor.vicgamesconsole.command;

import br.com.victor.vicgamesconsole.client.ClientHttpConfiguration;
import br.com.victor.vicgamesconsole.dto.ClienteDTO;
import br.com.victor.vicgamesconsole.service.ClienteService;
import br.com.victor.vicgamesconsole.service.ConverteDados;

import java.util.Scanner;

public class CadastrarClienteCommand implements Command {
    private Scanner sc;

    public CadastrarClienteCommand(Scanner sc) {
        this.sc = sc;
    }

    @Override
    public void execute() {
        try {
            ClientHttpConfiguration client = new ClientHttpConfiguration();
            ConverteDados conversor = new ConverteDados();
            ClienteService service = new ClienteService(client, conversor);

            System.out.println("Digite o nome do cliente: ");
            String nome = sc.nextLine().strip();

            System.out.println("Digite o telefone do cliente (Ex:1199999999): ");
            String telefone = sc.nextLine().strip();

            System.out.println("Digite o email do cliente (Ex:email@teste.com): ");
            String email = sc.nextLine().strip();

            System.out.println("Digite o CPF do cliente: ");
            String cpf = sc.nextLine().strip();

            ClienteDTO cliente = new ClienteDTO(nome, telefone, email, cpf);
            service.cadastrarCliente(cliente);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
