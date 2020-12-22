package logic.view.card.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ExamCardView {

	@FXML
	private Label labelName;

	@FXML
	private Label labelDate;

	@FXML
	private Label labelVote;

	@FXML
	private Label labelCourse;

	@FXML
	private Label labelCFU;

	@FXML
	private Label labelNumber;

	public void setLabel(String number, String date, String cfu, String vote, String name, String course) {
		labelNumber.setText(number);
		labelDate.setText(date);
		labelCourse.setText(course);
		labelCFU.setText(cfu);
		labelName.setText(name);
		labelVote.setText(vote);
	}
}
