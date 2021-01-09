package logic.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.Session;
import logic.bean.AnswerBean;
import logic.bean.QuestionBean;
import logic.bean.UserBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Answer;
import logic.model.Question;
import logic.model.User;
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
					for (Answer answer : answers) {
						
						User user = answer.getUser();
						UserBean userBean = new UserBean();
						userBean.setEmail(user.getEmail());
						userBean.setName(user.getName());
						userBean.setPassword(user.getPassword());
						userBean.setSurname(user.getSurname());
						userBean.setUsername(user.getUsername());
						
						AnswerBean answerBean = new AnswerBean();
						answerBean.setDate(answer.getDate());
						answerBean.setId(answer.getId());
						answerBean.setText(answer.getText());
						answerBean.setUser(userBean);
						
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
