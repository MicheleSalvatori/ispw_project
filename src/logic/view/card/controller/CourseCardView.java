package logic.view.card.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import logic.Session;
import logic.bean.CourseBean;
import logic.bean.RequestBean;
import logic.controller.JoinCourseController;
import logic.exceptions.RecordNotFoundException;
import logic.model.Professor;
import logic.model.Request;
import logic.model.dao.ProfessorDAO;
import logic.model.dao.RequestDAO;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.card.element.CourseCard.Type;
import logic.view.page.ProfilePageView;

public class CourseCardView {

	@FXML
	private Button btnCourse, btnProfessor, btnDelete;
	
	@FXML
	private Line line1, line2;
	
	@FXML
	private Label labelYear, labelSemester;
	
	private CourseBean courseBean;
	
	@FXML
	private void course(ActionEvent event) throws SQLException, IOException {
		PageLoader.getInstance().buildPage(Page.COURSE, event, courseBean);
	}
	
	@FXML
	private void professor(ActionEvent event) throws IOException {
		PageLoader.getInstance().buildPage(Page.PROFESSOR, event);
	}
	
	@FXML
	private void deleteRequest(ActionEvent event) throws SQLException, RecordNotFoundException {
		if (AlertController.confirmation("Do you want to cancel this request?", event)) {
			Request request = RequestDAO.getRequest(Session.getSession().getUsername(), courseBean.getAbbrevation());
			
			RequestBean requestBean = new RequestBean();
			requestBean.setStudent(request.getStudent());
			requestBean.setCourse(request.getCourse());
			
			JoinCourseController joinCourseController = new JoinCourseController();
			joinCourseController.deleteRequest(requestBean);
			
			AlertController.buildInfoAlert("Request of course '" + request.getCourse().getAbbrevation() + "' deleted.", "request", event);
			
			ProfilePageView profilePageView = (ProfilePageView) PageLoader.getInstance().getController();
			profilePageView.loadCourses();
		}
	}
	
	public void setCourse(CourseBean courseBean, Type type) throws SQLException {
		this.courseBean = courseBean;
		
		List<Professor> professors = ProfessorDAO.getCourseProfessors(courseBean.getAbbrevation());
		btnCourse.setText(courseBean.getAbbrevation());
		btnProfessor.setText(professors.get(0).getName() + " " + professors.get(0).getSurname());
		
		if (type == Type.REQUEST) {
			btnDelete.setVisible(true);
		}
		
		else if (type == Type.FOLLOW){
			labelYear.setVisible(true);
			labelSemester.setVisible(true);
			line1.setVisible(true);
			line2.setVisible(true);
			
			labelYear.setText(courseBean.getYear());
			labelSemester.setText(courseBean.getSemester());
		}
	}
}