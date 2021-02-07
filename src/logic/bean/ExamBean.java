package logic.bean;

import java.sql.Date;
import java.sql.Time;

public class ExamBean {

	private Date date;
	private Time time;
	private CourseBean course;
	private ClassroomBean classroom;
	private String note;
	
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
	
	public CourseBean getCourse() {
		return course;
	}
	
	public void setCourse(CourseBean course) {
		this.course = course;
	}
	
	public ClassroomBean getClassroom() {
		return classroom;
	}
	
	public void setClassroom(ClassroomBean classroom) {
		this.classroom = classroom;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
}
