package es.kuiko.api_comunidades.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Captura errores de deserialización cuando hay una propiedad JSON desconocida
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        Map<String, String> errorResponse = new HashMap<>();

        if (cause instanceof JsonMappingException) {
            JsonMappingException jsonEx = (JsonMappingException) cause;
            String invalidField = jsonEx.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .findFirst()
                    .orElse("campo desconocido");

            errorResponse.put("error", "Propiedad JSON inválida");
            errorResponse.put("detalles", "La propiedad '" + invalidField + "' no existe o es incorrecta.");
        } else {
            errorResponse.put("error", "Solicitud no válida");
            errorResponse.put("detalles", "Error en el formato JSON de la solicitud.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Manejo de errores de validación en @Valid (para DTOs)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // Manejo de excepciones de actualización ilegal
    @ExceptionHandler(IllegalUpdateException.class)
    public ResponseEntity<String> handleIllegalUpdateException(IllegalUpdateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Manejo de excepciones personalizadas de recurso no encontrado
    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCustomNotFoundException(CustomNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Recurso no encontrado");
        errorResponse.put("detalles", ex.getMessage());  // Incluye el mensaje de error personalizado en el JSON
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }



    // Manejo de argumentos inválidos generales
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Argumento inválido: " + ex.getMessage());
    }

    // Manejo de excepciones NullPointerException para recursos no encontrados
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNotFound(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recurso no encontrado: " + e.getMessage());
    }

    // Manejo de excepciones cuando no se encuentra un endpoint
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recurso no encontrado: " + ex.getRequestURL());
    }

    // Captura todas las demás excepciones
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado: " + e.getMessage());
    }

    // Manejo de excepciones de acceso de datos
    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<String> handleInvalidDataAccess(InvalidDataAccessApiUsageException ex) {
        return new ResponseEntity<>("Error de acceso a datos: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    
}