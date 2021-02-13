package logic.controller;

import java.sql.SQLException;

import logic.bean.CourseBean;
import logic.model.Course;
import logic.model.dao.CourseDAO;

public class AddCourseController {

	public void addCourse(CourseBean courseBean) throws SQLException {
		
		Course course = new Course();
		course.setAbbreviation(courseBean.getAbbreviation());
		course.setName(courseBean.getName());
		course.setCredits(courseBean.getCredits());
		course.setGoal(courseBean.getGoal());
		course.setYear(courseBean.getYear());
		course.setSemester(courseBean.getSemester());
		course.setPrerequisites(courseBean.getPrerequisites());
		course.setReception(courseBean.getReception());
		
		CourseDAO.addCourse(course);
	}
	
public void deleteCourse(CourseBean courseBean) throws SQLException {
		
		Course course = new Course();
		course.setAbbreviation(courseBean.getAbbreviation());
		course.setName(courseBean.getName());
		course.setCredits(courseBean.getCredits());
		course.setGoal(courseBean.getGoal());
		course.setYear(courseBean.getYear());
		course.setSemester(courseBean.getSemester());
		course.setPrerequisites(courseBean.getPrerequisites());
		course.setReception(courseBean.getReception());
		
		CourseDAO.deleteCourse(course);
	}
}
