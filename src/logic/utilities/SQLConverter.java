package logic.utilities;

import java.sql.Date;
import java.sql.Time;

public class SQLConverter {

	private SQLConverter() {
		
	}
	
	public static String date(Date date) {
		String d = date.toString();
		String year = d.substring(0, 4);
		String month = d.substring(5, 7);
		String day = d.substring(8);
		
		return day + "/" + month + "/" + year;
	}
	
	public static String time(Time time) {
		String t = time.toString();
		
		return t.substring(0, 5);
	}
}
