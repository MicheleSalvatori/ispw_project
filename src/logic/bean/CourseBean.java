package logic.bean;

import java.util.List;

import logic.model.Student;

public class CourseBean {

	private String name;
	private String abbrevation;
	private List<Student> studentOfCourse;
	
	public CourseBean() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Student> getStudentOfCourse() {
		return studentOfCourse;
	}

	public void addStudentToCourse(Student student) {
		this.studentOfCourse.add(student);
	}

	public String getAbbrevation() {
		return abbrevation;
	}

	public void setAbbrevation(String abbrevation) {
		this.abbrevation = abbrevation;
	}
}
