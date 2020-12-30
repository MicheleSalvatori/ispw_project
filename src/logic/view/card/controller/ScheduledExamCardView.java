package logic.view.card.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ScheduledExamCardView {
	
	@FXML
	private Label labelCourse, labelClass, labelTime, labelDay;
	
	public void setCard(String day, String course, String classroom, String time) {
		labelDay.setText(day);
		labelCourse.setText(course);
		labelClass.setText(classroom);
		labelTime.setText(time);
	}

}
