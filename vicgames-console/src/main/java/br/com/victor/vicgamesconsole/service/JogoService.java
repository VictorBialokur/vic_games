package br.com.victor.vicgamesconsole.service;

import br.com.victor.vicgamesconsole.client.ClientHttpConfiguration;
import br.com.victor.vicgamesconsole.dto.CadastroJogoDTO;
import br.com.victor.vicgamesconsole.dto.ErroValidacaoDTO;
import br.com.victor.vicgamesconsole.dto.JogoDTO;

import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;


public class JogoService {
    private ClientHttpConfiguration client;

    private ConverteDados conversor;

    private final String ENDERECO = "http://localhost:8080/jogos";

    public JogoService(ClientHttpConfiguration client, ConverteDados conversor) {
        this.client = client;
        this.conversor = conversor;
    }

    public void mostrarJogos(List<JogoDTO> jogos) {
        System.out.println("Jogos cadastrados: ");
        for(JogoDTO j : jogos) {
            System.out.println(j);
        }
    }

    public List<JogoDTO> listarJogos() throws RuntimeException{
        HttpResponse<String> response = client.dispararRequisicaoGet(ENDERECO);
        JogoDTO[] jogos = conversor.obterDados(response.body(), JogoDTO[].class);
        return Arrays.asList(jogos);
    }

    public void cadastrarJogo(CadastroJogoDTO dto) throws RuntimeException{
        try {
            HttpResponse<String> response = client.dispararRequisicaoPost(ENDERECO, dto);
            int statusCode = response.statusCode();
            if (statusCode == 200) {
                System.out.println("Jogo cadastrado com sucesso!");
                System.out.println(response.body());
            } else if (statusCode == 400 || statusCode == 500) {
                System.out.println("Erro ao cadastrar o jogo.");
                System.out.println(response.body());
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Digite um número válido.");
        } catch (NullPointerException e) {
            throw new RuntimeException("Os campos são obrigatórios.");
        }
    }

    public void alterarJogo(Long idJogo, CadastroJogoDTO dto) throws RuntimeException {
        String uri = ENDERECO + "/" + idJogo;

        HttpResponse<String> response = client.dispararRequisicaoPut(uri, dto);
        int statusCode = response.statusCode();

        if (statusCode == 200) {
            System.out.println("Jogo alterado com sucesso!");
            System.out.println(response.body());
        } else if (statusCode == 400 || statusCode == 500) {
            System.out.println("Erro ao alterar as informações do jogo: ");
            ErroValidacaoDTO[] erros = conversor.obterDados(response.body(), ErroValidacaoDTO[].class);
            List<ErroValidacaoDTO> errosList = Arrays.asList(erros);

            errosList.forEach(e -> System.out.println(e.campo().toUpperCase() + ": " + e.mensagem()));
        }
    }
}
