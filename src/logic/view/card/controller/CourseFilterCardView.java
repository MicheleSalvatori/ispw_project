package logic.view.card.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import logic.bean.CourseBean;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.page.RequestPageView;
import logic.view.page.ScheduledPageView;

public class CourseFilterCardView {
	
	@FXML
	private ToggleButton btnCourse;
	
	private CourseBean course;
	
	@FXML
	private void filter(ActionEvent event) throws IOException {

		if (PageLoader.getPage() == Page.REQUEST) {
			RequestPageView requestPageView = (RequestPageView) PageLoader.getInstance().getController();
			requestPageView.filterRequests(course);
		}
		
		else {
			ScheduledPageView scheduledPageView = (ScheduledPageView) PageLoader.getInstance().getController();
			if (PageLoader.getPage() == Page.SCHEDULED_LESSONS) {
				scheduledPageView.filterLessons(course);
			}
			else if (PageLoader.getPage() == Page.SCHEDULED_EXAMS ) {
				scheduledPageView.filterExams(course);
			}
		}
	}
	
	public void setCard(CourseBean course) {
		this.course = course;
		btnCourse.setText(course.getAbbrevation());
	}
	
	public ToggleButton getButton() {
		return btnCourse;
	}
	
	public CourseBean getCourse() {
		return course;
	}
}