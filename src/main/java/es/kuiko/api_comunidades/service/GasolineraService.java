package es.kuiko.api_comunidades.service;

import es.kuiko.api_comunidades.dto.gasolineras.GasolineraDTOin;
import es.kuiko.api_comunidades.dto.gasolineras.GasolineraDTOout;
import es.kuiko.api_comunidades.dto.gasolineras.GasolineraWrapperApiResponseOut;
import reactor.core.publisher.Mono;

import java.util.List;

public interface GasolineraService {

    /**
     * Obtiene una lista de gasolineras filtradas por provincia, extrayendo solo
     * los campos necesarios y manejando posibles valores vacíos.
     *
     * @param codigoProvincia Código de la provincia para la cual se requiere la lista de gasolineras.
     * @return Un Mono que emite una lista de objetos GasolineraDTOin correspondientes
     *         a las gasolineras de la provincia especificada.
     */
    //Mono<List<GasolineraDTOin>> getGasolinerasPorProvincia(String codigoProvincia);
    public Mono<GasolineraWrapperApiResponseOut> getGasolinerasPorProvincia(String codigoProvincia) ;
}


