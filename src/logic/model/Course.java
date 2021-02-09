package logic.model;

import java.util.List;

public class Course {
	
	private String name;
	private String abbreviation;
	private List<Student> studentOfCourse;
	
	private String year;
	private String semester;
	private String credits;
	private String prerequisites;
	private String goal;
	private String reception;
	
	public Course(String name, String abbrevation, String year, String semster, String credits, String prerequisites, String goal, String reception) {
		this.name = name;
		this.abbreviation = abbrevation;
		this.year = year;
		this.semester = semster;
		this.credits = credits;
		this.prerequisites = prerequisites;
		this.goal = goal;
		this.reception = reception;
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

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbrevation) {
		this.abbreviation = abbrevation;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public String getPrerequisites() {
		return prerequisites;
	}

	public void setPrerequisites(String prerequisites) {
		this.prerequisites = prerequisites;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getReception() {
		return reception;
	}

	public void setReception(String reception) {
		this.reception = reception;
	}

}
