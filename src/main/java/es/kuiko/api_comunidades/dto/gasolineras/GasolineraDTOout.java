package es.kuiko.api_comunidades.dto.gasolineras;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.Size;

@JsonPropertyOrder({
    "Rótulo", "Dirección", "Horario", "Localidad", "Municipio", "Provincia", "IDProvincia",
    "Latitud", "Longitud (WGS84)",  "precioGasoleoA",
    "Precio Gasolina 95 E5", "Precio Gasolina 95 E10", "Precio Gasolina 98 E5", "Precio Gasolina 98 E10","precioGNC", "precioGLP"
})
public class GasolineraDTOout {
	
	@JsonProperty("Rótulo")
    private String rotulo;

    @JsonProperty("Dirección")
    private String direccion;

    @JsonProperty("Horario")
    private String horario;

    @JsonProperty("Localidad")
    private String localidad;

    @JsonProperty("Municipio")
    private String municipio;

    @JsonProperty("Provincia")
    private String provincia;

    @JsonProperty("IDProvincia")
    private String idProvincia;

    @JsonProperty("Latitud")
    private String latitud;

    @JsonProperty("Longitud (WGS84)")  // Corregido aquí
    private String longitud;
    
    @JsonProperty("Precio Gasolina 95")
    private String precioGasolina95;

    @JsonProperty("Precio Gasolina 98")
    private String precioGasolina98;
    
    @JsonProperty("Precio Gasoleo A")
    private String precioGasoleoA;

    @JsonProperty("Precio GNC")
    private String precioGNC;

    @JsonProperty("Precio GLP")
    private String precioGLP;





    // Getters y Setters

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
