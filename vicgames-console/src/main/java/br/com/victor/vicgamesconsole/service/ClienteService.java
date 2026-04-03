package br.com.victor.vicgamesconsole.service;

import br.com.victor.vicgamesconsole.client.ClientHttpConfiguration;
import br.com.victor.vicgamesconsole.dto.ClienteDTO;
import br.com.victor.vicgamesconsole.dto.ErroValidacaoDTO;
import br.com.victor.vicgamesconsole.dto.ListarClientesDTO;
import br.com.victor.vicgamesconsole.dto.ListarJogoDto;

import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;


public class ClienteService {
    private ClientHttpConfiguration client;

    private ConverteDados conversor;

    private final String ENDERECO = "http://localhost:8080/clientes";

    public ClienteService(ClientHttpConfiguration client, ConverteDados conversor) {
        this.client = client;
        this.conversor = conversor;
    }

    public void mostrarClientes(List<ListarClientesDTO> clientes) {
        System.out.println("Clientes cadastrados: ");
        for (ListarClientesDTO c : clientes) {
            System.out.println(c);
        }
    }

    public void mostrarJogosComprados(List<ListarJogoDto> jogos) {
        System.out.println("Jogos comprados: ");
        for (ListarJogoDto j : jogos) {
            System.out.println("\t" + j);
        }
    }

    public List<ListarClientesDTO> listarClientes() throws RuntimeException {
        HttpResponse<String> response = client.dispararRequisicaoGet(ENDERECO);
        ListarClientesDTO[] clientes = conversor.obterDados(response.body(), ListarClientesDTO[].class);
        return Arrays.asList(clientes);
    }

    public ClienteDTO listarClientePorId(Long idCliente) throws RuntimeException {
        String uri = ENDERECO + "/" + idCliente;
        HttpResponse<String> response = client.dispararRequisicaoGet(uri);
        return conversor.obterDados(response.body(), ClienteDTO.class);
    }

    public List<ListarJogoDto> listarJogosComprados(Long idCliente) throws RuntimeException {
        String uri = ENDERECO + "/" + idCliente + "/comprados";
        HttpResponse<String> response = client.dispararRequisicaoGet(uri);
        ListarJogoDto[] jogos = conversor.obterDados(response.body(), ListarJogoDto[].class);
        return Arrays.asList(jogos);
    }

    public void cadastrarCliente(ClienteDTO dto) throws RuntimeException {
        HttpResponse<String> response = client.dispararRequisicaoPost(ENDERECO, dto);
        int statusCode = response.statusCode();
        if (statusCode == 200) {
            System.out.println("Cliente cadastrado com sucesso!");
            System.out.println(response.body());
        } else if (statusCode == 400 || statusCode == 500) {
            System.out.println("Erro ao cadastrar o cliente: ");
            ErroValidacaoDTO[] erros = conversor.obterDados(response.body(), ErroValidacaoDTO[].class);
            List<ErroValidacaoDTO> errosList = Arrays.asList(erros);

            errosList.forEach(e -> System.out.println(e.campo().toUpperCase() + ": " + e.mensagem()));
        }
    }

    public void alterarCliente(Long idCliente, ClienteDTO dto) throws RuntimeException {
        String uri = ENDERECO + "/" + idCliente;
        HttpResponse<String> response = client.dispararRequisicaoPut(uri, dto);
        int statusCode = response.statusCode();

        if (statusCode == 200) {
            System.out.println("Cliente alterado com sucesso!");
            System.out.println(response.body());
        } else if (statusCode == 400 || statusCode == 500) {
            System.out.println("Erro ao alterar o cliente: ");
            ErroValidacaoDTO[] erros = conversor.obterDados(response.body(), ErroValidacaoDTO[].class);
            List<ErroValidacaoDTO> errosList = Arrays.asList(erros);

            errosList.forEach(e -> System.out.println(e.campo().toUpperCase() + ": " + e.mensagem()));
        }
    }

    public void comprarJogo(Long idCliente, Long idJogo) throws RuntimeException {
        String uri = ENDERECO + "/" + idCliente + "/comprar";
        HttpResponse<String> response = client.dispararRequisicaoPut(uri, idJogo);
        int statusCode = response.statusCode();

        if (statusCode == 200) {
            System.out.println("Jogo comprado com sucesso.");
            System.out.println(response.body());
        } else if (statusCode == 400 || statusCode == 500) {
            System.out.println("Erro ao comprar o jogo: ");
            System.out.println(response.body());
        }
    }

    public void deletarCliente(Long idCliente) throws RuntimeException {
        String uri = ENDERECO + "/" + idCliente + "/delete";

        HttpResponse<String> response = client.dispararRequisicaoDelete(uri, idCliente);
        int statusCode = response.statusCode();

        if (statusCode == 200) {
            System.out.println("Usuário deletado com sucesso.");
            System.out.println(response.body());
        } else if (statusCode == 400 || statusCode == 500) {
            System.out.println("Erro ao deletar o usuário: ");
            System.out.println(response.body());
        }
    }
}
