package es.kuiko.api_comunidades.dto.gasolineras;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * DTO para representar la respuesta de la API de gasolineras de una provincia en particular 
 * solicitada por parámetro en la url.
 * Contiene información de la fecha de la respuesta, una lista de precios de estaciones de servicio,
 * una nota adicional y el resultado de la consulta.
 */
public class GasolineraWrapperApiResponseIn {

    /**
     * Fecha de la consulta. Debe tener un tamaño máximo de 30 caracteres.
     * Suponiendo que el formato de la fecha puede variar, por ejemplo "YYYY-MM-DD HH:MM:SS".
     */
    @JsonProperty("Fecha")
    @Size(max = 30)
    private String fecha;

    /**
     * Lista de precios de estaciones de servicio. Contiene un conjunto de objetos GasolineraDTOin,
     * que representan los detalles de cada estación de servicio.
     */
    @JsonProperty("ListaEESSPrecio")
    private List<GasolineraDTOin> listaEESSPrecio;

    /**
     * Nota adicional proporcionada en la respuesta de la API. Debe tener un tamaño máximo de 300 caracteres.
     */
    @JsonProperty("Nota")
    @Size(max = 300)
    private String nota;

    /**
     * Resultado de la consulta. Suponemos que el valor será una cadena corta como "OK" o algún otro estado breve.
     * Debe tener un tamaño máximo de 20 caracteres.
     */
    @JsonProperty("ResultadoConsulta")
    @Size(max = 20)
    private String resultadoConsulta;

    /**
     * Constructor vacío para GasolineraWrapperApiResponseIn.
     */
    public GasolineraWrapperApiResponseIn() {
    }

    /**
     * Constructor completo para inicializar todos los campos de GasolineraWrapperApiResponseIn.
     *
     * @param fecha Fecha de la consulta (máximo 30 caracteres).
     * @param listaEESSPrecio Lista de precios de estaciones de servicio.
     * @param nota Nota adicional (máximo 300 caracteres).
     * @param resultadoConsulta Resultado de la consulta (máximo 20 caracteres).
     */
    public GasolineraWrapperApiResponseIn(String fecha, List<GasolineraDTOin> listaEESSPrecio,
                                          String nota, String resultadoConsulta) {
        this.fecha = fecha;
        this.listaEESSPrecio = listaEESSPrecio;
        this.nota = nota;
        this.resultadoConsulta = resultadoConsulta;
    }

    /**
     * Obtiene la fecha de la consulta.
     *
     * @return Fecha de la consulta.
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de la consulta.
     *
     * @param fecha Fecha de la consulta (máximo 30 caracteres).
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene la lista de precios de estaciones de servicio.
     *
     * @return Lista de precios de estaciones de servicio.
     */
    public List<GasolineraDTOin> getListaEESSPrecio() {
        return listaEESSPrecio;
    }

    /**
     * Establece la lista de precios de estaciones de servicio.
     *
     * @param listaEESSPrecio Lista de precios de estaciones de servicio.
     */
    public void setListaEESSPrecio(List<GasolineraDTOin> listaEESSPrecio) {
        this.listaEESSPrecio = listaEESSPrecio;
    }

    /**
     * Obtiene la nota adicional de la respuesta.
     *
     * @return Nota adicional.
     */
    public String getNota() {
        return nota;
    }

    /**
     * Establece la nota adicional de la respuesta.
     *
     * @param nota Nota adicional (máximo 300 caracteres).
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
     * @param resultadoConsulta Resultado de la consulta (máximo 20 caracteres).
     */
    public void setResultadoConsulta(String resultadoConsulta) {
        this.resultadoConsulta = resultadoConsulta;
    }
}
