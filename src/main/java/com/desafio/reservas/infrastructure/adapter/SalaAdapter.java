package com.desafio.reservas.infrastructure.adapter;

import com.desafio.reservas.domain.enums.TipoSala;
import com.desafio.reservas.domain.model.Sala;
import com.desafio.reservas.domain.port.SalaPort;
import com.desafio.reservas.infrastructure.repository.SalaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SalaAdapter implements SalaPort {

    private final SalaRepository salaRepository;

    public SalaAdapter(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    @Override
    public Sala salvar(Sala sala) {
        return salaRepository.save(sala);
    }

    @Override
    public Optional<Sala> buscarPorId(Long id) {
        return salaRepository.findById(id);
    }

    @Override
    public Optional<Sala> buscarPorNome(String nome) {
        return salaRepository.findByNome(nome);
    }

    @Override
    public List<Sala> listarTodas() {
        return salaRepository.findAll();
    }

    @Override
    public List<Sala> listarAtivas() {
        return salaRepository.findByAtivaTrue();
    }

    @Override
    public List<Sala> listarPorTipo(TipoSala tipo) {
        return salaRepository.findByTipo(tipo);
    }

    @Override
    public boolean existePorNome(String nome) {
        return salaRepository.existsByNome(nome);
    }

    @Override
    public void deletar(Long id) {
        salaRepository.deleteById(id);
    }
}