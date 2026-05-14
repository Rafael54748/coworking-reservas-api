package com.desafio.reservas.application.dtos;

import com.desafio.reservas.domain.model.Sala;

public class SalaResponse {

    private Long id;
    private String nome;
    private String tipo;
    private Integer capacidade;
    private Boolean ativa;

    public SalaResponse() {}

    public SalaResponse(Long id, String nome, String tipo, Integer capacidade, Boolean ativa) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.capacidade = capacidade;
        this.ativa = ativa;
    }

    public static SalaResponse from(Sala sala) {
        return new SalaResponse(
                sala.getId(),
                sala.getNome(),
                sala.getTipo().name(),
                sala.getCapacidade(),
                sala.getAtiva()
        );
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }

    public Boolean getAtiva() { return ativa; }
    public void setAtiva(Boolean ativa) { this.ativa = ativa; }
}