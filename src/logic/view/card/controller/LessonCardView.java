package logic.view.card.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
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
import logic.model.Lesson;
import logic.model.dao.CourseDAO;
import logic.model.dao.LessonDAO;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.page.CoursePageView;
import logic.view.page.LessonPageView;

public class LessonCardView implements Initializable {
	
	@FXML
	private Label labelLesson, labelClass, labelTime;
	
	@FXML
	private Line line1, line2;
	
	@FXML
	private Button btnView, btnCourse;
	
	public void setLabel(String lesson, String classroom, String time) {
		line1.setVisible(false);
		line2.setVisible(false);
		btnCourse.setVisible(false);
		
		labelLesson.setText(lesson);
		labelClass.setText(classroom);
		labelTime.setText(time);
	}
	
	public void setLabel(String day, String classroom, String course, String time) {
		labelLesson.setText(day);
		labelClass.setText(classroom);
		btnCourse.setText(course);
		labelTime.setText(time);
	}
	
	@FXML
	public void course(ActionEvent event) throws IOException, SQLException {
		PageLoader.getInstance().buildPage(Page.COURSE, event);
    	CoursePageView coursePageView = (CoursePageView) PageLoader.getInstance().getController();
    	
    	Course course = CourseDAO.getCourseByAbbrevation(btnCourse.getText());
    	
    	CourseBean courseBean = new CourseBean();
    	courseBean.setAbbrevation(course.getAbbrevation());
    	courseBean.setName(course.getName());
    	courseBean.setProfessor(course.getProfessor());
    	
    	coursePageView.setPage(courseBean);
	}
	
	@FXML
	public void viewLesson(ActionEvent ae) throws IOException, SQLException {
		PageLoader.getInstance().buildPage(Page.LESSON, ae);
		LessonPageView lessonPageView = (LessonPageView) PageLoader.getInstance().getController();
		
		Date date = new Date(System.currentTimeMillis());
		Time time = Time.valueOf(labelTime.getText()+":00");
		
		Lesson lesson = LessonDAO.getLessonByDateAndTime(date, time);
		
		LessonBean lessonBean = new LessonBean();
		lessonBean.setDate(date);
		lessonBean.setTime(time);
		lessonBean.setCourse(lesson.getCourse());
		lessonBean.setClassroom(lesson.getClassroom());
		lessonBean.setTopic(lesson.getTopic());
		
		lessonPageView.setPage(lessonBean);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
