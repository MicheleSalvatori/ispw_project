package test.junit;

import static org.junit.Assert.*;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import logic.bean.CourseBean;
import logic.bean.RequestBean;
import logic.bean.StudentBean;
import logic.bean.UserBean;
import logic.controller.AcceptRequestController;
import logic.controller.JoinCourseController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.Role;

public class TestNotifications {
	UserBean profUserBean = new UserBean();
	StudentBean studBean = new StudentBean();
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
		
		studBean.setUsername("luca");
		
		courseBean.setAbbreviation("CE");
		
		requestBean.setStudent(studBean);
		requestBean.setCourse(courseBean);
	}

	@Test
	public void test() {		
		
		// prendo numero notifiche del prof prima
		try {
			reqCountBefore = acceptRequestController.getRequests(profUserBean).size();
		} catch (RecordNotFoundException e) {
			reqCountBefore = 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// eseguo richiesta con studente per il prof
		
		try {
			joinCourseController.sendRequest(requestBean);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// prendo numero notifiche del prof dopo
		try {
			reqCountAfter = acceptRequestController.getRequests(profUserBean).size();
		} catch (RecordNotFoundException  | SQLException e) {
			e.printStackTrace();
		}
		
		// assert prima==dopo-1
		assertEquals(reqCountBefore, reqCountAfter - 1);
	}
	
	
	@After
	public void cleanDB() throws SQLException {
		joinCourseController.deleteRequest(requestBean);
	}

}
