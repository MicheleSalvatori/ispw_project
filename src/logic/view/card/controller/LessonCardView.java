package logic.view.card.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
import logic.bean.CourseBean;
import logic.bean.LessonBean;
import logic.model.Course;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.SQLConverter;
import logic.view.page.CoursePageView;
import logic.view.page.LessonPageView;

public class LessonCardView implements Initializable {
	
	@FXML
	private Label labelLesson, labelClass, labelTime;
	
	@FXML
	private Line line1, line2;
	
	@FXML
	private Button btnView, btnCourse;
	
	private LessonBean lesson;
	
	public void setCard(LessonBean lesson) {
		this.lesson = lesson;
		
		if (PageLoader.getPage() == Page.HOMEPAGE) {
			line1.setVisible(false);
			line2.setVisible(false);
			btnCourse.setVisible(false);
			
			labelLesson.setText(lesson.getCourse().getAbbrevation());
			labelClass.setText(lesson.getClassroom().getName());
			labelTime.setText(SQLConverter.time(lesson.getTime()));
		}
		
		else if (PageLoader.getPage() == Page.COURSE){
			line1.setVisible(false);
			line2.setVisible(false);
			btnCourse.setVisible(false);
			
			if (lesson.getDate().toLocalDate().isEqual(LocalDate.now())) {
				labelLesson.setText("Today");
			}
			else {
				labelLesson.setText(SQLConverter.date(lesson.getDate()));
			}	
			
			labelClass.setText(lesson.getClassroom().getName());
			labelTime.setText(SQLConverter.time(lesson.getTime()));
		}
		
		else {
			labelLesson.setText(SQLConverter.date(lesson.getDate()));
			labelClass.setText(lesson.getClassroom().getName());
			labelTime.setText(SQLConverter.time(lesson.getTime()));
			btnCourse.setText(lesson.getCourse().getAbbrevation());
		}
	}
	
	@FXML
	public void course(ActionEvent event) throws IOException, SQLException {
		PageLoader.getInstance().buildPage(Page.COURSE, event);
    	CoursePageView coursePageView = (CoursePageView) PageLoader.getInstance().getController();
    	
    	Course course = lesson.getCourse();
    	
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
	public void viewLesson(ActionEvent ae) throws IOException, SQLException {
		PageLoader.getInstance().buildPage(Page.LESSON, ae);
		LessonPageView lessonPageView = (LessonPageView) PageLoader.getInstance().getController();
		
		lessonPageView.setPage(lesson);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub	
	}
}
