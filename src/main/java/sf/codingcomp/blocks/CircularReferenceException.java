package sf.codingcomp.blocks;


/**
 * 
 * Throw this if building blocks have been constructed in a circle.
 *
 */
public class CircularReferenceException extends RuntimeException {

	private static final long serialVersionUID = 6548403226715369580L;

	public CircularReferenceException() { }

	public CircularReferenceException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CircularReferenceException(String message, Throwable cause) {
		super(message, cause);
	}

	public CircularReferenceException(String message) {
		super(message);
	}

	public CircularReferenceException(Throwable cause) {
		super(cause);
	}

}
