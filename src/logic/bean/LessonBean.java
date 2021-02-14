package logic.bean;

import java.sql.Date;
import java.sql.Time;

public class LessonBean {

	private Date lbDate;
	private Time lbTime;
	private String lbCourse;
	private ClassroomBean lbClassroom;
	private UserBean lbProfessor;
	private String lbTopic;
	
	public Date getDate() {
		return lbDate;
	}
	
	public void setDate(Date date) {
		this.lbDate = date;
	}
	
	public Time getTime() {
		return lbTime;
	}
	
	public void setTime(Time time) {
		this.lbTime = time;
	}
	
	public String getCourse() {
		return lbCourse;
	}
	
	public void setCourse(String course) {
		this.lbCourse = course;
	}
	
	public ClassroomBean getClassroom() {
		return lbClassroom;
	}
	
	public void setClassroom(ClassroomBean classroom) {
		this.lbClassroom = classroom;
	}

	public String getTopic() {
		return lbTopic;
	}

	public void setTopic(String topic) {
		this.lbTopic = topic;
	}

	public UserBean getProfessor() {
		return lbProfessor;
	}

	public void setProfessor(UserBean professor) {
		this.lbProfessor = professor;
	}
}
