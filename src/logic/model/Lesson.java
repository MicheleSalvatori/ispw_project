package logic.model;

import java.sql.Date;
import java.sql.Time;

public class Lesson {

	private Date date;
	private Time time;
	private Course course;
	private Classroom classroom;
	private String topic;
	
	public Lesson(Date date, Time time, Course course, Classroom classroom, String topic) {
		this.date = date;
		this.time = time;
		this.course = course;
		this.classroom = classroom;
		this.topic = topic;
	}
	
	public Lesson(Date date, Time time, Course course, Classroom classroom) {
		this.date = date;
		this.time = time;
		this.course = course;
		this.classroom = classroom;
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

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
}
