package logic.view.card.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RequestCardView {
	
	@FXML
	private Label labelName, labelCourse;
	
	@FXML
	private Button btnDecline, btnAccept;
	
	public void setCard(String name, String course) {
		labelName.setText(name);
		labelCourse.setText(course);
	}

}
