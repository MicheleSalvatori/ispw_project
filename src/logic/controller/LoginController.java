package logic.controller;

import java.sql.SQLException;

import logic.Session;
import logic.bean.UserBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Professor;
import logic.model.Student;
import logic.model.dao.ProfessorDAO;
import logic.model.dao.StudentDAO;

public class LoginController {

	public void loginAsProfessor(UserBean userBean) throws SQLException, RecordNotFoundException {
		
		String username = userBean.getUsername();
		String password = userBean.getPassword();
		
		Professor professor = ProfessorDAO.findProfessor(username, password);
		System.out.println("FINE: "+ professor.getUsername());
		
		//Gestione Sessione
		Session.getSession().setUserLogged(professor);
	}
	
	public void loginAsStudent(UserBean userBean) throws SQLException, RecordNotFoundException {
			
		String username = userBean.getUsername();
		String password = userBean.getPassword();
			
		Student student = StudentDAO.findStudent(username, password);
		System.out.println("FINE: "+ student.getUsername());
			
		//Gestione Sessione
		Session.getSession().setUserLogged(student);
	}
	
	public void logout() throws SQLException, ClassNotFoundException {
		
		// Delete Session
		Session.getSession().setUserLogged(null);
	}
}
