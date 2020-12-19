package logic.controller;

import java.sql.SQLException;

import logic.bean.UserBean;
import logic.exceptions.DuplicatedRecordException;
import logic.exceptions.RecordNotFoundException;
import logic.model.dao.StudentDAO;

public class SignupController {
	
	public void signup(UserBean usrBean) throws ClassNotFoundException, SQLException, RecordNotFoundException, DuplicatedRecordException {
		
		String username = usrBean.getUsername();
		String name = usrBean.getName();
		String surname = usrBean.getSurname();
		String email = usrBean.getEmail();
		String password = usrBean.getPassword();
		
		StudentDAO.addStudent(username, password, name, surname, email);
		
		// Automatic redirect
		System.out.println("Automatic redirect to login");
		LoginController loginController = new LoginController();
		loginController.loginAsStudent(usrBean);
	}
}
