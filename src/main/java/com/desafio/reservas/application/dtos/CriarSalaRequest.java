package com.desafio.reservas.application.dtos;

import com.desafio.reservas.domain.enums.TipoSala;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CriarSalaRequest {

    @NotBlank(message = "O nome da sala é obrigatório.")
    private String nome;

    @NotNull(message = "O tipo da sala é obrigatório.")
    private TipoSala tipo;

    @NotNull(message = "A capacidade é obrigatória.")
    @Min(value = 1, message = "A capacidade deve ser no mínimo 1.")
    private Integer capacidade;

    public CriarSalaRequest() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public TipoSala getTipo() { return tipo; }
    public void setTipo(TipoSala tipo) { this.tipo = tipo; }

    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }
}