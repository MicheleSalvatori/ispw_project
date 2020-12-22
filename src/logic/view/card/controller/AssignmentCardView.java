package logic.view.card.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AssignmentCardView {

	@FXML
	private Label labelCourse, labelNumber, labelTitle, labelDate;
	@FXML
	private Button btnView;

	public void setLabel(String number, String title, String date, String course) {
		labelCourse.setText(course);
		labelNumber.setText(number);
		labelDate.setText(date);
		labelTitle.setText(title);
	}
	
	@FXML
	private void viewAssignment() {
		System.out.println("View Assignment");
	}
}
