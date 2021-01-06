package logic.view.page;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import logic.Session;
import logic.bean.ExamBean;
import logic.bean.LessonBean;
import logic.controller.ScheduleExamController;
import logic.controller.ScheduleLessonController;
import logic.model.Classroom;
import logic.model.Course;
import logic.model.Professor;
import logic.model.dao.ClassroomDAO;
import logic.model.dao.CourseDAO;
import logic.utilities.AlertController;

public class SchedulePageView implements Initializable {
	
	@FXML
	private TextField textTimeLesson, textTimeExam;
	
	@FXML
	private TextArea textTopic, textNote;
	
	@FXML
	private ComboBox<String> comboCourseLesson, comboClassLesson, comboCourseExam, comboClassExam;
	
	@FXML
	private DatePicker dateLesson, dateExam;
	
	@FXML
	private Button btnTimeLesson, btnTimeExam, btnAddLesson, btnAddExam;
	
	private List<Classroom> classrooms;
	private List<Course> courses;
	
	private ScheduleLessonController scheduleLessonController;
	private ScheduleExamController scheduleExamController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		btnAddLesson.disableProperty()
			.bind(textTimeLesson.textProperty().isEmpty()
			.or(comboCourseLesson.valueProperty().isNull())
			.or(comboClassLesson.valueProperty().isNull())
			.or(dateLesson.valueProperty().isNull())
			.or(textTopic.textProperty().isEmpty()));

		btnAddExam.disableProperty()
			.bind(textTimeExam.textProperty().isEmpty()
			.or(comboCourseExam.valueProperty().isNull())
			.or(comboClassExam.valueProperty().isNull())
			.or(dateExam.valueProperty().isNull())
			.or(textNote.textProperty().isEmpty()));
		
		try {
			classrooms = ClassroomDAO.getAllClassrooms();
			for (Classroom classroom : classrooms) {
				comboClassLesson.getItems().add(classroom.getName());
				comboClassExam.getItems().add(classroom.getName());
			}
			
			courses = CourseDAO.getProfessorCourses(Session.getSession().getUserLogged().getUsername());
			for (Course course : courses) {
				comboCourseLesson.getItems().add(course.getAbbrevation());
				comboCourseExam.getItems().add(course.getAbbrevation());
			}
			
		} catch (NullPointerException e) {
			System.out.println("No course available");
			return;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void addLesson(ActionEvent event) throws SQLException {
		LessonBean lessonBean = new LessonBean();
		
		String topic = textTopic.getText();
		lessonBean.setTopic(topic);
		
		LocalDate localDate = dateLesson.getValue();
		Date date = Date.valueOf(localDate);
		lessonBean.setDate(date);
		
		Time time = Time.valueOf(textTimeLesson.getText()+":00");
		lessonBean.setTime(time);
		
		Course course = courses.get(comboCourseLesson.getSelectionModel().getSelectedIndex());
		lessonBean.setCourse(course);
		
		Classroom classroom = classrooms.get(comboClassLesson.getSelectionModel().getSelectedIndex());
		lessonBean.setClassroom(classroom);
		
		Professor professor = (Professor) Session.getSession().getUserLogged();
		lessonBean.setProfessor(professor);
		
		scheduleLessonController = new ScheduleLessonController();
		if (!scheduleLessonController.scheduleLesson(lessonBean)) {
			AlertController.buildInfoAlert("Lesson doesn't added.\nTry later.", "Add lesson", event);
		}
		
		AlertController.buildInfoAlert("Lesson succesfully added", "Add lesson", event);
		resetLessonView();
	}
	
	@FXML
	private void addExam(ActionEvent event) throws SQLException {
		ExamBean examBean = new ExamBean();
		
		String note = textNote.getText();
		examBean.setNote(note);
		
		LocalDate localDate = dateExam.getValue();
		Date date = Date.valueOf(localDate);
		examBean.setDate(date);
		
		Time time = Time.valueOf(textTimeExam.getText()+":00");
		System.out.println(time);
		examBean.setTime(time);
		
		Course course = courses.get(comboCourseLesson.getSelectionModel().getSelectedIndex());
		examBean.setCourse(course);
		
		Classroom classroom = classrooms.get(comboClassLesson.getSelectionModel().getSelectedIndex());
		examBean.setClassroom(classroom);
		
		scheduleExamController = new ScheduleExamController();
		if (!scheduleExamController.scheduleExam(examBean)) {
			AlertController.buildInfoAlert("Exam doesn't added.\nTry later.", "Add exam", event);
		}
		
		AlertController.buildInfoAlert("Exam succesfully added", "Add exam", event);
		resetExamView();
	}
	
	@FXML
	public void time(ActionEvent ae) {
		Pair<String, String> result = AlertController.time(ae);
		
		switch (((Node) ae.getSource()).getId()) {
			
		case "btnTimeLesson":
			if (result != null) {	
				textTimeLesson.setText(result.getKey() + ":" + result.getValue());
			}
			break;
			
		case "btnTimeExam":
			if (result != null) {	
				textTimeExam.setText(result.getKey() + ":" + result.getValue());
			}
			break;
		}
		
	}
	
	private void resetLessonView() {
		textTimeLesson.setText(null);
		textTopic.setText(null);
		
		comboCourseLesson.getSelectionModel().clearSelection();
		comboClassLesson.getSelectionModel().clearSelection();
		
		dateLesson.setValue(null);
	}
	
	private void resetExamView() {
		textTimeExam.setText(null);
		textNote.setText(null);
		
		comboCourseExam.getSelectionModel().clearSelection();
		comboClassExam.getSelectionModel().clearSelection();
		
		dateExam.setValue(null);
	}
}
