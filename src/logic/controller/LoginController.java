package logic.controller;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.bean.UserBean;
import logic.exceptions.DuplicatedRecordException;
import logic.exceptions.RecordNotFoundException;
import logic.model.Admin;
import logic.model.Professor;
import logic.model.Student;
import logic.model.User;
import logic.model.dao.AdminDAO;
import logic.model.dao.ProfessorDAO;
import logic.model.dao.RoleDAO;
import logic.model.dao.StudentDAO;
import logic.utilities.Role;
import logic.view.menu.element.NavigationBar;

public class LoginController {
	
	public UserBean login(UserBean userBean) throws SQLException, RecordNotFoundException {
		
		Role role = getUserRole(userBean);
		System.out.println(role);
		switch (role) {
		
		case STUDENT:
			loginAsStudent(userBean);
			break;
			
		case PROFESSOR:
			loginAsProfessor(userBean);
			break;
			
		case ADMIN:
			loginAsAdmin(userBean);
			break;
		}
		
		userBean.setRole(role);
		return userBean;
	}
	
	public Role getUserRole(UserBean userBean) throws SQLException, RecordNotFoundException {
		return RoleDAO.findType(userBean.getUsername());
	}
	
	public UserBean getUserByEmail(UserBean userBean) throws SQLException, RecordNotFoundException {
		User user = RoleDAO.getPasswordByEmail(userBean.getEmail());
		userBean.setPassword(user.getPassword());
		return userBean;
	}

	public void loginAsProfessor(UserBean userBean) throws SQLException, RecordNotFoundException {
		
		String username = userBean.getUsername();
		String password = userBean.getPassword();
		
		Professor professor = ProfessorDAO.findProfessor(username, password);
		userBean.setName(professor.getName());
		userBean.setSurname(professor.getSurname());
		userBean.setEmail(professor.getEmail());
		System.out.println("FINE: "+ professor.getUsername());
	}
	
	public void loginAsStudent(UserBean userBean) throws SQLException, RecordNotFoundException {
			
		String username = userBean.getUsername();
		String password = userBean.getPassword();
			
		Student student = StudentDAO.findStudent(username, password);
		userBean.setName(student.getName());
		userBean.setSurname(student.getSurname());
		userBean.setEmail(student.getEmail());
		System.out.println("FINE: "+ student.getUsername());
	}
	
	public void loginAsAdmin(UserBean userBean) throws SQLException, RecordNotFoundException {
		String username = userBean.getUsername();
		String password = userBean.getPassword();
			
		Admin admin = AdminDAO.findAdmin(username, password);
		userBean.setName(admin.getName());
		userBean.setSurname(admin.getSurname());
		userBean.setEmail(admin.getEmail());
		System.out.println("FINE: "+ admin.getUsername());
	}
	
	public void logout() {
		// Delete Session
		UserBean.setInstance(null);
		
		// Delete Navigation Bar
		//NavigationBar.setInstance(null);
	}
	
	public void signup(UserBean userBean) throws SQLException, DuplicatedRecordException {
		User user = new User(userBean.getUsername(), userBean.getPassword(), userBean.getName(), userBean.getSurname(), userBean.getEmail());
		StudentDAO.addStudent(user);
	}
	
	public void changePassword(UserBean userBean) throws SQLException {
		User user = new User(userBean.getUsername(), userBean.getPassword(), userBean.getName(), userBean.getSurname(), userBean.getEmail());
		
		try {
			
			// User is a student
			if (userBean.getRole() == Role.STUDENT) {
				StudentDAO.changePassword(user);
			}
			
			// User is a professor
			else if (userBean.getRole() == Role.PROFESSOR) {
				ProfessorDAO.changePassword(user);
			}
			
		} catch (RecordNotFoundException e) {
			Logger.getGlobal().log(Level.SEVERE, "An unexpected error occured");
		}
	}
}