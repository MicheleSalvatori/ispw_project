package logic.view.page;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
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
import logic.model.Lesson;
import logic.model.Professor;
import logic.model.dao.LessonDAO;
import logic.model.dao.ProfessorDAO;
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

    private CourseBean course;

    @FXML
    void viewScheduledExams(ActionEvent event) throws IOException {
    	PageLoader.getInstance().buildPage(Page.SCHEDULED_EXAMS, event, course);
    }

    @FXML
    void viewScheduledLessons(ActionEvent event) throws IOException {
    	PageLoader.getInstance().buildPage(Page.SCHEDULED_LESSONS, event, course);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for (int i=0; i<5; i++) {
			try {
				WeeklyLessonCard weeklyLessonCard = new WeeklyLessonCard("Monday", "A3", "10:00");
				vboxScroll.getChildren().add(weeklyLessonCard);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public void setBean(Object course) {
		this.course = (CourseBean) course;
		setPage();
	}
	
	public void setPage() {
		
		Date date = new Date(System.currentTimeMillis());
		Time time = new Time(System.currentTimeMillis());
		
		Lesson lesson;
		LessonCard lessonCard;
		try {
			lesson = LessonDAO.getNextLessonByCourse(date, time, course.getAbbrevation());
			
			LessonBean lessonBean = new LessonBean();
			lessonBean.setClassroom(lesson.getClassroom());
			lessonBean.setCourse(lesson.getCourse());
			lessonBean.setDate(lesson.getDate());
			lessonBean.setProfessor(lesson.getProfessor());
			lessonBean.setTime(lesson.getTime());
			lessonBean.setTopic(lesson.getTopic());
			
			lessonCard = new LessonCard(lessonBean);

			anchorNextLesson.getChildren().add(lessonCard); 
			
		} catch (NullPointerException e) {
			System.out.println("NullPointer");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		labelCourse.setText(course.getAbbrevation());
		List<Professor> professors;
		try {
			professors = ProfessorDAO.getCourseProfessors(course.getAbbrevation());
			labelProfessor.setText("");
			for (Professor professor : professors) {
				labelProfessor.setText(labelProfessor.getText()+"/"+professor.getName() + " " + professor.getSurname());
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		labelYear.setText(course.getYear());
		labelCredits.setText(course.getCredits());
		labelGoal.setText(course.getGoal());
		labelReception.setText(course.getReception());
		labelPrerequisites.setText(course.getPrerequisites());
		labelSemester.setText(course.getSemester());
	}
}
