package br.com.victor.vicgames.controller;

import br.com.victor.vicgames.dto.ErroValidacaoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroValidacaoDto>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<ErroValidacaoDto> erros = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> new ErroValidacaoDto(e.getField(), e.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest().body(erros);
    }
}
