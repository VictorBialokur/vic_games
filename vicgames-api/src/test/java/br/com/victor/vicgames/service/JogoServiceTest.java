package br.com.victor.vicgames.service;

import br.com.victor.vicgames.dto.CadastroJogoDto;
import br.com.victor.vicgames.dto.JogoDto;
import br.com.victor.vicgames.exception.ValidacaoException;
import br.com.victor.vicgames.model.ClassificacaoIndicativa;
import br.com.victor.vicgames.model.Jogo;
import br.com.victor.vicgames.repository.JogoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JogoServiceTest {
    @InjectMocks
    private JogoService service;

    @Mock
    private JogoRepository repository;

    @Mock
    private Jogo jogo;

    @Captor
    private ArgumentCaptor<Jogo> jogoCaptor;

    @Test
    void deveriaRetornarUmaListaDeJogoDto() {
        Jogo j1 = new Jogo(new CadastroJogoDto("Teste", 14999, "T", 10));
        Jogo j2 = new Jogo(new CadastroJogoDto("Testando", 24999, "E", 20));
        List<Jogo> lista = List.of(j1, j2);
        when(repository.findAll()).thenReturn(lista);

        List<JogoDto> resultado = service.listarJogos();

        then(repository).should().findAll();
        Assertions.assertAll(
                () -> Assertions.assertNotNull(resultado),
                () -> Assertions.assertEquals(2, resultado.size()),
                () -> Assertions.assertEquals(j1.getNome(), resultado.get(0).nome())
        );
    }

    @Test
    void deveriaCadastrarJogo() {
        CadastroJogoDto dto = new CadastroJogoDto("Teste", 10000, "E", 10);
        when(repository.existsByNomeIgnoreCase(dto.nome())).thenReturn(false);

        service.cadastrarJogo(dto);

        then(repository).should().existsByNomeIgnoreCase(dto.nome());
        then(repository).should().save(jogoCaptor.capture());
        Jogo resultado = jogoCaptor.getValue();
        Assertions.assertEquals(dto.nome(), resultado.getNome());
    }

    @Test
    void deveriaLancarValidacaoExceptionAoCadastrarJogoQueJaExiste() {
        CadastroJogoDto dto = new CadastroJogoDto("Teste", 10000, "E", 10);
        when(repository.existsByNomeIgnoreCase(dto.nome())).thenReturn(true);

        ValidacaoException exception = Assertions.assertThrows(ValidacaoException.class, () -> {
            service.cadastrarJogo(dto);
        });

        then(repository).should().existsByNomeIgnoreCase(dto.nome());
        then(repository).should(never()).save(any());
        Assertions.assertEquals("Jogo já está cadastrado", exception.getMessage());
    }

    @Test
    void deveriaRetornarUmJogoDtoPeloId() {
        Long idJogo = 0L;
        when(repository.findById(idJogo)).thenReturn(Optional.of(jogo));

        JogoDto resultado = service.listarJogoPorId(idJogo);

        then(repository).should().findById(idJogo);
        Assertions.assertEquals(jogo.getNome(), resultado.nome());
    }

    @Test
    void deveriaLancarValidacaoExceptionAoBuscarUmJogoQueNaoExiste() {
        Long idJogo = 0L;
        when(repository.findById(idJogo)).thenReturn(Optional.empty());

        ValidacaoException exception = Assertions.assertThrows(ValidacaoException.class, () -> {
            service.listarJogoPorId(idJogo);
        });

        then(repository).should().findById(idJogo);
        Assertions.assertEquals("Não foi possível achar o jogo.", exception.getMessage());
    }

    @Test
    void deveriaAlterarInformacoesDeUmJogo() {
        Long idJogo = 0L;
        Jogo j1 = new Jogo(new CadastroJogoDto("Testando", 20000, "M", 20));
        CadastroJogoDto dto = new CadastroJogoDto("Teste", 10000, "E", 10);
        when(repository.findById(idJogo)).thenReturn(Optional.of(j1));

        service.alterarJogo(idJogo, dto);

        then(repository).should().findById(idJogo);
        then(repository).should().save(jogoCaptor.capture());
        Jogo resultado = jogoCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(dto.nome(), resultado.getNome()),
                () -> Assertions.assertEquals(dto.preco(), resultado.getPreco()),
                () -> Assertions.assertEquals(ClassificacaoIndicativa.fromString(dto.classificacaoIndicativa()), resultado.getClassificacaoIndicativa()),
                () -> Assertions.assertEquals(dto.quantidadeEstoque(), resultado.getQuantidadeEstoque())
        );
    }

    @Test
    void deveriaLancarValidacaoExceptionAoTentarBuscarUmJogoQueNaoExiste() {
        Long idJogo = 0L;
        CadastroJogoDto dto = new CadastroJogoDto("Teste", 10000, "E", 10);
        when(repository.findById(idJogo)).thenReturn(Optional.empty());

        ValidacaoException exception = Assertions.assertThrows(ValidacaoException.class, () -> {
            service.alterarJogo(idJogo, dto);
        });

        then(repository).should().findById(idJogo);
        then(repository).should(never()).save(any());
        Assertions.assertEquals("Jogo não encontrado.", exception.getMessage());
    }
}