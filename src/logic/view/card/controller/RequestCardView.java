package logic.view.card.controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.bean.CourseBean;
import logic.bean.RequestBean;
import logic.bean.StudentBean;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.page.RequestPageView;

public class RequestCardView {
	
	@FXML
	private Label labelName;
	
	@FXML
	private Button btnCourse, btnDecline, btnAccept;
	
	private RequestBean request;
	
	@FXML
	private void acceptRequest(ActionEvent event) throws SQLException, IOException {
		RequestPageView requestPageView = (RequestPageView) PageLoader.getInstance().getController();
		requestPageView.acceptRequest(request);
	}
	
	@FXML
	private void declineRequest(ActionEvent event) throws SQLException, IOException {
		RequestPageView requestPageView = (RequestPageView) PageLoader.getInstance().getController();
		requestPageView.declineRequest(request);
	}
	
	@FXML
	private void course(ActionEvent event) throws SQLException, IOException {
    	CourseBean courseBean = request.getCourse();
    	PageLoader.getInstance().buildPage(Page.COURSE, event, courseBean);
	}
	
	public void setCard(RequestBean request) {
		this.request = request;
		
		StudentBean student = request.getStudent();
		labelName.setText(String.format("%s %s (%s)", student.getName(), student.getSurname(), student.getUsername()));
		btnCourse.setText(request.getCourse().getAbbrevation());
	}
}