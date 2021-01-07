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
import logic.Session;
import logic.bean.CourseBean;
import logic.bean.RequestBean;
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
	
	List<Request> requests;
	List<Course> courses;
	
	List<String> filteredCourses = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			courses = CourseDAO.getProfessorCourses(Session.getSession().getUserLogged().getUsername());
			for (Course c : courses) {
				CourseBean courseBean = new CourseBean();
				courseBean.setAbbrevation(c.getAbbrevation());
				courseBean.setCredits(c.getCredits());
				courseBean.setGoal(c.getGoal());
				courseBean.setName(c.getName());
				courseBean.setPrerequisites(c.getPrerequisites());
				courseBean.setReception(c.getReception());
				courseBean.setSemester(c.getSemester());
				courseBean.setYear(c.getYear());
				
				CourseFilterCard courseFilterCard = new CourseFilterCard(courseBean);
				vboxCourse.getChildren().add(courseFilterCard);
			}
			
			updateRequests();
				
		} catch (NullPointerException e) {
			vboxCourse.getChildren().add(new Label("No course found"));
			return;
			
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
		try {
			requests = RequestDAO.getRequestsByProfessor(Session.getSession().getUserLogged().getUsername());
			for (Request request : requests) {
				RequestBean requestBean = new RequestBean();
				requestBean.setCourse(request.getCourse());
				requestBean.setStudent(request.getStudent());
						
				RequestCard requestCard = new RequestCard(requestBean);
				vboxRequest.getChildren().add(requestCard);
			}
			
		} catch (NullPointerException e) {
			vboxRequest.getChildren().add(new Label("No request found"));
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
	
	public void filterRequests(CourseBean course) {
		
		if (filteredCourses.contains(course.getAbbrevation())) {
			filteredCourses.remove(course.getAbbrevation());
		}
		else {
			filteredCourses.add(course.getAbbrevation());
		}

		try {
			vboxRequest.getChildren().clear();
			for (Request request : requests) {
				if (filteredCourses.contains(request.getCourse().getAbbrevation()) || filteredCourses.isEmpty()) {
					RequestBean requestBean = new RequestBean();
					requestBean.setCourse(request.getCourse());
					requestBean.setStudent(request.getStudent());
					
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
}
