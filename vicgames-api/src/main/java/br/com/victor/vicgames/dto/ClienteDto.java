package br.com.victor.vicgames.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteDto(@NotBlank(message = "O nome é obrigatório.")
                         String nome,
                         @NotBlank(message = "O telefone é obrigatório")
                         @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}",
                                  message = "Informe um telefone correto.")
                         String telefone,
                         @NotBlank(message = "O email é obrigatório.")
                         @Email(message = "Informe um e-mail válido.")
                         String email,
                         @NotBlank(message = "O cpf é obrigatório.")
                         @CPF(message = "CPF inválido.")
                         String cpf) {
}
