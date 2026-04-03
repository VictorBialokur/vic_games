package br.com.victor.vicgamesconsole.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record JogoDTO(@NotNull Long id,
                      @NotBlank String nome,
                      @NotNull int preco,
                      @NotNull String classificacaoIndicativa,
                      @NotNull int quantidadeEstoque) {

    @Override
    public String toString() {
        return """
                Id:%d - %s -> R$ %.2f - Em estoque: %d""".formatted(id, nome, (double) preco/100, quantidadeEstoque);
    }
}
