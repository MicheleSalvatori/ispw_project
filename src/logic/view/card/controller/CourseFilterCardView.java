package logic.view.card.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CourseFilterCardView {
	
	@FXML
	private Button btnCourse;
	
	public void setCard(String name) {
		btnCourse.setText(name);
	}
}
