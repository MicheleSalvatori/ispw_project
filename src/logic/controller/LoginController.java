package logic.controller;

import java.net.ConnectException;
import java.sql.SQLException;

import logic.Session;
import logic.bean.UserBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Admin;
import logic.model.Professor;
import logic.model.Student;
import logic.model.dao.AdminDAO;
import logic.model.dao.ProfessorDAO;
import logic.model.dao.RoleDAO;
import logic.model.dao.StudentDAO;
import logic.utilities.Role;
import logic.view.menu.element.NavigationBar;

public class LoginController {
	
	public Role getTypeUser(UserBean userBean) throws SQLException, RecordNotFoundException {
		String username = userBean.getUsername();
		Role type = RoleDAO.findType(username);
		return type;
	}

	public void loginAsProfessor(UserBean userBean) throws SQLException, RecordNotFoundException, ConnectException {
		
		String username = userBean.getUsername();
		String password = userBean.getPassword();
		
		Professor professor = ProfessorDAO.findProfessor(username, password);
		System.out.println("FINE: "+ professor.getUsername());
		
		//Gestione Sessione
		Session.getSession().setUserLogged(professor);
		Session.getSession().setType(Role.PROFESSOR);
	}
	
	public void loginAsStudent(UserBean userBean) throws SQLException, RecordNotFoundException {
			
		String username = userBean.getUsername();
		String password = userBean.getPassword();
			
		Student student = StudentDAO.findStudent(username, password);
		System.out.println("FINE: "+ student.getUsername());
			
		//Gestione Sessione
		Session.getSession().setUserLogged(student);
		Session.getSession().setType(Role.STUDENT);
	}
	
	public void loginAsAdmin(UserBean userBean) throws SQLException, RecordNotFoundException {
		String username = userBean.getUsername();
		String password = userBean.getPassword();
			
		Admin admin = AdminDAO.findAdmin(username, password);
		System.out.println("FINE: "+ admin.getUsername());
			
		//Gestione Sessione
		Session.getSession().setUserLogged(admin);
		Session.getSession().setType(Role.ADMIN);
		
	}
	public void logout() throws SQLException, ClassNotFoundException {
		
		// Delete Session
		Session.getSession().setUserLogged(null);
		
		// Delete Navigation Bar
		NavigationBar.setInstance(null);;
	}




}
