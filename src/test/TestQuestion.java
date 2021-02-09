package test;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import logic.bean.QuestionBean;
import logic.bean.StudentBean;
import logic.bean.UserBean;
import logic.controller.AskAQuestionController;
import logic.controller.QuestionController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.Role;

class TestQuestion {
	boolean dbUpdated = true;
	QuestionController questionController;
	QuestionBean questionTested;

	@Test
	void test() {
		String text = "Question Test text";
		String title = "Question Test";
		StudentBean student = new StudentBean();
		student.setUsername("michele.salvatori");
		QuestionBean questionBean = new QuestionBean();
		questionBean.setText(text);
		questionBean.setTitle(title);
		questionBean.setStudent(student);
		questionBean.setCourse("CE");

		AskAQuestionController controller = new AskAQuestionController();
		try {
			controller.save(questionBean);
		} catch (SQLException e) {
			e.printStackTrace();
			dbUpdated = false;
		}

		questionController = new QuestionController();
		UserBean user = new UserBean();
		user.setUsername("michele.salvatori");
		user.setRole(Role.STUDENT);
		List<QuestionBean> questions = null;
		try {
			questions = questionController.getAllQuestions(user);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}
		questionTested = questions.get(0);
		String resultTest = questionTested.getText();
		System.out.println(questionTested.getText());
		System.out.println(questionTested.getId());
		assertEquals(text, resultTest);

	}

	@After
	void cleanDB() {
		System.out.println("AFTER");
		if (dbUpdated) {
			System.out.println(questionTested.getId());
			questionController.deleteQuestion(questionTested.getId());
		}
	}

}
