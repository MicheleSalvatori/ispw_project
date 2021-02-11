package test;

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


public class TestNewAssignment {
	AssignmentBean assignmentBean;
	AddAssignmentController addAssignmentController;
	Course course;
	int insertedAssigmentId = -1;
	
	public TestNewAssignment() {
		
	}

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
			boolean finded = false;
			for (AssignmentBean a : assignmentsList) {
				finded = a.getId() == insertedAssigmentId;
			}
			assertEquals(true, finded);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		

	}
	
	
	@After
	public void cleanDB() throws SQLException {
		System.out.println("AFTER");
		addAssignmentController.deleteAssignmentById(insertedAssigmentId);
	}

}
