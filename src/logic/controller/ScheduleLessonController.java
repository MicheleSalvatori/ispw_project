package logic.controller;

import logic.bean.ClassroomBean;
import logic.bean.CourseBean;
import logic.bean.LessonBean;
import logic.bean.ProfessorBean;
import logic.model.Classroom;
import logic.model.Course;
import logic.model.Lesson;
import logic.model.Professor;
import logic.model.dao.LessonDAO;

public class ScheduleLessonController {
	
	public boolean scheduleLesson(LessonBean lessonBean) {
		
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
	
	public boolean deleteLesson(LessonBean lessonBean) {
		Course course = new Course();
		course.setAbbreviation(lessonBean.getCourse().getAbbreviation());
		
		Lesson lesson = new Lesson();
		lesson.setDate(lessonBean.getDate());
		lesson.setTime(lessonBean.getTime());
		lesson.setCourse(course);

		return LessonDAO.deleteLesson(lesson);
	}
}
