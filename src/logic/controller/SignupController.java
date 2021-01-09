package logic.controller;

import java.sql.SQLException;

import logic.Session;
import logic.bean.UserBean;
import logic.exceptions.DuplicatedRecordException;
import logic.model.User;
import logic.model.dao.ProfessorDAO;
import logic.model.dao.StudentDAO;
import logic.utilities.Role;

public class SignupController {
	
	public void signup(UserBean userBean) throws SQLException, DuplicatedRecordException {
		User user = new User(userBean.getUsername(), userBean.getPassword(), userBean.getName(), userBean.getSurname(), userBean.getEmail());
		StudentDAO.addStudent(user);
	}
	
	public void changePassword(UserBean userBean) throws SQLException {
		User user = new User(userBean.getUsername(), userBean.getPassword(), userBean.getName(), userBean.getSurname(), userBean.getEmail());
		
		// User is a student
		if (Session.getSession().getType() == Role.STUDENT) {
			StudentDAO.changePassword(user);
		}
		
		// User is a professor
		else if (Session.getSession().getType() == Role.PROFESSOR) {
			ProfessorDAO.changePassword(user);
		}
	}
}
