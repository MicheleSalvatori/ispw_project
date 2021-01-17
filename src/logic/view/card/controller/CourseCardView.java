package logic.view.card.controller;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import logic.bean.CourseBean;
import logic.bean.ProfessorBean;
import logic.bean.RequestBean;
import logic.bean.StudentBean;
import logic.bean.UserBean;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.card.element.CourseCard.Type;
import logic.view.page.ProfilePageView;

public class CourseCardView {

	@FXML
	private Button btnCourse;
	
	@FXML
	private Button btnProfessor;
	
	@FXML
	private Button btnDelete;
	
	@FXML
	private Line line1;
	
	@FXML
	private Line line2;
	
	@FXML
	private Label labelYear;
		
	@FXML
	private Label labelSemester;
	
	private CourseBean courseBean;
	
	@FXML
	private void course(ActionEvent event) throws IOException {
		PageLoader.getInstance().buildPage(Page.COURSE, courseBean);
	}
	
	@FXML
	private void professor(ActionEvent event) throws IOException {
		PageLoader.getInstance().buildPage(Page.PROFESSOR);
	}
	
	@FXML
	private void deleteRequest(ActionEvent event) {
		ProfilePageView profilePageView = (ProfilePageView) PageLoader.getInstance().getController();
		
		StudentBean studentBean = new StudentBean();
		studentBean.setEmail(UserBean.getInstance().getEmail());
		studentBean.setName(UserBean.getInstance().getName());
		studentBean.setPassword(UserBean.getInstance().getPassword());
		studentBean.setSurname(UserBean.getInstance().getSurname());
		studentBean.setUsername(UserBean.getInstance().getUsername());
		
		RequestBean requestBean = new RequestBean();
		requestBean.setStudent(studentBean);
		requestBean.setCourse(courseBean);
		
		profilePageView.deleteRequest(requestBean);
	}
	
	public void setCourse(CourseBean courseBean, List<ProfessorBean> professors, Type type) {
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