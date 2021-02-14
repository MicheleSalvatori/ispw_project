package logic.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.bean.CourseBean;
import logic.bean.QuestionBean;
import logic.bean.UserBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Course;
import logic.model.Question;
import logic.model.Student;
import logic.model.dao.CourseDAO;
import logic.model.dao.QuestionDAO;

public class AskAQuestionController {

	public List<CourseBean> getStudentCourses(UserBean user) throws SQLException {
		List<Course> courses = new ArrayList<>();
		List<CourseBean> courseBeans = null;
		String username = user.getUsername();
		
		try {
			courses = CourseDAO.getStudentCourses(username);

			courseBeans = new ArrayList<>();
			for (Course c : courses) {
				CourseBean cb = new CourseBean(c.getName(), c.getAbbreviation());
				courseBeans.add(cb);
			}
			
		} catch (RecordNotFoundException e) {
			Logger.getGlobal().log(Level.SEVERE, "An unexpected error occured");
		}
		
		return courseBeans;
	}

	public void save(QuestionBean questionBean) throws SQLException {
		Course course = new Course();
		course.setAbbreviation(questionBean.getCourse());
		
		Student student = new Student();
		student.setUsername(questionBean.getStudent().getUsername());
				
		Question question = new Question(questionBean.getTitle(), questionBean.getText(), course, student,
										 false, new Date(System.currentTimeMillis()));

		QuestionDAO.saveQuestion(question);
	}
}
