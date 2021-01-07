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
import logic.exceptions.RecordNotFoundException;
import logic.model.Course;
import logic.model.Student;
import logic.model.dao.CourseDAO;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.page.CoursePageView;
import logic.view.page.RequestPageView;

public class RequestCardView {
	
	@FXML
	private Label labelName;
	
	@FXML
	private Button btnCourse, btnDecline, btnAccept;
	
	private Student student;
	
	@FXML
	private void acceptRequest(ActionEvent event) throws SQLException, RecordNotFoundException, IOException {
		RequestBean requestBean = new RequestBean();
		requestBean.setStudent(student);
		requestBean.setCourse(CourseDAO.getCourseByAbbrevation(btnCourse.getText()));
		
		AcceptRequestController acceptRequestController = new AcceptRequestController();
		if (acceptRequestController.acceptRequest(requestBean)) {
			AlertController.buildInfoAlert("Request accepted.\nThe student will be notified", "request", event);
			
			RequestPageView requestPageView =  (RequestPageView) PageLoader.getInstance().getController();
			requestPageView.loadRequests();
		}
	}
	
	@FXML
	private void declineRequest(ActionEvent event) throws SQLException {
		RequestBean requestBean = new RequestBean();
		requestBean.setStudent(student);
		requestBean.setCourse(CourseDAO.getCourseByAbbrevation(btnCourse.getText()));
		
		AcceptRequestController acceptRequestController = new AcceptRequestController();
		if (acceptRequestController.declineRequest(requestBean)) {
			AlertController.buildInfoAlert("Request declined.\nThe student will be notified", "request", event);
			
			RequestPageView requestPageView =  (RequestPageView) PageLoader.getInstance().getController();
			requestPageView.loadRequests();
		}
	}
	
	@FXML
	private void course(ActionEvent event) throws SQLException, IOException {
		PageLoader.getInstance().buildPage(Page.COURSE, event, null);
    	CoursePageView coursePageView = (CoursePageView) PageLoader.getInstance().getController();
    	
    	Course course = CourseDAO.getCourseByAbbrevation(btnCourse.getText());
    	
    	CourseBean courseBean = new CourseBean();
    	courseBean.setAbbrevation(course.getAbbrevation());
    	courseBean.setName(course.getName());
    	courseBean.setYear(course.getYear());
    	courseBean.setCredits(course.getCredits());
    	courseBean.setSemester(course.getSemester());
    	courseBean.setPrerequisites(course.getPrerequisites());
    	courseBean.setGoal(course.getGoal());
    	courseBean.setReception(course.getReception());
    	
    	coursePageView.setPage(courseBean);
	}
	
	public void setCard(Student student, String course) {
		this.student = student;
		labelName.setText(String.format("%s %s (%s)", student.getName(), student.getSurname(), student.getUsername()));
		btnCourse.setText(course);
	}

}
