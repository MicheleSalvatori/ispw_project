package test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import logic.bean.QuestionBean;
import logic.bean.StudentBean;
import logic.bean.UserBean;
import logic.controller.AskAQuestionController;
import logic.controller.QuestionController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.Role;

public class TestAskQuestion {

	boolean dbUpdated = true;
	QuestionController questionController;
	AskAQuestionController askController;
	QuestionBean questionTested;
	String expectedText = "Testo di prova";
	String username = "michele.salvatori";
	QuestionBean questionBean;
	
	@Before
	public void prepareTest() {
		StudentBean student = new StudentBean();
		student.setUsername(username);
		questionBean = new QuestionBean();
		questionBean.setText(expectedText);
		questionBean.setTitle("Question Test");
		questionBean.setStudent(student);
		questionBean.setCourse("CE");
	}
	@Test
	public void test() {

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
			e.printStackTrace();
			dbUpdated = false;
		} catch (RecordNotFoundException e) {
			Logger.getGlobal().log(Level.INFO, "Error in DB Connection");
		}

		String resultTest = questionTested.getText();
		assertEquals(expectedText, resultTest);

	}

	@After
	public void cleanDB() {
		if (dbUpdated) {
			questionController.deleteQuestion(questionTested.getId());
		}
	}


}
