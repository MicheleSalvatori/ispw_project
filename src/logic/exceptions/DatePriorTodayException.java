package logic.exceptions;

public class DatePriorTodayException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DatePriorTodayException(String message) {
		super(message);
	}
}
