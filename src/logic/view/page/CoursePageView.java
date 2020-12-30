package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.card.element.LessonCard;
import logic.view.card.element.WeeklyLessonCard;

public class CoursePageView implements Initializable{

    @FXML
    private Label labelCourse;

    @FXML
    private Label labelProfessor;

    @FXML
    private AnchorPane anchorNextLesson;

    @FXML
    private Button btnViewLessons;

    @FXML
    private Button btnViewExams;

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
    private VBox vboxScroll;

    @FXML
    void viewScheduledExams(ActionEvent event) throws IOException {
    	PageLoader.getInstance().buildPage(Page.SCHEDULED_EXAMS, event);
    }

    @FXML
    void viewScheduledLessons(ActionEvent event) throws IOException {
    	PageLoader.getInstance().buildPage(Page.SCHEDULED_LESSONS, event);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		LessonCard lesson;
		try {
			lesson = new LessonCard("Caccia", "B9", "15:00");
			anchorNextLesson.getChildren().add(lesson);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		for (int i=0; i<5; i++) {
			try {
				WeeklyLessonCard weeklyLessonCard = new WeeklyLessonCard("Monday", "A3", "10:00");
				vboxScroll.getChildren().add(weeklyLessonCard);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		
		//carica dettagli corso
		labelYear.setText("3");
		labelCourse.setText("ISPW");
		labelCredits.setText("12");
		labelGoal.setText("Diventare dei falliti");
		labelReception.setText("At the end of the lesson outside the classroom");
		labelPrerequisites.setText("Algorithms and data structures");
		labelProfessor.setText("Gulyx");
		labelSemester.setText("II");
	}
}
