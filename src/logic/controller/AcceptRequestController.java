package logic.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.Session;
import logic.bean.CourseBean;
import logic.bean.RequestBean;
import logic.bean.StudentBean;
import logic.exceptions.NullException;
import logic.model.Course;
import logic.model.Request;
import logic.model.Student;
import logic.model.dao.CourseDAO;
import logic.model.dao.RequestDAO;

public class AcceptRequestController {

	public boolean acceptRequest(RequestBean requestBean) {
		if (deleteRequest(requestBean)) {
			
			StudentBean studentBean = requestBean.getStudent();
			Student student = new Student();
			student.setUsername(studentBean.getUsername());
			
			CourseBean courseBean = requestBean.getCourse();
			Course course = new Course();
			course.setAbbrevation(courseBean.getAbbrevation());
			
			Request request = new Request(student, course);
			return RequestDAO.insertFollow(request);
		}
		return false;
	}
	
	public boolean declineRequest(RequestBean requestBean) {
		return deleteRequest(requestBean);
	}
	
	public boolean deleteRequest(RequestBean requestBean) {
		StudentBean studentBean = requestBean.getStudent();
		Student student = new Student();
		student.setUsername(studentBean.getUsername());
		
		CourseBean courseBean = requestBean.getCourse();
		Course course = new Course();
		course.setAbbrevation(courseBean.getAbbrevation());
		
		Request request = new Request(student, course);
		return RequestDAO.deleteRequest(request);
	}
	
	public List<CourseBean> getCourses() throws SQLException, NullPointerException, NullException {
		
		List<Course> courses = CourseDAO.getProfessorCourses(Session.getSession().getUsername());
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
	
	public List<RequestBean> getRequests() throws SQLException, NullPointerException, NullException {
		
		List<Request> requests = RequestDAO.getRequestsByProfessor(Session.getSession().getUsername());
		List<RequestBean> requestsBean = new ArrayList<>();
	
		for (Request request : requests) {
			RequestBean requestBean = new RequestBean();
			
			Course course = request.getCourse();
			CourseBean courseBean = new CourseBean();
			courseBean.setAbbrevation(course.getAbbrevation());
			courseBean.setCredits(course.getAbbrevation());
			courseBean.setGoal(course.getGoal());
			courseBean.setName(course.getName());
			courseBean.setPrerequisites(course.getPrerequisites());
			courseBean.setReception(course.getReception());
			courseBean.setSemester(course.getSemester());
			courseBean.setYear(course.getYear());
			
			Student student = request.getStudent();
			StudentBean studentBean = new StudentBean();
			studentBean.setEmail(student.getEmail());
			studentBean.setName(student.getName());
			studentBean.setPassword(student.getPassword());
			studentBean.setSurname(student.getSurname());
			studentBean.setUsername(student.getUsername());
			
			requestBean.setCourse(courseBean);
			requestBean.setStudent(studentBean);
					
			requestsBean.add(requestBean);
		}
		
		return requestsBean;
	}
}
