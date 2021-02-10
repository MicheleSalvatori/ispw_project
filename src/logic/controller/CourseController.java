package logic.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.bean.ClassroomBean;
import logic.bean.CourseBean;
import logic.bean.LessonBean;
import logic.bean.ProfessorBean;
import logic.bean.WeeklyLessonBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Classroom;
import logic.model.Course;
import logic.model.Lesson;
import logic.model.Professor;
import logic.model.WeeklyLesson;
import logic.model.dao.CourseDAO;
import logic.model.dao.LessonDAO;
import logic.model.dao.ProfessorDAO;
import logic.model.dao.WeeklyLessonDAO;

public class CourseController {
	
	public LessonBean getNextLesson(CourseBean courseBean) throws SQLException, RecordNotFoundException {
		
		Date date = new Date(System.currentTimeMillis());
		Time time = new Time(System.currentTimeMillis());
		Lesson lesson = LessonDAO.getNextLessonByCourse(date, time, courseBean.getAbbreviation());
		
		Classroom classroom = lesson.getClassroom();
		ClassroomBean classroomBean = new ClassroomBean();
		classroomBean.setName(classroom.getName());
		
		LessonBean lessonBean = new LessonBean();
		lessonBean.setClassroom(classroomBean);
		lessonBean.setCourse(courseBean);
		lessonBean.setDate(lesson.getDate());
		lessonBean.setTime(lesson.getTime());
		
		return lessonBean;
	}
	
	public List<ProfessorBean> getCourseProfessors(CourseBean courseBean) throws SQLException, RecordNotFoundException {
		
		List<Professor> professors = ProfessorDAO.getCourseProfessors(courseBean.getAbbreviation());
		List<ProfessorBean> professorsBean = new ArrayList<>();
		
		for (Professor professor : professors) {
			ProfessorBean professorBean = new ProfessorBean();
			professorBean.setName(professor.getName());
			professorBean.setSurname(professor.getSurname());
			
			professorsBean.add(professorBean);
		}
		
		return professorsBean;
	}
	
	public List<WeeklyLessonBean> getWeeklyLessons(CourseBean courseBean) throws SQLException, RecordNotFoundException {
		
		List<WeeklyLesson> lessons = WeeklyLessonDAO.getCourseWeeklyLessons(courseBean.getAbbreviation());
		List<WeeklyLessonBean> lessonsBean = new ArrayList<>();
		
		for (WeeklyLesson lesson : lessons) {
			
			ClassroomBean classroomBean = new ClassroomBean();
			classroomBean.setName(lesson.getClassroom().getName());
			
			WeeklyLessonBean lessonBean = new WeeklyLessonBean();
			lessonBean.setDay(lesson.getDay());
			lessonBean.setTime(lesson.getTime());
			lessonBean.setClassroom(classroomBean);
			lessonBean.setCourse(courseBean);
			
			lessonsBean.add(lessonBean);
		}
		
		return lessonsBean;
	}
	
	public CourseBean getCourse(CourseBean courseBean) throws SQLException {
		
		Course course;
		
		try {
			course = CourseDAO.getCourseByAbbrevation(courseBean.getAbbreviation());
			
		} catch (RecordNotFoundException e) {
			Logger.getGlobal().log(Level.SEVERE, "An unexpected error occured");
			return null;
		}
		
		CourseBean c = new CourseBean();
		c.setAbbreviation(course.getAbbreviation());
		c.setCredits(course.getCredits());
		c.setGoal(course.getGoal());
		c.setName(course.getName());
		c.setPrerequisites(course.getPrerequisites());
		c.setReception(course.getReception());
		c.setSemester(course.getSemester());
		c.setYear(course.getYear());
		
		return c;
	}
}
