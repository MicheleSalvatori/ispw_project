package logic.view.page;

import java.io.IOException;
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
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.card.element.CourseFilterCard;
import logic.view.card.element.LessonCard;
import logic.view.card.element.ScheduledExamCard;

public class ScheduledPageView implements Initializable {

	@FXML
	private Label labelPage;
	
	@FXML
	private VBox vboxScroll, vboxCourse;
	
	private ScheduledController scheduledController;
	
	private List<LessonBean> lessons;
	private List<ExamBean> exams;
	private List<CourseBean> courses;
	
	private List<String> filteredCourses;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		scheduledController = new ScheduledController();
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
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			// Get user courses
			courses = scheduledController.getCourses(UserBean.getInstance());
			
		} catch (RecordNotFoundException e) {
			vboxCourse.getChildren().add(new Label(e.getMessage()));
			return;
			
		} catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setLessonPage(Object obj) throws IOException {
		CourseBean course = (CourseBean) obj;
		setFilters(course);
		filterLessons(course);
	}
	
	public void setExamPage(Object obj) throws IOException {
		CourseBean course = (CourseBean) obj;
		setFilters(course);
		filterExams(course);
	}
	
	public void setFilters(CourseBean course) throws IOException {
		for (CourseBean courseBean : courses) {	
			CourseFilterCard courseFilterCard = new CourseFilterCard(courseBean);
			if (courseBean.getAbbreviation().compareTo(course.getAbbreviation()) == 0) {
				courseFilterCard.getController().getButton().setSelected(true);
			}
			vboxCourse.getChildren().add(courseFilterCard);
		}
	}
	
	public void filterLessons(CourseBean course) {
		
		if (filteredCourses.contains(course.getAbbreviation())) {
			filteredCourses.remove(course.getAbbreviation());
		}
		else {
			filteredCourses.add(course.getAbbreviation());
		}
		
		try {
			vboxScroll.getChildren().clear();
			
			for (LessonBean lessonBean : lessons) {
				if (filteredCourses.contains(lessonBean.getCourse().getAbbreviation()) || filteredCourses.isEmpty()) {
					LessonCard lessonCard = new LessonCard(lessonBean);
					vboxScroll.getChildren().add(lessonCard);
				}
			}
			
			if (vboxScroll.getChildren().isEmpty()) {
				vboxScroll.getChildren().add(new Label("No lesson found."));
			}
			
		} catch (NullPointerException e) {
			vboxScroll.getChildren().add(new Label("No lesson found"));
			return;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void filterExams(CourseBean course) {
		
		if (filteredCourses.contains(course.getAbbreviation())) {
			filteredCourses.remove(course.getAbbreviation());
		}
		else {
			filteredCourses.add(course.getAbbreviation());
		}
		
		try {
			vboxScroll.getChildren().clear();
			for (ExamBean examBean : exams) {
				if (filteredCourses.contains(examBean.getCourse().getAbbreviation()) || filteredCourses.isEmpty()) {
					ScheduledExamCard scheduledExamCard = new ScheduledExamCard(examBean);
					vboxScroll.getChildren().add(scheduledExamCard);
				}
			}
			
			if (vboxScroll.getChildren().isEmpty()) {
				vboxScroll.getChildren().add(new Label("No exam found."));
			}
			
		} catch (NullPointerException e) {
			vboxScroll.getChildren().add(new Label("No exam found"));
			return;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}