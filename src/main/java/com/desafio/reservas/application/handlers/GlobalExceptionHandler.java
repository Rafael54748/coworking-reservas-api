package com.desafio.reservas.application.handlers;

import com.desafio.reservas.application.exception.ConflitoDeHorarioException;
import com.desafio.reservas.application.exception.ReservaNaoEncontradaException;
import com.desafio.reservas.application.exception.SalaInativaException;
import com.desafio.reservas.application.exception.SalaNaoEncontradaException;
import com.desafio.reservas.application.exception.SalaNomeJaExisteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SalaNaoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> handleSalaNaoEncontrada(SalaNaoEncontradaException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ReservaNaoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> handleReservaNaoEncontrada(ReservaNaoEncontradaException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(SalaNomeJaExisteException.class)
    public ResponseEntity<Map<String, Object>> handleNomeJaExiste(SalaNomeJaExisteException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(ConflitoDeHorarioException.class)
    public ResponseEntity<Map<String, Object>> handleConflito(ConflitoDeHorarioException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(SalaInativaException.class)
    public ResponseEntity<Map<String, Object>> handleSalaInativa(SalaInativaException ex) {
        return build(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException ex) {
        return build(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> build(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message
        ));
    }
}