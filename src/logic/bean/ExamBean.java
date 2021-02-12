package logic.bean;

import java.sql.Date;
import java.sql.Time;

public class ExamBean {

	private Date ebDate;
	private Time ebTime;
	private String ebCourse;
	private String ebClassroom;
	private String ebNote;
	
	public Date getDate() {
		return ebDate;
	}
	
	public void setDate(Date date) {
		this.ebDate = date;
	}
	
	public Time getTime() {
		return ebTime;
	}
	
	public void setTime(Time time) {
		this.ebTime = time;
	}
	
	public String getCourse() {
		return ebCourse;
	}
	
	public void setCourse(String course) {
		this.ebCourse = course;
	}
	
	public String getClassroom() {
		return ebClassroom;
	}
	
	public void setClassroom(String classroom) {
		this.ebClassroom = classroom;
	}
	
	public String getNote() {
		return ebNote;
	}
	
	public void setNote(String note) {
		this.ebNote = note;
	}
}
