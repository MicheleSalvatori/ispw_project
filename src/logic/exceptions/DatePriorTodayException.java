package logic.exceptions;

import logic.utilities.AlertController;

public class DatePriorTodayException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DatePriorTodayException(String message) {
		super(message);
		AlertController.infoAlert("Date entered must be after today.");
	}
}
