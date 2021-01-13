package logic.view.card.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.bean.AssignmentBean;
import logic.bean.CourseBean;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.SQLConverter;

public class AssignmentCardView {

	@FXML
	private Label labelNumber;
	
	@FXML
	private Label labelTitle;
	
	@FXML
	private Label labelDate;
	
	@FXML
	private Button btnView;
	
	@FXML
	private Button btnCourse;
	
	private AssignmentBean assignment;

	public void setCard(AssignmentBean assignment) {
		this.assignment = assignment;
		
		btnCourse.setText(assignment.getCourse().getAbbrevation());
		labelNumber.setText(Integer.toString(assignment.getId()));
		labelDate.setText(SQLConverter.date(assignment.getDate()));
		labelTitle.setText(assignment.getTitle());
	}
	
	@FXML
	private void viewAssignment(ActionEvent event) throws IOException {
		PageLoader.getInstance().buildPage(Page.ASSIGNMENT, assignment);
	}
	
	@FXML
	private void course(ActionEvent event) throws IOException {
    	CourseBean courseBean = assignment.getCourse();
    	PageLoader.getInstance().buildPage(Page.COURSE, courseBean);
	}
}
