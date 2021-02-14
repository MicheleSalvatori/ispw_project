package test.junit;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import logic.bean.CourseBean;
import logic.bean.RequestBean;
import logic.bean.UserBean;
import logic.controller.AcceptRequestController;
import logic.controller.JoinCourseController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.Role;

/*
 * Luca Santopadre 0257118
 */

public class TestNotifications {
	
	UserBean profUserBean = new UserBean();
	UserBean studentUserBean = new UserBean();
	CourseBean courseBean = new CourseBean();
	RequestBean requestBean = new RequestBean();
	AcceptRequestController acceptRequestController = new AcceptRequestController();
	JoinCourseController joinCourseController = new JoinCourseController();
	int reqCountBefore = -1;
	int reqCountAfter = -1;

	@Before
	public void prepare() {
		
		profUserBean.setUsername("lopresti");
		profUserBean.setRole(Role.PROFESSOR);
		
		studentUserBean.setUsername("luca");
		
		courseBean.setAbbreviation("CE");
		
		requestBean.setStudent(studentUserBean);
		requestBean.setCourse(courseBean.getAbbreviation());
	}

	@Test
	public void test() {	
		String connFail = "Connection failed";
		String message = "";
		
		// prendo numero notifiche del prof prima
		try {
			reqCountBefore = acceptRequestController.getRequests(profUserBean).size();
			
		} catch (RecordNotFoundException e) {
			reqCountBefore=0;
			message = "Record not found";
			
		} catch (SQLException e) {
			message = connFail;
		}
		
		// eseguo richiesta con studente per il prof
		
		try {
			joinCourseController.sendRequest(requestBean);
			
		} catch (SQLException e) {
			message = connFail;
		}
		
		// prendo numero notifiche del prof dopo
		try {
			reqCountAfter = acceptRequestController.getRequests(profUserBean).size();
			
		} catch (RecordNotFoundException e) {
			reqCountAfter=0;
			message = "Record not found";
			
		} catch (SQLException e) {
			message = connFail;
		}
		
		// assert prima==dopo-1
		assertEquals(message, reqCountBefore, reqCountAfter - 1l);
	}
	
	@After
	public void cleanDB() throws SQLException {
		joinCourseController.deleteRequest(requestBean);
	}
}