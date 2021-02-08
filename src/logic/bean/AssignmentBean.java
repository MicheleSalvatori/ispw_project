package logic.bean;

import java.sql.Date;

public class AssignmentBean {
	
	private int id;
	private CourseBean course;
	private String title;
	private Date date;
	private String text;
	
	public AssignmentBean() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public CourseBean getCourse() {
		return course;
	}
	
	public void setCourse(CourseBean course) {
		this.course = course;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
}
