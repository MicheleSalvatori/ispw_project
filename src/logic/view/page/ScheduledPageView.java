package logic.view.page;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import logic.bean.CourseBean;
import logic.bean.ExamBean;
import logic.bean.LessonBean;
import logic.bean.UserBean;
import logic.controller.ScheduledController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.card.element.CourseFilterCard;
import logic.view.card.element.LessonCard;
import logic.view.card.element.ScheduledExamCard;

public class ScheduledPageView implements Initializable {

	@FXML
	private Label labelPage;
	
	@FXML
	private VBox vboxScroll;
	
	@FXML
	private VBox vboxCourse;
	
	private List<LessonBean> lessons;
	private List<ExamBean> exams;
	private List<CourseBean> courses;
	
	private List<String> filteredCourses;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		ScheduledController scheduledController = new ScheduledController();
		filteredCourses = new ArrayList<>();

		try {
			
			if (PageLoader.getPage() == Page.SCHEDULED_LESSONS) {
				labelPage.setText("Lessons");
				
				// Get user lessons
				lessons = scheduledController.getLessons(UserBean.getInstance());
			}
			
			else if (PageLoader.getPage() == Page.SCHEDULED_EXAMS) {
				labelPage.setText("Exams");
				
				// Get user exams
				exams = scheduledController.getExams(UserBean.getInstance());
			}

		} catch (RecordNotFoundException e) {
			vboxScroll.getChildren().add(new Label(e.getMessage()));
			lessons = new ArrayList<>();
			exams = new ArrayList<>();
			
		} catch (SQLException e) {
			AlertController.infoAlert(AlertController.getError());
			PageLoader.getInstance().goBack();
		}
		
		
		try {
			// Get user courses
			courses = scheduledController.getCourses(UserBean.getInstance());
			
		} catch (RecordNotFoundException e) {
			vboxCourse.getChildren().add(new Label(e.getMessage()));
			
		} catch (SQLException e) {
			AlertController.infoAlert(AlertController.getError());
			PageLoader.getInstance().goBack();
		}
	}
	
	public void setBeanLesson(Object obj) {
		CourseBean course = (CourseBean) obj;
		setFilters(course);
		filterLessons(course);
	}
	
	public void setBeanExam(Object obj) {
		CourseBean course = (CourseBean) obj;
		setFilters(course);
		filterExams(course);
	}
	
	public void setFilters(CourseBean course) {
		for (CourseBean courseBean : courses) {	
			CourseFilterCard courseFilterCard = new CourseFilterCard(courseBean);
			if (courseBean.getAbbreviation().compareTo(course.getAbbreviation()) == 0) {
				courseFilterCard.getController().getButton().setSelected(true);
			}
			vboxCourse.getChildren().add(courseFilterCard.getPane());
		}
	}
	
	public void filterLessons(CourseBean course) {
		
		if (filteredCourses.contains(course.getAbbreviation())) {
			filteredCourses.remove(course.getAbbreviation());
		}
		else {
			filteredCourses.add(course.getAbbreviation());
		}
		
		vboxScroll.getChildren().clear();
			
		for (LessonBean lessonBean : lessons) {
			if (filteredCourses.contains(lessonBean.getCourse()) || filteredCourses.isEmpty()) {
				LessonCard lessonCard = new LessonCard(lessonBean);
				vboxScroll.getChildren().add(lessonCard.getPane());
			}
		}
			
		if (vboxScroll.getChildren().isEmpty()) {
			vboxScroll.getChildren().add(new Label("No lesson found."));
		}
	}
	
	
	public void filterExams(CourseBean course) {
		
		if (filteredCourses.contains(course.getAbbreviation())) {
			filteredCourses.remove(course.getAbbreviation());
		}
		else {
			filteredCourses.add(course.getAbbreviation());
		}
		
		vboxScroll.getChildren().clear();
		for (ExamBean examBean : exams) {
			if (filteredCourses.contains(examBean.getCourse()) || filteredCourses.isEmpty()) {
				ScheduledExamCard scheduledExamCard = new ScheduledExamCard(examBean);
				vboxScroll.getChildren().add(scheduledExamCard.getPane());
			}
		}
			
		if (vboxScroll.getChildren().isEmpty()) {
			vboxScroll.getChildren().add(new Label("No exam found."));
		}
	}
}