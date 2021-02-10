package logic.bean;

import java.sql.Date;

public class AssignmentBean {
	
	private int asbID;
	private String asbCourse;
	private String asbTitle;
	private Date asbDate;
	private String asbText;
	
	public int getId() {
		return asbID;
	}
	
	public void setId(int id) {
		this.asbID = id;
	}
	
	public String getCourse() {
		return asbCourse;
	}
	
	public void setCourse(String course) {
		this.asbCourse = course;
	}
	
	public String getTitle() {
		return asbTitle;
	}
	
	public void setTitle(String title) {
		this.asbTitle = title;
	}
	
	public Date getDate() {
		return asbDate;
	}
	
	public void setDate(Date date) {
		this.asbDate = date;
	}
	
	public String getText() {
		return asbText;
	}
	
	public void setText(String text) {
		this.asbText = text;
	}
}
