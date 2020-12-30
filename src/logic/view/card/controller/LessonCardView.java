package logic.view.card.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import logic.utilities.Page;
import logic.utilities.PageLoader;

public class LessonCardView implements Initializable {
	
	@FXML
	private Label labelLesson, labelClass, labelTime, labelCourse;
	
	@FXML
	private Line line1, line2;
	
	@FXML
	private Button btnView;
	
	public void setLabel(String lesson, String classroom, String time) {
		line1.setVisible(false);
		line2.setVisible(false);
		labelCourse.setVisible(false);
		
		labelLesson.setText(lesson);
		labelClass.setText(classroom);
		labelTime.setText(time);
	}
	
	public void setLabel(String day, String classroom, String course, String time) {
		labelLesson.setText(day);
		labelClass.setText(classroom);
		labelCourse.setText(course);
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
