package logic.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.bean.CourseBean;
import logic.bean.RequestBean;
import logic.bean.UserBean;
import logic.model.Course;
import logic.model.dao.CourseDAO;
import logic.model.dao.RequestDAO;

public class JoinCourseController {

	public boolean sendRequest(RequestBean requestBean) {
		return RequestDAO.insertRequest(requestBean);
	}
	
	public boolean removeCourse(RequestBean requestBean) {
		return RequestDAO.deleteFollow(requestBean);
	}
	
	public boolean deleteRequest(RequestBean requestBean) {
		return RequestDAO.deleteRequest(requestBean);
	}
	
	public List<CourseBean> getStudentCourses(UserBean userBean) throws SQLException {
		List<Course> courses = CourseDAO.getStudentCourses(userBean.getUsername());
		return setBeans(courses);
	}
	
	public List<CourseBean> getAvailableCourses(UserBean userBean) throws SQLException {
		List<Course> courses = CourseDAO.getAvailableCourses(userBean.getUsername());
		return setBeans(courses);
	}
	
	private List<CourseBean> setBeans(List<Course> courses) {
		
		List<CourseBean> coursesBean = new ArrayList<>();
		
		for (Course course : courses) {
			CourseBean courseBean = new CourseBean();
			courseBean.setAbbrevation(course.getAbbrevation());
			courseBean.setCredits(course.getCredits());
			courseBean.setGoal(course.getGoal());
			courseBean.setName(course.getName());
			courseBean.setPrerequisites(course.getPrerequisites());
			courseBean.setReception(course.getReception());
			courseBean.setSemester(course.getSemester());
			courseBean.setYear(course.getYear());
			
			coursesBean.add(courseBean);
		}
		
		return coursesBean;
	}
}