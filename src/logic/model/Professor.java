package logic.model;

import java.util.List;

public class Professor extends User {
	
	private List<Course> professorCourses;
	
	public Professor(String username, String password, String name, String surname, String email) {
		super(username, password, name, surname, email);
	}

	public List<Course> getProfessorCourses() {
		return professorCourses;
	}

	public void setProfessorCourses(List<Course> professorCourses) {
		this.professorCourses = professorCourses;
	}
}
