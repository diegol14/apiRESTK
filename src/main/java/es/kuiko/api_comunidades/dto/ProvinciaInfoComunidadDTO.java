package es.kuiko.api_comunidades.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

public class ProvinciaInfoComunidadDTO {

    @NotNull
    @Min(1)  
    @Max(9999)  // Límite sugerido, no tenemos requisitos dados mas que los de DB
    private Integer codigoProvincia;
    
    @NotBlank
    @Size(max = 50)  // Límite sugerido, no tenemos requisitos dados mas que los de DB
    private String nombreProvincia;
    
    @NotBlank
    @Size(max = 10)  // Límite sugerido, no tenemos requisitos dados mas que los de DB
    private String codigoCa;
    
    @NotBlank
    @Size(max = 50)  // Límite sugerido, no tenemos requisitos dados mas que los de DB
    private String nombreCa;

    
	public ProvinciaInfoComunidadDTO( Integer codigoProvincia,
			 String nombreProvincia,  String codigoCa,
			 String nombreCa) {
		this.codigoProvincia = codigoProvincia;
		this.nombreProvincia = nombreProvincia;
		this.codigoCa = codigoCa;
		this.nombreCa = nombreCa;
	}

	public Integer getCodigoProvincia() {
		return codigoProvincia;
	}

	public void setCodigoProvincia(Integer codigoProvincia) {
		this.codigoProvincia = codigoProvincia;
	}

	public String getNombreProvincia() {
		return nombreProvincia;
	}

	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}

	public String getCodigoCa() {
		return codigoCa;
	}

	public void setCodigoCa(String codigoCa) {
		this.codigoCa = codigoCa;
	}

	public String getNombreCa() {
		return nombreCa;
	}

	public void setNombreCa(String nombreCa) {
		this.nombreCa = nombreCa;
	}
    
    
}