package logic.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.bean.CourseBean;
import logic.bean.RequestBean;
import logic.bean.UserBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Course;
import logic.model.Professor;
import logic.model.Request;
import logic.model.Student;
import logic.model.User;
import logic.model.dao.CourseDAO;
import logic.model.dao.ProfessorDAO;
import logic.model.dao.RequestDAO;
import logic.utilities.Role;

public class JoinCourseController {

	public void sendRequest(RequestBean requestBean) throws SQLException {
		Student student = getStudent(requestBean);
		Course course = getCourse(requestBean);
			
		Request request = new Request(student, course);
		RequestDAO.insertRequest(request);
	}
	
	public void removeCourse(RequestBean requestBean) throws SQLException {
		Student student = getStudent(requestBean);
		Course course = getCourse(requestBean);
		
		Request request = new Request(student, course);
		RequestDAO.deleteFollow(request);
	}
	
	public void deleteRequest(RequestBean requestBean) throws SQLException {
		Student student = getStudent(requestBean);
		Course course = getCourse(requestBean);
		
		Request request = new Request(student, course);
		RequestDAO.deleteRequest(request);
	}
	
	public List<CourseBean> getStudentCourses(UserBean userBean) throws SQLException, RecordNotFoundException {
		List<Course> courses = CourseDAO.getStudentCourses(userBean.getUsername());
		return getBeans(courses);
	}
	
	public List<CourseBean> getAvailableCourses(UserBean userBean) throws SQLException, RecordNotFoundException {
		List<Course> courses = CourseDAO.getAvailableCourses(userBean.getUsername());
		return getBeans(courses);
	}
	
	public List<CourseBean> getCourses(UserBean userBean) throws SQLException, RecordNotFoundException {
		
		List<Course> courses = null;

		// User is a student
		if (userBean.getRole() == Role.STUDENT) {
			courses = CourseDAO.getStudentCourses(userBean.getUsername());
		}
			
		// User is a professor
		else {
			courses = CourseDAO.getProfessorCourses(userBean.getUsername());
		}
		
		return getBeans(courses);
	}
	
	public List<CourseBean> getRequestedCourses(UserBean userBean) throws SQLException, RecordNotFoundException {
		List<Course> courses = CourseDAO.getStudentCoursesByRequest(userBean.getUsername());
		return getBeans(courses);
	}
	
	private List<CourseBean> getBeans(List<Course> courses) {
		
		List<CourseBean> coursesBean = new ArrayList<>();
		
		for (Course course : courses) {
			CourseBean courseBean = new CourseBean();
			courseBean.setAbbreviation(course.getAbbreviation());
			courseBean.setSemester(course.getSemester());
			courseBean.setYear(course.getYear());
			courseBean.setName(course.getName());
			
			coursesBean.add(courseBean);
		}
		
		return coursesBean;
	}
	
	public List<UserBean> getCourseProfessors(CourseBean courseBean) throws SQLException {
	
		List<Professor> professors = null;
		
		try {
			professors = ProfessorDAO.getCourseProfessors(courseBean.getAbbreviation());
		
		} catch (RecordNotFoundException e) {
			Logger.getGlobal().log(Level.SEVERE, "An unexpected error occured");
			return new ArrayList<>();
		}
		
		List<UserBean> professorsBean = new ArrayList<>();
		
		for (User professor : professors) {
			UserBean professorBean = new UserBean();
			professorBean.setName(professor.getName());
			professorBean.setSurname(professor.getSurname());
			
			professorsBean.add(professorBean);
		}
		
		return professorsBean;
	}
	
	private Student getStudent(RequestBean requestBean) {
		UserBean studentBean = requestBean.getStudent();
		Student student = new Student();
		student.setUsername(studentBean.getUsername());
		return student;
	}
	
	private Course getCourse(RequestBean requestBean) {
		CourseBean courseBean = requestBean.getCourse();
		Course course = new Course();
		course.setAbbreviation(courseBean.getAbbreviation());
		return course;
	}
}