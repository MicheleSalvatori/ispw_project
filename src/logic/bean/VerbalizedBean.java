package logic.bean;

import java.sql.Date;

import logic.model.Course;
import logic.model.Student;

public class VerbalizedBean {
	
	private Student student;
	private Course course;
	private int grade;
	private Date date;
	
	public VerbalizedBean() {
		
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