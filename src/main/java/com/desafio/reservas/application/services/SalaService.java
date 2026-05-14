package com.desafio.reservas.application.services;

import com.desafio.reservas.application.exception.SalaNaoEncontradaException;
import com.desafio.reservas.application.exception.SalaNomeJaExisteException;
import com.desafio.reservas.application.usecase.SalaUseCase;
import com.desafio.reservas.domain.enums.TipoSala;
import com.desafio.reservas.domain.model.Sala;
import com.desafio.reservas.domain.port.SalaPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SalaService implements SalaUseCase {

    private final SalaPort salaPort;

    public SalaService(SalaPort salaPort) {
        this.salaPort = salaPort;
    }

    @Override
    @Transactional
    public Sala cadastrarSala(String nome, TipoSala tipo, Integer capacidade) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da sala é obrigatório.");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("O tipo da sala é obrigatório.");
        }
        if (capacidade == null || capacidade <= 0) {
            throw new IllegalArgumentException("A capacidade deve ser um número positivo.");
        }
        if (salaPort.existePorNome(nome.trim())) {
            throw new SalaNomeJaExisteException(nome);
        }

        Sala sala = new Sala(nome.trim(), tipo, capacidade);
        return salaPort.salvar(sala);
    }

    @Override
    @Transactional(readOnly = true)
    public Sala buscarPorId(Long salaId) {
        return salaPort.buscarPorId(salaId)
                .orElseThrow(() -> new SalaNaoEncontradaException(salaId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sala> listarTodas() {
        return salaPort.listarTodas();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sala> listarAtivas() {
        return salaPort.listarAtivas();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sala> listarPorTipo(TipoSala tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("O tipo da sala é obrigatório para filtrar.");
        }
        return salaPort.listarPorTipo(tipo);
    }

    @Override
    @Transactional
    public Sala ativarSala(Long salaId) {
        Sala sala = salaPort.buscarPorId(salaId)
                .orElseThrow(() -> new SalaNaoEncontradaException(salaId));

        if (Boolean.TRUE.equals(sala.getAtiva())) {
            throw new IllegalStateException("A sala já está ativa.");
        }

        sala.setAtiva(true);
        return salaPort.salvar(sala);
    }

    @Override
    @Transactional
    public Sala desativarSala(Long salaId) {
        Sala sala = salaPort.buscarPorId(salaId)
                .orElseThrow(() -> new SalaNaoEncontradaException(salaId));

        if (Boolean.FALSE.equals(sala.getAtiva())) {
            throw new IllegalStateException("A sala já está inativa.");
        }

        sala.setAtiva(false);
        return salaPort.salvar(sala);
    }
}