package test.junit;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import logic.bean.CourseBean;
import logic.controller.AddCourseController;
import logic.controller.CourseController;

/*
 * Luca Santopadre 0257118
 */
public class TestAddCourse {
	CourseBean courseBean;
	AddCourseController addCourseController;
	CourseController courseController;
	String abbr = "_TE";
	String courseName = "TEST course";
	boolean created = false;
	

	@Before
	public void prepare() {
		courseBean = new CourseBean();
		addCourseController = new AddCourseController();
		courseController = new CourseController();
		
		courseBean.setAbbreviation(abbr);
		courseBean.setName(courseName);
		courseBean.setYear("2021");
		courseBean.setCredits("9");
		courseBean.setPrerequisites("test");
		courseBean.setReception("test");
		courseBean.setGoal("test");
		courseBean.setSemester("I");
		
	}

	@Test
	public void test() {		
		// add couurse
		try {
			addCourseController.addCourse(courseBean);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// check if created
		courseBean = new CourseBean();
		courseBean.setAbbreviation(abbr);
		courseBean.setName(courseName);
		try {
			courseController.getCourse(courseBean);
			created=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}  catch (NullPointerException e) {
			e.printStackTrace();
			created=false;
		}
		
		// assert
		assertEquals(true, created);
		
	}
	
	
	@After
	public void cleanDB() throws SQLException {
		if(created) {
			addCourseController.deleteCourse(courseBean);
		}
	}

}
