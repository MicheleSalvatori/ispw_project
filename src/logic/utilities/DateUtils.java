package logic.utilities;

import java.sql.Date;
import java.time.LocalDate;

import logic.exceptions.DatePriorTodayException;

public class DateUtils {
	
	private DateUtils() {
		
	}
	
	public static void checkPriorDate(java.util.Date dateEntered) throws DatePriorTodayException {
		LocalDate localDateNow = LocalDate.now();
	
		if (dateEntered.before(Date.valueOf(localDateNow))) {
			throw new DatePriorTodayException("Date entered must be after today");
		}
	}

}
