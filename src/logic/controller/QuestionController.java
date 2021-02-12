package logic.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.bean.AnswerBean;
import logic.bean.QuestionBean;
import logic.bean.UserBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Answer;
import logic.model.Question;
import logic.model.User;
import logic.model.dao.AnswerDAO;
import logic.model.dao.CourseDAO;
import logic.model.dao.QuestionDAO;
import logic.utilities.Role;

public class QuestionController {

	public int getNumberCourses(UserBean userBean) throws SQLException {
		int courses;

		// User is a student
		if (userBean.getRole() == Role.STUDENT) {
			courses = CourseDAO.getStudentCourseNumberOf(userBean.getUsername());
		}

		// User is a professor
		else {
			courses = CourseDAO.getProfessorCourseNumberOf(userBean.getUsername());
		}

		return courses;
	}

	public QuestionBean getQuestionByID(int id) throws SQLException {

		Question question = null;
		try {
			question = QuestionDAO.getQuestion(id);

		} catch (RecordNotFoundException e) {
			Logger.getGlobal().log(Level.SEVERE, "An unexpected error occured");
			return null;
		}

		UserBean studentBean = new UserBean();
		studentBean.setName(question.getStudent().getName());
		studentBean.setSurname(question.getStudent().getSurname());
		studentBean.setUsername(question.getStudent().getUsername());

		QuestionBean questionBean = new QuestionBean();
		questionBean.setCourse(question.getCourse().getAbbreviation());
		questionBean.setId(question.getId());
		questionBean.setDate(question.getDate());
		questionBean.setSolved(question.isSolved());
		questionBean.setStudent(studentBean);
		questionBean.setText(question.getText());
		questionBean.setTitle(question.getTitle());

		return questionBean;
	}

	public List<QuestionBean> getAllQuestions(UserBean userBean) throws SQLException, RecordNotFoundException {

		List<QuestionBean> questionBeans = new ArrayList<>();
		List<Question> questionList;

		// User is a student
		if (userBean.getRole() == Role.STUDENT) {
			questionList = QuestionDAO.getStudentCoursesQuestions(userBean.getUsername());
		}

		// User is a professor
		else {
			questionList = QuestionDAO.getProfessorCoursesQuestions(userBean.getUsername());
		}

		for (Question q : questionList) {

			UserBean studentBean = new UserBean();
			studentBean.setName(q.getStudent().getName());
			studentBean.setSurname(q.getStudent().getSurname());
			studentBean.setUsername(q.getStudent().getUsername());

			QuestionBean bean = new QuestionBean();
			bean.setId(q.getId());
			bean.setStudent(studentBean);
			bean.setText(q.getText());
			bean.setTitle(q.getTitle());
			bean.setCourse(q.getCourse().getAbbreviation());
			bean.setSolved(q.isSolved());
			bean.setDate(q.getDate());
			questionBeans.add(bean);
		}

		return questionBeans;
	}

	public int countQuestions(UserBean userBean) throws SQLException, RecordNotFoundException {
		return QuestionDAO.countQuestionsByProfessor(userBean.getUsername());
	}

	public void setSolved(int questionID) throws SQLException {
		QuestionDAO.setSolved(questionID);
	}

	public List<AnswerBean> getAnswersOf(int questionID) throws RecordNotFoundException, SQLException {
		List<AnswerBean> answerList = new ArrayList<>();
		List<Answer> answers;
		answers = AnswerDAO.getAnswerOf(questionID);
		for (Answer answer : answers) {
			User user = answer.getUser();
			UserBean usrBean = new UserBean();
			usrBean.setName(user.getName());
			usrBean.setSurname(user.getSurname());

			AnswerBean answerBean = new AnswerBean();
			answerBean.setDate(answer.getDate());
			answerBean.setId(answer.getId());
			answerBean.setText(answer.getText());
			answerBean.setUser(usrBean);
			answerList.add(answerBean);
		}
		return answerList;
	}
	
	
	public void deleteQuestion(int id) {
		try {
			QuestionDAO.deleteQuestion(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
