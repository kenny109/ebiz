package org.ebiz.msestudiantescmd.repository;

import org.ebiz.msestudiantescmd.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaJpaRepository extends JpaRepository<Reserva, Long> {
    
    List<Reserva> findByEstudianteIdAndEstado(Long estudianteId, Reserva.EstadoReserva estado);
    
    List<Reserva> findBySalaIdAndEstado(Long salaId, Reserva.EstadoReserva estado);
    
    @Query("SELECT r FROM Reserva r WHERE r.sala.id = :salaId AND r.estado = 'ACTIVA' " +
           "AND DATE(r.fechaReserva) = DATE(:fecha)")
    List<Reserva> findBySalaAndFecha(@Param("salaId") Long salaId, @Param("fecha") LocalDateTime fecha);
    
    @Query("SELECT r FROM Reserva r WHERE r.estudiante.id = :estudianteId AND r.estado = 'ACTIVA' " +
           "AND DATE(r.fechaReserva) = DATE(:fecha)")
    List<Reserva> findByEstudianteAndFecha(@Param("estudianteId") Long estudianteId, @Param("fecha") LocalDateTime fecha);
    
    List<Reserva> findByEstado(Reserva.EstadoReserva estado);
}