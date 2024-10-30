package es.kuiko.api_comunidades.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProvinciaDTO {
	@NotNull
	private Integer codigo_provincia;

	@NotBlank
	private String nombre_provincia;

	@NotBlank
	private String codigo_ca;

	public Integer getCodigo_provincia() {
		return codigo_provincia;
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

