package org.ebiz.msestudiantescmd.service;

import org.ebiz.msestudiantescmd.model.Sala;

import java.util.List;

public interface SalaServiceInterface {
    Sala guardar(Sala sala);
    List<Sala> listar();
    Sala obtenerPorId(Long id);
    Sala actualizar(Long id, Sala sala);
    void eliminar(Long id);
    void cambiarEstado(Long id, boolean habilitado);
}