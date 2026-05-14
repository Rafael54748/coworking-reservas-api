package com.desafio.reservas.dtos;

import com.desafio.reservas.domain.enums.TipoSala;
import com.desafio.reservas.domain.model.Sala;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de retorno de uma sala")
public class SalaResponseDTO {

    @Schema(description = "ID da sala", example = "1")
    private Long id;

    @Schema(description = "Nome da sala", example = "Sala Amazônia")
    private String nome;

    @Schema(description = "Tipo da sala", example = "REUNIAO_COLETIVA")
    private TipoSala tipo;

    @Schema(description = "Capacidade máxima", example = "10")
    private Integer capacidade;

    @Schema(description = "Se a sala está ativa", example = "true")
    private Boolean ativa;

    public SalaResponseDTO() {}

    public SalaResponseDTO(Sala sala) {
        this.id = sala.getId();
        this.nome = sala.getNome();
        this.tipo = sala.getTipo();
        this.capacidade = sala.getCapacidade();
        this.ativa = sala.getAtiva();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public TipoSala getTipo() { return tipo; }
    public void setTipo(TipoSala tipo) { this.tipo = tipo; }

    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }

    public Boolean getAtiva() { return ativa; }
    public void setAtiva(Boolean ativa) { this.ativa = ativa; }
}