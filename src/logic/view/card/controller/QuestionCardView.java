package logic.view.card.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.bean.QuestionBean;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class QuestionCardView {

	@FXML
	private Label labelCourse, labelName, labelSurname, labelNumber, labelQuestionObject, labelQuestionDate,
			labelSolved;
	@FXML
	private Button btnView;
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
			labelSolved.setText("Solved");
			labelSolved.setStyle("-fx-text-fill: green");
		} else {
			labelSolved.setText("Unsolved");
			labelSolved.setStyle("-fx-text-fill: red");
		}
	}

	@FXML
	private void viewQuestion(ActionEvent ae) throws IOException {
		PageLoader.getInstance().buildPage(Page.QUESTION, ae, (Object) quest);
	}
}
