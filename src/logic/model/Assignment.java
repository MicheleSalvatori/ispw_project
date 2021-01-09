package logic.model;

import java.sql.Date;

public class Assignment {

	private int id;
	private Course course;
	private String title;
	private Date date;
	private String text;
	
	public Assignment(int id, Course course, String title, Date date, String text) {
		this.id = id;
		this.course = course;
		this.title = title;
		this.date = date;
		this.text = text;
	}
	
	public Assignment(Course course, String title, Date date, String text) {
		this.course = course;
		this.title = title;
		this.date = date;
		this.text = text;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Course getCourse() {
		return course;
	}
	
	public void setCourse(Course course) {
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
