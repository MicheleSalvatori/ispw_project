package logic.model;

import java.util.List;

public class Course {
	private String name;
	private String abbrevation;
	private List<Student> studentOfCourse;
//	private Professor professor;
	
	public Course(String name, String abbrevation) {
		this.name = name;
		this.abbrevation = abbrevation;
	}
	
	public Course() {
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
