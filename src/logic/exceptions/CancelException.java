package logic.exceptions;

public class CancelException extends Exception {
	
	private static final long serialVersionUID = 3L;

	public CancelException(String message) {
		super(message);
	}
}
