package br.com.victor.vicgamesconsole.domain;

import br.com.victor.vicgamesconsole.dto.JogoDTO;

import java.util.HashSet;
import java.util.Set;

public class Jogo {
    private Long id;
    private String nome;
    private int preco;
    private String classificacaoIndicativa;
    private int quantidadeEstoque;
    private Set<Cliente> clientes = new HashSet<>();

    public Jogo() {}

    public Jogo(JogoDTO dto) {
        this.nome = dto.nome();
        this.preco = dto.preco();
        this.classificacaoIndicativa = dto.classificacaoIndicativa();
        this.quantidadeEstoque = dto.quantidadeEstoque();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getPreco() {
        return preco;
    }

    public String getClassificacaoIndicativa() {
        return classificacaoIndicativa;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }
}
