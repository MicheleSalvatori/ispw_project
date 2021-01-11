

package logic.bean;

import java.util.List;

public class CourseBean {

	private String name;
	private String abbrevation;
	private List<StudentBean> students;
	
	private String year;
	private String semester;
	private String credits;
	private String prerequisites;
	private String goal;
	private String reception;
	
	public CourseBean() {
		
	}
	
	public CourseBean(String course, String abbrv) {
		this.name = course;
		this.abbrevation = abbrv;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setStudentsOfCourse(List<StudentBean> students) {
		this.students = students;
	}

	public List<StudentBean> getStudents() {
		return students;
	}

	public void addStudentToCourse(StudentBean student) {
		this.students.add(student);
	}

	public String getAbbrevation() {
		return abbrevation;
	}

	public void setAbbrevation(String abbrevation) {
		this.abbrevation = abbrevation;
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
