package logic.view.page;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import logic.bean.CourseBean;
import logic.bean.LessonBean;
import logic.bean.ProfessorBean;
import logic.bean.WeeklyLessonBean;
import logic.controller.CourseController;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.Page;
import logic.utilities.PageLoader;
import logic.view.card.element.LessonCard;
import logic.view.card.element.WeeklyLessonCard;

public class CoursePageView {

    @FXML
    private Label labelCourse, labelProfessor, labelPrerequisites, labelGoal, labelReception, labelYear, labelSemester,labelCredits;

    @FXML
    private AnchorPane anchorNextLesson;

    @FXML
    private Button btnViewLessons, btnViewExams;
    
    @FXML
    private VBox vboxScroll;
    
    private CourseBean course;

    @FXML
    void viewScheduledExams(ActionEvent event) throws IOException {
    	PageLoader.getInstance().buildPage(Page.SCHEDULED_EXAMS, course);
    }

    @FXML
    void viewScheduledLessons(ActionEvent event) throws IOException {
    	PageLoader.getInstance().buildPage(Page.SCHEDULED_LESSONS, course);
    }

	
	public void setBean(Object course) {
		this.course = (CourseBean) course;
		setPage();
	}
	
	public void setPage() {
		
		CourseController courseController = new CourseController();
		
		try {
			LessonBean lessonBean = courseController.getNextLesson(course); 
			LessonCard lessonCard = new LessonCard(lessonBean);
			anchorNextLesson.getChildren().add(lessonCard); 
			
			List<ProfessorBean> professors = courseController.getCourseProfessors(course);
			labelProfessor.setText("");
			for (ProfessorBean professor : professors) {
				labelProfessor.setText(labelProfessor.getText()+"/"+professor.getName() + " " + professor.getSurname());
			}	

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (RecordNotFoundException e) {
			// Nothing
			System.out.println(e.getMessage());
		}
		
		
		try {
			List<WeeklyLessonBean> weeklyLessons = courseController.getWeeklyLessons(course);
			for (WeeklyLessonBean lesson : weeklyLessons) {
				WeeklyLessonCard weeklyLessonCard = new WeeklyLessonCard(lesson);
				vboxScroll.getChildren().add(weeklyLessonCard);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (RecordNotFoundException e) {
			// Nothing
			vboxScroll.getChildren().add(new Label(e.getMessage()));
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