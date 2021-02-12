package test.junit;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import logic.bean.ClassroomBean;
import logic.bean.CourseBean;
import logic.bean.ExamBean;
import logic.bean.UserBean;
import logic.controller.ScheduleExamController;
import logic.controller.ScheduledController;
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
		
		ClassroomBean classroom = new ClassroomBean();
		classroom.setName(classroomName);
		CourseBean course = new CourseBean();
		course.setAbbreviation(courseAbbr);

		exam.setClassroom(classroom);
		exam.setCourse(course);
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

		dbUpdated = controller.scheduleExam(exam);
		
		try {
			examBean = scheduledController.getExams(user).get(0);
			result = examBean.getDate().toString();
		} catch (SQLException | RecordNotFoundException e) {
			message = "Connection failed";
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
