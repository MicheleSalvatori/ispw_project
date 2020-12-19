package logic.controller;

import java.sql.SQLException;

import logic.Session;
import logic.bean.ProfessorBean;
import logic.bean.StudentBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Professor;
import logic.model.User;
import logic.model.dao.ProfessorDao;

public class LoginController {

	public void login(ProfessorBean professorBean) throws SQLException, ClassNotFoundException, RecordNotFoundException {
		
		String username = professorBean.getUsername();
		String password = professorBean.getPassword();
		
		Professor professor = ProfessorDao.findProfessor(username, password);
		System.out.println("FINE: "+ professor.getUsername());
		
		//Gestione Sessione
//		Session.getSession().setProfessorLogged(professor);
	}
	
	public void login(StudentBean student) throws SQLException, ClassNotFoundException, RecordNotFoundException {
			
			String username = student.getUsername();
			String password = student.getPassword();
			
//			Student user = ProfessorDao.findProfessor(username, password);
//			System.out.println("FINE: "+ user.getUsername());
			
			//Gestione Sessione
//			Session.getSession().setUserLogged(user);
		}
	public void logout() throws SQLException, ClassNotFoundException {
		
		// Delete Session
//		Session.getSession().setUserLogged(null);
	}
}
