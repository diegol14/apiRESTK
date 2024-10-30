package es.kuiko.api_comunidades.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

public class ProvinciaDTO {

    @NotNull
    @Min(1)  
    @Max(9999)  // Límite sugerido, no tenemos requisitos dados mas que los de DB
    private Integer codigo_provincia;

    @NotBlank
    @Size(max = 50)  // Límite sugerido, no tenemos requisitos dados mas que los de DB
    private String nombre_provincia;

    @NotBlank
    @Size(max = 10)  // Límite sugerido, no tenemos requisitos dados mas que los de DB
    private String codigo_ca;

    public Integer getCodigo_provincia() {
        return codigo_provincia;
    }

    public void setCodigo_provincia(Integer codigo_provincia) {
        this.codigo_provincia = codigo_provincia;
    }

    public String getNombre_provincia() {
        return nombre_provincia;
    }

    public void setNombre_provincia(String nombre_provincia) {
        this.nombre_provincia = nombre_provincia;
    }

    public String getCodigo_ca() {
        return codigo_ca;
    }

    public void setCodigo_ca(String codigo_ca) {
        this.codigo_ca = codigo_ca;
    }
}

