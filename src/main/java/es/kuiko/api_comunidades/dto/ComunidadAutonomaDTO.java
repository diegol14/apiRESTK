package es.kuiko.api_comunidades.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ComunidadAutonomaDTO {

    @NotBlank
    @Size(max = 10)  // Límite sugerido, no tenemos requisitos dados mas que los de DB
    private String codigoCa;

    @NotBlank
    @Size(max = 50)  // Límite sugerido, no tenemos requisitos dados mas que los de DB
    private String nombreCa;

    public String getCodigoCa() {
        return codigoCa;
    }

    public void setCodigoCa(String codigoCa) {
        this.codigoCa = codigoCa;
    }

    public String getNombreCa() {
        return nombreCa;
    }

    public void setNombreCa(String nombre_ca) {
        this.nombreCa = nombre_ca;
    }
}
