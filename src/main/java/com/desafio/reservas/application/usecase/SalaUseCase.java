package com.desafio.reservas.application.usecase;

import com.desafio.reservas.domain.enums.TipoSala;
import com.desafio.reservas.domain.model.Sala;

import java.util.List;

public interface SalaUseCase {

    Sala cadastrarSala(String nome, TipoSala tipo, Integer capacidade);

    Sala buscarPorId(Long salaId);

    List<Sala> listarTodas();

    List<Sala> listarAtivas();

    List<Sala> listarPorTipo(TipoSala tipo);

    Sala ativarSala(Long salaId);

    Sala desativarSala(Long salaId);
}