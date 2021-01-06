package logic.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.bean.CourseBean;
import logic.bean.QuestionBean;
import logic.model.Course;
import logic.model.Question;
import logic.model.dao.CourseDAO;
import logic.model.dao.QuestionDAO;

public class InsertQuestionController {

	public InsertQuestionController() {
	}

	public List<CourseBean> getCoursesOfStudent(String usernameString) {
		List<Course> courses = new ArrayList<>();
		List<CourseBean> courseBeans;
		try {
			courses = CourseDAO.getStudentCourses(usernameString);
			if (courses == null) {
				courseBeans = null;
			} else {
				courseBeans = new ArrayList<>();
				for (Course c : courses) {
					CourseBean cb = new CourseBean(c.getName(), c.getAbbrevation());
					courseBeans.add(cb);
				}
			}
		} catch (SQLException e) {
			courseBeans = null;
		}
		return courseBeans;
	}

	public void save(QuestionBean questionBean) throws SQLException {
		Question question = new Question(questionBean.getTitle(), questionBean.getText(), questionBean.getCourse(), questionBean.getStudent(),
										 false, new Date(System.currentTimeMillis()), questionBean.getAnswers());

		QuestionDAO.saveQuestion(question);
	}

}
