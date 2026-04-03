package br.com.victor.vicgamesconsole.command;

import br.com.victor.vicgamesconsole.client.ClientHttpConfiguration;
import br.com.victor.vicgamesconsole.dto.CadastroJogoDTO;
import br.com.victor.vicgamesconsole.service.ConverteDados;
import br.com.victor.vicgamesconsole.service.JogoService;

import java.util.Scanner;

public class CadastrarJogoCommand implements Command {
    private Scanner sc;

    public CadastrarJogoCommand(Scanner sc) {
        this.sc = sc;
    }

    @Override
    public void execute() {
        try {
            ClientHttpConfiguration client = new ClientHttpConfiguration();
            ConverteDados conversor = new ConverteDados();
            JogoService service = new JogoService(client, conversor);
            Scanner sc = new Scanner(System.in);

            System.out.println("Digite o nome do jogo: ");
            String nome = sc.nextLine().strip();

            System.out.println("Digite o preço do jogo: ");
            double precoDigitado = Double.parseDouble(sc.nextLine().strip());
            int preco = (int) (precoDigitado*100);

            System.out.println("Digite a classificação indicativa deste jogo: " +
                    "\nE - Everyone | T - Teen | M - Mature");
            String classificacaoIndicativa = sc.nextLine().strip().toUpperCase();

            System.out.println("Digite a quantidade disponível em estoque: ");
            int quantidadeEstoque = Integer.parseInt(sc.nextLine().strip());

            CadastroJogoDTO jogo = new CadastroJogoDTO(nome, preco, classificacaoIndicativa.substring(0,1), quantidadeEstoque);

            service.cadastrarJogo(jogo);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
