package logic.view.card.controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.bean.CourseBean;
import logic.model.Course;
import logic.model.dao.CourseDAO;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.page.CoursePageView;

public class CourseCardView {
	
	// TODO meglio con Entity o senza
	private Course course;

	@FXML
	private Button btnCourse, btnProfessor;
	
	@FXML
	private Label labelYear, labelSemester;
	
	@FXML
	private void course(ActionEvent event) throws SQLException, IOException {
		PageLoader.getInstance().buildPage(Page.COURSE, event);
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
		PageLoader.getInstance().buildPage(Page.PROFESSOR, event);
	}
	
	public void setLabel(String name, String professor, String year, String semester) {
		btnCourse.setText(name);
		btnProfessor.setText(professor);
		labelYear.setText(year);
		labelSemester.setText(semester);
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
