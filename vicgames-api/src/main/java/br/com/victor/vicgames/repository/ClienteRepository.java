package br.com.victor.vicgames.repository;

import br.com.victor.vicgames.model.Cliente;
import br.com.victor.vicgames.model.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query("SELECT j FROM Cliente c JOIN c.jogos j WHERE c.id = :idCliente")
    List<Jogo> findJogoByClienteId(Long idCliente);

    boolean existsByCpf(String cpf);
}
