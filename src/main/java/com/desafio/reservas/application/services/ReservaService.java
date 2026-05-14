package com.desafio.reservas.application.services;

import com.desafio.reservas.application.exception.ConflitoDeHorarioException;
import com.desafio.reservas.application.exception.ReservaNaoEncontradaException;
import com.desafio.reservas.application.exception.SalaInativaException;
import com.desafio.reservas.application.exception.SalaNaoEncontradaException;
import com.desafio.reservas.application.usecase.ReservaUseCase;
import com.desafio.reservas.domain.enums.StatusReserva;
import com.desafio.reservas.domain.model.Reserva;
import com.desafio.reservas.domain.model.Sala;
import com.desafio.reservas.domain.port.ReservaPort;
import com.desafio.reservas.domain.port.SalaPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService implements ReservaUseCase {

    private final ReservaPort reservaPort;
    private final SalaPort salaPort;

    public ReservaService(ReservaPort reservaPort, SalaPort salaPort) {
        this.reservaPort = reservaPort;
        this.salaPort = salaPort;
    }

    @Override
    @Transactional
    public Reserva criarReserva(Long salaId, String responsavel,
                                LocalDateTime inicio, LocalDateTime fim) {

        validarHorarios(inicio, fim);

        Sala sala = salaPort.buscarPorId(salaId)
                .orElseThrow(() -> new SalaNaoEncontradaException(salaId));

        if (Boolean.FALSE.equals(sala.getAtiva())) {
            throw new SalaInativaException(salaId);
        }

        boolean conflito = reservaPort.existeConflito(salaId, inicio, fim, null);
        if (conflito) {
            throw new ConflitoDeHorarioException();
        }

        Reserva reserva = new Reserva(sala, responsavel, inicio, fim);
        return reservaPort.salvar(reserva);
    }

    @Override
    @Transactional
    public Reserva cancelarReserva(Long reservaId) {
        Reserva reserva = reservaPort.buscarPorId(reservaId)
                .orElseThrow(() -> new ReservaNaoEncontradaException(reservaId));

        if (reserva.getStatus() == StatusReserva.CANCELADA) {
            throw new IllegalStateException("A reserva de id " + reservaId + " já está cancelada.");
        }

        reserva.setStatus(StatusReserva.CANCELADA);
        return reservaPort.salvar(reserva);
    }

    @Override
    @Transactional(readOnly = true)
    public Reserva buscarPorId(Long reservaId) {
        return reservaPort.buscarPorId(reservaId)
                .orElseThrow(() -> new ReservaNaoEncontradaException(reservaId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reserva> consultarAgendaDiaria(LocalDate data) {
        return reservaPort.listarPorData(data);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reserva> consultarAgendaPorSalaEDia(Long salaId, LocalDate data) {
        salaPort.buscarPorId(salaId)
                .orElseThrow(() -> new SalaNaoEncontradaException(salaId));

        return reservaPort.listarPorSalaEData(salaId, data)
                .stream()
                .filter(r -> r.getStatus() == StatusReserva.ATIVA)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sala> consultarSalasLivres(LocalDate data, LocalDateTime inicio, LocalDateTime fim) {
        validarHorarios(inicio, fim);

        return salaPort.listarAtivas()
                .stream()
                .filter(sala -> !reservaPort.existeConflito(
                        sala.getId(), inicio, fim, null))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reserva> listarPorResponsavel(String responsavel) {
        return reservaPort.listarPorResponsavel(responsavel);
    }

    private void validarHorarios(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null || fim == null) {
            throw new IllegalArgumentException("Início e fim são obrigatórios.");
        }
        if (!fim.isAfter(inicio)) {
            throw new IllegalArgumentException(
                    "O fim deve ser posterior ao início.");
        }
        if (inicio.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "Não é possível criar reservas para datas passadas.");
        }
    }
}