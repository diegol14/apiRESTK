package es.kuiko.api_comunidades.dto;

import jakarta.validation.constraints.NotBlank;

public class ComAutonomaDTO {

	@NotBlank
	private String codigo_ca;

	@NotBlank
	private String nombre_ca;

	public String getCodigo_ca() {
		return codigo_ca;
	}

	public void setCodigo_ca(String codigo_ca) {
		this.codigo_ca = codigo_ca;
	}

	public String getNombre_ca() {
		return nombre_ca;
	}

	public void setNombre_ca(String nombre_ca) {
		this.nombre_ca = nombre_ca;
	}

}