

package logic.bean;

import java.util.List;

public class CourseBean {

	private String coubName;
	private String coubAbbreviation;
	private List<UserBean> coubStudents;
	
	private String coubYear;
	private String coubSemester;
	private String coubCredits;
	private String coubPrerequisites;
	private String coubGoal;
	private String coubReception;
	
	public CourseBean() {
		
	}
	
	public CourseBean(String course, String abbrv) {
		this.coubName = course;
		this.coubAbbreviation = abbrv;
	}

	public String getName() {
		return coubName;
	}

	public void setName(String name) {
		this.coubName = name;
	}
	
	public void setStudentsOfCourse(List<UserBean> students) {
		this.coubStudents = students;
	}

	public List<UserBean> getStudents() {
		return coubStudents;
	}

	public void addStudentToCourse(UserBean student) {
		this.coubStudents.add(student);
	}

	public String getAbbreviation() {
		return coubAbbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.coubAbbreviation = abbreviation;
	}
	
	public String getYear() {
		return coubYear;
	}

	public void setYear(String year) {
		this.coubYear = year;
	}

	public String getSemester() {
		return coubSemester;
	}

	public void setSemester(String semester) {
		this.coubSemester = semester;
	}

	public String getCredits() {
		return coubCredits;
	}

	public void setCredits(String credits) {
		this.coubCredits = credits;
	}

	public String getPrerequisites() {
		return coubPrerequisites;
	}

	public void setPrerequisites(String prerequisites) {
		this.coubPrerequisites = prerequisites;
	}

	public String getGoal() {
		return coubGoal;
	}

	public void setGoal(String goal) {
		this.coubGoal = goal;
	}

	public String getReception() {
		return coubReception;
	}

	public void setReception(String reception) {
		this.coubReception = reception;
	}

}
