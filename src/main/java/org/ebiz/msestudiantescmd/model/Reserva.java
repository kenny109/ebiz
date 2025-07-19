package org.ebiz.msestudiantescmd.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_reserva", schema = "estudiantes")
@Setter
@Getter
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_estudiante")
    @JsonManagedReference
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "id_sala")
    @JsonManagedReference
    private Sala sala;

    @Column(name = "fecha_reserva")
    private LocalDateTime fechaReserva;

    @Column(name = "cantidad_estudiantes")
    private int cantidadEstudiantes;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoReserva estado;

    public enum EstadoReserva {
        ACTIVA, CANCELADA, COMPLETADA
    }
}