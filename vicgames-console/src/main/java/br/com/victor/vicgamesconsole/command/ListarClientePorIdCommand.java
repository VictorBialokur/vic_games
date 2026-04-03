package br.com.victor.vicgamesconsole.command;

import br.com.victor.vicgamesconsole.client.ClientHttpConfiguration;
import br.com.victor.vicgamesconsole.dto.ClienteDTO;
import br.com.victor.vicgamesconsole.dto.ListarJogoDto;
import br.com.victor.vicgamesconsole.service.ClienteService;
import br.com.victor.vicgamesconsole.service.ConverteDados;

import java.util.List;
import java.util.Scanner;

public class ListarClientePorIdCommand implements Command {
    private Scanner sc;

    public ListarClientePorIdCommand(Scanner sc) {
        this.sc = sc;
    }

    @Override
    public void execute() {
        try {
            ClientHttpConfiguration client = new ClientHttpConfiguration();
            ConverteDados conversor = new ConverteDados();
            ClienteService service = new ClienteService(client, conversor);

            System.out.println("Digite o Id para pesquisa: ");
            Long id = Long.parseLong(sc.nextLine());

            ClienteDTO cliente = service.listarClientePorId(id);
            List<ListarJogoDto> jogos = service.listarJogosComprados(id);

            System.out.println("Cliente: ");
            System.out.println("\t" + cliente);

            service.mostrarJogosComprados(jogos);

        } catch (NumberFormatException e) {
            System.out.println("Digite um valor numérico válido.");
        } catch (RuntimeException e) {
            System.out.println("Ocorreu um erro.");
        }
    }
}
