package test.junit;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import logic.bean.ClassroomBean;
import logic.bean.LessonBean;
import logic.bean.SeatBean;
import logic.bean.UserBean;
import logic.controller.BookASeatController;
import logic.exceptions.SeatAlreadyBookedException;

/*
 * Michele Salvatori 0253519
 */

public class TestBookASeat {
	
	SeatBean seat;
	UserBean user;
	BookASeatController controller;
	boolean dbUpdated;
	
	LessonBean lesson;
	int idSeat = 1;
	
	@Before
	public void setupTest() {
		seat = new SeatBean(idSeat);
		user = new UserBean();
		lesson = new LessonBean();

		ClassroomBean classroom = new ClassroomBean();
		classroom.setName("test");
		user.setUsername("testStudent");
		
		lesson.setClassroom(classroom);
		lesson.setCourse("testCourse");
		lesson.setDate(Date.valueOf("2021-02-24"));
		lesson.setTime(Time.valueOf("21:00:00"));		
	}
	
	@Test
	public void test() {
		int idBooked = 0;
		controller = new BookASeatController();
			try {
				controller.occupateSeat(seat, lesson, user);
				idBooked = controller.getMySeat(lesson, user).getId();
				dbUpdated = true;
			} catch (SQLException | SeatAlreadyBookedException e) {
				e.printStackTrace();
			}
			
			assertEquals(idSeat, idBooked);
	}
	
	@After
	public void cleanDB() {
		if (dbUpdated) {
			try {
				controller.freeSeat(seat, lesson, user);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
