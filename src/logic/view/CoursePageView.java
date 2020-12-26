package logic.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.view.card.element.LessonCard;

public class CoursePageView implements Initializable{

    @FXML
    private Label labelCourse;

    @FXML
    private Label labelProfessor;

    @FXML
    private AnchorPane anchorNextLesson;

    @FXML
    private AnchorPane scrollWeeklyLessons;

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
    private VBox weeklyLesson;

    @FXML
    void viewScheduledExams(ActionEvent event) {

    }

    @FXML
    void viewScheduledLessons(ActionEvent event) {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		weeklyLesson = new VBox();
		for (int i=0; i<19; i++) {
			try {
				LessonCard lesson = new LessonCard("Caccia", "B9", "15:00");
				weeklyLesson.getChildren().add(lesson);
				if (i == 0) {		// prima lezione della settimana la mette nell'anchorpane
					anchorNextLesson.getChildren().add(lesson);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		weeklyLesson.setSpacing(20d);
		weeklyLesson.setPadding(new Insets(13, 10, 10, 13));		
		scrollWeeklyLessons.getChildren().add(weeklyLesson);
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
