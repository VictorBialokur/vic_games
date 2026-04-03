package br.com.victor.vicgames.service;

import br.com.victor.vicgames.dto.CadastroJogoDto;
import br.com.victor.vicgames.dto.ClienteDto;
import br.com.victor.vicgames.dto.ListarClientesDto;

import java.util.List;
import java.util.Optional;

import br.com.victor.vicgames.dto.ListarJogoDto;
import br.com.victor.vicgames.exception.ValidacaoException;
import br.com.victor.vicgames.model.Cliente;
import br.com.victor.vicgames.model.Jogo;
import br.com.victor.vicgames.repository.ClienteRepository;
import br.com.victor.vicgames.repository.JogoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {
    @InjectMocks
    private ClienteService service;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private JogoRepository jogoRepository;

    @Mock
    private Cliente cliente;

    @Mock
    private Jogo jogo;

    @Captor
    private ArgumentCaptor<Cliente> clienteCaptor;

    @Test
    void deveriaRetornarUmaListaDeListarClientesDto() {
        //ARRANGE
        Cliente c1 = new Cliente(new ClienteDto("Victor", "11999345566", "teste@teste.com", "47137040080"));
        Cliente c2 = new Cliente(new ClienteDto("Teste", "11922334422", "testando@email.com", "78272312093"));

        List<Cliente> lista = List.of(c1, c2);

        when(clienteRepository.findAll()).thenReturn(lista);

        //ACT
        List<ListarClientesDto> resultado = service.listarClientes();

        //ASSERT
        then(clienteRepository).should().findAll();
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals(c2.getNome(), resultado.get(1).nome());
    }

    @Test
    void deveriaCadastrarUmCliente() {
        ClienteDto cliente = new ClienteDto("Teste", "11922334422", "testando@email.com", "78272312093");

        service.cadastrarCliente(cliente);

        then(clienteRepository).should().save(clienteCaptor.capture());
        Cliente clienteSalvo = clienteCaptor.getValue();

        Assertions.assertEquals(cliente.nome(), clienteSalvo.getNome());
    }

    @Test
    void deveriaVerificarSeOCpfJaEstaRegistrado() {
        ClienteDto cliente = new ClienteDto("Teste", "11922334422", "testando@email.com", "78272312093");

        service.cadastrarCliente(cliente);

        then(clienteRepository).should().existsByCpf(cliente.cpf());
    }

    @Test
    void deveriaLancarValidacaoExceptionAoCadastrarUmClienteComCpfJaRegistrado() {
        ClienteDto cliente = new ClienteDto("Teste", "11922334422", "testando@email.com", "78272312093");
        when(clienteRepository.existsByCpf(cliente.cpf())).thenReturn(true);

        // ACT + ASSERT
        ValidacaoException exception = Assertions.assertThrows(ValidacaoException.class, () -> {
            service.cadastrarCliente(cliente);
        });

        then(clienteRepository).should().existsByCpf(cliente.cpf());
        Assertions.assertEquals("Cliente já está cadastrado", exception.getMessage());
        then(clienteRepository).should(never()).save(any());
    }

    @Test
    void deveriaVerificarSeOClienteEJogoExistem() {
        Long idCliente = 0L;
        Long idJogo = 0L;
        given(clienteRepository.findById(idCliente)).willReturn(Optional.of(cliente));
        given(jogoRepository.findById(idJogo)).willReturn(Optional.of(jogo));
        given(jogo.getQuantidadeEstoque()).willReturn(2);

        service.comprarJogo(idCliente, idJogo);

        then(clienteRepository).should().findById(idCliente);
        then(jogoRepository).should().findById(idJogo);
    }

    @Test
    void deveriaSalvarOJogoComprado() {
        Long idCliente = 0L;
        Long idJogo = 0L;
        given(clienteRepository.findById(idCliente)).willReturn(Optional.of(cliente));
        given(jogoRepository.findById(idJogo)).willReturn(Optional.of(jogo));
        given(jogo.getQuantidadeEstoque()).willReturn(2);

        service.comprarJogo(idCliente, idJogo);

        then(clienteRepository).should().save(cliente);
    }

    @Test
    void deveriaLancarValidacaoExcecaoAoComprarJogoIndisponivelEmEstoque() {
        Long idCliente = 0L;
        Long idJogo = 0L;
        given(clienteRepository.findById(idCliente)).willReturn(Optional.of(cliente));
        given(jogoRepository.findById(idJogo)).willReturn(Optional.of(jogo));
        given(jogo.getQuantidadeEstoque()).willReturn(0);

        ValidacaoException exception = Assertions.assertThrows(ValidacaoException.class, () -> {
            service.comprarJogo(idCliente, idJogo);
        });

        Assertions.assertEquals("Jogo indisponível no estoque", exception.getMessage());
        then(clienteRepository).should(never()).save(any());
    }

    @Test
    void deveriaLancarValidacaoExcecaoAoComprarJogoQueNaoExiste() {
        Long idCliente = 0L;
        Long idJogo = 0L;
        given(clienteRepository.findById(idCliente)).willReturn(Optional.of(cliente));
        given(jogoRepository.findById(idJogo)).willReturn(Optional.empty());

        ValidacaoException exception = Assertions.assertThrows(ValidacaoException.class, () -> {
            service.comprarJogo(idCliente, idJogo);
        });

        then(clienteRepository).should(never()).save(any());
        Assertions.assertEquals("Cliente ou jogo não existe", exception.getMessage());
    }

    @Test
    void deveriaLancarValidacaoExcecaoAoComprarJogoParaUmClienteQueNaoExiste() {
        Long idCliente = 0L;
        Long idJogo = 0L;
        given(clienteRepository.findById(idCliente)).willReturn(Optional.empty());
        given(jogoRepository.findById(idJogo)).willReturn(Optional.of(jogo));

        ValidacaoException exception = Assertions.assertThrows(ValidacaoException.class, () -> {
            service.comprarJogo(idCliente, idJogo);
        });

        then(clienteRepository).should(never()).save(any());
        Assertions.assertEquals("Cliente ou jogo não existe", exception.getMessage());
    }

    @Test
    void deveriaRetornarUmaListaDeListarJogosDto() {
        Long idCliente = 0L;
        Jogo j1 = new Jogo(new CadastroJogoDto("TesteJogo", 25000, "E", 10));
        Jogo j2 = new Jogo(new CadastroJogoDto("TesteJogo2", 30000, "M", 20));
        List<Jogo> lista = List.of(j1, j2);
        when(clienteRepository.findJogoByClienteId(idCliente)).thenReturn(lista);

        List<ListarJogoDto> resultado = service.listarJogosComprados(idCliente);

        then(clienteRepository).should().findJogoByClienteId(idCliente);
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals("TesteJogo", resultado.get(0).nome());
    }

    @Test
    void deveriaVerificarSeOClienteExisteAntesDeDeletarUmClientePeloId() {
        Long idCliente = 0L;
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));

        service.deletarCliente(idCliente);

        then(clienteRepository).should().findById(idCliente);
    }

    @Test
    void deveriaDeletarUmClientePeloId() {
        Long idCliente = 0L;
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));

        service.deletarCliente(idCliente);

        then(clienteRepository).should().deleteById(idCliente);
    }

    @Test
    void deveriaLancarUmValidacaoExceptionAoDeletarUmClienteQueNaoExiste() {
        Long idCliente = 0L;
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.empty());

        ValidacaoException exception = Assertions.assertThrows(ValidacaoException.class, () -> {
            service.deletarCliente(idCliente);
        });

        then(clienteRepository).should(never()).deleteById(any());
        Assertions.assertEquals("Cliente não existe.", exception.getMessage());
    }

    @Test
    void deveriaVerificarSeOClienteExisteAntesDeRetornarUmClienteDtoPeloId() {
        Long idCliente = 0L;
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));

        service.listarClientePorId(idCliente);

        then(clienteRepository).should().findById(idCliente);
    }

    @Test
    void deveriaRetornarUmClienteDtoPeloId() {
        Long idCliente = 0L;
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));

        ClienteDto resultado = service.listarClientePorId(idCliente);

        Assertions.assertEquals(cliente.getNome(), resultado.nome());
    }

    @Test
    void deveriaLancarUmValidacaoExceptionAoTentarRetornarUmClienteDtoPeloIdQueNaoExiste() {
        Long idCliente = 0L;
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.empty());

        ValidacaoException exception = Assertions.assertThrows(ValidacaoException.class, () -> {
            service.listarClientePorId(idCliente);
        });

        Assertions.assertEquals("Cliente não foi encontrado.", exception.getMessage());
    }

    @Test
    void deveriaVerificarSeOClienteExisteAntesDeAlterarAsInformacoesDoCliente() {
        Long idCliente = 0L;
        Cliente c1 = new Cliente(
                new ClienteDto("Victor", "1199223322", "email@email.com", "63496631015"));
        ClienteDto dto = new ClienteDto("Teste", "11999334433", "email@teste.com", "78272312093");
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(c1));

        service.alterarCliente(idCliente, dto);

        then(clienteRepository).should().findById(idCliente);
    }

    @Test
    void deveriaAlterarAsInformacoesDoCliente() {
        Long idCliente = 0L;
        Cliente c1 = new Cliente(
                new ClienteDto("Victor", "1199223322", "email@email.com", "63496631015"));
        ClienteDto dto = new ClienteDto("Teste", "11999334433", "email@teste.com", "78272312093");
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(c1));

        service.alterarCliente(idCliente, dto);

        then(clienteRepository).should().save(clienteCaptor.capture());
        Cliente resultado = clienteCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(dto.nome(), resultado.getNome()),
                () -> Assertions.assertEquals(dto.telefone(), resultado.getTelefone()),
                () -> Assertions.assertEquals(dto.email(), resultado.getEmail()),
                () -> Assertions.assertEquals(dto.cpf(), resultado.getCpf())
        );
    }

    @Test
    void deveriaLancarValidacaoExceptionAoTentarAlterarAsInformacoesDeClienteInexistente() {
        Long idCliente = 0L;
        ClienteDto dto = new ClienteDto("Teste", "11999334433", "email@teste.com", "78272312093");
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.empty());

        ValidacaoException exception = Assertions.assertThrows(ValidacaoException.class, () -> {
            service.alterarCliente(idCliente, dto);
        });

        then(clienteRepository).should(never()).save(any());
        Assertions.assertEquals("Cliente não encontrado.", exception.getMessage());
    }
}