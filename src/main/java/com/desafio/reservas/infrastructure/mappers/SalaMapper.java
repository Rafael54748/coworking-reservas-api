package com.desafio.reservas.infrastructure.mappers;

import com.desafio.reservas.domain.model.Sala;
import com.desafio.reservas.application.dtos.SalaResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SalaMapper {

    public SalaResponse toResponse(Sala sala) {
        return SalaResponse.from(sala);
    }

    public List<SalaResponse> toResponseList(List<Sala> salas) {
        return salas.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}