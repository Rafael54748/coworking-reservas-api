package com.desafio.reservas.application.exception;

public class SalaNomeJaExisteException extends RuntimeException {
    public SalaNomeJaExisteException(String nome) {
        super("Já existe uma sala cadastrada com o nome: " + nome);
    }
}