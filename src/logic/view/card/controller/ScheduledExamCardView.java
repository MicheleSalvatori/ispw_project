package logic.view.card.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ScheduledExamCardView {
	
	@FXML
	private Label labelCourse, labelClass, labelTime;
	
	public void setCard(String course, String classroom, String time) {
		labelCourse.setText(course);
		labelClass.setText(classroom);
		labelTime.setText(time);
	}

}
