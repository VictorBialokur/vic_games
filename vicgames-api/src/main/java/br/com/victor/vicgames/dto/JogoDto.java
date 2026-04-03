package br.com.victor.vicgames.dto;

import br.com.victor.vicgames.model.ClassificacaoIndicativa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record JogoDto(@NotNull Long id,
                      @NotBlank String nome,
                      @NotNull int preco,
                      @NotNull ClassificacaoIndicativa classificacaoIndicativa,
                      @NotNull int quantidadeEstoque) {
}
