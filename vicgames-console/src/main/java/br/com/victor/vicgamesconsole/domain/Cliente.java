package br.com.victor.vicgamesconsole.domain;

import br.com.victor.vicgamesconsole.dto.ClienteDTO;

import java.util.Set;

public class Cliente {
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private String cpf;
    private Set<Jogo> jogos;

    public Cliente() {}

    public Cliente(ClienteDTO dto) {
        this.nome = dto.nome();
        this.telefone = dto.telefone();
        this.email = dto.email();
        this.cpf = dto.cpf();
    }

    public void adquirirJogo(Jogo jogo) {
        this.jogos.add(jogo);
        jogo.getClientes().add(this);
    }

    public void removerJogo(Jogo jogo) {
        this.jogos.remove(jogo);
        jogo.getClientes().remove(this);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public Set<Jogo> getJogos() {
        return jogos;
    }
}
