package logic.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.bean.ClassroomBean;
import logic.bean.CourseBean;
import logic.bean.ExamBean;
import logic.bean.LessonBean;
import logic.bean.UserBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Classroom;
import logic.model.Course;
import logic.model.Exam;
import logic.model.Lesson;
import logic.model.dao.CourseDAO;
import logic.model.dao.ExamDAO;
import logic.model.dao.LessonDAO;
import logic.utilities.Role;

public class ScheduledController {
	
	public List<LessonBean> getLessons(UserBean userBean) throws SQLException, RecordNotFoundException {
		
		// Get current date
		Date date = new Date(System.currentTimeMillis());
		List<Lesson> lessons;
		List<LessonBean> lessonsBean = new ArrayList<>();
		
		// Student role
		if (userBean.getRole() == Role.STUDENT) {
			lessons = LessonDAO.getNextLessonsStudent(date, userBean.getUsername());
		}
		
		// Professor role
		else {
			lessons = LessonDAO.getNextLessonsProfessor(date, userBean.getUsername());
		}

		
		for (Lesson lesson : lessons) {
			
			Classroom classroom = lesson.getClassroom();
			ClassroomBean classroomBean = new ClassroomBean();
			classroomBean.setName(classroom.getName());
			
			Course course = lesson.getCourse();
			CourseBean courseBean = new CourseBean();
			courseBean.setAbbreviation(course.getAbbrevation());
			
			LessonBean lessonBean = new LessonBean();
			lessonBean.setClassroom(classroomBean);
			lessonBean.setCourse(courseBean);
			lessonBean.setDate(lesson.getDate());
			lessonBean.setTime(lesson.getTime());
			
			lessonsBean.add(lessonBean);
		}
		
		return lessonsBean;
	}
	
	public List<ExamBean> getExams(UserBean userBean) throws SQLException, RecordNotFoundException {
		
		// Get current date
		Date date = new Date(System.currentTimeMillis());
		List<Exam> exams;
		List<ExamBean> examsBean = new ArrayList<>();
		
		// Get user lessons and courses
		if (userBean.getRole() == Role.STUDENT) {
			exams = ExamDAO.getNextExamsStudent(date, userBean.getUsername());
		}
		
		else if (userBean.getRole() == Role.PROFESSOR) {
			exams = ExamDAO.getNextExamsProfessor(date, userBean.getUsername());
		}
		
		else {
			// Admin role
			return null;
		}
		
		for (Exam exam : exams) {
			
			Classroom classroom = exam.getClassroom();
			ClassroomBean classroomBean = new ClassroomBean();
			classroomBean.setName(classroom.getName());
			
			Course course = exam.getCourse();
			CourseBean courseBean = new CourseBean();
			courseBean.setAbbreviation(course.getAbbrevation());
			courseBean.setCredits(course.getCredits());
			courseBean.setGoal(course.getGoal());
			courseBean.setName(course.getName());
			courseBean.setPrerequisites(course.getPrerequisites());
			courseBean.setReception(course.getReception());
			courseBean.setSemester(course.getSemester());
			courseBean.setYear(course.getYear());
			
			ExamBean examBean = new ExamBean();
			examBean.setClassroom(classroomBean);
			examBean.setCourse(courseBean);
			examBean.setDate(exam.getDate());
			examBean.setNote(exam.getNote());
			examBean.setTime(exam.getTime());

	    	examsBean.add(examBean);
		}
		
		return examsBean;
	}
	
	public List<CourseBean> getCourses(UserBean userBean) throws SQLException, RecordNotFoundException {
		
		List<Course> courses;
		List<CourseBean> coursesBean = new ArrayList<>();
		
		// Student role
		if (userBean.getRole() == Role.STUDENT) {
			courses = CourseDAO.getStudentCourses(userBean.getUsername());
		}
		
		// Professor role
		else {
			courses = CourseDAO.getProfessorCourses(userBean.getUsername());
		}
		
		for (Course course : courses) {
	    	CourseBean courseBean = new CourseBean();
	    	courseBean.setAbbreviation(course.getAbbrevation());
	    	courseBean.setName(course.getName());
	    	
	    	coursesBean.add(courseBean);
		}

		return coursesBean;
	}
}
