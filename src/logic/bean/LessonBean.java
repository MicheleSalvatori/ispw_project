package logic.bean;

import java.sql.Date;
import java.sql.Time;

public class LessonBean {

	private Date date;
	private Time time;
	private CourseBean course;
	private ClassroomBean classroom;
	private ProfessorBean professor;
	private String topic;
	
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

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public ProfessorBean getProfessor() {
		return professor;
	}

	public void setProfessor(ProfessorBean professor) {
		this.professor = professor;
	}
}
