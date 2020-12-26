package logic.exceptions;

public class RecordNotFoundException extends Exception {
	
	private static final long serialVersionUID = 2L;

	public RecordNotFoundException(String message) {
		super(message);
	}
}
