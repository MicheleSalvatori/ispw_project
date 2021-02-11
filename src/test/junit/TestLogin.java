package test.junit;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import logic.bean.UserBean;
import logic.controller.LoginController;
import logic.exceptions.RecordNotFoundException;

public class TestLogin {
	
	UserBean userBean;
	
	@Before
	public void setupTest() {	
		String username = "user";
		String pass = "user";
		userBean = new UserBean();
		userBean.setUsername(username);
		userBean.setPassword(pass);
	}

	@Test
	public void testLogin() {
		String message = "";
		boolean result = true;
		LoginController loginController = new LoginController();
		
		try {
			loginController.login(userBean);
			result = true;
			
		} catch (SQLException e) {
			message = "Connection failed!";
			result = false;
			
		} catch (RecordNotFoundException e) {
			message = "User not found: incorrect username or password.\nTry again or signup!";
			result = false;
		}
		
		assertEquals(message, true, result);
	}
}