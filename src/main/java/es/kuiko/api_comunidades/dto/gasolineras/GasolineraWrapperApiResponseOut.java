package es.kuiko.api_comunidades.dto.gasolineras;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

/**
 * DTO para representar la respuesta de salida de la API de gasolineras 
 * de una provincia en particular solicitada por parámetro en la url.
 * Contiene información como la fecha de la consulta, una lista de estaciones de servicio (EESS),
 * una nota adicional y el resultado de la consulta. La ordenación de los campos en la respuesta
 * está especificada con la anotación {@code JsonPropertyOrder}.
 */
@JsonPropertyOrder({
    "Fecha",
    "ListaEESSPrecio",
    "Nota",
    "ResultadoConsulta"
})
public class GasolineraWrapperApiResponseOut {

    /**
     * Fecha de la consulta realizada. Se espera que esté en un formato de fecha adecuado.
     */
    @JsonProperty("Fecha")
    private String fecha;

    /**
     * Lista de estaciones de servicio con detalles de precios.
     * Cada elemento de la lista es un objeto {@code GasolineraDTOout}.
     */
    @JsonProperty("ListaEESSPrecio")
    private List<GasolineraDTOout> listaEESSPrecio;

    /**
     * Nota adicional incluida en la respuesta. Puede contener información adicional sobre la consulta.
     */
    @JsonProperty("Nota")
    private String nota;

    /**
     * Resultado de la consulta, indicando el estado de la misma. 
     * Puede tener valores como "OK" u otros estados posibles.
     */
    @JsonProperty("ResultadoConsulta")
    private String resultadoConsulta;

    /**
     * Constructor vacío para GasolineraWrapperApiResponseOut.
     */
    public GasolineraWrapperApiResponseOut() {
    }

    /**
     * Constructor completo para inicializar todos los campos de GasolineraWrapperApiResponseOut.
     *
     * @param fecha Fecha de la consulta.
     * @param listaEESSPrecio Lista de estaciones de servicio.
     * @param nota Nota adicional sobre la consulta.
     * @param resultadoConsulta Resultado de la consulta.
     */
    public GasolineraWrapperApiResponseOut(String fecha, List<GasolineraDTOout> listaEESSPrecio, 
                                           String nota, String resultadoConsulta) {
        this.fecha = fecha;
        this.listaEESSPrecio = listaEESSPrecio;
        this.nota = nota;
        this.resultadoConsulta = resultadoConsulta;
    }

    /**
     * Obtiene la fecha de la consulta realizada.
     *
     * @return Fecha de la consulta.
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de la consulta.
     *
     * @param fecha Fecha de la consulta.
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene la lista de estaciones de servicio (EESS) con detalles de precios.
     *
     * @return Lista de estaciones de servicio.
     */
    public List<GasolineraDTOout> getListaEESSPrecio() {
        return listaEESSPrecio;
    }

    /**
     * Establece la lista de estaciones de servicio con detalles de precios.
     *
     * @param listaEESSPrecio Lista de estaciones de servicio.
     */
    public void setListaEESSPrecio(List<GasolineraDTOout> listaEESSPrecio) {
        this.listaEESSPrecio = listaEESSPrecio;
    }

    /**
     * Obtiene la nota adicional incluida en la respuesta de la consulta.
     *
     * @return Nota adicional.
     */
    public String getNota() {
        return nota;
    }

    /**
     * Establece la nota adicional en la respuesta de la consulta.
     *
     * @param nota Nota adicional.
     */
    public void setNota(String nota) {
        this.nota = nota;
    }

    /**
     * Obtiene el resultado de la consulta.
     *
     * @return Resultado de la consulta.
     */
    public String getResultadoConsulta() {
        return resultadoConsulta;
    }

    /**
     * Establece el resultado de la consulta.
     *
     * @param resultadoConsulta Resultado de la consulta.
     */
    public void setResultadoConsulta(String resultadoConsulta) {
        this.resultadoConsulta = resultadoConsulta;
    }
}
