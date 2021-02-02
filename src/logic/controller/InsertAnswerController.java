package logic.controller;

import java.sql.SQLException;

import logic.bean.AnswerBean;
import logic.bean.UserBean;
import logic.model.Answer;
import logic.model.User;
import logic.model.dao.AnswerDAO;

public class InsertAnswerController {

	public void save(AnswerBean answerBean) throws SQLException {
		
		UserBean userBean = answerBean.getUser();
		//Non servono tutti gli attributi
		User user = new User(userBean.getUsername(), userBean.getPassword(), userBean.getName(), userBean.getSurname(), userBean.getEmail());
		
		Answer answer = new Answer(answerBean.getId(), answerBean.getText(), user, answerBean.getDate());
		AnswerDAO.saveAnswer(answer);	
	}
}
