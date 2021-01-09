package logic.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.bean.CourseBean;
import logic.bean.QuestionBean;
import logic.exceptions.NullException;
import logic.model.Course;
import logic.model.Question;
import logic.model.dao.CourseDAO;
import logic.model.dao.QuestionDAO;

public class InsertQuestionController {

	public InsertQuestionController() {
		
	}

	public List<CourseBean> getCoursesOfStudent(String usernameString) throws NullException {
		List<Course> courses = new ArrayList<>();
		List<CourseBean> courseBeans;
		try {
			courses = CourseDAO.getStudentCourses(usernameString);

			courseBeans = new ArrayList<>();
			for (Course c : courses) {
				CourseBean cb = new CourseBean(c.getName(), c.getAbbrevation());
				courseBeans.add(cb);
			}
			
		} catch (SQLException e) {
			courseBeans = null;
		}
		
		return courseBeans;
	}

	public void save(QuestionBean questionBean) throws SQLException {
		Course course = new Course();
		course.setAbbrevation(questionBean.getCourse().getAbbrevation());
				
		Question question = new Question(questionBean.getTitle(), questionBean.getText(), course, questionBean.getStudent(),
										 false, new Date(System.currentTimeMillis()));

		QuestionDAO.saveQuestion(question);
	}
	
}
