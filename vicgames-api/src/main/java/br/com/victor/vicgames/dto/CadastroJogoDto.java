package br.com.victor.vicgames.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroJogoDto(@NotBlank(message = "O nome é obrigatório.")
                              String nome,
                              @NotNull(message = "Digite um preço.")
                              int preco,
                              @NotNull(message = "A classificação é obrigatória.")
                              String classificacaoIndicativa,
                              @NotNull(message = "A quantidade em estoque é obrigatória.")
                              int quantidadeEstoque) {
}
