package logic.view.card.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.page.ScheduledPageView;

public class CourseFilterCardView {
	
	@FXML
	private ToggleButton btnCourse;
	
	@FXML
	private void filter(ActionEvent event) throws IOException {
		PageLoader.getInstance().buildPage(PageLoader.getPage(), event);
		ScheduledPageView scheduledPageView = (ScheduledPageView) PageLoader.getInstance().getController();
		/*if (btnCourse.isSelected()) {
			scheduledPageView.setPage();
			return;
		}*/
		
		if (PageLoader.getPage() == Page.SCHEDULED_LESSONS) {
			scheduledPageView.setLessonPage(btnCourse.getText());
		}
		else if (PageLoader.getPage() == Page.SCHEDULED_EXAMS ) {
			scheduledPageView.setExamPage(btnCourse.getText());
		}
	}
	
	public void setCard(String name) {
		btnCourse.setText(name);
	}
	
	public ToggleButton getButton() {
		return btnCourse;
	}
}
