package logic.model;

import java.sql.Date;
import java.sql.Time;

public class Exam {

	private Date eDate;
	private Time eTime;
	private Course eCourse;
	private Classroom exClassroom;
	private String eNote;
	
	public Exam(Date date, Time time, Course course, Classroom classroom, String note) {
		this.eDate = date;
		this.eTime = time;
		this.eCourse = course;
		this.exClassroom = classroom;
		this.eNote = note;
	}

	public Date getDate() {
		return eDate;
	}
	
	public void setDate(Date date) {
		this.eDate = date;
	}
	
	public Time getTime() {
		return eTime;
	}
	
	public void setTime(Time time) {
		this.eTime = time;
	}
	
	public Course getCourse() {
		return eCourse;
	}
	
	public void setCourse(Course course) {
		this.eCourse = course;
	}
	
	public Classroom getClassroom() {
		return exClassroom;
	}
	
	public void setClassroom(Classroom classroom) {
		this.exClassroom = classroom;
	}
	
	public String getNote() {
		return eNote;
	}
	
	public void setNote(String note) {
		this.eNote = note;
	}
}
