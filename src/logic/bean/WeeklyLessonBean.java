package logic.bean;

import java.sql.Time;

public class WeeklyLessonBean {

	private String day;
	private Time time;
	private ClassroomBean classroom;
	private CourseBean course;
	
	public String getDay() {
		return day;
	}
	
	public void setDay(String day) {
		this.day = day;
	}
	
	public Time getTime() {
		return time;
	}
	
	public void setTime(Time time) {
		this.time = time;
	}
	
	public ClassroomBean getClassroom() {
		return classroom;
	}
	
	public void setClassroom(ClassroomBean classroom) {
		this.classroom = classroom;
	}
	
	public CourseBean getCourse() {
		return course;
	}
	
	public void setCourse(CourseBean course) {
		this.course = course;
	}
}
