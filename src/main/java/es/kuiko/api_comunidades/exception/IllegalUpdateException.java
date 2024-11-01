package es.kuiko.api_comunidades.exception;

public class IllegalUpdateException  extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -452977695489952916L;

	public IllegalUpdateException(String message) {
        super(message);
    }

}