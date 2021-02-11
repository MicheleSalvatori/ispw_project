package logic.controller;

import java.util.ArrayList;
import java.util.List;

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
		List<String> info = new ArrayList<>();
		info.add(courseBean.getName());
		info.add(courseBean.getAbbreviation());
		info.add(courseBean.getYear());
		info.add(courseBean.getSemester());
		info.add(courseBean.getCredits());
		info.add(courseBean.getPrerequisites());
		info.add(courseBean.getGoal());
		info.add(courseBean.getReception());
		
		Course course = new Course(info);
		
		ClassroomBean classroomBean = examBean.getClassroom();
		Classroom classroom = new Classroom(classroomBean.getName());
		
		Exam exam = new Exam(examBean.getDate(), examBean.getTime(), course, classroom, examBean.getNote());
		return ExamDAO.insertExam(exam);
	}
}
