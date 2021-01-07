package logic.model;

import java.sql.Date;
import java.sql.Time;

public class Exam {

	private Date date;
	private Time time;
	private Course course;
	private Classroom classroom;
	private String note;
	
	public Exam(Date date, Time time, Course course, Classroom classroom, String note) {
		this.date = date;
		this.time = time;
		this.course = course;
		this.classroom = classroom;
		this.note = note;
	}

	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Time getTime() {
		return time;
	}
	
	public void setTime(Time time) {
		this.time = time;
	}
	
	public Course getCourse() {
		return course;
	}
	
	public void setCourse(Course course) {
		this.course = course;
	}
	
	public Classroom getClassroom() {
		return classroom;
	}
	
	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
}
