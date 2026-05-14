package com.desafio.reservas.domain.port;

import com.desafio.reservas.domain.model.Reserva;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservaPort {

    Reserva salvar(Reserva reserva);

    Optional<Reserva> buscarPorId(Long id);

    List<Reserva> listarTodas();

    List<Reserva> listarPorSalaEData(Long salaId, LocalDate data);

    List<Reserva> listarPorResponsavel(String responsavel);

    List<Reserva> listarPorData(LocalDate data);

    boolean existeConflito(Long salaId, LocalDateTime inicio,
                           LocalDateTime fim, Long reservaIdExcluir);

    void deletar(Long id);
}