package logic.controller;

import logic.bean.ClassroomBean;
import logic.bean.LessonBean;
import logic.bean.ProfessorBean;
import logic.exceptions.DatePriorTodayException;
import logic.bean.UserBean;
import logic.model.Classroom;
import logic.model.Course;
import logic.model.Lesson;
import logic.model.Professor;
import logic.model.dao.LessonDAO;
import logic.utilities.DateUtils;

public class ScheduleLessonController {
	
	public boolean scheduleLesson(LessonBean lessonBean) throws DatePriorTodayException {
    DateUtils.checkPriorDate(lessonBean.getDate());
		Course course = new Course();
		course.setAbbreviation(lessonBean.getCourse());
		
		ClassroomBean classroomBean = lessonBean.getClassroom();
		Classroom classroom = new Classroom(classroomBean.getName());
		
		UserBean professorBean = lessonBean.getProfessor();
		Professor professor = new Professor(professorBean.getUsername(), professorBean.getPassword(),
											professorBean.getName(), professorBean.getSurname(), professorBean.getEmail());
			
		Lesson lesson = new Lesson(lessonBean.getDate(), lessonBean.getTime(), course, classroom, lessonBean.getTopic(), professor);
		return LessonDAO.insertLesson(lesson);
	}

	public boolean deleteLesson(LessonBean lessonBean) {
		Course course = new Course();
		course.setAbbreviation(lessonBean.getCourse());
		
		Lesson lesson = new Lesson();
		lesson.setDate(lessonBean.getDate());
		lesson.setTime(lessonBean.getTime());
		lesson.setCourse(course);

		return LessonDAO.deleteLesson(lesson);
	}
}
