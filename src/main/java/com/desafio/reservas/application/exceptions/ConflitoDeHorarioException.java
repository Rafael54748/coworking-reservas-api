package com.desafio.reservas.application.exception;

public class ConflitoDeHorarioException extends RuntimeException {
    public ConflitoDeHorarioException() {
        super("Já existe uma reserva ativa para esta sala no horário solicitado.");
    }
}