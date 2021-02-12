package logic.controller;

import logic.bean.ExamBean;
import logic.model.Classroom;
import logic.model.Course;
import logic.model.Exam;
import logic.model.dao.ExamDAO;

public class ScheduleExamController {

	public boolean scheduleExam(ExamBean examBean) {
		
		Course course = new Course();
		course.setAbbreviation(examBean.getCourse());

		Classroom classroom = new Classroom(examBean.getClassroom());
		
		Exam exam = new Exam(examBean.getDate(), examBean.getTime(), course, classroom, examBean.getNote());
		return ExamDAO.insertExam(exam);
	}
}
