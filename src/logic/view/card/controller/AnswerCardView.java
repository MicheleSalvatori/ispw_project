package logic.view.card.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class AnswerCardView {

    @FXML
    private TextArea textAnswer;

    @FXML
    private Label labelStudent;

    @FXML
    private Label labelDate;

    public void setLabel(String student, String date, String text) {
		labelStudent.setText(student);
		labelDate.setText(date);
		textAnswer.setText(text);
	}
}
