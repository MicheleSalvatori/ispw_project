package logic.view.card.controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.bean.CourseBean;
import logic.bean.ExamBean;
import logic.model.Course;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.SQLConverter;

public class ScheduledExamCardView {
	
	@FXML
	private Label labelClass, labelTime, labelDay;
	
	@FXML
	private Button btnCourse;
	
	private ExamBean exam;
	
	@FXML
	private void course(ActionEvent event) throws SQLException, IOException {
    	Course course = exam.getCourse();
    	
    	CourseBean courseBean = new CourseBean();
    	courseBean.setAbbrevation(course.getAbbrevation());
    	courseBean.setName(course.getName());
    	courseBean.setYear(course.getYear());
    	courseBean.setCredits(course.getCredits());
    	courseBean.setSemester(course.getSemester());
    	courseBean.setPrerequisites(course.getPrerequisites());
    	courseBean.setGoal(course.getGoal());
    	courseBean.setReception(course.getReception());;

    	PageLoader.getInstance().buildPage(Page.COURSE, event, courseBean);
	}
	
	public void setCard(ExamBean exam) {
		this.exam = exam;
		
		labelDay.setText(SQLConverter.date(exam.getDate()));
		btnCourse.setText(exam.getCourse().getAbbrevation());
		labelClass.setText(exam.getClassroom().getName());
		labelTime.setText(SQLConverter.time(exam.getTime()));
	}
}