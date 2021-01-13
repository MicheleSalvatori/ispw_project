package logic.view.card.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import logic.bean.WeeklyLessonBean;
import logic.utilities.SQLConverter;

public class WeeklyLessonCardView {

	@FXML
	private Label labelDay;
	
	@FXML
	private Label labelClass;
	
	@FXML
	private Label labelTime;
	
	public void setCard(WeeklyLessonBean lesson) {	
		String d = lesson.getDay();
		String day = d.substring(0, 1).toUpperCase() + d.substring(1);
		
		labelDay.setText(day);
		labelClass.setText(lesson.getClassroom().getName());
		labelTime.setText(SQLConverter.time(lesson.getTime()));
	}
}
