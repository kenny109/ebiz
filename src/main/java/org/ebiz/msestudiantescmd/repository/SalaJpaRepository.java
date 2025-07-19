package org.ebiz.msestudiantescmd.repository;

import org.ebiz.msestudiantescmd.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SalaJpaRepository extends JpaRepository<Sala, Long> {
    List<Sala> findAllByHabilitado(int habilitado);
    Optional<Sala> findByIdAndHabilitado(Long id, int habilitado);
}