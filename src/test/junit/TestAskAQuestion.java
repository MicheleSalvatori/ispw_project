package test.junit;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import logic.bean.QuestionBean;
import logic.bean.UserBean;
import logic.controller.AskAQuestionController;
import logic.controller.QuestionController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.Role;

/*
 * Michele Salvatori 0253519
 */

public class TestAskAQuestion {

	boolean dbUpdated = true;
	QuestionController questionController;
	AskAQuestionController askController;
	QuestionBean questionTested;
	String expectedText = "Testo di prova";
	String username = "michele.salvatori";
	QuestionBean questionBean;
	
	@Before
	public void prepareTest() {
		UserBean student = new UserBean();
		student.setUsername(username);
		questionBean = new QuestionBean();
		questionBean.setText(expectedText);
		questionBean.setTitle("Question Test");
		questionBean.setStudent(student);
		questionBean.setCourse("CE");
	}
	
	@Test
	public void test() {
		
		String message = "";

		askController = new AskAQuestionController();
		questionController = new QuestionController();
		List<QuestionBean> questions = null;
		 
		try {
			askController.save(questionBean);
			UserBean user = new UserBean();
			user.setUsername(username);
			user.setRole(Role.STUDENT);
			questions = questionController.getAllQuestions(user);
			questionTested = questions.get(0);
			
		} catch (SQLException e) {
			message = "Connection failed";
			dbUpdated = false;
			
		} catch (RecordNotFoundException e) {
			message = e.getMessage();
			dbUpdated = false;
		}

		String resultTest = questionTested.getText();
		assertEquals(message, expectedText, resultTest);
	}

	@After
	public void cleanDB() {
		if (dbUpdated) {
			questionController.deleteQuestion(questionTested.getId());
		}
	}
}
