package com.desafio.reservas.infrastructure.repository;

import com.desafio.reservas.domain.enums.TipoSala;
import com.desafio.reservas.domain.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {

    Optional<Sala> findByNome(String nome);

    boolean existsByNome(String nome);

    List<Sala> findByAtivaTrue();

    List<Sala> findByTipo(TipoSala tipo);
}