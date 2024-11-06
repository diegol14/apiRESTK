package es.kuiko.api_comunidades.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para representar la información básica de una Comunidad Autónoma.
 * Contiene el código y el nombre de la Comunidad Autónoma. Este DTO se 
 * utiliza en operaciones de transferencia de datos entre el cliente y el 
 * servidor, especialmente en operaciones de creación y actualización.
 */
public class ComunidadAutonomaDTO {

    /**
     * Código de la Comunidad Autónoma. Debe ser un valor no vacío y tener 
     * un tamaño máximo de 10 caracteres.
     */
    @NotBlank
    @Size(max = 10)
    private String codigoCa;

    /**
     * Nombre de la Comunidad Autónoma. Debe ser un valor no vacío y tener 
     * un tamaño máximo de 50 caracteres.
     */
    @NotBlank
    @Size(max = 50)
    private String nombreCa;

    /**
     * Obtiene el código de la Comunidad Autónoma.
     *
     * @return Código de la Comunidad Autónoma.
     */
    public String getCodigoCa() {
        return codigoCa;
    }

    /**
     * Establece el código de la Comunidad Autónoma.
     *
     * @param codigoCa Código de la Comunidad Autónoma.
     */
    public void setCodigoCa(String codigoCa) {
        this.codigoCa = codigoCa;
    }

    /**
     * Obtiene el nombre de la Comunidad Autónoma.
     *
     * @return Nombre de la Comunidad Autónoma.
     */
    public String getNombreCa() {
        return nombreCa;
    }

    /**
     * Establece el nombre de la Comunidad Autónoma.
     *
     * @param nombreCa Nombre de la Comunidad Autónoma.
     */
    public void setNombreCa(String nombreCa) {
        this.nombreCa = nombreCa;
    }
}
