package br.com.victor.vicgamesconsole.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

public record ListarClientesDTO(@NotNull
                                Long id,
                                @NotBlank
                                String nome,
                                @NotBlank
                                @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}")
                                String telefone,
                                @NotBlank
                                @Email
                                String email,
                                @NotBlank
                                @CPF
                                String cpf) {


    @Override
    public String toString() {
        return """
                Id:%d - %s -> Tel: %s | Email: %s""".formatted(id, nome, telefone, email);
    }
}
