package logic.controller;

import logic.bean.ClassroomBean;
import logic.bean.CourseBean;
import logic.bean.ExamBean;
import logic.model.Classroom;
import logic.model.Course;
import logic.model.Exam;
import logic.model.dao.ExamDAO;

public class ScheduleExamController {

	public boolean scheduleExam(ExamBean examBean) {
		
		CourseBean courseBean = examBean.getCourse();
		Course course = new Course();
		course.setAbbreviation(courseBean.getAbbreviation());
		
		ClassroomBean classroomBean = examBean.getClassroom();
		Classroom classroom = new Classroom(classroomBean.getName());
		
		Exam exam = new Exam(examBean.getDate(), examBean.getTime(), course, classroom, examBean.getNote());
		return ExamDAO.insertExam(exam);
	}
	
	public boolean deleteExam(ExamBean examBean){
		Course course = new Course();
		course.setAbbreviation(examBean.getCourse().getAbbreviation());
		Classroom classroom = new Classroom(examBean.getClassroom().getName());
		Exam exam = new Exam(examBean.getDate(), examBean.getTime(), course, classroom, examBean.getNote());
		
		return ExamDAO.deleteExam(exam);
	}
}
