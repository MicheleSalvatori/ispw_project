package logic.view.card.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class LessonCardView implements Initializable {
	
	@FXML
	Label labelLesson, labelClass, labelTime;
	
	public void setLabel(String lesson, String classroom, String time) {
		labelLesson.setText(lesson);
		labelClass.setText(classroom);
		labelTime.setText(time);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
