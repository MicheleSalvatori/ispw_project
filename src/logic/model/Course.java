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

	public Course(List<String> info) {
		this.name = info.get(0);
		this.abbreviation = info.get(0);
		this.year = info.get(0);
		this.semester = info.get(0);
		this.credits = info.get(0);
		this.prerequisites = info.get(0);
		this.goal = info.get(0);
		this.reception = info.get(0);
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
