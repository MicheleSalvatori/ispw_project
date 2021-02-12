package logic.model;

import java.util.List;

public class Student extends User {
	
	private List<Course> studentCourses;
	
	public Student(String username, String password, String name, String surname, String email) {
		super(username, password, name, surname, email);
	}

	public Student() {

	}

	public List<Course> getStudentCourses() {
		return studentCourses;
	}

	public void setStudentCourses(List<Course> studentCourses) {
		this.studentCourses = studentCourses;
	}
}
