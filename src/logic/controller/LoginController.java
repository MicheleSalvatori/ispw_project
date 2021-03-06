package logic.controller;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.bean.UserBean;
import logic.exceptions.DuplicatedRecordException;
import logic.exceptions.RecordNotFoundException;
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
			
		default:
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

		User professor = ProfessorDAO.findProfessor(username, password);
		userBean.setName(professor.getName());
		userBean.setSurname(professor.getSurname());
		userBean.setEmail(professor.getEmail());
	}

	public void loginAsStudent(UserBean userBean) throws SQLException, RecordNotFoundException {

		String username = userBean.getUsername();
		String password = userBean.getPassword();

		User student = StudentDAO.findStudent(username, password);
		userBean.setName(student.getName());
		userBean.setSurname(student.getSurname());
		userBean.setEmail(student.getEmail());
	}

	public void loginAsAdmin(UserBean userBean) throws SQLException, RecordNotFoundException {
		String username = userBean.getUsername();
		String password = userBean.getPassword();

		User admin = AdminDAO.findAdmin(username, password);
		userBean.setName(admin.getName());
		userBean.setSurname(admin.getSurname());
		userBean.setEmail(admin.getEmail());
	}

	public void logout() {
		// Delete Session
		UserBean.setInstance(null);

		// Delete Navigation Bar
		NavigationBar.resetInstance();
	}

	public void signup(UserBean userBean) throws SQLException, DuplicatedRecordException {
		User user = new User(userBean.getUsername(), userBean.getPassword(), userBean.getName(), userBean.getSurname(), userBean.getEmail());
		StudentDAO.addStudent(user);
	}
	
	public void addProfessor(UserBean userBean) throws SQLException, DuplicatedRecordException {
		User user = new User(userBean.getUsername(), userBean.getPassword(), userBean.getName(), userBean.getSurname(), userBean.getEmail());
		ProfessorDAO.addProfessor(user);
	}

	public void changePassword(UserBean userBean) throws SQLException {
		User user = new User(userBean.getUsername(), userBean.getPassword(), userBean.getName(), userBean.getSurname(),
				userBean.getEmail());

		try {

			// User is a student
			if (userBean.getRole() == Role.STUDENT) {
				StudentDAO.changePasswordStudent(user);
			}

			// User is a professor
			else if (userBean.getRole() == Role.PROFESSOR) {
				ProfessorDAO.changePasswordProfessor(user);
			}

		} catch (RecordNotFoundException e) {
			Logger.getGlobal().log(Level.SEVERE, "An unexpected error occured");
		}
	}
	
	public void deleteUser(UserBean userBean) {
		User user = new User(userBean.getUsername(), userBean.getPassword(), userBean.getName(), userBean.getSurname(), userBean.getEmail());
	
		try {

			// User is a student
			if (userBean.getRole() == Role.STUDENT) {
				StudentDAO.deleteStudent(user);
			}

			// User is a professor
			else if (userBean.getRole() == Role.PROFESSOR) {
				ProfessorDAO.deleteProfessor(user);
			}

		} catch (RecordNotFoundException e) {
			Logger.getGlobal().log(Level.SEVERE, "An unexpected error occured");
			
		} catch (SQLException e) {
			Logger.getGlobal().log(Level.SEVERE, "Connection failed");
		}
	}
}