package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import logic.Session;
import logic.exceptions.RecordNotFoundException;
import logic.model.Course;
import logic.model.Request;
import logic.model.dao.CourseDAO;
import logic.model.dao.RequestDAO;
import logic.view.card.element.CourseFilterCard;
import logic.view.card.element.RequestCard;

public class RequestPageView implements Initializable {
	
	@FXML
	private VBox vboxRequest, vboxCourse;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			
			loadRequests();
			
			List<Course> courses = CourseDAO.getProfessorCourses(Session.getSession().getUserLogged().getUsername());
			for (Course course : courses) {
				CourseFilterCard courseFilterCard = new CourseFilterCard(course.getAbbrevation());
				vboxCourse.getChildren().add(courseFilterCard);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void loadRequests() {
		vboxRequest.getChildren().clear();
		
		List<Request> requests;
		try {
			requests = RequestDAO.getRequestsByProfessor(Session.getSession().getUserLogged().getUsername());
			for (Request request : requests) {
				RequestCard requestCard = new RequestCard(request.getStudent(), request.getCourse().getAbbrevation());
				vboxRequest.getChildren().add(requestCard);
			}
			
		} catch (NullPointerException e) {
			return;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (RecordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
