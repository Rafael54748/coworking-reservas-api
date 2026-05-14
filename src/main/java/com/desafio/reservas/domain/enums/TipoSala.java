package com.desafio.reservas.domain.enums;

public enum TipoSala {

    REUNIAO_COLETIVA("Reunião Coletiva"),
    REUNIAO_INDIVIDUAL("Reunião Individual"),
    AUDITORIO("Auditório");

    private final String descricao;

    TipoSala(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}