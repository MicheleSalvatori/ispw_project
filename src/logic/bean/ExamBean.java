package logic.bean;

import java.sql.Date;
import java.sql.Time;

public class ExamBean {

	private Date ebDate;
	private Time ebTime;
	private CourseBean ebCourse;
	private ClassroomBean ebClassroom;
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
	
	public CourseBean getCourse() {
		return ebCourse;
	}
	
	public void setCourse(CourseBean course) {
		this.ebCourse = course;
	}
	
	public ClassroomBean getClassroom() {
		return ebClassroom;
	}
	
	public void setClassroom(ClassroomBean classroom) {
		this.ebClassroom = classroom;
	}
	
	public String getNote() {
		return ebNote;
	}
	
	public void setNote(String note) {
		this.ebNote = note;
	}
}
