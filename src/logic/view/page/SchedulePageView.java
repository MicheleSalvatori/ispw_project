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
import logic.bean.ClassroomBean;
import logic.bean.CourseBean;
import logic.bean.ExamBean;
import logic.bean.LessonBean;
import logic.bean.ProfessorBean;
import logic.controller.ScheduleController;
import logic.controller.ScheduleExamController;
import logic.controller.ScheduleLessonController;
import logic.exceptions.RecordNotFoundException;
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
	
	private List<ClassroomBean> classrooms;
	private List<CourseBean> courses;

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
		
		
		ScheduleController scheduleController = new ScheduleController();
		try {
			classrooms = scheduleController.getClassrooms();
			for (ClassroomBean classroom : classrooms) {
				comboClassLesson.getItems().add(classroom.getName());
				comboClassExam.getItems().add(classroom.getName());
			}
			
			courses = scheduleController.getCourses();
			for (CourseBean course : courses) {
				comboCourseLesson.getItems().add(course.getAbbrevation());
				comboCourseExam.getItems().add(course.getAbbrevation());
			}
			
		} catch (RecordNotFoundException e) {
			System.out.println("No course available");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void addLesson(ActionEvent event) {
		LessonBean lessonBean = new LessonBean();
		
		String topic = textTopic.getText();
		lessonBean.setTopic(topic);
		
		LocalDate localDate = dateLesson.getValue();
		Date date = Date.valueOf(localDate);
		lessonBean.setDate(date);
		
		Time time = Time.valueOf(textTimeLesson.getText()+":00");
		lessonBean.setTime(time);
		
		CourseBean course = courses.get(comboCourseLesson.getSelectionModel().getSelectedIndex());
		lessonBean.setCourse(course);
		
		ClassroomBean classroom = classrooms.get(comboClassLesson.getSelectionModel().getSelectedIndex());
		lessonBean.setClassroom(classroom);
		
		ProfessorBean professor = new ProfessorBean();
		professor.setEmail(Session.getSession().getUserLogged().getEmail());
		professor.setName(Session.getSession().getUserLogged().getName());
		professor.setPassword(Session.getSession().getPassword());
		professor.setSurname(Session.getSession().getUserLogged().getSurname());
		professor.setUsername(Session.getSession().getUsername());
		lessonBean.setProfessor(professor);
		
		ScheduleLessonController scheduleLessonController = new ScheduleLessonController();
		if (!scheduleLessonController.scheduleLesson(lessonBean)) {
			AlertController.infoAlert("Lesson doesn't added.\nTry later.");
		}
		
		AlertController.infoAlert("Lesson succesfully added");
		resetLessonView();
	}
	
	@FXML
	private void addExam(ActionEvent event) {
		ExamBean examBean = new ExamBean();
		
		String note = textNote.getText();
		examBean.setNote(note);
		
		LocalDate localDate = dateExam.getValue();
		Date date = Date.valueOf(localDate);
		examBean.setDate(date);
		
		Time time = Time.valueOf(textTimeExam.getText()+":00");
		System.out.println(time);
		examBean.setTime(time);
		
		CourseBean course = courses.get(comboCourseExam.getSelectionModel().getSelectedIndex());
		examBean.setCourse(course);
		
		ClassroomBean classroom = classrooms.get(comboClassExam.getSelectionModel().getSelectedIndex());
		examBean.setClassroom(classroom);
		
		ScheduleExamController scheduleExamController = new ScheduleExamController();
		if (!scheduleExamController.scheduleExam(examBean)) {
			AlertController.infoAlert("Exam doesn't added.\nTry later.");
		}
		
		AlertController.infoAlert("Exam succesfully added");
		resetExamView();
	}
	
	@FXML
	public void time(ActionEvent event) {
		Pair<String, String> result = AlertController.timeSelector();
		
		if (((Node) event.getSource()).getId().equals("btnTimeLesson") && result != null) {	
			textTimeLesson.setText(result.getKey() + ":" + result.getValue());
		}
		
		else if (((Node) event.getSource()).getId().equals("btnTimeExam") && result != null) {	
			textTimeExam.setText(result.getKey() + ":" + result.getValue());
		}
	}
	
	// Clear lesson views
	private void resetLessonView() {
		textTimeLesson.setText(null);
		textTopic.setText(null);
		
		comboCourseLesson.getSelectionModel().clearSelection();
		comboClassLesson.getSelectionModel().clearSelection();
		
		dateLesson.setValue(null);
	}
	
	// Clear exam views
	private void resetExamView() {
		textTimeExam.setText(null);
		textNote.setText(null);
		
		comboCourseExam.getSelectionModel().clearSelection();
		comboClassExam.getSelectionModel().clearSelection();
		
		dateExam.setValue(null);
	}
}