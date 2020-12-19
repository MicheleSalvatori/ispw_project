package logic.controller;

import java.sql.SQLException;

import logic.bean.UserBean;
import logic.exceptions.DuplicatedRecordException;
import logic.exceptions.RecordNotFoundException;
import logic.model.User;
import logic.model.dao.UserDAO;

public class SignupController {
	
	public void signup(UserBean usrBean) throws ClassNotFoundException, SQLException, RecordNotFoundException, DuplicatedRecordException {
		
		String username = usrBean.getUsername();
		String name = usrBean.getName();
		String surname = usrBean.getSurname();
		String email = usrBean.getEmail();
		String password = usrBean.getPassword();
		
		User user = UserDAO.addUser(username, name, surname, email, password);
		
		// Automatic redirect
		System.out.println("Automatic redirect to login");
		LoginController loginController = new LoginController();
		loginController.login(usrBean);
	}
}
