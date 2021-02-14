package logic.controller;

import logic.bean.ClassroomBean;
import logic.bean.CourseBean;
import logic.bean.LessonBean;
import logic.bean.ProfessorBean;
import logic.exceptions.DatePriorTodayException;
import logic.model.Classroom;
import logic.model.Course;
import logic.model.Lesson;
import logic.model.Professor;
import logic.model.dao.LessonDAO;
import logic.utilities.DateUtils;

public class ScheduleLessonController {
	
	public boolean scheduleLesson(LessonBean lessonBean) throws DatePriorTodayException {
		
		DateUtils.checkPriorDate(lessonBean.getDate());
		
		CourseBean courseBean = lessonBean.getCourse();
		Course course = new Course(courseBean.getName(), courseBean.getAbbreviation(), courseBean.getYear(),
									courseBean.getSemester(), courseBean.getCredits(), courseBean.getPrerequisites(),
									courseBean.getGoal(), courseBean.getReception());
		
		ClassroomBean classroomBean = lessonBean.getClassroom();
		Classroom classroom = new Classroom(classroomBean.getName());
		
		ProfessorBean professorBean = lessonBean.getProfessor();
		Professor professor = new Professor(professorBean.getUsername(), professorBean.getPassword(),
											professorBean.getName(), professorBean.getSurname(), professorBean.getEmail());
			
		Lesson lesson = new Lesson(lessonBean.getDate(), lessonBean.getTime(), course, classroom, lessonBean.getTopic(), professor);
		return LessonDAO.insertLesson(lesson);
	}
	

}
