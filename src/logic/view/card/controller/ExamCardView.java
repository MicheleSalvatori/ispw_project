package logic.view.card.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.bean.CourseBean;
import logic.bean.VerbalizedBean;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.SQLConverter;
import logic.view.page.ExamPageView;

public class ExamCardView {

	@FXML
	private Label labelName;
	
	@FXML
	private Label labelDate;
	
	@FXML
	private Label labelVote;
	
	@FXML
	private Label labelCFU;
	
	@FXML
	private Label labelNumber;
	
	@FXML
	private Button btnCourse;
	
	private VerbalizedBean verb;

	public void setCard(VerbalizedBean verb, int num) {
		this.verb = verb;
		
		labelNumber.setText(Integer.toString(num));
		labelDate.setText(SQLConverter.date(verb.getDate()));
		btnCourse.setText(verb.getCourse().getAbbreviation());
		labelCFU.setText(verb.getCourse().getCredits());
		labelName.setText(verb.getCourse().getName());
		labelVote.setText(Integer.toString(verb.getGrade()));
	}
	
	@FXML
	private void course(ActionEvent event) throws IOException {
    	CourseBean courseBean = verb.getCourse();
    	PageLoader.getInstance().buildPage(Page.COURSE, courseBean);
	}
	
	@FXML
	private void deleteExam(ActionEvent event) throws IOException {
		ExamPageView examPageView = (ExamPageView) PageLoader.getInstance().getController();
		examPageView.deleteVerbalizedExam(verb);
	}
}