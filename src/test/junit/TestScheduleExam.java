package test.junit;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import logic.bean.ExamBean;
import logic.bean.UserBean;
import logic.controller.ScheduleExamController;
import logic.controller.ScheduledController;
import logic.exceptions.DatePriorTodayException;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.Role;

/*
 * Michele Salvatori 0253519
 */

public class TestScheduleExam {

	ScheduleExamController controller;
	ExamBean exam;
	UserBean user;
	boolean dbUpdated;

	@Before
	public void setupTest() {
		String courseAbbr = "testCourse";
		String classroomName = "test";
		Date date = Date.valueOf(LocalDateTime.now().toLocalDate());
		Time time = Time.valueOf("23:59:00");
		String note = "This is a test";
		
		controller = new ScheduleExamController();
		exam = new ExamBean();
		user = new UserBean();
		user.setUsername("testStudent");
		user.setRole(Role.STUDENT);

		exam.setClassroom(classroomName);
		exam.setCourse(courseAbbr);
		exam.setDate(date);
		exam.setNote(note);
		exam.setTime(time);
	}

	@Test
	public void test() {
		String message = "";
		String result = "";
		ExamBean examBean = null;
		ScheduledController scheduledController = new ScheduledController();

		try {
			dbUpdated = controller.scheduleExam(exam);
			examBean = scheduledController.getExams(user).get(0);
			result = examBean.getDate().toString();
			
		} catch (SQLException | RecordNotFoundException e) {
			message = "Connection failed";
			
		} catch (DatePriorTodayException e) {
			message = "Date before today error";
		}
		
		String expected = exam.getDate().toString();
		assertEquals(message, expected, result);
	}
	
	@After
	public void cleanDB() {
		if (dbUpdated) {
			controller.deleteExam(exam);
		}
	}
}