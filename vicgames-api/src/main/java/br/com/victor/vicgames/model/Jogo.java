package br.com.victor.vicgames.model;

import br.com.victor.vicgames.dto.CadastroJogoDto;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "jogos")
public class Jogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jogo_id", nullable = false, unique = true)
    private Long id;
    private String nome;
    private int preco;
    @Enumerated(value = EnumType.STRING)
    private ClassificacaoIndicativa classificacaoIndicativa;
    private int quantidadeEstoque;
    @ManyToMany(mappedBy = "jogos", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Cliente> clientes = new HashSet<>();

    public Jogo() {}

    public Jogo(CadastroJogoDto dto) {
        this.nome = dto.nome();
        this.preco = dto.preco();
        this.classificacaoIndicativa = ClassificacaoIndicativa.fromString(dto.classificacaoIndicativa());
        this.quantidadeEstoque = dto.quantidadeEstoque();
    }

    public void aumentaEstoque(int quantidade) {
        this.quantidadeEstoque += quantidade;
    }

    public void diminuiEstoque() {
        this.quantidadeEstoque -= 1;
    }

    public void diminuiEstoque(int quantidade) {
        if (this.quantidadeEstoque > quantidade) {
            this.quantidadeEstoque -= quantidade;
        } else {
            this.quantidadeEstoque = 0;
        }
    }

    public void atualizarDados(CadastroJogoDto dto) {
        this.nome = dto.nome();
        this.preco = dto.preco();
        this.classificacaoIndicativa = ClassificacaoIndicativa.fromString(dto.classificacaoIndicativa());
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

    public ClassificacaoIndicativa getClassificacaoIndicativa() {
        return classificacaoIndicativa;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
}
