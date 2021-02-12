package logic.bean;

import java.sql.Time;

public class WeeklyLessonBean {

	private String wbDay;
	private Time wbTime;
	private String wbClassroom;
	private String wbCourse;
	
	public String getDay() {
		return wbDay;
	}
	
	public void setDay(String day) {
		this.wbDay = day;
	}
	
	public Time getTime() {
		return wbTime;
	}
	
	public void setTime(Time time) {
		this.wbTime = time;
	}
	
	public String getClassroom() {
		return wbClassroom;
	}
	
	public void setClassroom(String classroom) {
		this.wbClassroom = classroom;
	}
	
	public String getCourse() {
		return wbCourse;
	}
	
	public void setCourse(String course) {
		this.wbCourse = course;
	}
}
