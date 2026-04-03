package br.com.victor.vicgames.service;

import br.com.victor.vicgames.dto.CadastroJogoDto;
import br.com.victor.vicgames.dto.JogoDto;
import br.com.victor.vicgames.exception.ValidacaoException;
import br.com.victor.vicgames.model.ClassificacaoIndicativa;
import br.com.victor.vicgames.model.Jogo;
import br.com.victor.vicgames.repository.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JogoService {
    @Autowired
    private JogoRepository jogoRepository;

    public List<JogoDto> listarJogos() {
        return jogoRepository.findAll()
                .stream()
                .map(j -> new JogoDto(j.getId(), j.getNome(), j.getPreco(), j.getClassificacaoIndicativa(), j.getQuantidadeEstoque()))
                .toList();
    }

    public void cadastrarJogo(CadastroJogoDto dto) {
        boolean jaExiste = jogoRepository.existsByNomeIgnoreCase(dto.nome());

        if (jaExiste) {
            throw new ValidacaoException("Jogo já está cadastrado");
        }

        jogoRepository.save(new Jogo(dto));
    }

    public JogoDto listarJogoPorId(Long idJogo) {
        Optional<Jogo> jogoOptional = jogoRepository.findById(idJogo);

        if(jogoOptional.isPresent()) {
            Jogo jogo = jogoOptional.get();
            return new JogoDto(jogo.getId(), jogo.getNome(), jogo.getPreco(), jogo.getClassificacaoIndicativa(), jogo.getQuantidadeEstoque());
        } else {
            throw new ValidacaoException("Não foi possível achar o jogo.");
        }
    }

    public void alterarJogo(Long idJogo, CadastroJogoDto dto) {
        Optional<Jogo> jogoOptional = jogoRepository.findById(idJogo);

        if (jogoOptional.isPresent()) {
            Jogo jogo = jogoOptional.get();
            jogo.atualizarDados(dto);
            this.jogoRepository.save(jogo);
        } else {
            throw new ValidacaoException("Jogo não encontrado.");
        }
    }
}
