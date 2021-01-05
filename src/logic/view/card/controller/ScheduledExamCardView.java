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

public class ScheduledExamCardView {
	
	@FXML
	private Label labelClass, labelTime, labelDay;
	
	@FXML
	private Button btnCourse;
	
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
    	courseBean.setReception(course.getReception());;
    	
    	coursePageView.setPage(courseBean);
	}
	
	public void setCard(String day, String course, String classroom, String time) {
		labelDay.setText(day);
		btnCourse.setText(course);
		labelClass.setText(classroom);
		labelTime.setText(time);
	}

}
