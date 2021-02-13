package logic.view.card.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.bean.CourseBean;
import logic.bean.RequestBean;
import logic.bean.UserBean;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.page.RequestPageView;

public class RequestCardView {
	
	@FXML
	private Label labelName;
	
	@FXML
	private Button btnCourse;
	
	@FXML
	private Button btnDecline;
	
	@FXML
	private Button btnAccept;
	
	private RequestBean request;
	
	@FXML
	private void acceptRequest(ActionEvent event) {
		RequestPageView requestPageView = (RequestPageView) PageLoader.getInstance().getController();
		requestPageView.acceptRequest(request);
		PageLoader.getInstance().buildPage(Page.REQUEST);
	}
	
	@FXML
	private void declineRequest(ActionEvent event) {
		RequestPageView requestPageView = (RequestPageView) PageLoader.getInstance().getController();
		requestPageView.declineRequest(request);
		PageLoader.getInstance().buildPage(Page.REQUEST);
	}
	
	@FXML
	private void course(ActionEvent event) {
    	CourseBean courseBean = new CourseBean();
    	courseBean.setAbbreviation(request.getCourse());
    	PageLoader.getInstance().buildPage(Page.COURSE, courseBean);
	}
	
	public void setCard(RequestBean request) {
		this.request = request;
		
		UserBean student = request.getStudent();
		labelName.setText(String.format("%s %s", student.getName(), student.getSurname()));
		btnCourse.setText(request.getCourse());
	}
}