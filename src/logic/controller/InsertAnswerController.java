package logic.controller;

import java.sql.SQLException;

import logic.bean.AnswerBean;
import logic.model.Answer;
import logic.model.dao.AnswerDAO;

public class InsertAnswerController {

	public void save(AnswerBean answerBean) throws SQLException {
		Answer answer = new Answer(answerBean.getId(), answerBean.getText(), answerBean.getStudent(), answerBean.getDate());
		AnswerDAO.saveAnswer(answer);	
	}
}
