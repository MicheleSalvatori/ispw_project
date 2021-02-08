package logic.view.page;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import logic.bean.CourseBean;
import logic.bean.RequestBean;
import logic.bean.UserBean;
import logic.controller.AcceptRequestController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.AlertController;
import logic.utilities.PageLoader;
import logic.view.card.element.CourseFilterCard;
import logic.view.card.element.RequestCard;

public class RequestPageView implements Initializable {
	
	@FXML
	private VBox vboxRequest;
	
	@FXML
	private VBox vboxCourse;
	
	private AcceptRequestController acceptRequestController;
	
	private List<RequestBean> requests;
	
	private List<String> filteredCourses = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		acceptRequestController = new AcceptRequestController();

		try {
			List<CourseBean> courses = acceptRequestController.getCourses(UserBean.getInstance());
			for (CourseBean courseBean : courses) {
				CourseFilterCard courseFilterCard = new CourseFilterCard(courseBean);
				vboxCourse.getChildren().add(courseFilterCard.getPane());
			}
			
			updateRequests();
			
		} catch (RecordNotFoundException e) {
			vboxCourse.getChildren().add(new Label("No course found"));
			
		} catch (SQLException e) {
			AlertController.infoAlert(AlertController.getError());
			PageLoader.getInstance().goBack();
		}
	}
	
	public void updateRequests() {
		
		vboxRequest.getChildren().clear();
		acceptRequestController = new AcceptRequestController();
		
		try {
			requests = acceptRequestController.getRequests(UserBean.getInstance());
			for (RequestBean requestBean : requests) {	
				RequestCard requestCard = new RequestCard(requestBean);
				vboxRequest.getChildren().add(requestCard.getPane());
			}
			
		} catch (RecordNotFoundException e) {
			vboxRequest.getChildren().add(new Label("No request found"));
			
		} catch (SQLException e) {
			AlertController.infoAlert(AlertController.getError());
			PageLoader.getInstance().goBack();
		}
	}
	
	public void filterRequests(CourseBean course) {
		
		if (filteredCourses.contains(course.getAbbreviation())) {
			filteredCourses.remove(course.getAbbreviation());
		}
		else {
			filteredCourses.add(course.getAbbreviation());
		}

		try {
			vboxRequest.getChildren().clear();
			
			for (RequestBean requestBean : requests) {
				if (filteredCourses.contains(requestBean.getCourse().getAbbreviation()) || filteredCourses.isEmpty()) {
					RequestCard requestCard = new RequestCard(requestBean);
					vboxRequest.getChildren().add(requestCard.getPane());
				}
			}
			
			if (vboxRequest.getChildren().isEmpty()) {
				vboxRequest.getChildren().add(new Label("No request found."));
			}
			
		} catch (NullPointerException e) { //TODO Unchecked Exception
			vboxRequest.getChildren().add(new Label("No request found"));
		}
	}
	
	public void acceptRequest(RequestBean requestBean) {
		acceptRequestController = new AcceptRequestController();
		
		try {
			acceptRequestController.acceptRequest(requestBean);
			AlertController.infoAlert("Request accepted.");
			updateRequests();
			
		} catch (SQLException e) {
			AlertController.infoAlert(AlertController.getError());
			updateRequests();
		}
	}
	
	public void declineRequest(RequestBean requestBean) {
		acceptRequestController = new AcceptRequestController();
		
		try {
			acceptRequestController.declineRequest(requestBean);
			AlertController.infoAlert("Request declined.");
			updateRequests();
			
		} catch (SQLException e) {
			AlertController.infoAlert(AlertController.getError());
			updateRequests();
		}
	}
}
