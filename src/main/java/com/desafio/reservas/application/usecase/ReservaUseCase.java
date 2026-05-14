package com.desafio.reservas.application.usecase;

import com.desafio.reservas.domain.model.Reserva;
import com.desafio.reservas.domain.model.Sala;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservaUseCase {

    Reserva criarReserva(Long salaId, String responsavel,
                         LocalDateTime inicio, LocalDateTime fim);

    Reserva cancelarReserva(Long reservaId);

    Reserva buscarPorId(Long reservaId);

    List<Reserva> consultarAgendaDiaria(LocalDate data);

    List<Reserva> consultarAgendaPorSalaEDia(Long salaId, LocalDate data);

    List<Sala> consultarSalasLivres(LocalDate data, LocalDateTime inicio, LocalDateTime fim);

    List<Reserva> listarPorResponsavel(String responsavel);
}