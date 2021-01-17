package logic.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.bean.AnswerBean;
import logic.bean.QuestionBean;
import logic.bean.StudentBean;
import logic.bean.UserBean;
import logic.exceptions.RecordNotFoundException;
import logic.model.Answer;
import logic.model.Question;
import logic.model.User;
import logic.model.dao.CourseDAO;
import logic.model.dao.QuestionDAO;
import logic.utilities.Role;

public class AllQuestionController {

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

	public List<QuestionBean> getAllQuestions(UserBean userBean) throws SQLException {
		
		List<QuestionBean> questionBeans = new ArrayList<>();
		List<Question> questionList;
		
		try {
			
			// User is a student
			if (userBean.getRole() == Role.STUDENT) {
				questionList = QuestionDAO.getStudentCoursesQuestions(userBean.getUsername());
			}
			
			// User is a professor
			else {
				questionList = QuestionDAO.getProfessorCoursesQuestions(userBean.getUsername());
			}

			for (Question q : questionList) {
				
				StudentBean studentBean = new StudentBean();
				studentBean.setEmail(q.getStudent().getEmail());
				studentBean.setName(q.getStudent().getName());
				studentBean.setSurname(q.getStudent().getSurname());
				studentBean.setUsername(q.getStudent().getUsername());
				
				QuestionBean bean = new QuestionBean();
				bean.setId(q.getId());
				bean.setStudent(studentBean);
				bean.setText(q.getText());
				bean.setTitle(q.getTitle());
				bean.setCourseByAbbr(q.getCourse().getAbbrevation());
				bean.setSolved(q.isSolved());
				bean.setDate(q.getDate());
				List<Answer> answers = q.getAnswers();
				if (answers != null) {
					for (Answer answer : answers) {
						
						User user = answer.getUser();
						UserBean usrBean = new UserBean();
						usrBean.setEmail(user.getEmail());
						usrBean.setName(user.getName());
						usrBean.setPassword(user.getPassword());
						usrBean.setSurname(user.getSurname());
						usrBean.setUsername(user.getUsername());
						
						AnswerBean answerBean = new AnswerBean();
						answerBean.setDate(answer.getDate());
						answerBean.setId(answer.getId());
						answerBean.setText(answer.getText());
						answerBean.setUser(usrBean);
						
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
	
	public int countQuestions(UserBean userBean) throws SQLException, RecordNotFoundException {
		return QuestionDAO.countQuestionsByProfessor(userBean.getUsername());
	}

	public boolean setSolved(int questionID) {
		try {
			QuestionDAO.setSolved(questionID);
			
		} catch (SQLException e) {
			// AlertControl
			return false;
		}
		
		return true;
	}
}
