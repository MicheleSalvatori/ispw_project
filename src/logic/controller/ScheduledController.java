package logic.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.Session;
import logic.bean.CourseBean;
import logic.bean.ExamBean;
import logic.bean.LessonBean;
import logic.model.Course;
import logic.model.Exam;
import logic.model.Lesson;
import logic.model.dao.CourseDAO;
import logic.model.dao.ExamDAO;
import logic.model.dao.LessonDAO;
import logic.utilities.Role;

public class ScheduledController {

	public ScheduledController() {
		
	}
	
	public List<LessonBean> getLessons() throws SQLException {
		
		// Get current date
		Date date = new Date(System.currentTimeMillis());
		List<Lesson> lessons;
		List<LessonBean> lessonsBean = new ArrayList<>();
		
		// Get user lessons and courses
		if (Session.getSession().getType() == Role.STUDENT) {
			lessons = LessonDAO.getNextLessonsStudent(date, Session.getSession().getUsername());
		}
		
		else if (Session.getSession().getType() == Role.PROFESSOR) {
			lessons = LessonDAO.getNextLessonsProfessor(date, Session.getSession().getUsername());
		}
		
		else {
			// Admin role
			return null;
		}
		
		for (Lesson lesson : lessons) {
			LessonBean lessonBean = new LessonBean();
			lessonBean.setClassroom(lesson.getClassroom());
			lessonBean.setCourse(lesson.getCourse());
			lessonBean.setDate(lesson.getDate());
			lessonBean.setProfessor(lesson.getProfessor());
			lessonBean.setTime(lesson.getTime());
			lessonBean.setTopic(lesson.getTopic());
			
			lessonsBean.add(lessonBean);
		}
		
		return lessonsBean;
	}
	
	public List<ExamBean> getExams() throws SQLException {
		
		// Get current date
		Date date = new Date(System.currentTimeMillis());
		List<Exam> exams;
		List<ExamBean> examsBean = new ArrayList<>();
		
		// Get user lessons and courses
		if (Session.getSession().getType() == Role.STUDENT) {
			exams = ExamDAO.getNextExamsStudent(date, Session.getSession().getUsername());
		}
		
		else if (Session.getSession().getType() == Role.PROFESSOR) {
			exams = ExamDAO.getNextExamsProfessor(date, Session.getSession().getUsername());
		}
		
		else {
			// Admin role
			return null;
		}
		
		for (Exam exam : exams) {
			ExamBean examBean = new ExamBean();
			examBean.setClassroom(exam.getClassroom());
			examBean.setCourse(exam.getCourse());
			examBean.setDate(exam.getDate());
			examBean.setNote(exam.getNote());
			examBean.setTime(exam.getTime());

	    	examsBean.add(examBean);
		}
		
		return examsBean;
	}
	
	public List<CourseBean> getCourses() throws SQLException {
		
		List<Course> courses;
		List<CourseBean> coursesBean = new ArrayList<>();
		
		// Get user lessons and courses
		if (Session.getSession().getType() == Role.STUDENT) {
			courses = CourseDAO.getStudentCourses(Session.getSession().getUsername());
		}
		
		else if (Session.getSession().getType() == Role.PROFESSOR) {
			courses = CourseDAO.getProfessorCourses(Session.getSession().getUsername());
		}
		
		else {
			// Admin role
			return null;
		}
		
		for (Course course : courses) {
	    	CourseBean courseBean = new CourseBean();
	    	courseBean.setAbbrevation(course.getAbbrevation());
	    	courseBean.setName(course.getName());
	    	courseBean.setYear(course.getYear());
	    	courseBean.setCredits(course.getCredits());
	    	courseBean.setSemester(course.getSemester());
	    	courseBean.setPrerequisites(course.getPrerequisites());
	    	courseBean.setGoal(course.getGoal());
	    	courseBean.setReception(course.getReception());
	    	
	    	coursesBean.add(courseBean);
		}
		
		return coursesBean;
	}
}
