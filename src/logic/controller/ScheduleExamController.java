package logic.controller;

import logic.bean.ExamBean;
import logic.model.dao.ExamDAO;

public class ScheduleExamController {

	public boolean scheduleExam(ExamBean examBean) {
		return ExamDAO.insertExam(examBean);
	}
}
