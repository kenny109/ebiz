package org.ebiz.msestudiantescmd.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "t_sala", schema = "estudiantes")
@Setter
@Getter
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sala")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "capacidad_maxima")
    private int capacidadMaxima;

    @Column(name = "dias_anticipacion_cancelacion")
    private int diasAnticipacionCancelacion;

    @Column(name = "habilitado")
    private int habilitado;

    @OneToMany(mappedBy = "sala")
    private List<Reserva> reservas;
}