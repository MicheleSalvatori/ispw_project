package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.bean.CourseBean;
import logic.model.Lesson;
import logic.model.dao.LessonDAO;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.utilities.SQLConverter;
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
    	ScheduledPageView scheduledPageView = (ScheduledPageView) PageLoader.getInstance().getController();
		scheduledPageView.setLessonPage(labelCourse.getText());
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Date date = new Date(System.currentTimeMillis());
		Time time = new Time(System.currentTimeMillis());
		
		Lesson lesson;
		LessonCard lessonCard;
		try {
			lesson = LessonDAO.getNextLesson(date, time);
			System.out.println(lesson.getDate().toLocalDate() + "  :  " + LocalDate.now());
			if (lesson.getDate().toLocalDate().isEqual(LocalDate.now())) {
				lessonCard = new LessonCard("Today", lesson.getClassroom().getName(), SQLConverter.time(lesson.getTime()));
			}
			else {
				lessonCard = new LessonCard(SQLConverter.date(lesson.getDate()), lesson.getClassroom().getName(), SQLConverter.time(lesson.getTime()));
			}
			anchorNextLesson.getChildren().add(lessonCard);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public void setPage(CourseBean courseBean) {
		labelCourse.setText(courseBean.getAbbrevation());
		labelProfessor.setText(courseBean.getProfessor().getName() + " " + courseBean.getProfessor().getSurname());
	}
}
