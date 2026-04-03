package br.com.victor.vicgames.model;

public enum ClassificacaoIndicativa {
    EVERYONE("E"),
    TEEN("T"),
    MATURE("M");

    private String classificacaoAbreviacao;

    ClassificacaoIndicativa(String classificacaoAbreviacao) {
        this.classificacaoAbreviacao = classificacaoAbreviacao;
    }

    public static ClassificacaoIndicativa fromString(String text) {
        for (ClassificacaoIndicativa classificacaoIndicativa : ClassificacaoIndicativa.values()) {
            if (classificacaoIndicativa.classificacaoAbreviacao.equalsIgnoreCase(text)) {
                return classificacaoIndicativa;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para o jogo");
    }
}
