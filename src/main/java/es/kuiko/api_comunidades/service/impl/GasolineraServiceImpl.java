package es.kuiko.api_comunidades.service.impl;

import es.kuiko.api_comunidades.dto.gasolineras.*;
import es.kuiko.api_comunidades.exception.CustomNotFoundException;
import es.kuiko.api_comunidades.mapper.GasolineraMapper;
import es.kuiko.api_comunidades.service.GasolineraService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GasolineraServiceImpl implements GasolineraService {

    private final WebClient webClient;
    private final GasolineraMapper gasolineraMapper;

    public GasolineraServiceImpl(WebClient webClient, GasolineraMapper gasolineraMapper) {
        this.webClient = webClient;
        this.gasolineraMapper = gasolineraMapper;
    }

    @Override
    public Mono<GasolineraWrapperApiResponseOut> getGasolinerasPorProvincia(String codigoProvincia) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(codigoProvincia).build())  // Construye la URI con el código de provincia
                .exchangeToMono(response -> {
                    if (response.statusCode().isError()) {
                        // Si el estado de la respuesta indica un error, procesa esta respuesta como un mensaje de error.
                        return response.bodyToMono(String.class)
                                .map(errorMessage -> createErrorResponse(errorMessage));  // Crea una respuesta de error personalizada a partir del mensaje de error.
                    } else {
                        // Si el estado de la respuesta es exitoso, procesa la respuesta normalmente.
                        return response.bodyToMono(GasolineraWrapperApiResponseIn.class)
                                .map(this::mapToResponseOut);  // Mapea la respuesta exitosa a la clase de salida.
                    }
                });
    }

    private GasolineraWrapperApiResponseOut createErrorResponse(String errorMessage) {
        // Crea y retorna una respuesta de error encapsulando el mensaje de error.
        GasolineraWrapperApiResponseOut response = new GasolineraWrapperApiResponseOut();
        response.setResultadoConsulta(errorMessage);
        return response;
    }

    private GasolineraWrapperApiResponseOut mapToResponseOut(GasolineraWrapperApiResponseIn response) {
        // Prepara la respuesta de salida mapeando desde la respuesta de la API de terceros.
        GasolineraWrapperApiResponseOut responseOut = new GasolineraWrapperApiResponseOut();
        responseOut.setFecha(response.getFecha());  // Asigna la fecha de la respuesta original.
        responseOut.setNota(response.getNota());  // Asigna la nota de la respuesta original.
        if (response.getListaEESSPrecio() == null || response.getListaEESSPrecio().isEmpty()) {
            // Si no hay datos, configura el mensaje de resultado de consulta.
            responseOut.setResultadoConsulta("No se encontraron datos para el código de provincia proporcionado.");
            responseOut.setListaEESSPrecio(Collections.emptyList());  // Retorna una lista vacía.
        } else {
            // Si hay datos, los transforma y mapea a la estructura de salida deseada.
            responseOut.setListaEESSPrecio(response.getListaEESSPrecio().stream()
                    .map(gasolineraMapper::mapToGasolineraDTOout)
                    .collect(Collectors.toList()));
            responseOut.setResultadoConsulta("OK");  // Establece el resultado de la consulta como "OK".
        }
        return responseOut;
    }


	/*
	 * private GasolineraWrapperApiResponseOut createErrorResponse(String message) {
	 * GasolineraWrapperApiResponseOut response = new
	 * GasolineraWrapperApiResponseOut(); response.setResultadoConsulta(message);
	 * return response; }
	 */




}
