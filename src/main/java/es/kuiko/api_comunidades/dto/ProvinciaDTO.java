package es.kuiko.api_comunidades.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

/**
 * DTO para representar la información básica de una Provincia.
 * Incluye el código de la provincia, el nombre y el código de la Comunidad Autónoma
 * a la que pertenece. Este DTO se utiliza para transferir datos de provincia entre 
 * el cliente y el servidor en operaciones de creación y actualización.
 */
public class ProvinciaDTO {

    /**
     * Constructor vacío para ProvinciaDTO.
     */
    public ProvinciaDTO() {
    }
    
    /**
     * Constructor completo para inicializar los campos de ProvinciaDTO.
     *
     * @param codigoProvincia Código de la provincia (valor entre 1 y 9999).
     * @param nombreProvincia Nombre de la provincia (máximo 50 caracteres).
     * @param codigoCa Código de la Comunidad Autónoma (máximo 10 caracteres).
     */
    public ProvinciaDTO(Integer codigoProvincia,
                        String nombreProvincia, 
                        String codigoCa) {
        this.codigoProvincia = codigoProvincia;
        this.nombreProvincia = nombreProvincia;
        this.codigoCa = codigoCa;
    }

    /**
     * Código de la provincia. Debe ser un valor no nulo entre 1 y 9999.
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
}
