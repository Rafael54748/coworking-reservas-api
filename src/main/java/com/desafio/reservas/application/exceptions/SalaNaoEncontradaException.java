package com.desafio.reservas.application.exception;

public class SalaNaoEncontradaException extends RuntimeException {
    public SalaNaoEncontradaException(Long id) {
        super("Sala não encontrada com id: " + id);
    }
    public SalaNaoEncontradaException(String nome) {
        super("Sala não encontrada com nome: " + nome);
    }
}