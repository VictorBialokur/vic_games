package br.com.victor.vicgamesconsole.command;

import br.com.victor.vicgamesconsole.client.ClientHttpConfiguration;
import br.com.victor.vicgamesconsole.dto.ListarClientesDTO;
import br.com.victor.vicgamesconsole.service.ClienteService;
import br.com.victor.vicgamesconsole.service.ConverteDados;

import java.util.List;

public class ListarClientesCommand implements Command {
    @Override
    public void execute() {
        try {
            ClientHttpConfiguration client = new ClientHttpConfiguration();
            ConverteDados conversor = new ConverteDados();
            ClienteService service = new ClienteService(client, conversor);

            List<ListarClientesDTO> clientes = service.listarClientes();

            if (clientes.isEmpty()) {
                System.out.println("Não há clientes cadastrados.");
            } else {
                service.mostrarClientes(clientes);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
