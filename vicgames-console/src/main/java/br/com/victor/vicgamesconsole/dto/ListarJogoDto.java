package br.com.victor.vicgamesconsole.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ListarJogoDto(@NotBlank String nome,
                            @NotNull int preco,
                            @NotNull String classificacaoIndicativa) {
    @Override
    public String toString() {
        return """
                %s -> R$ %.2f""".formatted(nome, (double) preco/100);
    }
}
