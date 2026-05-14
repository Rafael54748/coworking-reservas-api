package com.desafio.reservas.application.exception;

public class ReservaNaoEncontradaException extends RuntimeException {
    public ReservaNaoEncontradaException(Long id) {
        super("Reserva não encontrada com id: " + id);
    }
}