package logic.view.page;

import java.sql.SQLException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.bean.CourseBean;
import logic.bean.LessonBean;
import logic.bean.ProfessorBean;
import logic.bean.WeeklyLessonBean;
import logic.controller.CourseController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.AlertController;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.card.element.LessonCard;
import logic.view.card.element.WeeklyLessonCard;

public class CoursePageView {

    @FXML
    private Label labelCourse;
    
    @FXML
    private Label labelPrerequisites;
    
    @FXML
    private Label labelGoal;
    
    @FXML
    private Label labelReception;
    
    @FXML
    private Label labelYear;
    
    @FXML
    private Label labelSemester;
    
    @FXML
    private Label labelCredits;
    
    @FXML
    private ListView<String> listProfessor;

    @FXML
    private AnchorPane anchorNextLesson;

    @FXML
    private Button btnViewLessons;
    
    @FXML
    private Button btnViewExams;
    
    @FXML
    private VBox vboxScroll;
    
    private CourseBean course;

    @FXML
    void viewScheduledExams(ActionEvent event) {
    	PageLoader.getInstance().buildPage(Page.SCHEDULED_EXAMS, course);
    }

    @FXML
    void viewScheduledLessons(ActionEvent event) {
    	PageLoader.getInstance().buildPage(Page.SCHEDULED_LESSONS, course);
    }
	
	public void setBean(Object course) {
		this.course = (CourseBean) course;
		setPage();
	}
	
	public void setPage() {
		
		CourseController courseController = new CourseController();
		try {
			List<ProfessorBean> professors = courseController.getCourseProfessors(course);
			for (ProfessorBean professor : professors) {
				listProfessor.getItems().add(professor.getName() + " " + professor.getSurname());
			}	
			
			LessonBean lessonBean = courseController.getNextLesson(course); 
			LessonCard lessonCard = new LessonCard(lessonBean);
			anchorNextLesson.getChildren().add(lessonCard.getPane()); 

		} catch (SQLException e) {
			AlertController.infoAlert(AlertController.getError());
			PageLoader.getInstance().goBack();
			
		} catch (RecordNotFoundException e) {
			anchorNextLesson.getChildren().add(new Label("There is no future lesson."));
		}
		
		try {
			List<WeeklyLessonBean> weeklyLessons = courseController.getWeeklyLessons(course);
			for (WeeklyLessonBean lesson : weeklyLessons) {
				WeeklyLessonCard weeklyLessonCard = new WeeklyLessonCard(lesson);
				vboxScroll.getChildren().add(weeklyLessonCard.getPane());
			}
			
		} catch (SQLException e) {
			AlertController.infoAlert(AlertController.getError());
			PageLoader.getInstance().goBack();
			
		} catch (RecordNotFoundException e) {
			vboxScroll.getChildren().add(new Label(e.getMessage()));
		}

		labelCourse.setText(course.getAbbreviation());
		labelYear.setText(course.getYear());
		labelCredits.setText(course.getCredits());
		labelGoal.setText(course.getGoal());
		labelReception.setText(course.getReception());
		labelPrerequisites.setText(course.getPrerequisites());
		labelSemester.setText(course.getSemester());
	}
}