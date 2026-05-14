package com.desafio.reservas.domain.model;

import com.desafio.reservas.domain.enums.TipoSala;
import jakarta.persistence.*;

@Entity
@Table(name = "salas")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSala tipo;

    @Column(nullable = false)
    private Integer capacidade;

    @Column(nullable = false)
    private Boolean ativa = true;

    public Sala() {}

    public Sala(String nome, TipoSala tipo, Integer capacidade) {
        this.nome = nome;
        this.tipo = tipo;
        this.capacidade = capacidade;
        this.ativa = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public TipoSala getTipo() { return tipo; }
    public void setTipo(TipoSala tipo) { this.tipo = tipo; }

    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }

    public Boolean getAtiva() { return ativa; }
    public void setAtiva(Boolean ativa) { this.ativa = ativa; }
}