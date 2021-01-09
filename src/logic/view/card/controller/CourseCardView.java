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
import logic.bean.ProfessorBean;
import logic.bean.RequestBean;
import logic.bean.StudentBean;
import logic.exceptions.RecordNotFoundException;
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
		PageLoader.getInstance().buildPage(Page.PROFESSOR, event, null);
	}
	
	@FXML
	private void deleteRequest(ActionEvent event) throws SQLException, RecordNotFoundException {
		ProfilePageView profilePageView = (ProfilePageView) PageLoader.getInstance().getController();
		
		StudentBean studentBean = new StudentBean();
		studentBean.setEmail(Session.getSession().getUserLogged().getEmail());
		studentBean.setName(Session.getSession().getUserLogged().getName());
		studentBean.setPassword(Session.getSession().getPassword());
		studentBean.setSurname(Session.getSession().getUserLogged().getSurname());
		studentBean.setUsername(Session.getSession().getUsername());
		
		RequestBean requestBean = new RequestBean();
		requestBean.setStudent(studentBean);
		requestBean.setCourse(courseBean);
		
		profilePageView.deleteRequest(requestBean);
	}
	
	public void setCourse(CourseBean courseBean, List<ProfessorBean> professors, Type type) throws SQLException {
		this.courseBean = courseBean;
		
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