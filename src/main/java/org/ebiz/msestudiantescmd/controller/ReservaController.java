package org.ebiz.msestudiantescmd.controller;

import lombok.extern.slf4j.Slf4j;
import org.ebiz.msestudiantescmd.dto.CrearReservaRequest;
import org.ebiz.msestudiantescmd.model.Reserva;
import org.ebiz.msestudiantescmd.service.ReservaServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaServiceInterface reservaService;

    public ReservaController(ReservaServiceInterface reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<?> crearReserva(@RequestBody CrearReservaRequest request) {
        try {
            Reserva reserva = reservaService.crearReserva(
                    request.getEstudianteId(),
                    request.getSalaId(),
                    request.getFechaReserva(),
                    request.getCantidadEstudiantes()
            );
            return ResponseWrapper.success(reserva, "Reserva creada exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseWrapper.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()).toResponseEntity();
        }
    }

    @GetMapping
    public ResponseEntity<?> listarTodasReservas() {
        try {
            List<Reserva> reservas = reservaService.listarTodasReservas();
            return ResponseWrapper.success(reservas, "Reservas obtenidas exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseWrapper.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()).toResponseEntity();
        }
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<?> listarReservasPorEstudiante(@PathVariable Long estudianteId) {
        try {
            List<Reserva> reservas = reservaService.listarReservasPorEstudiante(estudianteId);
            return ResponseWrapper.success(reservas, "Reservas del estudiante obtenidas exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseWrapper.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()).toResponseEntity();
        }
    }

    @GetMapping("/sala/{salaId}")
    public ResponseEntity<?> listarReservasPorSala(@PathVariable Long salaId) {
        try {
            List<Reserva> reservas = reservaService.listarReservasPorSala(salaId);
            return ResponseWrapper.success(reservas, "Reservas de la sala obtenidas exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseWrapper.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()).toResponseEntity();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerReservaPorId(@PathVariable Long id) {
        try {
            Reserva reserva = reservaService.obtenerReservaPorId(id);
            return ResponseWrapper.success(reserva, "Reserva obtenida exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseWrapper.error(e.getMessage(), HttpStatus.NOT_FOUND.value()).toResponseEntity();
        }
    }

    @DeleteMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarReserva(@PathVariable Long id, @RequestParam Long estudianteId) {
        try {
            reservaService.cancelarReserva(id, estudianteId);
            return ResponseWrapper.success(null, "Reserva cancelada exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseWrapper.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()).toResponseEntity();
        }
    }
}