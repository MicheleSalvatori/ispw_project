package logic.view.card.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class QuestionCardView {

	@FXML
	private Label labelCourse, labelName, labelSurname, labelNumber, labelQuestionObject, labelQuestionDate;
	@FXML
	private Button btnView;

	public void setLabel(String number, String questionObject, String date, String name, String surname,
			String course) {
		labelCourse.setText(course);
		labelName.setText(name);
		labelSurname.setText(surname);
		labelNumber.setText(number);
		labelQuestionDate.setText(date);
		labelQuestionObject.setText(questionObject);
	}
	
	@FXML
	private void viewQuestion() {
		System.out.println("View Question");
	}
}
