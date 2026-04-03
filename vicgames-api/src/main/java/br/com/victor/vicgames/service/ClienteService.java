package br.com.victor.vicgames.service;

import br.com.victor.vicgames.dto.ClienteDto;
import br.com.victor.vicgames.dto.ListarClientesDto;
import br.com.victor.vicgames.dto.ListarJogoDto;
import br.com.victor.vicgames.exception.ValidacaoException;
import br.com.victor.vicgames.model.Cliente;
import br.com.victor.vicgames.model.Jogo;
import br.com.victor.vicgames.repository.ClienteRepository;
import br.com.victor.vicgames.repository.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private JogoRepository jogoRepository;

    public List<ListarClientesDto> listarClientes() {
        return this.clienteRepository.findAll()
                .stream()
                .map(c ->
                        new ListarClientesDto(c.getId(), c.getNome(), c.getTelefone(), c.getEmail(), c.getCpf()))
                .toList();
    }

    public void cadastrarCliente(ClienteDto dto) {
        boolean jaExiste = this.clienteRepository.existsByCpf(dto.cpf());

        if (jaExiste) {
            throw new ValidacaoException("Cliente já está cadastrado");
        }

        this.clienteRepository.save(new Cliente(dto));
    }

    public void comprarJogo(Long idCliente, Long idJogo) {
        Optional<Cliente> optionalCliente = clienteRepository.findById(idCliente);
        Optional<Jogo> optionalJogo = jogoRepository.findById(idJogo);

        if (optionalCliente.isPresent() && optionalJogo.isPresent()) {
            Cliente cliente = optionalCliente.get();
            Jogo jogo = optionalJogo.get();

            if (jogo.getQuantidadeEstoque() < 1) {
                throw new ValidacaoException("Jogo indisponível no estoque");
            }

            cliente.adquirirJogo(jogo);
            clienteRepository.save(cliente);
        } else {
            throw new ValidacaoException("Cliente ou jogo não existe");
        }
    }

    public List<ListarJogoDto> listarJogosComprados(Long idCliente) {
        return this.clienteRepository.findJogoByClienteId(idCliente)
                .stream()
                .map(j -> new ListarJogoDto(j.getNome(), j.getPreco(), j.getClassificacaoIndicativa()))
                .toList();
    }

    public void deletarCliente(Long idCliente) {
        Optional<Cliente> cliente = this.clienteRepository.findById(idCliente);

        if (cliente.isPresent()) {
            cliente.get().excluirCliente();
            this.clienteRepository.deleteById(idCliente);
        } else {
            throw new ValidacaoException("Cliente não existe.");
        }
    }

    public ClienteDto listarClientePorId(Long idCliente) {
        Optional<Cliente> cliente = this.clienteRepository.findById(idCliente);

        if (cliente.isPresent()) {
            Cliente clAux = cliente.get();
            return new ClienteDto(clAux.getNome(), clAux.getTelefone(), clAux.getEmail(), clAux.getCpf());
        } else {
            throw new ValidacaoException("Cliente não foi encontrado.");
        }
    }

    public void alterarCliente(Long idCliente, ClienteDto dto) {
        Optional<Cliente> cliente = this.clienteRepository.findById(idCliente);

        if(cliente.isPresent()) {
            Cliente clAux = cliente.get();
            clAux.atualizarDados(dto);
            this.clienteRepository.save(clAux);
        } else {
            throw new ValidacaoException("Cliente não encontrado.");
        }
    }
}
