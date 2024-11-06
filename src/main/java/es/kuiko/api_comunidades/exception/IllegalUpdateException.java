package es.kuiko.api_comunidades.exception;

/**
 * Excepción personalizada para manejar intentos de actualización ilegal en recursos.
 * Esta excepción se lanza cuando se intenta actualizar un recurso de una manera que no es permitida
 * por las reglas de negocio o las políticas de la aplicación.
 */
public class IllegalUpdateException extends RuntimeException {
    
    private static final long serialVersionUID = -452977695489952916L;

    /**
     * Constructor para crear una instancia de IllegalUpdateException con un mensaje específico.
     *
     * @param message Mensaje que describe la razón por la cual la actualización es ilegal.
     */
    public IllegalUpdateException(String message) {
        super(message);
    }
}
