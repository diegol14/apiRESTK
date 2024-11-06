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

/**
 * Clase de manejo global de excepciones en la aplicación. Captura varias excepciones
 * comunes y personalizadas, y devuelve respuestas HTTP apropiadas con mensajes claros.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de formato JSON no legible, lanzadas cuando se reciben propiedades JSON desconocidas.
     *
     * @param ex Excepción de tipo HttpMessageNotReadableException.
     * @return Respuesta con código de estado 400 (BAD REQUEST) y detalles del error en JSON.
     */
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

    /**
     * Maneja excepciones de validación cuando se usa @Valid en los DTOs.
     *
     * @param ex Excepción de tipo MethodArgumentNotValidException.
     * @return Respuesta con código de estado 400 (BAD REQUEST) y detalles de los errores de validación en JSON.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Maneja excepciones de actualización ilegal en recursos.
     *
     * @param ex Excepción personalizada de tipo IllegalUpdateException.
     * @return Respuesta con código de estado 400 (BAD REQUEST) y mensaje del error.
     */
    @ExceptionHandler(IllegalUpdateException.class)
    public ResponseEntity<String> handleIllegalUpdateException(IllegalUpdateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Maneja excepciones personalizadas para recursos no encontrados.
     *
     * @param ex Excepción personalizada de tipo CustomNotFoundException.
     * @return Respuesta con código de estado 404 (NOT FOUND) y detalles en JSON.
     */
    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCustomNotFoundException(CustomNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Recurso no encontrado");
        errorResponse.put("detalles", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Maneja excepciones de argumentos no válidos.
     *
     * @param ex Excepción de tipo IllegalArgumentException.
     * @return Respuesta con código de estado 400 (BAD REQUEST) y mensaje del error.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Maneja excepciones de NullPointerException, típicamente cuando un recurso no se encuentra.
     *
     * @param e Excepción de tipo NullPointerException.
     * @return Respuesta con código de estado 404 (NOT FOUND) y mensaje descriptivo.
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNotFound(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recurso no encontrado: " + e.getMessage());
    }

    /**
     * Maneja excepciones cuando un endpoint solicitado no existe.
     *
     * @param ex Excepción de tipo NoHandlerFoundException.
     * @return Respuesta con código de estado 404 (NOT FOUND) y mensaje con el URL no encontrado.
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recurso no encontrado: " + ex.getRequestURL());
    }

    /**
     * Captura todas las demás excepciones generales no manejadas específicamente.
     *
     * @param e Excepción de tipo Exception.
     * @return Respuesta con código de estado 500 (INTERNAL SERVER ERROR) y mensaje del error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado: " + e.getMessage());
    }

    /**
     * Maneja excepciones relacionadas con errores en el acceso a datos.
     *
     * @param ex Excepción de tipo InvalidDataAccessApiUsageException.
     * @return Respuesta con código de estado 400 (BAD REQUEST) y mensaje descriptivo del error de acceso a datos.
     */
    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<String> handleInvalidDataAccess(InvalidDataAccessApiUsageException ex) {
        return new ResponseEntity<>("Error de acceso a datos: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
