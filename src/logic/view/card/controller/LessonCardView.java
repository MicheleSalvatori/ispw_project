package logic.view.card.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class LessonCardView implements Initializable {
	
	@FXML
	private Label labelLesson, labelClass, labelTime;
	
	@FXML
	private Button btnView;
	
	public void setLabel(String lesson, String classroom, String time) {
		labelLesson.setText(lesson);
		labelClass.setText(classroom);
		labelTime.setText(time);
	}
	
	@FXML
	public void viewLesson(ActionEvent ae) throws IOException {
		PageLoader.getInstance().buildPage(Page.LESSON, ae);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
