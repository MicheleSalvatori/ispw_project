package logic.view.card.controller;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import logic.bean.CourseBean;
import logic.bean.RequestBean;
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
	private void course(ActionEvent event) {
		PageLoader.getInstance().buildPage(Page.COURSE, courseBean);
	}
	
	@FXML
	private void professor(ActionEvent event) {
		PageLoader.getInstance().buildPage(Page.PROFESSOR);
	}
	
	@FXML
	private void deleteRequest(ActionEvent event) {
		ProfilePageView profilePageView = (ProfilePageView) PageLoader.getInstance().getController();
		
		RequestBean requestBean = new RequestBean();
		requestBean.setStudent(UserBean.getInstance());
		requestBean.setCourse(courseBean.getAbbreviation());
		
		profilePageView.deleteRequest(requestBean);
	}
	
	public void setCourse(CourseBean courseBean, List<UserBean> professors, Type type) {
		this.courseBean = courseBean;
		
		btnCourse.setText(courseBean.getAbbreviation());
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