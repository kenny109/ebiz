package org.ebiz.msestudiantescmd.repository;

import org.ebiz.msestudiantescmd.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstudianteJpaRepository extends JpaRepository<Estudiante, Long> {
    Optional<Estudiante> findById(Long id);
    List<Estudiante> findAllByHabilitado(int habilitado);

    Optional<Estudiante> findByNombreAndApellido(String nombre, String apellido);
    Optional<Estudiante> findByCorreo(String correo);
    Optional<Estudiante> findByCodigo(String codigo);
    Optional<Estudiante> findByIdAndHabilitado(Long id, int habilitado);
}