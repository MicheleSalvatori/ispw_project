package logic.exceptions;

public class DuplicatedRecordException extends Exception {

	private static final long serialVersionUID = 1L;

	public DuplicatedRecordException(String message) {
		super(message);
	}

}
