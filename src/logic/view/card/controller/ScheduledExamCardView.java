package logic.view.card.controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.bean.CourseBean;
import logic.bean.ExamBean;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.SQLConverter;

public class ScheduledExamCardView {
	
	@FXML
	private Label labelClass, labelTime, labelDay;
	
	@FXML
	private Button btnCourse;
	
	private ExamBean exam;
	
	@FXML
	private void course(ActionEvent event) throws SQLException, IOException {
    	CourseBean courseBean = exam.getCourse();
    	PageLoader.getInstance().buildPage(Page.COURSE, event, courseBean);
	}
	
	public void setCard(ExamBean exam) {
		this.exam = exam;
		
		labelDay.setText(SQLConverter.date(exam.getDate()));
		btnCourse.setText(exam.getCourse().getAbbrevation());
		labelClass.setText(exam.getClassroom().getName());
		labelTime.setText(SQLConverter.time(exam.getTime()));
	}
}