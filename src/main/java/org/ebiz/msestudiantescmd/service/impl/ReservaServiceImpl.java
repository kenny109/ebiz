package org.ebiz.msestudiantescmd.service.impl;

import org.ebiz.msestudiantescmd.model.Estudiante;
import org.ebiz.msestudiantescmd.model.Reserva;
import org.ebiz.msestudiantescmd.model.Sala;
import org.ebiz.msestudiantescmd.repository.EstudianteJpaRepository;
import org.ebiz.msestudiantescmd.repository.ReservaJpaRepository;
import org.ebiz.msestudiantescmd.repository.SalaJpaRepository;
import org.ebiz.msestudiantescmd.service.ReservaServiceInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaServiceInterface {

    private final ReservaJpaRepository reservaRepository;
    private final EstudianteJpaRepository estudianteRepository;
    private final SalaJpaRepository salaRepository;

    public ReservaServiceImpl(ReservaJpaRepository reservaRepository,
                             EstudianteJpaRepository estudianteRepository,
                             SalaJpaRepository salaRepository) {
        this.reservaRepository = reservaRepository;
        this.estudianteRepository = estudianteRepository;
        this.salaRepository = salaRepository;
    }

    @Override
    public Reserva crearReserva(Long estudianteId, Long salaId, LocalDateTime fechaReserva, int cantidadEstudiantes) {
        // Validar que el estudiante existe y está habilitado
        Estudiante estudiante = estudianteRepository.findByIdAndHabilitado(estudianteId, 1)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado o no habilitado"));

        // Validar que la sala existe y está habilitada
        Sala sala = salaRepository.findByIdAndHabilitado(salaId, 1)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada o no habilitada"));

        // Validar fecha futura
        if (fechaReserva.isBefore(LocalDateTime.now().plusHours(1))) {
            throw new RuntimeException("La reserva debe ser con al menos 1 hora de anticipación");
        }

        // Validar capacidad
        if (cantidadEstudiantes > sala.getCapacidadMaxima()) {
            throw new RuntimeException("La cantidad de estudiantes excede la capacidad máxima de la sala");
        }

        if (cantidadEstudiantes <= 0) {
            throw new RuntimeException("La cantidad de estudiantes debe ser mayor a 0");
        }

        // Validar que la sala no esté ocupada en la misma fecha
        List<Reserva> reservasEnFecha = reservaRepository.findBySalaAndFecha(salaId, fechaReserva);
        if (!reservasEnFecha.isEmpty()) {
            throw new RuntimeException("La sala ya está reservada para esa fecha");
        }

        // Validar que el estudiante no tenga otra reserva el mismo día
        List<Reserva> reservasEstudianteEnFecha = reservaRepository.findByEstudianteAndFecha(estudianteId, fechaReserva);
        if (!reservasEstudianteEnFecha.isEmpty()) {
            throw new RuntimeException("Ya tienes una reserva para esa fecha");
        }

        // Crear la reserva
        Reserva reserva = new Reserva();
        reserva.setEstudiante(estudiante);
        reserva.setSala(sala);
        reserva.setFechaReserva(fechaReserva);
        reserva.setCantidadEstudiantes(cantidadEstudiantes);
        reserva.setFechaCreacion(LocalDateTime.now());
        reserva.setEstado(Reserva.EstadoReserva.ACTIVA);

        return reservaRepository.save(reserva);
    }

    @Override
    public List<Reserva> listarReservasPorEstudiante(Long estudianteId) {
        return reservaRepository.findByEstudianteIdAndEstado(estudianteId, Reserva.EstadoReserva.ACTIVA);
    }

    @Override
    public List<Reserva> listarReservasPorSala(Long salaId) {
        return reservaRepository.findBySalaIdAndEstado(salaId, Reserva.EstadoReserva.ACTIVA);
    }

    @Override
    public List<Reserva> listarTodasReservas() {
        return reservaRepository.findByEstado(Reserva.EstadoReserva.ACTIVA);
    }

    @Override
    public Reserva obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
    }

    @Override
    public void cancelarReserva(Long reservaId, Long estudianteId) {
        Reserva reserva = obtenerReservaPorId(reservaId);

        // Validar que la reserva pertenece al estudiante
        if (!reserva.getEstudiante().getId().equals(estudianteId)) {
            throw new RuntimeException("Solo puedes cancelar tus propias reservas");
        }

        // Validar que la reserva esté activa
        if (reserva.getEstado() != Reserva.EstadoReserva.ACTIVA) {
            throw new RuntimeException("Solo se pueden cancelar reservas activas");
        }

        // Validar tiempo de cancelación (al menos 1 hora antes)
        long horasHastaReserva = ChronoUnit.HOURS.between(LocalDateTime.now(), reserva.getFechaReserva());
        if (horasHastaReserva < 1) {
            throw new RuntimeException("Las reservas solo se pueden cancelar con al menos 1 hora de anticipación");
        }

        reserva.setEstado(Reserva.EstadoReserva.CANCELADA);
        reservaRepository.save(reserva);
    }
}