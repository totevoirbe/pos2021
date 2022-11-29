package be.panidel.exception;

public class IllegalTypeException extends Exception {

	private static final long serialVersionUID = 1L;

	public IllegalTypeException() {
		super();
	}

	public IllegalTypeException(String message) {
		super(message);
	}

	public IllegalTypeException(Throwable cause) {
		super(cause);
	}

	public IllegalTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalTypeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
