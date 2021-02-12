package logic.bean;

import java.sql.Date;

public class VerbalizedBean {
	
	private String vbStudent;
	private CourseBean vbCourse;
	private int vbGrade;
	private Date vbDate;
	
	public String getStudent() {
		return vbStudent;
	}
	
	public void setStudent(String student) {
		this.vbStudent = student;
	}
	
	public CourseBean getCourse() {
		return vbCourse;
	}
	
	public void setCourse(CourseBean course) {
		this.vbCourse = course;
	}
	
	public int getGrade() {
		return vbGrade;
	}
	
	public void setGrade(int grade) {
		this.vbGrade = grade;
	}

	public Date getDate() {
		return vbDate;
	}

	public void setDate(Date date) {
		this.vbDate = date;
	}
}