package test.junit;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import logic.bean.AssignmentBean;
import logic.bean.UserBean;
import logic.controller.AddAssignmentController;
import logic.exceptions.RecordNotFoundException;
import logic.model.Course;
import logic.utilities.Role;

/*
 * Luca Santopadre 0257118
 */

public class TestNewAssignment {
	
	AssignmentBean assignmentBean;
	AddAssignmentController addAssignmentController;
	Course course;
	int insertedAssigmentId = -1;
	boolean found = false;
	
	@Before
	public void prepare() {
		assignmentBean = new AssignmentBean();
		addAssignmentController = new AddAssignmentController();
		assignmentBean.setCourse("CE");
		assignmentBean.setDate(Date.valueOf("2021-04-30"));
		assignmentBean.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
		assignmentBean.setTitle("TEST");
		
		course = new Course();
		course.setAbbreviation(assignmentBean.getCourse());
	}

	@Test
	public void test() {	
		String message = "";
		
		// save assignment
		try {
			addAssignmentController.saveAssignment(assignmentBean);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// check if user see the assignment
		UserBean userStudentBean = new UserBean();
		userStudentBean.setUsername("luca");
		userStudentBean.setRole(Role.STUDENT);
		try {
			List<AssignmentBean> assignmentsList = addAssignmentController.getAssignments(userStudentBean);
			insertedAssigmentId = assignmentsList.get(assignmentsList.size()-1).getId();
			for (AssignmentBean a : assignmentsList) {
				found = (a.getId() == insertedAssigmentId);
			}
			
		} catch (SQLException e) {
			message = "Connection failed";
			
		} catch (RecordNotFoundException e) {
			message = e.getMessage();
		}
		
		assertEquals(message, true, found);
	}
	
	
	@After
	public void cleanDB() throws SQLException {
		if(found) {
			addAssignmentController.deleteAssignmentById(insertedAssigmentId);
		}
	}

}
