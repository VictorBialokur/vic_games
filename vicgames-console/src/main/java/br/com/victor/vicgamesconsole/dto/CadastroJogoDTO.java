package br.com.victor.vicgamesconsole.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroJogoDTO(@NotBlank String nome,
                              @NotNull int preco,
                              @NotNull String classificacaoIndicativa,
                              @NotNull int quantidadeEstoque) {

    @Override
    public String toString() {
        return """
                %s -> R$ %.2f - Em estoque: %d""".formatted(nome, (double) preco/100, quantidadeEstoque);
    }
}
