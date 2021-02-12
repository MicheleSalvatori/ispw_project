package logic.view.card.controller;

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
	private Label labelClass;
	
	@FXML
	private Label labelTime;
	
	@FXML
	private Label labelDay;
	
	@FXML
	private Button btnCourse;
	
	private ExamBean exam;
	
	@FXML
	private void course(ActionEvent event) {
    	CourseBean courseBean = new CourseBean();
    	courseBean.setAbbreviation(exam.getCourse());
    	PageLoader.getInstance().buildPage(Page.COURSE, courseBean);
	}
	
	public void setCard(ExamBean exam) {
		this.exam = exam;
		
		labelDay.setText(SQLConverter.date(exam.getDate()));
		btnCourse.setText(exam.getCourse());
		labelClass.setText(exam.getClassroom());
		labelTime.setText(SQLConverter.time(exam.getTime()));
	}
}