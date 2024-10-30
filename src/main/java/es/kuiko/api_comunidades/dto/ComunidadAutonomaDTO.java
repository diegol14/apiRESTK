package es.kuiko.api_comunidades.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ComunidadAutonomaDTO {

    @NotBlank
    @Size(max = 10)  // Límite sugerido, no tenemos requisitos dados mas que los de DB
    private String codigo_ca;

    @NotBlank
    @Size(max = 50)  // Límite sugerido, no tenemos requisitos dados mas que los de DB
    private String nombre_ca;

    public String getCodigo_ca() {
        return codigo_ca;
    }

    public void setCodigo_ca(String codigo_ca) {
        this.codigo_ca = codigo_ca;
    }

    public String getNombre_ca() {
        return nombre_ca;
    }

    public void setNombre_ca(String nombre_ca) {
        this.nombre_ca = nombre_ca;
    }
}
