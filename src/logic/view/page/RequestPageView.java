package logic.view.page;

import java.io.IOException;
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
import logic.controller.AcceptRequestController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.AlertController;
import logic.view.card.element.CourseFilterCard;
import logic.view.card.element.RequestCard;

public class RequestPageView implements Initializable {
	
	@FXML
	private VBox vboxRequest, vboxCourse;
	
	private AcceptRequestController acceptRequestController;
	
	private List<RequestBean> requests;
	private List<CourseBean> courses;
	
	private List<String> filteredCourses = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		acceptRequestController = new AcceptRequestController();

		try {
			courses = acceptRequestController.getCourses();
			for (CourseBean courseBean : courses) {
				CourseFilterCard courseFilterCard = new CourseFilterCard(courseBean);
				vboxCourse.getChildren().add(courseFilterCard);
			}
			
			updateRequests();
			
		} catch (RecordNotFoundException e) {
			vboxCourse.getChildren().add(new Label("No course found"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateRequests() {
		
		vboxRequest.getChildren().clear();
		acceptRequestController = new AcceptRequestController();
		
		try {
			requests = acceptRequestController.getRequests();
			for (RequestBean requestBean : requests) {	
				RequestCard requestCard = new RequestCard(requestBean);
				vboxRequest.getChildren().add(requestCard);
			}
			
		} catch (RecordNotFoundException e) {
			vboxRequest.getChildren().add(new Label("No request found"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void filterRequests(CourseBean course) {
		
		if (filteredCourses.contains(course.getAbbrevation())) {
			filteredCourses.remove(course.getAbbrevation());
		}
		else {
			filteredCourses.add(course.getAbbrevation());
		}

		try {
			vboxRequest.getChildren().clear();
			
			for (RequestBean requestBean : requests) {
				if (filteredCourses.contains(requestBean.getCourse().getAbbrevation()) || filteredCourses.isEmpty()) {
					RequestCard requestCard = new RequestCard(requestBean);
					vboxRequest.getChildren().add(requestCard);
				}
			}
			
			if (vboxRequest.getChildren().isEmpty()) {
				vboxRequest.getChildren().add(new Label("No request found."));
			}
			
		} catch (NullPointerException e) {
			vboxRequest.getChildren().add(new Label("No request found"));
			return;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void acceptRequest(RequestBean requestBean) {
		acceptRequestController = new AcceptRequestController();
		if (acceptRequestController.acceptRequest(requestBean)) {
			AlertController.infoAlert("Request accepted.\nThe student will be notified");
			updateRequests();
		}
	}
	
	public void declineRequest(RequestBean requestBean) {
		acceptRequestController = new AcceptRequestController();
		if (acceptRequestController.declineRequest(requestBean)) {
			AlertController.infoAlert("Request declined.\nThe student will be notified");
			updateRequests();
		}
	}
}
