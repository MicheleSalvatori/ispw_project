package logic.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.Session;
import logic.bean.CourseBean;
import logic.bean.RequestBean;
import logic.model.Course;
import logic.model.Request;
import logic.model.dao.CourseDAO;
import logic.model.dao.RequestDAO;

public class AcceptRequestController {

	public boolean acceptRequest(RequestBean requestBean) {
		if (deleteRequest(requestBean)) {
			return RequestDAO.insertFollow(requestBean);
		}
		return false;
	}
	
	public boolean declineRequest(RequestBean requestBean) {
		return deleteRequest(requestBean);
	}
	
	public boolean deleteRequest(RequestBean requestBean) {
		return RequestDAO.deleteRequest(requestBean);
	}
	
	public List<CourseBean> getCourses() throws SQLException, NullPointerException {
		
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
	
	public List<RequestBean> getRequests() throws SQLException, NullPointerException {
		
		List<Request> requests = RequestDAO.getRequestsByProfessor(Session.getSession().getUsername());
		List<RequestBean> requestsBean = new ArrayList<>();
	
		requests = RequestDAO.getRequestsByProfessor(Session.getSession().getUsername());
		for (Request request : requests) {
			RequestBean requestBean = new RequestBean();
			requestBean.setCourse(request.getCourse());
			requestBean.setStudent(request.getStudent());
					
			requestsBean.add(requestBean);
		}
		
		return requestsBean;
	}
}
