package es.kuiko.api_comunidades.dto.gasolineras;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * DTO para representar la información de salida de una gasolinera.
 * Incluye datos como nombre, ubicación (dirección, localidad, municipio, provincia),
 * horario de atención y precios de distintos tipos de combustibles.
 * Este DTO se utiliza en las respuestas de la API de gasolineras.
 */
@JsonPropertyOrder({
    "Rótulo", "Dirección", "Horario", "Localidad", "Municipio", "Provincia", "IDProvincia",
    "Latitud", "Longitud (WGS84)", "precioGasoleoA",
    "Precio Gasolina 95 E5", "Precio Gasolina 95 E10", "Precio Gasolina 98 E5", "Precio Gasolina 98 E10", "precioGNC", "precioGLP"
})
public class GasolineraDTOout {

    /**
     * Nombre o rótulo de la gasolinera.
     */
    @JsonProperty("Rótulo")
    private String rotulo;

    /**
     * Dirección de la gasolinera.
     */
    @JsonProperty("Dirección")
    private String direccion;

    /**
     * Horario de apertura de la gasolinera.
     */
    @JsonProperty("Horario")
    private String horario;

    /**
     * Localidad donde se encuentra la gasolinera.
     */
    @JsonProperty("Localidad")
    private String localidad;

    /**
     * Municipio donde se encuentra la gasolinera.
     */
    @JsonProperty("Municipio")
    private String municipio;

    /**
     * Provincia donde se encuentra la gasolinera.
     */
    @JsonProperty("Provincia")
    private String provincia;

    /**
     * Identificador de la provincia.
     */
    @JsonProperty("IDProvincia")
    private String idProvincia;

    /**
     * Latitud de la ubicación de la gasolinera.
     */
    @JsonProperty("Latitud")
    private String latitud;

    /**
     * Longitud de la ubicación de la gasolinera en el sistema de coordenadas WGS84.
     */
    @JsonProperty("Longitud (WGS84)")
    private String longitud;

    /**
     * Precio de la Gasolina 95 E5.
     */
    @JsonProperty("Precio Gasolina 95")
    private String precioGasolina95;

    /**
     * Precio de la Gasolina 98 E5.
     */
    @JsonProperty("Precio Gasolina 98")
    private String precioGasolina98;

    /**
     * Precio del Gasóleo A.
     */
    @JsonProperty("Precio Gasoleo A")
    private String precioGasoleoA;

    /**
     * Precio del Gas Natural Comprimido (GNC).
     */
    @JsonProperty("Precio GNC")
    private String precioGNC;

    /**
     * Precio de los Gases Licuados del Petróleo (GLP).
     */
    @JsonProperty("Precio GLP")
    private String precioGLP;

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

    public String getPrecioGasolina95() {
        return precioGasolina95;
    }

    public void setPrecioGasolina95(String precioGasolina95) {
        this.precioGasolina95 = precioGasolina95;
    }

    public String getPrecioGasolina98() {
        return precioGasolina98;
    }

    public void setPrecioGasolina98(String precioGasolina98) {
        this.precioGasolina98 = precioGasolina98;
    }

    public String getPrecioGasoleoA() {
        return precioGasoleoA;
    }

    public void setPrecioGasoleoA(String precioGasoleoA) {
        this.precioGasoleoA = precioGasoleoA;
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
}
