package logic.controller;

import java.sql.SQLException;

import logic.bean.AnswerBean;
import logic.model.Answer;
import logic.model.dao.AnswerDAO;

public class InsertAnswerController {

	public void save(AnswerBean answerBean) throws SQLException {
		Answer answer = new Answer();
		answer.setDate(answerBean.getDate());
		answer.setId(answerBean.getId());
		answer.setStudent(answerBean.getStudent());
		answer.setText(answerBean.getText());
		AnswerDAO.saveAnswer(answer);	
	}
}
