package com.desafio.reservas.infrastructure.adapter;

import com.desafio.reservas.domain.model.Reserva;
import com.desafio.reservas.domain.port.ReservaPort;
import com.desafio.reservas.infrastructure.repository.ReservaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ReservaAdapter implements ReservaPort {

    private final ReservaRepository reservaRepository;

    public ReservaAdapter(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Override
    public Reserva salvar(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @Override
    public Optional<Reserva> buscarPorId(Long id) {
        return reservaRepository.findById(id);
    }

    @Override
    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }

    @Override
    public List<Reserva> listarPorData(LocalDate data) {  // novo
        return reservaRepository.findByData(data);
    }

    @Override
    public List<Reserva> listarPorSalaEData(Long salaId, LocalDate data) {
        return reservaRepository.findBySalaIdAndData(salaId, data);
    }

    @Override
    public List<Reserva> listarPorResponsavel(String responsavel) {
        return reservaRepository.findByResponsavel(responsavel);
    }

    @Override
    public boolean existeConflito(Long salaId, LocalDateTime inicio,
                                  LocalDateTime fim, Long reservaIdExcluir) {
        return reservaRepository.existeConflito(salaId, inicio, fim, reservaIdExcluir);
    }

    @Override
    public void deletar(Long id) {
        reservaRepository.deleteById(id);
    }
}