package br.com.victor.vicgamesconsole.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
