package logic.view.card.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.Session;
import logic.bean.QuestionBean;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.page.ForumPageView;

public class QuestionCardView {

	@FXML
	private Label labelCourse, labelName, labelSurname, labelNumber, labelQuestionObject, labelQuestionDate;
	@FXML
	private Button btnView, btnSolved;
	private QuestionBean quest;

	public void setLabel(QuestionBean bean) {
		this.quest = bean;
		labelCourse.setText(bean.getQuestionCourse().getAbbrevation());
		labelName.setText(bean.getStudent().getName());
		labelSurname.setText(bean.getStudent().getSurname());
		labelNumber.setText(String.valueOf(bean.getId()));
		labelQuestionDate.setText(bean.getDate().toString());
		labelQuestionObject.setText(bean.getTitle());
		if (bean.isSolved()) {
			btnSolved.setDisable(true);
			btnSolved.setText("Solved");
			btnSolved.setStyle("-fx-text-fill: green");
		} else {
//			Only the author of this question can be set it to solved
			if (bean.getStudent().getUsername().equals(Session.getSession().getUsername())) {
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
		PageLoader.getInstance().buildPage(Page.QUESTION, ae, (Object) quest);
	}

	@FXML
	private void setSolved(ActionEvent ae) {
		ForumPageView controller = (ForumPageView) PageLoader.getInstance().getController();
		if (controller.setSolved(quest.getId())) {
			quest.setSolved(true);
			setLabel(quest);
		}
	}

}
