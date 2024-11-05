package es.kuiko.api_comunidades.dto.gasolineras;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import java.util.List;

public class GasolineraWrapperApiResponseIn {

    @JsonProperty("Fecha")
    @Size(max = 30) // Suponiendo un formato de fecha con un límite razonable
    private String fecha;

    @JsonProperty("ListaEESSPrecio")
    private List<GasolineraDTOin> listaEESSPrecio;

    @JsonProperty("Nota")
    @Size(max = 300) // Limite razonable para un mensaje de nota extenso
    private String nota;

    @JsonProperty("ResultadoConsulta")
    @Size(max = 20) // Suponiendo que el resultado sea una cadena corta como "OK" u otros posibles valores
    private String resultadoConsulta;
    

    // Getters y setters

    public GasolineraWrapperApiResponseIn() {
	}
    

	public GasolineraWrapperApiResponseIn( String fecha, List<GasolineraDTOin> listaEESSPrecio,
			String nota,  String resultadoConsulta) {
		this.fecha = fecha;
		this.listaEESSPrecio = listaEESSPrecio;
		this.nota = nota;
		this.resultadoConsulta = resultadoConsulta;
	}


	public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<GasolineraDTOin> getListaEESSPrecio() {
        return listaEESSPrecio;
    }

    public void setListaEESSPrecio(List<GasolineraDTOin> listaEESSPrecio) {
        this.listaEESSPrecio = listaEESSPrecio;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getResultadoConsulta() {
        return resultadoConsulta;
    }

    public void setResultadoConsulta(String resultadoConsulta) {
        this.resultadoConsulta = resultadoConsulta;
    }
}
