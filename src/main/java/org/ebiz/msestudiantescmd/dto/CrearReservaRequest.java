package org.ebiz.msestudiantescmd.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CrearReservaRequest {
    private Long estudianteId;
    private Long salaId;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaReserva;
    
    private int cantidadEstudiantes;
}