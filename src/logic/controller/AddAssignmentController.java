package logic.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.bean.AssignmentBean;
import logic.bean.CourseBean;
import logic.bean.UserBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Assignment;
import logic.model.Course;
import logic.model.dao.AssignmentDAO;
import logic.model.dao.CourseDAO;
import logic.utilities.Role;

public class AddAssignmentController {
	
	public List<AssignmentBean> getAssignments(UserBean userBean) throws SQLException, RecordNotFoundException {
		
		List<Assignment> assignments;
		List<AssignmentBean> assignmentsBean = new ArrayList<>();
		
		// User is a student
		if (userBean.getRole() == Role.STUDENT) {
			assignments = AssignmentDAO.getAssignmentsByStudent(userBean.getUsername());
		}
		
		// User is a professor
		else {
			assignments = AssignmentDAO.getAssignmentsByProfessor(userBean.getUsername());
		}
		
		for (Assignment assignment : assignments) {
			
			Course course = assignment.getCourse();
			
			AssignmentBean assignmentBean = new AssignmentBean();
			assignmentBean.setCourse(course.getAbbreviation());
			assignmentBean.setDate(assignment.getDate());
			assignmentBean.setId(assignment.getId());
			assignmentBean.setText(assignment.getText());
			assignmentBean.setTitle(assignment.getTitle());
			
			assignmentsBean.add(assignmentBean);
		}
		
		return assignmentsBean;
	}
	
	public List<CourseBean> getCoursesOfProfessor(UserBean userBean) throws RecordNotFoundException {
		List<Course> courses = new ArrayList<>();
		List<CourseBean> courseBeans;
		
		try {
			courses = CourseDAO.getProfessorCourses(userBean.getUsername());
			courseBeans = new ArrayList<>();
			for (Course c : courses) {
				CourseBean cb = new CourseBean(c.getName(), c.getAbbreviation());
				courseBeans.add(cb);
			}
			
		} catch (SQLException e) {
			courseBeans = null;
		}
		
		return courseBeans;
	}
	
	public boolean saveAssignment(AssignmentBean assignmentBean) throws SQLException {

		Course course = new Course();
		course.setAbbreviation(assignmentBean.getCourse());
		
		Assignment assignment = new Assignment(course, assignmentBean.getTitle(), assignmentBean.getDate(), assignmentBean.getText());

		return AssignmentDAO.saveAssignment(assignment);
	}
	
	public AssignmentBean getAssignmentByID(int id) throws SQLException {

		AssignmentBean assignmentBean = null;
		
		try {
			Assignment assignment = AssignmentDAO.getAssignment(id);
			assignmentBean = new AssignmentBean();
			assignmentBean.setCourse(assignment.getCourse().getAbbreviation());
			assignmentBean.setTitle(assignment.getTitle());
			assignmentBean.setText(assignment.getText());
			assignmentBean.setDate(assignment.getDate());
			assignmentBean.setId(assignment.getId());
			
		} catch (RecordNotFoundException e) {
			Logger.getGlobal().log(Level.SEVERE, "An unexpected error occured");
		}

		return assignmentBean;
	}
}
