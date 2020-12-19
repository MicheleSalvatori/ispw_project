package logic;

import logic.model.Professor;
import logic.model.Student;

public class Session {
	
	private static Session instance = null;
	private Professor professorLogged;
	private Student studentLogged;

	private Session() {}

	public static Session getSession() {
		if (Session.instance == null) {
			Session.instance = new Session();
		}
		return instance;
	}
	
	public Professor getProfessorLogged() {
		return professorLogged;
	}
	
	public void setProfessorLogged(Professor professorLogged) {
		this.professorLogged = professorLogged;
	}

	public Student getStudentLogged() {
		return studentLogged;
	}

	public void setStudentLogged(Student studentLogged) {
		this.studentLogged = studentLogged;
	}

}
