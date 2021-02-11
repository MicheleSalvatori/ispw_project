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
import logic.bean.CourseBean;
import logic.bean.LessonBean;
import logic.bean.ProfessorBean;
import logic.controller.BookASeatController;
import logic.controller.ScheduleLessonController;

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
		Time time = Time.valueOf("23:59:00");
		String topic = "The lesson topic is test";
		
		ClassroomBean classroomBean = new ClassroomBean();
		classroomBean.setName(classroom);
		
		CourseBean courseBean = new CourseBean();
		courseBean.setAbbreviation(course);
		
		ProfessorBean professorBean = new ProfessorBean();
		professorBean.setUsername(professor);
		
		lessonBean = new LessonBean();
		lessonBean.setClassroom(classroomBean);
		lessonBean.setCourse(courseBean);
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
		BookASeatController lessonController = new BookASeatController();
		
		dbUpdate = controller.scheduleLesson(lessonBean);

		try {
			lesson = lessonController.getLesson(lessonBean);
			
		} catch (SQLException e) {
			message = "Connection failed";
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
