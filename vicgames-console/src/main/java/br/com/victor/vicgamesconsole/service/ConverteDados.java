package br.com.victor.vicgamesconsole.service;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.exc.MismatchedInputException;

public class ConverteDados implements IConverteDados {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) throws RuntimeException {
        try {
            return mapper.readValue(json, classe);
        } catch (MismatchedInputException e) {
            throw e;
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }
}
