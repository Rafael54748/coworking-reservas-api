package com.desafio.reservas.domain.enums;

public enum StatusReserva {

    ATIVA("Ativa"),
    CANCELADA("Cancelada");

    private final String descricao;

    StatusReserva(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}