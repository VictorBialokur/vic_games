package br.com.victor.vicgames.model;

import br.com.victor.vicgames.dto.ClienteDto;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id", unique = true, nullable = false)
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private String cpf;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Jogo> jogos = new HashSet<>();

    public Cliente() {}

    public Cliente(ClienteDto dto) {
        this.nome = dto.nome();
        this.telefone = dto.telefone();
        this.email = dto.email();
        this.cpf = dto.cpf();
    }

    public void adquirirJogo(Jogo jogo) {
        this.jogos.add(jogo);
        jogo.getClientes().add(this);
        jogo.diminuiEstoque();
    }

    public void removerJogo(Jogo jogo) {
        this.jogos.remove(jogo);
        jogo.getClientes().remove(this);
    }

    public void excluirCliente() {
        this.jogos.forEach(j -> j.getClientes().remove(this));
        this.jogos.clear();
    }

    public void atualizarDados(ClienteDto dto) {
        this.nome = dto.nome();
        this.email = dto.email();
        this.telefone = dto.telefone();
        this.cpf = dto.cpf();
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

    public Set<Jogo> getJogos() {
        return jogos;
    }

    public String getCpf() {
        return cpf;
    }
}
