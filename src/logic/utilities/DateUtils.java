package logic.utilities;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import logic.exceptions.DatePriorTodayException;

public class DateUtils {
	
	public static void checkPriorDate(java.util.Date dateEntered) throws DatePriorTodayException {
		try {
			LocalDate localDateNow = LocalDate.now();
			java.util.Date d;
			
			if (dateEntered.before(Date.valueOf(localDateNow))) {
				d = null;
			} else {
				d = dateEntered;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    d = (java.util.Date) sdf.parse(d.toString());
		    
		} catch (NullPointerException | ParseException  e) {
			throw new DatePriorTodayException("Date entered must be after today");
		}
	}

}
