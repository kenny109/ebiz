package org.ebiz.msestudiantescmd.service;

import org.ebiz.msestudiantescmd.model.Reserva;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaServiceInterface {
    Reserva crearReserva(Long estudianteId, Long salaId, LocalDateTime fechaReserva, int cantidadEstudiantes);
    List<Reserva> listarReservasPorEstudiante(Long estudianteId);
    List<Reserva> listarReservasPorSala(Long salaId);
    List<Reserva> listarTodasReservas();
    Reserva obtenerReservaPorId(Long id);
    void cancelarReserva(Long reservaId, Long estudianteId);
}