package es.kuiko.api_comunidades.dto.gasolineras;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonPropertyOrder({
    "Fecha",
    "ListaEESSPrecio",
    "Nota",
    "ResultadoConsulta"
})
public class GasolineraWrapperApiResponseOut {

    @JsonProperty("Fecha")
    private String fecha;

    @JsonProperty("ListaEESSPrecio")
    private List<GasolineraDTOout> listaEESSPrecio;

    @JsonProperty("Nota")
    private String nota;

    @JsonProperty("ResultadoConsulta")
    private String resultadoConsulta;

    // Getters y setters

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<GasolineraDTOout> getListaEESSPrecio() {
        return listaEESSPrecio;
    }

    public void setListaEESSPrecio(List<GasolineraDTOout> listaEESSPrecio) {
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
