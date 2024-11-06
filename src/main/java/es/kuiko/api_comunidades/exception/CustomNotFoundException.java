package es.kuiko.api_comunidades.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción personalizada para manejar casos en los que no se encuentra un recurso.
 * Esta excepción devuelve automáticamente un código de estado HTTP 404 (NOT FOUND) 
 * cuando se lanza en un controlador de Spring.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    /**
     * Constructor para crear una instancia de CustomNotFoundException con un mensaje específico.
     *
     * @param message Mensaje descriptivo de la excepción, que indica la razón por la cual no se encontró el recurso.
     */
    public CustomNotFoundException(String message) {
        super(message);
    }
}
