package logic.view.card.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WeeklyLessonCardView {

	@FXML
	private Label labelDay;
	
	@FXML
	private Label labelClass;
	
	@FXML
	private Label labelTime;
	
	public void setCard(String day, String classroom, String time) {
		labelDay.setText(day);
		labelClass.setText(classroom);
		labelTime.setText(time);
	}
}
