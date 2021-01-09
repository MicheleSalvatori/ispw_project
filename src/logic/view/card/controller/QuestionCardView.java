package logic.view.card.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.Session;
import logic.bean.CourseBean;
import logic.bean.QuestionBean;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.SQLConverter;
import logic.view.page.ForumPageView;

public class QuestionCardView {

	@FXML
	private Label labelName, labelSurname, labelNumber, labelQuestionObject, labelQuestionDate;
	
	@FXML
	private Button btnCourse, btnView, btnSolved;
	
	private QuestionBean question;

	public void setCard(QuestionBean question) {
		this.question = question;

		btnCourse.setText(question.getCourse().getAbbrevation());
		labelName.setText(question.getStudent().getName());
		labelSurname.setText(question.getStudent().getSurname());
		labelNumber.setText(String.valueOf(question.getId()));
		labelQuestionDate.setText(SQLConverter.date(question.getDate()));
		labelQuestionObject.setText(question.getTitle());

		if (question.isSolved()) {
			btnSolved.setDisable(true);
			btnSolved.setText("Solved");
			btnSolved.setStyle("-fx-text-fill: green");
		} else {
//			Only the author of this question can be set it to solved
			if (question.getStudent().getUsername().equals(Session.getSession().getUsername())) {
				btnSolved.setDisable(false);
				btnSolved.setText("Set Solved");
			} else {
				btnSolved.setText("Unsolved");
			}
			btnSolved.setStyle("-fx-text-fill: red");
		}
	}

	@FXML
	private void viewQuestion(ActionEvent ae) throws IOException {
		PageLoader.getInstance().buildPage(Page.QUESTION, ae, question);
	}

	@FXML
	private void setSolved(ActionEvent ae) {
		ForumPageView controller = (ForumPageView) PageLoader.getInstance().getController();
		if (controller.setSolved(question.getId())) {
			question.setSolved(true);
			setCard(question);
		}
	}

	@FXML
	private void course(ActionEvent event) throws IOException {
    	CourseBean courseBean = question.getCourse();
    	PageLoader.getInstance().buildPage(Page.COURSE, event, courseBean);
	}
}
