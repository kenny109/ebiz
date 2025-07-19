package org.ebiz.msestudiantescmd.controller;

import lombok.extern.slf4j.Slf4j;
import org.ebiz.msestudiantescmd.model.Sala;
import org.ebiz.msestudiantescmd.service.SalaServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/salas")
public class SalaController {

    private final SalaServiceInterface salaService;

    public SalaController(SalaServiceInterface salaService) {
        this.salaService = salaService;
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Sala sala) {
        try {
            Sala guardada = salaService.guardar(sala);
            return ResponseWrapper.success(guardada, "Sala guardada exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseWrapper.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()).toResponseEntity();
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<Sala> salas = salaService.listar();
            return ResponseWrapper.success(salas, "Salas obtenidas exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseWrapper.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()).toResponseEntity();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            Sala sala = salaService.obtenerPorId(id);
            return ResponseWrapper.success(sala, "Sala obtenida exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseWrapper.error(e.getMessage(), HttpStatus.NOT_FOUND.value()).toResponseEntity();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Sala sala) {
        try {
            Sala actualizada = salaService.actualizar(id, sala);
            return ResponseWrapper.success(actualizada, "Sala actualizada exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseWrapper.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()).toResponseEntity();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            salaService.eliminar(id);
            return ResponseWrapper.success(null, "Sala eliminada exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseWrapper.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()).toResponseEntity();
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestParam boolean habilitado) {
        try {
            salaService.cambiarEstado(id, habilitado);
            return ResponseWrapper.success(null, "Estado de sala actualizado exitosamente").toResponseEntity();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseWrapper.error(e.getMessage(), HttpStatus.BAD_REQUEST.value()).toResponseEntity();
        }
    }
}