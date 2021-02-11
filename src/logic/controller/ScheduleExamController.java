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
		List<String> courseInfo = new ArrayList<>();
		courseInfo.add(courseBean.getName());
		courseInfo.add(courseBean.getAbbreviation());
		courseInfo.add(courseBean.getYear());
		courseInfo.add(courseBean.getSemester());
		courseInfo.add(courseBean.getCredits());
		courseInfo.add(courseBean.getPrerequisites());
		courseInfo.add(courseBean.getGoal());
		courseInfo.add(courseBean.getReception());
		
		Course course = new Course(courseInfo);
		
		ClassroomBean classroomBean = examBean.getClassroom();
		Classroom classroom = new Classroom(classroomBean.getName());
		
		Exam exam = new Exam(examBean.getDate(), examBean.getTime(), course, classroom, examBean.getNote());
		return ExamDAO.insertExam(exam);
	}
}
