package logic.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.Session;
import logic.bean.ClassroomBean;
import logic.bean.CourseBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Classroom;
import logic.model.Course;
import logic.model.dao.ClassroomDAO;
import logic.model.dao.CourseDAO;

public class ScheduleController {

	public List<ClassroomBean> getClassrooms() throws SQLException, RecordNotFoundException {
		
		List<Classroom> classrooms = ClassroomDAO.getAllClassrooms();
		List<ClassroomBean> classroomsBean = new ArrayList<>();
		
		for (Classroom classroom : classrooms) {
			ClassroomBean classroomBean = new ClassroomBean();
			classroomBean.setName(classroom.getName());
			
			classroomsBean.add(classroomBean);
		}
		
		return classroomsBean;
	}
	
	public List<CourseBean> getCourses() throws SQLException, RecordNotFoundException {
		
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
}