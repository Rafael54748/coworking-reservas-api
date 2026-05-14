package com.desafio.reservas.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "Dados para criação de uma reserva")
public class ReservaRequestDTO {

    @Schema(description = "ID da sala a ser reservada", example = "1")
    @NotNull(message = "O ID da sala é obrigatório")
    private Long salaId;

    @Schema(description = "Nome do responsável pela reserva", example = "João Silva")
    @NotBlank(message = "O responsável é obrigatório")
    private String responsavel;

    @Schema(description = "Data e hora de início da reserva (yyyy-MM-ddTHH:mm)", example = "2025-06-15T09:00")
    @NotNull(message = "O início é obrigatório")
    @Future(message = "O início não pode ser no passado")
    private LocalDateTime inicio;

    @Schema(description = "Data e hora de fim da reserva (yyyy-MM-ddTHH:mm)", example = "2025-06-15T10:00")
    @NotNull(message = "O fim é obrigatório")
    @Future(message = "O fim não pode ser no passado")
    private LocalDateTime fim;

    public Long getSalaId() { return salaId; }
    public void setSalaId(Long salaId) { this.salaId = salaId; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

    public LocalDateTime getInicio() { return inicio; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }

    public LocalDateTime getFim() { return fim; }
    public void setFim(LocalDateTime fim) { this.fim = fim; }
}