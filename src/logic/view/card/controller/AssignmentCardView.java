package logic.view.card.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.utilities.Page;
import logic.utilities.PageLoader;

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
	private void viewAssignment(ActionEvent ae) throws IOException {
		System.out.println("View Assignment");
		PageLoader.getInstance().buildPage(Page.ASSIGNMENT, ae, null);
	}
}
