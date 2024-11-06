package es.kuiko.api_comunidades.mapper;

import es.kuiko.api_comunidades.dto.gasolineras.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para transformar los datos de entrada de gasolineras a la estructura de salida
 * utilizada por la API. Convierte objetos de tipo GasolineraWrapperApiResponseIn y GasolineraDTOin
 * a sus correspondientes tipos de salida GasolineraWrapperApiResponseOut y GasolineraDTOout.
 */
@Component
public class GasolineraMapper {

    /**
     * Transforma un objeto de tipo GasolineraWrapperApiResponseIn en un objeto de tipo
     * GasolineraWrapperApiResponseOut. Este método copia directamente los datos de cabecera
     * y pie de la respuesta y convierte cada elemento de la lista de gasolineras.
     *
     * @param response Objeto de entrada de tipo GasolineraWrapperApiResponseIn.
     * @return Objeto transformado de tipo GasolineraWrapperApiResponseOut.
     */
    public GasolineraWrapperApiResponseOut mapToGasolineraWrapperApiResponseOut(GasolineraWrapperApiResponseIn response) {
        GasolineraWrapperApiResponseOut responseOut = new GasolineraWrapperApiResponseOut();
        
        // Mapear directamente los campos de cabecera y pie
        responseOut.setFecha(response.getFecha());
        responseOut.setNota(response.getNota());
        responseOut.setResultadoConsulta(response.getResultadoConsulta());
        
        // Transformar la lista de gasolineras de entrada a la lista de salida
        List<GasolineraDTOout> listaEESSPrecio = response.getListaEESSPrecio().stream()
                .map(this::mapToGasolineraDTOout)  // Llamada al método de mapeo de cada gasolinera individual
                .collect(Collectors.toList());

        responseOut.setListaEESSPrecio(listaEESSPrecio);
        
        return responseOut;
    }

    /**
     * Transforma un objeto de tipo GasolineraDTOin en un objeto de tipo GasolineraDTOout.
     * Este método mapea cada campo de información de la gasolinera y selecciona el precio
     * disponible para la gasolina 95 y 98, dando preferencia a E5 sobre E10.
     *
     * @param gasolineraDTOin Objeto de entrada de tipo GasolineraDTOin.
     * @return Objeto transformado de tipo GasolineraDTOout.
     */
    public GasolineraDTOout mapToGasolineraDTOout(GasolineraDTOin gasolineraDTOin) {
        GasolineraDTOout gasolineraDTOout = new GasolineraDTOout();

        // Mapear los datos de la gasolinera individual
        gasolineraDTOout.setRotulo(gasolineraDTOin.getRotulo());
        gasolineraDTOout.setDireccion(gasolineraDTOin.getDireccion());
        gasolineraDTOout.setHorario(gasolineraDTOin.getHorario());
        gasolineraDTOout.setLocalidad(gasolineraDTOin.getLocalidad());
        gasolineraDTOout.setMunicipio(gasolineraDTOin.getMunicipio());
        gasolineraDTOout.setProvincia(gasolineraDTOin.getProvincia());
        gasolineraDTOout.setIdProvincia(gasolineraDTOin.getIdProvincia());
        gasolineraDTOout.setLatitud(gasolineraDTOin.getLatitud());
        gasolineraDTOout.setLongitud(gasolineraDTOin.getLongitud());
        gasolineraDTOout.setPrecioGNC(gasolineraDTOin.getPrecioGNC());
        gasolineraDTOout.setPrecioGLP(gasolineraDTOin.getPrecioGLP());
        gasolineraDTOout.setPrecioGasoleoA(gasolineraDTOin.getPrecioGasoleoA());

        // Selección de gasolina 95 con preferencia E5
        gasolineraDTOout.setPrecioGasolina95(selectAvailablePrice(gasolineraDTOin.getPrecioGasolina95E5(), gasolineraDTOin.getPrecioGasolina95E10()));

        // Selección de gasolina 98 con preferencia E5
        gasolineraDTOout.setPrecioGasolina98(selectAvailablePrice(gasolineraDTOin.getPrecioGasolina98E5(), gasolineraDTOin.getPrecioGasolina98E10()));

        return gasolineraDTOout;
    }

    /**
     * Selecciona el precio disponible para el tipo de gasolina, dando preferencia a E5 sobre E10.
     * Si ambos valores están vacíos, se devuelve "Sin Información".
     *
     * @param e5 Precio de la gasolina con mezcla E5.
     * @param e10 Precio de la gasolina con mezcla E10.
     * @return Precio seleccionado, con preferencia a E5 si está disponible.
     */
    private String selectAvailablePrice(String e5, String e10) {
        if (e5 != null && !e5.isEmpty()) {
            return "E5: " + e5;
        } else if (e10 != null && !e10.isEmpty()) {
            return "E10: " + e10;
        }
        return "Sin Información";
    }
}
