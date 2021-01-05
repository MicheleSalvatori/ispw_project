package logic.bean;

import logic.model.Course;
import logic.model.Student;

public class RequestBean {

	private Student student;
	private Course course;
	
	public RequestBean() {
		
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
}
