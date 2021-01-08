package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.bean.CourseBean;
import logic.bean.LessonBean;
import logic.bean.ProfessorBean;
import logic.controller.CourseController;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.card.element.LessonCard;
import logic.view.card.element.WeeklyLessonCard;

public class CoursePageView implements Initializable{

    @FXML
    private Label labelCourse, labelProfessor, labelPrerequisites, labelGoal, labelReception, labelYear, labelSemester,labelCredits;

    @FXML
    private AnchorPane anchorNextLesson;

    @FXML
    private Button btnViewLessons, tnViewExams;
    
    @FXML
    private VBox vboxScroll;

    private CourseController courseController;
    
    private CourseBean course;
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// TODO weeklyLesson DB
		for (int i=0; i<5; i++) {
			try {
				WeeklyLessonCard weeklyLessonCard = new WeeklyLessonCard("Monday", "A3", "10:00");
				vboxScroll.getChildren().add(weeklyLessonCard);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}

    @FXML
    void viewScheduledExams(ActionEvent event) throws IOException {
    	PageLoader.getInstance().buildPage(Page.SCHEDULED_EXAMS, event, course);
    }

    @FXML
    void viewScheduledLessons(ActionEvent event) throws IOException {
    	PageLoader.getInstance().buildPage(Page.SCHEDULED_LESSONS, event, course);
    }

	
	public void setBean(Object course) {
		this.course = (CourseBean) course;
		setPage();
	}
	
	public void setPage() {
		
		courseController = new CourseController();
		
		try {
			LessonBean lessonBean = courseController.getNextLesson(course); 
			LessonCard lessonCard = new LessonCard(lessonBean);
			anchorNextLesson.getChildren().add(lessonCard); 
			
			List<ProfessorBean> professors = courseController.getCourseProfessors(course);
			labelProfessor.setText("");
			for (ProfessorBean professor : professors) {
				labelProfessor.setText(labelProfessor.getText()+"/"+professor.getName() + " " + professor.getSurname());
			}
			
		} catch (NullPointerException e) {
			// Nothing
			System.out.println("NullPointer");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		labelCourse.setText(course.getAbbrevation());

		labelYear.setText(course.getYear());
		labelCredits.setText(course.getCredits());
		labelGoal.setText(course.getGoal());
		labelReception.setText(course.getReception());
		labelPrerequisites.setText(course.getPrerequisites());
		labelSemester.setText(course.getSemester());
	}
}