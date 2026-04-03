package br.com.victor.vicgames.repository;

import br.com.victor.vicgames.model.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogoRepository extends JpaRepository<Jogo, Long> {
    boolean existsByNomeIgnoreCase(String nome);
}
