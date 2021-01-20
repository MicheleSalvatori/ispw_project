package logic.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import logic.Session;
import logic.bean.ClassroomBean;
import logic.bean.CourseBean;
import logic.bean.LessonBean;
import logic.bean.ProfessorBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Classroom;
import logic.model.Course;
import logic.model.Lesson;
import logic.model.Professor;
import logic.model.dao.LessonDAO;
import logic.utilities.Role;

public class ViewNextLessonController {

	public ViewNextLessonController() {
		
	}
	
	public List<LessonBean> getTodayLessons() throws SQLException, RecordNotFoundException {
		
		List<Lesson> lessons;
		List<LessonBean> lessonsBean = new ArrayList<>();
		
		// Get current date and time
		Date date = new Date(System.currentTimeMillis());
		Time time = new Time(System.currentTimeMillis());
		
		if (Session.getSession().getRole() == Role.STUDENT) {
			lessons = LessonDAO.getTodayNextLessonsStudent(date, time, Session.getSession().getUsername());
		}
		
		else if (Session.getSession().getRole() == Role.PROFESSOR) {
			lessons = LessonDAO.getTodayNextLessonsProfessor(date, time, Session.getSession().getUsername());
		}
		
		else {
			return null;
		}
		
		for (Lesson lesson : lessons) {
			
			Course course = lesson.getCourse();
			CourseBean courseBean = new CourseBean();
			courseBean.setAbbrevation(course.getAbbrevation());
			courseBean.setCredits(course.getCredits());
			courseBean.setGoal(course.getGoal());
			courseBean.setName(course.getName());
			courseBean.setPrerequisites(course.getPrerequisites());
			courseBean.setReception(course.getReception());
			courseBean.setSemester(course.getSemester());
			courseBean.setYear(course.getYear());
			
			Classroom classroom = lesson.getClassroom();
			ClassroomBean classroomBean = new ClassroomBean();
			classroomBean.setName(classroom.getName());
			classroomBean.setSeatRow(classroom.getSeatRow());
			classroomBean.setSeatColumn(classroom.getSeatColumn());
			
			
			Professor professor = lesson.getProfessor();
			ProfessorBean professorBean = new ProfessorBean();
			professorBean.setEmail(professor.getEmail());
			professorBean.setName(professor.getName());
			professorBean.setPassword(professor.getPassword());
			professorBean.setSurname(professor.getSurname());
			professorBean.setUsername(professor.getUsername());
			
			LessonBean lessonBean = new LessonBean();
			lessonBean.setClassroom(classroomBean);
			lessonBean.setCourse(courseBean);
			lessonBean.setDate(lesson.getDate());
			lessonBean.setProfessor(professorBean);
			lessonBean.setTime(lesson.getTime());
			lessonBean.setTopic(lesson.getTopic());
			
			lessonsBean.add(lessonBean);
		}
		
		return lessonsBean;
	}
}
