package pos.xml.model;

public class ModelValidatorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModelValidatorException() {
		super();
	}

	public ModelValidatorException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ModelValidatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ModelValidatorException(String message) {
		super(message);
	}

	public ModelValidatorException(Throwable cause) {
		super(cause);
	}

}
