package logic.controller;

import java.sql.SQLException;

import logic.Session;
import logic.bean.UserBean;
import logic.exceptions.DuplicatedRecordException;
import logic.model.dao.ProfessorDAO;
import logic.model.dao.StudentDAO;
import logic.utilities.Role;

public class SignupController {
	
	public void signup(UserBean usrBean) throws SQLException, DuplicatedRecordException {
		
		String username = usrBean.getUsername();
		String name = usrBean.getName();
		String surname = usrBean.getSurname();
		String email = usrBean.getEmail();
		String password = usrBean.getPassword();
		
		StudentDAO.addStudent(username, password, name, surname, email);
	}
	
	public void changePassword(UserBean userBean) throws SQLException {
		
		String username = userBean.getUsername();
		String password = userBean.getPassword();
		System.out.println(password);
		
		if (Session.getSession().getType() == Role.STUDENT) {
			StudentDAO.changePassword(username, password);
		}
		else if (Session.getSession().getType() == Role.PROFESSOR) {
			ProfessorDAO.changePassword(username, password);
		}
	}
}
