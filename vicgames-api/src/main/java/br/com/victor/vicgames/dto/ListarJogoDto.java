package br.com.victor.vicgames.dto;

import br.com.victor.vicgames.model.ClassificacaoIndicativa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ListarJogoDto(@NotBlank String nome,
                            @NotNull int preco,
                            @NotNull ClassificacaoIndicativa classificacaoIndicativa) {
}
