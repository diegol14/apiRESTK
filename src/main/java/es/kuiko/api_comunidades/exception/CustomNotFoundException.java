package es.kuiko.api_comunidades.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// La anotación @ResponseStatus permite que el controlador devuelva un código de estado específico al lanzar la excepción.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CustomNotFoundException(String message) {
        super(message);
    }
}