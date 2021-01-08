package logic.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import logic.Session;
import logic.bean.LessonBean;
import logic.model.Lesson;
import logic.model.dao.LessonDAO;
import logic.utilities.Role;

public class ViewNextLessonController {

	public ViewNextLessonController() {
		
	}
	
	public List<LessonBean> getTodayLessons() throws SQLException, NullPointerException {
		
		List<Lesson> lessons;
		List<LessonBean> lessonsBean = new ArrayList<>();
		
		// Get current date and time
		Date date = new Date(System.currentTimeMillis());
		Time time = new Time(System.currentTimeMillis());
		
		if (Session.getSession().getType() == Role.STUDENT) {
			lessons = LessonDAO.getTodayNextLessonsStudent(date, time, Session.getSession().getUsername());
		}
		
		else if (Session.getSession().getType() == Role.PROFESSOR) {
			lessons = LessonDAO.getTodayNextLessonsProfessor(date, time, Session.getSession().getUsername());
		}
		
		else {
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
}
