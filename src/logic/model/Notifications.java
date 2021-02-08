package logic.model;

import java.sql.Date;

public class Notifications {
	
	private String title;
	private String description;
	private Date date;
	
	public Notifications() {
	}
	
	
	public String getTitle() {
		return title;
	}




	public void setTitle(String title) {
		this.title = title;
	}




	public String getDescription() {
		return description;
	}




	public void setDescription(String description) {
		this.description = description;
	}




	public Date getDate() {
		return date;
	}




	public void setDate(Date date) {
		this.date = date;
	}





}
