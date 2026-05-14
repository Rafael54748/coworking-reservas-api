package com.desafio.reservas.infrastructure.repository;

import com.desafio.reservas.domain.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("""
            SELECT r FROM Reserva r
            WHERE CAST(r.inicio AS date) = :data
              AND r.status = 'ATIVA'
            """)
    List<Reserva> findByData(@Param("data") LocalDate data);

    @Query("""
            SELECT r FROM Reserva r
            WHERE r.sala.id = :salaId
              AND CAST(r.inicio AS date) = :data
            """)
    List<Reserva> findBySalaIdAndData(@Param("salaId") Long salaId,
                                      @Param("data") LocalDate data);

    List<Reserva> findByResponsavel(String responsavel);

    @Query("""
            SELECT COUNT(r) > 0 FROM Reserva r
            WHERE r.sala.id = :salaId
              AND r.status = 'ATIVA'
              AND (:reservaId IS NULL OR r.id <> :reservaId)
              AND r.inicio < :fim
              AND r.fim > :inicio
            """)
    boolean existeConflito(@Param("salaId") Long salaId,
                           @Param("inicio") LocalDateTime inicio,
                           @Param("fim") LocalDateTime fim,
                           @Param("reservaId") Long reservaId);
}