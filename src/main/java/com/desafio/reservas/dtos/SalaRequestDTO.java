package com.desafio.reservas.dtos;

import com.desafio.reservas.domain.enums.TipoSala;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para cadastro ou atualização de uma sala")
public class SalaRequestDTO {

    @Schema(description = "Nome único da sala", example = "Sala Amazônia")
    @NotBlank(message = "O nome da sala é obrigatório")
    private String nome;

    @Schema(description = "Tipo da sala: REUNIAO_COLETIVA, REUNIAO_INDIVIDUAL ou AUDITORIO",
            example = "REUNIAO_COLETIVA")
    @NotNull(message = "O tipo da sala é obrigatório")
    private TipoSala tipo;

    @Schema(description = "Capacidade máxima de pessoas", example = "10")
    @NotNull(message = "A capacidade é obrigatória")
    @Min(value = 1, message = "A capacidade deve ser de pelo menos 1 pessoa")
    private Integer capacidade;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public TipoSala getTipo() { return tipo; }
    public void setTipo(TipoSala tipo) { this.tipo = tipo; }

    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }
}