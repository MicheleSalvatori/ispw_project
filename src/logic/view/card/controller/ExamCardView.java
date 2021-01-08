package logic.view.card.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.bean.CourseBean;
import logic.bean.VerbalizedBean;
import logic.model.Course;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.SQLConverter;

public class ExamCardView {

	@FXML
	private Label labelName, labelDate, labelVote, labelCFU, labelNumber;
	
	@FXML
	private Button btnCourse;
	
	private VerbalizedBean verb;

	public void setCard(VerbalizedBean verb, int num) {
		this.verb = verb;
		
		labelNumber.setText(Integer.toString(num));
		labelDate.setText(SQLConverter.date(verb.getDate()));
		btnCourse.setText(verb.getCourse().getAbbrevation());
		labelCFU.setText(verb.getCourse().getCredits());
		labelName.setText(verb.getCourse().getName());
		labelVote.setText(Integer.toString(verb.getGrade()));
	}
	
	@FXML
	private void course(ActionEvent event) throws IOException {
		Course course = verb.getCourse();
    	
    	CourseBean courseBean = new CourseBean();
    	courseBean.setAbbrevation(course.getAbbrevation());
    	courseBean.setName(course.getName());
    	courseBean.setYear(course.getYear());
    	courseBean.setCredits(course.getCredits());
    	courseBean.setSemester(course.getSemester());
    	courseBean.setPrerequisites(course.getPrerequisites());
    	courseBean.setGoal(course.getGoal());
    	courseBean.setReception(course.getReception());

    	PageLoader.getInstance().buildPage(Page.COURSE, event, courseBean);
	}
}