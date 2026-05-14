package com.desafio.reservas.domain.port;

import com.desafio.reservas.domain.enums.TipoSala;
import com.desafio.reservas.domain.model.Sala;

import java.util.List;
import java.util.Optional;

public interface SalaPort {

    Sala salvar(Sala sala);

    Optional<Sala> buscarPorId(Long id);

    Optional<Sala> buscarPorNome(String nome);

    List<Sala> listarTodas();

    List<Sala> listarAtivas();

    List<Sala> listarPorTipo(TipoSala tipo);

    boolean existePorNome(String nome);

    void deletar(Long id);
}