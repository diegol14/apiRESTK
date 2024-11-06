package es.kuiko.api_comunidades.dto.gasolineras;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Size;

/**
 * DTO para representar la información detallada de una gasolinera,
 * incluyendo datos de ubicación, horario y precios de diferentes tipos de combustibles.
 * Este DTO es utilizado para la respuesta de la API de gasolineras.
 */
@JsonPropertyOrder({
    "Fecha",
    "ListaEESSPrecio",
    "Nota",
    "ResultadoConsulta"
})
public class GasolineraDTOin {

    /**
     * Nombre de la gasolinera.
     */
    @JsonProperty("Rótulo")
    @Size(max = 100)
    private String rotulo;
    
    /**
     * Dirección de la gasolinera.
     */
    @JsonProperty("Dirección")
    @Size(max = 100)
    private String direccion;

    /**
     * Horario de apertura de la gasolinera.
     */
    @JsonProperty("Horario")
    @Size(max = 50)
    private String horario;

    /**
     * Localidad donde se encuentra la gasolinera.
     */
    @JsonProperty("Localidad")
    @Size(max = 50)
    private String localidad;

    /**
     * Municipio donde se encuentra la gasolinera.
     */
    @JsonProperty("Municipio")
    @Size(max = 50)
    private String municipio;

    /**
     * Provincia donde se encuentra la gasolinera.
     */
    @JsonProperty("Provincia")
    @Size(max = 50)
    private String provincia;

    /**
     * Identificador de la provincia.
     */
    @JsonProperty("IDProvincia")
    @Size(max = 4)
    private String idProvincia;

    /**
     * Latitud de la ubicación de la gasolinera.
     */
    @JsonProperty("Latitud")
    @Size(max = 20)
    private String latitud;

    /**
     * Longitud de la ubicación de la gasolinera.
     */
    @JsonProperty("Longitud (WGS84)")
    @Size(max = 20)
    private String longitud;

    /**
     * Precio del Gas Natural Comprimido (GNC).
     */
    @JsonAlias("Precio Gas Natural Comprimido")
    private String precioGNC;

    /**
     * Precio de los Gases Licuados del Petróleo (GLP).
     */
    @JsonAlias("Precio Gases licuados del petróleo")
    private String precioGLP;

    /**
     * Precio del Gasóleo A.
     */
    @JsonAlias("Precio Gasoleo A")
    private String precioGasoleoA;

    /**
     * Precio de la Gasolina 95 E5.
     */
    @JsonProperty("Precio Gasolina 95 E5")
    private String precioGasolina95E5;

    /**
     * Precio de la Gasolina 95 E10.
     */
    @JsonProperty("Precio Gasolina 95 E10")
    private String precioGasolina95E10;

    /**
     * Precio de la Gasolina 98 E5.
     */
    @JsonProperty("Precio Gasolina 98 E5")
    private String precioGasolina98E5;

    /**
     * Precio de la Gasolina 98 E10.
     */
    @JsonProperty("Precio Gasolina 98 E10")
    private String precioGasolina98E10;

    // Getters y setters de todas las propiedades

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(String idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getPrecioGNC() {
        return precioGNC;
    }

    public void setPrecioGNC(String precioGNC) {
        this.precioGNC = precioGNC;
    }

    public String getPrecioGLP() {
        return precioGLP;
    }

    public void setPrecioGLP(String precioGLP) {
        this.precioGLP = precioGLP;
    }

    public String getPrecioGasoleoA() {
        return precioGasoleoA;
    }

    public void setPrecioGasoleoA(String precioGasoleoA) {
        this.precioGasoleoA = precioGasoleoA;
    }

    public String getPrecioGasolina95E5() {
        return precioGasolina95E5;
    }

    public void setPrecioGasolina95E5(String precioGasolina95E5) {
        this.precioGasolina95E5 = precioGasolina95E5;
    }

    public String getPrecioGasolina95E10() {
        return precioGasolina95E10;
    }

    public void setPrecioGasolina95E10(String precioGasolina95E10) {
        this.precioGasolina95E10 = precioGasolina95E10;
    }

    public String getPrecioGasolina98E5() {
        return precioGasolina98E5;
    }

    public void setPrecioGasolina98E5(String precioGasolina98E5) {
        this.precioGasolina98E5 = precioGasolina98E5;
    }

    public String getPrecioGasolina98E10() {
        return precioGasolina98E10;
    }

    public void setPrecioGasolina98E10(String precioGasolina98E10) {
        this.precioGasolina98E10 = precioGasolina98E10;
    }
}
