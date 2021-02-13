package logic.view.page;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import logic.bean.ClassroomBean;
import logic.bean.CourseBean;
import logic.bean.ExamBean;
import logic.bean.LessonBean;
import logic.bean.ProfessorBean;
import logic.bean.UserBean;
import logic.controller.ScheduleController;
import logic.controller.ScheduleExamController;
import logic.controller.ScheduleLessonController;
import logic.exceptions.DatePriorTodayException;
import logic.exceptions.RecordNotFoundException;
import logic.utilities.AlertController;
import logic.utilities.PageLoader;

public class SchedulePageView implements Initializable {
	
	@FXML
	private TextField textTimeLesson;
	
	@FXML
	private TextField textTimeExam;
	
	@FXML
	private TextArea textTopic;
	
	@FXML
	private TextArea textNote;
	
	@FXML
	private ComboBox<String> comboCourseLesson;
	
	@FXML
	private ComboBox<String> comboClassLesson;
	
	@FXML
	private ComboBox<String> comboCourseExam;
	
	@FXML
	private ComboBox<String> comboClassExam;
	
	@FXML
	private DatePicker dateLesson;
	
	@FXML
	private DatePicker dateExam;
	
	@FXML
	private Button btnTimeLesson;
	
	@FXML
	private Button btnTimeExam;
	
	@FXML
	private Button btnAddLesson;
	
	@FXML
	private Button btnAddExam;
	
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
			
			courses = scheduleController.getCourses(UserBean.getInstance());
			for (CourseBean course : courses) {
				comboCourseLesson.getItems().add(course.getAbbreviation());
				comboCourseExam.getItems().add(course.getAbbreviation());
			}

		} catch (SQLException e) {
			AlertController.infoAlert(AlertController.getError());
			PageLoader.getInstance().goBack();
			
		} catch (RecordNotFoundException e) {
			AlertController.infoAlert("You don't teach any courses.");
			PageLoader.getInstance().goBack();
		}
	}
	
	@FXML
	private void addLesson(ActionEvent event) throws DatePriorTodayException {
		LessonBean lessonBean = new LessonBean();
		
		String topic = textTopic.getText();
		lessonBean.setTopic(topic);
		
		try {
			LocalDate localDateNow = LocalDate.now();
			LocalDate localDate = dateLesson.getValue();
			Date dateEntered = Date.valueOf(localDate);
			Date date;
			
			if (dateEntered.before(Date.valueOf(localDateNow))) {
				date = null;
			} else {
				date = dateEntered;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    date = (Date) sdf.parse(date.toString());
		    lessonBean.setDate(date);
		    
		} catch (NullPointerException | ParseException e) {
			throw new DatePriorTodayException("Date entered must be after today");
		}
		
		Time time = Time.valueOf(textTimeLesson.getText()+":00");
		lessonBean.setTime(time);
		
		CourseBean course = courses.get(comboCourseLesson.getSelectionModel().getSelectedIndex());
		lessonBean.setCourse(course);
		
		ClassroomBean classroom = classrooms.get(comboClassLesson.getSelectionModel().getSelectedIndex());
		lessonBean.setClassroom(classroom);
		
		ProfessorBean professor = new ProfessorBean();
		professor.setEmail(UserBean.getInstance().getEmail());
		professor.setName(UserBean.getInstance().getName());
		professor.setPassword(UserBean.getInstance().getPassword());
		professor.setSurname(UserBean.getInstance().getSurname());
		professor.setUsername(UserBean.getInstance().getUsername());
		lessonBean.setProfessor(professor);
		
		ScheduleLessonController scheduleLessonController = new ScheduleLessonController();
		if (!scheduleLessonController.scheduleLesson(lessonBean)) {
			AlertController.infoAlert("Lesson was not added.\nTry later.");
		}
		
		AlertController.infoAlert("Lesson succesfully added");
		resetLessonView();
	}
	
	@FXML
	private void addExam(ActionEvent event) throws DatePriorTodayException {
		ExamBean examBean = new ExamBean();
		
		String note = textNote.getText();
		examBean.setNote(note);
		
		try {
			LocalDate localDateNow = LocalDate.now();
			LocalDate localDate = dateExam.getValue();
			Date dateEntered = Date.valueOf(localDate);
			Date date;
			
			if (dateEntered.before(Date.valueOf(localDateNow))) {
				date = null;
			} else {
				date = dateEntered;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    date = (Date) sdf.parse(date.toString());
		    examBean.setDate(date);
		    
		} catch (NullPointerException | ParseException e) {
			AlertController.infoAlert("Date entered must be after today.");
			throw new DatePriorTodayException("Date entered must be after today");
		}
		
		
		
		Time time = Time.valueOf(textTimeExam.getText()+":00");
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