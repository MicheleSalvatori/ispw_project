package logic.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.Session;
import logic.bean.AnswerBean;
import logic.bean.QuestionBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Answer;
import logic.model.Question;
import logic.model.dao.CourseDAO;
import logic.model.dao.QuestionDAO;
import logic.utilities.Role;

public class AllQuestionController {
	private List<Question> questionList;

	public AllQuestionController() {
	}

	public int getNumberCourses() throws SQLException {
		Role role = Session.getSession().getType();
		int courses;
		courses = CourseDAO.getCourseNumberOf(Session.getSession().getUserLogged().getUsername(), role);
		return courses;
	}

	public List<QuestionBean> getAllQuestions() throws SQLException {
		List<QuestionBean> questionBeans = new ArrayList<>();
		Role role = Session.getSession().getType();
		try {
			questionList = QuestionDAO.getCoursesQuestions(Session.getSession().getUserLogged().getUsername(), role);
			for (Question q : questionList) {
				QuestionBean bean = new QuestionBean();
				bean.setId(q.getId());
				bean.setStudent(q.getStudent());
				bean.setText(q.getText());
				bean.setTitle(q.getTitle());
				bean.setCourseByAbbr(q.getCourse().getAbbrevation());
				bean.setSolved(q.isSolved());
				bean.setDate(q.getDate());
				List<Answer> answers = q.getAnswers();
				if (answers != null) {
					for (Answer a : answers) {
						AnswerBean answerBean = new AnswerBean(a.getId(), a.getText(), a.getUser(), a.getDate());
						bean.addAnswers(answerBean);
					}
				}
				questionBeans.add(bean);
			}
		} catch (RecordNotFoundException e) {
			throw new SQLException();
		} catch (NullPointerException e) {
			questionBeans = null;
		}
		return questionBeans;
	}

	public boolean setSolved(int questionID) {
		try {
			QuestionDAO.setSolved(questionID);
		} catch (SQLException e) {
//			AlertControl
			return false;
		}
		return true;
	}
}
