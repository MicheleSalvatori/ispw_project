package logic.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.bean.CourseBean;
import logic.bean.ProfessorBean;
import logic.bean.RequestBean;
import logic.bean.StudentBean;
import logic.bean.UserBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Course;
import logic.model.Professor;
import logic.model.Request;
import logic.model.Student;
import logic.model.dao.CourseDAO;
import logic.model.dao.ProfessorDAO;
import logic.model.dao.RequestDAO;
import logic.utilities.Role;

public class JoinCourseController {

	public boolean sendRequest(RequestBean requestBean) {
		Student student = getStudent(requestBean);
		Course course = getCourse(requestBean);
			
		Request request = new Request(student, course);
		return RequestDAO.insertRequest(request);
	}
	
	public boolean removeCourse(RequestBean requestBean) {
		Student student = getStudent(requestBean);
		Course course = getCourse(requestBean);
		
		Request request = new Request(student, course);
		return RequestDAO.deleteFollow(request);
	}
	
	public boolean deleteRequest(RequestBean requestBean) {
		Student student = getStudent(requestBean);
		Course course = getCourse(requestBean);
		
		Request request = new Request(student, course);
		return RequestDAO.deleteRequest(request);
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
		
		List<Course> courses;
		
		// User is a student
		if (userBean.getRole() == Role.STUDENT) {
			courses = CourseDAO.getStudentCourses(userBean.getUsername());
		}
		
		// User is a professor
		else if (userBean.getRole() == Role.PROFESSOR) {
			courses = CourseDAO.getProfessorCourses(userBean.getUsername());
		}
		
		// User is an admin
		else {
			return null;
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
	
	public List<ProfessorBean> getCourseProfessors(CourseBean courseBean) throws SQLException, RecordNotFoundException {
		List<Professor> professors = ProfessorDAO.getCourseProfessors(courseBean.getAbbrevation());
		List<ProfessorBean> professorsBean = new ArrayList<>();
		
		for (Professor professor : professors) {
			ProfessorBean professorBean = new ProfessorBean();
			professorBean.setEmail(professor.getEmail());
			professorBean.setName(professor.getName());
			professorBean.setPassword(professor.getPassword());
			professorBean.setSurname(professor.getSurname());
			professorBean.setUsername(professor.getUsername());
			
			professorsBean.add(professorBean);
		}
		
		return professorsBean;
	}
	
	private Student getStudent(RequestBean requestBean) {
		StudentBean studentBean = requestBean.getStudent();
		Student student = new Student();
		student.setUsername(studentBean.getUsername());
		return student;
	}
	
	private Course getCourse(RequestBean requestBean) {
		CourseBean courseBean = requestBean.getCourse();
		Course course = new Course();
		course.setAbbrevation(courseBean.getAbbrevation());
		return course;
	}
}