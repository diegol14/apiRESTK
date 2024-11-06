package es.kuiko.api_comunidades.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

/**
 * DTO para representar información detallada de una Provincia junto con
 * los datos de la Comunidad Autónoma a la que pertenece.
 * Incluye el código y el nombre de la provincia, así como el código y el nombre
 * de la comunidad autónoma asociada. Este DTO es útil para mostrar detalles
 * completos de una provincia y su comunidad.
 */
public class ProvinciaInfoComunidadDTO {

    /**
     * Código de la provincia. Debe ser un valor entre 1 y 9999.
     */
    @NotNull
    @Min(1)  
    @Max(9999)
    private Integer codigoProvincia;
    
    /**
     * Nombre de la provincia. Debe ser un valor no vacío y tener un tamaño máximo de 50 caracteres.
     */
    @NotBlank
    @Size(max = 50)
    private String nombreProvincia;
    
    /**
     * Código de la Comunidad Autónoma a la que pertenece la provincia.
     * Debe ser un valor no vacío y tener un tamaño máximo de 10 caracteres.
     */
    @NotBlank
    @Size(max = 10)
    private String codigoCa;
    
    /**
     * Nombre de la Comunidad Autónoma a la que pertenece la provincia.
     * Debe ser un valor no vacío y tener un tamaño máximo de 50 caracteres.
     */
    @NotBlank
    @Size(max = 50)
    private String nombreCa;

    /**
     * Constructor completo para inicializar los campos de ProvinciaInfoComunidadDTO.
     *
     * @param codigoProvincia Código de la provincia (valor entre 1 y 9999).
     * @param nombreProvincia Nombre de la provincia (máximo 50 caracteres).
     * @param codigoCa Código de la Comunidad Autónoma (máximo 10 caracteres).
     * @param nombreCa Nombre de la Comunidad Autónoma (máximo 50 caracteres).
     */
    public ProvinciaInfoComunidadDTO(Integer codigoProvincia, String nombreProvincia, String codigoCa, String nombreCa) {
        this.codigoProvincia = codigoProvincia;
        this.nombreProvincia = nombreProvincia;
        this.codigoCa = codigoCa;
        this.nombreCa = nombreCa;
    }

    /**
     * Obtiene el código de la provincia.
     *
     * @return Código de la provincia.
     */
    public Integer getCodigoProvincia() {
        return codigoProvincia;
    }

    /**
     * Establece el código de la provincia.
     *
     * @param codigoProvincia Código de la provincia (valor entre 1 y 9999).
     */
    public void setCodigoProvincia(Integer codigoProvincia) {
        this.codigoProvincia = codigoProvincia;
    }

    /**
     * Obtiene el nombre de la provincia.
     *
     * @return Nombre de la provincia.
     */
    public String getNombreProvincia() {
        return nombreProvincia;
    }

    /**
     * Establece el nombre de la provincia.
     *
     * @param nombreProvincia Nombre de la provincia (máximo 50 caracteres).
     */
    public void setNombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }

    /**
     * Obtiene el código de la Comunidad Autónoma a la que pertenece la provincia.
     *
     * @return Código de la Comunidad Autónoma.
     */
    public String getCodigoCa() {
        return codigoCa;
    }

    /**
     * Establece el código de la Comunidad Autónoma a la que pertenece la provincia.
     *
     * @param codigoCa Código de la Comunidad Autónoma (máximo 10 caracteres).
     */
    public void setCodigoCa(String codigoCa) {
        this.codigoCa = codigoCa;
    }

    /**
     * Obtiene el nombre de la Comunidad Autónoma a la que pertenece la provincia.
     *
     * @return Nombre de la Comunidad Autónoma.
     */
    public String getNombreCa() {
        return nombreCa;
    }

    /**
     * Establece el nombre de la Comunidad Autónoma a la que pertenece la provincia.
     *
     * @param nombreCa Nombre de la Comunidad Autónoma (máximo 50 caracteres).
     */
    public void setNombreCa(String nombreCa) {
        this.nombreCa = nombreCa;
    }
}
