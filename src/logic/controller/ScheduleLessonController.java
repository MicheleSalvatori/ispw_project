package logic.controller;

import logic.bean.LessonBean;
import logic.model.dao.LessonDAO;

public class ScheduleLessonController {
	
	public boolean scheduleLesson(LessonBean lessonBean) {
		return LessonDAO.insertLesson(lessonBean);
	}

}
