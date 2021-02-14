package logic.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.bean.ClassroomBean;
import logic.bean.LessonBean;
import logic.bean.UserBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Classroom;
import logic.model.Course;
import logic.model.Lesson;
import logic.model.dao.LessonDAO;
import logic.utilities.Role;

public class ViewNextLessonController {
	
	public LessonBean getLesson(LessonBean lesson) throws SQLException {

		Lesson l = null;
		
		try {
			l = LessonDAO.getLesson(lesson.getDate(), lesson.getTime(), lesson.getCourse());

		} catch (RecordNotFoundException e) {
			Logger.getGlobal().log(Level.SEVERE, "An unexpected error occured");
			return null;
		}
		
		ClassroomBean classroom = new ClassroomBean();
		classroom.setName(l.getClassroom().getName());
		classroom.setSeatRow(l.getClassroom().getSeatRow());
		classroom.setSeatColumn(l.getClassroom().getSeatColumn());
		
		UserBean professor = new UserBean();
		professor.setName(l.getProfessor().getName());
		professor.setSurname(l.getProfessor().getSurname());
		
		LessonBean lessonBean = new LessonBean();
		lessonBean.setClassroom(classroom);
		lessonBean.setCourse(l.getCourse().getAbbreviation());
		lessonBean.setDate(l.getDate());
		lessonBean.setProfessor(professor);
		lessonBean.setTime(l.getTime());
		lessonBean.setTopic(l.getTopic());
		
		return lessonBean;
	}
	
	public List<LessonBean> getTodayLessons(UserBean userBean) throws SQLException, RecordNotFoundException {
		
		List<Lesson> lessons;
		List<LessonBean> lessonsBean = new ArrayList<>();
		
		// Get current date and time
		Date date = new Date(System.currentTimeMillis());
		Time time = new Time(System.currentTimeMillis());
		
		// Student role
		if (userBean.getRole() == Role.STUDENT) {
			lessons = LessonDAO.getTodayNextLessonsStudent(date, time, userBean.getUsername());
		}
		
		// Professor role
		else {
			lessons = LessonDAO.getTodayNextLessonsProfessor(date, time, userBean.getUsername());
		}

		for (Lesson lesson : lessons) {
			
			Course course = lesson.getCourse();
			
			Classroom classroom = lesson.getClassroom();
			ClassroomBean classroomBean = new ClassroomBean();
			classroomBean.setName(classroom.getName());
			
			LessonBean lessonBean = new LessonBean();
			lessonBean.setClassroom(classroomBean);
			lessonBean.setCourse(course.getAbbreviation());
			lessonBean.setDate(lesson.getDate());
			lessonBean.setTime(lesson.getTime());
			
			lessonsBean.add(lessonBean);
		}
		
		return lessonsBean;
	}
}
