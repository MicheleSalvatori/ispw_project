package test.junit;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import logic.bean.ClassroomBean;
import logic.bean.LessonBean;
import logic.bean.UserBean;
import logic.controller.ScheduleLessonController;
import logic.controller.ViewNextLessonController;
import logic.exceptions.DatePriorTodayException;

/*
 * Gabriele Quatrana 0253513
 */

public class TestScheduleLesson {
	
	ScheduleLessonController controller;
	LessonBean lessonBean;
	boolean dbUpdate;
	
	@Before
	public void setupTest() {
		String course = "testCourse";
		String professor = "testProfessor";
		String classroom = "test";
		Date date = Date.valueOf(LocalDateTime.now().toLocalDate());
		Time time = Time.valueOf("23:58:00");
		String topic = "The lesson topic is test";
		
		ClassroomBean classroomBean = new ClassroomBean();
		classroomBean.setName(classroom);
		
		UserBean professorBean = new UserBean();
		professorBean.setUsername(professor);
		
		lessonBean = new LessonBean();
		lessonBean.setClassroom(classroomBean);
		lessonBean.setCourse(course);
		lessonBean.setProfessor(professorBean);
		lessonBean.setTime(time);
		lessonBean.setDate(date);
		lessonBean.setTopic(topic);
	}

	@Test
	public void testScheduleLesson() {
		String message = "";
		LessonBean lesson = new LessonBean();
		
		controller = new ScheduleLessonController();
		ViewNextLessonController lessonController = new ViewNextLessonController();
		
		try {
			dbUpdate = controller.scheduleLesson(lessonBean);
			lesson = lessonController.getLesson(lessonBean);
			
		} catch (SQLException e) {
			message = "Connection failed";
			
		} catch (DatePriorTodayException e) {
			message = e.getMessage();
		}
		
		String expected = lessonBean.getTopic();
		String result = lesson.getTopic();
		
		assertEquals(message, expected, result);
	}
	
	@After
	public void cleanDB() {
		if (dbUpdate) {
			controller.deleteLesson(lessonBean);
		}
	}
}
