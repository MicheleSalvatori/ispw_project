package test.junit;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import logic.bean.UserBean;
import logic.controller.LoginController;
import logic.exceptions.DuplicatedRecordException;
import logic.utilities.Role;

public class TestSignup {
	
	UserBean userBean;
	LoginController controller;
	boolean result;
	
	@Before
	public void setupTest() {
		String username = "newTest";
		String password = "newTest";
		String name = "name";
		String surname = "surname";
		String email = "test@test.com";
		
		userBean = new UserBean();
		userBean.setUsername(username);
		userBean.setPassword(password);
		userBean.setSurname(surname);
		userBean.setName(name);
		userBean.setEmail(email);
		userBean.setRole(Role.STUDENT);
	}

	@Test
	public void testSignup() {
		String message = "";
		controller = new LoginController();
		
		try {
			controller.signup(userBean);
			result = true;
			
		} catch (SQLException e) {
			message = "Connection failed!";
			result = false;
			
		} catch (DuplicatedRecordException e) {
			message = e.getMessage();
			result = false;
		}
		
		assertEquals(message, true, result);
	}
	
	@After
	public void cleanDB() {
		if (result) {
			controller.deleteUser(userBean);
		}
	}
}
