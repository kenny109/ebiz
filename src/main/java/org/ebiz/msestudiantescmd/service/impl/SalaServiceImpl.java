package org.ebiz.msestudiantescmd.service.impl;

import org.ebiz.msestudiantescmd.model.Sala;
import org.ebiz.msestudiantescmd.repository.SalaJpaRepository;
import org.ebiz.msestudiantescmd.service.SalaServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaServiceImpl implements SalaServiceInterface {

    private final SalaJpaRepository salaRepository;

    public SalaServiceImpl(SalaJpaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    @Override
    public Sala guardar(Sala sala) {
        if (sala.getCapacidadMaxima() <= 0) {
            throw new RuntimeException("La capacidad máxima debe ser mayor a 0");
        }
        if (sala.getDiasAnticipacionCancelacion() < 0) {
            throw new RuntimeException("Los días de anticipación no pueden ser negativos");
        }
        sala.setHabilitado(1);
        return salaRepository.save(sala);
    }

    @Override
    public List<Sala> listar() {
        return salaRepository.findAllByHabilitado(1);
    }

    @Override
    public Sala obtenerPorId(Long id) {
        return salaRepository.findByIdAndHabilitado(id, 1)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));
    }

    @Override
    public Sala actualizar(Long id, Sala sala) {
        Sala salaExistente = obtenerPorId(id);
        
        if (sala.getCapacidadMaxima() <= 0) {
            throw new RuntimeException("La capacidad máxima debe ser mayor a 0");
        }
        if (sala.getDiasAnticipacionCancelacion() < 0) {
            throw new RuntimeException("Los días de anticipación no pueden ser negativos");
        }
        
        salaExistente.setNombre(sala.getNombre());
        salaExistente.setCapacidadMaxima(sala.getCapacidadMaxima());
        salaExistente.setDiasAnticipacionCancelacion(sala.getDiasAnticipacionCancelacion());
        
        return salaRepository.save(salaExistente);
    }

    @Override
    public void eliminar(Long id) {
        Sala sala = obtenerPorId(id);
        sala.setHabilitado(0);
        salaRepository.save(sala);
    }

    @Override
    public void cambiarEstado(Long id, boolean habilitado) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala no encontrada"));
        sala.setHabilitado(habilitado ? 1 : 0);
        salaRepository.save(sala);
    }
}