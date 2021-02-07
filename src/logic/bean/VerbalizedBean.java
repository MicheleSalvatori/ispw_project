package logic.bean;

import java.sql.Date;

public class VerbalizedBean {
	
	private StudentBean student;
	private CourseBean course;
	private int grade;
	private Date date;
	
	public StudentBean getStudent() {
		return student;
	}
	
	public void setStudent(StudentBean student) {
		this.student = student;
	}
	
	public CourseBean getCourse() {
		return course;
	}
	
	public void setCourse(CourseBean course) {
		this.course = course;
	}
	
	public int getGrade() {
		return grade;
	}
	
	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}