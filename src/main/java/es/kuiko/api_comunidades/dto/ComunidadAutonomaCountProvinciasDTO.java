package es.kuiko.api_comunidades.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para representar el conteo de provincias en una Comunidad Autónoma.
 * Contiene información básica de la Comunidad Autónoma, como el código, el nombre
 * y la cantidad de provincias que pertenecen a ella.
 */
public class ComunidadAutonomaCountProvinciasDTO implements ComunidadAutonomaCountProvinciasProjection {

    /**
     * Código de la Comunidad Autónoma. Debe estar presente y tener un tamaño máximo de 10 caracteres.
     */
    @NotBlank
    @Size(max = 10)
    private String codigoCa;
    
    /**
     * Nombre de la Comunidad Autónoma. Debe estar presente y tener un tamaño máximo de 50 caracteres.
     */
    @NotBlank
    @Size(max = 50)
    private String nombreCa;
    
    /**
     * Cantidad de provincias en la Comunidad Autónoma.
     * Debe ser un valor positivo (mínimo 1) y un máximo sugerido de 9999.
     */
    @NotNull
    @Min(1)
    @Max(9999)
    private Integer cantidadProvinciaInComunidad;

    /**
     * Constructor para inicializar los campos de ComunidadAutonomaCountProvinciasDTO.
     *
     * @param codigoCa Código de la Comunidad Autónoma (máximo 10 caracteres).
     * @param nombreCa Nombre de la Comunidad Autónoma (máximo 50 caracteres).
     * @param cantidadProvinciaInComunidad Cantidad de provincias en la Comunidad Autónoma.
     */
    public ComunidadAutonomaCountProvinciasDTO(@NotBlank @Size(max = 10) String codigoCa,
                                               @NotBlank @Size(max = 50) String nombreCa,
                                               @NotNull @Min(1) @Max(9999) Integer cantidadProvinciaInComunidad) {
        this.codigoCa = codigoCa;
        this.nombreCa = nombreCa;
        this.cantidadProvinciaInComunidad = cantidadProvinciaInComunidad;
    }

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

    /**
     * Obtiene la cantidad de provincias en la Comunidad Autónoma.
     *
     * @return Cantidad de provincias en la Comunidad Autónoma.
     */
    public Integer getCantidadProvinciaInComunidad() {
        return cantidadProvinciaInComunidad;
    }

    /**
     * Establece la cantidad de provincias en la Comunidad Autónoma.
     *
     * @param cantidadProvinciaInComunidad Cantidad de provincias en la Comunidad Autónoma.
     */
    public void setCantidadProvinciaInComunidad(Integer cantidadProvinciaInComunidad) {
        this.cantidadProvinciaInComunidad = cantidadProvinciaInComunidad;
    }
}
