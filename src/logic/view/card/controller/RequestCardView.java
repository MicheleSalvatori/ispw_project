package logic.view.card.controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.bean.CourseBean;
import logic.bean.RequestBean;
import logic.controller.AcceptRequestController;
import logic.model.Course;
import logic.model.Student;
import logic.utilities.AlertController;
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
		AcceptRequestController acceptRequestController = new AcceptRequestController();
		if (acceptRequestController.acceptRequest(request)) {
			AlertController.buildInfoAlert("Request accepted.\nThe student will be notified", "request", event);
			
			RequestPageView requestPageView =  (RequestPageView) PageLoader.getInstance().getController();
			requestPageView.updateRequests();
		}
	}
	
	@FXML
	private void declineRequest(ActionEvent event) throws SQLException, IOException {
		AcceptRequestController acceptRequestController = new AcceptRequestController();
		if (acceptRequestController.declineRequest(request)) {
			AlertController.buildInfoAlert("Request declined.\nThe student will be notified", "request", event);
			
			RequestPageView requestPageView =  (RequestPageView) PageLoader.getInstance().getController();
			requestPageView.updateRequests();
		}
	}
	
	@FXML
	private void course(ActionEvent event) throws SQLException, IOException {
    	Course course = request.getCourse();
    	
    	CourseBean courseBean = new CourseBean();
    	courseBean.setAbbrevation(course.getAbbrevation());
    	courseBean.setName(course.getName());
    	courseBean.setYear(course.getYear());
    	courseBean.setCredits(course.getCredits());
    	courseBean.setSemester(course.getSemester());
    	courseBean.setPrerequisites(course.getPrerequisites());
    	courseBean.setGoal(course.getGoal());
    	courseBean.setReception(course.getReception());
    	
    	PageLoader.getInstance().buildPage(Page.COURSE, event, courseBean);
	}
	
	public void setCard(RequestBean request) {
		this.request = request;
		
		Student student = request.getStudent();
		labelName.setText(String.format("%s %s (%s)", student.getName(), student.getSurname(), student.getUsername()));
		btnCourse.setText(request.getCourse().getAbbrevation());
	}
}