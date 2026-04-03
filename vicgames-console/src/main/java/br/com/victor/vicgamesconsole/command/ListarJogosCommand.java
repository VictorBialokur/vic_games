package br.com.victor.vicgamesconsole.command;

import br.com.victor.vicgamesconsole.client.ClientHttpConfiguration;
import br.com.victor.vicgamesconsole.dto.JogoDTO;
import br.com.victor.vicgamesconsole.dto.ListarJogoDto;
import br.com.victor.vicgamesconsole.service.ConverteDados;
import br.com.victor.vicgamesconsole.service.JogoService;

import java.util.List;

public class ListarJogosCommand implements Command {
    @Override
    public void execute() {
        ClientHttpConfiguration client = new ClientHttpConfiguration();
        ConverteDados conversor = new ConverteDados();
        JogoService service = new JogoService(client, conversor);

        try {
            List<JogoDTO> jogos = service.listarJogos();

            if (jogos.isEmpty()) {
                System.out.println("Nenhum jogo cadastrado.");
            } else {
                service.mostrarJogos(jogos);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
