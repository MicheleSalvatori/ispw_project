package logic.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import logic.bean.CourseBean;
import logic.bean.LessonBean;
import logic.bean.ProfessorBean;
import logic.model.Lesson;
import logic.model.Professor;
import logic.model.dao.LessonDAO;
import logic.model.dao.ProfessorDAO;

public class CourseController {

	public CourseController() {
		
	}
	
	public LessonBean getNextLesson(CourseBean course) throws SQLException {
		
		Date date = new Date(System.currentTimeMillis());
		Time time = new Time(System.currentTimeMillis());
		Lesson lesson = LessonDAO.getNextLessonByCourse(date, time, course.getAbbrevation());
		
		LessonBean lessonBean = new LessonBean();
		lessonBean.setClassroom(lesson.getClassroom());
		lessonBean.setCourse(lesson.getCourse());
		lessonBean.setDate(lesson.getDate());
		lessonBean.setProfessor(lesson.getProfessor());
		lessonBean.setTime(lesson.getTime());
		lessonBean.setTopic(lesson.getTopic());
		
		return lessonBean;
	}
	
	public List<ProfessorBean> getCourseProfessors(CourseBean course) throws SQLException {
		
		List<Professor> professors = ProfessorDAO.getCourseProfessors(course.getAbbrevation());
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
}
