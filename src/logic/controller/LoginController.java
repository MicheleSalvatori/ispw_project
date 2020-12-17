package logic.controller;

import java.sql.SQLException;

import logic.Session;
import logic.bean.UserBean;
import logic.model.User;
import logic.model.UserDAO;

public class LoginController {

	
	public void login(UserBean usrBean) throws SQLException, ClassNotFoundException {
		String username = usrBean.getUsername();
		String password = usrBean.getPassword();
		User user = UserDAO.findUser(username, password);
		System.out.println("FINE: "+ user.getUsername());
		
		//Gestione Sessione
		Session.getSession().setUserLogged(user);
	}
	
	public void logout() throws SQLException, ClassNotFoundException {
		
		// Delete Session
		Session.getSession().setUserLogged(null);
	}
}
