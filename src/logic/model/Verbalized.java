package logic.model;

import java.sql.Date;

public class Verbalized {

	private Student student;
	private Course course;
	private int grade;
	private Date date;
	
	public Verbalized(Student student, Course course, int grade, Date date) {
		this.student = student;
		this.course = course;
		this.grade = grade;
		this.date = date;
	}
	
	public Student getStudent() {
		return student;
	}
	
	public void setStudent(Student student) {
		this.student = student;
	}
	
	public Course getCourse() {
		return course;
	}
	
	public void setCourse(Course course) {
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