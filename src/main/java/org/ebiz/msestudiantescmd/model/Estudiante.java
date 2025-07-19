package org.ebiz.msestudiantescmd.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "t_estudiante", schema = "estudiantes")
@Setter
@Getter
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estudiante")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "codigo", unique = true)
    private String codigo;

    @Column(name = "correo", unique = true)
    private String correo;

    @Column(name = "carrera")
    private String carrera;

    @Column(name = "habilitado")
    private int habilitado;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_aula")
    @JsonBackReference
    private Aula aula;

    @OneToMany(mappedBy = "estudiante")
    private List<Reserva> reservas;
}