package es.kuiko.api_comunidades.mapper;

import es.kuiko.api_comunidades.dto.gasolineras.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GasolineraMapper {

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

    private String selectAvailablePrice(String e5, String e10) {
        if (e5 != null && !e5.isEmpty()) {
            return "E5: " + e5;
        } else if (e10 != null && !e10.isEmpty()) {
            return "E10: " + e10;
        }
        return "Sin Información";
    }
}
