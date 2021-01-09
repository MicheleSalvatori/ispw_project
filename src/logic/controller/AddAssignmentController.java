package logic.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.Session;
import logic.bean.AssignmentBean;
import logic.bean.CourseBean;
import logic.bean.ProfessorBean;
import logic.exceptions.NullException;
import logic.model.Assignment;
import logic.model.Course;
import logic.model.dao.AssignmentDAO;
import logic.model.dao.CourseDAO;
import logic.utilities.Role;

public class AddAssignmentController {

	public AddAssignmentController() {
		
	}
	
	public List<AssignmentBean> getAssignments() throws SQLException, NullException {
		
		List<Assignment> assignments;
		List<AssignmentBean> assignmentsBean = new ArrayList<>();
		
		// User is a student
		if (Session.getSession().getType() == Role.STUDENT) {
			assignments = AssignmentDAO.getAssignmentsByStudent(Session.getSession().getUsername());
		}
		
		// User is a professor
		else {
			assignments = AssignmentDAO.getAssignmentsByProfessor(Session.getSession().getUsername());
		}
		
		for (Assignment assignment : assignments) {
			
			Course course = assignment.getCourse();
			CourseBean courseBean = new CourseBean();
			courseBean.setAbbrevation(course.getAbbrevation());
			courseBean.setCredits(course.getCredits());
			courseBean.setGoal(course.getGoal());
			courseBean.setName(course.getName());
			courseBean.setPrerequisites(course.getPrerequisites());
			courseBean.setReception(course.getReception());
			courseBean.setSemester(course.getSemester());
			courseBean.setYear(course.getYear());
			
			AssignmentBean assignmentBean = new AssignmentBean();
			assignmentBean.setCourse(courseBean);
			assignmentBean.setDate(assignment.getDate());
			assignmentBean.setId(assignment.getId());
			assignmentBean.setText(assignment.getText());
			assignmentBean.setTitle(assignment.getTitle());
			
			assignmentsBean.add(assignmentBean);
		}
		
		return assignmentsBean;
	}
	
	public List<CourseBean> getCoursesOfProfessor(ProfessorBean professorBean) throws NullException {
		List<Course> courses = new ArrayList<>();
		List<CourseBean> courseBeans;
		
		try {
			courses = CourseDAO.getProfessorCourses(professorBean.getUsername());
			courseBeans = new ArrayList<>();
			for (Course c : courses) {
				CourseBean cb = new CourseBean(c.getName(), c.getAbbrevation());
				courseBeans.add(cb);
			}
			
		} catch (SQLException e) {
			courseBeans = null;
		}
		
		return courseBeans;
	}
	
	public boolean saveAssignment(AssignmentBean assignmentBean) throws SQLException {

		Course course = new Course();
		course.setAbbrevation(assignmentBean.getCourse().getAbbrevation());
		
		Assignment assignment = new Assignment(course, assignmentBean.getTitle(), assignmentBean.getDate(), assignmentBean.getText());

		return AssignmentDAO.saveAssignment(assignment);
	}
}
