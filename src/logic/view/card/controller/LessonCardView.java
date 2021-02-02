package logic.view.card.controller;

import java.io.IOException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import logic.bean.CourseBean;
import logic.bean.LessonBean;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.SQLConverter;

public class LessonCardView {
	
	@FXML
	private Label labelLesson;
	
	@FXML
	private Label labelClass;
	
	@FXML
	private Label labelTime;
	
	@FXML
	private Line line1;
	
	@FXML
	private Line line2;
	
	@FXML
	private Button btnView;
	
	@FXML
	private Button btnCourse;
	
	private LessonBean lesson;
	
	public void setCard(LessonBean lesson) {
		this.lesson = lesson;
		
		if (PageLoader.getPage() == Page.HOMEPAGE) {
			line1.setVisible(false);
			line2.setVisible(false);
			btnCourse.setVisible(false);
			
			labelLesson.setText(lesson.getCourse().getAbbreviation());
			labelClass.setText(lesson.getClassroom().getName());
			labelTime.setText(SQLConverter.time(lesson.getTime()));
		}
		
		else if (PageLoader.getPage() == Page.COURSE){
			line1.setVisible(false);
			line2.setVisible(false);
			btnCourse.setVisible(false);
			
			if (lesson.getDate().toLocalDate().isEqual(LocalDate.now())) {
				labelLesson.setText("Today");
			}
			else {
				labelLesson.setText(SQLConverter.date(lesson.getDate()));
			}	
			
			labelClass.setText(lesson.getClassroom().getName());
			labelTime.setText(SQLConverter.time(lesson.getTime()));
		}
		
		else {
			labelLesson.setText(SQLConverter.date(lesson.getDate()));
			labelClass.setText(lesson.getClassroom().getName());
			labelTime.setText(SQLConverter.time(lesson.getTime()));
			btnCourse.setText(lesson.getCourse().getAbbreviation());
		}
	}
	
	@FXML
	public void course(ActionEvent event) throws IOException {
    	CourseBean courseBean = lesson.getCourse();
    	PageLoader.getInstance().buildPage(Page.COURSE, courseBean);
	}
	
	@FXML
	public void viewLesson(ActionEvent event) throws IOException {
		PageLoader.getInstance().buildPage(Page.LESSON, lesson);
	}
}
