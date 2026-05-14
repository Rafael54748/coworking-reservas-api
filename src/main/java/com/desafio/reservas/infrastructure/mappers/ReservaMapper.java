package com.desafio.reservas.infrastructure.mappers;

import com.desafio.reservas.domain.model.Reserva;
import com.desafio.reservas.application.dtos.ReservaResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReservaMapper {

    public ReservaResponse toResponse(Reserva reserva) {
        return ReservaResponse.from(reserva);
    }

    public List<ReservaResponse> toResponseList(List<Reserva> reservas) {
        return reservas.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}