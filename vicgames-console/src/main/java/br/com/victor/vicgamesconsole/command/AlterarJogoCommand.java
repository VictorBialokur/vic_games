package br.com.victor.vicgamesconsole.command;

import br.com.victor.vicgamesconsole.client.ClientHttpConfiguration;
import br.com.victor.vicgamesconsole.dto.CadastroJogoDTO;
import br.com.victor.vicgamesconsole.service.ConverteDados;
import br.com.victor.vicgamesconsole.service.JogoService;

import java.util.Scanner;

public class AlterarJogoCommand implements Command {
    private Scanner sc;

    public AlterarJogoCommand(Scanner sc) {
        this.sc = sc;
    }

    @Override
    public void execute() {
        try {
            ClientHttpConfiguration client = new ClientHttpConfiguration();
            ConverteDados conversor = new ConverteDados();
            JogoService service = new JogoService(client, conversor);

            System.out.println("Digite o ID do jogo que quer alterar: ");
            long id = Long.parseLong(sc.nextLine().strip());

            System.out.println("Digite o novo nome: ");
            String nome = sc.nextLine().strip();

            System.out.println("Digite o novo preço: ");
            double precoUser = Double.parseDouble(sc.nextLine().strip());
            int preco = (int) (precoUser*100);

            System.out.println("Digite a nova classificação indicativa deste jogo: " +
                    "\nE - Everyone | T - Teen | M - Mature");
            String classificacaoIndicativa = sc.nextLine().strip().toUpperCase();

            System.out.println("Digite a nova quantidade disponível em estoque: ");
            int quantidadeEstoque = Integer.parseInt(sc.nextLine().strip());

            CadastroJogoDTO dto = new CadastroJogoDTO(nome, preco, classificacaoIndicativa, quantidadeEstoque);

            service.alterarJogo(id, dto);
        } catch (NumberFormatException e) {
            System.out.println("Digite um valor numérico válido.");
        } catch (RuntimeException e) {
            System.out.println("Ocorreu um erro.");
        }
    }
}
