package logic.model;

public class Request {

	private Student student;
	private Course course;
	
	public Request(Student student, Course course) {
		this.student = student;
		this.course = course;
	}
	
	public Request() {
		
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
