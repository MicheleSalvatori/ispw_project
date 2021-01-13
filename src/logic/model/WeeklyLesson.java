package logic.model;

import java.sql.Time;

public class WeeklyLesson {

	private String day;
	private Time time;
	private Classroom classroom;
	private Course course;
	
	public WeeklyLesson(String day, Time time, Classroom classroom, Course course) {
		this.day = day;
		this.time = time;
		this.classroom = classroom;
		this.course = course;
	}

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

	public Classroom getClassroom() {
		return classroom;
	}

	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
