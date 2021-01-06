package logic.view.card.controller;

import java.io.IOException;
import java.sql.SQLException;

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
import logic.model.Course;
import logic.model.Request;
import logic.model.dao.CourseDAO;
import logic.model.dao.RequestDAO;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.page.CoursePageView;
import logic.view.page.ProfilePageView;

public class CourseCardView {
	
	// TODO meglio con Entity o senza
	private Course course;

	@FXML
	private Button btnCourse, btnProfessor, btnDelete;
	
	@FXML
	private Line line1, line2;
	
	@FXML
	private Label labelYear, labelSemester;
	
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
	
	@FXML
	private void professor(ActionEvent event) throws IOException {
		PageLoader.getInstance().buildPage(Page.PROFESSOR, event, null);
	}
	
	@FXML
	private void deleteRequest(ActionEvent event) throws SQLException, RecordNotFoundException {
		
		if (AlertController.confirmation("Do you want to cancel this request?", event)) {
			Request request = RequestDAO.getRequest(Session.getSession().getUsername(), btnCourse.getText());
			
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
	
	public void setCard(String course, String professor, String year, String semester) {
		labelYear.setVisible(true);
		labelSemester.setVisible(true);
		line1.setVisible(true);
		line2.setVisible(true);
		
		btnCourse.setText(course);
		btnProfessor.setText(professor);
		labelYear.setText(year);
		labelSemester.setText(semester);
	}
	
	public void setCard(String course, String professor) {
		btnDelete.setVisible(true);
		
		btnCourse.setText(course);
		btnProfessor.setText(professor);
	}
	
	// TODO
	public void setCourse(Course course) {
		this.course = course;
		btnCourse.setText(course.getAbbrevation());
		btnProfessor.setText("lezzo");
		labelYear.setText(course.getYear());
		labelSemester.setText(course.getSemester());
	}
}
