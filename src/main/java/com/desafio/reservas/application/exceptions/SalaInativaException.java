package com.desafio.reservas.application.exception;

public class SalaInativaException extends RuntimeException {
    public SalaInativaException(Long salaId) {
        super("A sala de id " + salaId + " está inativa e não pode receber reservas.");
    }
}