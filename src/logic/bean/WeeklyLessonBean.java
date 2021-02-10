package logic.bean;

import java.sql.Time;

public class WeeklyLessonBean {

	private String wbDay;
	private Time wbTime;
	private ClassroomBean wbClassroom;
	private CourseBean wbCourse;
	
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
	
	public ClassroomBean getClassroom() {
		return wbClassroom;
	}
	
	public void setClassroom(ClassroomBean classroom) {
		this.wbClassroom = classroom;
	}
	
	public CourseBean getCourse() {
		return wbCourse;
	}
	
	public void setCourse(CourseBean course) {
		this.wbCourse = course;
	}
}
